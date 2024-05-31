package com.example;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginService {

    String username = "abc";
    String password ="abc";

    public void  validateUser(String username, String password, HttpServletResponse response) throws IOException {
        if(this.username.equals(username)&&(this.password.equals(password))){
            response.getWriter().write("log in success full");

        }else {
            response.getWriter().write("log in failed");
        }
    }
}
