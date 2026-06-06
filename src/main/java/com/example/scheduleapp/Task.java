package com.example.scheduleapp;

import java.time.LocalTime;

public class Task {
    private String taskName;
    private LocalTime alarmTime;
    private String status = "pending...";

    public Task(String taskName, LocalTime alarmTime){
        this.taskName = taskName;
        this.alarmTime = alarmTime;
    }

    public String getTask(){
        return taskName;
    }

    public LocalTime getAlarmTime(){
        return alarmTime;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updateStatus() {
        LocalTime now = LocalTime.now();
        if (alarmTime.equals(now)) {
            status = "it is time...";
        } else if (alarmTime.isAfter(now)) {
            status = "pending...";
        } else {
            status = "time passed...";
        }
    }

    public void setTask(String newTask) {
        this.taskName = newTask;
    }

    public void setAlarmTime(LocalTime alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String toString() {
        return taskName + " " + alarmTime;
    }
}
