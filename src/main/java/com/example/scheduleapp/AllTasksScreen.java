package com.example.scheduleapp;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class AllTasksScreen {

    private final TaskManager taskManager = new TaskManager();

    public VBox getScreen() {
        Label header = new Label("All Tasks");
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        TableView<Task> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No tasks found."));

        TableColumn<Task, String> taskCol = new TableColumn<>("Task");
        taskCol.setCellValueFactory(new PropertyValueFactory<>("task"));

        TableColumn<Task, String> timeCol = new TableColumn<>("Alarm Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("alarmTime"));

        TableColumn<Task, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(taskCol, timeCol, statusCol);
        taskManager.loadTasksIntoTable(table);

        // DELETE button
        Button deleteBtn = new Button("🗑 Delete Selected");
        deleteBtn.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-cursor: hand;");

        Label statusLabel = new Label("");

        deleteBtn.setOnAction(e -> {
            Task selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                table.getItems().remove(selected);
                taskManager.deleteTaskFromUI(selected);
                statusLabel.setText("🗑 Task deleted.");
            } else {
                statusLabel.setText("❌ Select a task first.");
            }
        });

        VBox screen = new VBox(15, header, table, deleteBtn, statusLabel);
        screen.setPadding(new Insets(25));
        VBox.setVgrow(table, Priority.ALWAYS);
        return screen;
    }
}