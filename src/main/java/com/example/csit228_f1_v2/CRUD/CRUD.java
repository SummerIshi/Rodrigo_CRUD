package com.example.csit228_f1_v2.CRUD;

import com.example.csit228_f1_v2.SESSION;
import com.example.csit228_f1_v2.Tweet;
import com.example.csit228_f1_v2.User;

import java.sql.*;
import java.util.ArrayList;

public class CRUD {

    private CRUD (){
        super();
    }

    public static void createTable(){

        try (Connection c = MySQLConnection.getConnection()){

            String query1 = "CREATE TABLE IF NOT EXISTS tblusers (" +
                    "uid INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(50) NOT NULL," +
                    "email VARCHAR(100) NOT NULL," +
                    "username VARCHAR(100) NOT NULL," +
                    "password VARCHAR(200) NOT NULL)";

            String query2 = "CREATE TABLE IF NOT EXISTS tbltweet (" +
                    "tweetid INT PRIMARY KEY AUTO_INCREMENT," +
                    "uid INT(100) NOT NULL," +
                    "body VARCHAR(200) NOT NULL," +
                    "heart INT(50) NOT NULL)";

            c.setAutoCommit(false);
            Statement statement = c.createStatement();
            statement.execute(query1);
            statement.execute(query2);
            c.commit();
            System.out.println("Table created successfully. Transaction Complete");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //CREATE
    public static void insertRecord(String name, String email, String username, String password){

        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("INSERT INTO tblusers (name,email,username,password) VALUES (?,?,?,?)")){

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, username);
            statement.setString(4, password);

            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows " +rowsInserted);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void insertPost(int uid, String body){

        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("INSERT INTO tbltweet (uid,body,heart) VALUES (?,?,?)")){

            statement.setInt(1, uid);
            statement.setString(2, body);
            statement.setInt(3, 0);

            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows " +rowsInserted);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Tweet> readPosts() {
        ArrayList<Tweet> posts = new ArrayList<>();

        try (Connection c = MySQLConnection.getConnection();
             Statement statement = c.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM tbltweet")) {

            while (resultSet.next()) {
                posts.add(new Tweet(resultSet.getInt("tweetid"), resultSet.getInt("uid"), resultSet.getString("body"),resultSet.getInt("heart")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return posts;
    }

    public static User getUser(int uid){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM tblusers WHERE uid=?")) {
            statement.setInt(1,uid);
            ResultSet result = statement.executeQuery();
            result.next();

            int userid = result.getInt("uid");
            String name = result.getString("name");
            String email = result.getString("email");
            String username = result.getString("username");
            String password = result.getString("password");

            return new User(userid,name,email,username,password );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User Login(String username, String password){
        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement()) {
            String query = "SELECT * FROM tblusers";
            ResultSet res = statement.executeQuery(query);

            while (res.next()){
                int uid = res.getInt("uid");
                String name = res.getString("name");
                String email = res.getString("email");
                String data_username = res.getString("username");
                String data_password = res.getString("password");

                if(data_username.equals(username) && data_password.equals(password)){
                    return new User(uid, name, email, data_username, data_password);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    public static void updateProfileInfo(int uid, String uname, String email, String password){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE tblusers SET username=?, email=?, password=? WHERE uid=?")) {

            statement.setString(1, uname);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setInt(4, uid);

            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows Updated: " + rowsUpdated);

            User user = new User(uid, SESSION.getInstance().getUser().name, email, uname, password);
            SESSION.getInstance().setUser(user);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updatePost(String b, int tid){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE tbltweet SET body=? WHERE tweetid=?")) {

            statement.setString(1, b);
            statement.setInt(2, tid);

            statement.executeUpdate();
            System.out.println("Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void LikePost(Tweet t){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement1 = c.prepareStatement("SELECT heart FROM tbltweet WHERE tweetid=?");
            PreparedStatement statement = c.prepareStatement("UPDATE tbltweet SET heart=? WHERE tweetid=?")) {

            statement1.setInt(1,t.tweetid);
            ResultSet likes = statement1.executeQuery();
            likes.next();
            int num_likes = likes.getInt("heart");
            num_likes++;

            statement.setInt(1, num_likes);
            statement.setInt(2, t.tweetid);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteAcc(int uid){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement1 = c.prepareStatement("DELETE FROM tblusers WHERE uid=?");
            PreparedStatement statement2 = c.prepareStatement("DELETE FROM tbltweet WHERE uid=?")) {

            c.setAutoCommit(false);

            statement1.setInt(1,uid);
            statement2.setInt(1,uid);
            statement1.executeUpdate();
            statement2.executeUpdate();

            c.commit();
            System.out.println("Transaction Complete");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteTweet(Tweet tweet){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("DELETE FROM tbltweet WHERE tweetid=?")) {

            statement.setInt(1,tweet.tweetid);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
