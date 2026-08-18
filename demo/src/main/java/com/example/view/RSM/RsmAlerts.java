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
 * RsmAlerts - MedTrack Pro (RSM Portal)
 * Dedicated view for Regional Notifications, Low Inventory Warnings, and Approvals.
 */
public class RsmAlerts {

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
        VBox sidebar = new RsmDashBoard().createSideNavBar(primaryStage, "Alerts");
        root.setLeft(sidebar);

        // Main Body
        VBox mainBox = new VBox(0);

        HBox topBar = new HBox(16);
        topBar.setPrefHeight(72);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 28, 0, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: transparent transparent " + COLOR_BORDER + " transparent; -fx-border-width: 0 0 1 0;");

        Label title = new Label("Regional System Alerts & Action Center (3 Unread)");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setTextFill(Color.web(COLOR_TEXT_MAIN));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button markReadBtn = new Button("Mark All As Read");
        markReadBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        markReadBtn.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-text-fill: " + COLOR_TEXT_MAIN + "; -fx-background-radius: 8px; -fx-padding: 8px 18px; -fx-cursor: hand;");
        markReadBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerts Status");
            alert.setHeaderText(null);
            alert.setContentText("All regional system notifications have been marked as read.");
            alert.showAndWait();
        });

        topBar.getChildren().addAll(title, sp, markReadBtn);
        mainBox.getChildren().add(topBar);

        // Content Scroll
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: " + COLOR_BG_CANVAS + "; -fx-background: " + COLOR_BG_CANVAS + "; -fx-border-color: transparent;");

        VBox container = new VBox(20);
        container.setPadding(new Insets(24, 28, 28, 28));

        // Alerts Feed Card
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 14px;");

        Label cardTitle = new Label("Active Notifications & Escalations");
        cardTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        cardTitle.setTextFill(Color.web(COLOR_TEXT_MAIN));
        card.getChildren().add(cardTitle);

        String[][] alertsList = {
                {"High Priority", "Low Stock Alert in Agra Depot", "CardioPro 50mg inventory dropped below minimum threshold (150 units left).", "10 mins ago", "#FEF3C7", "#D97706"},
                {"Action Required", "ASM Expense Claim Approval Needed", "Rahul Verma submitted outstation travel voucher for Rs. 18,500.", "1 hour ago", "#F3E8FF", "#7C3AED"},
                {"Target Notice", "Kanpur Area Target Milestone", "Kanpur area achieved 80% monthly sales quota target today.", "3 hours ago", "#DCFCE7", "#16A34A"},
                {"System Alert", "Monthly Tour Program Submitted", "All 5 ASMs submitted June 2025 field tour programs for review.", "Yesterday", "#E0F2FE", "#0284C7"}
        };

        for (String[] alt : alertsList) {
            VBox altBox = new VBox(8);
            altBox.setPadding(new Insets(14, 16, 14, 16));
            altBox.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 10px; -fx-border-color: #E2E8F0; -fx-border-radius: 10px;");

            HBox aHead = new HBox(12);
            aHead.setAlignment(Pos.CENTER_LEFT);

            Label badge = new Label(alt[0]);
            badge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
            badge.setStyle("-fx-background-color: " + alt[4] + "; -fx-text-fill: " + alt[5] + "; -fx-padding: 3 8; -fx-background-radius: 6px;");

            Label aTitle = new Label(alt[1]);
            aTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
            aTitle.setTextFill(Color.web(COLOR_TEXT_MAIN));

            Region aSp = new Region();
            HBox.setHgrow(aSp, Priority.ALWAYS);

            Label time = new Label(alt[3]);
            time.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
            time.setTextFill(Color.web(COLOR_TEXT_MUTED));

            aHead.getChildren().addAll(badge, aTitle, aSp, time);

            Label desc = new Label(alt[2]);
            desc.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
            desc.setTextFill(Color.web(COLOR_TEXT_MUTED));

            altBox.getChildren().addAll(aHead, desc);
            card.getChildren().add(altBox);
        }

        container.getChildren().add(card);
        scroll.setContent(container);

        VBox.setVgrow(scroll, Priority.ALWAYS);
        double w = (primaryStage != null && primaryStage.getWidth() > 0) ? primaryStage.getWidth() : 1360;
        double h = (primaryStage != null && primaryStage.getHeight() > 0) ? primaryStage.getHeight() : 860;
        mainScene = new Scene(root, w, h);
        return mainScene;
    }
}
