
package lk.ijse.fitnesscentre.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.fitnesscentre.dao.custom.impl.CredentialDAOImpl;
import lk.ijse.fitnesscentre.util.Regex;
import lk.ijse.fitnesscentre.util.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;

public class LoginPaneController {

    public Pane loginPane;

    public JFXButton btnLogin;
    public JFXButton btnRegister;

    public JFXPasswordField txtPassword;

    public JFXTextField txtUsername;

    public Text txtGreeting;

    public String username;

    CredentialDAOImpl credentialDAO = new CredentialDAOImpl();

    public void initialize() {
        setGreeting();
        txtUsername.requestFocus();
    }

//    public void getUserName() { username = txtUsername.getText(); }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        username = txtUsername.getText();
        String password = txtPassword.getText();

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            if (credentialDAO.checkLoginCredential(username, password)) {
                navigateToDashboard();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while checking login details.").show();
        }
    }

    public void linkForgotPwOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/forgotPWPane.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        loginPane.getChildren().clear();
        loginPane.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), loginPane);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/registerPane.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        loginPane.getChildren().clear();
        loginPane.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), loginPane);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    private void navigateToDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/main_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.loginPane.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    private void setGreeting() {
        LocalTime time = LocalTime.now();
        String greeting = (time.getHour() < 12) ? "Good Morning !" : (time.getHour() < 16) ? "Good Afternoon !" : "Good Evening !";
        txtGreeting.setText(greeting);
    }

    public void txtUsernameOnAction(ActionEvent actionEvent) { txtPassword.requestFocus(); }

    public void txtPWOnAction(ActionEvent actionEvent) throws IOException { btnLoginOnAction(actionEvent); }

    public void txtUsernameOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.USERNAME, txtUsername); }

    public void txtPWOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.PASSWORD, txtPassword);
    }

    public String isValid(){
        String message = "";

        if (!Regex.setTextColor(TextField.USERNAME,txtUsername))
            message += "Enter valid Username.\n\n";

        if (!Regex.setTextColor(TextField.PASSWORD,txtPassword))
            message += "Enter valid Password.";

        return message.isEmpty() ? null : message;
    }

}
