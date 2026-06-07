package com.example.scheduleapp;

//import javafx.concurrent.Task;
import com.example.scheduleapp.Task;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TaskManager {
    String filePath = "C:\\Users\\HomePC\\Downloads\\file_example_WAV_1MG.wav";
    String writeFilePath = "c:\\Users\\HomePC\\Desktop\\task-db.txt";
    private ArrayList<Task> tasks = new ArrayList<>();

    public TaskManager(){
        loadTaskFromFile();
    }
    private void loadTaskFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader(writeFilePath)) ){
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

                    Task task = new Task(name, LocalTime.parse(time));
                    if(parts.length >= 3){
                        try {
                            task.setDate(LocalDate.parse(parts[2].trim()));
                        }catch (Exception e){
                            task.setDate(LocalDate.now());
                        }
                    }
                    if(parts.length >= 4){
                        task.setStatus(parts[3].trim());
                    }
                    task.updateStatus();
                    tasks.add(task);
                }
                }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTaskFromUI(Task task){
        tasks.add(task);
        try (FileWriter writer = new FileWriter(writeFilePath, true)) {
            writer.write( task.getTask()+ "|" + task.getAlarmTime() +  "|" + task.getDate() + "|" + task.getStatus() + "\n");
        } catch (IOException e) {
            System.out.println("Could not save task to file");
        }
        AlarmClock alarmClock = new AlarmClock(task, null, filePath);
        new Thread(alarmClock).start();
    }

    public void loadTasksIntoTable(javafx.scene.control.TableView<Task> table) {
        table.getItems().clear();
        for(Task t : tasks){
            if(!t.getStatus().equalsIgnoreCase("completed")){
                t.updateStatus();
                table.getItems().add(t);
            }
        }


//        try (BufferedReader reader = new BufferedReader(new FileReader(writeFilePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                line = line.trim();
//                if (line.isEmpty()) {
//                    continue;
//                }
//                String[] parts = line.split("\\|");
//                if (parts.length >= 2) {
//                    String name = parts[0].trim();
//                    String time = parts[1].trim();
//                    // String status = parts[2].trim();
//
//                    Task task = new Task(name, LocalTime.parse(time));
//                    if(parts.length >= 3 && !parts[2].trim().isEmpty()){
//                        try{
//                            task.setDate(LocalDate.parse(parts[2].trim()));
//                        }catch(Exception e){
//                            task.setDate(LocalDate.now());
//                        }
//                    }
//
//                    task.updateStatus();
//                    table.getItems().add(task);
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("No saved tasks found.");
//        }

    }

    public void deleteTaskFromUI(Task taskToDelete){
        tasks.removeIf(t -> t.getTask().equals(taskToDelete.getTask()));
        saveTasksToFile();
    }

    public void markTaskCompleteFromUI(Task taskToComplete){
        for(Task t : tasks){
           if(t.getTask().equalsIgnoreCase(taskToComplete.getTask())){
               t.setStatus("completed");
           }
       }
       saveTasksToFile();
    }

    public void loadCompletedTasksIntoTable(javafx.scene.control.TableView<Task> table) {
        table.getItems().clear();
        for(Task data : tasks){
            if(data.getStatus().equalsIgnoreCase("completed")){
                table.getItems().add(data);
            }
        }
//        try (BufferedReader reader = new BufferedReader(new FileReader(writeFilePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                line = line.trim();
//                if (line.isEmpty()) continue;
//                String[] parts = line.split("\\|");
//                if (parts.length >= 3 && parts[2].trim().equalsIgnoreCase("completed")) {
//                    Task task = new Task(parts[0].trim(), LocalTime.parse(parts[1].trim()));
//
//                    // load saved date if it exists, otherwise use today
//                    if (parts.length >= 3 && !parts[2].trim().isEmpty()) {
//                        try {
//                            task.setDate(LocalDate.parse(parts[2].trim()));
//                        } catch (Exception e) {
//                            task.setDate(LocalDate.now());
//                        }
//                    }
//
//                    task.setStatus("completed");
//                    table.getItems().add(task);
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("No completed tasks found.");
//        }
    }

    private void saveTasksToFile() {
        try (FileWriter writer = new FileWriter(writeFilePath)) {
            for (Task task : tasks) {
                writer.write(task.getTask() + "|" + task.getAlarmTime() + "|" + task.getDate() + "|" + task.getStatus() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Could not write into file");
        }
    }

    private void sortTaskByDate(){

    }
}
