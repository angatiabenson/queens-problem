package sample;

import animatefx.animation.BounceIn;
import animatefx.animation.FadeOut;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import static java.lang.Thread.sleep;

public class Splash implements Initializable {

    @FXML
    private AnchorPane anchorFirst;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorFirst.getStyleClass().add("splash_background");
        Runnable runnable = () -> {
            try {
                    sleep(5000);
                Platform.runLater(() -> {
                    new FadeOut(anchorFirst).play();
                    Stage stage = (Stage) anchorFirst.getScene().getWindow();
                    new Main().display(stage);
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();
    }
}
