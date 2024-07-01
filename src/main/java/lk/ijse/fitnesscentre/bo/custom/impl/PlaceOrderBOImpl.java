package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.entity.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderBOImpl {

    PurchaseBOImpl purchaseDAO = new PurchaseBOImpl();
    PurchaseDetailBOImpl purchaseDetailDAO = new PurchaseDetailBOImpl();
    ProductBOImpl productDAO = new ProductBOImpl();

    public boolean placeOrder(PlaceOrder po) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isPurchaseSaved = purchaseDAO.addPurchase(po.getPurchase());
            if (isPurchaseSaved) {
                boolean isPurchaseDetailSaved = purchaseDetailDAO.add(po.getPdList());
                if (isPurchaseDetailSaved) {
                    boolean isProductQtyUpdate = productDAO.updateProductQty(po.getPdList());
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
