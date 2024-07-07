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
import lk.ijse.fitnesscentre.bo.BOFactory;
import lk.ijse.fitnesscentre.bo.custom.ScheduleBO;
import lk.ijse.fitnesscentre.bo.custom.TrainerBO;
import lk.ijse.fitnesscentre.bo.custom.TrainerDetailsBO;
import lk.ijse.fitnesscentre.dto.TrainerDetailsDTO;
import lk.ijse.fitnesscentre.entity.Schedule;
import lk.ijse.fitnesscentre.entity.Trainer;
import lk.ijse.fitnesscentre.view.tdm.TrainerDetailsTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerDetailsFormController {
    public AnchorPane trainerDetailsPane;

    public JFXComboBox<String> cmbScheduleId;
    public JFXComboBox<String> cmbTrainerId;

    public TableColumn colScheduleName;
    public TableColumn colTrainerName;
    public TableColumn colScheduleId;
    public TableColumn colTrainerId;

    public JFXTextField txtScheduleName;
    public JFXTextField txtTrainerName;

    @FXML
    private TableView<TrainerDetailsTm> tblTrainerDetails;

    @FXML
    private List<TrainerDetailsDTO> trainerDetailsList = new ArrayList<>();

    //BO Objects
    TrainerDetailsBO trainerDetailsBO = (TrainerDetailsBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.TRAINER_DETAILS);
    ScheduleBO scheduleBO = (ScheduleBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.SCHEDULE);
    TrainerBO trainerBO = (TrainerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.TRAINER);

    public void initialize() {
        this.trainerDetailsList = getAllTrainerDetails();
        setCellValueFactory();
        loadTrainerDetailsTable();
        loadScheduleId();
        loadTrainerId();
    }

    @FXML
    void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String scheduleId = cmbScheduleId.getValue();
        String trainerId = cmbTrainerId.getValue();

        if (scheduleId == null || trainerId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select values for Schedule ID and Trainer ID.").show();
            return;
        }

        TrainerDetailsDTO dto = new TrainerDetailsDTO(scheduleId, trainerId);

        try {
            boolean isAdded = trainerDetailsBO.addTrainerDetails(dto);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Trainer Details Saved.").show();
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
        String trainerId = cmbTrainerId.getValue();

        if (scheduleId == null || trainerId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select values for Schedule ID and Trainer ID.").show();
            return;
        }

        TrainerDetailsDTO dto = new TrainerDetailsDTO(scheduleId, trainerId);

        try {
            boolean isUpdated = trainerDetailsBO.updateTrainerDetails(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Trainer Details Updated.").show();
                clearField();
                refreshTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String scheduleId = cmbScheduleId.getValue();
        String trainerId = cmbTrainerId.getValue();

        if (scheduleId == null || trainerId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select values for Schedule ID and Trainer ID.").show();
            return;
        }

        try {
            boolean isDeleted = trainerDetailsBO.deleteTrainerDetails(scheduleId, trainerId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Trainer Details Deleted.").show();
                clearField();
                refreshTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearField() {
        cmbScheduleId.setValue(null);
        txtScheduleName.clear();
        cmbTrainerId.setValue(null);
        txtTrainerName.clear();
    }

    public void refreshTable() {
        this.trainerDetailsList = getAllTrainerDetails();
        loadTrainerDetailsTable();
    }

    private void loadTrainerId() {
        try {
            List<String> types = trainerBO.getTrainerIds();
            cmbTrainerId.getItems().addAll(types);
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

    public void cmbTrainerIdSearchOnAction() {
        String trainerId = cmbTrainerId.getValue();
        try {
            Trainer trainer = trainerBO.searchByTrainerId(trainerId);
            if (trainer != null) {
                txtTrainerName.setText(trainer.getTrainerName());
                txtTrainerName.setEditable(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTrainerDetailsTable() {
        ObservableList<TrainerDetailsTm> tmList = FXCollections.observableArrayList();

        for (TrainerDetailsDTO trainerDetails : trainerDetailsList) {
            TrainerDetailsTm trainerDetailsTm = new TrainerDetailsTm(
                    trainerDetails.getScheduleId(),
                    trainerDetails.getScheduleName(),
                    trainerDetails.getTrainerId(),
                    trainerDetails.getTrainerName()
            );

            tmList.add(trainerDetailsTm);
        }
        tblTrainerDetails.setItems(tmList);
        tblTrainerDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cmbScheduleId.setValue(newValue.getScheduleId());
                txtScheduleName.setText(newValue.getScheduleName());
                cmbTrainerId.setValue(newValue.getTrainerId());
                txtTrainerName.setText(newValue.getTrainerName());
            }
        });
    }

    private void setCellValueFactory() {
        colScheduleId.setCellValueFactory(new PropertyValueFactory<>("scheduleId"));
        colScheduleName.setCellValueFactory(new PropertyValueFactory<>("scheduleName"));
        colTrainerId.setCellValueFactory(new PropertyValueFactory<>("trainerId"));
        colTrainerName.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
    }

    private List<TrainerDetailsDTO> getAllTrainerDetails() {
        List<TrainerDetailsDTO> trainerDetailsList = null;
        try {
            trainerDetailsList = trainerDetailsBO.getAllTrainerDetails();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trainerDetailsList;
    }

}
