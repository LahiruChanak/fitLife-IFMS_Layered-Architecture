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
import lk.ijse.fitnesscentre.dao.custom.impl.CredentialDAOImpl;
import lk.ijse.fitnesscentre.util.GMailer;
import lk.ijse.fitnesscentre.util.Regex;
import lk.ijse.fitnesscentre.util.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class ForgotPWPaneController {

    public Pane forgotPWPane;

    public JFXButton btnFCancel;

    public JFXTextField txtUsername;
    public JFXTextField txtEmail;
    public JFXTextField txtOTP;
    public JFXPasswordField txtNewPW;
    public JFXPasswordField txtConfirmPW;

    CredentialDAOImpl credentialDAO = new CredentialDAOImpl();

    public void btnChangeOnAction(ActionEvent actionEvent) {
        String email = txtEmail.getText();
        String otp = txtOTP.getText();
        String newPw = txtNewPW.getText();
        String confirmPw = txtConfirmPW.getText();

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isTrue = credentialDAO.checkForgetPWCredential(email, otp, newPw, confirmPw);
            if (isTrue) {
                new Alert(Alert.AlertType.INFORMATION, "Password Changed successfully.").show();
                clearField();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Incorrect Login Details.").show();
        }
    }

    public void btnCancelOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/loginPane.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        forgotPWPane.getChildren().clear();
        forgotPWPane.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1 ), forgotPWPane);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    public void btnSendOnAction(ActionEvent actionEvent) throws Exception {
        GMailer gMailer = new GMailer();
        gMailer.OTPSend();

        txtOTP.requestFocus();
    }

    private void clearField() {
        txtUsername.clear();
        txtEmail.clear();
        txtOTP.clear();
        txtNewPW.clear();
        txtConfirmPW.clear();
    }

    public void txtUsernameOnAction(ActionEvent actionEvent) { txtEmail.requestFocus(); }

    public void txtEmailOnAction(ActionEvent actionEvent) { txtNewPW.requestFocus(); }

    public void txtNewPWOnAction(ActionEvent actionEvent) { txtConfirmPW.requestFocus(); }

    public void txtConfirmPWOnAction(ActionEvent actionEvent) throws Exception { btnSendOnAction(actionEvent); }

    public void txtOTPOnAction(ActionEvent actionEvent) { btnChangeOnAction(actionEvent); }

    public void txtUsernameOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.USERNAME, txtUsername); }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.EMAIL, txtEmail); }

    public void txtOTPOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.OTP, txtOTP); }

    public void txtPWOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.PASSWORD, txtNewPW); }

    public void txtConfirmPWOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.PASSWORD, txtConfirmPW); }

    public String isValid(){

        String message = "";

        if (!Regex.setTextColor(TextField.USERNAME,txtUsername))
            message += "Username must be between 3 and 16 characters long.\n\n";

        if (!Regex.setTextColor(TextField.EMAIL,txtEmail))
            message += "Enter valid email address.\n\n";

        if (!Regex.setTextColor(TextField.OTP,txtOTP))
            message += "OTP code must be 5 numbers.\n\n";

        if (!Regex.setTextColor(TextField.PASSWORD,txtNewPW))
            message += "Please enter password following type,\n" +
                    "\t* Contains at least one alphabetic character and one digit.\n" +
                    "\t* Include special characters such as @$!%*?&.\n" +
                    "\t* Password at least 8 characters long.";

        return message.isEmpty() ? null : message;
    }
}
