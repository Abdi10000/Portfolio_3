package com.company;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import java.util.ArrayList;

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

        // Creates the exit button action
        view.exitButton.setOnAction(e -> Platform.exit());

       // EventHandler<ActionEvent> printStudents = e -> HandlePrintStudentGrades(view.StudentSelectionComBo.getValue(), view.ClassSelectionComBo.getValue(), view.grade);

        // Creates an action button
    //    view.FindStudentButton.setOnAction(printStudents);

    }

    // Controller for the student names
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



  /*  // Look at it for later
    public void HandlePrintStudentGrades(String student, String course, TextArea txtArea) {
        txtArea.clear();
        // Shows this text on the screen when pressing the button called "Find Student"
        txtArea.appendText("Student Name: \nCourse Name: \nGrade: \n");
        model.PreparedStmtPrintStudentInfo();
        ArrayList<StudentData> school = model.FindStudentData(student, course);
        for (int i = 0; i < school.size(); i++) {
            txtArea.appendText(i + " " + school.get(i).student + " " + school.get(i).course);
        }
    } */
}