package sample.tableviewPOJO;

import javafx.beans.property.SimpleStringProperty;

public class UserLoginPOJO {


    private static SimpleStringProperty userID = new SimpleStringProperty();
    private static SimpleStringProperty userFullName = new SimpleStringProperty();

    public static String getUserID() {
        return userID.get();
    }

    public static SimpleStringProperty userIDProperty() {
        return userID;
    }

    public static void setUserID(String userID) {
        UserLoginPOJO.userID.set(userID);
    }

    public static String getUserFullName() {
        return userFullName.get();
    }

    public static SimpleStringProperty userFullNameProperty() {
        return userFullName;
    }

    public static void setUserFullName(String userFullName) {
        UserLoginPOJO.userFullName.set(userFullName);
    }
}
