package com.task1;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GradeTrackerGUI extends Application {

    private ArrayList<Student> students = new ArrayList<>();
    private TextField nameInput;
    private TextField gradeInput;
    private TextArea displayArea;
    private Label statsLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Grade Tracker");

        // 1. Main Layout
        VBox mainLayout = new VBox(15); // Increased spacing
        mainLayout.setPadding(new Insets(25));
        
        // Add a Header Label
        Label headerLabel = new Label("Student Grade Tracker");
        headerLabel.getStyleClass().add("header-title"); // Link to CSS

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(15);
        inputGrid.setVgap(15);

        // 2. Inputs
        Label nameLabel = new Label("Student Name:");
        nameInput = new TextField();
        nameInput.setPromptText("Ex: John Doe");

        Label gradeLabel = new Label("Grade (0-100):");
        gradeInput = new TextField();
        gradeInput.setPromptText("Ex: 85.5");

        Button addButton = new Button("Add Student");
        addButton.setMaxWidth(Double.MAX_VALUE); // Button stretches to fill cell
        
        inputGrid.add(nameLabel, 0, 0);
        inputGrid.add(nameInput, 1, 0);
        inputGrid.add(gradeLabel, 0, 1);
        inputGrid.add(gradeInput, 1, 1);
        inputGrid.add(addButton, 1, 2);

        // 3. Output
        Label listLabel = new Label("Student List:");
        displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefHeight(200);
        displayArea.setPromptText("No students added yet...");

        statsLabel = new Label("Average: 0.0 | Highest: N/A | Lowest: N/A");
        statsLabel.getStyleClass().add("stats-label"); // Link to CSS

        addButton.setOnAction(e -> addStudent());

        mainLayout.getChildren().addAll(headerLabel, inputGrid, listLabel, displayArea, statsLabel);

        Scene scene = new Scene(mainLayout, 450, 550);

        // 4. LINKING THE CSS FILE HERE
        try {
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("CSS file not found. Make sure 'styles.css' is in the same folder.");
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addStudent() {
        String name = nameInput.getText();
        String gradeText = gradeInput.getText();

        if (name.isEmpty() || gradeText.isEmpty()) {
            showAlert("Error", "Please enter both name and grade.");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeText);
            if (grade < 0 || grade > 100) {
                showAlert("Error", "Grade must be between 0 and 100.");
                return;
            }

            students.add(new Student(name, grade));
            nameInput.clear();
            gradeInput.clear();
            updateDisplay();

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid grade. Please enter a number.");
        }
    }

    private void updateDisplay() {
        StringBuilder listContent = new StringBuilder();
        double total = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;

        // Header for the text area
        listContent.append(String.format("%-20s %s\n", "NAME", "GRADE"));
        listContent.append("----------------------------\n");

        for (Student s : students) {
            // Formatting for alignment in the text area
            listContent.append(String.format("%-20s %.2f\n", s.name, s.grade));
            total += s.grade;
            if (s.grade > highest) highest = s.grade;
            if (s.grade < lowest) lowest = s.grade;
        }

        displayArea.setText(listContent.toString());

        if (!students.isEmpty()) {
            double average = total / students.size();
            statsLabel.setText(String.format("Average: %.2f  |  Highest: %.2f  |  Lowest: %.2f", average, highest, lowest));
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    static class Student {
        String name;
        double grade;

        public Student(String name, double grade) {
            this.name = name;
            this.grade = grade;
        }
    }
}