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
 * RsmAsmPerformance - MedTrack Pro (RSM Portal)
 * Dedicated view for Area Sales Manager (ASM) Performance Audit & Leaderboards.
 */
public class RsmAsmPerformance {

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
        VBox sidebar = new RsmDashBoard().createSideNavBar(primaryStage, "ASM Performance");
        root.setLeft(sidebar);

        // Main Body
        VBox mainBox = new VBox(0);

        HBox topBar = new HBox(16);
        topBar.setPrefHeight(72);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 28, 0, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: transparent transparent " + COLOR_BORDER + " transparent; -fx-border-width: 0 0 1 0;");

        Label title = new Label("Area Sales Managers (ASM) Leaderboard & Quota");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setTextFill(Color.web(COLOR_TEXT_MAIN));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button exportBtn = new Button("Export ASM Report");
        exportBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        exportBtn.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + COLOR_PRIMARY + "; -fx-text-fill: " + COLOR_PRIMARY + "; -fx-background-radius: 8px; -fx-padding: 8px 18px; -fx-cursor: hand;");
        exportBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export ASM Performance Report");
            fileChooser.setInitialFileName("ASM_Performance_Report.xlsx");
            Stage targetStage = (primaryStage != null) ? primaryStage : Welcome.welcomeStage;
            File file = fileChooser.showSaveDialog(targetStage);
            if (file != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Report Exported");
                alert.setHeaderText(null);
                alert.setContentText("ASM Performance report exported successfully to: " + file.getName());
                alert.showAndWait();
            }
        });

        topBar.getChildren().addAll(title, sp, exportBtn);
        mainBox.getChildren().add(topBar);

        // Content Scroll
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: " + COLOR_BG_CANVAS + "; -fx-background: " + COLOR_BG_CANVAS + "; -fx-border-color: transparent;");

        VBox container = new VBox(20);
        container.setPadding(new Insets(24, 28, 28, 28));

        // ASM Ranking Cards
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 14px;");

        Label cardTitle = new Label("ASM Regional Rankings (MTD)");
        cardTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        cardTitle.setTextFill(Color.web(COLOR_TEXT_MAIN));
        card.getChildren().add(cardTitle);

        Object[][] asmRows = {
                {"Rank 1", "Rahul Verma", "Central District", "Rs. 58.6 Lakh", "Rs. 75.0 Lakh", 0.781, "78.1%"},
                {"Rank 2", "Neha Singh", "North Sector", "Rs. 48.2 Lakh", "Rs. 60.0 Lakh", 0.803, "80.3%"},
                {"Rank 3", "Suresh Yadav", "East Sector", "Rs. 46.7 Lakh", "Rs. 60.0 Lakh", 0.778, "77.8%"},
                {"Rank 4", "Pooja Sharma", "South Hub", "Rs. 41.3 Lakh", "Rs. 55.0 Lakh", 0.751, "75.1%"},
                {"Rank 5", "Amit Tiwari", "West Zone", "Rs. 38.9 Lakh", "Rs. 50.0 Lakh", 0.778, "77.8%"}
        };

        HBox tHead = new HBox(12);
        tHead.setPadding(new Insets(8, 12, 8, 12));
        tHead.setStyle("-fx-background-color: #F3E8FF; -fx-background-radius: 8px;");

        Label th1 = new Label("Rank"); th1.setPrefWidth(100); th1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th2 = new Label("ASM Manager"); th2.setPrefWidth(140); th2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th3 = new Label("Assigned Zone"); th3.setPrefWidth(140); th3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th4 = new Label("Sales (Rs.)"); th4.setPrefWidth(120); th4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th5 = new Label("Target (Rs.)"); th5.setPrefWidth(120); th5.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th6 = new Label("Quota Achievement"); th6.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));

        tHead.getChildren().addAll(th1, th2, th3, th4, th5, th6);
        card.getChildren().add(tHead);

        for (Object[] r : asmRows) {
            HBox row = new HBox(12);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(10, 12, 10, 12));

            Label l1 = new Label((String) r[0]); l1.setPrefWidth(100); l1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
            Label l2 = new Label((String) r[1]); l2.setPrefWidth(140); l2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
            Label l3 = new Label((String) r[2]); l3.setPrefWidth(140); l3.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
            Label l4 = new Label((String) r[3]); l4.setPrefWidth(120); l4.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
            Label l5 = new Label((String) r[4]); l5.setPrefWidth(120); l5.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));

            HBox pBox = new HBox(8);
            pBox.setAlignment(Pos.CENTER_LEFT);
            ProgressBar pb = new ProgressBar((double) r[5]);
            pb.setPrefWidth(120);
            pb.setStyle("-fx-accent: " + COLOR_PRIMARY + ";");
            Label pct = new Label((String) r[6]); pct.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            pBox.getChildren().addAll(pb, pct);

            row.getChildren().addAll(l1, l2, l3, l4, l5, pBox);
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
}
