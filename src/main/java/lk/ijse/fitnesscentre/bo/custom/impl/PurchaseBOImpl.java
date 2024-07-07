package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.PurchaseBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.PurchaseDAO;
import lk.ijse.fitnesscentre.entity.Purchase;

import java.sql.SQLException;
import java.util.List;

public class PurchaseBOImpl implements PurchaseBO {

    PurchaseDAO purchaseDAO = (PurchaseDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PURCHASE);

    @Override
    public String currentPurchaseId() throws SQLException {
        return purchaseDAO.currentId();
    }

//    @Override
//    public boolean addPurchase(Purchase purchase) throws SQLException {
//        return purchaseDAO.add(new Purchase(
//                purchase.getPurchaseId(),
//                purchase.getPurchaseDate(),
//                purchase.getPurchaseTime(),
//                purchase.getTotalPrice(),
//                purchase.getMemberId())
//        );
//    }

    @Override
    public double getTotalPurchase() throws SQLException {
        return purchaseDAO.getTotal();
    }

    @Override
    public List<String> getPurchaseIds() throws SQLException {
        return purchaseDAO.getIds();
    }

}
