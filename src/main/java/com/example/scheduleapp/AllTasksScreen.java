package com.example.scheduleapp;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.time.LocalTime;

public class AllTasksScreen {

    private final TaskManager taskManager;
    public AllTasksScreen(TaskManager taskManager){
        this.taskManager = taskManager;
    }

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

        TableColumn<Task, String> dateCol = new TableColumn<>("Date Added");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getColumns().addAll(taskCol, timeCol, statusCol, dateCol);
        taskManager.loadTasksIntoTable(table);

        // Mark as complete button
        Button markBtn = new Button("Mark Selected as Completed");
        markBtn.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-cursor: hand;");

        // DELETE button
        Button deleteBtn = new Button("🗑 Delete Selected");
        deleteBtn.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-cursor: hand;");

        HBox inputRow = new HBox(10, deleteBtn, markBtn);
        inputRow.setPadding(new Insets(10, 0, 0, 0));

        Label statusLabel = new Label("");

        markBtn.setOnAction(e -> {
            Task selected = table.getSelectionModel().getSelectedItem();
            if(selected == null){
                statusLabel.setTextFill(Color.RED);
                statusLabel.setText("❌ Select a task first.");
                return;
            }
            if(selected.getStatus().equalsIgnoreCase("completed")){
                statusLabel.setTextFill(Color.ORANGE);
                statusLabel.setText("⚠️ Already completed.");
                return;
            }
            if(selected.getAlarmTime().isAfter(LocalTime.now())){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alarm Not Reached");
                alert.setHeaderText("This task's alarm time has not been reached yet.");
                alert.setContentText("Are you sure you want to mark it as completed?");

                alert.showAndWait().ifPresent(response -> {
                    if(response == ButtonType.OK){
                        selected.setStatus("completed");
                        taskManager.markTaskCompleteFromUI(selected);
                        table.getItems().remove(selected);
                        statusLabel.setTextFill(Color.GREEN);
                        statusLabel.setText("✅ Marked as completed: " + selected.getTask());
                    }
                });
                return;
            }
            selected.setStatus("completed");
            taskManager.markTaskCompleteFromUI(selected);
            table.getItems().remove(selected);
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("✅ Marked as completed: " + selected.getTask());
//            if (selected != null) {
//                taskManager.markTaskCompleteFromUI(selected);
//                selected.setStatus("completed");
//                table.refresh();
//                statusLabel.setText("✅ Marked as completed.");
//            } else {
//                statusLabel.setText("❌ Select a task first.");
//            }
        });


        Label deleteLabel = new Label("");

        deleteBtn.setOnAction(e -> {
            Task selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                table.getItems().remove(selected);
                taskManager.deleteTaskFromUI(selected);
                deleteLabel.setText("🗑 Task deleted.");
            } else {
                statusLabel.setText("❌ Select a task first.");
            }
        });

        VBox screen = new VBox(15, header, table, inputRow, statusLabel);
        screen.setPadding(new Insets(25));
        VBox.setVgrow(table, Priority.ALWAYS);
        return screen;
    }
}