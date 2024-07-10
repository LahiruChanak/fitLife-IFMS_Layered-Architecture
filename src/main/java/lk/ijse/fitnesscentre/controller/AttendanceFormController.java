package lk.ijse.fitnesscentre.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.fitnesscentre.bo.BOFactory;
import lk.ijse.fitnesscentre.bo.custom.AttendanceBO;
import lk.ijse.fitnesscentre.bo.custom.MemberBO;
import lk.ijse.fitnesscentre.dto.AttendanceDTO;
import lk.ijse.fitnesscentre.dto.MemberDTO;
import lk.ijse.fitnesscentre.entity.Attendance;
import lk.ijse.fitnesscentre.entity.Member;
import lk.ijse.fitnesscentre.view.tdm.AttendanceTm;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFormController {

    @FXML
    private AnchorPane attendancePane;

    @FXML
    private TableColumn<?, ?> colAttendanceId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colMemberId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<AttendanceTm> tblAttendance;

    @FXML
    private JFXTextField txtAttendDate;

    @FXML
    public JFXTextField txtAttendTime;

    @FXML
    private JFXTextField txtAttendanceId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXComboBox<String> cmbMemberId;

    public JFXButton btnScan;

    private List<AttendanceDTO> attendanceList = new ArrayList<>();

    AttendanceBO attendanceBO = (AttendanceBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ATTENDANCE);
    MemberBO memberBO = (MemberBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MEMBER);



    public void initialize() {
        this.attendanceList = getAllAttendance();
        loadAttendanceTable();
        setCellValueFactory();
        loadNextAttendId();
        loadMemberId();
        setAttendDate();
        setAttendTime();

        btnScan.setTooltip(new Tooltip("Scan"));
    }

    @FXML
    void btnAttendOnAction(ActionEvent event) throws SQLException {
        String attendanceId = txtAttendanceId.getText();
        String memberName = txtName.getText();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String memberId = cmbMemberId.getValue();

        AttendanceDTO dto =new AttendanceDTO(attendanceId,memberName,date,time,memberId);

        try {
            boolean isAdded = attendanceBO.addAttendance(dto);
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Attendance saved!").show();
                clearField();
                refreshTable();
                loadNextAttendId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearField() {
        cmbMemberId.setValue(null);
        txtName.clear();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/member_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        attendancePane.getChildren().clear();
        attendancePane.getChildren().add(registerPane);
    }

    @FXML
    void btnScannerOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/qrScanner.fxml"));
        Parent rootNode = loader.load();

        QrScannerController qrScannerFormController = loader.getController();
        qrScannerFormController.setAttendanceFormController(this);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(attendancePane.getScene().getWindow());
        stage.setScene(new Scene(rootNode));
        stage.setTitle("QR Scanner");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public void txtAttIdSearchOnAction(ActionEvent actionEvent) {
        String attendanceId = txtAttendanceId.getText();
        try {
            AttendanceDTO attendance = attendanceBO.searchByAttendanceId(attendanceId);
            if (attendance != null) {
                cmbMemberId.setValue(attendance.getMemberId());
                txtName.setText(attendance.getMemberName());
                txtAttendDate.setText(String.valueOf(attendance.getDate()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbMemberIdSearchOnAction() {
        String memberId = cmbMemberId.getValue();

        try {
            MemberDTO member = memberBO.searchByMemberId(memberId);
            if (member != null) {
                txtName.setText(member.getMemberName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        txtName.setEditable(false);
        txtAttendDate.setEditable(false);
        txtAttendTime.setEditable(false);
        setAttendDate();
        setAttendTime();
    }

    private void loadNextAttendId() {
        try {
            String currentId = attendanceBO.currentAttendanceId();
            String nextId = nextAttendId(currentId);

            txtAttendanceId.setText(nextId);
            txtAttendanceId.setEditable(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextAttendId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("AT");
            int id = Integer.parseInt(split[1]);
            return "AT" + String.format("%03d", ++id);
        }
        return "AT001";
    }

    private void setAttendDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        txtAttendDate.setText(formattedDate);
        txtAttendDate.setEditable(false);
    }

    private void setAttendTime() {
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            txtAttendTime.setText(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));
        }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        txtAttendTime.setEditable(false);
    }

    private void setCellValueFactory() {
        colAttendanceId.setCellValueFactory(new PropertyValueFactory<>("attendanceId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
    }

    private List<AttendanceDTO> getAllAttendance() {
        List<AttendanceDTO> attendanceList = null;

        try {
            attendanceList = attendanceBO.getAllAttendances();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return attendanceList;
    }

    private void loadAttendanceTable() {
        ObservableList<AttendanceTm> tmList = FXCollections.observableArrayList();

        for (AttendanceDTO attendance : attendanceList) {
            AttendanceTm attendanceTm = new AttendanceTm(
                    attendance.getAttendanceId(),
                    attendance.getMemberName(),
                    attendance.getDate(),
                    attendance.getTime(),
                    attendance.getMemberId()
            );
            tmList.add(attendanceTm);
        }
        tblAttendance.setItems(tmList);
        AttendanceTm selectedItem = tblAttendance.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    public void refreshTable() throws SQLException {
        this.attendanceList = getAllAttendance();
        loadAttendanceTable();
    }

    public void qrScanner(String memberId){
        cmbMemberId.setValue(memberId);

        Platform.runLater(() -> {
            try{
                MemberDTO dto = memberBO.searchByMemberId(memberId);
                if (dto == null){
                    new Alert(Alert.AlertType.WARNING, "Invalid Member ID").show();
                } else {
                    cmbMemberIdSearchOnAction();
                }
            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        });
    }

    public void cmbMemberIdOnAction() {
        String memberId = cmbMemberId.getValue();
        try {
            MemberDTO dto = memberBO.searchByMemberId(memberId);
            if (dto != null) {
                txtName.setText(dto.getMemberName());
                txtName.setEditable(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMemberId() {
        try {
            List<String> types = memberBO.getMemberIds();
            cmbMemberId.getItems().addAll(types);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}