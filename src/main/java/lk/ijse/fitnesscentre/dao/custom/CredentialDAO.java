package lk.ijse.fitnesscentre.dao.custom;

import java.sql.SQLException;

//import static lk.ijse.fitnesscentre.util.GMailer.generateRandomNumber;

public interface CredentialDAO {

    boolean checkLoginCredential(String username, String password) throws SQLException;

    boolean checkForgetPWCredential(String email, String otp, String newPW, String confirmPW) throws SQLException;

    boolean checkRegisterCredential(String username, String name, String email, String otp, String password, String confirmPW) throws SQLException;

    boolean updatePassword(String email, String newPW) throws SQLException;

    String getUserName() throws SQLException;

}
