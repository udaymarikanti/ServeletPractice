package com.example;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import com.mongodb.client.MongoDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import static java.lang.System.out;

public class Register extends HttpServlet {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private  String username;
    private  String password;

    private  String otp;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String uri ="mongodb://localhost:27017";
        MongoClientURI clientURI = new MongoClientURI(uri);
        mongoClient = new MongoClient(clientURI);
        database = mongoClient.getDatabase("crm");
        // Get collection
        MongoCollection<Document> collection = database.getCollection("user");

        username = req.getParameter("username");
         password = req.getParameter("password");
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            out.println("<h1>Username and Password are required!</h1>");
            return;
        }
        otp = UUID.randomUUID().toString();

        // Create document
        Document user = new Document("username", username)
                .append("password", password).append("otp",otp);

        collection.insertOne(user);


        // Response to client
        out.println("<h1>Registration Successful</h1>");
        out.println("<h1>your username :  </h1>"+username);
        out.println("<h1>Your otp</h1>"+otp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MongoCollection<Document> collection = database.getCollection("user");
        PrintWriter out = resp.getWriter();


       String userOtp = req.getParameter("otp");
      //  Document getDocument = ;
            Document userDocument= collection.find(Filters.and(
                    Filters.eq("username", username),
                    Filters.eq("otp", userOtp)
            )).first();

            if(userDocument!=null){
            out.println("<h1>your Otp is valide</h1>");
        }else {
            out.println("<h1>please enter valid otp  </h1>");
        }


    }

    @Override
    public void destroy() {
        super.destroy();
        // Close MongoDB connection
        if (mongoClient != null) {
            mongoClient.close();
        }
    }


}
