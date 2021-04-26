package sample;

import javafx.fxml.Initializable;
import sample.entities.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageStudent implements Initializable {

    private Student student;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }
}
