package lk.ijse.fitnesscentre.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class MainFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Pane mainPane;

    @FXML
    protected JFXButton btnHome;

    @FXML
    private JFXButton btnMember;

    @FXML
    private JFXButton btnMembership;

    @FXML
    private JFXButton btnSchedule;

    @FXML
    private JFXButton btnStore;

    @FXML
    private JFXButton btnTrainer;

    public void initialize() throws IOException {
        setButtonActive(btnHome);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashBoard_form.fxml"));

        Pane registerPane = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);
    }

    @FXML
    void btnExitOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/credential_form.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnHome);

        URL resource = getClass().getResource("/view/dashboard_form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), mainPane);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    @FXML
    void btnMemberOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnMember);

        URL resource = getClass().getResource("/view/member_form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), mainPane);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    @FXML
    void btnMembershipOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnMembership);

        URL resource = getClass().getResource("/view/membership_form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), mainPane);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    @FXML
    void btnScheduleOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnSchedule);

        URL resource = getClass().getResource("/view/schedule_form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), mainPane);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    @FXML
    void btnStoreOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnStore);
        URL resource = getClass().getResource("/view/store_form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), mainPane);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    @FXML
    void btnTrainerOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnTrainer);
        URL resource = getClass().getResource("/view/trainer_form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), mainPane);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    private void setButtonActive(JFXButton button) {

        btnHome.getStyleClass().removeAll("jfx-active-button");
        btnMember.getStyleClass().removeAll("jfx-active-button");
        btnMembership.getStyleClass().removeAll("jfx-active-button");
        btnSchedule.getStyleClass().removeAll("jfx-active-button");
        btnStore.getStyleClass().removeAll("jfx-active-button");
        btnTrainer.getStyleClass().removeAll("jfx-active-button");

        // Add Style
        button.getStyleClass().add("jfx-active-button");
    }
}
