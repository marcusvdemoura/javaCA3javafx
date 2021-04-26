package sample.entities;

public class Assignment {

    private String dueDate;
    private String description;
    private String lecturerName;
    private Module module;



    public Assignment(String dueDate, String description, String lecturerName, Module module) {
        this.dueDate = dueDate;
        this.description = description;
        this.lecturerName = lecturerName;
        this.module = module;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public Module getModule() {
        return module;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

