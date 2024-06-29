package lk.ijse.fitnesscentre.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.fitnesscentre.entity.Trainer;
import lk.ijse.fitnesscentre.view.tdm.TrainerTm;
import lk.ijse.fitnesscentre.dao.custom.impl.TrainerDAOImpl;
import lk.ijse.fitnesscentre.util.Regex;
import lk.ijse.fitnesscentre.util.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerFormController {
    public AnchorPane rootNode;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtExperience;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtTrainerId;

    public TableColumn colTrainerId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colExperience;

    @FXML
    private TableView<TrainerTm> tblTrainer;

    private List<Trainer> trainerList = new ArrayList<>();

    TrainerDAOImpl trainerDAO = new TrainerDAOImpl();

    //Button Actions

    public void btnTrainerDetailsOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/trainerDetails_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Trainer Details Form");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException {
        String trainerId = txtTrainerId.getText();
        String trainerName = txtName.getText();
        String trainerAddress = txtAddress.getText();
        String trainerContact = txtContact.getText();
        String trainerExperience = txtExperience.getText();

        Trainer trainer = new Trainer(trainerId, trainerName, trainerAddress, trainerContact, trainerExperience);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isAdded = trainerDAO.add(trainer);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Trainer Added.").show();
                clearField();
                refreshTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) { clearField(); }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String trainerId = txtTrainerId.getText();
        String trainerName = txtName.getText();
        String trainerAddress = txtAddress.getText();
        String trainerContact = txtContact.getText();
        String trainerExperience = txtExperience.getText();

        Trainer trainer = new Trainer(trainerId, trainerName, trainerAddress, trainerContact, trainerExperience);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isUpdated = trainerDAO.update(trainer);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Trainer Updated.").show();
                refreshTable();
                clearField();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String trainerId = txtTrainerId.getText();

        try {
            boolean isDeleted = trainerDAO.delete(trainerId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Trainer Deleted.").show();
                refreshTable();
                clearField();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) { txtTrainerIdSearchOnAction(actionEvent); }

        //Extra Functions

    private void clearField() {
        txtTrainerId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtExperience.clear();
    }

    public void txtTrainerIdSearchOnAction(ActionEvent actionEvent) {
        String trainerId = txtTrainerId.getText();

        try {
            Trainer trainer = trainerDAO.searchById(trainerId);

            if (trainer != null) {
                txtTrainerId.setText(trainer.getTrainerId());
                txtName.setText(trainer.getTrainerName());
                txtAddress.setText(trainer.getTrainerAddress());
                txtContact.setText(trainer.getTrainerContact());
                txtExperience.setText(trainer.getTrainerExperience());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadTrainerTable() {
        ObservableList<TrainerTm> tmList = FXCollections.observableArrayList();

        for (Trainer trainer : trainerList) {
            TrainerTm trainerTm = new TrainerTm(
                    trainer.getTrainerId(),
                    trainer.getTrainerName(),
                    trainer.getTrainerAddress(),
                    trainer.getTrainerContact(),
                    trainer.getTrainerExperience()
            );

            tmList.add(trainerTm);
        }
        tblTrainer.setItems(tmList);
        TrainerTm selectedItem = tblTrainer.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colTrainerId.setCellValueFactory(new PropertyValueFactory<>("trainerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("trainerAddress"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("trainerContact"));
        colExperience.setCellValueFactory(new PropertyValueFactory<>("trainerExperience"));
    }

    private List<Trainer> getAllTrainers() {
        List<Trainer> trainerList = null;
        try {
            trainerList = trainerDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trainerList;
    }

    public void refreshTable() {
        this.trainerList =getAllTrainers();
        loadTrainerTable();
    }

    private void loadNextId() {
        try {
            String currentId = trainerDAO.currentId();
            String nextId = nextPaymentId(currentId);

            txtTrainerId.setText(nextId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextPaymentId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("T");
            int id = Integer.parseInt(split[1]);
            return "T" + String.format("%03d", ++id);
        }
        return "T001";
    }

    public void initialize() {
        loadNextId();
        this.trainerList =getAllTrainers();
        loadTrainerTable();
        setCellValueFactory();
    }

    //VALIDATION
    public void txtTrainerIdOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.TRAINERID,txtTrainerId); }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.NAME,txtName); }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.ADDRESS,txtAddress); }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.CONTACT,txtContact); }

    public void txtExperienceOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.EXPERIENCE,txtExperience); }

    public String isValid(){

        String message = "";

        if (!Regex.setTextColor(TextField.TRAINERID,txtTrainerId))
            message += "TrainerId must be starts with 'T' and exactly three digits.\n\n";

        if (!Regex.setTextColor(TextField.NAME,txtName))
            message += "Name must be at least 3 letters.\n\n";

        if (!Regex.setTextColor(TextField.ADDRESS,txtAddress))
            message += "Address must be has at least 4 characters and special characters (-/,.@+).\n\n";

        if (!Regex.setTextColor(TextField.CONTACT,txtContact))
            message += "Contact must be starts with either +94, or 07\n\n";

        if (!Regex.setTextColor(TextField.EXPERIENCE,txtExperience))
            message += "Experience must be numbers and type as 'years' or 'months' at end.\n\n";

        return message.isEmpty() ? null : message;
    }
}
