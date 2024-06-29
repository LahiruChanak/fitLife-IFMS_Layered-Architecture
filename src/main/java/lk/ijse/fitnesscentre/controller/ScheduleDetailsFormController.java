package lk.ijse.fitnesscentre.controller;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.fitnesscentre.dao.custom.impl.MemberDAOImpl;
import lk.ijse.fitnesscentre.dao.custom.impl.ScheduleDAOImpl;
import lk.ijse.fitnesscentre.entity.Member;
import lk.ijse.fitnesscentre.entity.Schedule;
import lk.ijse.fitnesscentre.entity.ScheduleDetails;
import lk.ijse.fitnesscentre.view.tdm.ScheduleDetailsTm;
import lk.ijse.fitnesscentre.dao.custom.impl.ScheduleDetailsDAOImpl;

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

    ScheduleDetailsDAOImpl scheduleDetailsDAO = new ScheduleDetailsDAOImpl();
    MemberDAOImpl memberDAO = new MemberDAOImpl();
    ScheduleDAOImpl scheduleDAO = new ScheduleDAOImpl();

    @FXML
    private TableView<ScheduleDetailsTm> tblScheduleDetails;

    public TableColumn colScheduleId;
    public TableColumn colMemberId;
    public TableColumn colScheduleName;
    public TableColumn colMemberName;

    private List<ScheduleDetails> scheduleDetailsList = new ArrayList<>();

    public void initialize() {
        this.scheduleDetailsList = getAllScheduleDetails();
        setCellValueFactory();
        loadScheduleDetailsTable();
        loadScheduleId();
        loadMemberId();
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

        ScheduleDetails scheduleDetails = new ScheduleDetails(scheduleId, scheduleName, memberId, memberName);

        try {
            boolean isAdded = scheduleDetailsDAO.add(scheduleDetails);
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

        ScheduleDetails scheduleDetails = new ScheduleDetails(scheduleId, scheduleName, memberId, memberName);

        try {
            boolean isUpdated = scheduleDetailsDAO.update(scheduleDetails);
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
            boolean isDeleted = scheduleDetailsDAO.delete(scheduleId,memberId);
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
            Member member = memberDAO.searchById(memberId);
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
            Schedule schedule = scheduleDAO.searchById(scheduleId);
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
            List<String> types = memberDAO.getIds();
            cmbMemberId.getItems().addAll(types);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadScheduleId() {
        try {
            List<String> types = scheduleDAO.getIds();
            cmbScheduleId.getItems().addAll(types);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadScheduleDetailsTable() {
        ObservableList<ScheduleDetailsTm> tmList = FXCollections.observableArrayList();

        for (ScheduleDetails scheduleDetails : scheduleDetailsList) {
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

    private List<ScheduleDetails> getAllScheduleDetails() {
        List<ScheduleDetails> scheduleDetails = null;
        try {
            scheduleDetails = scheduleDetailsDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return scheduleDetails;
    }
}
