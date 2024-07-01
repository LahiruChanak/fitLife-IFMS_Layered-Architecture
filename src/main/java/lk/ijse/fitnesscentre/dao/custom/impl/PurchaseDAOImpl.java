package lk.ijse.fitnesscentre.dao.custom.impl;

import lk.ijse.fitnesscentre.dao.custom.PurchaseDAO;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.entity.Purchase;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.*;
import java.util.List;

public class PurchaseDAOImpl implements PurchaseDAO {


    public String currentId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT purchaseId FROM purchase ORDER BY purchaseId desc LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public boolean add(Purchase entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO purchase VALUES(?, ?, ?, ?, ?)", entity.getPurchaseId(), entity.getPurchaseDate(), entity.getPurchaseTime(), entity.getTotalPrice(), entity.getMemberId());
    }

    public double getTotal() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT SUM(totalPrice) AS totalPurchase FROM purchase");

        double totalPurchase = 0;

        if (resultSet.next()) {
            totalPurchase = resultSet.getDouble("totalPurchase");
        }
        return totalPurchase;
    }


    //Not Implemented

    @Override
    public List<String> getIds() throws SQLException { return List.of(); }

    @Override
    public int getCount() throws SQLException { return 0; }

    @Override
    public boolean update(Purchase entity) throws SQLException { return false; }

    @Override
    public boolean delete(String id) throws SQLException { return false; }

    @Override
    public List<Purchase> getAll() throws SQLException { return null; }

    @Override
    public boolean add(List<Purchase> entity) throws SQLException { return false; }

    @Override
    public Purchase searchById(String id) throws SQLException { return null; }

}
