package com.example.csit228_f1_v2;

import com.example.csit228_f1_v2.CRUD.CRUD;
import javafx.scene.control.*;

public class HomeController {

    public CRUD crud = new CRUD();
    public TextArea regEmail, regName,regUsername,regPass;
    public ToggleButton tbNight;
    public ProgressIndicator piProgress;
    public Slider slSlider;
    public ProgressBar pbProgress;

    public void onSliderChange() {
        double val = slSlider.getValue();
        System.out.println(val);
        piProgress.setProgress(val/100);
        pbProgress.setProgress(val/100);
        if (val == 100) {
            System.exit(0);
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

    public void insertNewUser(){
        String name = regName.getText();
        String email = regEmail.getText();
        String username = regUsername.getText();
        String password = regPass.getText();

        crud.insertRecord(name,email,username,password);
    }
}
