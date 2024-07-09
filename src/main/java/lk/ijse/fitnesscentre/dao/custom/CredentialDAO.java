package lk.ijse.fitnesscentre.dao.custom;

import lk.ijse.fitnesscentre.dao.SuperDAO;

import java.sql.SQLException;


public interface CredentialDAO extends SuperDAO {

    boolean checkLoginCredential(String username, String password) throws SQLException;

    boolean checkForgetPWCredential(String email, String otp, String newPW, String confirmPW) throws SQLException;

    boolean checkRegisterCredential(String username, String name, String email, String otp, String password, String confirmPW) throws SQLException;

    boolean updatePassword(String email, String newPW) throws SQLException;

    String getUsrName(String username) throws SQLException;

}
