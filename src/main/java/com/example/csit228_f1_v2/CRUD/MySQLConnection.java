package com.example.csit228_f1_v2.CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/dbRodrigo_oop2";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    static Connection getConnection(){
        Connection c = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("DB connection success!!");
        }catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return c;
    }

}
