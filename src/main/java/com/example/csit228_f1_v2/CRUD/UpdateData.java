//package com.example.csit228_f1_v2.CRUD;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class UpdateData {
//    public static void main(String[] args) {
//        try (Connection c = MySQLConnection.getConnection();
//             PreparedStatement statement = c.prepareStatement("UPDATE users SET name=? WHERE id=?")) {
//
//                String new_name = "Jeongwoo";
//                int id = 2;
//
//                statement.setString(1,new_name);
//                statement.setInt(2,id);
//                int rowsUpdated = statement.executeUpdate();
//                System.out.println("Rows Updated: " + rowsUpdated);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
