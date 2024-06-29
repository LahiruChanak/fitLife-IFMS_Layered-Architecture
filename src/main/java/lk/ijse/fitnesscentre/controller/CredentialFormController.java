package lk.ijse.fitnesscentre.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

import static lk.ijse.fitnesscentre.util.GMailer.generateRandomNumber;

public class CredentialFormController {

    public AnchorPane rootNode;
    public Pane credentialPane;

    public void initialize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/loginPane.fxml"));

        Pane pane = fxmlLoader.load();
        credentialPane.getChildren().clear();
        credentialPane.getChildren().add(pane);
    }

    public static boolean correctOTP(String otp) {
        return otp.equals(generateRandomNumber());
    }
}

