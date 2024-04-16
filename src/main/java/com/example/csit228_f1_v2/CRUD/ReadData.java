//package com.example.csit228_f1_v2.CRUD;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class ReadData {
//    public static void main(String[] args) {
//        try (Connection c = MySQLConnection.getConnection();
//             Statement statement = c.createStatement()){
//
//            String query = "SELECT * FROM users";
//            ResultSet res = statement.executeQuery(query);
//
//            while (res.next()){
//                int id = res.getInt("id");
//                String name = res.getString("name");
//                String email = res.getString(3);
//                String username = res.getString("username");
//                String password = res.getString("password");
//                System.out.println("ID: " + id + "\nName: " + name + "\nEmail: " + email + "\nUsername: " +  username + "\nPassword: " +  password );
//            }
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//        }
//    }
//}
