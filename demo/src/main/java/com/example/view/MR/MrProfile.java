package com.example.view.MR;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MrProfile {

    public VBox createPage() {
        VBox page = new VBox(20);
        page.setPadding(new Insets(24, 28, 24, 28));

        Label title = new Label("⚙️ Settings & Representative Profile");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#0F172A"));

        Label sub = new Label("Manage personal profile details, notification preferences, and application settings");
        sub.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        sub.setTextFill(Color.web("#64748B"));

        VBox headBox = new VBox(4, title, sub);

        VBox profileForm = new VBox(16);
        profileForm.setPadding(new Insets(24));
        profileForm.setStyle(
                "-fx-background-color: white; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-width: 1px;");

        Label formTitle = new Label("👤 Representative Profile Information");
        formTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        formTitle.setTextFill(Color.web("#0F172A"));

        GridPane formGrid = new GridPane();
        formGrid.setHgap(16);
        formGrid.setVgap(14);

        TextField nameField = createFormField("Full Name", "Pratik Lohar");
        TextField emailField = createFormField("Email Address", "pratik.m@pharma.com");
        TextField phoneField = createFormField("Phone Number", "+91 98765 43210");
        TextField zoneField = createFormField("Assigned Territory / Zone", "West Region • Zone 4");

        formGrid.add(createFieldWrapper("Full Name", nameField), 0, 0);
        formGrid.add(createFieldWrapper("Email Address", emailField), 1, 0);
        formGrid.add(createFieldWrapper("Phone Number", phoneField), 0, 1);
        formGrid.add(createFieldWrapper("Territory Zone", zoneField), 1, 1);

        Button saveBtn = createStyledButton("💾 Save Profile Changes", "#2563EB", "white");
        profileForm.getChildren().addAll(formTitle, formGrid, saveBtn);

        // Notifications Preferences Box
        VBox prefBox = new VBox(14);
        prefBox.setPadding(new Insets(24));
        prefBox.setStyle(
                "-fx-background-color: white; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-width: 1px;");

        Label prefTitle = new Label("🔔 Notification & System Alerts");
        prefTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        prefTitle.setTextFill(Color.web("#0F172A"));

        CheckBox c1 = new CheckBox("Email alerts for scheduled visit reminders");
        c1.setSelected(true);
        CheckBox c2 = new CheckBox("Push notifications for manager target updates");
        c2.setSelected(true);
        CheckBox c3 = new CheckBox("SMS alerts for urgent clinic itinerary changes");

        prefBox.getChildren().addAll(prefTitle, c1, c2, c3);

        page.getChildren().addAll(headBox, profileForm, prefBox);
        return page;
    }

    private VBox createFieldWrapper(String labelStr, TextField field) {
        Label lbl = new Label(labelStr);
        lbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        lbl.setTextFill(Color.web("#475569"));
        return new VBox(6, lbl, field);
    }

    private TextField createFormField(String label, String val) {
        TextField tf = new TextField(val);
        tf.setPrefHeight(38);
        tf.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-background-radius: 8px; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-padding: 8px 12px;");
        return tf;
    }

    private Button createStyledButton(String text, String bgColor, String textColor) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + textColor
                        + "; -fx-background-color: " + bgColor
                        + "; -fx-background-radius: 8px; -fx-padding: 10px 20px; -fx-cursor: hand;");
        return btn;
    }
}
