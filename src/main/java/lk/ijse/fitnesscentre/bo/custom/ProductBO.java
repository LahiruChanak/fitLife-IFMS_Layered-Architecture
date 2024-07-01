package lk.ijse.fitnesscentre.bo.custom;

import javafx.scene.chart.PieChart;
import lk.ijse.fitnesscentre.bo.SuperBO;
import lk.ijse.fitnesscentre.dao.CrudDAO;
import lk.ijse.fitnesscentre.dto.ProductDTO;
import lk.ijse.fitnesscentre.dto.PurchaseDetailDTO;
import lk.ijse.fitnesscentre.entity.Product;
import lk.ijse.fitnesscentre.entity.PurchaseDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductBO extends SuperBO {

    boolean addProduct(ProductDTO dto) throws SQLException;

    boolean updateProduct(ProductDTO dto) throws SQLException;

    boolean deleteProduct(String productId) throws SQLException;

    Product searchByProductId(String productId) throws SQLException;

    String currentProductId() throws SQLException;

    List<ProductDTO> getAllProducts() throws SQLException;

    List<String> getProductIds() throws SQLException;

    boolean updateProductQty(List<PurchaseDetail> pdList) throws SQLException;

    boolean ProductQty(PurchaseDetail dto) throws SQLException;

    void productSales(PieChart pieChart) throws SQLException;

}
