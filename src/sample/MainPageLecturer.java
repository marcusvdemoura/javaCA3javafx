package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.entities.Lecturer;
import sample.queriesSQL.UsefulVariables;
import sample.tableviewPOJO.*;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


public class MainPageLecturer implements Initializable {


    private UsefulVariables usefulVariables = new UsefulVariables();
    private Connection con = DriverManager.getConnection(usefulVariables.URL, usefulVariables.DB_USER, usefulVariables.DB_PASSWORD);


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
    private TableColumn<AssigmentMaster, Integer> col_assignmentId;

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
    private Button clearGrades;

    @FXML
    private Label labelWelcome2;

    @FXML
    private TableView<GradesMaster> tableViewGrades;

    @FXML
    private TableColumn<GradesMaster, String> col_gradesId;

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

    private void setWelcomeMessage(String message) {
        labelWelcome1.setText("Welcome " + message);
        labelWelcome2.setText("Welcome " + message);
        labelWelcome3.setText("Welcome " + message);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setWelcomeMessage(UserLoginPOJO.getUserFullName());

        try {
            setAssignmentGetModule.getItems().addAll(getModules());
            gradesGetModule.getItems().addAll(getModules());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        refreshAssignmentList();




        gradesGetModule.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                gradesGetStudent.getItems().clear();
                gradesGetStudent.setDisable(true);
            } else {
                // sample code, adapt as needed:
                List<String> list = null;
                try {
                    list = getStudents(gradesGetModule.getValue());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                gradesGetStudent.getItems().setAll(list);
                gradesGetStudent.setDisable(false);
            }
        });


    }


    // TAB ASSIGNMENT

    private ArrayList<String> getModules() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select * from module WHERE idlecturer = ?";
        usefulVariables.getModules = con.prepareStatement(sql);
        usefulVariables.getModules.setString(1, UserLoginPOJO.getUserID());
        ResultSet resultBranches = usefulVariables.getModules.executeQuery();

        ArrayList<String> allModules = new ArrayList<>();

        while (resultBranches.next()) {
            allModules.add(resultBranches.getString("subject"));
        }


        return allModules;
    }


    @FXML
    void createAssignment(ActionEvent event) throws SQLException, ClassNotFoundException {

        String dueDate = setAssignmentGetDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String description = setAssignmentGetDescription.getText();
        String module = setAssignmentGetModule.getValue();
        String lecturerid = UserLoginPOJO.getUserID();

        Lecturer.createAssignment(dueDate, description, module, lecturerid);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Assignment created");
        alert.show();

        refreshAssignmentList();

        toClear();
    }

    private ObservableList<AssigmentMaster> getAssignmentList() throws ClassNotFoundException, SQLException {


        ObservableList<AssigmentMaster> list = FXCollections.observableArrayList();


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "Select * from assignment WHERE lecturerId = ?";

        UsefulVariables.getAllAssignments = con.prepareStatement(sql);
        UsefulVariables.getAllAssignments.setString(1, UserLoginPOJO.getUserID());
        ResultSet result = UsefulVariables.getAllAssignments.executeQuery();

        while (result.next()) {

            AssigmentMaster assigmentMaster = new AssigmentMaster();
            assigmentMaster.setId(result.getInt("idassignment"));
            assigmentMaster.setDate(result.getString("dueDate"));
            assigmentMaster.setDescription(result.getString("description"));
            assigmentMaster.setModule(result.getString("moduleSubject"));
            assigmentMaster.setDescription(result.getString("description"));


            list.add(assigmentMaster);


        }


        return list;


    }



    public void getSelectedAssignment(javafx.scene.input.MouseEvent mouseEvent) throws ClassNotFoundException, SQLException, ParseException {

        int indexAssignments = -1;

        indexAssignments = tableViewAssignment.getSelectionModel().getSelectedIndex();
        if (indexAssignments <= -1) {
            return;
        }

        int assignmentId = col_assignmentId.getCellData(indexAssignments);

        Class.forName("com.mysql.jdbc.Driver");

        System.out.println(assignmentId);


        String sql = "select * from assignment WHERE idassignment = ?";
        usefulVariables.getSelectedAssignment = con.prepareStatement(sql);
        usefulVariables.getSelectedAssignment.setInt(1, assignmentId);
        ResultSet resultAllAssignments = usefulVariables.getSelectedAssignment.executeQuery();


        while (resultAllAssignments.next()) {
            setAssignmentGetModule.setValue(resultAllAssignments.getString("moduleSubject"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(resultAllAssignments.getString("dueDate"), formatter);
            setAssignmentGetDate.setValue(localDate);
            setAssignmentGetDescription.setText(resultAllAssignments.getString("description"));


        }


    }

    private void refreshAssignmentList() {

        col_assignmentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_assignmentModule.setCellValueFactory(new PropertyValueFactory<>("module"));
        col_assignmentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_assignmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));


        try {
            tableViewAssignment.getItems().setAll(getAssignmentList());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @FXML
    private void toEditAssignment() throws Exception {

        int indexAssignments = -1;

        indexAssignments = tableViewAssignment.getSelectionModel().getSelectedIndex();
        if (indexAssignments <= -1) {
            return;
        }

        int assignmentId = col_assignmentId.getCellData(indexAssignments);


        Lecturer.editAssignment(assignmentId, setAssignmentGetDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                setAssignmentGetDescription.getText(), setAssignmentGetModule.getValue());


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Assignment edited");
        alert.show();
        refreshAssignmentList();

        toClear();




    }

    @FXML
    private void toDeleteAssignment() throws Exception {

        int indexAssignments = -1;

        indexAssignments = tableViewAssignment.getSelectionModel().getSelectedIndex();
        if (indexAssignments <= -1) {
            return;
        }

        int assignmentId = col_assignmentId.getCellData(indexAssignments);

        Lecturer.deleteAssignment(assignmentId);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Assignment Deleted");
        alert.show();
        refreshAssignmentList();

        refreshAssignmentList();

        toClear();

    }



    // TAB GRADES





    @FXML
    void setGrades(ActionEvent event) throws Exception {

        String getModule = gradesGetModule.getValue();
        String studentid = gradesGetStudent.getValue();
        Double grade = Double.parseDouble(gradesGetGrade.getText());
        String lecturerid = UserLoginPOJO.getUserID();

        Lecturer.setGrade(studentid, getModule, grade, lecturerid);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Grade added for Student");
        alert.show();

        refreshAssignmentList();

        toClear();
    }






    // TAB EXAMS






    @FXML
    private void toClear(){

        setAssignmentGetModule.getSelectionModel().clearSelection();
        setAssignmentGetDate.getEditor().clear();
        setAssignmentGetDescription.clear();

//        gradesGetStudent.getSelectionModel().clearSelection();
//        gradesGetModule.getSelectionModel().clearSelection();
//        gradesGetGrade.clear();
//
//        examsModule.getSelectionModel().clearSelection();
//        examsDate.getEditor().clear();

    }




    private ArrayList<String> getStudents(String moduleName) throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.jdbc.Driver");

        String sql1 = "select * from courseYear WHERE moduleName = ?";
        PreparedStatement getcourseYearList = con.prepareStatement(sql1);
        getcourseYearList.setString(1, moduleName);
        ResultSet firstResult = getcourseYearList.executeQuery();


        String course = "";
        Integer year = 0;

        while (firstResult.next()) {


            course = firstResult.getString("course");
            year = firstResult.getInt("year");

        }

        ArrayList<String> allStudents = new ArrayList<>();

        String sql2 = "SELECT idstudent FROM student WHERE course = ? AND courseYear = ?";
        PreparedStatement getListStudents = con.prepareStatement(sql2);
        getListStudents.setString(1,course);
        getListStudents.setInt(2, year);
        ResultSet secondResult = getListStudents.executeQuery();

        while(secondResult.next()){
            allStudents.add(secondResult.getString("idstudent"));
        }


        return allStudents;


    }







}
