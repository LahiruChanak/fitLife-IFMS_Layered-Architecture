package lk.ijse.fitnesscentre.bo.custom;

import lk.ijse.fitnesscentre.bo.SuperBO;
import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.dto.PurchaseDetailDTO;
import lk.ijse.fitnesscentre.entity.PurchaseDetail;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseDetailBO extends SuperBO {

    boolean add(List<PurchaseDetail> entity) throws SQLException;

    boolean addPurchase(PurchaseDetail dto) throws SQLException;

}
