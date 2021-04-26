package sample.entities;

public class Exam {

    private String date;
    private String lecturerName;
    private Module module;


    public Exam(String date, String lecturerName, Module module) {
        this.date = date;
        this.lecturerName = lecturerName;
        this.module = module;
    }

    public String getDate() {
        return date;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public Module getModule() {
        return module;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
