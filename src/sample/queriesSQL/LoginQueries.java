package sample.queriesSQL;

import java.sql.*;

public class LoginQueries {
    final String URL = "jdbc:mysql://127.0.0.1:3306/collegeManagementSystem?autoReconnect=true&useSSL=false";
    final String DB_USER = "root";
    final String DB_PASSWORD = "M@rcus2020";


    private PreparedStatement loginQuery;

    public ResultSet loginAdmin(String id, String password) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");

        Connection con = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);


        String sql = "Select * from admin where adminID = ? and password = ?";
        loginQuery = con.prepareStatement(sql);

        loginQuery.setString(1, id);
        loginQuery.setString(2, password);

        ResultSet result = loginQuery.executeQuery();



//            System.out.println(result.getString("firstName"));



        return result;

    }

    public ResultSet loginStudent(String id, String password) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");

        Connection con = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);


        String sql = "Select * from student where idstudent = ? and password = ?";
        loginQuery = con.prepareStatement(sql);

        loginQuery.setString(1, id);
        loginQuery.setString(2, password);

        ResultSet result = loginQuery.executeQuery();




        return result;

    }

    public ResultSet loginLecturer(String id, String password) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");

        Connection con = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);


        String sql = "Select * from lecturer where idlecturer = ? and password = ?";
        loginQuery = con.prepareStatement(sql);

        loginQuery.setString(1, id);
        loginQuery.setString(2, password);

        ResultSet result = loginQuery.executeQuery();


        return result;

    }

}

