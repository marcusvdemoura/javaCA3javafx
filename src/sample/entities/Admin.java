package sample.entities;

import java.sql.*;
import java.util.ArrayList;

public class Admin extends Person {

    private Student student;
    private Lecturer lecturer;
    private CollegeBranch collegeBranch;
    private Module module;
    private Course course;
    private String courseYear;
    private ArrayList<CollegeBranch> listOfBranches = new ArrayList<>();


    final static String URL = "jdbc:mysql://127.0.0.1:3306/collegeManagementSystem?autoReconnect=true&useSSL=false";
    final static String DB_USER = "root";
    final static String DB_PASSWORD = "M@rcus2020";

    private PreparedStatement createBranchQuery;
    private PreparedStatement deleteBranchQuery;
    private PreparedStatement editBranchQuery;
    private PreparedStatement createStudentQuery;
    private PreparedStatement deleteStudentQuery;
    private PreparedStatement editStudentQuery;
    private PreparedStatement createLecturerQuery;
    private PreparedStatement deleteLecturerQuery;
    private PreparedStatement editLecturerQuery;
    private PreparedStatement createAdminQuery;
    private PreparedStatement createModuleQuery;
    private PreparedStatement deleteModuleQuery;
    private PreparedStatement editModuleQuery;
    private PreparedStatement createCourseQuery;
    private PreparedStatement deleteCourseQuery;
    private PreparedStatement editCourseQuery;
    private PreparedStatement addStudentModuleQuery;
    private PreparedStatement createCourseYear;
    private PreparedStatement addPaymentQuery;


    private Connection con = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);


    public Admin(String first_name, String last_name, String gender, String phone, String dob, String emailAddress, String adminId, String password) throws SQLException {
        super(first_name, last_name, gender, phone, dob, emailAddress, adminId, password);
    }

    public Admin(String first_name, String last_name) throws SQLException {
        super(first_name, last_name);
    }


    // CREATING BRANCHES, LECTURERS AND STUDENTS AND NEW ADMIN

    public void createAdmin(String first_name, String last_name, String gender, String phone, String dob,
                            String emailAddress, String adminId, String password) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO admin(firstName, lastName, gender, phoneNumber, dob, emailAddress, adminID," +
                " password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        createAdminQuery = con.prepareStatement(sql);

        createAdminQuery.setString(1, first_name);
        createAdminQuery.setString(2, last_name);
        createAdminQuery.setString(3, gender);
        createAdminQuery.setString(4, phone);
        createAdminQuery.setString(5, dob);
        createAdminQuery.setString(6, emailAddress);
        createAdminQuery.setString(7, adminId);
        createAdminQuery.setString(8, password);


        createAdminQuery.execute();

    }

    public void createBranch(String unit, String address) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO collegeBranches(unit, address) VALUES (?, ?)";
        createBranchQuery = con.prepareStatement(sql);

        createBranchQuery.setString(1, unit);
        createBranchQuery.setString(2, address);

        createBranchQuery.execute();


    }

    public void createLecturer(String first_name, String last_name, String gender, String phone, String dob, String emailAddress,
                               String lecturerId, String password) throws Exception {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO lecturer(firstName, lastName, gender, phone, dob, emailAddress, idlecturer," +
                " password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        createLecturerQuery = con.prepareStatement(sql);

        createLecturerQuery.setString(1, first_name);
        createLecturerQuery.setString(2, last_name);
        createLecturerQuery.setString(3, gender);
        createLecturerQuery.setString(4, phone);
        createLecturerQuery.setString(5, dob);
        createLecturerQuery.setString(6, emailAddress);
        createLecturerQuery.setString(7, lecturerId);
        createLecturerQuery.setString(8, password);


        createLecturerQuery.execute();
    }


    public void createStudents(String first_name, String last_name, String gender, String phone, String dob,
                               String emailAddress, String studentId, String password, String collegeBranchName, String courseName, int courseYear,
                               boolean isPaidFull) throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO student(firstName, lastName, gender, phone, dob, emailAddress, idstudent," +
                " password, collegeBranch, course, isPaidFull, courseYear) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        createStudentQuery = con.prepareStatement(sql);

        createStudentQuery.setString(1, first_name);
        createStudentQuery.setString(2, last_name);
        createStudentQuery.setString(3, gender);
        createStudentQuery.setString(4, phone);
        createStudentQuery.setString(5, dob);
        createStudentQuery.setString(6, emailAddress);
        createStudentQuery.setString(7, studentId);
        createStudentQuery.setString(8, password);
        createStudentQuery.setString(9, collegeBranchName);
        createStudentQuery.setString(10, courseName);
        createStudentQuery.setBoolean(11, isPaidFull);
        createStudentQuery.setInt(12, courseYear);


        createStudentQuery.execute();

    }


    // DELETING COURSES LECTURERS AND STUDENTS

    public void deleteStudent(String studentID) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "DELETE from student WHERE idstudent = '" + studentID + "'";

        deleteStudentQuery = con.prepareStatement(sql);


        deleteStudentQuery.execute();


    }

    public void deleteCourse(String collegeBranchUnit, String courseName) throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "DELETE from course WHERE collegeBranchUnit = '" + collegeBranchUnit + "' AND name = '" + courseName + "'";

        deleteCourseQuery = con.prepareStatement(sql);


        deleteCourseQuery.execute();


    }

    public void deleteModule(String subject) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "DELETE from module WHERE subject = '" + subject + "'";

        deleteModuleQuery = con.prepareStatement(sql);


        deleteModuleQuery.execute();

    }

    public void deleteLecturer(String lecturerId) throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.jdbc.Driver");

        // this is givin an error

        String sql = "DELETE from lecturer WHERE idlecturer = '" + lecturerId + "'";

        deleteLecturerQuery = con.prepareStatement(sql);


        deleteLecturerQuery.execute();


    }

    // DELETING BRANCHES

    public void deleteBranch(String branchUnit) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "DELETE FROM collegeBranches WHERE unit = ?";
        deleteBranchQuery = con.prepareStatement(sql);

        deleteBranchQuery.setString(1, branchUnit);

        deleteBranchQuery.execute();


    }

    // CREATING MODULES AND COURSES - ADDING STUDENTS TO MODULES
    public void createModule(String moduleName, String courseName, String collegeBranchUnit, String weekDay, String classHour, String lecturerId) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO module(subject, course, collegeBranch, weekDay, classHour, idlecturer) VALUES (?, ?, ?, ?, ?, ?)";

        createModuleQuery = con.prepareStatement(sql);

        createModuleQuery.setString(1, moduleName);
        createModuleQuery.setString(2, courseName);
        createModuleQuery.setString(3, collegeBranchUnit);
        createModuleQuery.setString(4, weekDay);
        createModuleQuery.setString(5, classHour);
        createModuleQuery.setString(6, lecturerId);

        createModuleQuery.execute();


    }

    public void createCourse(String collegeBranchUnit, String name, Double price) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO course(collegeBranchUnit, name, price) VALUES (?, ?, ?)";

        createCourseQuery = con.prepareStatement(sql);

        createCourseQuery.setString(1, collegeBranchUnit);
        createCourseQuery.setString(2, name);
        createCourseQuery.setDouble(3, price);

        createCourseQuery.execute();


    }

    public void addStudentToModule(String studentId, String moduleSubject) throws Exception {


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO studentInModule(idStudent, moduleName) VALUES (?, ?)";

        addStudentModuleQuery = con.prepareStatement(sql);

        addStudentModuleQuery.setString(1, studentId);
        addStudentModuleQuery.setString(2, moduleSubject);


        addStudentModuleQuery.execute();


    }


    // to create course year

    public void createCourseYear(String year, String courseName, String moduleName) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String sql = "INSERT INTO courseYear(year, course, moduleName) VALUES (?, ?, ?)";
        createCourseYear = con.prepareStatement(sql);

        createCourseYear.setString(1, year);
        createCourseYear.setString(2, courseName);
        createCourseYear.setString(3, moduleName);

        createCourseYear.execute();

    }


    // GETTING AND PRINTING LIST OF BRANCHES

    public ArrayList<CollegeBranch> getListOfBranches() {
        return listOfBranches;
    }

    public void printListOfBranches() {
        for (CollegeBranch cb : getListOfBranches()) {
            System.out.println("Branch " + cb.getUnit() + "\nLocated at: " + cb.getAddress());
        }
    }


    // DELETING STUDENTS, LECTURERES AND BRANCHES

    public void deleteStudent(String studentID, CollegeBranch cb) {

        int counting = 0;

        for (int i = 0; i < collegeBranch.getListOfStudents().size(); i++) {
            counting++;
            if (collegeBranch.getListOfStudents().get(i).getId().equalsIgnoreCase(studentID)) {
                collegeBranch.getListOfStudents().remove(i);
                System.out.println("Student removed");
                break;
            }
        }

        if (counting == collegeBranch.getListOfStudents().size()) {
            System.out.println("This student does not exist in this branch!!!");
        }

    }


    // Add payment to student
    public boolean addPayment(String idStudent, String paymentDate) throws SQLException, ClassNotFoundException {

        int count = 0;
        int numberOfInstallments = 0;


        Class.forName("com.mysql.jdbc.Driver");


        String sql = "SELECT numberOfInstallments FROM student WHERE idstudent = ?";
        addPaymentQuery = con.prepareStatement(sql);
        addPaymentQuery.setString(1, idStudent);
        ResultSet numberInstallmentsResult = addPaymentQuery.executeQuery();

        while(numberInstallmentsResult.next()) {
            numberOfInstallments = Integer.parseInt(numberInstallmentsResult.getString("numberOfInstallments"));
        }



        String sql1 = "SELECT * FROM payments WHERE idstudent = ?";
        addPaymentQuery = con.prepareStatement(sql1);
        addPaymentQuery.setString(1, idStudent);

        ResultSet result = addPaymentQuery.executeQuery();

        while(result.next()){
            count++;
        }

        System.out.println(numberOfInstallments+" = number of installments");
        System.out.println(count + " = count");


        if(numberOfInstallments - count > 0){
            String sql2 = "INSERT into payments(idstudent, paymentDate) VALUES (?,?)";
            addPaymentQuery = con.prepareStatement(sql2);

            addPaymentQuery.setString(1, idStudent);
            addPaymentQuery.setString(2, paymentDate);
            addPaymentQuery.execute();



            return true;
        } else {
            String sql2 = "UPDATE student SET isPaidFull = ? WHERE idstudent = ?";
            addPaymentQuery = con.prepareStatement(sql2);
            addPaymentQuery.setBoolean(1, true);
            addPaymentQuery.setString(2, idStudent);

            addPaymentQuery.execute();

            return false;
        }




    }


    // Set fees installments

    public void setInstallments(Integer installments, String studentId) throws ClassNotFoundException, SQLException {

        PreparedStatement setInstall;

        Class.forName("com.mysql.jdbc.Driver");
        String sql = "UPDATE student SET numberOfInstallments = ? WHERE idstudent = ?";
        setInstall = con.prepareStatement(sql);

        setInstall.setInt(1, installments);
        setInstall.setString(2, studentId);


        setInstall.execute();

    }


    // EDITING STUDENTS, LECTURERS

    public void editingStudent(String first_name, String last_name, String gender, String phone, String dob,
                               String emailAddress, String studentId, String password, String collegeBranchName, String courseName, int courseYear,
                               boolean isPaidFull, String col_idValue) throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "UPDATE student SET firstName = ?, lastName = ?, gender = ?, phone = ?, dob = ?, emailAddress = ?, idstudent = ?," +
                "password = ?, collegeBranch = ?, course = ?, isPaidFull = ?, courseYear = ? WHERE idstudent=?";


        editStudentQuery = con.prepareStatement(sql);

        editStudentQuery.setString(1, first_name);
        editStudentQuery.setString(2, last_name);
        editStudentQuery.setString(3, gender);
        editStudentQuery.setString(4, phone);
        editStudentQuery.setString(5, dob);
        editStudentQuery.setString(6, emailAddress);
        editStudentQuery.setString(7, studentId);
        editStudentQuery.setString(8, password);
        editStudentQuery.setString(9, collegeBranchName);
        editStudentQuery.setString(10, courseName);
        editStudentQuery.setBoolean(11, isPaidFull);
        editStudentQuery.setInt(12, courseYear);
        editStudentQuery.setString(13, col_idValue);


        editStudentQuery.execute();
    }

    public void editingLecturer(String first_name, String last_name, String gender, String phone, String dob, String emailAddress,
                                String lecturerId, String password, String col_idValue) throws Exception {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "UPDATE lecturer SET firstName = ?, lastName = ?, gender = ?, phone = ?, dob = ?, emailAddress = ?, idlecturer = ?," +
                "password = ? WHERE idlecturer=?";

        editLecturerQuery = con.prepareStatement(sql);

        editLecturerQuery.setString(1, first_name);
        editLecturerQuery.setString(2, last_name);
        editLecturerQuery.setString(3, gender);
        editLecturerQuery.setString(4, phone);
        editLecturerQuery.setString(5, dob);
        editLecturerQuery.setString(6, emailAddress);
        editLecturerQuery.setString(7, lecturerId);
        editLecturerQuery.setString(8, password);
        editLecturerQuery.setString(9, col_idValue);


        editLecturerQuery.execute();

    }

    public void editingBranch(String unit, String address, String col_unit) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "UPDATE collegeBranches SET unit = ?, address = ? WHERE unit=?";
        editBranchQuery = con.prepareStatement(sql);

        editBranchQuery.setString(1, unit);
        editBranchQuery.setString(2, address);
        editBranchQuery.setString(3, col_unit);

        editBranchQuery.execute();

    }
    public void editingCourse(String collegeBranchUnit, String name, Double price, String col_courseName) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "UPDATE course SET collegeBranchUnit = ?, name = ?, price = ? WHERE name=?";

        editCourseQuery = con.prepareStatement(sql);

        editCourseQuery.setString(1, collegeBranchUnit);
        editCourseQuery.setString(2, name);
        editCourseQuery.setDouble(3, price);
        editCourseQuery.setString(4, col_courseName);

        editCourseQuery.execute();

    }

    public void editingModule(String moduleName, String courseName, String collegeBranchUnit, String weekDay,
                              String classHour, String lecturerId, String col_moduleSubject) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        String sql = "UPDATE module SET subject = ?, course = ?, collegeBranch = ?, weekday = ?, classHour = ?, idlecturer = ? WHERE subject=?";

        editModuleQuery = con.prepareStatement(sql);

        editModuleQuery.setString(1, moduleName);
        editModuleQuery.setString(2, courseName);
        editModuleQuery.setString(3, collegeBranchUnit);
        editModuleQuery.setString(4, weekDay);
        editModuleQuery.setString(5, classHour);
        editModuleQuery.setString(6, lecturerId);
        editModuleQuery.setString(7, col_moduleSubject);

        editModuleQuery.execute();


    }


        public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }






}
