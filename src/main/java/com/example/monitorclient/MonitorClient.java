package com.example.monitorclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MonitorClient extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MonitorClient.class.getResource("monitorApp.fxml"));
        Parent root = fxmlLoader.load();
        stage.initStyle(StageStyle.UNDECORATED);
        try {
            Image image = new Image("logo.png");
            if (image.isError()) {
                throw new RuntimeException(image.exceptionProperty().toString());
            }
            stage.getIcons().add(image);
        }
        catch(RuntimeException re)
        { System.out.println("Failed to load the Icon: " + re.getMessage()); }
        stage.setTitle("");
        stage.setScene(new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}