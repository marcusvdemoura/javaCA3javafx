package sample.entities;

import sample.queriesSQL.UsefulVariables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Lecturer extends Person implements LecturerTools {

    private UsefulVariables usefulVariables = new UsefulVariables();
    private Connection con = DriverManager.getConnection(usefulVariables.URL, usefulVariables.DB_USER, usefulVariables.DB_PASSWORD);

    public static Lecturer lecturer;



    public Lecturer(String first_name, String last_name, String gender, String phone, String dob, String emailAddress,
                    String lecturerId, String password) throws Exception {
        super(first_name, last_name, gender, phone, dob, emailAddress, lecturerId, password);

    }


    @Override
    public void setGrade(Double grade, Student s) throws Exception {


    }

    @Override
    public void createAssignment(String dueDate, String description, String moduleSubject, String lecturerId) throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO assignment (dueDate, description, lecturerId, moduleSubject) VALUES (?,?,?,?)";

        usefulVariables.createAssignmentQuery = con.prepareStatement(sql);

        usefulVariables.createAssignmentQuery.setString(1, dueDate);
        usefulVariables.createAssignmentQuery.setString(2, description);
        usefulVariables.createAssignmentQuery.setString(3, lecturerId);
        usefulVariables.createAssignmentQuery.setString(4, moduleSubject);


        usefulVariables.createAssignmentQuery.execute();


    }

    @Override
    public void deleteAssignment(Assignment assignment) {


    }

    @Override
    public void editAssignment(Assignment assignment, String dueDate, String description) {


    }

    @Override
    public void getStudentGrade(Student student) {

    }



    @Override
    public void createExam(String date) {


    }

    @Override
    public void editExam(Exam e, String date) {


    }

}
