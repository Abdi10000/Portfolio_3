package com.company;

public class GradeStudent {
    String SID;
    String StudentName;
    String CourseName;
    String CID;
    Integer Grade;

    public GradeStudent(String SID, String StudentName, String CourseName, String CID, Integer Grade) {
        this.SID = SID;
        this.StudentName = StudentName;
        this.CourseName = CourseName;
        this.CID = CID;
        this.Grade = Grade;
    }

    public String getName() {
        return CID;
    }

    // String that display selected information to the user interface
    @Override
    public String toString() {
        return SID + " " + StudentName + " " + CourseName + " " + CID + " " + Grade;
    }
}
