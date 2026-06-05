module com.example.scheduleapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.scheduleapp to javafx.fxml;
    exports com.example.scheduleapp;
}