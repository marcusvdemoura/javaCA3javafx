package sample.entities;

import java.sql.SQLException;

public interface LecturerTools {

    void setGrade(Double grade, Student student) throws Exception;
    void createAssignment(String dueDate, String description, String moduleSubject, String lecturerId) throws ClassNotFoundException, SQLException;
    void deleteAssignment(Assignment assignment);
    void editAssignment(Assignment assignment, String dueDate, String description);
    void getStudentGrade(Student student);
    void createExam(String date);
    void editExam(Exam e, String date);

}
