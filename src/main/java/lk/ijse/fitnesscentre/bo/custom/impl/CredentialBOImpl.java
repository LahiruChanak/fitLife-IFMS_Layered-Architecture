package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.CredentialBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.CredentialDAO;

import java.sql.SQLException;


public class CredentialBOImpl implements CredentialBO {

    public static String userName;

    CredentialDAO credentialDAO = (CredentialDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CREDENTIAL);

    @Override
    public boolean checkLoginCredential(String username, String password) throws SQLException {
        return credentialDAO.checkLoginCredential(username, password);
    }

    @Override
    public boolean checkForgetPWCredential(String email, String otp, String newPW, String confirmPW) throws SQLException {
        return credentialDAO.checkForgetPWCredential(email, otp, newPW, confirmPW);
    }

    @Override
    public boolean checkRegisterCredential(String username, String name, String email, String otp, String password, String confirmPW) throws SQLException {
        return credentialDAO.checkRegisterCredential(username, name, email, otp, password, confirmPW);
    }

    @Override
    public boolean updatePassword(String email, String newPW) throws SQLException {
        return credentialDAO.updatePassword(email, newPW);
    }

    @Override
    public String getUsrName(String username) throws SQLException {
        return credentialDAO.getUsrName(username);
    }

}
