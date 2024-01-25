package com.example.monitorclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MonitorClient extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MonitorClient.class.getResource("monitor demo.fxml"));
        Parent root = fxmlLoader.load();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("");
        stage.setScene(new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}