package lk.ijse.fitnesscentre.dao.custom;

import javafx.scene.chart.PieChart;
import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.entity.Product;
import lk.ijse.fitnesscentre.entity.PurchaseDetail;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO extends CrudDAO<Product> {

    boolean updateQty(List<PurchaseDetail> pdList) throws SQLException;

    boolean qtyUpdate(PurchaseDetail entity) throws SQLException;

}
