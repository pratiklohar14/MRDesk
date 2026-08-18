package com.example.view.RSM;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import com.example.view.Welcome;

/**
 * RsmReports - MedTrack Pro (RSM Portal)
 * Dedicated view for Regional Analytics, PDF/Excel Exports, and Audit Downloads.
 */
public class RsmReports {

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
        VBox sidebar = new RsmDashBoard().createSideNavBar(primaryStage, "Reports");
        root.setLeft(sidebar);

        // Main Body
        VBox mainBox = new VBox(0);

        HBox topBar = new HBox(16);
        topBar.setPrefHeight(72);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 28, 0, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: transparent transparent " + COLOR_BORDER + " transparent; -fx-border-width: 0 0 1 0;");

        Label title = new Label("Regional Reports & Analytics Export Center");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setTextFill(Color.web(COLOR_TEXT_MAIN));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button exportAllBtn = new Button("Download All Reports (ZIP)");
        exportAllBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        exportAllBtn.setStyle("-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white; -fx-background-radius: 8px; -fx-padding: 8px 18px; -fx-cursor: hand;");
        exportAllBtn.setOnAction(e -> handleExportReport("RSM_Full_Regional_Audit_Q2.zip"));

        topBar.getChildren().addAll(title, sp, exportAllBtn);
        mainBox.getChildren().add(topBar);

        // Content Scroll
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: " + COLOR_BG_CANVAS + "; -fx-background: " + COLOR_BG_CANVAS + "; -fx-border-color: transparent;");

        VBox container = new VBox(20);
        container.setPadding(new Insets(24, 28, 28, 28));

        // Available Executive Reports Cards
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 14px;");

        Label cardTitle = new Label("Available Executive Regional Reports");
        cardTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        cardTitle.setTextFill(Color.web(COLOR_TEXT_MAIN));
        card.getChildren().add(cardTitle);

        String[][] reports = {
                {"Q2 Regional Sales Audit Report", "PDF Document", "Generated today at 08:00 AM", "MedTrack_Q2_Regional_Sales_Audit.pdf"},
                {"ASM Performance & Quota Summary", "Excel Spreadsheet", "Generated yesterday", "ASM_Performance_Quota_Audit.xlsx"},
                {"Doctor & Chemist Coverage Directory", "CSV Dataset", "Generated 2 days ago", "Doctor_Chemist_Coverage_Master.csv"},
                {"Product Inventory & Sample Dispatch Log", "PDF Document", "Generated 3 days ago", "Sample_Dispatch_Audit_Log.pdf"}
        };

        for (String[] rep : reports) {
            HBox row = new HBox(16);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(14, 16, 14, 16));
            row.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 10px; -fx-border-color: #E2E8F0; -fx-border-radius: 10px;");

            Label icon = new Label("[R]");
            icon.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            VBox info = new VBox(3);
            Label rTitle = new Label(rep[0]);
            rTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
            rTitle.setTextFill(Color.web(COLOR_TEXT_MAIN));

            Label rMeta = new Label(rep[1] + " - " + rep[2]);
            rMeta.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
            rMeta.setTextFill(Color.web(COLOR_TEXT_MUTED));
            info.getChildren().addAll(rTitle, rMeta);

            Region rSp = new Region();
            HBox.setHgrow(rSp, Priority.ALWAYS);

            Button dlBtn = new Button("Download " + rep[1]);
            dlBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            dlBtn.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + COLOR_PRIMARY + "; -fx-text-fill: " + COLOR_PRIMARY + "; -fx-background-radius: 6px; -fx-cursor: hand;");
            dlBtn.setOnAction(e -> handleExportReport(rep[3]));

            row.getChildren().addAll(icon, info, rSp, dlBtn);
            card.getChildren().add(row);
        }

        container.getChildren().add(card);
        scroll.setContent(container);

        VBox.setVgrow(scroll, Priority.ALWAYS);
        double w = (primaryStage != null && primaryStage.getWidth() > 0) ? primaryStage.getWidth() : 1360;
        double h = (primaryStage != null && primaryStage.getHeight() > 0) ? primaryStage.getHeight() : 860;
        mainScene = new Scene(root, w, h);
        return mainScene;
    }

    private void handleExportReport(String defaultFileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Regional Report");
        fileChooser.setInitialFileName(defaultFileName);
        Stage targetStage = (primaryStage != null) ? primaryStage : Welcome.welcomeStage;
        File file = fileChooser.showSaveDialog(targetStage);
        if (file != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Report Downloaded");
            alert.setHeaderText(null);
            alert.setContentText("Successfully exported report to: " + file.getName());
            alert.showAndWait();
        }
    }
}
