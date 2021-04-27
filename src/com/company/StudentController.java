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

        EventHandler<ActionEvent> printStudents = e -> HandlePrintStudentGrades(view.StudentSelectionComBo.getValue(), view.ClassSelectionComBo.getValue(), view.txtArea);
        EventHandler<ActionEvent> printCourseInformation = e-> HandlePrintCourseInfo(view.entireCourseCombo.getValue(), view.txtArea);
        EventHandler<ActionEvent> printStudentResults = e-> HandleStudentResults(view.studentResultCombo.getValue(), view.txtArea);

        //EventHandler<ActionEvent> insertGrade = e-> HandleGradeInsert();

        // Creates an action button
        view.FindStudentButton.setOnAction(printStudents);
        view.findButton.setOnAction(printCourseInformation);
        view.otherButton.setOnAction(printStudentResults);

        //view.inserting.setOnAction();
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

    public ObservableList<Integer> inputGrade() {
        ArrayList<Integer> grades = new ArrayList<>();
        grades.add(-03);
        grades.add(00);
        grades.add(02);
        grades.add(4);
        grades.add(7);
        grades.add(10);
        grades.add(12);
        ObservableList<Integer> gradeOBs = FXCollections.observableList(grades);
        return gradeOBs;
    }


    ObservableList<Student> Null_Grade_Student = FXCollections.observableArrayList();
    ObservableList<Course> Null_Grade_Courses = FXCollections.observableArrayList();

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
                                  "Student Grade: " + school.get(i).Grade + "\n");
            System.out.println();
        }
    }

    // prints out whole course information
    public void HandlePrintCourseInfo(String course, TextArea txtArea) {
        txtArea.clear();
        model.SQLCourseInfo();
        ArrayList<StudentModel.CourseData> infoCourse = model.FindCourseData(course);
            for (int j = 0; j < infoCourse.size(); j++) {
                txtArea.appendText("The student named " + infoCourse.get(j).StudentName + " with the student ID: " + infoCourse.get(j).SID +
                        " got the grade " + infoCourse.get(j).Grade + ",\n" + "from the course " + infoCourse.get(j).CourseName + ".\n" +
                        "The course ID is " + infoCourse.get(j).CID + " and the semester was in " + infoCourse.get(j).Semester + "\n" +
                        "and the year " + infoCourse.get(j).Year + ". The teacher of the course is " + infoCourse.get(j).Teacher + ".\n" +
                        "\n");
            }
            // The average course grade part
            model.averageCourseGrade();
            ArrayList<StudentModel.averageCourse> infos = model.coursesAverage(course);
                for (int i = 0; i < infos.size(); i++) {
                    txtArea.appendText("The course " + infos.get(i).courseName + " has the average grade of " + infos.get(i).courseAverage);
            }
        }


        // prints out whole student data of classes attended and grades
        public void HandleStudentResults(String student, TextArea txtArea) {
        txtArea.clear();
        model.SQLStudentResults();
        ArrayList<StudentModel.GradeStudent> infoResults = model.studentResulties(student);
        for (int k = 0; k < infoResults.size(); k++) {
            txtArea.appendText("Student name: " + infoResults.get(k).StudentName + " with the student ID: " + infoResults.get(k).SID + " from the course" + ".\n" +
                   infoResults.get(k).CourseName + " has grade: " + infoResults.get(k).Grade + ". " + "You can find the info at course ID: " +
                    infoResults.get(k).CID + ".\n" +
                    "\n");
             }
        // The average student grade part
        model.averageStudentGrade();
        ArrayList<StudentModel.averageStudent> info = model.studentAverage(student);
            for (int i = 0; i < info.size(); i++) {
                txtArea.appendText("The student " + info.get(i).studentName + " got the average grade: " + info.get(i).gradeAverage);
            }
        }


        // This method is for showing the printed out grade inserted for the student with null grade
        public void HandleGradeInsert(Integer grade, String studentID, String courseID, TextArea txtArea) {
        txtArea.clear();

        model.insertStudentGrade(grade, studentID, courseID);
        model.PreparedStmtPrintStudentInfo();
            ArrayList<StudentModel.StudentData> school = model.FindStudentData(studentID, courseID);
            for (int i = 0; i < school.size(); i++) {
                txtArea.appendText("Student ID: " + school.get(i).SID + "\n" +
                        "Student Name: " + school.get(i).StudentName + "\n" +
                        "Course Name: " + school.get(i).CourseName + "\n" +
                        "Course ID: " + school.get(i).CID + "\n" +
                        "Student Grade: " + school.get(i).Grade + "\n");
                System.out.println();
            }
    }
}