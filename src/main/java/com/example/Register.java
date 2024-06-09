package com.example;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

public class Register extends HttpServlet {
    private MongoClient mongoClient;
    private MongoDatabase database;

    @Override
    public void init() throws ServletException {
        String uri = "mongodb://localhost:27017";
        MongoClientURI clientURI = new MongoClientURI(uri);
        mongoClient = new MongoClient(clientURI);
        database = mongoClient.getDatabase("crm");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            out.println("<h1>Username and Password are required!</h1>");
            return;
        }

        String otp = UUID.randomUUID().toString();

        MongoCollection<Document> collection = database.getCollection("user");

        Document user = new Document("username", username)
                .append("password", password)
                .append("otp", otp);

        collection.insertOne(user);

        out.println("<h1>Registration Successful</h1>");
        out.println("<p>Your username: " + username + "</p>");
        out.println("<p>Your OTP: " + otp + "</p>");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        String userOtp = req.getHeader("otp");
        String username = req.getParameter("username");

        if (userOtp == null || userOtp.isEmpty() || username == null || username.isEmpty()) {
            out.println("<h1>OTP and Username are required!</h1>");
            return;
        }

        MongoCollection<Document> collection = database.getCollection("user");

        Document userDocument = collection.find(Filters.and(
                Filters.eq("username", username),
                Filters.eq("otp", userOtp)
        )).first();

        if (userDocument != null) {
            out.println("<h1>Your OTP is valid</h1>");
        } else {
            out.println("<h1>Please enter a valid OTP</h1>");
        }
    }

    @Override
    public void destroy() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
