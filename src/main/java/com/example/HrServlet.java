package com.example;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class HrServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
            response.getWriter().write("This is the HR Servlet");

            String userName = request.getParameter("username");
            String password = request.getParameter("password");

            LoginService service = new LoginService();
            service.validateUser(userName,password,response);
        }
}
