package lk.ijse.fitnesscentre.bo.custom.impl;

import lk.ijse.fitnesscentre.bo.custom.PurchaseHistoryBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.QueryDAO;
import lk.ijse.fitnesscentre.dao.custom.impl.QueryDAOImpl;
import lk.ijse.fitnesscentre.dto.PurchaseHistoryDTO;
import lk.ijse.fitnesscentre.entity.PurchaseHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryBOImpl implements PurchaseHistoryBO {

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY_DAO);

    @Override
    public List<PurchaseHistoryDTO> getAllHistory() throws SQLException {

        List<PurchaseHistoryDTO> allHistory = new ArrayList<>();
        List<PurchaseHistory> all = queryDAO.getPurchaseHistory();

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
                    ph.getTotal()
            ));
        }
        return allHistory;
    }

}
