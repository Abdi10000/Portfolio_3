package com.company;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import java.util.ArrayList;
import java.sql.SQLException;

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

        EventHandler<ActionEvent> printStudents = e -> HandlePrintStudentGrades(view.StudentSelectionComBo.getValue(), view.ClassSelectionComBo.getValue(), view.grade);

        // Creates an action button
        view.FindStudentButton.setOnAction(printStudents);

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

    // prints out the students information
    public void HandlePrintStudentGrades(String student, String course, TextArea txtArea) {
        txtArea.clear();
        model.PreparedStmtPrintStudentInfo();
        ArrayList<StudentModel.StudentData> school = model.FindStudentData(student, course);
        for (int i = 0; i < school.size(); i++) {
            txtArea.appendText("Student ID: " + school.get(i).SID + "\n" +
                                  "Student Name: " + school.get(i).StudentName + "\n" +
                                  "Course Name: " + school.get(i).CourseName + "\n" +
                                  "Course ID: " + school.get(i).CID + "\n" +
                                  "Student Grade: " + school.get(i).Grade);
        }
    }
}