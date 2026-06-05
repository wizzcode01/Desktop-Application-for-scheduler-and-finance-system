package com.example.scheduleapp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
   @Override
   public void start(Stage primaryStage){
//          Parent root = FXMLLoader.load(getClass().getResource("/hello-view.fxml"));
//          Scene scene = new Scene(root, 400, 400);
//         // scene.getStylesheets().add(String.valueOf(getClass().getResource("application.css ")));
//          primaryStage.setScene(scene);
//          primaryStage.show();
       Label title = new Label("Welcome to wiSchedule");
       title.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: white;");
       HBox titleBar = new HBox(title);
       titleBar.setStyle("-fx-background-color: #1A252F");
       titleBar.setPadding(new Insets(15));

       // SIDEBAR BUTTONS
       Button tasksBtn = new Button("📋 Add todo tasks");
       Button financeBtn = new Button("💰 Your finance");
       Button completedBtn = new Button("✅ Completed tasks");
       Button ListAllBtn = new Button("📋 All todo tasks");
       Button Notification = new Button("🔔 Notifications");


       String btnStyle = "-fx-background-color: transparent; " +
               "-fx-text-fill: white; " +
               "-fx-font-size: 14px; " +
               "-fx-alignment: BASELINE_LEFT;" +
               "-fx-cursor: hand;";

       tasksBtn.setStyle(btnStyle);
       financeBtn.setStyle(btnStyle);
       completedBtn.setStyle(btnStyle);
       ListAllBtn.setStyle(btnStyle);
       Notification.setStyle(btnStyle);

       tasksBtn.setMaxWidth(Double.MAX_VALUE);
       financeBtn.setMaxWidth(Double.MAX_VALUE);
       completedBtn.setMaxWidth(Double.MAX_VALUE);
       ListAllBtn.setMaxWidth(Double.MAX_VALUE);
       Notification.setMaxWidth(Double.MAX_VALUE);

       // SIDEBAR
       VBox sidebar = new VBox(20, tasksBtn, financeBtn, completedBtn, ListAllBtn, Notification);
       sidebar.setStyle("-fx-background-color: #2C3E50;");
       sidebar.setPadding(new Insets(20));
       sidebar.setPrefWidth(180);

       // CENTER CONTENT
       Label content = new Label("👈  Select a menu item to get started");
       content.setStyle("-fx-font-size: 16px; -fx-text-fill: #888888;");

       // BUTTON ACTIONS
       tasksBtn.setOnAction(e -> content.setText("Tasks screen coming soon"));
       financeBtn.setOnAction(e -> content.setText("Finance screen coming soon"));
       completedBtn.setOnAction(e -> content.setText("Completed screen coming soon"));

       // ROOT LAYOUT
       BorderPane root = new BorderPane();
       root.setTop(titleBar);
       root.setLeft(sidebar);
       root.setCenter(content);
       root.setStyle("-fx-background-color: #F4F6F7;");

       Scene scene = new Scene(root, 750, 520);
       Stage stage = new Stage();
       stage.setTitle("Wisdom App");
       stage.setScene(scene);
       stage.show();
   }

    public static void main(String[] args) {
       launch(args);
    }

 //   @Override
//    public void start(Stage primaryStage) throws Exception {
//        Group root = new Group();
//        Scene scene = new Scene(root, 600, 600, Color.LIGHTSKYBLUE);
//        Stage stage = new Stage();
//
//        Text text = new Text();
//        text.setText("HEY wizzyyyy");
//        text.setX(50);
//        text.setY(50);
//        text.setFont(Font.font("Verdana", 50));
//        text.setFill(Color.PURPLE);
//
//        Line line = new Line();
//        line.setStartX(50);
//        line.setStartY(70);
//        line.setEndX(150);
//        line.setEndY(70);
//        line.setStrokeWidth(5);
//        line.setStroke(Color.GRAY);
//        // line.setOpacity(0.5);
//        // line.setRotate(45);
//
//        Rectangle rectangle = new Rectangle();
//        rectangle.setX(100);
//        rectangle.setY(100);
//        rectangle.setWidth(100);
//        rectangle.setHeight(100);
//        rectangle.setFill(Color.BLUE);
//        rectangle.setStrokeWidth(5);
//        rectangle.setStroke(Color.BLACK);
//
//
//        root.getChildren().add(text);
//        root.getChildren().add(line);
//        root.getChildren().add(rectangle);
//
//
//
//        // Image icon = new Image();
//        //stage.getIcons().add(icon);
////        stage.setTitle("Stage Demo Program");
////        stage.setWidth(420);
////        stage.setHeight(420);
////        stage.setResizable(false);
////        stage.setFullScreen(true);
////        stage.setFullScreenExitHint("You CAN'T ESCAPE unless you press q");
////        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));
////
//        stage.setScene(scene);
//        stage.show();
//    }
}
