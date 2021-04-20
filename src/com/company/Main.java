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

 //   public static void main(String[] args) {
 //       launch(args);
 //   }


        // All these code lines are statements
  /*  public static void main(String[] args) {

        //Establishing connection with the database
        String url = "jdbc:sqlite:C:\\Users\\Abdi\\Documents\\SD\\StudentDB.db";
        StudentModel studModel = new StudentModel(url);

        try{
            studModel.connect();
            studModel.CreateStatement();
            ArrayList<String> names = studModel.SQLQuerryStudentNames();
            //studModel.PrintNames(names);
            studModel.StudentInfoQuerry();

            studModel.PreparedStatement();
            studModel.FindGrade("Essential Computing 1");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
             studModel.close();
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    } */
}

// Try out this code when inputting
// Abdi' OR 1==1;--