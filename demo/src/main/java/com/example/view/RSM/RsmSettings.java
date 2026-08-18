package com.example.view.RSM;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import com.example.view.Welcome;

/**
 * RsmSettings - MedTrack Pro (RSM Portal)
 * Dedicated view for Regional Preferences, Territory Configuration, & Profile Settings.
 */
public class RsmSettings {

    private Stage primaryStage;
    private Scene mainScene;

    private static final String COLOR_PRIMARY = "#7C3AED";
    private static final String COLOR_BG_CANVAS = "#F8FAFC";
    private static final String COLOR_BORDER = "#E9D5FF";
    private static final String COLOR_TEXT_MAIN = "#1E1B4B";
    private static final String COLOR_TEXT_MUTED = "#64748B";

    public Scene createView() {
        return createView(Welcome.welcomeStage != null ? Welcome.welcomeStage : null);
    }

    public Scene createView(Stage stage) {
        if (stage != null) {
            this.primaryStage = stage;
            this.primaryStage.setMaximized(true);
        }

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BG_CANVAS + ";");

        // Sidebar Navigation
        VBox sidebar = new RsmDashBoard().createSideNavBar(primaryStage, "Settings");
        root.setLeft(sidebar);

        // Main Body
        VBox mainBox = new VBox(0);

        HBox topBar = new HBox(16);
        topBar.setPrefHeight(72);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 28, 0, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: transparent transparent " + COLOR_BORDER + " transparent; -fx-border-width: 0 0 1 0;");

        Label title = new Label("Regional Settings & Preferences");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setTextFill(Color.web(COLOR_TEXT_MAIN));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button saveBtn = new Button("Save Changes");
        saveBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        saveBtn.setStyle("-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white; -fx-background-radius: 8px; -fx-padding: 8px 18px; -fx-cursor: hand;");
        saveBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Settings Saved");
            alert.setHeaderText(null);
            alert.setContentText("Regional settings and preferences updated successfully!");
            alert.showAndWait();
        });

        topBar.getChildren().addAll(title, sp, saveBtn);
        mainBox.getChildren().add(topBar);

        // Content Scroll
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: " + COLOR_BG_CANVAS + "; -fx-background: " + COLOR_BG_CANVAS + "; -fx-border-color: transparent;");

        VBox container = new VBox(20);
        container.setPadding(new Insets(24, 28, 28, 28));

        // Settings Cards
        VBox card = new VBox(20);
        card.setPadding(new Insets(24));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 14px;");

        Label cardTitle = new Label("RSM Profile & Regional Preferences");
        cardTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        cardTitle.setTextFill(Color.web(COLOR_TEXT_MAIN));
        card.getChildren().add(cardTitle);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(16);

        grid.add(new Label("Full Name:"), 0, 0);
        TextField nameField = new TextField("Amit Sharma");
        nameField.setPrefWidth(300);
        grid.add(nameField, 1, 0);

        grid.add(new Label("Designation Role:"), 0, 1);
        TextField roleField = new TextField("Regional Sales Manager (RSM)");
        roleField.setEditable(false);
        grid.add(roleField, 1, 1);

        grid.add(new Label("Assigned Region:"), 0, 2);
        TextField regionField = new TextField("North Region (5 Districts)");
        grid.add(regionField, 1, 2);

        grid.add(new Label("Email Notifications:"), 0, 3);
        CheckBox emailNotif = new CheckBox("Receive Daily RSM Digest & Stock Escalations");
        emailNotif.setSelected(true);
        grid.add(emailNotif, 1, 3);

        card.getChildren().add(grid);
        container.getChildren().add(card);
        scroll.setContent(container);

        VBox.setVgrow(scroll, Priority.ALWAYS);
        double w = (primaryStage != null && primaryStage.getWidth() > 0) ? primaryStage.getWidth() : 1360;
        double h = (primaryStage != null && primaryStage.getHeight() > 0) ? primaryStage.getHeight() : 860;
        mainScene = new Scene(root, w, h);
        return mainScene;
    }
}
