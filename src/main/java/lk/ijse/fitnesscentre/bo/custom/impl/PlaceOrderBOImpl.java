package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.PlaceOrderBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.ProductDAO;
import lk.ijse.fitnesscentre.dao.custom.PurchaseDAO;
import lk.ijse.fitnesscentre.dao.custom.PurchaseDetailDAO;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.dto.PlaceOrderDTO;
import lk.ijse.fitnesscentre.entity.Purchase;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    PurchaseDAO purchaseDAO = (PurchaseDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PURCHASE);
    ProductDAO productDAO = (ProductDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);
    PurchaseDetailDAO purchaseDetailDAO = (PurchaseDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PURCHASE_DETAILS);


    public boolean placeOrder(PlaceOrderDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        Purchase purchase = new Purchase(dto.getPurchaseId(), dto.getPurchaseDate(), dto.getPurchaseTime(), dto.getTotalPrice(), dto.getMemberId());

        try {
            boolean isPurchaseSaved = purchaseDAO.add(purchase);
            if (isPurchaseSaved) {
                boolean isPurchaseDetailSaved = purchaseDetailDAO.add(dto.getPdList());
                if (isPurchaseDetailSaved) {
                    boolean isProductQtyUpdate = productDAO.updateQty(dto.getPdList());
                    if (isProductQtyUpdate) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

}
