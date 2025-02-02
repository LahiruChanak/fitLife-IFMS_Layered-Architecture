package lk.ijse.fitnesscentre.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import lk.ijse.fitnesscentre.dao.custom.ProductDAO;
import lk.ijse.fitnesscentre.entity.Product;
import lk.ijse.fitnesscentre.entity.PurchaseDetail;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public boolean add(Product entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO product VALUES(?,?,?,?,?,?)", entity.getProductId(), entity.getProductName(), entity.getUnitPrice(), entity.getQtyOnHand(), entity.getAddedDate(), entity.getAddedTime());
    }

    @Override
    public boolean update(Product product) throws SQLException {
        return SQLUtil.execute("UPDATE product SET productName = ?, unitPrice = ?, qtyOnHand = ?, addedDate = ?, addedTime = ? WHERE productId = ?", product.getProductName(), product.getUnitPrice(), product.getQtyOnHand(), product.getAddedDate(), product.getAddedTime(), product.getProductId());
    }

    @Override
    public boolean delete(String productId) throws SQLException {
        return SQLUtil.execute("DELETE FROM product WHERE productId = ?", productId);
    }

    @Override
    public Product searchById(String productId) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM product WHERE productId = ?", productId);

        Product product = null;

        if (resultSet.next()) {
            String product_id = resultSet.getString("productId");
            String productName = resultSet.getString("productName");
            double unitPrice = resultSet.getDouble("unitPrice");
            int qtyOnHand = resultSet.getInt("qtyOnHand");
            Date addedDate = Date.valueOf(resultSet.getString("addedDate"));
            Time addedTime = Time.valueOf(resultSet.getString("addedTime"));

            product = new Product(product_id, productName, unitPrice, qtyOnHand, addedDate, addedTime);
        }
        return product;
    }

    @Override
    public String currentId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT productId FROM product ORDER BY productId DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public List<Product> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM product");

        List<Product> productList = new ArrayList<>();
        while (resultSet.next()) {
            String productId = resultSet.getString(1);
            String productName = resultSet.getString(2);
            double unitPrice = resultSet.getDouble(3);
            int qtyOnHand = resultSet.getInt(4);
            Date addedDate = Date.valueOf(resultSet.getString(5));
            Time addedTime = Time.valueOf(resultSet.getString(6));

            Product product = new Product(productId, productName, unitPrice, qtyOnHand, addedDate, addedTime);
            productList.add(product);
        }
        return productList;
    }

    @Override
    public List<String> getIds() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT productId FROM product");

        List<String> idList = new ArrayList<>();

        while(resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    @Override
    public boolean updateQty(List<PurchaseDetail> pdList) throws SQLException {
        for (PurchaseDetail pd : pdList) {
            if(!qtyUpdate(pd)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean qtyUpdate(PurchaseDetail entity) throws SQLException {
        return SQLUtil.execute("UPDATE product SET qtyOnHand = qtyOnHand - ? WHERE productId = ?", entity.getQty(), entity.getProductId());
    }


    //Not Implemented

    @Override
    public boolean add(List<Product> entity) throws SQLException { return false; }

    @Override
    public int getCount() throws SQLException { return 0; }

    @Override
    public double getTotal() throws SQLException { return 0; }

}
