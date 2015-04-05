package co.edu.uniandes.hrs.server;

/**
 * La idea es la siguiente para el init:
 * 1. Hacer el start
 * 
 * La idea es la siguiente para el list de recomendados:
 * 1. Pedir el usuario
 * 2. Tomar todos los documentos de los otros que sean distintos a los del usuario y al elemento e indexarlos (Bien calificados?)
 * 3. Recomendar seg�n las entradas del usuario (Se mezclan todas o se hacen por grupos?)
 * 
 * El resultado debe dejar los predict en otro vector por si lo preguntan
 * 
 */
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

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

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import co.edu.uniandes.hrs.shared.CBParametersL;
import co.edu.uniandes.hrs.shared.CBResultL;

public class ContentBasedL {

	private Directory indexDir;
	private StandardAnalyzer analyzer;
	private IndexWriterConfig config;
	private CBResultL cblr=new CBResultL();
	private ArrayList<Document> userDocs;

	public CBResultL initCB(CBParametersL data) throws IOException {
		System.out.println("Iniciando recomendador");
		start(); //Inicia los analizadores
		
		System.out.println("Obteniendo datos de los usuarios");
		writerEntries(data.getUser(), (int)(data.getWaitTime()*60));
		
		System.out.println("Recomendando elementos parecidos");
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
	
	public void writerEntries(String user, int secondsToWait) throws IOException{
		IndexWriter indexWriter = new IndexWriter(indexDir, config);
		indexWriter.commit();
		
		MongoClient mongoClient = new MongoClient("localhost");
		DB db = mongoClient.getDB("recommenderBusiness");
		DBCollection coll = db.getCollection("review");
		BasicDBObject allQuery = new BasicDBObject().append("stars", new BasicDBObject("$gt",3));
		allQuery.append("user_id", new BasicDBObject("$ne",user));
		BasicDBObject fields = new BasicDBObject();
		int posicionConsulta=1;
		fields.put("_id", posicionConsulta++);
		fields.put("user_id", posicionConsulta++);
		fields.put("business_id", posicionConsulta++);
		fields.put("text", posicionConsulta);
		DBCursor cursor = coll.find(allQuery, fields);

		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.SECOND, secondsToWait);
			Date now = new Date();
			while(cursor.hasNext() && now.before(calendar.getTime())) {
				try {
					DBObject dbo=cursor.next();
					String _id=dbo.get("_id").toString();
					String user_id=dbo.get("user_id").toString();
					String business_id=dbo.get("business_id").toString();
					String text=dbo.get("text").toString();
					Document doc = createDocument(_id, user_id, business_id, text);
					indexWriter.addDocument(doc);
				} catch (NullPointerException e) {}
				now = new Date();
			}
			indexWriter.commit();
			indexWriter.forceMerge(100, true);
			indexWriter.close();
		} finally {
			cursor.close();
		}
		mongoClient.close();
	}

	private Document createDocument(String id, String user, String business, String content) {
		FieldType type = new FieldType();
		type.setIndexed(true);
		type.setStored(true);
		type.setStoreTermVectors(true); //TermVectors are needed for MoreLikeThis
		
		Document doc = new Document();
		doc.add(new StringField("id", id, Store.YES));
		doc.add(new Field("user_id", user, type));
		doc.add(new Field("business_id", user, type));
		doc.add(new Field("content", content, type));
		return doc;
	}

	private ArrayList<Document> getUserDocs(String user) {
		ArrayList<Document> ret=new ArrayList<Document>();
		MongoClient mongoClient = new MongoClient("localhost");
		DB db = mongoClient.getDB("recommenderBusiness");
		DBCollection coll = db.getCollection("review");
		BasicDBObject allQuery = new BasicDBObject().append("stars", new BasicDBObject("$gt",3));
		allQuery.append("user_id", user);
		BasicDBObject fields = new BasicDBObject();
		int posicionConsulta=1;
		fields.put("_id", posicionConsulta++);
		fields.put("user_id", posicionConsulta++);
		fields.put("business_id", posicionConsulta++);
		fields.put("text", posicionConsulta);
		DBCursor cursor = coll.find(allQuery, fields);

		try {
			while(cursor.hasNext()) {
				try {
					DBObject dbo=cursor.next();
					String _id=dbo.get("_id").toString();
					String user_id=dbo.get("user_id").toString();
					String business_id=dbo.get("business_id").toString();
					String text=dbo.get("text").toString();
					Document doc = createDocument(_id, user_id, business_id, text);
					ret.add(doc);
				} catch (NullPointerException e) {}
			}
		} finally {
			cursor.close();
		}
		mongoClient.close();
		return(ret);
	}
	
	private void findSilimar(CBParametersL searchForSimilar) throws IOException {
		float precision=0;
		float recall=0;
		int found=0;
		Hashtable <String,Integer> businessReferenced=new Hashtable <String,Integer>();
		IndexReader reader = DirectoryReader.open(indexDir);
		IndexSearcher indexSearcher = new IndexSearcher(reader);
		ArrayList<Document> userDocs=this.getUserDocs(searchForSimilar.getUser());
		ArrayList<Document> userVerification=new ArrayList<Document>();
		ArrayList<Document> result=new ArrayList<Document>();
		ArrayList<String> queryText=new ArrayList<String>();
		ArrayList<String> resultText=new ArrayList<String>();
		
		MoreLikeThis mlt = new MoreLikeThis(reader);
	    mlt.setMinTermFreq(searchForSimilar.getMinTermFrequency());
	    mlt.setMinDocFreq(searchForSimilar.getMinDocFrequency());
	    mlt.setMinWordLen(searchForSimilar.getMinWordLen());
	    mlt.setFieldNames(new String[]{"business_id", "content"});
	    mlt.setAnalyzer(analyzer);
	    
	    double docsSelected=1;
	    if(searchForSimilar.getDatasetSize().startsWith("6")) {
	    	docsSelected = userDocs.size() * 0.6;
	    } else if(searchForSimilar.getDatasetSize().startsWith("7")) {
	    	docsSelected = userDocs.size() * 0.7;
	    } else {
	    	docsSelected = userDocs.size() * 0.8;
	    }
	    
	    int posicion=0;
	    while((double)posicion<docsSelected) {
	    	Document doc=userDocs.get(posicion);
		    Query query = mlt.like("content",new StringReader(doc.get("content")));
		    TopDocs topDocs = indexSearcher.search(query,20);
		    queryText.add(doc.get("business_id") + ": " + doc.get("content"));
		    for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
		        Document aSimilar = indexSearcher.doc( scoreDoc.doc );
		        if(businessReferenced.get(aSimilar.get("business_id"))==null) {
		        	businessReferenced.put(aSimilar.get("business_id"),1);
		        }
		        result.add(aSimilar);
		        resultText.add(aSimilar.get("business_id") + ": " + aSimilar.get("content"));
		    }
	    	posicion++;
	    }
	    while(posicion<userDocs.size()) {
	    	userVerification.add(userDocs.get(posicion));
	    	posicion++;
	    }
	    
	    String[] totalResult=new String[result.size()];
	    for(int i=0;i<result.size();i++) {
	    	totalResult[i]=result.get(i).get("business_id");
	    }
	    this.cblr.setData(totalResult);
		
		int maxDataSize=50;
		String[] retListSearch;
		if(totalResult.length<=maxDataSize) {
			maxDataSize=totalResult.length;
		}
		String[] retListData=new String[maxDataSize];
		retListSearch=new String[maxDataSize];
		
		for(int i=0;i<maxDataSize;i++) {
			retListSearch[i]=totalResult[i];
		}
		
		ArrayList<String[]> retListAllData=MongoDB.getBusinessInfo(retListSearch);
		for(int i=0;i<retListAllData.size();i++) {
			String[] businessData=retListAllData.get(i);
			retListData[i]=businessData[7] + ": " +businessData[1] + " (" + businessData[4] + " - " + businessData[5] + ")"; 
		}

		System.out.println("Calculando precision y recall");
		for(int i=0; i<userVerification.size();i++) {
			if(businessReferenced.get(userVerification.get(i).get("business_id"))!=null) {
				found++;
			}
		}
		precision=((float)found)/((float)(found + result.size()));
		recall=((float)found)/((float)(found + userDocs.size()));
		this.cblr.setDataInfo(retListData);
		this.cblr.setPrecision(precision);
		this.cblr.setRecall(recall);
		this.cblr.setUserDocs(this.arrayListToStringArray(queryText));
		this.cblr.setOtherDocs(this.arrayListToStringArray(resultText));
		System.out.println("Fin CBL");
	}
	
	private String[] arrayListToStringArray(ArrayList<String> al) {
		String[] ret=new String[al.size()];
		for(int i=0;i<ret.length;i++) {
			ret[i]=al.get(i);
		}
		return(ret);
	}
}
