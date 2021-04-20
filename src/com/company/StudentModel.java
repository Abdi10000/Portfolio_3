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

    public ArrayList<String> SQLQuerryStudentNames() {
        ArrayList<String> Names = new ArrayList<String>();
        String sql ="SELECT FirstName FROM Student;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                Names.add(name);
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        rs = null;
        return Names;
    }

    public ArrayList<String> SQLQuerryCourseNames() {
        ArrayList<String> Names = new ArrayList<String>();
        String sql ="SELECT CourseID FROM Course;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                Names.add(name);
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        rs = null;
        return Names;
    }

    public void StudentInfoQuerry() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write the name of the student, you wish to get information on:");
        String student = scanner.nextLine();
        String sql = " SELECT FirstName, LastName FROM Student WHERE FirstName='"+ student + "';";
        try {
            rs = stmt.executeQuery(sql);
            if (rs == null)
                System.out.println("Student does not exist in the database");
            while (rs != null && rs.next())
            {
                String fName = rs.getString(1);
                String lName = rs.getString(2);
                System.out.println("First name: " + fName + ", " + "Last name: " + lName);
            }
            rs = null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void PreparedStatement() {
        String sql = "SELECT Course.CourseID, AVG(Teacher.Grade) AS avgClassGrade " +
                "FROM Course " +
                "LEFT JOIN Teacher ON Course.CourseID = ? " +
                "GROUP BY Course.CourseID;";
                try {
                    pstmt = conn.prepareStatement(sql);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
    }

    public void FindGrade(String CourseID) {
        try {
            pstmt.setString(1, CourseID);
            rs = pstmt.executeQuery();
            if (rs == null)
                System.out.println("No records fetched");
            while (rs != null && rs.next()) {
                System.out.println("From Course: " + rs.getString(1) + " average grade is " + rs.getDouble(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void PrintNames(ArrayList<String> Names) {
        for (int i = 0; i < Names.size(); i++) {
            System.out.println(Names.get(i));
        }
    }
}