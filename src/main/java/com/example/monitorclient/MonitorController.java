package com.example.monitorclient;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Objects;


public class MonitorController {

    @FXML
    private WebView myWebView;

    @FXML
    public void initialize() {
        WebEngine engine = myWebView.getEngine();
        engine.setJavaScriptEnabled(true);
        engine.setUserStyleSheetLocation(Objects.requireNonNull(getClass().getResource("/hideScrollBar.css")).toExternalForm());
        engine.load("https://nowrealestate.gr/property/6066");

    }
}