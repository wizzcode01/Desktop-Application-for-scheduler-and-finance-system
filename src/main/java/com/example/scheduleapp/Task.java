package com.example.scheduleapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Task {
    private String taskName;
    private LocalTime alarmTime;
    private String status = "pending...";
    private LocalDate date;

    public Task(String taskName, LocalTime alarmTime){
        this.taskName = taskName;
        this.alarmTime = alarmTime;
        this.date = LocalDate.now();
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

    public LocalDate getDate() { return date; }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updateStatus() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        if(date.isBefore(today)){
          long daysAgo = ChronoUnit.DAYS.between(date, today);
          if(daysAgo == 1){
              status = "⚠️ passed 1 day ago";
              } else {
                  status = "⚠️ passed " + daysAgo + " days ago";
              }
          }else {
            if (alarmTime.equals(now)) {
                status = "it is time";
            } else if (alarmTime.isAfter(now)) {
                status = "pending...";
            } else {
                status = "time passed";
            }
        }
    }

    public void setTask(String newTask) {
        this.taskName = newTask;
    }

    public void setAlarmTime(LocalTime alarmTime) {
        this.alarmTime = alarmTime;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public String toString() {
        return taskName + " " + alarmTime;
    }
}
