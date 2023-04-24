package com.example.gabelia_project;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "postsServlet", value = "/posts-servlet")
public class PostsServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/posts";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASS = "my-secret-pw";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error while loading DB driver: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DB_URL, MYSQL_USER, MYSQL_PASS);
            Statement statement = dbConnection.createStatement();

            ResultSet results = statement.executeQuery("select * from " + "posts");

            StringBuilder responseHtml = new StringBuilder("<h1> Posts </h1>");


            while(results.next()) {
                String nickname = results.getString("nickname");
                String firstName = results.getString("first_name");
                String lastName = results.getString("last_name");
                String post = results.getString("post");

                String div = String.format("""
<div style = "border: 2px solid black">

    <p>Nickname: %s </p>
    <p>Person: %s %s </p>
    <hr>
    <p>%s</p>
</div>
<br>
<br>
""", nickname, firstName, lastName, post);
                responseHtml.append(div);
            }

            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println(responseHtml);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String nickname = req.getParameter("nickname");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String post = req.getParameter("post");

        try {
            Connection dbConnection = DriverManager.getConnection(DB_URL, MYSQL_USER, MYSQL_PASS);
            Statement statement = dbConnection.createStatement();
            statement.execute(String.format("insert into posts (nickname, first_name, last_name, post) values ('%s', '%s', '%s', '%s')",
                    nickname, firstName, lastName, post));

            statement.close();
            dbConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting DB connection: " + e.getMessage());
        }

        PrintWriter out = resp.getWriter();
        out.println("Saved");
    }
}