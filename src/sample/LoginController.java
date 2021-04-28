package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.entities.*;

import sample.queriesSQL.*;




import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.tableviewPOJO.UserLoginPOJO;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {



    private LoginQueries loginQueries = new LoginQueries();
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Admin admin;
    private Student student;
    private Lecturer lecturer;
    
    private static String userId;

    @FXML
    private ChoiceBox<String> myChoiceBox = new ChoiceBox<>();
    @FXML
    private TextField idTextField;
    @FXML
    private PasswordField passwordTextField;

    private String loginOptions[] = {"Admin", "Student", "Lecturer"};

    @FXML
    private Button loginButton = new Button();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        myChoiceBox.getItems().addAll(loginOptions);

    }

    @FXML
    private void toLogin(ActionEvent event) throws Exception {

        String userId = idTextField.getText();
        String password = passwordTextField.getText();


        if (myChoiceBox.getValue().toString().equals("Admin")) {

            admin = null;


            ResultSet result = loginQueries.loginAdmin(userId, password);
            while (result.next()) {
                this.admin = new Admin(result.getString("firstName"), result.getString("lastName"),
                        result.getString("gender"), result.getString("phoneNumber"), result.getString("dob"),
                        result.getString("emailAddress"), result.getString("adminID"), result.getString("password"));
            }


            if (admin.getPassword().equals(password) && admin.getId().equals(userId)) {


                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminMainPage.fxml"));
                Parent root = loader.load();

                AdminMainPage adminMainPage = loader.getController();
                adminMainPage.setAdmin(admin);

//                LoginUser.userId = admin.getFirstName();
                System.out.println("Admin name: " + admin.getFirstName());
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } else {
                throw new Exception("Wrong password");
            }


        } else if (myChoiceBox.getValue().toString().equals("Student")) {
            student = null;


            ResultSet result = loginQueries.loginStudent(userId, password);

            while (result.next()) {
                this.student = new Student(result.getString("firstName"), result.getString("lastName"),
                        result.getString("gender"), result.getString("phone"), result.getString("dob"),
                        result.getString("emailAddress"), result.getString("idstudent"), result.getString("password"),
                        result.getString("collegeBranch"), result.getString("course"), result.getBoolean("isPaidFull"),
                        result.getString("courseYear"));
            }


            if (student.getPassword().equals(password) && student.getId().equals(userId)) {


                UserLoginPOJO.setUserFullName(student.getFirstName()+" "+ student.getLastName());
                UserLoginPOJO.setUserID(student.getId());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage_Student.fxml"));
                Parent root = loader.load();

                MainPageStudent mainPageStudent = loader.getController();
                mainPageStudent.setStudent(student);
                System.out.println("Student name: " + student.getFirstName());
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } else {
                throw new Exception("Wrong password");
            }
        } else if (myChoiceBox.getValue().toString().equals("Lecturer")) {
            lecturer = null;


            ResultSet result = loginQueries.loginLecturer(userId, password);

            while (result.next()) {
                this.lecturer = new Lecturer(result.getString("firstName"), result.getString("lastName"),
                        result.getString("gender"), result.getString("phone"), result.getString("dob"),
                        result.getString("emailAddress"), result.getString("idlecturer"), result.getString("password"));
            }


            if (lecturer.getPassword().equals(password) && lecturer.getId().equals(userId)) {


                UserLoginPOJO.setUserID(lecturer.getId());
                UserLoginPOJO.setUserFullName(lecturer.getFirstName() + " " + lecturer.getLastName());


                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage_Lecturer.fxml"));
                Parent root = loader.load();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                System.setProperty("userId", lecturer.getId());
                scene = new Scene(root);
                stage.setScene(scene);

                stage.show();

            } else {
                throw new Exception("Wrong password");
            }
        }
    }




    public Admin getAdmin() {
        return admin;
    }

    private void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Student getStudent() {
        return student;
    }

    private void setStudent(Student student) {
        this.student = student;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    private void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }



}

