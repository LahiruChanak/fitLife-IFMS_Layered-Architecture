package lk.ijse.fitnesscentre.bo.custom;

import lk.ijse.fitnesscentre.bo.SuperBO;
import lk.ijse.fitnesscentre.entity.Purchase;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseBO extends SuperBO {

    String currentPurchaseId() throws SQLException;

//    boolean addPurchase(Purchase purchase) throws SQLException;

    double getTotalPurchase() throws SQLException;

    List<String> getPurchaseIds() throws SQLException;

}
