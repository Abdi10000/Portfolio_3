package com.company;

import javafx.collections.ObservableList;

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
    // Method for establishing connection with the url
    public void connect() throws SQLException {
        conn = getConnection(url);
    }

    // Method for closing the connection
    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }
    // This method is required for creating connection with the database,
    // queries, sql code and the program
    public void CreateStatement() throws SQLException {
        this.stmt = conn.createStatement();
    }

    // Query statement for retrieving student data and set them into the list
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

    // Query statement for retrieving course data and set them into the list
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

    // The prepared statement allows the user to retrieve/display data when using
    // student ID and course ID as input
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

        // Method for executing the query and process the resultSet object for displaying both student and course
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
                   studentdatas.add(new StudentData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), (Integer) rs.getObject(5)));
                   System.out.println("Student ID: " + rs.getString(1));
                   System.out.println("The student name: " + rs.getString(2));
                   System.out.println("The Course name: " + rs.getString(3));
                   System.out.println("The Course ID: " + rs.getString(4));
                   System.out.println("The students grade: " + rs.getObject(5));
               }
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
           return studentdatas;
   }
    // This prepared statement allows the user to retrieve/display the entire/whole class
    // of the student when using course ID as input
    public void SQLCourseInfo() {
        String sql = "SELECT Student.StudentID, Grade.Grade, Student.StudentName, Course.CourseID, Course.CourseName, Course.Semester, Course.Year, Course.Teacher " +
                "FROM Student LEFT JOIN Grade ON Student.StudentID= Grade.SID " +
                "LEFT JOIN Course ON Grade.CID = Course.CourseID " +
                "WHERE Course.CourseID = ?;";
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Method for executing the query and process the resultSet object for displaying the entire course information
    public ArrayList<CourseData> FindCourseData (String course) {
        ArrayList<CourseData> courseDatas  = new ArrayList<>();
        try {
            pstmt.setString(1, course);
            rs = pstmt.executeQuery();
            if (rs == null) {
                System.out.println("Course may not exist or the data may be gone");
            }
            while (rs != null && rs.next()) {
                courseDatas.add(new CourseData(rs.getString(1), (Integer) rs.getObject(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8)));
                System.out.println("The student named " + rs.getString(3) + " with the student ID: " + rs.getString(1) +
                        " got the grade " + rs.getObject(2) + ",\n" + "from the course " + rs.getString(5) + ".\n" +
                        "The course ID is " + rs.getString(4) + " and the semester was in " + rs.getString(6) + "\n" +
                        "and the year " + rs.getInt(7) + ". The teacher of the course is " + rs.getString(8) + ".\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courseDatas;
    }
    // This prepared statement allows the user to retrieve/display
    // data of the student when using student ID as input
    public void SQLStudentResults() {
            String sql = "SELECT G1.SID, S1.StudentName, C1.CourseName, G1.CID, G1.Grade " +
                    "FROM Course AS C1 JOIN Grade AS G1 ON C1.CourseID = G1.CID " +
                    "LEFT JOIN Student AS S1 ON G1.SID = S1.StudentID " +
                    "WHERE G1.SID = ?;";
            try {
                pstmt = conn.prepareStatement(sql);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        // Method for executing the query and process the resultSet object for displaying
        // courses the student attend and grades
        public ArrayList<GradeStudent> studentResulties(String student) {
            ArrayList<GradeStudent> studgrad = new ArrayList<>();
            try {
                pstmt.setString(1, student);
                rs = pstmt.executeQuery();
                if (rs == null) {
                    System.out.println("Student results may not exist or the data may be gone");
                }
                while (rs != null && rs.next()) {
                    studgrad.add(new GradeStudent(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
                    System.out.println("Student name: " + rs.getString(2) + " with the student ID: " + rs.getString(1) + " from the course" + "\n" +
                            rs.getString(3) + " has grade: " + rs.getString(5) + ". " + "You can find the info at course ID: " + rs.getString(4) + "\n");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return studgrad;
        }
        // This prepared statement allows the user to retrieve/display the
        // average grade of a student when using student ID as input
        public void averageStudentGrade() {
        String sql = "SELECT S1.StudentName, avg(G1.Grade) " +
                "FROM Student AS S1 JOIN Grade AS G1 ON G1.SID = S1.StudentID " +
                "WHERE G1.SID = ?;";
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // The average grade for selected student
    class averageStudent{
        String studentName;
        Integer gradeAverage;

        public averageStudent(String studentName, Integer gradeAverage) {
            this.studentName = studentName;
            this.gradeAverage = gradeAverage;
        }
    }
    // Method for executing the query and process the resultSet object for displaying
    // the average grade for selected student
    public ArrayList<averageStudent> studentAverage(String student) {
        ArrayList<averageStudent> studAVG = new ArrayList<>();
        try {
            pstmt.setString(1, student);
            rs = pstmt.executeQuery();
            if (rs == null) {
                System.out.println("The average grade is either lost or not inserted into the database");
            }
            while (rs != null && rs.next()) {
                studAVG.add(new averageStudent(rs.getString(1), rs.getInt(2)));
                System.out.println("The student " + rs.getString(1) + " got the average grade: " + rs.getInt(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return studAVG;
    }
    // This prepared statement allows the user to retrieve/display the
    // average grade of a course when using course ID as input
    public void averageCourseGrade() {
        String sql = "SELECT C1.CourseName, avg(G1.Grade) " +
                "FROM Course AS C1 JOIN Grade AS G1 ON C1.CourseID = G1.CID " +
                "WHERE G1.CID = ?;";
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // The average grade for selected course
    class averageCourse{
        String courseName;
        Integer courseAverage;

        public averageCourse(String courseName, Integer courseAverage) {
            this.courseName = courseName;
            this.courseAverage = courseAverage;
        }
    }
    // Method for executing the query and process the resultSet object for displaying
    // the average grade for selected course
    public ArrayList<averageCourse> coursesAverage(String course) {
        ArrayList<averageCourse> courseAVG = new ArrayList<>();
        try {
            pstmt.setString(1, course);
            rs = pstmt.executeQuery();
            if (rs == null) {
                System.out.println("The average grade is either lost or not inserted into the database");
            }
            while (rs != null && rs.next()) {
                courseAVG.add(new averageCourse(rs.getString(1), rs.getInt(2)));
                System.out.println("The course " + rs.getString(1) + " got the average grade: " + rs.getInt(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courseAVG;
    }

    // Prepared statement method for inserting grades for students with null value
    public void insertStudentGrade() {
        String sql = "UPDATE Grade " +
                "SET Grade = ? WHERE SID = ? AND CID = ? AND Grade IS NULL;";
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
        // Method for executing the query and process the resultSet object for displaying
        // the grade given to the student
        public ArrayList<StudentData> insertingGrade(Integer grade, String student, String course) {
        ArrayList<StudentData> gradeInsert = new ArrayList<>();
        try {
            pstmt.setInt(1, grade);
            pstmt.setString(2, student);
            pstmt.setString(3, course);
            pstmt.executeUpdate();
            rs = pstmt.executeQuery();
            if (rs == null) {
                System.out.println("You may not be able to insert grade at the moment. Wait for technical support");
            }
            while (rs != null && rs.next()) {
                gradeInsert.add(new StudentData(rs.getInt(1), rs.getString(2), rs.getString(3)));
                System.out.println("The grade inserted : " + rs.getInt(1) + " for the student " + rs.getString(2) + " in the course " + rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Does not work");
        }
        return gradeInsert;
    }
}