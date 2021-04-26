package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import sample.entities.Lecturer;
import sample.queriesSQL.UsefulVariables;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainPageLecturer implements Initializable {


    private Lecturer lecturer;
    private Lecturer myLecturer;
    private UsefulVariables usefulVariables = new UsefulVariables();
    private Connection con = DriverManager.getConnection(usefulVariables.URL, usefulVariables.DB_USER, usefulVariables.DB_PASSWORD);

    @FXML
    private Label welcomeMessage;

    @FXML
    private ChoiceBox<String> lecturerModules;

    @FXML
    private DatePicker assignmentDate;

    @FXML
    private TextArea assignmentDescription;

    public MainPageLecturer() throws SQLException, ClassNotFoundException {

//        getModules();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private ArrayList<String> getModules() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select * from module WHERE idlecturer = ?";
        usefulVariables.getModules = con.prepareStatement(sql);
        usefulVariables.getModules.setString(1, getLecturer().getId());
        ResultSet resultBranches = usefulVariables.getModules.executeQuery();

        ArrayList<String> allModules = new ArrayList<>();

        while (resultBranches.next()) {
            allModules.add(resultBranches.getString("subject"));
        }

        lecturerModules.getItems().addAll(getModules());

        return allModules;
    }


    @FXML
    private void createAssignment() throws SQLException, ClassNotFoundException {

        lecturer.createAssignment(assignmentDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), assignmentDescription.getText(),
        getLecturer().getId(), lecturerModules.getValue());



    }


    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) throws SQLException, ClassNotFoundException {
        this.lecturer = lecturer;
        myLecturer = lecturer;
        welcomeMessage.setText("Welcome " + getLecturer().getFirstName() +  " " + getLecturer().getLastName());

    }
}
