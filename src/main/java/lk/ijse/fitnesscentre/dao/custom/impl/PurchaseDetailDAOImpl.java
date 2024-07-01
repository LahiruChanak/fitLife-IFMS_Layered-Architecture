package lk.ijse.fitnesscentre.dao.custom.impl;

import lk.ijse.fitnesscentre.dao.custom.PurchaseDetailDAO;
import lk.ijse.fitnesscentre.entity.PurchaseDetail;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.SQLException;
import java.util.List;

public class PurchaseDetailDAOImpl implements PurchaseDetailDAO {

    public boolean add(List<PurchaseDetail> pdList) throws SQLException {
        for (PurchaseDetail pd : pdList) {
            if(!add(pd)) {
                return false;
            }
        }
        return true;
    }

    public boolean add(PurchaseDetail entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO purchaseDetails VALUES(?, ?, ?, ?, ?)", entity.getPurchaseId(), entity.getProductId(), entity.getDate(), entity.getTime(), entity.getQty());
    }


    //Not Implemented

    @Override
    public boolean update(PurchaseDetail entity) throws SQLException { return false; }

    @Override
    public boolean delete(String id) throws SQLException { return false; }

    @Override
    public List<PurchaseDetail> getAll() throws SQLException { return List.of(); }

    @Override
    public String currentId() throws SQLException { return ""; }

    @Override
    public PurchaseDetail searchById(String id) throws SQLException { return null; }

    @Override
    public List<String> getIds() throws SQLException { return List.of(); }

    @Override
    public int getCount() throws SQLException { return 0; }

    @Override
    public double getTotal() throws SQLException { return 0; }

}
