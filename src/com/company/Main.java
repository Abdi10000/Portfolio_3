package com.company;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
//public class Main {

        @Override
        public void start(Stage primaryStage) throws Exception{

        //Establishing connection with the database
        String url = "jdbc:sqlite:C:\\Users\\Abdi\\Documents\\SD\\StudentDB.db";

        // From the StudentModel class
        StudentModel studModel = new StudentModel(url);

        // From the StudentController class
        StudentController control = new StudentController(studModel);

        // From the StudenView class
        StudentView view = new StudentView(control);
        control.setView(view);
        primaryStage.setTitle("Student And Grade Finder");
        primaryStage.setScene(new Scene(view.asParent(), 600, 475));
        primaryStage.show();
    }
}