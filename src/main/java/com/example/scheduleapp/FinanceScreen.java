package com.example.scheduleapp;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.text.NumberFormat;

public class FinanceScreen {
    public VBox getScreen(){
        Label header = new Label("Finance Manager");
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        Label subtext = new Label("Enter your money and we wiil show you how to spend it using wisdom's algorithm.");
        subtext.setStyle("-fx-text-fill: #555555; -fx-font-size: 13px;");

        TextField moneyInput = new TextField();
        moneyInput.setPromptText("Enter amount e.g 5000");
        moneyInput.setPrefWidth(250);

        Button analyzeBtn = new Button("Analyze");
        analyzeBtn.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white;" +
                "-fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 8 16;");

        HBox inputRow = new HBox(10, moneyInput, analyzeBtn);

        // RESULT AREA
        VBox resultBox = new VBox(8);
        resultBox.setPadding(new Insets(15));
        resultBox.setStyle("-fx-background-color: white; -fx-background-radius: 8;");

        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-font-size: 13px;");

        analyzeBtn.setOnAction(e -> {
            String input = moneyInput.getText().trim().replace(",", "");
            if (input.isEmpty()) {
                statusLabel.setTextFill(Color.RED);
                statusLabel.setText("❌ Please enter an amount.");
                return;
            }
            try {
                double money = Double.parseDouble(input);
                if (money <= 0) {
                    statusLabel.setTextFill(Color.RED);
                    statusLabel.setText("❌ Amount must be greater than zero.");
                    return;
                }

                NumberFormat fmt = NumberFormat.getNumberInstance();
                resultBox.getChildren().clear();

                Label title = new Label("Money received: ₦" + fmt.format(money));
                title.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
                resultBox.getChildren().add(title);

                String[][] breakdown = {
                        {"🙏 Give (10%)",        String.valueOf(0.10 * money)},
                        {"🏦 Save (15%)",                String.valueOf(0.15 * money)},
                        {"🛒 Buy what you need (30%)",   String.valueOf(0.30 * money)},
                        {"🍽 Get food (40%)",             String.valueOf(0.40 * money)},
                        {"❤️ Give to family (5%)",        String.valueOf(0.05 * money)},
                };

                for (String[] row : breakdown) {
                    Label item = new Label(row[0] + "  →  ₦" + fmt.format(Double.parseDouble(row[1])));
                    item.setStyle("-fx-font-size: 14px; -fx-text-fill: #2C3E50;");
                    resultBox.getChildren().add(item);
                }

                statusLabel.setTextFill(Color.GREEN);
                statusLabel.setText("Analysis complete.");

            } catch (NumberFormatException ex) {
                statusLabel.setTextFill(Color.RED);
                statusLabel.setText("❌ Invalid amount. Numbers only.");
            }
        });

        VBox screen = new VBox(15, header, subtext, inputRow, statusLabel, resultBox);
        screen.setPadding(new Insets(25));
        return screen;
    }
}
