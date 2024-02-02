module com.example.monitorclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;


    opens com.example.monitorclient to javafx.fxml;
    exports com.example.monitorclient;
}