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
 * RsmSalesPerformance - MedTrack Pro (RSM Portal)
 * Dedicated view for Revenue Growth Analytics & Product Sales Performance.
 */
public class RsmSalesPerformance {

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
        VBox sidebar = new RsmDashBoard().createSideNavBar(primaryStage, "Sales Performance");
        root.setLeft(sidebar);

        // Main Body
        VBox mainBox = new VBox(0);

        HBox topBar = new HBox(16);
        topBar.setPrefHeight(72);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 28, 0, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: transparent transparent " + COLOR_BORDER + " transparent; -fx-border-width: 0 0 1 0;");

        Label title = new Label("Regional Sales Analytics & Revenue Trends");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setTextFill(Color.web(COLOR_TEXT_MAIN));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        ComboBox<String> yearFilter = new ComboBox<>();
        yearFilter.getItems().addAll("FY 2025-26", "FY 2024-25");
        yearFilter.setValue("FY 2025-26");
        yearFilter.setPrefHeight(38);
        yearFilter.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-border-radius: 8px;");
        yearFilter.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Filter Applied");
            alert.setHeaderText(null);
            alert.setContentText("Sales data updated for " + yearFilter.getValue());
            alert.showAndWait();
        });

        topBar.getChildren().addAll(title, sp, yearFilter);
        mainBox.getChildren().add(topBar);

        // Content Scroll
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: " + COLOR_BG_CANVAS + "; -fx-background: " + COLOR_BG_CANVAS + "; -fx-border-color: transparent;");

        VBox container = new VBox(20);
        container.setPadding(new Insets(24, 28, 28, 28));

        // Top Category Cards
        HBox categoryRow = new HBox(16);
        categoryRow.getChildren().addAll(
                createCategoryCard("Cardiology Revenue", "Rs. 98.4 Lakh", "+ 24.1% Growth", COLOR_PRIMARY),
                createCategoryCard("Diabetology Sales", "Rs. 74.2 Lakh", "+ 18.5% Growth", "#0284C7"),
                createCategoryCard("Neurology & Ortho", "Rs. 72.4 Lakh", "+ 12.8% Growth", "#16A34A")
        );

        // Top Product Revenue Table
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 14px;");

        Label cardTitle = new Label("Top Product Sales Contribution (MTD)");
        cardTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        cardTitle.setTextFill(Color.web(COLOR_TEXT_MAIN));
        card.getChildren().add(cardTitle);

        String[][] prodRows = {
                {"CardioPro 50mg", "Cardiology", "14,200 Units", "Rs. 42.6 Lakh", "+ 28%"},
                {"GlycoShield 500mg", "Diabetology", "18,500 Units", "Rs. 37.0 Lakh", "+ 22%"},
                {"NeuroCalm 10mg", "Neurology", "9,800 Units", "Rs. 29.4 Lakh", "+ 15%"},
                {"OrthoFlex Forte", "Orthopedics", "8,200 Units", "Rs. 24.6 Lakh", "+ 12%"},
                {"ImmunoBoost Syrup", "Pediatrics", "12,000 Units", "Rs. 18.0 Lakh", "+ 19%"}
        };

        HBox tHead = new HBox(12);
        tHead.setPadding(new Insets(8, 12, 8, 12));
        tHead.setStyle("-fx-background-color: #F3E8FF; -fx-background-radius: 8px;");

        Label th1 = new Label("Product Name"); th1.setPrefWidth(160); th1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th2 = new Label("Therapeutic Category"); th2.setPrefWidth(140); th2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th3 = new Label("Volume Sold"); th3.setPrefWidth(120); th3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th4 = new Label("Revenue (Rs.)"); th4.setPrefWidth(120); th4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th5 = new Label("YoY Growth"); th5.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));

        tHead.getChildren().addAll(th1, th2, th3, th4, th5);
        card.getChildren().add(tHead);

        for (String[] r : prodRows) {
            HBox row = new HBox(12);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(10, 12, 10, 12));

            Label l1 = new Label(r[0]); l1.setPrefWidth(160); l1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
            Label l2 = new Label(r[1]); l2.setPrefWidth(140); l2.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
            Label l3 = new Label(r[2]); l3.setPrefWidth(120); l3.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
            Label l4 = new Label(r[3]); l4.setPrefWidth(120); l4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));

            Label l5 = new Label(r[4]);
            l5.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            l5.setTextFill(Color.web("#16A34A"));

            row.getChildren().addAll(l1, l2, l3, l4, l5);
            card.getChildren().add(row);
        }

        container.getChildren().addAll(categoryRow, card);
        scroll.setContent(container);

        VBox.setVgrow(scroll, Priority.ALWAYS);
        double w = (primaryStage != null && primaryStage.getWidth() > 0) ? primaryStage.getWidth() : 1360;
        double h = (primaryStage != null && primaryStage.getHeight() > 0) ? primaryStage.getHeight() : 860;
        mainScene = new Scene(root, w, h);
        return mainScene;
    }

    private VBox createCategoryCard(String t, String v, String g, String color) {
        VBox c = new VBox(6);
        c.setPadding(new Insets(18));
        c.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 14px;");
        HBox.setHgrow(c, Priority.ALWAYS);

        Label title = new Label(t); title.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13)); title.setTextFill(Color.web(COLOR_TEXT_MUTED));
        Label val = new Label(v); val.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22)); val.setTextFill(Color.web(COLOR_TEXT_MAIN));
        Label growth = new Label(g); growth.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12)); growth.setTextFill(Color.web(color));

        c.getChildren().addAll(title, val, growth);
        return c;
    }
}
