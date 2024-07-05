package lk.ijse.fitnesscentre.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.fitnesscentre.dao.custom.CredentialDAO;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

import static lk.ijse.fitnesscentre.controller.CredentialFormController.correctOTP;

public class CredentialDAOImpl implements CredentialDAO {

    @Override
    public boolean checkLoginCredential(String username, String password) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT password FROM user WHERE username = ?", username);

        if (resultSet.next()) {
            String dbPassword = resultSet.getString(1);

            if (dbPassword.equals(password)) {
                return true;
            } else {
                new Alert(Alert.AlertType.ERROR, "Incorrect Password.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Incorrect Username.").show();
        }
        return false;
    }

    @Override
    public boolean checkForgetPWCredential(String email, String otp, String newPW, String confirmPW) throws SQLException {
        if (!newPW.equals(confirmPW)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match.").show();

            if (!correctOTP(otp)) {
                new Alert(Alert.AlertType.ERROR, "Incorrect OTP.").show();
                return false;
            }
            return false;
        }

        ResultSet resultSet = SQLUtil.execute("SELECT email FROM user", email, otp, newPW, confirmPW);

        if (resultSet.next()) {
            String dbEmail = resultSet.getString(1);

            if (dbEmail.equals(email)) {
                updatePassword(email, newPW);
                new Alert(Alert.AlertType.INFORMATION, "Password updated successfully.").show();
            } else {
                throw new SQLException("Incorrect Email.");
            }

        } else {
            throw new SQLException("Incorrect UseId.");
        }
        return false;
    }

    @Override
    public boolean checkRegisterCredential(String username, String name, String email, String otp, String password, String confirmPW) throws SQLException {
        if (!password.equals(confirmPW)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match.").show();

            if (!correctOTP(otp)) {
                new Alert(Alert.AlertType.ERROR, "Incorrect OTP.").show();
                return false;
            }
            return false;
        }
        return SQLUtil.execute("Insert INTO user VALUES (?, ?, ?, ?)", username, name, email, otp, password);
    }

    @Override
    public boolean updatePassword(String email, String newPW) throws SQLException {
        return SQLUtil.execute("UPDATE user SET password = ? WHERE email = ?", email, newPW);
    }

}
