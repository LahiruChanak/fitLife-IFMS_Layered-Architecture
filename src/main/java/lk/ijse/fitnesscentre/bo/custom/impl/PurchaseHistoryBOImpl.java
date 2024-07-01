package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.PurchaseHistoryBO;
import lk.ijse.fitnesscentre.dao.custom.impl.PurchaseHistoryDAOImpl;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.dto.PurchaseHistoryDTO;
import lk.ijse.fitnesscentre.entity.PurchaseHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryBOImpl implements PurchaseHistoryBO {

    @Override
    public List<PurchaseHistoryDTO> getAllHistory() throws SQLException {

        List<PurchaseHistoryDTO> allHistory = new ArrayList<>();
        List<PurchaseHistory> all = new PurchaseHistoryDAOImpl().getAll();

        for (PurchaseHistory ph : all) {
            allHistory.add(new PurchaseHistoryDTO(
                    ph.getPurchaseId(),
                    ph.getProductId(),
                    ph.getProductName(),
                    ph.getUnitPrice(),
                    ph.getMemberId(),
                    ph.getMemberName(),
                    ph.getDate(),
                    ph.getTime(),
                    ph.getQty(),
                    ph.getTotal())
            );
        }
        return allHistory;
    }

}
