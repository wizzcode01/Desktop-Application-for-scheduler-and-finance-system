package com.example.scheduleapp;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;
import javafx.scene.media.MediaPlayer;

public class AlarmClock implements Runnable{
    private final String filePath;
    private final Scanner scanner;
    private final Task task;

    AlarmClock(Task task, Scanner scanner, String filePath) {
        this.filePath = filePath;
        this.scanner = scanner;
        this.task = task;
    }

    @Override
    public void run() {
        if (LocalTime.now().isAfter(task.getAlarmTime())) {
            System.out.println("Alarm time has already passed for " + task.getTask());
        }
        while (LocalTime.now().isBefore(task.getAlarmTime())) {
            try {
                Thread.sleep(1000);

                LocalTime now = LocalTime.now();

                // System.out.printf("\r%02d:%02d:%02d",
                // now.getHour(),
                // now.getMinute(),
                // now.getSecond());
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted");
            }
        }
        System.out.println("\n*Alarm time reached for " + task.getTask());
        playSound(filePath);
    }

    private void playSound(String filePath) {
        File audioFile = new File(filePath);

        try {
                javafx.scene.media.Media sound = new javafx.scene.media.Media(new java.io.File(filePath).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                Thread.sleep(10000);
                mediaPlayer.stop();
        } catch (InterruptedException e) {
            System.out.println("Alarm was interrupted");
        } catch (Exception e) {
            System.out.println("Error reading audio file");
        }
    }
}
