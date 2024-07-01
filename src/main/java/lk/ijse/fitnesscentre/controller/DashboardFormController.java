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
import lk.ijse.fitnesscentre.bo.BOFactory;
import lk.ijse.fitnesscentre.bo.custom.*;
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

    CredentialBO credentialBO = (CredentialBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CREDENTIAL);
    MemberBO memberBO = (MemberBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MEMBER);
    TrainerBO trainerBO = (TrainerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.TRAINER);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PAYMENT);
    PurchaseBO purchaseBO = (PurchaseBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PURCHASE);
    ProductBO productBO = (ProductBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PRODUCT);


    public void initialize() throws SQLException {

        updateTime();
        updateDate();

        try {
            trainerCount = trainerBO.getTrainerCount();
            memberCount = memberBO.getMemberCount();
            totalFee = paymentBO.getTotalPayment();
            totalPurchase = purchaseBO.getTotalPurchase();
            name = credentialBO.getUserName();
            paymentBO.monthlyFeeChart(barMembershipFee);
            productBO.productSales(pieChartSales);

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