package com.example;

import com.mongodb.MongoClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HeaderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("hello  from the header ");
        req.getHeader("username");
        req.getHeader("password");
        req.getRequestDispatcher("HrServlet").forward(req,resp);


    }
}
