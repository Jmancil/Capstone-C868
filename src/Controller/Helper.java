package Controller;

import Database.JDBC;
import Database.Read;
import Model.Appointment;
import Model.Customer;
import Model.Users;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
Functions to help with error checking, login validation, and alerts
 */
public class Helper {
    /**
    Validates user login by parsing DB with Read.getUSersInfo
    Iterates user list for username and password match, returns true if found
    Returns false if not found
    @param  username user username for comparison
    @param  password user password for comparison
     */
    public static boolean loginValidation(String username, String password) {

        ObservableList<Users> users = Read.getUsersInfo();
        AtomicBoolean loginValid = new AtomicBoolean(false);
        /**
        @Lambda expression Compares user input for username and password and matches them against the database
        returns true or false for loginValidation
         */
        users.forEach((user)->{
            if(user.getUserName().equals(username)){
                if(user.getPassword().equals(password)){
                    loginValid.set(true);
                }
            }
        });
        return loginValid.get();
    }
    /**
    A simple confirmation alert that is used throughout program to alert user to confirmation messages
    @param  Confirmation
    @param  title
    @param  header
     */

    public static void AlertConfirmation(Alert.AlertType Confirmation, String title, String header) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        Optional<ButtonType> result = alert.showAndWait();

    }
    /**
        A simple confirmation alert error that is used throughout program to alert user to confirmation error messages
        @param  title
        @param  header
         */
    public static void AlertError(Alert.AlertType Error, String title, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        Optional<ButtonType> result = alert.showAndWait();
    }
/**
used to check date conversion for localdatetime
Reads LocalDateTime to a string
 */
    public static void checkDateConversion() {
        System.out.println("Create DATE TEST");
        String sql = "Select Create_date from countries";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
/**
Screen change method used throughout program to more easily change screens
@throws IOException Exception catches IO errors if exist
@param  actionEvent
@param  resourceName
@param  title
@param isResize
 */
    public static FXMLLoader screenChange(ActionEvent actionEvent, String resourceName, String title, boolean isResize) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Helper.class.getResource(resourceName));
        stage.setScene(new Scene(scene, 1200, 900));
        stage.setTitle(title);
        stage.setResizable(isResize);
        stage.show();
        return null;
    }
/**
customer input validation
checks if data entry is null returns boolean if true/false
@param  customer
 */
    public static boolean validateCustomer(Customer customer){
        boolean isFieldEmpty = true;
        if(customer.getName().equals("")){
            isFieldEmpty = false;
        }
        if(customer.getAddress().equals("")){
            isFieldEmpty = false;
        }
        if(customer.getPostal_code().equals("")){
            isFieldEmpty = false;
        }
        if(customer.getPhone().equals("")){
            isFieldEmpty = false;
        }
        return isFieldEmpty;
    }

    public static boolean validateAppointment(Appointment appointment){
        boolean isFieldEmpty = true;
//        if(appointment.getUserId() == 0){
//            isFieldEmpty = false;
//        }
//        if(appointment.getCustomerId() == 0){
//            isFieldEmpty = false;
//        }
        if(appointment.getTitle().equals("")){
            isFieldEmpty = false;
        }
        if(appointment.getDescription().equals("")){
            isFieldEmpty = false;
        }
        if(appointment.getLocation().equals("")){
            isFieldEmpty = false;
        }
        if(appointment.getStartDateTime().toString().equals("")){
            isFieldEmpty = false;
        }
        if(appointment.getEndDateTime().toString().equals("")){
            isFieldEmpty = false;
        }
        if(appointment.getType().equals("")){
            isFieldEmpty = false;
        }
        return isFieldEmpty;
    }
}



