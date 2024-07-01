package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.PurchaseDetailBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.PurchaseDAO;
import lk.ijse.fitnesscentre.dao.custom.PurchaseDetailDAO;
import lk.ijse.fitnesscentre.dto.PurchaseDetailDTO;
import lk.ijse.fitnesscentre.entity.Purchase;
import lk.ijse.fitnesscentre.entity.PurchaseDetail;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.SQLException;
import java.util.List;


public class PurchaseDetailBOImpl implements PurchaseDetailBO {

    PurchaseDetailDAO purchaseDetailDAO = (PurchaseDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PURCHASE_DETAILS);

    @Override
    public boolean add(List<PurchaseDetail> pdList) throws SQLException {
        for (PurchaseDetail pd : pdList) {
            if(!addPurchase(pd)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addPurchase(PurchaseDetail pd) throws SQLException {
        return purchaseDetailDAO.add(new PurchaseDetail(
                pd.getPurchaseId(),
                pd.getProductId(),
                pd.getDate(),
                pd.getTime(),
                pd.getQty())
        );
    }

}
