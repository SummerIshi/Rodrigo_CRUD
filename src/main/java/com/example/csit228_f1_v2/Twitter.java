package com.example.csit228_f1_v2;

import com.example.csit228_f1_v2.CRUD.CRUD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Twitter extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Twitter.stage = stage;
        goLogIn();
    }

    public static void goLogIn(){
        CRUD.createTable();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("log_in.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Twitter");
        stage.setScene(scene);

        stage.setScene(scene);
        scene.setFill(Color.CORNFLOWERBLUE);
        stage.show();
    }


}
