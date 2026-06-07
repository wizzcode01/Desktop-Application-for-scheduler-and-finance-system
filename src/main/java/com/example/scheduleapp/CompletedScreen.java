package com.example.scheduleapp;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class CompletedScreen {

    private final TaskManager taskManager;
    public CompletedScreen(TaskManager taskManager){
        this.taskManager = taskManager;
    }

    public VBox getScreen() {
        Label header = new Label("Completed Tasks");
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        TableView<Task> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No completed tasks yet."));

        TableColumn<Task, String> taskCol = new TableColumn<>("Task");
        taskCol.setCellValueFactory(new PropertyValueFactory<>("task"));

        TableColumn<Task, String> timeCol = new TableColumn<>("Alarm Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("alarmTime"));

        TableColumn<Task, String> dateCol = new TableColumn<>("Date Added");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getColumns().addAll(taskCol, timeCol, dateCol);

        // Load only completed tasks
        taskManager.loadCompletedTasksIntoTable(table);

        Label statusLabel = new Label("");

//        markBtn.setOnAction(e -> {
//            Task selected = table.getSelectionModel().getSelectedItem();
//            if (selected != null) {
//                taskManager.markTaskCompleteFromUI(selected);
//                selected.setStatus("completed");
//                table.refresh();
//                statusLabel.setText("✅ Marked as completed.");
//            } else {
//                statusLabel.setText("❌ Select a task first.");
//            }
//        });

        VBox screen = new VBox(15, header, table, statusLabel);
        screen.setPadding(new Insets(25));
        VBox.setVgrow(table, Priority.ALWAYS);
        return screen;
    }
}