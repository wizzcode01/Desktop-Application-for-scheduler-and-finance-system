package com.example.scheduleapp;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        HBox inputRow = new HBox(10, taskInput, timeInput, addBtn);
        inputRow.setPadding(new Insets(10, 0, 10, 0));

        // TABLE
        TableView<Task> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.setPlaceholder(new Label("No tasks added yet."));

        TableColumn<Task, String> taskCol = new TableColumn<>("Task");
        taskCol.setCellValueFactory(new PropertyValueFactory<>("task"));

        TableColumn<Task, String> timeCol = new TableColumn<>("Alarm Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("alarmTime"));

        TableColumn<Task, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(taskCol, timeCol, statusCol);

        // STATUS MESSAGE
        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-font-size: 13px");

        addBtn.setOnAction(e -> {
            String taskName = taskInput.getText().trim();
            String time = timeInput.getText().trim();

            if(taskName.isEmpty() || time.isEmpty()){
                statusLabel.setTextFill(Color.RED);
                statusLabel.setText("❌ Please fill in both task name and time.");
                return;
            }

            try{
                LocalTime alarmTime = LocalTime.parse(time, formatter);
                Task task = new Task(taskName, alarmTime);
                task.updateStatus();
                table.getItems().add(task);
                taskManager.addTaskFromUI(task);
                taskInput.clear();
                timeInput.clear();
                statusLabel.setTextFill(Color.GREEN);
                statusLabel.setText("Task added: " + taskName + " at " + time);
            }catch(DateTimeParseException ex){
                statusLabel.setTextFill(Color.RED);
                statusLabel.setText("❌ Invalid time. Use format HH:MM:SS e.g 14:30:00");
            }
        });

        // LOAD existing tasks
        taskManager.loadTasksIntoTable(table);

        VBox screen = new VBox(15, header, inputRow, table, statusLabel);
        screen.setPadding(new Insets(25));
        VBox.setVgrow(table, Priority.ALWAYS);
        return screen;

    }
}
