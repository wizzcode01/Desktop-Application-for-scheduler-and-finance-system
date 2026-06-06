package com.example.scheduleapp;

import java.time.LocalTime;

public class Task {
    private String taskName;
    private LocalTime alarmTime;

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
}
