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
 * RsmTeamPerformance - MedTrack Pro (RSM Portal)
 * Dedicated view for Regional Field Representatives & Team Productivity.
 */
public class RsmTeamPerformance {

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
        VBox sidebar = new RsmDashBoard().createSideNavBar(primaryStage, "Team Performance");
        root.setLeft(sidebar);

        // Main Body
        VBox mainBox = new VBox(0);

        HBox topBar = new HBox(16);
        topBar.setPrefHeight(72);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 28, 0, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: transparent transparent " + COLOR_BORDER
                + " transparent; -fx-border-width: 0 0 1 0;");

        Label title = new Label("Regional Team Performance Overview");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setTextFill(Color.web(COLOR_TEXT_MAIN));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        TextField searchField = new TextField();
        searchField.setPromptText("Search MR / ASM...");
        searchField.setPrefHeight(38);
        searchField.setPrefWidth(220);
        searchField.setStyle(
                "-fx-background-color: #F1F5F9; -fx-background-radius: 8px; -fx-border-color: #E2E8F0; -fx-border-radius: 8px;");

        topBar.getChildren().addAll(title, sp, searchField);
        mainBox.getChildren().add(topBar);

        // Content Scroll
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: " + COLOR_BG_CANVAS + "; -fx-background: " + COLOR_BG_CANVAS
                + "; -fx-border-color: transparent;");

        VBox container = new VBox(20);
        container.setPadding(new Insets(24, 28, 28, 28));

        // Team Metrics Row
        HBox metricsRow = new HBox(16);
        metricsRow.getChildren().addAll(
                createMiniCard("Total Field Representatives", "42 Reps", "Active across 5 zones", COLOR_PRIMARY),
                createMiniCard("Average Daily Calls", "12.4 Visits", "Target: 10 Visits/day", "#16A34A"),
                createMiniCard("Sample Distribution Rate", "94.2%", "Optimal Inventory Flow", "#0284C7"));

        // Representatives Roster Table
        VBox tableCard = new VBox(16);
        tableCard.setPadding(new Insets(20));
        tableCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: "
                + COLOR_BORDER + "; -fx-border-radius: 14px;");

        Label tableTitle = new Label("Field Medical Representatives (MRs)");
        tableTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        tableTitle.setTextFill(Color.web(COLOR_TEXT_MAIN));
        tableCard.getChildren().add(tableTitle);

        String[][] teamRows = {
                { "Alex Mercer", "Central District", "Rahul Verma", "14 Visits/day", "Rs. 12.4 Lakh", "Active" },
                { "Sarah Connor", "North Sector", "Neha Singh", "12 Visits/day", "Rs. 10.8 Lakh", "Active" },
                { "David Miller", "East Sector", "Suresh Yadav", "11 Visits/day", "Rs. 9.6 Lakh", "Active" },
                { "Robert Chen", "South Hub", "Pooja Sharma", "13 Visits/day", "Rs. 11.2 Lakh", "Active" },
                { "Priya Sharma", "West Zone", "Amit Tiwari", "10 Visits/day", "Rs. 8.9 Lakh", "On Field" }
        };

        HBox tHead = new HBox(12);
        tHead.setPadding(new Insets(8, 12, 8, 12));
        tHead.setStyle("-fx-background-color: #F3E8FF; -fx-background-radius: 8px;");

        Label th1 = new Label("Representative");
        th1.setPrefWidth(140);
        th1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th2 = new Label("Territory");
        th2.setPrefWidth(130);
        th2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th3 = new Label("Reporting ASM");
        th3.setPrefWidth(130);
        th3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th4 = new Label("Daily Call Avg");
        th4.setPrefWidth(120);
        th4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th5 = new Label("Sales Generated");
        th5.setPrefWidth(120);
        th5.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th6 = new Label("Status");
        th6.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));

        tHead.getChildren().addAll(th1, th2, th3, th4, th5, th6);
        tableCard.getChildren().add(tHead);

        VBox rowsContainer = new VBox();

        Runnable updateTable = () -> {
            rowsContainer.getChildren().clear();
            String query = searchField.getText() == null ? "" : searchField.getText().toLowerCase().trim();
            for (String[] r : teamRows) {
                if (query.isEmpty() || r[0].toLowerCase().contains(query) || r[1].toLowerCase().contains(query) || r[2].toLowerCase().contains(query)) {
                    HBox row = new HBox(12);
                    row.setAlignment(Pos.CENTER_LEFT);
                    row.setPadding(new Insets(10, 12, 10, 12));

                    Label l1 = new Label(r[0]);
                    l1.setPrefWidth(140);
                    l1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
                    Label l2 = new Label(r[1]);
                    l2.setPrefWidth(130);
                    l2.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
                    Label l3 = new Label(r[2]);
                    l3.setPrefWidth(130);
                    l3.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
                    Label l4 = new Label(r[3]);
                    l4.setPrefWidth(120);
                    l4.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
                    Label l5 = new Label(r[4]);
                    l5.setPrefWidth(120);
                    l5.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));

                    Label l6 = new Label(r[5]);
                    l6.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
                    l6.setStyle(
                            "-fx-background-color: #DCFCE7; -fx-text-fill: #16A34A; -fx-padding: 3 8; -fx-background-radius: 6px;");

                    row.getChildren().addAll(l1, l2, l3, l4, l5, l6);
                    rowsContainer.getChildren().add(row);
                }
            }
        };

        searchField.textProperty().addListener((obs, oldVal, newVal) -> updateTable.run());
        updateTable.run();
        tableCard.getChildren().add(rowsContainer);

        container.getChildren().addAll(metricsRow, tableCard);
        scroll.setContent(container);

        VBox.setVgrow(scroll, Priority.ALWAYS);
        double w = (primaryStage != null && primaryStage.getWidth() > 0) ? primaryStage.getWidth() : 1360;
        double h = (primaryStage != null && primaryStage.getHeight() > 0) ? primaryStage.getHeight() : 860;
        mainScene = new Scene(root, w, h);
        return mainScene;
    }

    private VBox createMiniCard(String t, String v, String s, String color) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER
                + "; -fx-border-radius: 14px;");
        HBox.setHgrow(card, Priority.ALWAYS);

        Label title = new Label(t);
        title.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        title.setTextFill(Color.web(COLOR_TEXT_MUTED));
        Label val = new Label(v);
        val.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        val.setTextFill(Color.web(COLOR_TEXT_MAIN));
        Label sub = new Label(s);
        sub.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        sub.setTextFill(Color.web(color));

        card.getChildren().addAll(title, val, sub);
        return card;
    }
}
