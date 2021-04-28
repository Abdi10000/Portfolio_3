package com.company;


public class StudentData {
    String SID;
    String StudentName;
    String CourseName;
    String CID;
    Integer Grade;

    public StudentData(String SID, String StudentName, String CourseName, String CID, Integer Grade) {
        this.SID = SID;
        this.StudentName = StudentName;
        this.CourseName = CourseName;
        this.CID = CID;
        this.Grade = Grade;
    }

    public StudentData(String Sname, String ID) {
        StudentName = Sname;
        SID = ID;
    }

    // Polymorphism used for inserting grade to the student
    public StudentData (Integer grade, String studentID, String courseID) {
        Grade = grade;
        SID = studentID;
        CID = courseID;
    }


    public String getName() {
        return StudentName;
    }


    // String that display selected information to the user interface
    @Override
    public String toString() {
        return SID + " " + StudentName + " " + CourseName + " " + CID + " " + Grade;
    }
}
