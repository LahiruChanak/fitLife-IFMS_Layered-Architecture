package lk.ijse.fitnesscentre.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.fitnesscentre.bo.BOFactory;
import lk.ijse.fitnesscentre.bo.custom.MembershipBO;
import lk.ijse.fitnesscentre.dao.custom.MembershipDAO;
import lk.ijse.fitnesscentre.dto.MembershipDTO;
import lk.ijse.fitnesscentre.entity.Membership;
import lk.ijse.fitnesscentre.view.tdm.MembershipTm;
import lk.ijse.fitnesscentre.dao.custom.impl.MembershipDAOImpl;
import lk.ijse.fitnesscentre.util.Regex;
import lk.ijse.fitnesscentre.util.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MembershipFormController {

    @FXML
    public AnchorPane membershipPane;

    @FXML
    private JFXComboBox<String> cmbMembershipType;

    @FXML
    private JFXTextField txtMembershipFee;

    @FXML
    private JFXTextField txtMembershipId;

    @FXML
    private JFXTextField txtDescription;

    public TableView<MembershipTm> tblMembership;

    public TableColumn colMembershipId;
    public TableColumn colType;
    public TableColumn colDescription;
    public TableColumn colMembershipFee;

    public JFXButton btnPayment;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnClear;
    public JFXButton btnSearch;

    private List<MembershipDTO> membershipList = new ArrayList<>();

    MembershipBO membershipBO = (MembershipBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MEMBERSHIP);


    public void initialize() {
        loadNextId();
        loadMembershipTypes();
        this.membershipList = getAllMembership();
        setCellValueFactory();
        loadMembershipTable();
        hoverText();
    }

    //Button Actions
    public void btnPaymentOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/payment_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        membershipPane.getChildren().clear();
        membershipPane.getChildren().add(registerPane);
    }

    //Other Button Actions

    public void btnAddOnAction(ActionEvent actionEvent) {
        String membershipId = txtMembershipId.getText();
        String membershipType = cmbMembershipType.getValue();
        String description = txtDescription.getText();
        String feeText = txtMembershipFee.getText();

        double membershipFee;
        try {
            membershipFee = Double.parseDouble(feeText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid membership fee").show();
            return;
        }

        MembershipDTO dto = new MembershipDTO(membershipId, membershipType, description, membershipFee);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isAdded = membershipBO.addMembership(dto);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Membership saved!").show();
                clearField();
                refreshTable();
                loadNextId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadMembershipTable();
    }

    public void btnClearOnAction(ActionEvent actionEvent) { clearField(); }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String membershipId = txtMembershipId.getText();
        String membershipType = cmbMembershipType.getValue();
        String description = txtDescription.getText();
        String feeText = txtMembershipFee.getText();

        double membershipFee;
        try {
            membershipFee = Double.parseDouble(feeText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid membership fee").show();
            return;
        }

        MembershipDTO dto = new MembershipDTO(membershipId, membershipType, description, membershipFee);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isUpdate = membershipBO.updateMembership(dto);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Membership updated!").show();
                clearField();
                refreshTable();
                loadNextId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String membershipId = txtMembershipId.getText();

        try {
            boolean isDeleted = membershipBO.deleteMembership(membershipId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Membership deleted!").show();
                clearField();
                refreshTable();
                loadNextId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) { txtMembershipIdSearchOnAction(actionEvent); }

    //Extra Functions

    private void clearField() {
        txtMembershipId.setText("");
        cmbMembershipType.setValue(null);
        txtDescription.setText("");
        txtMembershipFee.setText("");
    }

    public void refreshTable() {
        this.membershipList =getAllMembership();
        loadMembershipTable();
    }

    public void txtMembershipIdSearchOnAction(ActionEvent actionEvent) {
        String membershipId = txtMembershipId.getText();

        try {
            MembershipDTO membership = membershipBO.searchByMembershipId(membershipId);

            if (membership != null) {
                txtMembershipId.setText(membership.getMembershipId());
                cmbMembershipType.setValue(membership.getMembershipType());
                txtDescription.setText(membership.getDescription());
                txtMembershipFee.setText(String.valueOf(membership.getMembershipFee()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadMembershipTable() {
        ObservableList<MembershipTm> tmList = FXCollections.observableArrayList();

        for (MembershipDTO membership : membershipList) {
            MembershipTm membershipTm = new MembershipTm(
                    membership.getMembershipId(),
                    membership.getMembershipType(),
                    membership.getDescription(),
                    membership.getMembershipFee()
            );

            tmList.add(membershipTm);
        }
        tblMembership.setItems(tmList);
        MembershipTm selectedItem = tblMembership.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colMembershipId.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("membershipType"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colMembershipFee.setCellValueFactory(new PropertyValueFactory<>("membershipFee"));
    }

    private List<MembershipDTO> getAllMembership() {
        List<MembershipDTO> membershipList = null;

        try {
            membershipList = membershipBO.getAllMemberships();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return membershipList;
    }

    @FXML
    void cmbMembershipTypeOnAction(ActionEvent actionEvent) throws SQLException {
        if (cmbMembershipType.getItems().isEmpty()) {
            loadMembershipTypes();
        }
        setDescription();
    }

    private void loadMembershipTypes() {
        ObservableList<String> genderTypes = FXCollections.observableArrayList("Monthly", "3 Months", "6 Months", "Annual");
        cmbMembershipType.setItems(genderTypes);
    }

    public void setDescription() {
        String type = cmbMembershipType.getValue();

        if (type == null) {
            txtDescription.setText("");
            return;
        }

        String description = null;

        switch (type) {
            case "Monthly":
                description = "Monthly membership";
                break;

            case "3 Months":
                description = "Three Months membership";
                break;

            case "6 Months":
                description = "Six Months membership";
                break;

            case "Annual":
                description = "Annual membership";
                break;

            default:
                description = "";
                break;
        }

        txtDescription.setText(description);
        txtDescription.setEditable(false);
    }

    private void loadNextId() {
        try {
            String currentId = membershipBO.currentMembershipId();
            String nextId = nextMemberId(currentId);

            txtMembershipId.setText(nextId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextMemberId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("MS");
            int id = Integer.parseInt(split[1]);
            return "MS" + String.format("%03d", ++id);
        }
        return "MS001";
    }

    public void hoverText(){
        btnAdd.setTooltip(new Tooltip("Add"));
        btnUpdate.setTooltip(new Tooltip("Update"));
        btnDelete.setTooltip(new Tooltip("Delete"));
        btnSearch.setTooltip(new Tooltip("Search"));
        btnClear.setTooltip(new Tooltip("Clear"));
    }

    public void txtMembershipIdOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.MEMBERSHIPID, txtMembershipId); }

    public void txtMembershipFeeOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.PRICE, txtMembershipFee); }

    public void txtDescriptionOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.DESCRIPTION, txtDescription); }

    public String isValid(){

        String message = "";

        if (!Regex.setTextColor(TextField.MEMBERSHIPID,txtMembershipId))
            message += "MembershipId must be starts with 'MS' and exactly three digits.\n\n";

        if (!Regex.setTextColor(TextField.PRICE,txtMembershipFee))
            message += "MembershipFee must be a price.\n\n";

        if (!Regex.setTextColor(TextField.DESCRIPTION,txtDescription))
            message += "Description must not be empty.\n\n";

        return message.isEmpty() ? null : message;
    }

}
