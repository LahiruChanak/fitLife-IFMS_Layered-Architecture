package lk.ijse.fitnesscentre.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.fitnesscentre.entity.Membership;
import lk.ijse.fitnesscentre.entity.Payment;
import lk.ijse.fitnesscentre.view.tdm.PaymentTm;
import lk.ijse.fitnesscentre.dao.custom.impl.MembershipDAOImpl;
import lk.ijse.fitnesscentre.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.fitnesscentre.util.Regex;
import lk.ijse.fitnesscentre.util.TextField;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaymentFormController {

    @FXML
    public AnchorPane paymentPane;

    public TableView tblPayment;

    public TableColumn colPaymentId;
    public TableColumn colMethod;
    public TableColumn colFee;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colMembershipId;

    @FXML
    private JFXTextField txtDate;

    @FXML
    private JFXTextField txtTime;

    @FXML
    private JFXTextField txtFee;

    @FXML
    private JFXTextField txtPaymentId;

    @FXML
    public JFXButton btnBack;

    @FXML
    public ComboBox<String> cmbMethod;

    @FXML
    private ComboBox<String> cmbMembershipId;

    private List<Payment> paymentList = new ArrayList<>();

    PaymentDAOImpl paymentDAO = new PaymentDAOImpl();
    MembershipDAOImpl membershipDAO = new MembershipDAOImpl();

    //Button Actions

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/membership_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        paymentPane.getChildren().clear();
        paymentPane.getChildren().add(registerPane);
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentId.getText();
        String paymentMethod = cmbMethod.getValue();
        String feeText = txtFee.getText();
        String dateText = txtDate.getText();
        String timeText = txtTime.getText();
        String membershipId = cmbMembershipId.getValue();

        double membershipFee;
        Date date;
        Time time;

        try {
            membershipFee = Double.parseDouble(feeText);
            date = Date.valueOf(dateText);
            time = Time.valueOf(timeText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Invalid Fields").show();
            return;
        }

        Payment payment = new Payment(paymentId, paymentMethod, membershipFee, date, time, membershipId);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isAdded = paymentDAO.add(payment);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Added.").show();
                clearField();
                refreshTable();
                loadNextId();
                setAttendDateTime();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadPaymentTable();
    }

    public void btnClearOnAction(ActionEvent actionEvent) { clearField(); }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        txtPaymentIdSearchOnAction(actionEvent);
    }

    private void setAttendDateTime() {
        //Date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        txtDate.setText(formattedDate);
        txtDate.setEditable(false);

        //Time
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            txtTime.setText(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));
        }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

//    public void btnUpdateOnAction(ActionEvent actionEvent) {
//        String paymentId = txtPaymentId.getText();
//        String paymentMethod = cmbMethod.getValue();
//        String feeText = txtFee.getText();
//        String dateText = txtDate.getText();
//        String timeText = txtTime.getText();
//        String membershipId = txtMembershipId.getText();
//
//        double membershipFee;
//        Date date;
//        Time time;
//
//        try {
//            membershipFee = Double.parseDouble(feeText);
//            date = Date.valueOf(dateText);
//            time = Time.valueOf(timeText);
//        } catch (NumberFormatException e) {
//            new Alert(Alert.AlertType.WARNING, "Invalid Fields").show();
//            return;
//        }
//
//        Payment payment = new Payment(paymentId, paymentMethod, membershipFee, date, time, membershipId);
//
//        String errorMessage = isValid();
//
//        if (errorMessage != null) {
//            new Alert(Alert.AlertType.ERROR, errorMessage).show();
//            return;
//        }
//
//        try {
//            boolean isUpdate = PaymentRepo.update(payment);
//            if (isUpdate) {
//                new Alert(Alert.AlertType.CONFIRMATION, "Payment Updated.").show();
//                clearField();
//                refreshTable();
//            }
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
//        }
//    }
//
//    public void btnDeleteOnAction(ActionEvent actionEvent) {
//        String paymentId = txtPaymentId.getText();
//
//        try {
//            boolean isDeleted = PaymentRepo.delete(paymentId);
//            if (isDeleted) {
//                new Alert(Alert.AlertType.CONFIRMATION, "Payment Deleted.").show();
//                clearField();
//                refreshTable();
//            }
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
//        }
//    }

    //Extra Functions

    private void clearField() {
        txtPaymentId.setText("");
        cmbMethod.setValue(null);
        txtFee.setText("");
        txtDate.setText("");
        txtTime.setText("");
        cmbMembershipId.setValue(null);
    }

    public void refreshTable() {
        this.paymentList =getAllPayments();
        loadPaymentTable();
    }

    public void txtPaymentIdSearchOnAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentId.getText();

        try {
            Payment payment = paymentDAO.searchById(paymentId);

            if (payment != null) {
                txtPaymentId.setText(payment.getPaymentId());
                cmbMethod.setValue(payment.getPaymentMethod());
                txtFee.setText(String.valueOf(payment.getMembershipFee()));
                txtDate.setText(String.valueOf(payment.getDate()));
                txtTime.setText(String.valueOf(payment.getTime()));
                cmbMembershipId.setValue(payment.getMembershipId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void cmbMembershipIdOnAction() {
        String membershipId = cmbMembershipId.getValue();
        try {
            Membership membership = membershipDAO.getFee(membershipId);
            if (membership != null) {
                txtFee.setText(String.valueOf(membership.getMembershipFee()));
                txtFee.setEditable(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadPaymentTable() {
        ObservableList<PaymentTm> tmList = FXCollections.observableArrayList();

        for (Payment payment : paymentList) {
            PaymentTm paymentTm = new PaymentTm(
                    payment.getPaymentId(),
                    payment.getPaymentMethod(),
                    payment.getMembershipFee(),
                    payment.getDate(),
                    payment.getTime(),
                    payment.getMembershipId()
            );

            tmList.add(paymentTm);
        }
        tblPayment.setItems(tmList);
        PaymentTm selectedItem = (PaymentTm) tblPayment.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<Payment, String>("paymentId"));
        colMethod.setCellValueFactory(new PropertyValueFactory<Payment, String>("paymentMethod"));
        colFee.setCellValueFactory(new PropertyValueFactory<Payment, Double>("membershipFee"));
        colDate.setCellValueFactory(new PropertyValueFactory<Payment, Date>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<Payment, Time>("time"));
        colMembershipId.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
    }

    private List<Payment> getAllPayments() {
        List<Payment> paymentList = null;
        try {
            paymentList = paymentDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paymentList;
    }

    @FXML
    void cmbMethodOnAction(ActionEvent actionEvent) {
        if (cmbMethod.getItems().isEmpty()) {
            loadPaymentTypes();
        }
    }

    private void loadPaymentTypes() {
        ObservableList<String> types = FXCollections.observableArrayList("Cash", "Card");
        cmbMethod.setItems(types);
    }

    private void loadMembershipIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = membershipDAO.getIds();
            for (String id : idList) {
                obList.add(id);
            }
            cmbMembershipId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNextId() {
        try {
            String currentId = paymentDAO.currentId();
            String nextId = nextPaymentId(currentId);

            txtPaymentId.setText(nextId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextPaymentId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("PAY");
            int id = Integer.parseInt(split[1]);
            return "PAY" + String.format("%03d", ++id);
        }
        return "PAY001";
    }

    public void initialize() throws SQLException {
        loadNextId();
        this.paymentList = getAllPayments();
        setCellValueFactory();
        loadPaymentTypes();
        loadMembershipIds();
        loadPaymentTable();
        setAttendDateTime();
    }

    public void txtPaymentIdOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.PAYMENTID, txtPaymentId); }

    public void txtFeeOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.PRICE, txtFee); }

    public String isValid(){

        String message = "";

        if (!Regex.setTextColor(TextField.PAYMENTID,txtPaymentId))
            message += "PaymentId must be starts with 'PAY' and exactly three digits.\n\n";

        if (!Regex.setTextColor(TextField.PRICE,txtFee))
            message += "MembershipFee must be has numbers and one or two decimal values.\n\n";

        return message.isEmpty() ? null : message;
    }

}
