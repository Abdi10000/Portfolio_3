package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.sql.DriverManager.getConnection;

public class StudentModel {

    Connection conn = null;
    String url;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    StudentModel(String url) {
        this.url = url;
    }

    public void connect() throws SQLException {
        conn = getConnection(url);
    }

    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }

    public void CreateStatement() throws SQLException {
        this.stmt = conn.createStatement();
    }

    // Important data. This data is retrieving the StudentID for the GUI
    public ArrayList<String> SQLQuerryStudentNames() {
        ArrayList<String> Names = new ArrayList<String>();
        String sql = "SELECT StudentID FROM Student;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                Names.add(name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        rs = null;
        return Names;
    }

    // Important data. This data is retrieving the CourseID for the GUI
    public ArrayList<String> SQLQuerryCourseNames() {
        ArrayList<String> Names = new ArrayList<String>();
        String sql = "SELECT CourseID FROM Course;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                Names.add(name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        rs = null;
        return Names;
    }

    // Important data. This data is retrieving the studentName, CourseName and grade
    public void PreparedStmtPrintStudentInfo() {
        String sql = "SELECT G1.SID, S1.StudentName, C1.CourseName, G1.CID, G1.Grade" +
                "FROM Course AS C1 JOIN Grade AS G1 ON C1.CourseID = G1.CID" +
                "LEFT JOIN Student AS S1 ON G1.SID = S1.StudentID" +
                "WHERE G1.SID = ?;";
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    // Probably gonna be used
    class StudentData {
        String student;
        String course;

        public StudentData(String student, String course) {
            this.student = student;
            this.course = course;
        }
    }

    public ArrayList<StudentData> FindStudentData(String student, String course) {
        ArrayList<StudentData> studentdatas = new ArrayList<>();
        try {
            pstmt.setString(1, student);
            pstmt.setString(2, course);
            rs = pstmt.executeQuery();
            if (rs == null) {
                System.out.println("No data found, student does not exist");
            }
            while (rs != null && rs.next()) {
                studentdatas.add(new StudentData(rs.getString(1), rs.getString(2)));
                System.out.println("Student Name: " + rs.getString(1));
                System.out.println("The students course: " + rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return studentdatas;
    }
}