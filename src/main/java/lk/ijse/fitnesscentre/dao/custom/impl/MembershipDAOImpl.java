package lk.ijse.fitnesscentre.dao.custom.impl;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import lk.ijse.fitnesscentre.dao.custom.MembershipDAO;
import lk.ijse.fitnesscentre.entity.Membership;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MembershipDAOImpl implements MembershipDAO {

    @Override
    public boolean add(Membership entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO membership VALUES(?,?,?,?)", entity.getMembershipId(), entity.getMembershipType(), entity.getDescription(), entity.getMembershipFee());
    }

    @Override
    public boolean update(Membership entity) throws SQLException {
        return SQLUtil.execute("UPDATE membership SET membershipType = ?, description = ?, membershipFee = ? WHERE membershipId = ?", entity.getMembershipType(), entity.getDescription(), entity.getMembershipFee(), entity.getMembershipId());
    }

    @Override
    public boolean delete(String membershipId) throws SQLException {
        return SQLUtil.execute("DELETE FROM membership WHERE membershipId = ?", membershipId);
    }

    @Override
    public Membership searchById(String membershipId) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM membership WHERE membershipId = ?", membershipId);

        Membership membership = null;

        if (resultSet.next()) {
            String membership_id = resultSet.getString(1);
            String membershipType = resultSet.getString(2);
            String description = resultSet.getString(3);
            double membershipFee = Double.parseDouble(resultSet.getString(4));

            membership = new Membership(membership_id, membershipType, description, membershipFee);
        }
        return membership;
    }

    @Override
    public List<Membership> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM membership");

        List<Membership> membershipList = new ArrayList<>();
        while (resultSet.next()) {
            String membership_id = resultSet.getString(1);
            String membershipType = resultSet.getString(2);
            String description = resultSet.getString(3);
            double membershipFee = Double.parseDouble(resultSet.getString(4));

            Membership membership = new Membership(membership_id, membershipType, description, membershipFee);
            membershipList.add(membership);
        }
        return membershipList;
    }

    @Override
    public List<String> getIds() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT DISTINCT membershipId FROM membership");

        List<String> typeList = new ArrayList<>();

        while (resultSet.next()) {
            typeList.add(resultSet.getString("membershipId"));
        }
        return typeList;
    }

    @Override
    public String currentId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT membershipId FROM membership ORDER BY membershipId DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public Membership getFee(String membershipId) throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT membershipFee FROM membership WHERE membershipId = ?", membershipId);

        Membership membership = null;

        if (rst.next()) {
            double fee = rst.getDouble("membershipFee");

            membership = new Membership(fee);
        }
        return membership;
    }

//    public void monthlyFeeChart(BarChart<String, Number> barChart) throws SQLException {
//
//        ResultSet resultSet = SQLUtil.execute("SELECT CONCAT(MONTHNAME(date), '-', YEAR(date)) AS month, SUM(membershipFee) AS monthlyFee FROM payment GROUP BY month");
//
//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//
//        while (resultSet.next()) {
//            String month = resultSet.getString("month");
//            double monthlyFee = resultSet.getDouble("monthlyFee");
//            series.getData().add(new XYChart.Data<>(month, monthlyFee));
//        }
//        barChart.getData().add(series);
//
//        for(Node n:barChart.lookupAll(".default-color0.chart-bar")) {
//            n.setStyle("-fx-bar-fill: #ee5253;");
//        }
//    }

    @Override
    public String getEndDate(String membershipId) throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT membershipType FROM membership WHERE membershipId = ?", membershipId);

        if(rst.next()) {
            return rst.getString("membershipType");
        }
        return null;
    }


    //Not Implemented

    @Override
    public boolean add(List<Membership> entity) throws SQLException { return false; }

    @Override
    public int getCount() throws SQLException { return 0; }

    @Override
    public double getTotal() throws SQLException { return 0; }

}
