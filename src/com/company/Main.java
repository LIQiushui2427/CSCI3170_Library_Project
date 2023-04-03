package com.company;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

import util.*;
import util.show;


public class Main{


    public static void main(String[] args) throws Exception {
        //1.load driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        //clean database
        clean.delete();
        //initialize database
        init_db.load("src/user.csv", "src/books.csv");
        //2.connect to database
        String url = "jdbc:mysql://localhost:3306/library_system";
        String username = "root";
        String password = "12345678";
        Connection conn = DriverManager.getConnection(url,username,password);

        //show library interface in console
        System.out.println(conn.getClass().getName());
        System.out.println("Connection successful!, Welcome to Library System!");
        System.out.println("Date:" + new java.util.Date());
        System.out.println("----------------------------------------------------");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String uid;
        show.show_books(conn);
        show.show_users(conn);
        System.out.println("----------------------------------------------------");
        while (true){
         System.out.println("Login...Please enter your username:");
                    uid = scanner.next();
                    if(!verify_user.verify(conn, uid)){
                        System.out.println("Username not found, please try again!");
                        continue;
                    }
                    System.out.println("Login successful! Welcome " + uid + "!");
                    break;
        }
        while (true){
            System.out.println("Please choose the following option:\n1.initiate database\n" +
                    "2.Customer operation\n3.Bookstore operation\n4.exit");
            String input = scanner.next();
            switch (input){
                case "1": {//initiate database
                    clean.delete();
                    init_db.load("src/user.csv", "src/books.csv");
                    break;
                }
                case "2": {//customer operation
                    System.out.println("Customer operation here...");
                    customer_operation.customer_operation(conn, uid);
                    continue;
                }
                case "3":{
                    System.out.println("Bookstore operation here...");
                    continue;
                }
                case "4": {
                    System.out.println("Exiting...");
                    System.exit(0);
                    conn.close();
                    System.exit(0);
                }


        }

    }
}
}
