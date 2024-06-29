package lk.ijse.fitnesscentre.dao.custom.impl;

import lk.ijse.fitnesscentre.dao.custom.PurchaseHistoryDAO;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.entity.PurchaseHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryDAOImpl implements PurchaseHistoryDAO {

    @Override
    public List<PurchaseHistory> getAll() throws SQLException {
        String sql = "SELECT pu.purchaseId, p.productId, p.productName, m.memberId, m.memberName, qty, unitPrice,(unitPrice * qty) AS total, pd.date, pd.time FROM product p JOIN purchaseDetails pd ON p.productId = pd.productId JOIN purchase pu ON pu.purchaseId = pd.purchaseId JOIN member m ON pu.memberId = m.memberId";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<PurchaseHistory> historyList = new ArrayList<>();
        while (resultSet.next()) {
            String purchaseId = resultSet.getString("purchaseId");
            String productId = resultSet.getString("productId");
            String productName = resultSet.getString("productName");
            String memberId = resultSet.getString("memberId");
            String memberName = resultSet.getString("memberName");
            int qty = resultSet.getInt("qty");
            double unitPrice = resultSet.getDouble("unitPrice");
            double total = resultSet.getDouble("total");
            Date date = resultSet.getDate("date");
            Time time = resultSet.getTime("time");

            PurchaseHistory purchaseHistory = new PurchaseHistory(purchaseId,productId,productName,unitPrice,memberId,memberName,date,time,qty,total);
            historyList.add(purchaseHistory);
        }
        return historyList;
    }


    //Not Implemented

    @Override
    public boolean add(PurchaseHistory entity) throws SQLException { return false; }

    @Override
    public boolean add(List<PurchaseHistory> entity) throws SQLException { return false; }

    @Override
    public boolean update(PurchaseHistory entity) throws SQLException { return false; }

    @Override
    public boolean delete(String id) throws SQLException { return false; }

    @Override
    public String currentId() throws SQLException { return ""; }

    @Override
    public PurchaseHistory searchById(String id) throws SQLException { return null; }

    @Override
    public List<String> getIds() throws SQLException { return List.of(); }

    @Override
    public int getCount() throws SQLException { return 0; }

    @Override
    public double getTotal() throws SQLException { return 0; }

}
