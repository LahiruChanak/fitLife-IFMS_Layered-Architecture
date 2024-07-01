package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.PurchaseBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.PurchaseDAO;
import lk.ijse.fitnesscentre.dto.PurchaseDTO;
import lk.ijse.fitnesscentre.entity.Purchase;

import java.sql.SQLException;

public class PurchaseBOImpl implements PurchaseBO {

    PurchaseDAO purchaseDAO = (PurchaseDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PURCHASE);

    @Override
    public String currentPurchaseId() throws SQLException {
        return purchaseDAO.currentId();
    }

    @Override
    public boolean addPurchase(PurchaseDTO dto) throws SQLException {
        return purchaseDAO.add(new Purchase(dto.getPurchaseId(),
                dto.getPurchaseDate(),
                dto.getPurchaseTime(),
                dto.getTotalPrice(),
                dto.getMemberId())
        );
    }

    @Override
    public double getTotalPurchase() throws SQLException {
        return purchaseDAO.getTotal();
    }

}
