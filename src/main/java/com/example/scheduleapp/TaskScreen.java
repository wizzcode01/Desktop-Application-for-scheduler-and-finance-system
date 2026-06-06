package com.example.scheduleapp;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class TaskScreen {
    private final TaskManager taskManager = new TaskManager();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public VBox getScreen(){
        Label header = new Label("Add New Task");
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        TextField taskInput = new TextField();
        taskInput.setPromptText("Enter today todo task...");
        taskInput.setPrefWidth(280);

        TextField timeInput = new TextField();
        timeInput.setPromptText("Set alarm time for this task e.g 14:30:00");
        timeInput.setPrefWidth(200);

        Button addBtn = new Button("+ Add Task");
        addBtn.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white;" +
                "-fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 8 16;");



    }

}
