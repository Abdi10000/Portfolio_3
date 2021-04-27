package com.company;

public class Course {

    private String CourseName;
    private String CID;
    private String Teacher;
    private String Semester;
    private Integer Year;

    public Course(String courseName, String courseID, String teacher, String semester, Integer year) {
        CourseName = courseName;
        CID = courseID;
        Teacher = teacher;
        Semester = semester;
        Year = year;
    }

    public String getCID() {
        return CID;
    }
}
