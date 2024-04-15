package com.example.monitorclient;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MonitorController {

    @FXML
    private WebView myWebView;

    @FXML
    public void initialize() {
        myWebView.sceneProperty().addListener(new ChangeListener<javafx.scene.Scene>() {
            @Override
            public void changed(ObservableValue<? extends javafx.scene.Scene> observable, javafx.scene.Scene oldValue, javafx.scene.Scene newValue) {
                if (newValue != null) {
                    newValue.getAccelerators().put(
                            new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN),
                            () -> {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("popup.fxml"));
                                    Parent root = loader.load();
                                    Stage stage = new Stage();
                                    stage.setScene(new Scene(root));
                                    stage.showAndWait();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                }
            }
        });

        WebEngine engine = myWebView.getEngine();
        engine.setUserStyleSheetLocation(Objects.requireNonNull(getClass().getResource("/hideScrollBar.css")).toExternalForm());
        engine.load("https://nowrealestate.gr/property/6066");
    }
    @FXML
    public void Forcedurl(String URL) {
        WebEngine engine = myWebView.getEngine();
        engine.setUserStyleSheetLocation(Objects.requireNonNull(getClass().getResource("/hideScrollBar.css")).toExternalForm());
        engine.load(URL);
    }
}