package com.company;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class StudentView {
    StudentController control;
    private GridPane StartView;
    Button exitButton = new Button("Exit");
    Button FindStudentButton = new Button("Find Student");
    Label StudentSelection = new Label("Select Student");
    Label ClassSelection = new Label("Select Class");
    Label GradeSelection = new Label("Select Grade");

    TextArea averageGrade = new TextArea();
    ComboBox<String> StudentSelectionComBo = new ComboBox<String>();
    ComboBox<String> ClassSelectionComBo = new ComboBox<String>();

    public StudentView(StudentController control){
        this.control = control;
        CreateAndConfigure();
    }

    //object constructor

    private void CreateAndConfigure(){
        StartView = new GridPane();
        StartView.setMinSize(300,200);
        StartView.setPadding(new Insets(10,10,10,10));
        StartView.setVgap(5);
        StartView.setHgap(1);

        // Viewing buttons inside the GUI
        StartView.add(StudentSelection, 1, 1);
        StartView.add(StudentSelectionComBo, 15, 1);
        StartView.add(ClassSelection, 1, 3);
        StartView.add(ClassSelectionComBo, 15, 3);
        StartView.add(GradeSelection, 1, 5);
        StartView.add(FindStudentButton, 15, 6);
        StartView.add(averageGrade, 1, 7, 15, 7);
        StartView.add(exitButton, 20,15);

        // Shows list of students inside the GUI
        ObservableList<String> studentsList = control.getStudent();
        StudentSelectionComBo.setItems(studentsList);
        // Shows the first student in the selected list
        StudentSelectionComBo.getSelectionModel().selectFirst();

        // Shows list of courses inside the GUI
        ObservableList<String> classList = control.getCourse();
        ClassSelectionComBo.setItems(classList);
        // Shows the first course in the selected list
        ClassSelectionComBo.getSelectionModel().selectFirst();
    }

    public Parent asParent(){
        return StartView;
    }
}