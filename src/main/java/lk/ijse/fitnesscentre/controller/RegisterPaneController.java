package lk.ijse.fitnesscentre.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.fitnesscentre.bo.BOFactory;
import lk.ijse.fitnesscentre.bo.custom.CredentialBO;
import lk.ijse.fitnesscentre.util.GMailer;
import lk.ijse.fitnesscentre.util.Regex;
import lk.ijse.fitnesscentre.util.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class RegisterPaneController {
    public Pane registerPane;
    public JFXButton btnSignUp;
    public JFXButton btnCancel;
    public JFXTextField txtUsername;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtOTP;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtConfirmPW;

    CredentialBO credentialBO = (CredentialBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CREDENTIAL);

    public void btnSignUpOnAction(ActionEvent actionEvent) throws IOException {

        String username = txtUsername.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String otp = txtOTP.getText();
        String password = txtPassword.getText();
        String confirmPW = txtConfirmPW.getText();

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isTrue = credentialBO.checkRegisterCredential(username, name, email, otp, password, confirmPW);
            if (isTrue) {
                new Alert(Alert.AlertType.INFORMATION, "Registration Succussfully.").show();
                clearField();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Incorrect Register Details.").show();
        }
    }

    public void btnCancelOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/loginPane.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        registerPane.getChildren().clear();
        registerPane.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), registerPane);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    public void btnSendOnAction(ActionEvent actionEvent) throws Exception {
        GMailer gMailer = new GMailer();
        gMailer.sendMail(txtEmail.getText());

        txtOTP.requestFocus();
    }

    private void clearField() {
        txtUsername.clear();
        txtName.clear();
        txtEmail.clear();
        txtOTP.clear();
        txtPassword.clear();
        txtConfirmPW.clear();
    }

    public void txtUsernameOnAction(ActionEvent actionEvent) { txtName.requestFocus(); }

    public void txtNameOnAction(ActionEvent actionEvent) { txtEmail.requestFocus(); }

    public void txtEmailOnAction(ActionEvent actionEvent) { txtPassword.requestFocus(); }

    public void txtOTPOnAction(ActionEvent actionEvent) throws IOException { btnSignUpOnAction(actionEvent); }

    public void txtPasswordOnAction(ActionEvent actionEvent) { txtConfirmPW.requestFocus(); }

    public void txtConfirmPWOnAction(ActionEvent actionEvent) throws Exception { btnSendOnAction(actionEvent); }

    public void txtUsernameOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.USERNAME, txtUsername); }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.NAME, txtName); }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.EMAIL, txtEmail); }

    public void txtOTPOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.OTP, txtOTP); }

    public void txtPWOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.PASSWORD, txtPassword); }

    public void setTxtConfirmPWOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.PASSWORD, txtConfirmPW); }

    public String isValid(){

        String message = "";

        if (!Regex.setTextColor(TextField.USERNAME,txtUsername))
            message += "Username must be between 3 and 16 characters long.\n\n";

        if (!Regex.setTextColor(TextField.NAME,txtName))
            message += "Name must be at least 3 letters.\n\n";

        if (!Regex.setTextColor(TextField.EMAIL,txtEmail))
            message += "Enter valid email address.\n\n";

        if (!Regex.setTextColor(TextField.OTP,txtOTP))
            message += "OTP code must be 5 numbers.\n\n";

        if (!Regex.setTextColor(TextField.PASSWORD,txtPassword))
            message += "Please enter password following type,\n" +
                    "\t* Contains at least one alphabetic character and one digit.\n" +
                    "\t* Include special characters such as @$!%*?&.\n" +
                    "\t* Password at least 8 characters long.";

        return message.isEmpty() ? null : message;
    }
}
