package lk.ijse.fitnesscentre.controller;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;

public class QrScannerController {

    public AnchorPane scannerPane;

    private Webcam webcam;
    private volatile boolean isThreadRunning = true;

    @FXML
    private ImageView imageView;

    AttendanceFormController attendanceFormController;

    @FXML
    void initialize() {
        webcam = Webcam.getDefault();
        webcam.open();

        new Thread(this::readQRCode).start();
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        stopWebcamAndClose();
    }

    private void stopWebcamAndClose() {
        isThreadRunning = false;
        webcam.close();
        Platform.runLater(() -> {
            Stage window= (Stage) imageView.getScene().getWindow();
            window.close();
        });
    }

    public void setAttendanceFormController(AttendanceFormController attendanceFormController) {
        this.attendanceFormController=attendanceFormController;
    }

    private void readQRCode() {
        while (isThreadRunning) {
            try {
                BufferedImage image = webcam.getImage();
                Image fxImage = SwingFXUtils.toFXImage(image, null);

                // Update the ImageView on the JavaFX thread
                Platform.runLater(() -> imageView.setImage(fxImage));

                Result result = null;
                BufferedImage timage = null;

                if (webcam.isOpen()) {
                    if ((timage = webcam.getImage()) == null) {
                        continue;
                    }
                }

                LuminanceSource source = new BufferedImageLuminanceSource(timage);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {
                    // QR code not found in the current frame
                }

                if (result != null) {
                    stopWebcamAndClose();
                    String memberId = result.getText();
                    attendanceFormController.qrScanner(memberId);
                }

                Thread.sleep(33); // Capture frames at 30 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
