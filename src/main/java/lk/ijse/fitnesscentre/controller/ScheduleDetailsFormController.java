package lk.ijse.fitnesscentre.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.fitnesscentre.bo.BOFactory;
import lk.ijse.fitnesscentre.bo.custom.MemberBO;
import lk.ijse.fitnesscentre.bo.custom.ScheduleBO;
import lk.ijse.fitnesscentre.bo.custom.ScheduleDetailsBO;
import lk.ijse.fitnesscentre.dto.MemberDTO;
import lk.ijse.fitnesscentre.dto.ScheduleDetailsDTO;
import lk.ijse.fitnesscentre.entity.Member;
import lk.ijse.fitnesscentre.entity.Schedule;
import lk.ijse.fitnesscentre.entity.ScheduleDetails;
import lk.ijse.fitnesscentre.view.tdm.ScheduleDetailsTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDetailsFormController {
    public AnchorPane scheduleDetailsPane;

    public JFXComboBox<String> cmbScheduleId;
    public JFXComboBox<String> cmbMemberId;
    public JFXTextField txtScheduleName;
    public JFXTextField txtMemberName;

    @FXML
    private TableView<ScheduleDetailsTm> tblScheduleDetails;

    public TableColumn colScheduleId;
    public TableColumn colMemberId;
    public TableColumn colScheduleName;
    public TableColumn colMemberName;

    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnClear;

    private List<ScheduleDetailsDTO> scheduleDetailsList = new ArrayList<>();

    //Objects
    ScheduleDetailsBO scheduleDetailsBO = (ScheduleDetailsBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.SCHEDULE_DETAILS);
    MemberBO memberBO = (MemberBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MEMBER);
    ScheduleBO scheduleBO = (ScheduleBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.SCHEDULE);


    public void initialize() {
        this.scheduleDetailsList = getAllScheduleDetails();
        setCellValueFactory();
        loadScheduleDetailsTable();
        loadScheduleId();
        loadMemberId();
        hoverText();
    }

    @FXML
    void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String scheduleId = cmbScheduleId.getValue();
        String scheduleName = txtScheduleName.getText();
        String memberId = cmbMemberId.getValue();
        String memberName = txtMemberName.getText();

        if (scheduleId == null || memberId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select values for Schedule ID and Member ID.").show();
            return;
        }

        ScheduleDetailsDTO dto = new ScheduleDetailsDTO(scheduleId, scheduleName, memberId, memberName);

        try {
            boolean isAdded = scheduleDetailsBO.addScheduleDetails(dto);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Schedule Details Saved.").show();
                clearField();
                refreshTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) { clearField(); }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String scheduleId = cmbScheduleId.getValue();
        String scheduleName = txtScheduleName.getText();
        String memberId = cmbMemberId.getValue();
        String memberName = txtMemberName.getText();

        if (scheduleId == null || memberId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select values for Schedule ID and Member ID.").show();
            return;
        }

        ScheduleDetailsDTO dto = new ScheduleDetailsDTO(scheduleId, scheduleName, memberId, memberName);

        try {
            boolean isUpdated = scheduleDetailsBO.updateScheduleDetails(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Schedule Details Updated.").show();
                clearField();
                refreshTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Schedule Details.").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String scheduleId = cmbScheduleId.getValue();
        String memberId = cmbMemberId.getValue();

        if (scheduleId == null || memberId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select values for Schedule ID and Member ID.").show();
            return;
        }

        try {
            boolean isDeleted = scheduleDetailsBO.deleteScheduleDetails(scheduleId,memberId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Schedule Details Deleted.").show();
                clearField();
                refreshTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void cmbMemberIdSearchOnAction() {
        String memberId = cmbMemberId.getValue();
        try {
            MemberDTO member = memberBO.searchByMemberId(memberId);
            if (member != null) {
                txtMemberName.setText(member.getMemberName());
                txtMemberName.setEditable(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbScheduleIdSearchOnAction() {
        String scheduleId = cmbScheduleId.getValue();
        try {
            Schedule schedule = scheduleBO.searchByScheduleId(scheduleId);
            if (schedule != null) {
                txtScheduleName.setText(schedule.getScheduleName());
                txtScheduleName.setEditable(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearField() {
        cmbScheduleId.setValue(null);
        txtScheduleName.clear();
        cmbMemberId.setValue(null);
        txtMemberName.clear();
    }

    public void refreshTable() {
        this.scheduleDetailsList = getAllScheduleDetails();
        loadScheduleDetailsTable();
    }

    private void loadMemberId() {
        try {
            List<String> types = memberBO.getMemberIds();
            cmbMemberId.getItems().addAll(types);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadScheduleId() {
        try {
            List<String> types = scheduleBO.getScheduleIds();
            cmbScheduleId.getItems().addAll(types);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadScheduleDetailsTable() {
        ObservableList<ScheduleDetailsTm> tmList = FXCollections.observableArrayList();

        for (ScheduleDetailsDTO scheduleDetails : scheduleDetailsList) {
            ScheduleDetailsTm scheduleDetailsTm = new ScheduleDetailsTm(
                    scheduleDetails.getScheduleId(),
                    scheduleDetails.getScheduleName(),
                    scheduleDetails.getMemberId(),
                    scheduleDetails.getMemberName()
            );

            tmList.add(scheduleDetailsTm);
        }
        tblScheduleDetails.setItems(tmList);
//        ScheduleDetailsTm selectedItem = tblScheduleDetails.getSelectionModel().getSelectedItem();
//        System.out.println("selectedItem = " + selectedItem);
        tblScheduleDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cmbScheduleId.setValue(newValue.getScheduleId());
                txtScheduleName.setText(newValue.getScheduleName());
                cmbMemberId.setValue(newValue.getMemberId());
                txtMemberName.setText(newValue.getMemberName());
            }
        });
    }

    private void setCellValueFactory() {
        colScheduleId.setCellValueFactory(new PropertyValueFactory<>("scheduleId"));
        colScheduleName.setCellValueFactory(new PropertyValueFactory<>("scheduleName"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("memberName"));
    }

    private List<ScheduleDetailsDTO> getAllScheduleDetails() {
        List<ScheduleDetailsDTO> scheduleDetails = null;
        try {
            scheduleDetails = scheduleDetailsBO.getAllScheduleDetails();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return scheduleDetails;
    }

    public void hoverText() {
        btnAdd.setTooltip(new Tooltip("Add"));
        btnUpdate.setTooltip(new Tooltip("Update"));
        btnDelete.setTooltip(new Tooltip("Delete"));
        btnClear.setTooltip(new Tooltip("Clear"));
    }
}
