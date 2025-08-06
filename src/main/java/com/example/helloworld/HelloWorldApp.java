package com.example.helloworld;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

        // Create a subtitle label
        Label subtitleLabel = new Label("This is a JavaFX application built with Java 21");
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

        // Create the layout container
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.getChildren().addAll(helloLabel, subtitleLabel, actionButton, feedbackLabel);
        
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
     * Main method - entry point for the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
