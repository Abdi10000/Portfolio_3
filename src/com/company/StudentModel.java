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

    // List of students
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

    // List of courses
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


    // This functions uses student and course as input for retrieving data
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


    // Student class
   class StudentData {
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

    // Method for retrieving the whole class of the student information
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


    // Course Class
    class CourseData {
        String SID;
        Integer Grade;
        String StudentName;
        String CID;
        String CourseName;
        String Semester;
        int Year;
        String Teacher;

        public CourseData(String SID, Integer Grade, String StudentName, String CID, String CourseName, String Semester, int Year, String Teacher) {
            this.SID = SID;
            this.Grade = Grade;
            this.StudentName = StudentName;
            this.CID = CID;
            this.CourseName = CourseName;
            this.Semester = Semester;
            this.Year = Year;
            this.Teacher = Teacher;
        }
    }


    public ArrayList<CourseData> FindCourseData (String course) {
        ArrayList<CourseData> courseDatas  = new ArrayList<>();
        try {
            pstmt.setString(1, course);
            rs = pstmt.executeQuery();
            if (rs == null) {
                System.out.println("Course may not exist or the data may be gone");
            }
            while (rs != null && rs.next()) {
                courseDatas.add(new CourseData(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8)));
                System.out.println("The student named " + rs.getString(3) + " with the student ID: " + rs.getString(1) +
                        " got the grade " + rs.getInt(2) + ",\n" + "from the course " + rs.getString(5) + ".\n" +
                        "The course ID is " + rs.getString(4) + " and the semester was in " + rs.getString(6) + "\n" +
                        "and the year " + rs.getInt(7) + ". The teacher of the course is " + rs.getString(8) + ".\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courseDatas;
    }


    // Class for showing both of the students classes and grades
    class GradeStudent {
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
    }

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



        // Average grade of a student
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

    // the average student class
    class averageStudent{
        String studentName;
        Integer gradeAverage;

        public averageStudent(String studentName, Integer gradeAverage) {
            this.studentName = studentName;
            this.gradeAverage = gradeAverage;
        }
    }

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



    // Average grade of a course
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

    class averageCourse{
        String courseName;
        Integer courseAverage;

        public averageCourse(String courseName, Integer courseAverage) {
            this.courseName = courseName;
            this.courseAverage = courseAverage;
        }
    }

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



    // Prepared statement for inserting grades with null value
    public void insertStudentGrade(Integer grade, String SID, String CID) {
        String sql = "UPDATE Grade " +
                "SET Grade = ? WHERE SID = ? AND CID = ? AND Grade IS NULL;";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, grade);
            pstmt.setString(2, SID);
            pstmt.setString(3, CID);
            int numberOfRowsUpdated = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Inserting grade class
    class insertGrade {
        Integer grade;
        String studentID;
        String courseID;

        public insertGrade(Integer grade, String studentID, String courseID) {
            this.grade = grade;
            this.studentID = studentID;
            this.courseID = courseID;
        }
    }


        public ArrayList<insertGrade> insertingGrade(Integer grade, String student, String course) {
        ArrayList<insertGrade> gradeInsert = new ArrayList<>();
        try {
            pstmt.setInt(1, grade);
            pstmt.setString(2, student);
            pstmt.setString(3, course);
            rs = pstmt.executeQuery();
            if (rs == null) {
                System.out.println("You may not be able to insert grade at the moment. Wait for technical support");
            }
            while (rs != null && rs.next()) {
                gradeInsert.add(new insertGrade(rs.getInt(1), rs.getString(2), rs.getString(3)));
                System.out.println("The grade inserted : " + rs.getInt(1) + " for the student " + rs.getString(2) + " in the course " + rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Does not work");
        }
        return gradeInsert;
    }

    // method for showing course with null grades
    public ObservableList<Course> QueryStatement_NullCourses(ObservableList<Course> NullCourses) throws SQLException {
        System.out.println("\nFetching Null Courses...");
        String sql = "SELECT DISTINCT C1.CourseID, C1.CourseName, C1.Teacher, C1.Semester, C1.Year" +
                "FROM Course AS C1" +
                "JOIN Grade AS G1 ON C1.CourseID = G1.CID" +
                "WHERE Grade IS NULL;";
        ResultSet ReSet_NullCourses = stmt.executeQuery(sql);
        if (ReSet_NullCourses == null){
            System.out.println("No record found");
        }
        while (ReSet_NullCourses != null && ReSet_NullCourses.next()){
            String CourseName = ReSet_NullCourses.getString(2);
            String CourseID = ReSet_NullCourses.getString(1);
            String Teacher = ReSet_NullCourses.getString(3);
            String Semester = ReSet_NullCourses.getString(4);
            Integer Year = ReSet_NullCourses.getInt(5);

            Course course = new Course(CourseName, CourseID, Teacher, Semester,Year);
            NullCourses.add(course);
        }
        return NullCourses;
    }

    // method for showing students with null grade
    public ObservableList<Student> QueryStatement_NullStudents(ObservableList<Student> NullStudents) throws Exception {
        System.out.println("\nFetching Null Students...");
        String sql = "SELECT S1.StudentID, S1.StudentName, S1.City, G1.Grade" +
                "FROM Student S1" +
                "JOIN Grade AS G1 ON S1.StudentID = G1.SID" +
                "WHERE Grade IS NULL;";
        ResultSet ReSet_NullStudents = stmt.executeQuery(sql);
        if (ReSet_NullStudents == null) {
            System.out.println("No Record retrieved");
        }
        while (ReSet_NullStudents != null && ReSet_NullStudents.next()) {
            String StudentID = ReSet_NullStudents.getString(1);
            String StudentName = ReSet_NullStudents.getString(2);
            System.out.println(StudentID + " " + StudentName);
            Student student = new Student(StudentID, StudentName);
            NullStudents.add(student);
        }
        return NullStudents;
    }
}