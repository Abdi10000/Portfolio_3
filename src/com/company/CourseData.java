package com.company;

public class CourseData {
    String SID;
    Integer Grade;
    String StudentName;
    String CID;
    String CourseName;
    String Semester;
    Integer Year;
    String Teacher;

    public CourseData(String SID, Integer Grade, String StudentName, String CID, String CourseName, String Semester, Integer Year, String Teacher) {
        this.SID = SID;
        this.Grade = Grade;
        this.StudentName = StudentName;
        this.CID = CID;
        this.CourseName = CourseName;
        this.Semester = Semester;
        this.Year = Year;
        this.Teacher = Teacher;
    }

    public String getCID() {
        return CID;
    }


    // String that display selected information to the user interface
    @Override
    public String toString() {
        return SID + " " + Grade + " " + StudentName + " " + CID + " " + CourseName
                + " " + Semester + " " + Year + " " + Teacher;
    }
}
