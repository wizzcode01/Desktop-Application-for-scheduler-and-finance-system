package com.example.scheduleapp;

import com.example.scheduleapp.Task;
//import javafx.concurrent.Task;
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
    private final TaskManager taskManager;
    public TaskScreen(TaskManager taskManager){
        this.taskManager = taskManager;
    }
   // private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private LocalTime parseFlexibleTime(String input){
        try{
            return LocalTime.parse(input, DateTimeFormatter.ofPattern("HH:mm:ss") );

        } catch (Exception e) {
            try{
                return LocalTime.parse(input, DateTimeFormatter.ofPattern("HH:mm"));
            }catch (Exception e2){
                throw new RuntimeException("Invalid time format");
            }
        }
    }

    public VBox getScreen(){
        Label header = new Label("Add New Task");
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        TextField taskInput = new TextField();
        taskInput.setPromptText("Enter today todo task...");
        taskInput.setPrefWidth(250);

        TextField timeInput = new TextField();
        timeInput.setPromptText("e.g 14:30 or 09:30");
        timeInput.setPrefWidth(200);

        Label timeHint = new Label("ℹ️ Use 24hr format: 09:00 = 9AM, 14:00 = 2PM, 20:00 = 8PM");
        timeHint.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");

        Label timePreview = new Label("");
        timePreview.setStyle("-fx-text-fill: #2980B9; -fx-font-size: 12px");

        timeInput.textProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal.trim().isEmpty()){
                timePreview.setText("");
            }
            try{
                LocalTime parsed = parseFlexibleTime(newVal.trim());

                String ampm = parsed.getHour() < 12 ? "AM" : "PM";
                int hour12 = parsed.getHour() % 12;
                if(hour12 == 0) hour12 = 12;
                timePreview.setText("⏰ Alarm will ring at: "
                        + String.format("%d:%02d %s", hour12, parsed.getMinute(), ampm));
                timePreview.setTextFill(Color.valueOf("#2980B9"));
            }catch(Exception e){
                timePreview.setText("❌ Invalid format. Use HH:MM e.g. 14:00");
                timePreview.setTextFill(Color.RED);
            }
        });


        Button addBtn = new Button("+ Add Task");
        addBtn.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white;" +
                "-fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 8 16;");
        HBox.setMargin(addBtn, new Insets(0, 0, 10, 0));
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

        TableColumn<Task, String> dateCol = new TableColumn<>("Date Added");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Task, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(taskCol, timeCol, dateCol, statusCol);

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
                LocalTime alarmTime = parseFlexibleTime(time);
                Task task = new Task(taskName, alarmTime);
                task.updateStatus();
                table.getItems().add(task);
                table.getItems().sort((t1, t2) -> {
                    int dateCompare = t2.getDate().compareTo(t1.getDate());
                    if(dateCompare != 0) return dateCompare;
                    return t2.getAlarmTime().compareTo(t1.getAlarmTime());
                });
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
        // Sort: latest date first, then latest time first within same day
        table.getItems().sort((t1, t2) -> {
            int dateCompare = t2.getDate().compareTo(t1.getDate());
            if (dateCompare != 0) return dateCompare;
            return t2.getAlarmTime().compareTo(t1.getAlarmTime());
        });

        VBox screen = new VBox(15, header, inputRow, timeHint, timePreview, table, statusLabel);
        screen.setPadding(new Insets(25));
        VBox.setVgrow(table, Priority.ALWAYS);
        return screen;

    }
}
