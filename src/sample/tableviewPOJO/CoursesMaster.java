package sample.tableviewPOJO;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class CoursesMaster {
    private final SimpleStringProperty course = new SimpleStringProperty();
    private final SimpleStringProperty branch = new SimpleStringProperty();
    private final SimpleDoubleProperty price = new SimpleDoubleProperty();

    public CoursesMaster() {
    }

    public String getCourse() {
        return course.get();
    }

    public SimpleStringProperty courseProperty() {
        return course;
    }

    public void setCourse(String course) {
        this.course.set(course);
    }

    public String getBranch() {
        return branch.get();
    }

    public SimpleStringProperty branchProperty() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch.set(branch);
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
}
