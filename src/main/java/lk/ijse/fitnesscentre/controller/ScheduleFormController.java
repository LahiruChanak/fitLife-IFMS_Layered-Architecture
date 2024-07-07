package lk.ijse.fitnesscentre.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.fitnesscentre.bo.BOFactory;
import lk.ijse.fitnesscentre.bo.custom.ScheduleBO;
import lk.ijse.fitnesscentre.dto.ScheduleDTO;
import lk.ijse.fitnesscentre.entity.Schedule;
import lk.ijse.fitnesscentre.view.tdm.ScheduleTm;
import lk.ijse.fitnesscentre.dao.custom.impl.ScheduleDAOImpl;
import lk.ijse.fitnesscentre.util.Regex;
import lk.ijse.fitnesscentre.util.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleFormController {
    public AnchorPane rootNode;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtScheduleId;

    public TableColumn colScheduleId;
    public TableColumn colName;
    public TableColumn colDescription;

    @FXML
    private TableView<ScheduleTm> tblSchedule;

    private List<ScheduleDTO> scheduleList = new ArrayList<>();

    ScheduleBO scheduleBO = (ScheduleBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.SCHEDULE);


    //Button Actions

    public void btnScheduleDetailsOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/scheduleDetails_form.fxml"));
        Parent pane = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(rootNode.getScene().getWindow());
        stage.setScene(new Scene(pane));
        stage.setTitle("Schedule Details Form");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnAddOnAction(ActionEvent actionEvent) throws SQLException {
        String scheduleId = txtScheduleId.getText();
        String scheduleName = txtName.getText();
        String description = txtDescription.getText();

        ScheduleDTO dto =new ScheduleDTO(scheduleId,scheduleName, description);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isAdded = scheduleBO.addSchedule(dto);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Schedule Added.").show();
                clearField();
                refreshTable();
                loadNextId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadScheduleTable();
    }

    @FXML
    void btnClearOnAction(ActionEvent actionEvent) { clearField(); }

    @FXML
    void btnUpdateOnAction(ActionEvent actionEvent) {
        String scheduleId = txtScheduleId.getText();
        String scheduleName = txtName.getText();
        String description = txtDescription.getText();

        ScheduleDTO dto =new ScheduleDTO(scheduleId,scheduleName, description);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isUpdated = scheduleBO.updateSchedule(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Schedule Updated.").show();
                clearField();
                refreshTable();
                loadNextId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent actionEvent) {
        String scheduleId = txtScheduleId.getText();

        try {
            boolean isDeleted = scheduleBO.deleteSchedule(scheduleId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Schedule Deleted.").show();
                clearField();
                refreshTable();
                loadNextId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent actionEvent) { txtScheduleIdSearchOnAction(actionEvent); }

    //Extra Functions

    private void clearField() {
        txtScheduleId.setText("");
        txtName.setText("");
        txtDescription.setText("");
    }

    public void txtScheduleIdSearchOnAction(ActionEvent actionEvent) {
        String scheduleId = txtScheduleId.getText();

        try {
            Schedule schedule = scheduleBO.searchByScheduleId(scheduleId);

            if (schedule != null) {
                txtScheduleId.setText(schedule.getScheduleId());
                txtName.setText(schedule.getScheduleName());
                txtDescription.setText(schedule.getDescription());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadScheduleTable() {
        ObservableList<ScheduleTm> tmList = FXCollections.observableArrayList();

        for (ScheduleDTO schedule : scheduleList) {
            ScheduleTm scheduleTm = new ScheduleTm(
                    schedule.getScheduleId(),
                    schedule.getScheduleName(),
                    schedule.getDescription()
            );

            tmList.add(scheduleTm);
        }
        tblSchedule.setItems(tmList);
        ScheduleTm selectedItem = tblSchedule.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colScheduleId.setCellValueFactory(new PropertyValueFactory<>("scheduleId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("scheduleName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleList = null;
        try {
            scheduleList = scheduleBO.getAllSchedules();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return scheduleList;
    }

    public void refreshTable() {
        this.scheduleList =getAllSchedules();
        loadScheduleTable();
    }

    private void loadNextId() {
        try {
            String currentId = scheduleBO.currentScheduleId();
            String nextId = nextPaymentId(currentId);

            txtScheduleId.setText(nextId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextPaymentId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("S");
            int id = Integer.parseInt(split[1]);
            return "S" + String.format("%03d", ++id);
        }
        return "S001";
    }

    public void initialize() {
        loadNextId();
        this.scheduleList =getAllSchedules();
        loadScheduleTable();
        setCellValueFactory();
    }

    //VALIDATION
    public void txtScheduleIdOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.SCHEDULEID,txtScheduleId); }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.NAME,txtName); }

    public void txtDescriptionOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.DESCRIPTION,txtDescription); }

    public String isValid(){

        String message = "";

        if (!Regex.setTextColor(TextField.SCHEDULEID,txtScheduleId))
            message += "ScheduleId must be starts with 'S' and exactly three digits.\n\n";

        if (!Regex.setTextColor(TextField.NAME,txtName))
            message += "Name must be at least 3 letters.\n\n";

        if (!Regex.setTextColor(TextField.DESCRIPTION,txtDescription))
            message += "Description can has letters, digits, underscores, and punctuation characters. \n\n";

        return message.isEmpty() ? null : message;
    }
}