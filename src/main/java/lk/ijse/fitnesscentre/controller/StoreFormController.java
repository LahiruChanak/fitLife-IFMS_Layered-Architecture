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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.fitnesscentre.bo.BOFactory;
import lk.ijse.fitnesscentre.bo.custom.ProductBO;
import lk.ijse.fitnesscentre.dto.ProductDTO;
import lk.ijse.fitnesscentre.entity.Product;
import lk.ijse.fitnesscentre.view.tdm.ProductTm;
import lk.ijse.fitnesscentre.dao.custom.impl.ProductDAOImpl;
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

public class StoreFormController {

    @FXML
    public AnchorPane storePane;

    @FXML
    private JFXTextField txtPrdName;

    @FXML
    private JFXTextField txtProductId;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtAddedDate;

    @FXML
    private JFXTextField txtAddedTime;

    @FXML
    private TableColumn colProductId;

    @FXML
    private TableColumn colProductName;

    @FXML
    private TableColumn colUnitPrice;

    @FXML
    private TableColumn colQtyOnHand;

    @FXML
    private TableColumn colAddedDate;

    @FXML
    private TableColumn colAddedTime;

    @FXML
    private TableView<ProductTm> tblProduct;

    private List<ProductDTO> productList = new ArrayList<>();

    ProductBO productBO = (ProductBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PRODUCT);


    public void initialize() {
        loadNextId();
        this.productList =getAllProducts();
        loadProductTable();
        setCellValueFactory();
        setAttendDateTime();

        txtAddedDate.setEditable(false);
        txtAddedTime.setEditable(false);
    }

    @FXML
    void btnCartOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/cart_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        storePane.getChildren().clear();
        storePane.getChildren().add(registerPane);
    }

    private void loadProductTable() {
        ObservableList<ProductTm> tmList = FXCollections.observableArrayList();

        for (ProductDTO product : productList) {
            JFXButton btnAddToCart = new JFXButton("Cart");
            btnAddToCart.setStyle("-fx-background-radius: 15; -fx-background-color: #121110; -fx-text-fill: white;");

            ProductTm productTm = new ProductTm(
                    product.getProductId(),
                    product.getProductName(),
                    product.getUnitPrice(),
                    product.getQtyOnHand(),
                    product.getAddedDate(),
                    product.getAddedTime()
            );

            tmList.add(productTm);
        }

        tblProduct.setItems(tmList);
        ProductTm selectedItem = tblProduct.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colAddedDate.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        colAddedTime.setCellValueFactory(new PropertyValueFactory<>("addedTime"));
    }

    private List<ProductDTO> getAllProducts() {
        List<ProductDTO> productList = null;
        try {
            productList = productBO.getAllProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String productId = txtProductId.getText();
        String productName = txtPrdName.getText();
        String priceText = txtUnitPrice.getText();
        String qtyText = txtQtyOnHand.getText();
        String dateText = txtAddedDate.getText();
        String timeText = txtAddedTime.getText();

        double unitPrice;
        int qtyOnHand;
        Date addedDate;
        Time addedTime;

        try {
            unitPrice = Double.parseDouble(priceText);
            qtyOnHand = Integer.parseInt(qtyText);
            addedDate = Date.valueOf(dateText);
            addedTime = Time.valueOf(timeText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Fields").show();
            return;
        }

        ProductDTO dto = new ProductDTO(productId, productName, unitPrice, qtyOnHand, addedDate, addedTime);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isAdded = productBO.addProduct(dto);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Product saved!").show();
                clearField();
                refreshTable();
                loadNextId();
                setAttendDateTime();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) { clearField(); }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String productId = txtProductId.getText();
        String productName = txtPrdName.getText();
        String priceText = txtUnitPrice.getText();
        String qtyText = txtQtyOnHand.getText();
        String dateText = txtAddedDate.getText();
        String timeText = txtAddedTime.getText();

        double unitPrice;
        int qtyOnHand;
        Date addedDate;
        Time addedTime;

        try {
            unitPrice = Double.parseDouble(priceText);
            qtyOnHand = Integer.parseInt(qtyText);
            addedDate = Date.valueOf(dateText);
            addedTime = Time.valueOf(timeText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Fields").show();
            return;
        }

        ProductDTO dto = new ProductDTO(productId, productName, unitPrice, qtyOnHand, addedDate, addedTime);

        try {
            boolean isUpdated = productBO.updateProduct(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Product Updated.").show();
                clearField();
                refreshTable();
                loadNextId();
                setAttendDateTime();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String productId = txtProductId.getText();

        try {
            boolean isDeleted = productBO.deleteProduct(productId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Product Deleted.").show();
                clearField();
                refreshTable();
                loadNextId();
                setAttendDateTime();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) { txtPrdIdSearchOnAction(actionEvent); }

    private void clearField() {
        txtProductId.clear();
        txtPrdName.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtAddedDate.clear();
        txtAddedTime.clear();
    }

    private void loadNextId() {
        try {
            String currentId = productBO.currentProductId();
            String nextId = nextPaymentId(currentId);

            txtProductId.setText(nextId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextPaymentId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("PRD");
            int id = Integer.parseInt(split[1]);
            return "PRD" + String.format("%03d", ++id);
        }
        return "PRD001";
    }

    public void txtPrdIdSearchOnAction(ActionEvent actionEvent) {
        String productId = txtProductId.getText();

        try {
            Product product = productBO.searchByProductId(productId);

            if (product != null) {
                txtProductId.setText(product.getProductId());
                txtPrdName.setText(product.getProductName());
                txtUnitPrice.setText(String.valueOf(product.getUnitPrice()));
                txtQtyOnHand.setText(String.valueOf(product.getQtyOnHand()));
                txtAddedDate.setText(String.valueOf(product.getAddedDate()));
                txtAddedTime.setText(String.valueOf(product.getAddedTime()));

                txtPrdName.requestFocus();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        }
    }

    private void setAttendDateTime() {
        //Date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        txtAddedDate.setText(formattedDate);

        //Time
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            txtAddedTime.setText(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));
        }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void txtPrdNameSearchOnAction(ActionEvent actionEvent) { txtUnitPrice.requestFocus(); }

    public void txtPriceSearchOnAction(ActionEvent actionEvent) { txtQtyOnHand.requestFocus(); }

    public void refreshTable() {
        this.productList =getAllProducts();
        loadProductTable();
    }

    public void txtProductIdOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.PRODUCTID, txtProductId); }

    public void txtPriceOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.PRICE, txtUnitPrice); }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.NAME, txtPrdName); }

    public void txtQtyOnHandOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.COUNT, txtQtyOnHand); }

    public String isValid(){

        String message = "";

        if (!Regex.setTextColor(TextField.PRODUCTID,txtProductId))
            message += "ProductId must be starts with 'PRD' and exactly three digits.\n\n";

        if (!Regex.setTextColor(TextField.NAME,txtPrdName))
            message += "Name must be at least 3 letters.\n\n";

        if (!Regex.setTextColor(TextField.PRICE,txtUnitPrice))
            message += "UnitPrice must be has numbers and one or two decimal values.\n\n";

        if (!Regex.setTextColor(TextField.COUNT,txtQtyOnHand))
            message += "QtyOnHand couldn't be below than 0.\n\n";

        return message.isEmpty() ? null : message;
    }
}
