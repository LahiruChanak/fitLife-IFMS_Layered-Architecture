package lk.ijse.fitnesscentre.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.fitnesscentre.bo.BOFactory;
import lk.ijse.fitnesscentre.bo.custom.MemberBO;
import lk.ijse.fitnesscentre.bo.custom.MembershipBO;
import lk.ijse.fitnesscentre.dto.MemberDTO;
import lk.ijse.fitnesscentre.entity.Member;
import lk.ijse.fitnesscentre.view.tdm.MemberTm;
import lk.ijse.fitnesscentre.util.Regex;
import lk.ijse.fitnesscentre.util.TextField;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MemberFormController {

    public AnchorPane memberPane;

    public JFXButton btnAttendance;

    @FXML
    private TableColumn<?, ?> colMemberId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDoB;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colMembershipId;

    @FXML
    private TableColumn<?, ?> colStartDate;

    @FXML
    private TableColumn<?, ?> colEndDate;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtDoB;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtMemberId;

    @FXML
    private JFXTextField txtName;

    @FXML
    public JFXTextField txtStartDate;

    @FXML
    public JFXTextField txtEndDate;

    @FXML
    private JFXComboBox<String> cmbMembershipId;

    @FXML
    public JFXComboBox<String> cmbGender;

    @FXML
    private TableView<MemberTm> tblMember;

    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnClear;
    public JFXButton btnSearch;

    private List<MemberDTO> memberList = new ArrayList<>();

    //Objects
    MemberBO memberBO = (MemberBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MEMBER);
    MembershipBO membershipBO = (MembershipBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MEMBERSHIP);


    @FXML
    void btnAddOnAction(ActionEvent actionEvent) throws IOException {
        String memberId = txtMemberId.getText();
        String memberName = txtName.getText();
        String memberContact = txtContact.getText();
        String dobText = txtDoB.getText();
        String gender = cmbGender.getValue();
        String email = txtEmail.getText();
        String membershipId = cmbMembershipId.getValue();
        String startDateText = txtStartDate.getText();
        String endDateText = txtEndDate.getText();

        Date dob;
        Date startDate;
        Date endDate;

        try {
            dob = Date.valueOf(dobText);
            startDate = Date.valueOf(startDateText);
            endDate = Date.valueOf(endDateText);
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.WARNING, "Please enter valid dates.").show();
            return;
        }

        MemberDTO dto = new MemberDTO(memberId, memberName, memberContact, dob, gender, email, membershipId, startDate, endDate);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isAdded = memberBO.addMember(dto);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Member Saved.").showAndWait();
                clearField();
                refreshTable();
                loadNextMemberId();
                setStartDate();
                loadPaymentWindow();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loadPaymentWindow() throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/paid_form.fxml"));
        Parent pane = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(memberPane.getScene().getWindow());
        stage.setScene(new Scene(pane));
        stage.setTitle("Payment Form");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void btnClearOnAction(ActionEvent actionEvent) { clearField(); }

    @FXML
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String memberId = txtMemberId.getText();
        String memberName = txtName.getText();
        String memberContact = txtContact.getText();
        String dobText = txtDoB.getText();
        String gender = cmbGender.getValue();
        String email = txtEmail.getText();
        String membershipId = cmbMembershipId.getValue();
        String startDateText = txtStartDate.getText();
        String endDateText = txtEndDate.getText();

        Date dob;
        Date startDate;
        Date endDate;

        try {
            dob = Date.valueOf(dobText);
            startDate = Date.valueOf(startDateText);
            endDate = Date.valueOf(endDateText);
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.WARNING, "Please enter valid dates.").show();
            return;
        }

        MemberDTO dto = new MemberDTO(memberId, memberName, memberContact, dob, gender, email, membershipId, startDate, endDate);

        String errorMessage = isValid();

        if (errorMessage != null) {
            new Alert(Alert.AlertType.ERROR, errorMessage).show();
            return;
        }

        try {
            boolean isUpdated = memberBO.updateMember(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Member Updated.").show();
                clearField();
                refreshTable();
                loadNextMemberId();
                setStartDate();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String memberId = txtMemberId.getText();

        try {
            boolean isDeleted = memberBO.deleteMember(memberId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Member Deleted.").show();
                clearField();
                refreshTable();
                loadNextMemberId();
                setStartDate();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) { txtMemberIdSearchOnAction(actionEvent); }

    //Other Functions

    private void clearField() {
        txtMemberId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        cmbGender.setValue(null);
        txtContact.setText("");
        txtDoB.setText("");
        cmbMembershipId.setValue(null);
        txtStartDate.setText("");
        txtEndDate.setText("");
    }

    private void setStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        txtStartDate.setText(formattedDate);
    }

    public void setEndDate() throws SQLException {
        String type = membershipBO.getEndDate(cmbMembershipId.getValue());
        String startDateText = txtStartDate.getText();

        LocalDate startDate = LocalDate.parse(startDateText); // Parse LocalDate directly

        LocalDate endDate = null;

        switch (type) {
            case "Monthly":
                endDate = startDate.plusMonths(1);
                break;

            case "3 Months":
                endDate = startDate.plusMonths(3);
                break;

            case "6 Months":
                endDate = startDate.plusMonths(6);
                break;

            case "Annual":
                endDate = startDate.plusYears(1);
                break;

            default:
                break;
        }

        if (endDate.getDayOfMonth() != startDate.getDayOfMonth()) {
            endDate = endDate.withDayOfMonth(1).plusMonths(1);
        }

        // Format the end date to a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedEndDate = endDate.format(formatter);
        txtEndDate.setText(formattedEndDate);
    }


    public void refreshTable() {
        this.memberList = getAllMembers();
        loadMemberTable();
    }

    @FXML
    public void txtMemberIdSearchOnAction(ActionEvent actionEvent) {
        String memberId = txtMemberId.getText();

        try {
            MemberDTO member = memberBO.searchByMemberId(memberId);

            if (member != null) {
                txtMemberId.setText(member.getMemberId());
                txtName.setText(member.getMemberName());
                txtContact.setText(member.getMemberContact());
                txtDoB.setText(String.valueOf(member.getDateOfBirth()));
                cmbGender.setValue(member.getGender());
                txtEmail.setText(member.getEmail());
                cmbMembershipId.setValue(member.getMembershipId());
                txtStartDate.setText(String.valueOf(member.getStartDate()));
                txtEndDate.setText(String.valueOf(member.getEndDate()));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Member ID not found.").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtContactSearchOnAction(ActionEvent actionEvent) {
        String memberContact = txtContact.getText();

        try {
            MemberDTO member = memberBO.searchByContact(memberContact);

            if (member != null) {
                txtMemberId.setText(member.getMemberId());
                txtName.setText(member.getMemberName());
                txtContact.setText(member.getMemberContact());
                txtDoB.setText(String.valueOf(member.getDateOfBirth()));
                cmbGender.setValue(member.getGender());
                txtEmail.setText(member.getEmail());
                cmbMembershipId.setValue(member.getMembershipId());
                txtStartDate.setText(String.valueOf(member.getStartDate()));
                txtEndDate.setText(String.valueOf(member.getEndDate()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbGenderOnAction(ActionEvent event) {
        if (cmbGender.getItems().isEmpty()) {
            loadGenderTypes();
        }
    }

    @FXML
    void cmbMembershipIdOnAction(ActionEvent actionEvent) throws SQLException {
        if (cmbMembershipId.getItems().isEmpty()) {
            loadMembershipIds();
        }
        setEndDate();
    }

    private void loadGenderTypes() {
        ObservableList<String> genderTypes = FXCollections.observableArrayList("Male", "Female");
        cmbGender.setItems(genderTypes);
    }

    private void loadMembershipIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = membershipBO.getMembershipIds();
            for (String id : idList) {
                obList.add(id);
            }
            cmbMembershipId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNextMemberId() {
        try {
            String currentId = memberBO.currentMemberId();
            String nextId = nextMemberId(currentId);

            txtMemberId.setText(nextId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextMemberId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("M");
            int id = Integer.parseInt(split[1]);
            return "M" + String.format("%03d", ++id);
        }
        return "M001";
    }

    private void loadMemberTable() {
        ObservableList<MemberTm> tmList = FXCollections.observableArrayList();

        for (MemberDTO member : memberList) {
            MemberTm memberTm = new MemberTm(
                    member.getMemberId(),
                    member.getMemberName(),
                    member.getMemberContact(),
                    member.getDateOfBirth(),
                    member.getGender(),
                    member.getEmail(),
                    member.getMembershipId(),
                    member.getStartDate(),
                    member.getEndDate()
            );

            tmList.add(memberTm);
        }
        tblMember.setItems(tmList);
        MemberTm selectedItem = tblMember.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("memberContact"));
        colDoB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMembershipId.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    }

    private List<MemberDTO> getAllMembers() {
        List<MemberDTO> memberList = null;
        try {
            memberList = memberBO.getAllMember();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return memberList;
    }

    public void initialize() throws SQLException {
        loadNextMemberId();
        setStartDate();
        this.memberList =getAllMembers();
        loadMemberTable();
        setCellValueFactory();
        loadGenderTypes();
        loadMembershipIds();
        hoverText();
    }

    public void hoverText(){
        btnAdd.setTooltip(new Tooltip("Add"));
        btnUpdate.setTooltip(new Tooltip("Update"));
        btnDelete.setTooltip(new Tooltip("Delete"));
        btnSearch.setTooltip(new Tooltip("Search"));
        btnClear.setTooltip(new Tooltip("Clear"));
    }

    public void btnAttendanceOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/attendance_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        memberPane.getChildren().clear();
        memberPane.getChildren().add(registerPane);
    }

    public void txtMemberIdOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.MEMBERID, txtMemberId); }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.NAME, txtName); }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.CONTACT, txtContact); }

    public void txtDOBOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.DATE, txtDoB); }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.EMAIL, txtEmail); }

    public void txtStartDateOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.DATE, txtStartDate); }

    public void txtEndDateOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextField.DATE, txtEndDate); }

    public String isValid(){

        String message = "";

        if (!Regex.setTextColor(TextField.MEMBERID,txtMemberId))
            message += "MemberId must be starts with 'M' and exactly three digits.\n\n";

        if (!Regex.setTextColor(TextField.NAME,txtName))
            message += "Name must be at least 3 letters.\n\n";

        if (!Regex.setTextColor(TextField.CONTACT,txtContact))
            message += "Contact must be starts with either +94, or 07\n\n";

        if (!Regex.setTextColor(TextField.DATE,txtDoB))
            message += "DOB must be yyyy/mm/dd format.\n\n";

        if (!Regex.setTextColor(TextField.EMAIL,txtEmail))
            message += "Enter valid email address.\n\n";

        if (!Regex.setTextColor(TextField.DATE, txtStartDate))
            message += "StartDate must be yyyy/mm/dd format.\n\n";

        if (!Regex.setTextColor(TextField.DATE, txtEndDate))
            message += "EndDate must be yyyy/mm/dd format.\n\n";

        return message.isEmpty() ? null : message;
    }

    public void btnAddOnHover(MouseEvent mouseEvent) {
//        btnAdd.setTooltip(new Tooltip("Add"));
    }

}
