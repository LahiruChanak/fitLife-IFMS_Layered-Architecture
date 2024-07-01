package lk.ijse.fitnesscentre.bo.custom;

import lk.ijse.fitnesscentre.bo.SuperBO;
import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.dto.PurchaseDTO;
import lk.ijse.fitnesscentre.entity.Purchase;

import java.sql.SQLException;

public interface PurchaseBO extends SuperBO {

    String currentPurchaseId() throws SQLException;

    boolean addPurchase(PurchaseDTO dto) throws SQLException;

    double getTotalPurchase() throws SQLException;

}
