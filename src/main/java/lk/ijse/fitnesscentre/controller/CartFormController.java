package lk.ijse.fitnesscentre.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.fitnesscentre.bo.BOFactory;
import lk.ijse.fitnesscentre.bo.custom.*;
import lk.ijse.fitnesscentre.bo.custom.impl.PlaceOrderBOImpl;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.dto.PlaceOrderDTO;
import lk.ijse.fitnesscentre.dto.ProductDTO;
import lk.ijse.fitnesscentre.entity.*;
import lk.ijse.fitnesscentre.view.tdm.CartTm;
import lk.ijse.fitnesscentre.util.Regex;
import lk.ijse.fitnesscentre.util.TextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class CartFormController {

    @FXML
    private AnchorPane cartPane;

    @FXML
    public JFXTextField txtQty;

    @FXML
    private JFXTextField txtPrdName;

    @FXML
    private JFXTextField txtPurchaseId;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXComboBox<String> cmbProductId;

    @FXML
    public JFXComboBox<String> cmbMemberId;

    @FXML
    private TableColumn<?, ?> colPrdName;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn colRemove;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableView<CartTm> tblCart;

    @FXML
    private Text txtNetTotal;

    @FXML
    private Text txtOrderDate;

    private final ObservableList<CartTm> cartList = FXCollections.observableArrayList();

    //BO Objects
    MemberBO memberBO = (MemberBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MEMBER);
    ProductBO productBO = (ProductBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PRODUCT);
    PurchaseBO purchaseBO = (PurchaseBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PURCHASE);
    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PLACE_ORDER);


    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String productId = cmbProductId.getValue();
        String productName = txtPrdName.getText();
        String priceText = txtUnitPrice.getText();
        String qtyText = txtQty.getText();

        int qty;
        double unitPrice;

        try {
            qty = Integer.parseInt(qtyText);
            unitPrice = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Fields").show();
            return;
        }

        double total = qty * unitPrice;

        ImageView delete = new ImageView(new Image("/asserts/icon/remove.png"));
        delete.setFitHeight(20);
        delete.setPreserveRatio(true);

        JFXButton btnRemove = new JFXButton("", delete);

        btnRemove.setCursor(Cursor.HAND);
        btnRemove.setStyle("-fx-background-color: transparent; -fx-text-fill: red");
        btnRemove.setPrefHeight(30);
        btnRemove.setPrefWidth(50);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        for (int i = 0; i < tblCart.getItems().size(); i++) {
            if (productId.equals(colProductId.getCellData(i))) {
                qty += cartList.get(i).getQty();
                total = unitPrice * qty;

                cartList.get(i).setQty(qty);
                cartList.get(i).setTotal(total);

                tblCart.refresh();
                txtQty.clear();
                return;
            }
        }

        CartTm cartTm = new CartTm(productId, productName, unitPrice, qty, total, btnRemove);

        cartList.add(cartTm);

        cartRefresh();

        tblCart.setItems(cartList);
        calculateNetTotal();
        clearFields();
    }

    public void clearFields() {
        txtQty.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        txtPrdName.clear();
        cmbProductId.setValue(null);
    }

    private void cartRefresh() {

        for (int i = 0; i < cartList.size(); i++) {
            final int index = i;

            cartList.get(i).getBtnRemove().setOnAction(event -> {

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);


                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {
                    cartList.remove(index);
                    cartRefresh();
                    calculateNetTotal();
                }
            });
        }
    }

    private void calculateNetTotal() {

        double total = 0;

        for (int i = 0; i < tblCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }

        txtNetTotal.setText(String.valueOf(total));
    }

    private void getProductId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = productBO.getProductIds();
            for (String id : idList) {
                obList.add(id);
            }

            cmbProductId.setItems(obList);
            txtPrdName.setEditable(false);
            txtUnitPrice.setEditable(false);
            txtQtyOnHand.setEditable(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getMemberId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = memberBO.getMemberIds();
            for (String id : idList) {
                obList.add(id);
            }

            cmbMemberId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNextPurchaseId() {
        try {
            String currentId = purchaseBO.currentPurchaseId();
            String nextId = nextPurchaseId(currentId);

            txtPurchaseId.setText(nextId);
            txtPurchaseId.setEditable(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextPurchaseId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("PUR");
            int id = Integer.parseInt(split[1]);
            return "PUR" + String.format("%03d", ++id);
        }
        return "PUR001";
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/store_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        cartPane.getChildren().clear();
        cartPane.getChildren().add(registerPane);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        String purchaseId = txtPurchaseId.getText();
        Date purchaseDate = Date.valueOf(LocalDate.now());
        Time purchaseTime = Time.valueOf(LocalTime.now());
        double totalPrice = Double.valueOf(txtNetTotal.getText());
        String memberId = cmbMemberId.getValue();

//        var purchase = new Purchase(purchaseId, purchaseDate, purchaseTime, totalPrice, memberId);

        if (tblCart.getItems() == null) {
            new Alert(Alert.AlertType.ERROR, "Cart is Empty").show();
            return;
        }

        List<PurchaseDetail> pdList = new ArrayList<>();

        for (int i = 0; i < tblCart.getItems().size(); i++) {
            CartTm cartTm = cartList.get(i);

            PurchaseDetail pd = new PurchaseDetail(
                    purchaseId,
                    cartTm.getProductId(),
                    purchaseDate,
                    purchaseTime,
                    cartTm.getQty()
            );
            pdList.add(pd);
        }

        PlaceOrderDTO dto = new PlaceOrderDTO(purchaseId, purchaseDate, purchaseTime, totalPrice, memberId, pdList);

        try {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Confirm the Purchase?");
            Optional<ButtonType> type = confirm.showAndWait();

            if (type.isPresent() && type.get() == ButtonType.OK) {
                boolean isSuccess = placeOrderBO.placeOrder(dto);

                if (isSuccess) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Purchased Successfully. Do you want a receipt?");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        btnReceiptOnAction(event);
                    }
                    cartTableClear();
                    loadNextPurchaseId();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Purchased Failed. Please try again.").show();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void cartTableClear() {
        tblCart.getItems().clear();
        txtNetTotal.setText("-");
        txtPrdName.clear();
        txtQty.clear();
        cmbMemberId.setValue(null);
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        cmbProductId.setValue(null);
    }

    @FXML
    void btnReceiptOnAction(ActionEvent event) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/ReportNew.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> data = new HashMap<>();
            data.put("PURCHASE_ID", txtPurchaseId.getText());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error generating receipt");
            alert.setContentText("An error occurred while generating the receipt. Check the logs for more details.");
            alert.showAndWait();
        }
    }

    public void cmbProductIdSearchOnAction(ActionEvent actionEvent) {
        String prdId = cmbProductId.getValue();
        try {
            ProductDTO product = productBO.searchByProductId(prdId);
            if (product != null) {
                txtPrdName.setText(product.getProductName());
                txtUnitPrice.setText(String.valueOf(product.getUnitPrice()));
                txtQtyOnHand.setText(String.valueOf(product.getQtyOnHand()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtQty.requestFocus();
    }

    public void cmbMemberIdOnAction(ActionEvent actionEvent) {
        cmbProductId.requestFocus();
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
        btnAddToCartOnAction(actionEvent);
    }

    public void initialize() {
        setCellValueFactory();
        loadNextPurchaseId();
        getProductId();
        getMemberId();
        updateDateTime();
    }

    private void setCellValueFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colPrdName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void updateDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy    HH:mm:ss");
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {

            LocalDateTime now = LocalDateTime.now();
            String formattedDateTime = now.format(formatter);
//            formattedDateTime = formattedDateTime.replace("am", "AM").replace("pm", "PM");
            txtOrderDate.setText(formattedDateTime);
        }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void txtQtyOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.COUNT, txtQty); }

    public String isValid(){

        String message = "";

        if (!Regex.setTextColor(TextField.COUNT,txtQty))
            message += "Qty couldn't be below than 0.\n\n";

        return message.isEmpty() ? null : message;
    }

    public void btnPurchaseHistoryOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/purchaseHistory_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        cartPane.getChildren().clear();
        cartPane.getChildren().add(registerPane);
    }
}