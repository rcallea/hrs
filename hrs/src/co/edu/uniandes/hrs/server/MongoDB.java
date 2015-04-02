package co.edu.uniandes.hrs.server;

import java.io.BufferedReader;
import java.io.FileReader;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDB {
	
	private void listUserScore() {
		MongoClient mongoClient = new MongoClient("localhost");
		DB db = mongoClient.getDB("recommenderBusiness");
		DBCollection coll = db.getCollection("review");
		BasicDBObject allQuery = new BasicDBObject();
		BasicDBObject fields = new BasicDBObject();
		fields.put("user_id", 1);
		fields.put("business_id", 2);
		fields.put("stars", 3);
		DBCursor cursor = coll.find(allQuery, fields);
		try {
			int i=0;
			System.out.println("Usuario\tNecodio\tCalif");
			while(cursor.hasNext()) {
			   DBObject singleField=(DBObject) cursor.next();
			   System.out.print("" + singleField.get("user_id"));
			   System.out.print("\t" + singleField.get("business_id"));
			   System.out.println("\t" + singleField.get("stars"));
			   
		       i++;
		   }
		} finally {
		   cursor.close();
		}
	}
	
	private void classifyTestData() {
		// db.review.update({},{$set : {"g60":0}},false,true)
		// db.review.update({},{$set : {"g70":0}},false,true)
		// db.review.update({},{$set : {"g80":0}},false,true)
		try {
			MongoClient mongoClient = new MongoClient("localhost");
			DB db = mongoClient.getDB("recommenderBusiness");
			DBCollection coll = db.getCollection("review");
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Ricardo\\Documents\\Maestría\\05SRecomendacion\\distribucion.txt"));
			String line;
			line = reader.readLine();
			String[] tokens;
			while (line != null) {
				// Skip comment lines
				if (line.trim().startsWith("//")) {
					line = reader.readLine();
					continue;
				}
				tokens = line.split("\t");
				String usuario=tokens[0];
				System.out.println("Usuario actual: " + usuario);
				int g60=Integer.parseInt(tokens[2]);
				int g70=Integer.parseInt(tokens[3]);
				int g80=Integer.parseInt(tokens[4]);
				// First, add the ratings.
				//dm.addRating(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
				line = reader.readLine();
				
				//TODO aquí se establece la consulta para que solo saque el usuario
				BasicDBObject allQuery = new BasicDBObject().append("user_id", usuario);
				BasicDBObject fields = new BasicDBObject();
//				fields.put("user_id", 1);
//				fields.put("business_id", 2);
//				fields.put("stars", 3);

				DBCursor cursor = coll.find(allQuery, fields);
				try {
//					System.out.println("Usuario\tNegocio\tCalif");
					while(cursor.hasNext()) {
					   BasicDBObject singleField=(BasicDBObject) cursor.next();
					   int value=0;
					   if(g60>0) {
						   g60--;
						   value=1;
					   }
					   BasicDBObject newDocument=new BasicDBObject();
					   newDocument.append("$set", new BasicDBObject().append("g60", value));
					   coll.update(singleField, newDocument);
					   value=0;

					   if(g70>0) {
						   g70--;
						   value=1;
					   }
					   newDocument=new BasicDBObject();
					   newDocument.append("$set", new BasicDBObject().append("g70", value));
					   coll.update(singleField, newDocument);
					   value=0;
					   
					   if(g80>0) {
						   g80--;
						   value=1;
					   }
					   newDocument=new BasicDBObject();
					   newDocument.append("$set", new BasicDBObject().append("g80", value));
					   coll.update(singleField, newDocument);

//					   System.out.print("" + singleField.get("user_id"));
//					   System.out.print("\t" + singleField.get("business_id"));
//					   System.out.println("\t" + singleField.get("stars"));
					   System.out.println(singleField);
					   
					}
				} finally {
				   cursor.close();
				}
			}
			reader.close();
			mongoClient.close();
		} catch (Exception e) {}
	}
	
	public static void main(String[] args) {
		new MongoDB().classifyTestData();
	}

}
