package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.entities.*;

import sample.queriesSQL.UsefulVariables;
import sample.tableviewPOJO.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AdminMainPage implements Initializable {

    private UsefulVariables usefulVariables = new UsefulVariables();

    private Connection con = DriverManager.getConnection(usefulVariables.URL, usefulVariables.DB_USER,
            usefulVariables.DB_PASSWORD);

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Admin admin;


    @FXML
    private ChoiceBox<String> studentsInDebt;


    @FXML
    private TextField branchUnitInsert, createLecturerId, lecturerFirstName, lecturerLastName, lecturerPhone, lecturerEmail, lecturerpassword;
    @FXML
    private TextField branchAddressInsert, createModuleName, createModuleAddHour, insertStudentFirstName, insertStudentLastName, insertStudentPhone;
    @FXML
    private ChoiceBox<String> createCourseBranchChoice, createModuleAddBranch, studentInBranch;
    @FXML
    private TextField courseNameInput;
    @FXML
    private TextField coursePrice;
    @FXML
    private ChoiceBox<String> courseYearNameInput, genderLecturer, genderStudent;

    @FXML
    private TextField courseYearInput, studentEmailInsert, studentPasswordInsert, insertStudentId;
    @FXML
    private ChoiceBox<String> courseYearModuleInput, ModuleInCourse, studentInCourse, courseYearBranchInput;
    @FXML
    private ChoiceBox<String> createModuleAddLecturer, addStudentCourseYear;

    @FXML
    private DatePicker lecturerbirth, studentdobInsert;

    @FXML
    private ChoiceBox<String> createModuleAddWeekDay;
    private String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    @FXML
    private ChoiceBox<String> studentIsPaid;


    @FXML
    private ListView listViewStudent, listViewLecturer, listViewBranches, listViewCourses, listViewModules, listViewCourseYear;

    private ArrayList<String> listCourses, listModules, listCourseYear;


    public AdminMainPage() throws SQLException, ClassNotFoundException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderLecturer.getItems().addAll("Male", "Female", "Prefer not to say");
        genderStudent.getItems().addAll("Male", "Female", "Prefer not to say");
        studentIsPaid.getItems().addAll("Yes", "No");
        createModuleAddWeekDay.getItems().addAll(weekdays);

        try {
            studentInBranch.getItems().addAll(getBranches());
            createModuleAddBranch.getItems().addAll(getBranches());
            courseYearBranchInput.getItems().addAll(getBranches());
            createCourseBranchChoice.getItems().addAll(getBranches());
            studentsInDebt.getItems().addAll(getStudentsToPay());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            createModuleAddLecturer.getItems().addAll(getLecturersId());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // GET CHOICES BOX FOR STUDENT
        studentInBranch.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                studentInCourse.getItems().clear();
                studentInCourse.setDisable(true);
            } else {
                // sample code, adapt as needed:
                List<String> list = null;
                try {
                    list = getCourses(studentInBranch.getValue());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                studentInCourse.getItems().setAll(list);
                studentInCourse.setDisable(false);
            }
        });

        studentInCourse.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                addStudentCourseYear.getItems().clear();
                addStudentCourseYear.setDisable(true);
            } else {
                // sample code, adapt as needed:
                List<String> list = null;
                try {
                    list = getCourseYears(studentInCourse.getValue());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                addStudentCourseYear.getItems().setAll(list);
                addStudentCourseYear.setDisable(false);
            }
        });

        //get choice boxes for modules

        createModuleAddBranch.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                ModuleInCourse.getItems().clear();
                ModuleInCourse.setDisable(true);
            } else {
                // sample code, adapt as needed:
                List<String> list = null;
                try {
                    list = getCourses(createModuleAddBranch.getValue());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ModuleInCourse.getItems().setAll(list);
                ModuleInCourse.setDisable(false);
            }
        });

        courseYearBranchInput.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                courseYearNameInput.getItems().clear();
                courseYearNameInput.setDisable(true);
            } else {
                // sample code, adapt as needed:
                List<String> list = null;
                try {
                    list = getCourses(courseYearBranchInput.getValue());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                courseYearNameInput.getItems().setAll(list);
                courseYearNameInput.setDisable(false);
            }
        });


        courseYearNameInput.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                courseYearModuleInput.getItems().clear();
                courseYearModuleInput.setDisable(true);
            } else {
                // sample code, adapt as needed:
                List<String> list = null;
                try {
                    list = getModules(courseYearNameInput.getValue());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                courseYearModuleInput.getItems().setAll(list);
                courseYearModuleInput.setDisable(false);
            }
        });


        // fetch data into table views from db

        refreshStudentTable();
        refreshLecturerTable();
        refreshCollegeBranch();
        refreshCourse();
        refreshModule();
        refreshCourseYear();
        refreshPaymentLog();


    }


    // CREATE STUDENT, LECTURER

    @FXML
    private void toCreateStudent() throws SQLException, ClassNotFoundException, IOException {

        Integer installments[] = {1, 2, 3, 4, 5, 6};
        List<Integer> dialogData;
        Integer numberInstallments = 0;


        admin.createStudents(insertStudentFirstName.getText(), insertStudentLastName.getText(), genderStudent.getValue(), insertStudentPhone.
                        getText(), studentdobInsert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                studentEmailInsert.getText(), insertStudentId.getText(), studentPasswordInsert.getText(), studentInBranch.getValue(),
                studentInCourse.getValue(), Integer.parseInt(addStudentCourseYear.getValue()),
                studentIsPaid.getValue().equals("Yes") ? true : false);

        if (studentIsPaid.getValue().equals("No")) {

            dialogData = Arrays.asList(installments);
            Dialog dialog = new ChoiceDialog(dialogData.get(0), dialogData);
            dialog.setTitle("Installments");
            dialog.setHeaderText("Select the number of installments: ");
            Optional<Integer> result = dialog.showAndWait();
            String selected = "cancelled.";

            if (result.isPresent()) {
                selected = String.valueOf(result.get());
                numberInstallments = result.get();
            }

            admin.setInstallments(numberInstallments, insertStudentId.getText());

        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Student created");
        alert.show();

        insertStudentFirstName.clear();
        insertStudentLastName.clear();
        genderStudent.getSelectionModel().clearSelection();
        insertStudentPhone.clear();
        studentdobInsert.getEditor().clear();
        studentEmailInsert.clear();
        insertStudentId.clear();
        studentPasswordInsert.clear();
        studentInBranch.getSelectionModel().clearSelection();
        studentInCourse.getSelectionModel().clearSelection();
        addStudentCourseYear.getSelectionModel().clearSelection();
        studentIsPaid.getSelectionModel().clearSelection();

        refreshStudentTable();


    }

    @FXML
    private void toCreateLecturer() throws Exception {

        admin.createLecturer(lecturerFirstName.getText(), lecturerLastName.getText(), genderLecturer.getValue(), lecturerPhone.getText(),
                lecturerbirth.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), lecturerEmail.getText(),
                createLecturerId.getText(), lecturerpassword.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Lecturer created");
        alert.show();

        lecturerFirstName.clear();
        lecturerLastName.clear();
        genderLecturer.getSelectionModel().clearSelection();
        lecturerPhone.clear();
        lecturerbirth.getEditor().clear();
        lecturerEmail.clear();
        createLecturerId.clear();
        lecturerpassword.clear();

        refreshLecturerTable();


    }


    // DELETE STUDENT, LECTURER
    @FXML
    private void toDeleteStudent() throws SQLException, ClassNotFoundException {

        admin.deleteStudent(insertStudentId.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Student deleted");
        alert.show();

        insertStudentFirstName.clear();
        insertStudentLastName.clear();
        genderStudent.getSelectionModel().clearSelection();
        insertStudentPhone.clear();
        studentdobInsert.getEditor().clear();
        studentEmailInsert.clear();
        insertStudentId.clear();
        studentPasswordInsert.clear();
        studentInBranch.getSelectionModel().clearSelection();
        studentInCourse.getSelectionModel().clearSelection();
        addStudentCourseYear.getSelectionModel().clearSelection();
        studentIsPaid.getSelectionModel().clearSelection();
    }

    @FXML
    private void toDeleteLecturer() throws SQLException, ClassNotFoundException {

        admin.deleteLecturer(createLecturerId.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Lecturer deleted");
        alert.show();

        lecturerFirstName.clear();
        lecturerLastName.clear();
        genderLecturer.getSelectionModel().clearSelection();
        lecturerPhone.clear();
        lecturerbirth.getEditor().clear();
        lecturerEmail.clear();
        createLecturerId.clear();
        lecturerpassword.clear();

    }


    // CREATE COURSES AND COURSES YEAR


    @FXML
    private void toCreateCourse() throws ClassNotFoundException, SQLException {

        getAdmin().createCourse(createCourseBranchChoice.getValue(), courseNameInput.getText(),
                Double.valueOf(coursePrice.getText()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Course created");
        alert.show();


        createCourseBranchChoice.getSelectionModel().clearSelection();
        courseNameInput.clear();
        coursePrice.clear();
        refreshCourse();

    }

    @FXML
    private void toCreateCourseYear() throws SQLException, ClassNotFoundException {

        String year = courseYearInput.getText();
        String courseName = courseYearNameInput.getValue();
        String module = courseYearModuleInput.getValue();


        admin.createCourseYear(year, courseName, module);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Course Year created");
        alert.show();


        courseYearBranchInput.getSelectionModel().clearSelection();
        courseYearInput.clear();
        courseYearNameInput.getSelectionModel().clearSelection();
        courseYearModuleInput.getSelectionModel().clearSelection();

        refreshCourseYear();


    }

    // DELETE COURSE

    @FXML
    private void toDeleteCourse() throws ClassNotFoundException, SQLException {

        getAdmin().deleteCourse(createCourseBranchChoice.getValue(), courseNameInput.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");

        alert.setHeaderText("Course deleted");
        alert.show();

        createCourseBranchChoice.getSelectionModel().clearSelection();
        courseNameInput.clear();
        coursePrice.clear();


    }


    // CREATE MODULES

    @FXML
    private void toCreateModule() throws ClassNotFoundException, SQLException {

        getAdmin().createModule(createModuleName.getText(), ModuleInCourse.getValue(), createModuleAddBranch.getValue(), createModuleAddWeekDay.getValue(),
                createModuleAddHour.getText(), createModuleAddLecturer.getValue());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Module created");
        alert.show();

        createModuleAddWeekDay.getSelectionModel().clearSelection();
        createModuleAddLecturer.getSelectionModel().clearSelection();
        ModuleInCourse.getSelectionModel().clearSelection();
        createModuleName.clear();
        createModuleAddBranch.getSelectionModel().clearSelection();
        createModuleName.clear();

        refreshModule();
    }

    // DELETE MODULES

    @FXML
    private void toDeleteModules() throws SQLException, ClassNotFoundException {

        admin.deleteModule(createModuleName.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Module deleted");
        alert.show();

        createModuleAddWeekDay.getSelectionModel().clearSelection();
        createModuleAddLecturer.getSelectionModel().clearSelection();
        ModuleInCourse.getSelectionModel().clearSelection();
        createModuleName.clear();
        createModuleAddBranch.getSelectionModel().clearSelection();
        createModuleName.clear();

    }

    // CREATE COLLEGE BRANCHES

    @FXML
    private void toCreateCollegeBranch() throws SQLException, ClassNotFoundException {


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO collegeBranches(unit, address) VALUES (?, ?)";
        usefulVariables.createBranchQuery = con.prepareStatement(sql);

        usefulVariables.createBranchQuery.setString(1, branchUnitInsert.getText());
        usefulVariables.createBranchQuery.setString(2, branchAddressInsert.getText());

        usefulVariables.createBranchQuery.execute();

        refreshCollegeBranch();

    }

    // DELETE COLLEGE BRANCHES

    @FXML
    private void toDeleteBranch() throws SQLException, ClassNotFoundException {

        admin.deleteBranch(branchUnitInsert.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Branch deleted");
        alert.show();

        branchUnitInsert.clear();
        branchAddressInsert.clear();

    }

    private ArrayList<String> getStudentsToPay() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select * from student WHERE isPaidFull=?";
        usefulVariables.addPaymentQuery = con.prepareStatement(sql);
        usefulVariables.addPaymentQuery.setBoolean(1, false);
        ResultSet resultStudentsInDebt = usefulVariables.addPaymentQuery.executeQuery();

        ArrayList<String> studentsToPay = new ArrayList<>();

        while (resultStudentsInDebt.next()) {
            studentsToPay.add(resultStudentsInDebt.getString("idstudent"));
        }


        return studentsToPay;

    }


    private ArrayList<String> getBranches() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select * from collegeBranches";
        usefulVariables.getBranches = con.prepareStatement(sql);
        ResultSet resultBranches = usefulVariables.getBranches.executeQuery();

        ArrayList<String> branchOptions = new ArrayList<>();

        while (resultBranches.next()) {
            branchOptions.add(resultBranches.getString("unit"));
        }


        return branchOptions;
    }

    private ArrayList<String> getCourses(String branchUnit) throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "select * from course WHERE collegeBranchUnit = ?";
        usefulVariables.getCourses = con.prepareStatement(sql);
        usefulVariables.getCourses.setString(1, branchUnit);
        ResultSet resultBranches = usefulVariables.getCourses.executeQuery();

        ArrayList<String> allCourses = new ArrayList<>();


        while (resultBranches.next()) {
            allCourses.add(resultBranches.getString("name"));
        }


        return allCourses;


    }

    private ArrayList<String> getLecturersId() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select * from lecturer";
        usefulVariables.getLecturersId = con.prepareStatement(sql);
        ResultSet resultBranches = usefulVariables.getLecturersId.executeQuery();

        ArrayList<String> allLecturers = new ArrayList<>();

        while (resultBranches.next()) {
            allLecturers.add(resultBranches.getString("idlecturer"));
        }


        return allLecturers;
    }

    private ArrayList<String> getModules(String courseName) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select * from module WHERE course = ?";
        usefulVariables.getModules = con.prepareStatement(sql);
        usefulVariables.getModules.setString(1, courseName);
        ResultSet resultBranches = usefulVariables.getModules.executeQuery();

        ArrayList<String> allModules = new ArrayList<>();

        while (resultBranches.next()) {
            allModules.add(resultBranches.getString("subject"));
        }


        return allModules;
    }

    private ArrayList<String> getCourseYears(String courseName) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select * from courseYear WHERE course = ?";
        usefulVariables.getModules = con.prepareStatement(sql);
        usefulVariables.getModules.setString(1, courseName);
        ResultSet resultBranches = usefulVariables.getModules.executeQuery();

        ArrayList<String> allYears = new ArrayList<>();

        while (resultBranches.next()) {
            allYears.add(resultBranches.getString("year"));
        }


        return allYears;
    }


    @FXML
    private void toClearInputs() {
        insertStudentFirstName.clear();
        insertStudentLastName.clear();
        genderStudent.getSelectionModel().clearSelection();
        insertStudentPhone.clear();
        studentdobInsert.getEditor().clear();
        studentEmailInsert.clear();
        insertStudentId.clear();
        studentPasswordInsert.clear();
        studentInBranch.getSelectionModel().clearSelection();
        studentInCourse.getSelectionModel().clearSelection();
        addStudentCourseYear.getSelectionModel().clearSelection();
        studentIsPaid.getSelectionModel().clearSelection();


        lecturerFirstName.clear();
        lecturerLastName.clear();
        genderLecturer.getSelectionModel().clearSelection();
        lecturerPhone.clear();
        lecturerbirth.getEditor().clear();
        lecturerEmail.clear();
        createLecturerId.clear();
        lecturerpassword.clear();


        createCourseBranchChoice.getSelectionModel().clearSelection();
        courseNameInput.clear();
        coursePrice.clear();

        createModuleAddWeekDay.getSelectionModel().clearSelection();
        createModuleAddLecturer.getSelectionModel().clearSelection();
        ModuleInCourse.getSelectionModel().clearSelection();
        createModuleName.clear();
        createModuleAddBranch.getSelectionModel().clearSelection();
        createModuleName.clear();

        branchUnitInsert.clear();
        branchAddressInsert.clear();

        courseYearBranchInput.getSelectionModel().clearSelection();
        courseYearInput.clear();
        courseYearNameInput.getSelectionModel().clearSelection();
        courseYearModuleInput.getSelectionModel().clearSelection();


    }


    // TABLE VIEWS CONFIG

    // Student table view

    @FXML
    private TableView<StudentMaster> studentTableView = new TableView<>();

    @FXML
    private TableColumn<StudentMaster, String> col_studentid = new TableColumn<StudentMaster, String>();

    @FXML
    private TableColumn<StudentMaster, String> col_studentFirstName = new TableColumn<>();

    @FXML
    private TableColumn<StudentMaster, String> col_studentLastName = new TableColumn<>();

    @FXML
    private TableColumn<StudentMaster, String> col_studentBranch = new TableColumn<>();

    @FXML
    private TableColumn<StudentMaster, String> col_studentCourse = new TableColumn<>();

    @FXML
    private TableColumn<StudentMaster, String> col_studentYear = new TableColumn<>();

    // Get All Students

    private ObservableList<StudentMaster> getStudentsList() throws ClassNotFoundException, SQLException {


        ObservableList<StudentMaster> list = FXCollections.observableArrayList();


        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement getStudents;

        String sql = "Select * from student";

        getStudents = con.prepareStatement(sql);


        ResultSet result = getStudents.executeQuery();

        while (result.next()) {

            StudentMaster studentMaster = new StudentMaster();
            studentMaster.setStudentID(result.getString("idstudent"));
            studentMaster.setFirstName(result.getString("firstName"));
            studentMaster.setLastName(result.getString("lastName"));
            studentMaster.setBranch(result.getString("collegeBranch"));
            studentMaster.setCourse(result.getString("course"));
            studentMaster.setYear(result.getInt("courseYear"));

            list.add(studentMaster);


        }


        return list;


    }

    // to edit student data

    int indexStudent = -1;

    public void getSelectedStudent(javafx.scene.input.MouseEvent mouseEvent) throws ClassNotFoundException, SQLException, ParseException {

        indexStudent = studentTableView.getSelectionModel().getSelectedIndex();
        if (indexStudent <= -1) {
            return;
        }

        String idstudent = col_studentid.getCellData(indexStudent).toString();

        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select * from student WHERE idstudent = ?";
        usefulVariables.getAllStudents = con.prepareStatement(sql);
        usefulVariables.getAllStudents.setString(1, idstudent);
        ResultSet resultAllStudents = usefulVariables.getAllStudents.executeQuery();


        while (resultAllStudents.next()) {
            insertStudentId.setText(resultAllStudents.getString("idstudent"));
            insertStudentFirstName.setText(resultAllStudents.getString("firstName"));
            insertStudentLastName.setText(resultAllStudents.getString("lastName"));
            insertStudentPhone.setText(resultAllStudents.getString("phone"));
            studentEmailInsert.setText(resultAllStudents.getString("emailAddress"));
            studentPasswordInsert.setText(resultAllStudents.getString("password"));
            String gender = resultAllStudents.getString("gender");
            genderStudent.setValue(gender);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(resultAllStudents.getString("dob"), formatter);
            studentdobInsert.setValue(localDate);
            String branch = resultAllStudents.getString("collegeBranch");
            String course = resultAllStudents.getString("course");
            int courseYear = resultAllStudents.getInt("courseYear");
            String isPaidFull = "";
            if (resultAllStudents.getBoolean("isPaidFull")) {
                isPaidFull = "Yes";
            } else {
                isPaidFull = "No";
            }
            studentInBranch.setValue(branch);
            studentInCourse.setValue(course);
            addStudentCourseYear.setValue(String.valueOf(courseYear));
            studentIsPaid.setValue(isPaidFull);
        }


    }

    @FXML
    private void toEditStudent() throws SQLException, ClassNotFoundException {

        Integer installments[] = {1, 2, 3, 4, 5, 6};
        List<Integer> dialogData;
        Integer numberInstallments = 0;

        indexStudent = studentTableView.getSelectionModel().getSelectedIndex();
        if (indexStudent <= -1) {
            return;
        }

        String idstudent = col_studentid.getCellData(indexStudent).toString();


        admin.editingStudent(insertStudentFirstName.getText(), insertStudentLastName.getText(), genderStudent.getValue(), insertStudentPhone.
                        getText(), studentdobInsert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                studentEmailInsert.getText(), insertStudentId.getText(), studentPasswordInsert.getText(), studentInBranch.getValue(),
                studentInCourse.getValue(), Integer.parseInt(addStudentCourseYear.getValue()),
                studentIsPaid.getValue().equals("Yes") ? true : false, idstudent);

        if (studentIsPaid.getValue().equals("No")) {

            dialogData = Arrays.asList(installments);
            Dialog dialog = new ChoiceDialog(dialogData.get(0), dialogData);
            dialog.setTitle("Installments");
            dialog.setHeaderText("Select the number of installments: ");
            Optional<Integer> result = dialog.showAndWait();
            String selected = "cancelled.";

            if (result.isPresent()) {
                selected = String.valueOf(result.get());
                numberInstallments = result.get();
            }

            admin.setInstallments(numberInstallments, insertStudentId.getText());

        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Student edited");
        alert.show();

        insertStudentFirstName.clear();
        insertStudentLastName.clear();
        genderStudent.getSelectionModel().clearSelection();
        insertStudentPhone.clear();
        studentdobInsert.getEditor().clear();
        studentEmailInsert.clear();
        insertStudentId.clear();
        studentPasswordInsert.clear();
        studentInBranch.getSelectionModel().clearSelection();
        studentInCourse.getSelectionModel().clearSelection();
        addStudentCourseYear.getSelectionModel().clearSelection();
        studentIsPaid.getSelectionModel().clearSelection();

        refreshStudentTable();

    }

    private void refreshStudentTable() {

        col_studentid.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        col_studentFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_studentLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_studentBranch.setCellValueFactory(new PropertyValueFactory<>("branch"));
        col_studentCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        col_studentYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        try {
            studentTableView.getItems().setAll(getStudentsList());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @FXML
    private TableView<LecturerMaster> lecturerTableView;

    @FXML
    private TableColumn<LecturerMaster, String> col_lecturerid;

    @FXML
    private TableColumn<LecturerMaster, String> col_lecturerFirstName;

    @FXML
    private TableColumn<LecturerMaster, String> col_lecturerLastName;

    @FXML
    private TableColumn<LecturerMaster, String> col_lecturerGender;

    @FXML
    private TableColumn<LecturerMaster, String> col_lecturerPhone;

    // Get All Lecturers

    private ObservableList<LecturerMaster> getLecturersList() throws ClassNotFoundException, SQLException {


        ObservableList<LecturerMaster> list = FXCollections.observableArrayList();


        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement getStudents;

        String sql = "Select * from lecturer";

        usefulVariables.getAllLecturers = con.prepareStatement(sql);


        ResultSet result = usefulVariables.getAllLecturers.executeQuery();

        while (result.next()) {

            LecturerMaster lecturerMaster = new LecturerMaster();
            lecturerMaster.setLecturerId(result.getString("idlecturer"));
            lecturerMaster.setFirstName(result.getString("firstName"));
            lecturerMaster.setLastName(result.getString("lastName"));
            lecturerMaster.setGender(result.getString("gender"));
            lecturerMaster.setPhone(result.getString("phone"));

            list.add(lecturerMaster);


        }


        return list;


    }


    int indexLecturer = -1;

    public void getSelectedLecturer(javafx.scene.input.MouseEvent mouseEvent) throws ClassNotFoundException, SQLException, ParseException {

        indexLecturer = lecturerTableView.getSelectionModel().getSelectedIndex();
        if (indexLecturer <= -1) {
            return;
        }

        String idlecturer = col_lecturerid.getCellData(indexLecturer).toString();

        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select * from lecturer WHERE idlecturer = ?";
        usefulVariables.getAllLecturers = con.prepareStatement(sql);
        usefulVariables.getAllLecturers.setString(1, idlecturer);
        ResultSet resultAllStudents = usefulVariables.getAllLecturers.executeQuery();


        while (resultAllStudents.next()) {
            createLecturerId.setText(resultAllStudents.getString("idlecturer"));
            lecturerFirstName.setText(resultAllStudents.getString("firstName"));
            lecturerLastName.setText(resultAllStudents.getString("lastName"));
            lecturerPhone.setText(resultAllStudents.getString("phone"));
            lecturerEmail.setText(resultAllStudents.getString("emailAddress"));
            lecturerpassword.setText(resultAllStudents.getString("password"));
            String gender = resultAllStudents.getString("gender");
            genderLecturer.setValue(gender);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(resultAllStudents.getString("dob"), formatter);
            lecturerbirth.setValue(localDate);

        }


    }

    @FXML
    private void toEditLecturer() throws Exception {

        String idlecturer = col_lecturerid.getCellData(indexLecturer).toString();

        admin.editingLecturer(lecturerFirstName.getText(), lecturerLastName.getText(), genderLecturer.getValue(), lecturerPhone.getText(),
                lecturerbirth.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), lecturerEmail.getText(),
                createLecturerId.getText(), lecturerpassword.getText(), idlecturer);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Lecturer edited");
        alert.show();

        lecturerFirstName.clear();
        lecturerLastName.clear();
        genderLecturer.getSelectionModel().clearSelection();
        lecturerPhone.clear();
        lecturerbirth.getEditor().clear();
        lecturerEmail.clear();
        createLecturerId.clear();
        lecturerpassword.clear();

        refreshLecturerTable();

    }

    private void refreshLecturerTable() {

        col_lecturerid.setCellValueFactory(new PropertyValueFactory<>("lecturerId"));
        col_lecturerFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lecturerLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_lecturerGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        col_lecturerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));


        try {
            lecturerTableView.getItems().setAll(getLecturersList());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    // COLLEGE BRANCH TABLE VIEW MANAGEMENT

    @FXML
    private TableView<CollegeBranchMaster> collegeBranchTableView;

    @FXML
    private TableColumn<CollegeBranchMaster, String> col_createbranchUnit;

    @FXML
    private TableColumn<CollegeBranchMaster, String> col_createbranchAddress;


    private ObservableList<CollegeBranchMaster> getBranchesList() throws ClassNotFoundException, SQLException {


        ObservableList<CollegeBranchMaster> list = FXCollections.observableArrayList();


        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement getStudents;

        String sql = "Select * from collegeBranches";

        usefulVariables.getAllBranches = con.prepareStatement(sql);


        ResultSet result = usefulVariables.getAllBranches.executeQuery();

        while (result.next()) {

            CollegeBranchMaster collegeBranchMaster = new CollegeBranchMaster();
            collegeBranchMaster.setBranchUnit(result.getString("unit"));
            collegeBranchMaster.setAddress(result.getString("address"));


            list.add(collegeBranchMaster);


        }


        return list;


    }

    int indexBranches = -1;

    public void getSelectedBranch(javafx.scene.input.MouseEvent mouseEvent) throws ClassNotFoundException, SQLException, ParseException {

        indexBranches = collegeBranchTableView.getSelectionModel().getSelectedIndex();
        if (indexBranches <= -1) {
            return;
        }

        String idlecturer = col_createbranchUnit.getCellData(indexBranches).toString();

        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select * from collegeBranches WHERE unit = ?";
        usefulVariables.getAllBranches = con.prepareStatement(sql);
        usefulVariables.getAllBranches.setString(1, idlecturer);
        ResultSet resultAllBranches = usefulVariables.getAllBranches.executeQuery();


        while (resultAllBranches.next()) {
            branchUnitInsert.setText(resultAllBranches.getString("unit"));
            branchAddressInsert.setText(resultAllBranches.getString("address"));

        }


    }

    @FXML
    private void toEditCollegeBranch() throws Exception {

        String branchUnit = col_createbranchUnit.getCellData(indexBranches).toString();


        admin.editingBranch(branchUnitInsert.getText(), branchAddressInsert.getText(), branchUnit);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("College Branch edited");
        alert.show();

        branchUnitInsert.clear();
        branchAddressInsert.clear();

        refreshCollegeBranch();

    }

    private void refreshCollegeBranch() {

        col_createbranchUnit.setCellValueFactory(new PropertyValueFactory<>("branchUnit"));
        col_createbranchAddress.setCellValueFactory(new PropertyValueFactory<>("address"));


        try {
            collegeBranchTableView.getItems().setAll(getBranchesList());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    // TABLE VIEW MANAGE COURSES

    @FXML
    private TableView<CoursesMaster> courseTableVIew;

    @FXML
    private TableColumn<CoursesMaster, String> col_createCourseName;

    @FXML
    private TableColumn<CoursesMaster, String> col_createCourseBranch;

    @FXML
    private TableColumn<CoursesMaster, Double> col_createCoursePrice;

    private ObservableList<CoursesMaster> getCoursesList() throws ClassNotFoundException, SQLException {


        ObservableList<CoursesMaster> list = FXCollections.observableArrayList();


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "Select * from course";

        usefulVariables.getAllCourses = con.prepareStatement(sql);


        ResultSet result = usefulVariables.getAllCourses.executeQuery();

        while (result.next()) {

            CoursesMaster coursesMaster = new CoursesMaster();
            coursesMaster.setCourse(result.getString("name"));
            coursesMaster.setBranch(result.getString("collegeBranchUnit"));
            coursesMaster.setPrice(result.getDouble("price"));


            list.add(coursesMaster);


        }


        return list;


    }

    int indexCourse = -1;

    public void getSelectedCourse(javafx.scene.input.MouseEvent mouseEvent) throws ClassNotFoundException, SQLException, ParseException {

        indexCourse = courseTableVIew.getSelectionModel().getSelectedIndex();
        if (indexCourse <= -1) {
            return;
        }

        String courseName = col_createCourseName.getCellData(indexCourse).toString();

        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select * from course WHERE name = ?";
        usefulVariables.getAllCourses = con.prepareStatement(sql);
        usefulVariables.getAllCourses.setString(1, courseName);
        ResultSet resultAllBranches = usefulVariables.getAllCourses.executeQuery();


        while (resultAllBranches.next()) {
            createCourseBranchChoice.setValue(resultAllBranches.getString("collegeBranchUnit"));
            courseNameInput.setText(resultAllBranches.getString("name"));
            coursePrice.setText(String.valueOf(resultAllBranches.getDouble("price")));

        }


    }

    @FXML
    private void toEditCourse() throws Exception {

        String courseName = col_createCourseName.getCellData(indexCourse).toString();


        admin.editingCourse(createCourseBranchChoice.getValue(), courseNameInput.getText(), Double.parseDouble(coursePrice.getText()),
                courseName);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Course edited");
        alert.show();

        createCourseBranchChoice.getSelectionModel().clearSelection();
        courseNameInput.clear();
        coursePrice.clear();

        refreshCourse();

    }

    private void refreshCourse() {

        col_createCourseName.setCellValueFactory(new PropertyValueFactory<>("course"));
        col_createCourseBranch.setCellValueFactory(new PropertyValueFactory<>("branch"));
        col_createCoursePrice.setCellValueFactory(new PropertyValueFactory<>("price"));


        try {
            courseTableVIew.getItems().setAll(getCoursesList());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    // MANAGING MODULE TABLEVIEW

    @FXML
    private TableView<ModuleMaster> moduleTableView;

    @FXML
    private TableColumn<ModuleMaster, String> col_CreateModuleName;

    @FXML
    private TableColumn<ModuleMaster, String> col_createModuleCourse;

    @FXML
    private TableColumn<ModuleMaster, String> col_createModuleLecturerid;

    @FXML
    private TableColumn<ModuleMaster, String> col_createModuleWeekday;

    @FXML
    private TableColumn<ModuleMaster, String> col_createModuleHour;


    private ObservableList<ModuleMaster> getModuleList() throws ClassNotFoundException, SQLException {


        ObservableList<ModuleMaster> list = FXCollections.observableArrayList();


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "Select * from module";

        usefulVariables.getAllModules = con.prepareStatement(sql);


        ResultSet result = usefulVariables.getAllModules.executeQuery();

        while (result.next()) {

            ModuleMaster moduleMaster = new ModuleMaster();
            moduleMaster.setModuleName(result.getString("subject"));
            moduleMaster.setCourseName(result.getString("course"));
            moduleMaster.setIdlecturer(result.getString("idlecturer"));
            moduleMaster.setWeekday(result.getString("weekDay"));
            moduleMaster.setClasshour(result.getString("classHour"));


            list.add(moduleMaster);


        }


        return list;


    }

    int indexModule = -1;

    public void getSelectedModule(javafx.scene.input.MouseEvent mouseEvent) throws ClassNotFoundException, SQLException, ParseException {

        indexModule = moduleTableView.getSelectionModel().getSelectedIndex();
        if (indexModule <= -1) {
            return;
        }

        String courseName = col_CreateModuleName.getCellData(indexModule).toString();

        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select * from module WHERE subject = ?";
        usefulVariables.getAllModules = con.prepareStatement(sql);
        usefulVariables.getAllModules.setString(1, courseName);
        ResultSet resultAllModules = usefulVariables.getAllModules.executeQuery();


        while (resultAllModules.next()) {
            createModuleAddBranch.setValue(resultAllModules.getString("collegeBranch"));
            ModuleInCourse.setValue(resultAllModules.getString("course"));
            createModuleAddLecturer.setValue(resultAllModules.getString("idlecturer"));
            createModuleName.setText(resultAllModules.getString("subject"));
            createModuleAddHour.setText(resultAllModules.getString("classHour"));
            createModuleAddWeekDay.setValue(resultAllModules.getString("weekday"));

        }


    }

    @FXML
    private void toEditModule() throws Exception {

        String moduleSubject = col_CreateModuleName.getCellData(indexModule).toString();


        admin.editingModule(createModuleName.getText(), ModuleInCourse.getValue(), createModuleAddBranch.getValue(), createModuleAddWeekDay.getValue(),
                createModuleAddHour.getText(), createModuleAddLecturer.getValue(),
                moduleSubject);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Module edited");
        alert.show();

        createModuleAddWeekDay.getSelectionModel().clearSelection();
        createModuleAddLecturer.getSelectionModel().clearSelection();
        ModuleInCourse.getSelectionModel().clearSelection();
        createModuleName.clear();
        createModuleAddBranch.getSelectionModel().clearSelection();
        createModuleName.clear();

        refreshModule();

    }

    private void refreshModule() {

        col_CreateModuleName.setCellValueFactory(new PropertyValueFactory<>("moduleName"));
        col_createModuleCourse.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        col_createModuleLecturerid.setCellValueFactory(new PropertyValueFactory<>("idlecturer"));
        col_createModuleWeekday.setCellValueFactory(new PropertyValueFactory<>("weekday"));
        col_createModuleHour.setCellValueFactory(new PropertyValueFactory<>("classhour"));


        try {
            moduleTableView.getItems().setAll(getModuleList());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    // MANAGING COURSE YEAR TABLE VIEW

    @FXML
    private TableView<CourseYearMaster> courseYearTableView;

    @FXML
    private TableColumn<CourseYearMaster, String> col_createCourseYearCourse;

    @FXML
    private TableColumn<CourseYearMaster, Integer> col_createCourseYearYear;

    @FXML
    private TableColumn<CourseYearMaster, String> col_createCourseYearModule;


    private ObservableList<CourseYearMaster> getCourseYearList() throws ClassNotFoundException, SQLException {


        ObservableList<CourseYearMaster> list = FXCollections.observableArrayList();


        Class.forName("com.mysql.jdbc.Driver");

        String sql = "Select * from courseYear";

        usefulVariables.getAllCourseYear = con.prepareStatement(sql);
        ResultSet result = usefulVariables.getAllCourseYear.executeQuery();

        while (result.next()) {

            CourseYearMaster courseYearMaster = new CourseYearMaster();
            courseYearMaster.setYear(result.getInt("year"));
            courseYearMaster.setCourse(result.getString("course"));
            courseYearMaster.setModule(result.getString("moduleName"));


            list.add(courseYearMaster);


        }


        return list;


    }

    int indexCourseYear = -1;

    public void getSelectedCourseYear(javafx.scene.input.MouseEvent mouseEvent) throws ClassNotFoundException, SQLException, ParseException {

        indexCourseYear = courseYearTableView.getSelectionModel().getSelectedIndex();
        if (indexCourseYear <= -1) {
            return;
        }

        String moduleName = col_createCourseYearModule.getCellData(indexCourseYear).toString();

        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select * from courseYear WHERE moduleName = ?";
        usefulVariables.getAllCourseYear = con.prepareStatement(sql);
        usefulVariables.getAllCourseYear.setString(1, moduleName);
        ResultSet resultAllModules = usefulVariables.getAllCourseYear.executeQuery();


        while (resultAllModules.next()) {
            courseYearNameInput.setValue(resultAllModules.getString("course"));
            courseYearModuleInput.setValue(resultAllModules.getString("moduleName"));
            courseYearInput.setText(resultAllModules.getString("year"));


        }


    }

    private void refreshCourseYear() {

        col_createCourseYearCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        col_createCourseYearYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_createCourseYearModule.setCellValueFactory(new PropertyValueFactory<>("module"));


        try {
            courseYearTableView.getItems().setAll(getCourseYearList());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    // MANAGE PAYMENTS

    @FXML
    private TextField insertStudentIDPayments;

    @FXML
    private TableView<PaymentsMaster> tableViewPayments;

    @FXML
    private TableColumn<PaymentsMaster, Integer> col_paymentsID;

    @FXML
    private TableColumn<PaymentsMaster, String> col_paymentsStudentID;

    @FXML
    private TableColumn<PaymentsMaster, String> col_paymentsInstallment;

    @FXML
    private TableColumn<PaymentsMaster, String> col_paymentsDate;

    @FXML
    private Button btnAddPayment;

    @FXML
    private Label numberInstallmentsToAdd;


    @FXML
    private ObservableList<PaymentsMaster> getStudentPaymentList() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        ObservableList<PaymentsMaster> list = FXCollections.observableArrayList();


        String sql = "Select * from payments";
        usefulVariables.getPayments = con.prepareStatement(sql);
        ResultSet result = usefulVariables.getPayments.executeQuery();


        while (result.next()) {

            PaymentsMaster payments = new PaymentsMaster();
            payments.setPaymentid(result.getInt("idpayments"));
            payments.setStudentid(result.getString("idstudent"));
            payments.setPaidInstallment(result.getString("paidInstallment"));
            payments.setDate(result.getString("paymentDate"));
            list.add(payments);

        }

        return list;


    }

    int indexPaymentLog = -1;

    public void getSelectedPaymentLog(javafx.scene.input.MouseEvent mouseEvent) throws ClassNotFoundException, SQLException, ParseException {

        indexPaymentLog = tableViewPayments.getSelectionModel().getSelectedIndex();
        if (indexPaymentLog <= -1) {
            return;
        }

        String studentid = col_paymentsStudentID.getCellData(indexPaymentLog).toString();

        Class.forName("com.mysql.jdbc.Driver");


        String sql = "select idstudent from payments WHERE idstudent = ?";
        usefulVariables.getPayments = con.prepareStatement(sql);
        usefulVariables.getPayments.setString(1, studentid);
        ResultSet resultPaymentLog = usefulVariables.getPayments.executeQuery();


        while (resultPaymentLog.next()) {
            insertStudentIDPayments.setText(resultPaymentLog.getString("idstudent"));


        }


    }


    @FXML
    private void refreshPaymentLog() {


        col_paymentsID.setCellValueFactory(new PropertyValueFactory<>("paymentid"));
        col_paymentsStudentID.setCellValueFactory(new PropertyValueFactory<>("studentid"));
        col_paymentsInstallment.setCellValueFactory(new PropertyValueFactory<>("paidInstallment"));
        col_paymentsDate.setCellValueFactory(new PropertyValueFactory<>("date"));


        try {
            tableViewPayments.getItems().setAll(getStudentPaymentList());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }


    @FXML
    private void addPayments() throws ClassNotFoundException, SQLException {

        String idStudent = studentsInDebt.getValue();

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = myDateObj.format(myFormatObj);

        if (!admin.addPayment(idStudent, formattedDate)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR ALERT");
            alert.setHeaderText("No more payments needed for this student!!!");
            alert.show();
        } else {
            refreshPaymentLog();
        }


    }


    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }


}
