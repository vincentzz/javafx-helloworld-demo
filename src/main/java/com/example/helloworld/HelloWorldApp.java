package com.example.helloworld;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX Hello World Application
 * This is a simple JavaFX application that displays a "Hello, World!" message
 * and demonstrates basic JavaFX UI components.
 */
public class HelloWorldApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Hello World");

        // Create the main label
        Label helloLabel = new Label("Hello, World!");
        helloLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2E86AB;");

        // Create a subtitle label using Apache Commons Lang3
        String subtitle = org.apache.commons.lang3.StringUtils.capitalize("this is a javaFX application built with java 21");
        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");

        // Create a button
        Button actionButton = new Button("Click Me!");
        actionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2E86AB; -fx-text-fill: white; -fx-padding: 10 20 10 20;");
        
        // Create a label to show button click feedback
        Label feedbackLabel = new Label("");
        feedbackLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #28A745;");

        // Set button action
        actionButton.setOnAction(e -> {
            feedbackLabel.setText("Button clicked! JavaFX is working perfectly.");
        });

        // Create calculator button
        Button calculatorButton = new Button("Open Calculator");
        calculatorButton.setStyle("-fx-font-size: 14px; -fx-background-color: #28A745; -fx-text-fill: white; -fx-padding: 10 20 10 20;");
        
        // Create a label to show calculator launch feedback
        Label calculatorFeedbackLabel = new Label("");
        calculatorFeedbackLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #DC3545;");

        // Set calculator button action
        calculatorButton.setOnAction(e -> {
            try {
                launchCalculator();
                calculatorFeedbackLabel.setText("Calculator launched successfully!");
                calculatorFeedbackLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #28A745;");
            } catch (Exception ex) {
                calculatorFeedbackLabel.setText("Failed to launch calculator: " + ex.getMessage());
                calculatorFeedbackLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #DC3545;");
            }
        });

        // Create the layout container
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.getChildren().addAll(helloLabel, subtitleLabel, actionButton, feedbackLabel, 
                                 calculatorButton, calculatorFeedbackLabel);
        
        // Set background color
        root.setStyle("-fx-background-color: #F8F9FA;");

        // Create and set the scene
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        
        // Make the window resizable
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(350);
        primaryStage.setMinHeight(250);
        
        // Show the stage
        primaryStage.show();
    }

    /**
     * Launches the system calculator application
     * @throws IOException if the calculator cannot be launched
     */
    private void launchCalculator() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder processBuilder;
        
        if (os.contains("mac")) {
            // macOS Calculator
            processBuilder = new ProcessBuilder("open", "-a", "Calculator");
        } else if (os.contains("win")) {
            // Windows Calculator
            processBuilder = new ProcessBuilder("calc.exe");
        } else {
            // Linux/Unix Calculator (try common calculator applications)
            try {
                processBuilder = new ProcessBuilder("gnome-calculator");
            } catch (Exception e) {
                try {
                    processBuilder = new ProcessBuilder("kcalc");
                } catch (Exception e2) {
                    processBuilder = new ProcessBuilder("xcalc");
                }
            }
        }
        
        processBuilder.start();
    }

    /**
     * Main method - entry point for the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
