package sample.entities;

import sample.queriesSQL.UsefulVariables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Lecturer extends Person{

//    private UsefulVariables usefulVariables = new UsefulVariables();
    private static Connection con;

    static {
        try {
            con = DriverManager.getConnection(UsefulVariables.URL, UsefulVariables.DB_USER, UsefulVariables.DB_PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Lecturer lecturer;



    public Lecturer(String first_name, String last_name, String gender, String phone, String dob, String emailAddress,
                    String lecturerId, String password) throws Exception {
        super(first_name, last_name, gender, phone, dob, emailAddress, lecturerId, password);

    }


    public static void setGrade(String studentid, String modulename, Double grade, String lecturerid) throws Exception {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO gradesModule (studentid, modulename, grade, lecturerid) VALUES (?,?,?,?)";

        UsefulVariables.addGrade = con.prepareStatement(sql);

        UsefulVariables.addGrade.setString(1, studentid);
        UsefulVariables.addGrade.setString(2, modulename);
        UsefulVariables.addGrade.setDouble(3, grade);
        UsefulVariables.addGrade.setString(4, lecturerid);



        UsefulVariables.addGrade.execute();


    }

    public static void editGrade(Integer id, Double grade, String module) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        String sql = "UPDATE gradesModule SET grade = ?, modulename = ? WHERE gradeid = ?";

        UsefulVariables.editAssignment = con.prepareStatement(sql);

        UsefulVariables.editAssignment.setDouble(1, grade);
        UsefulVariables.editAssignment.setString(2, module);
        UsefulVariables.editAssignment.setInt(3, id);



        UsefulVariables.editAssignment.execute();
    }



    public static void createAssignment(String dueDate, String description, String moduleSubject, String lecturerId) throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO assignment (dueDate, description, lecturerId, moduleSubject) VALUES (?,?,?,?)";

        UsefulVariables.createAssignmentQuery = con.prepareStatement(sql);

        UsefulVariables.createAssignmentQuery.setString(1, dueDate);
        UsefulVariables.createAssignmentQuery.setString(2, description);
        UsefulVariables.createAssignmentQuery.setString(3, lecturerId);
        UsefulVariables.createAssignmentQuery.setString(4, moduleSubject);


        UsefulVariables.createAssignmentQuery.execute();


    }

    public static void deleteAssignment(Integer id) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "DELETE from assignment WHERE idassignment = '" + id + "'";

        UsefulVariables.deleteAssignment = con.prepareStatement(sql);


        UsefulVariables.deleteAssignment.execute();


    }

    public static void editAssignment(Integer id, String dueDate, String description, String moduleSubject) throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "UPDATE assignment SET dueDate = ?, description = ?, moduleSubject = ? WHERE idassignment = ?";

        UsefulVariables.editAssignment = con.prepareStatement(sql);

        UsefulVariables.editAssignment.setString(1, dueDate);
        UsefulVariables.editAssignment.setString(2, description);
        UsefulVariables.editAssignment.setString(3, moduleSubject);
        UsefulVariables.editAssignment.setInt(4, id);


        UsefulVariables.editAssignment.execute();

    }



    public static void createExam(String date, String module, String lecturerid) throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO exam (date, lecturerId, module) VALUES (?,?,?)";

        UsefulVariables.createExam = con.prepareStatement(sql);

        UsefulVariables.createExam.setString(1, date);
        UsefulVariables.createExam.setString(2, lecturerid);
        UsefulVariables.createExam.setString(3, module);



        UsefulVariables.createExam.execute();


    }

    public static void editExam(String date, String module, Integer id) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "UPDATE exam SET date = ?, module = ? WHERE idexam = ?";

        UsefulVariables.editExam = con.prepareStatement(sql);

        UsefulVariables.editExam.setString(1, date);
        UsefulVariables.editExam.setString(2, module);
        UsefulVariables.editExam.setInt(4, id);


        UsefulVariables.editExam.execute();

    }

    public static void deleteExam(Integer id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        String sql = "DELETE from exam WHERE idexam = '" + id + "'";

        UsefulVariables.deleteExam = con.prepareStatement(sql);


        UsefulVariables.deleteExam.execute();
    }

}
