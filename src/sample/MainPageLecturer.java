package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.entities.Lecturer;
import sample.queriesSQL.UsefulVariables;
import sample.tableviewPOJO.AssigmentMaster;
import sample.tableviewPOJO.ExamsMaster;
import sample.tableviewPOJO.GradesMaster;
import sample.tableviewPOJO.UserLoginPOJO;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class MainPageLecturer implements Initializable {



    private UsefulVariables usefulVariables = new UsefulVariables();
    private Connection con = DriverManager.getConnection(usefulVariables.URL, usefulVariables.DB_USER, usefulVariables.DB_PASSWORD);

    @FXML
    private Label welcomeMessage;

    @FXML
    private DatePicker setAssignmentGetDate;

    @FXML
    private TextArea setAssignmentGetDescription;

    @FXML
    private Button saveAssignment;

    @FXML
    private Button updateAssignment;

    @FXML
    private Button deleteAssignment;

    @FXML
    private Button clearAssignmentContent;

    @FXML
    private ChoiceBox<String> setAssignmentGetModule;

    @FXML
    private Label labelWelcome1;

    @FXML
    private TableView<AssigmentMaster> tableViewAssignment;

    @FXML
    private TableColumn<AssigmentMaster, String> col_assignmentModule;

    @FXML
    private TableColumn<AssigmentMaster, String> col_assignmentDate;

    @FXML
    private TableColumn<AssigmentMaster, String> col_assignmentDescription;

    @FXML
    private ComboBox<String> gradesGetModule;

    @FXML
    private ComboBox<String> gradesGetStudent;

    @FXML
    private TextField gradesGetGrade;

    @FXML
    private Button btnSaveGrade;

    @FXML
    private Button btnEditGrade;

    @FXML
    private Button btnDeleteGrade;

    @FXML
    private Button clearGrades;

    @FXML
    private Label labelWelcome2;

    @FXML
    private TableView<GradesMaster> tableViewGrades;

    @FXML
    private TableColumn<GradesMaster, String> col_gradesStudentId;

    @FXML
    private TableColumn<GradesMaster, String> col_gradesModule;

    @FXML
    private TableColumn<GradesMaster, Double> col_gradesGrades;

    @FXML
    private ComboBox<String> examsModule;

    @FXML
    private DatePicker examsDate;

    @FXML
    private Button examsSave;

    @FXML
    private Button examsUpdate;

    @FXML
    private Button examsDelete;

    @FXML
    private Button examsClear;

    @FXML
    private Label labelWelcome3;

    @FXML
    private TableView<ExamsMaster> tableViewExams;

    @FXML
    private TableColumn<ExamsMaster, String> col_examsModule;

    @FXML
    private TableColumn<ExamsMaster, String> col_examsDate;





    public MainPageLecturer() throws SQLException, ClassNotFoundException {

    }

    private void setWelcomeMessage(String message){
        labelWelcome1.setText("Welcome " + message);
        labelWelcome2.setText("Welcome " + message);
        labelWelcome3.setText("Welcome " + message);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setWelcomeMessage(UserLoginPOJO.getUserFullName());

        System.out.println("this is: " + UserLoginPOJO.getUserID());


//        setWelcomeMessage();



    }






//    @FXML
//    private ArrayList<String> getModules() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
//
//
//        String sql = "select * from module WHERE idlecturer = ?";
//        usefulVariables.getModules = con.prepareStatement(sql);
//        usefulVariables.getModules.setString(1, getLecturer().getId());
//        ResultSet resultBranches = usefulVariables.getModules.executeQuery();
//
//        ArrayList<String> allModules = new ArrayList<>();
//
//        while (resultBranches.next()) {
//            allModules.add(resultBranches.getString("subject"));
//        }
//
//        lecturerModules.getItems().addAll(getModules());
//
//        return allModules;
//    }


    @FXML
    void createAssignment(ActionEvent event) {

    }

}
