package com.task3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatbotGUI extends Application {

    private ChatbotLogic botLogic = new ChatbotLogic();
    
    private VBox messageContainer; 
    private ScrollPane scrollPane;
    private TextField inputField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AI Chatbot");

        // 1. Message Container (Holds the bubbles)
        messageContainer = new VBox(10); // 10px gap between messages
        messageContainer.setPadding(new Insets(10));
        
        // 2. Scroll Pane (Allows scrolling through history)
        scrollPane = new ScrollPane(messageContainer);
        scrollPane.setFitToWidth(true); // Ensures bubbles don't stretch weirdly
        scrollPane.setStyle("-fx-background-color: white;");

        // 3. Input Area
        inputField = new TextField();
        inputField.setPromptText("Type a message...");
        inputField.setPrefHeight(40);
        inputField.setOnAction(e -> sendMessage()); // Press Enter to send

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> sendMessage());

        HBox inputBox = new HBox(10, inputField, sendButton);
        inputBox.setPadding(new Insets(15));
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-background-color: white; -fx-border-color: #E5E5EA; -fx-border-width: 1 0 0 0;");
        
        HBox.setHgrow(inputField, javafx.scene.layout.Priority.ALWAYS);

        // 4. Main Layout
        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setBottom(inputBox);

        Scene scene = new Scene(root, 400, 600);
        
        // LINKING CSS
        try {
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("CSS file not found!");
        }

        primaryStage.setScene(scene);
        primaryStage.show();

        // Welcome message
        addMessage("Hello! I am your AI assistant.", false);
    }

    private void sendMessage() {
        String userMessage = inputField.getText();
        if (userMessage.trim().isEmpty()) return;

        // 1. Add User Bubble (Right side)
        addMessage(userMessage, true);
        inputField.clear();

        // 2. Get Bot Response
        String response = botLogic.generateResponse(userMessage);

        // 3. Add Bot Bubble (Left side)
        addMessage(response, false);
    }

    private void addMessage(String text, boolean isUser) {
        // Create the bubble
        Label label = new Label(text);
        label.setWrapText(true);
        
        // Create a container for the label to handle alignment (Left vs Right)
        HBox rowContainer = new HBox();
        
        if (isUser) {
            label.getStyleClass().add("user-bubble");
            rowContainer.setAlignment(Pos.CENTER_RIGHT); // Align Right
        } else {
            label.getStyleClass().add("bot-bubble");
            rowContainer.setAlignment(Pos.CENTER_LEFT);  // Align Left
        }

        rowContainer.getChildren().add(label);
        messageContainer.getChildren().add(rowContainer);

        // Auto-scroll to bottom
        scrollPane.setVvalue(1.0);
    }
}