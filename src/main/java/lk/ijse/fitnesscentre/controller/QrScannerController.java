package lk.ijse.fitnesscentre.controller;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class QrScannerController {

    public AnchorPane scannerPane;

    private Webcam webcam;
    private volatile boolean isThreadRunning = true;

    @FXML
    private ImageView imageView;

    AttendanceFormController afc;

    @FXML
    void initialize() {
        webcam = Webcam.getDefault();
        webcam.open();

        // Flip the image horizontally by setting scaleX to -1
        imageView.setScaleX(-1);

        new Thread(this::readQRCode).start();

        // Add handler for the default close button
        Platform.runLater(() -> {
            Stage stage = (Stage) scannerPane.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                stopWebcamAndClose();
                event.consume(); // Prevents the window from closing until stopWebcamAndClose() completes
            });
        });
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        stopWebcamAndClose();
    }

    private void stopWebcamAndClose() {
        isThreadRunning = false;
        if (webcam != null) {
            webcam.close();
        }
        Platform.runLater(() -> {
            Stage window = (Stage) imageView.getScene().getWindow();
            window.close();
        });
    }

    public void setAttendanceFormController(AttendanceFormController afc) {
        this.afc = afc;
    }

    private void readQRCode() {
        while (isThreadRunning) {
            try {
                BufferedImage image = webcam.getImage();
                if (image != null) {
                    Image fxImage = SwingFXUtils.toFXImage(image, null);

                    // Update the ImageView on the JavaFX thread
                    Platform.runLater(() -> imageView.setImage(fxImage));

                    Result result = null;

                    // Optimize QR code detection
                    BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                    try {
                        result = new MultiFormatReader().decode(bitmap);
                    } catch (NotFoundException e) {
                        // No QR code found in this frame
                    }

                    if (result != null) {
                        stopWebcamAndClose();
                        String memberId = result.getText();
                        Platform.runLater(() -> afc.qrScanner(memberId));
                        break;
                    }
                }

                Thread.sleep(66); // Capture frames at approximately 15 FPS
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
