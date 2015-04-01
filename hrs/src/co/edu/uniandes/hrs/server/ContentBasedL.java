package co.edu.uniandes.hrs.server;

/**
 * La idea es la siguiente para el init:
 * 1. Hacer el start
 * 
 * La idea es la siguiente para el list de recomendados:
 * 1. Pedir el usuario
 * 2. Tomar todos los documentos de los otros que sean distintos a los del usuario y al elemento e indexarlos (Bien calificados?)
 * 3. Recomendar según las entradas del usuario (Se mezclan todas o se hacen por grupos?)
 * 
 * El resultado debe dejar los predict en otro vector por si lo preguntan
 * 
 */
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import co.edu.uniandes.hrs.shared.CBParametersL;
import co.edu.uniandes.hrs.shared.CBResultL;

public class ContentBasedL {

	private Directory indexDir;
	private StandardAnalyzer analyzer;
	private IndexWriterConfig config;
	private CBResultL cblr=new CBResultL();

	public CBResultL initCB(CBParametersL data) throws IOException {
		start(); //Inicia los analizadores
		
		//TODO modificar las entradas para que sean las de mongo
		writerEntries();
		
		findSilimar(data);
		return(this.cblr);
	}
	
	public void start() throws IOException{
		analyzer = new StandardAnalyzer();
		config = new IndexWriterConfig(Version.LATEST, analyzer);
		config.setOpenMode(OpenMode.CREATE_OR_APPEND);
		
		indexDir = new RAMDirectory(); //don't write on disk
		//indexDir = FSDirectory.open(new File("/Path/to/luceneIndex/")); //write on disk
	}
	
	public void writerEntries() throws IOException{
		IndexWriter indexWriter = new IndexWriter(indexDir, config);
		indexWriter.commit();
		
		Document doc1 = createDocument("1","doduck","all what i love is prototype your a one interesting idea");
		Document doc2 = createDocument("2","doduck","I think I love programming");
		Document doc3 = createDocument("3","We do", "prototype");
		Document doc4 = createDocument("4","We love", "this is our challange");
		indexWriter.addDocument(doc1);
		indexWriter.addDocument(doc2);
		indexWriter.addDocument(doc3);
		indexWriter.addDocument(doc4);
		
		indexWriter.commit();
		indexWriter.forceMerge(100, true);
		indexWriter.close();
	}

	private Document createDocument(String id, String title, String content) {
		FieldType type = new FieldType();
		type.setIndexed(true);
		type.setStored(true);
		type.setStoreTermVectors(true); //TermVectors are needed for MoreLikeThis
		
		Document doc = new Document();
		doc.add(new StringField("id", id, Store.YES));
		doc.add(new Field("title", title, type));
		doc.add(new Field("content", content, type));
		return doc;
	}


	private void findSilimar(CBParametersL searchForSimilar) throws IOException {
		IndexReader reader = DirectoryReader.open(indexDir);
		IndexSearcher indexSearcher = new IndexSearcher(reader);
		
		MoreLikeThis mlt = new MoreLikeThis(reader);
	    mlt.setMinTermFreq(searchForSimilar.getMinTermFrequency());
	    mlt.setMinDocFreq(searchForSimilar.getMinDocFrequency());
	    mlt.setMinWordLen(searchForSimilar.getMinWordLen());
	    mlt.setFieldNames(new String[]{"title", "content"});
	    mlt.setAnalyzer(analyzer);
	    
	    
	    Reader sReader = new StringReader("doduck prototype");
	    Query query = mlt.like(null,sReader);
		
	    TopDocs topDocs = indexSearcher.search(query,10);
	    
	    for ( ScoreDoc scoreDoc : topDocs.scoreDocs ) {
	        Document aSimilar = indexSearcher.doc( scoreDoc.doc );
	        String similarTitle = aSimilar.get("title");
	        String similarContent = aSimilar.get("content");
	        
	        System.out.println("====similar finded====");
	        System.out.println("title: "+ similarTitle);
	        System.out.println("content: "+ similarContent);
	    }
	    String[] rrrr=mlt.retrieveInterestingTerms(0);
	    for(int i=0;i<rrrr.length;i++)
	    		System.out.println(rrrr[i]);
	    this.cblr.setData(rrrr);
	}
	
}
