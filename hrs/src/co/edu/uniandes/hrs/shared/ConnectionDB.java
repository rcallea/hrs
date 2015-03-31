package co.edu.uniandes.hrs.shared;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.core.stemmers.SnowballStemmer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class ConnectionDB {

	static SnowballStemmer snowballStemmer =  new SnowballStemmer();
	
	public static void main(String[] args) {
		
		//procesarNombre();
		//procesarCategorias();
		//procesarComentarios();
		//procesarAtributos();
		
		//getCiudades();
		//getCategories();
		
		//getKeyWordsCompare();
	}
	
	public static void procesarCategorias()
	{
		HashMap<String,HashMap<String,Integer>> businessId_catId = new HashMap<>();
		
		try {
            
            MongoClient mongoClient = new MongoClient("localhost");
            DB db = mongoClient.getDB("recommenderBusiness");
            
            DBCollection collection = db.getCollection("business");
            
            BasicDBObject allQuery = new BasicDBObject("keyWordsCategories",new BasicDBObject("$exists",false));
            BasicDBObject fields = new BasicDBObject();
      	  	//allQuery.put("business_id", "vcNAWiLM4dR7D2nwwJ7nCA");
      	  	fields.put("business_id", 1);
      	  	fields.put("categories", 1);
       
      	  	DBCursor cursor2 = collection.find(allQuery, fields);
      	  	int j = 0;
      	  	while (cursor2.hasNext()) {
      	  		DBObject cursor = cursor2.next();
      	  		Object[] categories = ((BasicDBList) cursor.get("categories")).toArray();
      	  		String idBusiness =  (String) cursor.get("business_id");

      	  		for (int i = 0; i < categories.length; i++) {
	      	  	
      	  			HashMap<String,Integer> hashCat = null;
			      	if(!businessId_catId.containsKey(idBusiness)){
			      		hashCat = new HashMap<String, Integer>();
			      		hashCat.put(categories[i].toString().trim(), j++);
			      		businessId_catId.put(idBusiness, hashCat);
			      	}
			      	else
			      	{
			      		hashCat = businessId_catId.get(idBusiness);
			      		hashCat.put(categories[i].toString().trim(), j++);
			      		businessId_catId.put(idBusiness, hashCat);
			      	}
				}
      	  	}
           mongoClient.close();
          
        } 
		catch (Exception ex) {
            ex.printStackTrace();
        }
		
		
		try{
				PrintWriter pr= null;
				for (String business  : businessId_catId.keySet()) {
					pr= new PrintWriter(new File("c://procesoCategorias//Archivos//categorias.txt"));
					HashMap<String,Integer> hashCat = businessId_catId.get(business);
					for (String categoria  : hashCat.keySet()) {
						pr.println(categoria);
					}
					pr.close();
				
					//procesar el archivo para que quede sin stopword y aplique stemming 
					//java -cp weka-3.7.0.jar;snowball-20051019.jar  weka.filters.unsupervised.attribute.StringToWordVector -i text_example.arff -o C:/prueba/class1/noticia.arff -W 1000 -C -N 1 -L -M 2 -S -stemmer weka.core.stemmers.SnowballStemmer
					
					 TextDirectoryLoader loader = new TextDirectoryLoader();
					 loader.setDirectory(new File("c://procesoCategorias"));
					 Instances dataRaw = loader.getDataSet();
					 
					 StringToWordVector filter = new StringToWordVector();
					    
					 filter.setInputFormat(dataRaw);
					 filter.setLowerCaseTokens(true);
					 filter.setUseStoplist(true);
					 filter.setStemmer(snowballStemmer);
					 
					 Instances dataFiltered = Filter.useFilter(dataRaw, filter);
					 
					 BasicDBList dbl = new BasicDBList();
					 for (int k = 0; k < dataFiltered.numAttributes(); k++) {
						 String name = dataFiltered.attribute(k).name();
						 if( name.compareTo("class") != 0 && !name.isEmpty())
							 dbl.add(name);
					 }
					  
					  MongoClient mongoClient = new MongoClient("localhost");
			          DB db = mongoClient.getDB("recommenderBusiness");
			          DBCollection collection = db.getCollection("business");
			          
			          BasicDBObject query = new BasicDBObject("business_id", business);
			          BasicDBObject update = new BasicDBObject();
			          update.put("$set", new BasicDBObject("keyWordsCategories",dbl));
			           
			          WriteResult result = collection.update(query, update);
			          mongoClient.close();
				}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		System.out.println("Fin --> ConnectionDB.procesarCategorias()");
	}

	public static void procesarComentarios()
	{
		HashMap<String,HashMap<String,Integer>> businessId_comentarios = new HashMap<>();
		
		try {
            
            MongoClient mongoClient = new MongoClient("localhost");
            DB db = mongoClient.getDB("recommenderBusiness");
            
            DBCollection collection = db.getCollection("review");
            
            BasicDBObject allQuery = new BasicDBObject();
            BasicDBObject fields = new BasicDBObject();
      	  	//allQuery.put("business_id", "vcNAWiLM4dR7D2nwwJ7nCA");
      	  	fields.put("business_id", 1);
      	  	fields.put("text", 1);
       
      	  	DBCursor cursor2 = collection.find(allQuery, fields);
      	  	int j = 0;
      	  	while (cursor2.hasNext()) {
      	  		DBObject cursor = cursor2.next();
      	  		String comentario = (String) cursor.get("text");
      	  		String idBusiness =  (String) cursor.get("business_id");
	      	  	
  	  			HashMap<String,Integer> hashComentario = null;
		      	if(!businessId_comentarios.containsKey(idBusiness)){
		      		hashComentario = new HashMap<String, Integer>();
		      		hashComentario.put(comentario.trim(), j++);
		      		businessId_comentarios.put(idBusiness, hashComentario);
		      	}
		      	else
		      	{
		      		hashComentario = businessId_comentarios.get(idBusiness);
		      		hashComentario.put(comentario.trim(), j++);
		      		businessId_comentarios.put(idBusiness, hashComentario);
		      	}
				
      	  	}
           mongoClient.close();
          
        } 
		catch (Exception ex) {
            ex.printStackTrace();
        }
		
		try{
				PrintWriter pr= null;
				for (String business  : businessId_comentarios.keySet()) {
					pr= new PrintWriter(new File("c://procesoComentarios//Archivos//comentarios.txt"));
					HashMap<String,Integer> hashComentario = businessId_comentarios.get(business);
					for (String comentario  : hashComentario.keySet()) {
						pr.println(comentario);
					}
					pr.close();
				
					//procesar el archivo para que quede sin stopword y aplique stemming 
					//java -cp weka-3.7.0.jar;snowball-20051019.jar  weka.filters.unsupervised.attribute.StringToWordVector -i text_example.arff -o C:/prueba/class1/noticia.arff -W 1000 -C -N 1 -L -M 2 -S -stemmer weka.core.stemmers.SnowballStemmer
					
					 TextDirectoryLoader loader = new TextDirectoryLoader();
					 loader.setDirectory(new File("c://procesoComentarios"));
					 Instances dataRaw = loader.getDataSet();
					 
					 StringToWordVector filter = new StringToWordVector();
					    
					 filter.setInputFormat(dataRaw);
					 filter.setLowerCaseTokens(true);
					 filter.setUseStoplist(true);
					 filter.setStemmer(snowballStemmer);
					 
					 Instances dataFiltered = Filter.useFilter(dataRaw, filter);
					 
					 BasicDBList dbl = new BasicDBList();
					 for (int k = 0; k < dataFiltered.numAttributes(); k++) {
						 String name = dataFiltered.attribute(k).name();
						 if( name.compareTo("class") != 0)
							 dbl.add(name);
					 }
					  
					  MongoClient mongoClient = new MongoClient("localhost");
			          DB db = mongoClient.getDB("recommenderBusiness");
			          DBCollection collection = db.getCollection("business");
			          
			          BasicDBObject query = new BasicDBObject("business_id", business);
			          BasicDBObject update = new BasicDBObject();
			          update.put("$set", new BasicDBObject("keyWordsComments",dbl));
			           
			          WriteResult result = collection.update(query, update);
			          mongoClient.close();
				}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		System.out.println("Fin --> ConnectionDB.procesarComentarios()");
	}

	public static void procesarNombre()
	{
		HashMap<String,String> businessId_nombre = new HashMap<>();
		
		try {
            
            MongoClient mongoClient = new MongoClient("localhost");
            DB db = mongoClient.getDB("recommenderBusiness");
            
            DBCollection collection = db.getCollection("business");
            
            BasicDBObject allQuery = new BasicDBObject("keyWordsName",new BasicDBObject("$exists",false));
      	  	BasicDBObject fields = new BasicDBObject();
      	  	//allQuery.put("business_id", "vcNAWiLM4dR7D2nwwJ7nCA");
      	  	fields.put("business_id", 1);
      	  	fields.put("name", 1);
       
      	  	DBCursor cursor2 = collection.find(allQuery, fields);
      	  	int j = 0;
      	  	while (cursor2.hasNext()) {
      	  		DBObject cursor = cursor2.next();
      	  		String nombre = (String) cursor.get("name");
      	  		String idBusiness =  (String) cursor.get("business_id");
	      	  	
      	  		businessId_nombre.put(idBusiness, nombre);	
      	  	}
           mongoClient.close();
          
        } 
		catch (Exception ex) {
            ex.printStackTrace();
        }
		
		try{
				PrintWriter pr= null;
				for (String business  : businessId_nombre.keySet()) {
					pr= new PrintWriter(new File("c://procesoNombre//Archivos//nombre.txt"));
					String nombre = businessId_nombre.get(business);
					pr.println(nombre);
					pr.close();
				
					//procesar el archivo para que quede sin stopword y aplique stemming 
					 TextDirectoryLoader loader = new TextDirectoryLoader();
					 loader.setDirectory(new File("c://procesoNombre"));
					 Instances dataRaw = loader.getDataSet();
					 
					 StringToWordVector filter = new StringToWordVector();
					    
					 filter.setInputFormat(dataRaw);
					 filter.setLowerCaseTokens(true);
					 filter.setUseStoplist(true);
					 //filter.setStemmer(snowballStemmer);
					 
					 Instances dataFiltered = Filter.useFilter(dataRaw, filter);
					 
					 BasicDBList dbl = new BasicDBList();
					 for (int k = 0; k < dataFiltered.numAttributes(); k++) {
						 String name = dataFiltered.attribute(k).name();
						 if( name.compareTo("class") != 0)
							 dbl.add(name);
					 }
					  
					  MongoClient mongoClient = new MongoClient("localhost");
			          DB db = mongoClient.getDB("recommenderBusiness");
			          DBCollection collection = db.getCollection("business");
			          
			          BasicDBObject query = new BasicDBObject("business_id", business);
			          BasicDBObject update = new BasicDBObject();
			          update.put("$set", new BasicDBObject("keyWordsName",dbl));
			           
			          WriteResult result = collection.update(query, update);
			          mongoClient.close();
				}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		System.out.println("Fin --> ConnectionDB.procesarNombre()");
	}

	public static void procesarAtributos()
	{
		HashMap<String,HashMap<String,Integer>> businessId_attrId = new HashMap<>();
		
		try {
            
            MongoClient mongoClient = new MongoClient("localhost");
            DB db = mongoClient.getDB("recommenderBusiness");
            
            DBCollection collection = db.getCollection("business");
            
            BasicDBObject allQuery = new BasicDBObject();
      	  	BasicDBObject fields = new BasicDBObject();
      	  	//allQuery.put("business_id", "vcNAWiLM4dR7D2nwwJ7nCA");
      	  	fields.put("business_id", 1);
      	  	fields.put("attributes", 1);
       
      	  	DBCursor cursor2 = collection.find(allQuery, fields);
      	  	int j = 0;
      	  	while (cursor2.hasNext()) {
      	  		DBObject cursor = cursor2.next();
      	  		Map attributes = ((BasicDBObject) cursor.get("attributes")).toMap();
      	  		
	      	  	//OJO
      	  		
      	  		String idBusiness =  (String) cursor.get("business_id");

      	  		/*for (int i = 0; i < attributes.length; i++) {
	      	  	
      	  			HashMap<String,Integer> hashAttibute = null;
			      	if(!businessId_attrId.containsKey(idBusiness)){
			      		hashAttibute = new HashMap<String, Integer>();
			      		hashAttibute.put(attributes[i].toString().trim(), j++);
			      		businessId_attrId.put(idBusiness, hashAttibute);
			      	}
			      	else
			      	{
			      		hashAttibute = businessId_attrId.get(idBusiness);
			      		hashAttibute.put(attributes[i].toString().trim(), j++);
			      		businessId_attrId.put(idBusiness, hashAttibute);
			      	}
				}*/
      	  	}
           mongoClient.close();
          
        } 
		catch (Exception ex) {
            ex.printStackTrace();
        }
		
		/*
		try{
				PrintWriter pr= null;
				for (String business  : businessId_attrId.keySet()) {
					pr= new PrintWriter(new File("c://procesoAtributos//Archivos//atributos.txt"));
					HashMap<String,Integer> hashCat = businessId_attrId.get(business);
					for (String categoria  : hashCat.keySet()) {
						pr.println(categoria);
					}
					pr.close();
				
					//procesar el archivo para que quede sin stopword y aplique stemming 
					//java -cp weka-3.7.0.jar;snowball-20051019.jar  weka.filters.unsupervised.attribute.StringToWordVector -i text_example.arff -o C:/prueba/class1/noticia.arff -W 1000 -C -N 1 -L -M 2 -S -stemmer weka.core.stemmers.SnowballStemmer
					
					 TextDirectoryLoader loader = new TextDirectoryLoader();
					 loader.setDirectory(new File("c://procesoAtributos"));
					 Instances dataRaw = loader.getDataSet();
					 
					 StringToWordVector filter = new StringToWordVector();
					    
					 filter.setInputFormat(dataRaw);
					 filter.setLowerCaseTokens(true);
					 filter.setUseStoplist(true);
					 filter.setStemmer(snowballStemmer);
					 
					 Instances dataFiltered = Filter.useFilter(dataRaw, filter);
					 
					 BasicDBList dbl = new BasicDBList();
					 for (int k = 0; k < dataFiltered.numAttributes(); k++) {
						 String name = dataFiltered.attribute(k).name();
						 if( name.compareTo("class") != 0)
							 dbl.add(name);
					 }
					  
					  MongoClient mongoClient = new MongoClient("localhost");
			          DB db = mongoClient.getDB("recommenderBusiness");
			          DBCollection collection = db.getCollection("business");
			          
			          BasicDBObject query = new BasicDBObject("business_id", business);
			          BasicDBObject update = new BasicDBObject();
			          update.put("$set", new BasicDBObject("keyWordsAttributes",dbl));
			           
			          WriteResult result = collection.update(query, update);
			          mongoClient.close();
			        
					break;
				}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}*/
	}

	public static String[] ProcesarRequerimiento(String requerimiento)
	{
		String[] dbl = null;
		try{
				PrintWriter pr= new PrintWriter(new File("c://procesoRequerimiento//Archivos//requerimiento.txt"));
				pr.println(requerimiento);
				pr.close();
				
				TextDirectoryLoader loader = new TextDirectoryLoader();
				loader.setDirectory(new File("c://procesoRequerimiento"));
				Instances dataRaw = loader.getDataSet();
	
				StringToWordVector filter = new StringToWordVector();
	
				filter.setInputFormat(dataRaw);
				filter.setLowerCaseTokens(true);
				filter.setUseStoplist(true);
				filter.setStemmer(snowballStemmer);
	
				Instances dataFiltered = Filter.useFilter(dataRaw, filter);
	
				dbl = new String[dataFiltered.numAttributes()];
				for (int k = 0; k < dataFiltered.numAttributes(); k++) {
					String name = dataFiltered.attribute(k).name();
					if (name.compareTo("class") != 0)
						dbl[k] = name;
				}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return dbl;
	}
	
	public static HashMap<String,List<String[]>> getKeyWordsNegociosComparar(String category, String city)
	{
		HashMap<String,List<String[]>> keyWordsBusiness = new HashMap<>();
		
		MongoClient mongoClient = new MongoClient("localhost");
        DB db = mongoClient.getDB("recommenderBusiness");
        
        DBCollection collection = db.getCollection("business");
        
        BasicDBObject allQuery = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject();
        allQuery.put("city", java.util.regex.Pattern.compile(city));
  	  	allQuery.put("categories", java.util.regex.Pattern.compile(category));
  	  	
  	  	fields.put("business_id", 1);
  	  	fields.put("keyWordsCategories", 1);
  	  	fields.put("keyWordsAttributes", 1);
  	  	fields.put("keyWordsComments", 1);
  	  	fields.put("keyWordsName", 1);
		
  	  	DBCursor cursor2 = collection.find(allQuery, fields);
  	  	while(cursor2.hasNext()){
  	  		DBObject cursor = cursor2.next();
  	  		
  	  		String businessId = (String)cursor.get("business_id");
  	  		
  	  		String[] wordsName = ((BasicDBList)cursor.get("keyWordsName")).toArray(new String[1]);
  	  		
  	  		String[] wordsCategories = new String[1]; 
	  		DBObject objCategories = (DBObject)cursor.get("keyWordsCategories");
	  		if(objCategories != null)
	  			wordsCategories = ((BasicDBList)cursor.get("keyWordsCategories")).toArray(new String[1]);
  	  		
  	  		String[] wordsAttributes = new String[1]; 
  	  		DBObject objAttributes = (DBObject)cursor.get("keyWordsAttributes");
  	  		if(objAttributes != null)
  	  			wordsAttributes = ((BasicDBList)cursor.get("keyWordsAttributes")).toArray(new String[1]);
  	  		
  	  		String[] wordsComments = new String[1]; 
	  		DBObject objComments = (DBObject)cursor.get("keyWordsComments");
	  		if(objComments != null)
	  			wordsComments = ((BasicDBList)cursor.get("keyWordsComments")).toArray(new String[1]);
	  		
  	  		
  	  		List<String[]> arrs = new ArrayList<String[]>();
  	  		arrs.add(wordsName);
  	  		arrs.add(wordsAttributes);
  	  		arrs.add(wordsCategories);
  	  		arrs.add(wordsComments);
  	  		
  	  		keyWordsBusiness.put(businessId, arrs);
  	  	}
  	  	
  	  	mongoClient.close();
		
  	  	return keyWordsBusiness;
	}
	
	public static void getCiudades()
	{
		try {
            
            MongoClient mongoClient = new MongoClient("localhost");
            DB db = mongoClient.getDB("recommenderBusiness");
            
            DBCollection collection = db.getCollection("business");
            
            BasicDBObject allQuery = new BasicDBObject();
      	  	BasicDBObject fields = new BasicDBObject();
      	  	fields.put("business_id", 1);
      	  	fields.put("city", 1);
       
      	  	HashMap<String, Integer> itemHashmap = new HashMap<String, Integer>();
      	  
      	  	DBCursor cursor2 = collection.find(allQuery, fields);
      	  	int j = 0;
      	  	while (cursor2.hasNext()) {
      	  		DBObject cursor = cursor2.next();
      	  		String ciudad = (String)cursor.get("city");
      	  		
      	  		if(!itemHashmap.containsKey(ciudad.trim())){
						itemHashmap.put(ciudad.trim(), j++);
				}
				
      	  		//System.out.println(cursor2.next());
      	  	}
           mongoClient.close();
           
           for (String ciudad  : itemHashmap.keySet()) {
        	   System.out.print(ciudad + "\n");
           }
             
        } 
		catch (UnknownError ex) {
            ex.printStackTrace();
        }
		
	}

	public static void getCategories()
	{
		HashMap<String, Integer> itemHashmap = new HashMap<String, Integer>();
		
		try {
            
            MongoClient mongoClient = new MongoClient("localhost");
            DB db = mongoClient.getDB("recommenderBusiness");
            
            DBCollection collection = db.getCollection("business");
            
            BasicDBObject allQuery = new BasicDBObject();
      	  	BasicDBObject fields = new BasicDBObject();
      	  	//allQuery.put("business_id", "27gyPasypVLFdkVdT6O9vA");
      	  	fields.put("business_id", 1);
      	  	fields.put("categories", 1);
       
      	  	DBCursor cursor2 = collection.find(allQuery, fields);
      	  	int j = 0;
      	  	
      	  	while (cursor2.hasNext()) {
      	  		DBObject cursor = cursor2.next();
      	  		Object[] categories = ((BasicDBList) cursor.get("categories")).toArray();
      	  		
      	  		for (int i = 0; i < categories.length; i++) {
      	  			
      	  			if(!itemHashmap.containsKey(categories[i].toString().trim())){
						itemHashmap.put(categories[i].toString().trim(), j++);
					}
				}
      	  		//System.out.println(cursor2.next());
      	  	}
           mongoClient.close();
           
           for (String category  : itemHashmap.keySet()) {
        	   System.out.print(category + "\n");
           }
             
        } 
		catch (UnknownError ex) {
            ex.printStackTrace();
        }
	}

	public static String getNombreNegocio(String businessId)
	{
		String name = "";
		
		try {
            
            MongoClient mongoClient = new MongoClient("localhost");
            DB db = mongoClient.getDB("recommenderBusiness");
            
            DBCollection collection = db.getCollection("business");
            
            BasicDBObject allQuery = new BasicDBObject();
      	  	BasicDBObject fields = new BasicDBObject();
      	  	allQuery.put("business_id", businessId);
      	  	fields.put("business_id", 1);
      	  	fields.put("name", 1);
       
      	  	DBCursor cursor2 = collection.find(allQuery, fields);
      	  	
      	  	while (cursor2.hasNext()) {
      	  		DBObject cursor = cursor2.next();
      	  		name = (String)cursor.get("name");
      	  	}
           mongoClient.close();
             
        } 
		catch (UnknownError ex) {
            ex.printStackTrace();
        }
		
		return name;
	}
	
}