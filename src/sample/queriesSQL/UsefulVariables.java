package sample.queriesSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UsefulVariables {

    public final static String URL = "jdbc:mysql://127.0.0.1:3306/collegeManagementSystem?autoReconnect=true&useSSL=false";
    public final static String DB_USER = "root";
    public final static String DB_PASSWORD = "M@rcus2020";

    public static PreparedStatement createBranchQuery;

    public static PreparedStatement createAssignmentQuery;



    public static PreparedStatement getBranches;

    public static PreparedStatement getCourses;

    public static PreparedStatement getLecturersId;

    public static PreparedStatement getModules;

    public static PreparedStatement createModuleGetCourses;

    public static PreparedStatement getAllStudents;

    public static PreparedStatement getAllLecturers;

    public static PreparedStatement getAllCourses;

    public static PreparedStatement getAllBranches;

    public static PreparedStatement getAllModules;

    public static PreparedStatement getAllCourseYear;

    public static PreparedStatement getPayments;

    public static PreparedStatement addPaymentQuery;


    public static PreparedStatement getAllAssignments;

    public static PreparedStatement getSelectedAssignment;

    public static PreparedStatement editAssignment;

    public static PreparedStatement deleteAssignment;

    public static PreparedStatement addGrade;












}
