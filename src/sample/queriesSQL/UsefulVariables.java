package sample.queriesSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UsefulVariables {

    public final static String URL = "jdbc:mysql://127.0.0.1:3306/collegeManagementSystem?autoReconnect=true&useSSL=false";
    public final static String DB_USER = "root";
    public final static String DB_PASSWORD = "M@rcus2020";

    public PreparedStatement createBranchQuery;
    public PreparedStatement deleteBranchQuery;
    public PreparedStatement createStudentQuery;
    public PreparedStatement deleteStudentQuery;
    public PreparedStatement createLecturerQuery;
    public PreparedStatement deleteLecturerQuery;
    public PreparedStatement createAdminQuery;
    public PreparedStatement createModuleQuery;
    public PreparedStatement deleteModuleQuery;
    public PreparedStatement createCourseQuery;
    public PreparedStatement deleteCourseQuery;
    public PreparedStatement addStudentModuleQuery;
    public PreparedStatement createAssignmentQuery;



    public PreparedStatement getBranches;

    public PreparedStatement getCourses;

    public PreparedStatement getLecturersId;

    public PreparedStatement getModules;

    public PreparedStatement createModuleGetCourses;

    public PreparedStatement getAllStudents;

    public PreparedStatement getAllLecturers;

    public PreparedStatement getAllCourses;

    public PreparedStatement getAllBranches;

    public PreparedStatement getAllModules;

    public PreparedStatement getAllCourseYear;

    public PreparedStatement getPayments;

    public PreparedStatement addPaymentQuery;









}
