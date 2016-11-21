package com.flyfish.common.mongodb;

import java.util.ArrayList;

import org.bson.Document;

import java.util.*;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * description: 
 *<p>
 * @author  
 *<p>
 * create date：2016年11月21日 下午2:58:28 
 *</p>
  * @version 1.0  
*/
public class MongodbLink {

    public static void main(String[] args) {
        
         MongoClient mongoClient=new MongoClient("localhost",27017);
         MongoDatabase mongoDB=mongoClient.getDatabase("liu");
         MongoCollection<Document> collection=mongoDB.getCollection("users");
         Document saveDocument=new Document();
         saveDocument.append("username", "testadd");
         saveDocument.append("password","savepwd");
         collection.insertOne(saveDocument);
         List<Document> docList=collection.find().into(new ArrayList<Document>());
         System.out.println(docList);
        
    }

}
