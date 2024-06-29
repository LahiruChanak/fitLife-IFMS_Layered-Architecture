package lk.ijse.fitnesscentre.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.fitnesscentre.dao.custom.impl.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {
    public AnchorPane homeNode;

    public Label lblMemberCount;
    public Label lblTotalFee;
    public Label lblTrainerCount;
    public Label lblTotalPurchase;
    public Label lblUser;

    public BarChart<String, Number> barMembershipFee;
    public PieChart pieChartSales;

    public Text txtTime;
    public Text txtDate;

    private int trainerCount;
    private int memberCount;
    private double totalFee;
    private double totalPurchase;
    private String name;

    CredentialDAOImpl credentialDAO = new CredentialDAOImpl();
    MemberDAOImpl memberDAO = new MemberDAOImpl();
    TrainerDAOImpl trainerDAO = new TrainerDAOImpl();
    PaymentDAOImpl paymentDAO = new PaymentDAOImpl();
    PurchaseDAOImpl purchaseDAO = new PurchaseDAOImpl();
    ProductDAOImpl productDAO = new ProductDAOImpl();

    public void initialize() throws SQLException {

        updateTime();
        updateDate();

        try {
            trainerCount = trainerDAO.getCount();
            memberCount = memberDAO.getCount();
            totalFee = paymentDAO.getTotal();
            totalPurchase = purchaseDAO.getTotal();
            name = credentialDAO.getUserName();
            paymentDAO.monthlyFeeChart(barMembershipFee);
            productDAO.productSales(pieChartSales);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        setTrainerCount(trainerCount);
        setMemberCount(memberCount);
        setTotalFee(totalFee);
        setTotalPurchase(totalPurchase);
        setUserName(name);
    }

    private void setTrainerCount(int trainerCount) { lblTrainerCount.setText(String.valueOf(trainerCount)); }

    private void setMemberCount(int memberCount) { lblMemberCount.setText(String.valueOf(memberCount));}

    private void setTotalFee(double totalFee) { lblTotalFee.setText(String.valueOf(totalFee)); }

    private void setTotalPurchase(double totalPurchase) { lblTotalPurchase.setText(String.valueOf(totalPurchase)); }

    private void setUserName(String name) { lblUser.setText(name); }

    private void updateTime() {
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            txtTime.setText(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));
        }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        txtDate.setText(formattedDate);
    }
}