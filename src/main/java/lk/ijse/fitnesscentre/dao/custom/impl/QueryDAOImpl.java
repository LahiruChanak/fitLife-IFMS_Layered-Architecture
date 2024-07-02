package lk.ijse.fitnesscentre.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import lk.ijse.fitnesscentre.dao.custom.QueryDAO;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.entity.PurchaseHistory;
import lk.ijse.fitnesscentre.entity.ScheduleDetails;
import lk.ijse.fitnesscentre.entity.TrainerDetails;
import lk.ijse.fitnesscentre.util.SQLUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public List<PurchaseHistory> getPurchaseHistory() throws SQLException {

        String sql = "SELECT pu.purchaseId, p.productId, p.productName, m.memberId, m.memberName, pd.qty, p.unitPrice,(unitPrice * qty) AS total, pd.date, pd.time FROM product p JOIN purchaseDetails pd ON p.productId = pd.productId JOIN purchase pu ON pu.purchaseId = pd.purchaseId JOIN member m ON pu.memberId = m.memberId";

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

    @Override
    public List<ScheduleDetails> getScheduleDetails() throws SQLException {

        String sql = "SELECT s.scheduleId, s.scheduleName, m.memberId, m.memberName FROM scheduleDetails sd JOIN member m ON sd.memberId = m.memberId JOIN schedule s ON sd.scheduleId = s.scheduleId";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<ScheduleDetails> scheduleDetailsList = new ArrayList<>();

        while (resultSet.next()) {
            String scheduleId = resultSet.getString(1);
            String scheduleName = resultSet.getString(2);
            String memberId = resultSet.getString(3);
            String memberName = resultSet.getString(4);

            ScheduleDetails scheduleDetails = new ScheduleDetails(scheduleId, scheduleName, memberId, memberName);
            scheduleDetailsList.add(scheduleDetails);
        }
        return scheduleDetailsList;
    }

    @Override
    public List<TrainerDetails> getTrainerDetails() throws SQLException {
        String sql = "SELECT s.scheduleId, s.scheduleName, t.trainerId, t.trainerName FROM trainerDetails td JOIN trainer t ON td.trainerId = t.trainerId JOIN schedule s ON td.scheduleId = s.scheduleId";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<TrainerDetails> tdList = new ArrayList<>();
        while (resultSet.next()) {
            String scheduleId = resultSet.getString(1);
            String scheduleName = resultSet.getString(2);
            String trainerId = resultSet.getString(3);
            String trainerName = resultSet.getString(4);

            TrainerDetails td = new TrainerDetails(scheduleId, scheduleName, trainerId, trainerName);
            tdList.add(td);
        }
        return tdList;
    }

    @Override
    public void productSales(PieChart pieChart) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT productName, SUM(unitPrice * qty) AS productTotal FROM product p JOIN purchaseDetails pd ON p.productId = pd.productId group by productName");

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        while (resultSet.next()) {
            String productName = resultSet.getString("productName");
            double productTotal = resultSet.getDouble("productTotal");
            data.add(new PieChart.Data(productName, productTotal));
        }

        pieChart.setData(data);

        List<String> colors = Arrays.asList(
                "#d63031", "#fd79a8", "#6c5ce7", "#636e72", "#0984e3",
                "#fdcb6e", "#00b894", "#3c40c6", "#0be881", "#ffa801",
                "#34e7e4", "#3d3d3d", "#f5cd79", "#f19066", "#3F51B5"
        );

        for (int i = 0; i < pieChart.getData().size(); i++) {
            PieChart.Data slice = pieChart.getData().get(i);
            slice.getNode().setStyle("-fx-pie-color: " + colors.get(i % colors.size()) + ";");
        }
    }

}
