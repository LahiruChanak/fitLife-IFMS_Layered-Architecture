package lk.ijse.fitnesscentre.dao.custom.impl;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import lk.ijse.fitnesscentre.dao.custom.PaymentDAO;
import lk.ijse.fitnesscentre.entity.Payment;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public boolean add(Payment entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO payment VALUES(?,?,?,?,?,?)", entity.getPaymentId(), entity.getPaymentMethod(), entity.getMembershipFee(), entity.getDate(), entity.getTime(), entity.getMembershipId());
    }

    @Override
    public Payment searchById(String paymentId) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM payment WHERE paymentId = ?", paymentId);

        Payment payment = null;

        if (resultSet.next()) {
            String payment_id = resultSet.getString(1);
            String paymentMethod = resultSet.getString(2);
            Double membershipFee = Double.valueOf(resultSet.getString(3));
            Date date = Date.valueOf(resultSet.getString(4));
            Time time = Time.valueOf(resultSet.getString(5));
            String membershipId = resultSet.getString(6);

            payment = new Payment(payment_id, paymentMethod, membershipFee, date, time, membershipId);
        }
        return payment;
    }

    @Override
    public List<Payment> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM payment");

        List<Payment> paymentList = new ArrayList<>();
        while (resultSet.next()) {
            String paymentId = resultSet.getString(1);
            String paymentMethod = resultSet.getString(2);
            Double membershipFee = Double.valueOf(resultSet.getString(3));
            Date date = Date.valueOf(resultSet.getString(4));
            Time time = Time.valueOf(resultSet.getString(5));
            String membershipId = resultSet.getString(6);

            Payment payment = new Payment(paymentId, paymentMethod, membershipFee, date, time, membershipId);
            paymentList.add(payment);
        }
        return paymentList;
    }

    @Override
    public double getTotal() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT SUM(membershipFee) AS totalFee FROM payment");

        double totalfee = 0;

        if (resultSet.next()) {
            totalfee = resultSet.getDouble("totalFee");
        }
        return totalfee;
    }

    @Override
    public String currentId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT paymentId FROM payment ORDER BY paymentId DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public void monthlyFeeChart(BarChart<String, Number> barChart) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT CONCAT(MONTHNAME(date), '-', YEAR(date)) AS month, SUM(membershipFee) AS monthlyFee FROM payment GROUP BY month");

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        while (resultSet.next()) {
            String month = resultSet.getString("month");
            double monthlyFee = resultSet.getDouble("monthlyFee");
            series.getData().add(new XYChart.Data<>(month, monthlyFee));
        }
        barChart.getData().add(series);

        for(Node n:barChart.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: #ee5253;");
        }
    }


    //Not Implemented

    @Override
    public boolean add(List<Payment> entity) throws SQLException { return false; }

    @Override
    public boolean update(Payment entity) throws SQLException { return false; }

    @Override
    public boolean delete(String id) throws SQLException { return false; }

    @Override
    public List<String> getIds() throws SQLException { return null; }

    @Override
    public int getCount() throws SQLException { return 0; }

}
