package lk.ijse.fitnesscentre.dao.custom.impl;

import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.entity.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderDAOImpl {

    PurchaseDAOImpl purchaseDAO = new PurchaseDAOImpl();
    PurchaseDetailDAOImpl purchaseDetailDAO = new PurchaseDetailDAOImpl();
    ProductDAOImpl productDAO = new ProductDAOImpl();

    public boolean placeOrder(PlaceOrder po) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isPurchaseSaved = purchaseDAO.add(po.getPurchase());
            if (isPurchaseSaved) {
                boolean isPurchaseDetailSaved = purchaseDetailDAO.add(po.getPdList());
                if (isPurchaseDetailSaved) {
                    boolean isProductQtyUpdate = productDAO.updateQty(po.getPdList());
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
