package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// We inherit the Application class for running the GUI
public class Main extends Application {

        // The start method is used for taking a single parameter of the type Stage
        @Override
        public void start(Stage primaryStage) throws Exception{

        //Establishing a connection with the database
        String url = "jdbc:sqlite:C:\\Users\\Abdi\\Documents\\SD\\StudentDB.db";

        // From the StudentModel class, it connects with the database url
        StudentModel studModel = new StudentModel(url);

        // From the StudentController class, it connects with the StudentModel class
        StudentController control = new StudentController(studModel);

        // From the StudentView class, it connects with the StudentController class
        StudentView view = new StudentView(control);
        control.setView(view);
        primaryStage.setTitle("Student, Course And Grade Finder");
        // The size of the GUI and setting the display
        primaryStage.setScene(new Scene(view.asParent(), 600, 475));
        // This part shows the GUi on the screen
        primaryStage.show();
    }
}