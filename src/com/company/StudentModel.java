package com.company;

import java.sql.*;
import java.util.ArrayList;
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


    public void PreparedStmtPrintStudentInfo() {
        String sql = "SELECT G1.SID, S1.StudentName, C1.CourseName, G1.CID, G1.Grade " +
                "FROM Course AS C1 JOIN Grade AS G1 ON C1.CourseID = G1.CID " +
                "LEFT JOIN Student AS S1 ON G1.SID = S1.StudentID " +
                "WHERE G1.SID = ? AND G1.CID = ?;";
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

   class StudentData {
       String SID;
       String StudentName;
       String CourseName;
       String CID;
       int Grade;

       public StudentData(String SID, String StudentName, String CourseName, String CID, int Grade) {
           this.SID = SID;
           this.StudentName = StudentName;
           this.CourseName = CourseName;
           this.CID = CID;
           this.Grade = Grade;
       }
   }

       public ArrayList<StudentData> FindStudentData(String students, String courses) {
           ArrayList<StudentData> studentdatas = new ArrayList<>();
           try {
               pstmt.setString(1, students);
               pstmt.setString(2, courses);
               rs = pstmt.executeQuery();
               if (rs == null) {
                   System.out.println("No data found, student does not exist");
               }
               while (rs != null && rs.next()) {
                   studentdatas.add(new StudentData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
                   System.out.println("Student ID: " + rs.getString(1));
                   System.out.println("The student name: " + rs.getString(2));
                   System.out.println("The Course name: " + rs.getString(3));
                   System.out.println("The Course ID: " + rs.getString(4));
                   System.out.println("The students grade: " + rs.getInt(5));
               }
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
           return studentdatas;
   }
}