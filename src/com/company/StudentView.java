package com.company;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class StudentView {
    StudentController control;
    private GridPane StartView;

    // Button and Label are shown on the GUI
    Button exitButton = new Button("Exit");
    Button FindStudentButton = new Button("Show Student Information");
    Label StudentSelection = new Label("Select Student ID");
    Label ClassSelection = new Label("Select Class ID");

    Label resultStudents = new Label("Show student results");
    Label entireCourse = new Label("Show Course Info");

    Button findButton = new Button("Print out the course info");
    Button otherButton = new Button("Print out student results");

    // TextArea is the box showing the information we retrieve from the database
    TextArea txtArea = new TextArea();

    // The ComboBoxes are used to store the data we created from the ObservableList
    ComboBox<String> StudentSelectionComBo = new ComboBox<String>();
    ComboBox<String> ClassSelectionComBo = new ComboBox<String>();
    ComboBox<String> studentResultCombo = new ComboBox<String>();
    ComboBox<String> entireCourseCombo = new ComboBox<String>();

    ComboBox<Integer> gradeListCombo = new ComboBox<Integer>();
    Button inserting = new Button("Insert grade");

    // Here we connect the StudentController with the StudentView
    public StudentView(StudentController control){
        this.control = control;
        CreateAndConfigure();
    }

    // This method we use to edit the GUI and adding labels, buttons and comboboxes
    private void CreateAndConfigure(){
        StartView = new GridPane();
        // Editing part of the GUI
        StartView.setMinSize(300,400);
        StartView.setPadding(new Insets(10,10,10,10));
        StartView.setVgap(5);
        StartView.setHgap(1);

        StartView.add(StudentSelection, 1, 1);
        StartView.add(StudentSelectionComBo, 15, 1);

        StartView.add(ClassSelection, 1, 3);
        StartView.add(ClassSelectionComBo, 15, 3);

        StartView.add(FindStudentButton, 15, 6);
        StartView.add(txtArea, 1, 7, 20, 50);
        StartView.add(exitButton, 1,60);

        StartView.add(resultStudents, 16, 1);
        StartView.add(studentResultCombo, 17,1);

        StartView.add(entireCourse, 16, 3);
        StartView.add(entireCourseCombo, 17, 3);

        StartView.add(findButton, 20, 3);
        StartView.add(otherButton, 20, 1);

        StartView.add(gradeListCombo, 17,4);
        StartView.add(inserting, 20, 4);

        // Shows list of students inside the GUI
        ObservableList<String> studentsList = control.getStudent();
        StudentSelectionComBo.setItems(studentsList);

        // Shows list of courses inside the GUI
        ObservableList<String> classList = control.getCourse();
        ClassSelectionComBo.setItems(classList);

        // Shows student results inside the GUI
        ObservableList<String> studResults = control.getStudent();
        studentResultCombo.setItems(studResults);

        // Show whole course information inside the GUI
        ObservableList<String> courseInfo = control.getCourse();
        entireCourseCombo.setItems(courseInfo);

        // Shows the list of grades used in the danish grading system
        ObservableList<Integer> gradesInsert = control.inputGrade();
        gradeListCombo.setItems(gradesInsert);
    }

    // This method is used to connect with the GUI and display the layout of the StudentView class
    public Parent asParent(){
        return StartView;
    }
}