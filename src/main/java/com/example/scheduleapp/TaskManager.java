package com.example.scheduleapp;

import javafx.concurrent.Task;

import java.io.FileWriter;
import java.io.IOException;
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
}
