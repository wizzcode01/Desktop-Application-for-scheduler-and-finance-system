module com.example.scheduleapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.scheduleapp to javafx.fxml;
    exports com.example.scheduleapp;
}