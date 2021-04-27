package com.company;

public class Grade {
    private String SID;
    private String CID;
    private Integer Grade;

    public Grade(String studentID, String courseID, Integer Grade) {
        this.SID = studentID;
        this.CID = courseID;
        this.Grade = Grade;
    }

    public Integer getGrade() {
        return Grade;
    }

    @Override
    public String toString() {
        return " " + Grade;
    }
}

