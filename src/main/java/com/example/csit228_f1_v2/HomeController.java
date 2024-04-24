package com.example.csit228_f1_v2;

import com.example.csit228_f1_v2.CRUD.CRUD;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class HomeController {

    public Button post_btn;
    public TextArea post_desc;

    public VBox mainVbox;

    public TextArea regEmail, regName,regUsername,regPass;
    public ToggleButton tbNight;
    public ProgressIndicator piProgress;
    public Slider slSlider;
    public ProgressBar pbProgress;

    public Label loginPromptLabel,deletedlabel;

    public TextField lg_username, lg_password, reg_name, reg_email, reg_username, reg_password, prof_username, prof_password, prof_email;
    public Button regBtn;

    public void onSliderChange() {
        double val = slSlider.getValue();
        System.out.println(val);
        piProgress.setProgress(val/100);
        pbProgress.setProgress(val/100);
        if (val == 100) {
            System.exit(0);
        }
    }

    public void LogIn(){
        String uname = lg_username.getText();
        String pword = lg_password.getText();
        User user = CRUD.Login(uname, pword);
        if(user != null){
            try {
                Parent p = FXMLLoader.load(getClass().getResource("home_page.fxml"));
                Scene s = new Scene(p);
                Twitter.stage.setScene(s);
                Twitter.stage.show();
                SESSION.getInstance().setUser(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            loginPromptLabel.setText("Account does not exist.");
            loginPromptLabel.setTextFill(Color.RED); //
            loginPromptLabel.setFont(Font.font("System", 14)); //
        }
    }

    public void onNightModeClick() {
        if (tbNight.isSelected()) {
            tbNight.getParent().setStyle("-fx-background-color: BLACK");
            tbNight.setText("DAY");
        } else {
            tbNight.getParent().setStyle("-fx-background-color: WHITE");
            tbNight.setText("NIGHT");
        }
    }


    public void goBackLogIn(){
        Twitter.goLogIn();
    }

    public void goRegisterPage(){

        try {
            Parent p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register.fxml")));
            Scene s = new Scene(p);
            Twitter.stage.setScene(s);
            Twitter.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void RegisterUser(){
        String name = reg_name.getText();
        String email = reg_email.getText();
        String username = reg_username.getText();
        String password = reg_password.getText();

        CRUD.insertRecord(name, email, username, password);
        goBackLogIn();
    }

    public void goCreateTweet(){
        mainVbox.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create_post.fxml"));
        AnchorPane ap = null;
        try {
            ap = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainVbox.getChildren().add(ap);
    }

    public void goProfile(){

        try {
            Parent p = FXMLLoader.load(getClass().getResource("profile.fxml"));

            AnchorPane ap = (AnchorPane) p;

            TextField uname = (TextField) ap.lookup("#prof_username");
            TextField email = (TextField) ap.lookup("#prof_email");
            TextField password = (TextField) ap.lookup("#prof_password");

            uname.setText(SESSION.getInstance().getUser().username);
            email.setText(SESSION.getInstance().getUser().email);
            password.setText(SESSION.getInstance().getUser().password);

            p = ap;

            Scene s = new Scene(p);
            Twitter.stage.setScene(s);
            Twitter.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goBackHome(){
        try {
            Parent p = FXMLLoader.load(getClass().getResource("home_page.fxml"));
            Scene s = new Scene(p);
            Twitter.stage.setScene(s);
            Twitter.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPosts() throws IOException {
        mainVbox.getChildren().clear();
        ArrayList<Tweet> tweets = CRUD.readPosts();

        for(Tweet t : tweets){
            User user = CRUD.getUser(t.uid);

            FXMLLoader p = new FXMLLoader(getClass().getResource("actual_tweet.fxml"));
            AnchorPane ap = p.load();
            Label uname = (Label) ap.lookup("#tweet_username");
            Label body = (Label) ap.lookup("#tweet_body");
            Button like = (Button) ap.lookup("#tweet_like");
            like.setOnAction(actionEvent -> likePost(t));

            body.setText(t.body);
            like.setText(String.valueOf(t.like));

            if(t.uid == SESSION.getInstance().getUser().uid){
                ap.getStylesheets().clear();
                ap.getStylesheets().add(Objects.requireNonNull(getClass().getResource("moots.css")).toExternalForm());
                uname.setText(user.username + " (you)");
            } else {
                uname.setText(user.username);
            }
            mainVbox.getChildren().add(ap);
        }

    }

    public void addTweet(){
        String a = post_desc.getText();
        CRUD.insertPost(SESSION.getInstance().getUser().uid,a);
    }

    public void deletePost(Tweet t) throws IOException {
        CRUD.deleteTweet(t);
        loadPosts();
    }

    public void likePost(Tweet t){
        CRUD.LikePost(t);
        try {
            loadPosts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePostChanges(String body, int tweetid){
        CRUD.updatePost(body, tweetid);
        try {
            loadPosts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(){
        CRUD.deleteAcc(SESSION.getInstance().getUser().uid);
        goBackLogIn();
    }

    public void saveProfileChanges(){
        CRUD.updateProfileInfo(SESSION.getInstance().getUser().uid, prof_username.getText(), prof_email.getText(), prof_password.getText());
        deletedlabel.setText("Updated successfully!");
        deletedlabel.setTextFill(Color.GREEN); //
        deletedlabel.setFont(Font.font("System", 14)); //
    }

    public void goMyTweets(){

        mainVbox.getChildren().clear();
        ArrayList<Tweet> tweets = CRUD.readPosts();

        for(Tweet t : tweets){
            User user = CRUD.getUser(t.uid);
            if(t.uid == SESSION.getInstance().getUser().uid){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("editdelete_tweet.fxml"));
                AnchorPane ap = null;
                try {
                    ap = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                TextField uname = (TextField) ap.lookup("#tweet_username");
                TextField body = (TextField) ap.lookup("#tweet_body");
                Button like = (Button) ap.lookup("#tweet_like");
                Button edit = (Button) ap.lookup("#tweet_edit");
                Button delete = (Button) ap.lookup("#tweet_delete");

                like.setOnAction(actionEvent -> likePost(t));

                edit.setOnAction(actionEvent -> updatePostChanges(body.getText(), t.tweetid));

                delete.setOnAction(actionEvent -> {
                    try {
                        deletePost(t);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                uname.setText(user.username + " (you)");
                body.setText(t.body);
                like.setText(String.valueOf(t.like));

                mainVbox.getChildren().add(ap);

            }


        }

    }




}
