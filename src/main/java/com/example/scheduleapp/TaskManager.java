package com.example.scheduleapp;

import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class TaskManager {
    String filePath = "codes\\file_example_WAV_1MG.wav";
    String writeFilePath = "c:\\Users\\HomePC\\Desktop\\task-db.txt";
    private ArrayList<Task> tasks = new ArrayList<>();

    public void addTaskFromUI(Task task){
        tasks.add(task);
        try (FileWriter writer = new FileWriter(writeFilePath, true)) {
            writer.write( task.getTask()+ "|" + task.getAlarmTime() + "\n");
        } catch (IOException e) {
            System.out.println("Could not save task to file");
        }
        AlarmClock alarmClock = new AlarmClock(task, null, filePath);
        new Thread(alarmClock).start();
    }

    public void loadTasksIntoTable(javafx.scene.control.TableView<Task> table) {
        try (BufferedReader reader = new BufferedReader(new FileReader(writeFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    String time = parts[1].trim();
                    // String status = parts[2].trim();

                    Task task = new Task(name, LocalTime.parse(time));
                    task.updateStatus();
                    table.getItems().add(task);
                }
            }
        } catch (Exception e) {
            System.out.println("No saved tasks found.");
        }
    }
}
