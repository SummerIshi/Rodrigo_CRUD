package com.example.csit228_f1_v2.CRUD;

import java.sql.*;

public class CRUD {

    public CRUD (){
        super();
    }

    public void createTable(){
        Connection c = MySQLConnection.getConnection();
        String query = "CREATE TABLE IF NOT EXISTS tblusers (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(50) NOT NULL," +
                "email VARCHAR(100) NOT NULL," +
                "username VARCHAR(100) NOT NULL," +
                "password VARCHAR(100) NOT NULL)";
        try {
            Statement statement = c.createStatement();
            statement.execute(query);
            System.out.println("Table created suck-sis-fully");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //CREATE
    public void insertRecord(String name1, String email1, String username1, String password1){
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("INSERT INTO users (name,email,username,password) VALUES (?,?,?,?)");){

            statement.setString(1, name1);
            statement.setString(2, email1);
            statement.setString(3, username1);
            statement.setString(4, password1);

            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows Inserted: " + rowsInserted);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //READ
    public String readData(){
        StringBuilder results;

        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement()) {
            String query = "SELECT * FROM tblusers";
            ResultSet res = statement.executeQuery(query);

            results = new StringBuilder();

            while (res.next()){
                int id = res.getInt( "id");
                String name = res.getString("name");
                String email = res.getString("email");
                String username = res.getString("username");
                String password = res.getString("password");
                results.append("ID: " +
                        id + "\nName: " +
                        name  + "\nEmail: " +
                        email + "\nUsername: " +
                        username + "\nPassword: " +
                        password + "\n\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results.toString();
    }

    public boolean checkRecordPresent(String column, String data1, String data2){
        boolean answer = false;
        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement()) {
            String query = "SELECT * FROM tblusers";
            ResultSet res = statement.executeQuery(query);

            while (res.next()){
                int id = res.getInt( "id");
                String name = res.getString("name");
                String email = res.getString("email");
                String username = res.getString("username");
                String password = res.getString("password");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return answer;
    }

    //UPDATE
    public void updateData(String column, int bounds, String new_data, String operation){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE tblusers SET " + column + "=? WHERE id" + operation + "?")) {

            statement.setString(1, new_data);
            statement.setInt(2, bounds);

            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows Updated: " + rowsUpdated);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateData(String column, int bounds1, int bounds2, String new_data, String operation1, String operation2, String operator){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE tblusers SET " + column + "=? WHERE id" + operation1 + "? " + operator + " id" + operation2 + "?")) {

            statement.setString(1, new_data);
            statement.setInt(2, bounds1);
            statement.setInt(3, bounds2);

            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows Updated: " + rowsUpdated);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //DELETE
    public void deleteData(int bounds1, int bounds2, String operator1, String operator2, String operation){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("DELETE FROM tblusers WHERE id" + operator1 +"? " + operation + " id" + operator2 + "?")) {

            statement.setInt(1,bounds1);
            statement.setInt(2,bounds2);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Rows Deleted: " + rowsDeleted);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
