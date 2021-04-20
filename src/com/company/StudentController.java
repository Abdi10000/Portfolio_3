package com.company;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentController {

    StudentModel model;
    StudentView view;

    public StudentController(StudentModel model) {
        this.model = model;

        try {
            model.connect();
            model.CreateStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    // Exit button function
    public void setView(StudentView view) {
        this.view = view;
        view.exitButton.setOnAction(e-> Platform.exit());
    }

    // controller for the student names
    public ObservableList<String> getStudent() {
        ArrayList<String> Names = model.SQLQuerryStudentNames();
        ObservableList<String> StudentNames = FXCollections.observableList(Names);
        return StudentNames;
    }

    // Controller for the courses
    public ObservableList<String> getCourse() {
        ArrayList<String> Course = model.SQLQuerryCourseNames();
        ObservableList<String> CourseNames = FXCollections.observableList(Course);
        return CourseNames;
    }

    public void HandlePrintStudentGrades(String student, String course, double grade, double averageGrade, TextArea txtArea) {



    }

}
