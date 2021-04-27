package sample.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student extends Person{

    private HashMap<String, Double> grades = new HashMap<String, Double>();
    private ArrayList<Module> timetable = new ArrayList<>();
    private ArrayList<Double> fees = new ArrayList<>();
    private CollegeBranch collegeBranch;
    private Course course;
    private boolean isPaidFull;
    private ArrayList<Assignment> listOfAssignments = new ArrayList<>();
    private ArrayList<Exam> listOfExams = new ArrayList<>();
    private String courseYear;




    public Student(String firstName, String lastName, String gender, String phone, String dob,
                   String emailAddress, String id, String password, String collegeBranchName,
                   String courseName, boolean isPaidFull, String courseYear) {
        super(firstName, lastName, gender, phone, dob, emailAddress, id, password);
        this.collegeBranch = collegeBranch;
        this.isPaidFull = isPaidFull;
        this.courseYear = courseYear;
    }

    public ArrayList<Assignment> getListOfAssignments() {
        return listOfAssignments;
    }

    public CollegeBranch getCollegeBranch() {
        return collegeBranch;
    }

    public HashMap<String, Double> getGrades() {
        return grades;
    }

    public ArrayList<Double> getFees() {
        return fees;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean isPaidFull() {
        return isPaidFull;
    }

    public ArrayList<Module> getTimetable() {
        return timetable;
    }

    public ArrayList<Exam> getListOfExams() {
        return listOfExams;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }



    // PRINTING TIMETABLE

    public void printTimetable(){
        System.out.println("--- TIMETABLE ---");

        for (int i = 0; i < getTimetable().size(); i++){
            System.out.println(getTimetable().get(i).getSubject()+": on " + getTimetable().get(i).getWeekDay()+ " at " + getTimetable().get(i).getClassHour());
            System.out.println("Lecturer: " + getTimetable().get(i).getLecturer().getFirstName()+" "+ getTimetable().get(i).getLecturer().getLastName());
        }
        System.out.println();
    }

    // PRINTING GRADES
    public void printGrades(){

        System.out.println(getFirstName() + " " + getLastName() + " grades:");
        System.out.println("----------------------");

        for (Map.Entry<String, Double> entry : getGrades().entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
    }

    // FEES MANAGEMENT


    public void printFees(){

        System.out.println("COLLEGE FEES: ");

        int count = 0;
        for (Double a : getFees()){
            if (a!=0){
                count++;
            }
        }

        if(count == 0){
            System.out.println("ALL FEES ARE PAID!");
        } else {
            for (int i = 1; i<= getFees().size(); i++){
                System.out.println("Fee " + i + ": €" + getFees().get(i-1));
            }
        }

    }

    // PRINTING and MANAGING ASSIGNMENTS

    public void printAssignments(){

        System.out.println("ASSIGNMENT LIST AND DATES\n");

        for (Assignment ass : getListOfAssignments()){
            System.out.println("Subject: " + ass.getModule().getSubject());
            System.out.println("Due date: " + ass.getDueDate());
            System.out.println("Description: "+ ass.getDescription());
            System.out.println("======================================");
        }

    }



}
