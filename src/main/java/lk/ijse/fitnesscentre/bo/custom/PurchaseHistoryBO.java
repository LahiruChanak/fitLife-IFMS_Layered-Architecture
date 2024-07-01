package lk.ijse.fitnesscentre.bo.custom;

import lk.ijse.fitnesscentre.bo.SuperBO;
import lk.ijse.fitnesscentre.dto.PurchaseHistoryDTO;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseHistoryBO extends SuperBO {

    List<PurchaseHistoryDTO> getAllHistory() throws SQLException;

}
