package lk.ijse.fitnesscentre.bo.custom.impl;

import javafx.scene.chart.PieChart;
import lk.ijse.fitnesscentre.bo.custom.ProductBO;
import lk.ijse.fitnesscentre.dao.DAOFactory;
import lk.ijse.fitnesscentre.dao.custom.impl.ProductDAOImpl;
import lk.ijse.fitnesscentre.dto.ProductDTO;
import lk.ijse.fitnesscentre.dto.PurchaseDetailDTO;
import lk.ijse.fitnesscentre.entity.Product;
import lk.ijse.fitnesscentre.entity.PurchaseDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductBOImpl implements ProductBO {

    ProductDAOImpl productDAO = (ProductDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);

    @Override
    public boolean addProduct(ProductDTO dto) throws SQLException {
        return productDAO.add(new Product(dto.getProductId(),
                dto.getProductName(),
                dto.getUnitPrice(),
                dto.getQtyOnHand(),
                dto.getAddedDate(),
                dto.getAddedTime())
        );
    }

    @Override
    public boolean updateProduct(ProductDTO dto) throws SQLException {
        return productDAO.update(new Product(dto.getProductId(),
                dto.getProductName(),
                dto.getUnitPrice(),
                dto.getQtyOnHand(),
                dto.getAddedDate(),
                dto.getAddedTime())
        );
    }

    @Override
    public boolean deleteProduct(String productId) throws SQLException {
        return productDAO.delete(productId);
    }

    @Override
    public Product searchByProductId(String productId) throws SQLException {
        return productDAO.searchById(productId);
    }

    @Override
    public String currentProductId() throws SQLException {
        return productDAO.currentId();
    }

    @Override
    public List<ProductDTO> getAllProducts() throws SQLException {

        List<ProductDTO> allProducts = new ArrayList<>();
        List<Product> all = productDAO.getAll();

        for (Product p : all) {
            allProducts.add(new ProductDTO(p.getProductId(),
                    p.getProductName(),
                    p.getUnitPrice(),
                    p.getQtyOnHand(),
                    p.getAddedDate(),
                    p.getAddedTime())
            );
        }
        return allProducts;
    }

    @Override
    public List<String> getProductIds() throws SQLException {
        return productDAO.getIds();
    }

    @Override
    public boolean updateProductQty(List<PurchaseDetail> pdList) throws SQLException {
        return productDAO.updateQty(pdList);
    }

    public boolean ProductQty(PurchaseDetail pd) throws SQLException {
        return productDAO.qtyUpdate(pd);
    }

    @Override
    public void productSales(PieChart pieChart) throws SQLException {
        productDAO.productSales(pieChart);
    }

}
