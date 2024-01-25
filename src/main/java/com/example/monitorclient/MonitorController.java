package com.example.monitorclient;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class MonitorController {

    @FXML
    private WebView myWebView;

    @FXML
    public void initialize() {
        WebEngine engine = myWebView.getEngine();
        engine.load("https://" + "nowrealestate.gr/property/6391");

        engine.getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                System.out.println("Page has been loaded");
            } else if (newValue == Worker.State.FAILED) {
                System.out.println("Loading failed");
            }
        });

    }
}