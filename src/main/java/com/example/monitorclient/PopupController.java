package com.example.monitorclient;

import com.example.monitorclient.database.dbconn;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import static com.example.monitorclient.Internal.*;
import static com.example.monitorclient.database.dbconn.initializeConn;
import static com.example.monitorclient.database.dbf.getMonitorPosition;
import static com.example.monitorclient.MonitorController.*;
import static com.example.monitorclient.database.dbf.setMonitorPosition;

public class PopupController {

    @FXML
    public Text dbconn_status;
    @FXML
    public ListView dataLIst;
    @FXML
    public TextField url_filed;
    @FXML
    public TextField mrw_filed;
    @FXML
    public TextField mid_filed;
    @FXML
    public TextField mcol_filed;
    @FXML
    public TextField port;
    @FXML
    public TextField password;
    @FXML
    public TextField username;
    @FXML
    public TextField dbname;
    @FXML
    public TextField dbhost_ip;
    @FXML
    public Button Fbutton;


    @FXML
    public void initialize() {

        //initialize datalist with default values
        String[] datalogs = getLastLogFromLogCSVFile();
        String[] data = getDatafile();
        dataLIst.setItems(FXCollections.observableArrayList(
                "Startup time: "+ datalogs[1],
                "Last heartbeat: " + datalogs[3],
                "Monitor ip: "+ datalogs[2],
                "Current URL: "+ datalogs[5],
                "Current Logged views (max 500): "+ datalogs[4],
                "Current's advert view time: "+ datalogs[6],
                "Total Views: " + datalogs[7]
        ));

        mid_filed.setText(data[0]);
        dbname.setText(data[1]);
        dbhost_ip.setText(data[2]);
        port.setText(data[3]);
        username.setText(data[4]);
        password.setText(data[5]);
        mrw_filed.setText(data[6]);
        mcol_filed.setText(data[7]);
        if (getForcedState()) {
            Fbutton.setText("Unforce");
            url_filed.setText(getForcedURL());
        }
        else {
            Fbutton.setText("Force URL");
        }
    }

    @FXML
    public void Refresh(ActionEvent actionEvent) {
        String[] datalogs = getLastLogFromLogCSVFile();
        dataLIst.setItems(FXCollections.observableArrayList(
                "Startup time: "+ datalogs[1],
                "Last heartbeat: " + datalogs[3],
                "Monitor ip: "+ datalogs[2],
                "Current URL: "+ datalogs[5],
                "Current Logged views (max 500): "+ datalogs[4],
                "Current's advert view time: "+ datalogs[6],
                "Total Views: " + datalogs[7]
        ));
    }

    @FXML
    public void Forse_url(ActionEvent actionEvent) throws IOException {
        if (getForcedState()) {
            String[] data = new String[]{"0",""};
            setForcedFile(data);
            closeStage();
        }
        else {
            String[] data = new String[]{"1",url_filed.getText()};
            setForcedFile(data);
            closeStage();
        }
    }


    @FXML
    public void DBdata_save(ActionEvent actionEvent) throws IOException {
        Internal.writedbData(dbname.getText(),dbhost_ip.getText(),port.getText(),username.getText(),password.getText());
    }

    @FXML
    public void Mdata_save(ActionEvent actionEvent) throws IOException {
        Internal.writeMonitorData(mid_filed.getText(),mrw_filed.getText(),mcol_filed.getText());
    }

    @FXML
    public void Init_bd_conn(ActionEvent actionEvent) {
        dbconn_status.setText(dbconn.testConnection());
    }

    @FXML
    public void import_from_db(ActionEvent actionEvent) throws SQLException {
        String[] pos = getMonitorPosition(Integer.parseInt(mid_filed.getText()));
        mrw_filed.setText(pos[0]);
        mcol_filed.setText(pos[1]);
    }

    @FXML
    public void force_reboot(ActionEvent actionEvent) throws IOException {
        forceReboot();
    }

    @FXML
    public void update_db(ActionEvent actionEvent) throws SQLException {
        setMonitorPosition(mid_filed.getText(), mrw_filed.getText(), mcol_filed.getText());
    }

    @FXML
    public void reload_csv(ActionEvent actionEvent) {
        String[] data = Internal.getDatafile();
        mid_filed.setText(data[0]);
        dbname.setText(data[1]);
        dbhost_ip.setText(data[2]);
        port.setText(data[3]);
        username.setText(data[4]);
        password.setText(data[5]);
        mrw_filed.setText(data[6]);
        mcol_filed.setText(data[7]);
    }
    @FXML
    public void closeStage(){
        Stage stage = (Stage) url_filed.getScene().getWindow();
        stage.close();
    }
}
