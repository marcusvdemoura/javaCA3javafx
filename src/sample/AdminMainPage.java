package sample;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.entities.*;

import sample.entities.Module;
import sample.queriesSQL.UsefulVariables;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
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


    public AdminMainPage() throws SQLException {
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


        courseYearInput.clear();
        courseYearNameInput.getSelectionModel().clearSelection();
        courseYearModuleInput.getSelectionModel().clearSelection();


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

        courseYearInput.clear();
        courseYearNameInput.getSelectionModel().clearSelection();
        courseYearModuleInput.getSelectionModel().clearSelection();


    }



    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }


}
