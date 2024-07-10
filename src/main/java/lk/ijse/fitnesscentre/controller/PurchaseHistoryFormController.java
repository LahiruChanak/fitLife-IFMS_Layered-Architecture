package lk.ijse.fitnesscentre.controller;

import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.fitnesscentre.bo.BOFactory;
import lk.ijse.fitnesscentre.bo.custom.PurchaseBO;
import lk.ijse.fitnesscentre.bo.custom.PurchaseDetailBO;
import lk.ijse.fitnesscentre.bo.custom.PurchaseHistoryBO;
import lk.ijse.fitnesscentre.db.DbConnection;
import lk.ijse.fitnesscentre.dto.PurchaseHistoryDTO;
import lk.ijse.fitnesscentre.view.tdm.PurchaseHistoryTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseHistoryFormController {
    public AnchorPane purchaseHistoryPane;

    public TableColumn colPurchaseId;
    public TableColumn colProductId;
    public TableColumn colProductName;
    public TableColumn colUnitPrice;
    public TableColumn colMemberId;
    public TableColumn colMemberName;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colQty;
    public TableColumn colTotal;

    public JFXComboBox cmbPurchaseId;

    @FXML
    private TableView<PurchaseHistoryTm> tblPurchaseHistory;

    @FXML
    private List<PurchaseHistoryDTO> historyList = new ArrayList<>();

    PurchaseHistoryBO purchaseHistoryBO = (PurchaseHistoryBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PURCHASE_HISTORY);
    PurchaseBO purchaseBO = (PurchaseBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PURCHASE);


    public void initialize() {
        this.historyList = getAllHistory();
        loadPurchaseTable();
        loadPurchaseId();
        setCellValueFactory();
        setTableViewSelectionListener();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/cart_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        purchaseHistoryPane.getChildren().clear();
        purchaseHistoryPane.getChildren().add(registerPane);
    }

    @FXML
    void btnReceiptOnAction(ActionEvent event) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/ReportNew.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

//            PurchaseHistoryTm selectedItem = tblPurchaseHistory.getSelectionModel().getSelectedItem();

            if (cmbPurchaseId.getValue() == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a Purchase Id").show();
                return;
            }

            Map<String, Object> data = new HashMap<>();
            data.put("PURCHASE_ID", cmbPurchaseId.getValue());

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

    private void setCellValueFactory() {
        colPurchaseId.setCellValueFactory(new PropertyValueFactory<>("purchaseId"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private void loadPurchaseTable() {
        ObservableList<PurchaseHistoryTm> tmList = FXCollections.observableArrayList();

        for (PurchaseHistoryDTO history : historyList) {
            PurchaseHistoryTm historyTm = new PurchaseHistoryTm(
                    history.getPurchaseId(),
                    history.getProductId(),
                    history.getProductName(),
                    history.getUnitPrice(),
                    history.getMemberId(),
                    history.getMemberName(),
                    history.getDate(),
                    history.getTime(),
                    history.getQty(),
                    history.getTotal()
            );
            tmList.add(historyTm);
        }

        tblPurchaseHistory.setItems(tmList);
        PurchaseHistoryTm selectedItem = tblPurchaseHistory.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setTableViewSelectionListener() {
        tblPurchaseHistory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cmbPurchaseId.setValue(newValue.getPurchaseId());
            }
        });
    }

    private List<PurchaseHistoryDTO> getAllHistory() {
        List<PurchaseHistoryDTO> historyList = null;
        try {
            historyList = purchaseHistoryBO.getAllHistory();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return historyList;
    }

    private void loadPurchaseId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = purchaseBO.getPurchaseIds();
            for (String id : idList) {
                obList.add(id);
            }
            cmbPurchaseId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
