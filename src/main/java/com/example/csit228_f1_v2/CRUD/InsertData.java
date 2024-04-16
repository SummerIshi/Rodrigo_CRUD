//package com.example.csit228_f1_v2.CRUD;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class InsertData {
//    public static void main(String[] args) {
//        try(Connection c = MySQLConnection.getConnection();
//            PreparedStatement statement = c.prepareStatement("INSERT INTO users (name, email, username, password) VALUES (?,?)"
//            )){
//            String name = "Jeonghan";
//            String email = " hanni@gmail.com";
//            String username = "Hanni";
//            String password = "123";
//            statement.setString(1,name);
//            statement.setString(2,email);
//            statement.setString(3,username);
//            statement.setString(4,password);
//
//            int rowsInserted = statement.executeUpdate();
//            System.out.println("Rows Inserted: "+ rowsInserted);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
