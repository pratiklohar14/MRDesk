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
 * RsmTargets - MedTrack Pro (RSM Portal)
 * Dedicated view for Regional Sales Target Management & Quota Distribution.
 */
public class RsmTargets {

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
        VBox sidebar = new RsmDashBoard().createSideNavBar(primaryStage, "Targets");
        root.setLeft(sidebar);

        // Main Content
        VBox mainBox = new VBox(0);

        // Header Bar
        HBox topBar = new HBox(16);
        topBar.setPrefHeight(72);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 28, 0, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: transparent transparent " + COLOR_BORDER + " transparent; -fx-border-width: 0 0 1 0;");

        Label title = new Label("Regional Sales Target Allocation");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setTextFill(Color.web(COLOR_TEXT_MAIN));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        // Territory Targets Table Card
        VBox tableCard = new VBox(16);
        tableCard.setPadding(new Insets(20));
        tableCard.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 14px;");

        Button newTargetBtn = new Button("+ Allocate New Target");
        newTargetBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        newTargetBtn.setStyle("-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white; -fx-background-radius: 8px; -fx-padding: 8px 18px; -fx-cursor: hand;");
        newTargetBtn.setOnAction(e -> handleAllocateTarget(tableCard));

        topBar.getChildren().addAll(title, sp, newTargetBtn);
        mainBox.getChildren().add(topBar);

        // Content Scroll
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: " + COLOR_BG_CANVAS + "; -fx-background: " + COLOR_BG_CANVAS + "; -fx-border-color: transparent;");

        VBox container = new VBox(20);
        container.setPadding(new Insets(24, 28, 28, 28));

        // Targets Summary Cards
        HBox summaryRow = new HBox(16);
        VBox card1 = createSummaryCard("Total Regional Target", "Rs. 3.00 Cr", "Q2 Target", COLOR_PRIMARY);
        VBox card2 = createSummaryCard("Achieved Target (MTD)", "Rs. 2.45 Cr", "72.4% Complete", "#16A34A");
        VBox card3 = createSummaryCard("Remaining Target", "Rs. 55.0 Lakh", "14 Days Remaining", "#D97706");

        HBox.setHgrow(card1, Priority.ALWAYS);
        HBox.setHgrow(card2, Priority.ALWAYS);
        HBox.setHgrow(card3, Priority.ALWAYS);
        summaryRow.getChildren().addAll(card1, card2, card3);

        Label tableTitle = new Label("Area Territory Target Breakdown");
        tableTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        tableTitle.setTextFill(Color.web(COLOR_TEXT_MAIN));
        tableCard.getChildren().add(tableTitle);

        String[][] targetRows = {
                {"Lucknow Central", "Rahul Verma", "Rs. 75.0 Lakh", "Rs. 58.6 Lakh", "78.1%"},
                {"Kanpur District", "Neha Singh", "Rs. 60.0 Lakh", "Rs. 48.2 Lakh", "80.3%"},
                {"Varanasi East", "Suresh Yadav", "Rs. 60.0 Lakh", "Rs. 46.7 Lakh", "77.8%"},
                {"Agra Zone", "Pooja Sharma", "Rs. 55.0 Lakh", "Rs. 41.3 Lakh", "75.1%"},
                {"Allahabad Sector", "Amit Tiwari", "Rs. 50.0 Lakh", "Rs. 38.9 Lakh", "77.8%"}
        };

        HBox tHead = new HBox(12);
        tHead.setPadding(new Insets(8, 12, 8, 12));
        tHead.setStyle("-fx-background-color: #F3E8FF; -fx-background-radius: 8px;");

        Label th1 = new Label("Territory Area"); th1.setPrefWidth(160); th1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th2 = new Label("Assigned ASM"); th2.setPrefWidth(140); th2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th3 = new Label("Monthly Target"); th3.setPrefWidth(120); th3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th4 = new Label("Achieved (MTD)"); th4.setPrefWidth(120); th4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th5 = new Label("Progress"); th5.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        tHead.getChildren().addAll(th1, th2, th3, th4, th5);

        tableCard.getChildren().add(tHead);

        for (String[] r : targetRows) {
            HBox row = new HBox(12);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(10, 12, 10, 12));

            Label l1 = new Label(r[0]); l1.setPrefWidth(160); l1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
            Label l2 = new Label(r[1]); l2.setPrefWidth(140); l2.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
            Label l3 = new Label(r[2]); l3.setPrefWidth(120); l3.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
            Label l4 = new Label(r[3]); l4.setPrefWidth(120); l4.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));

            HBox pBox = new HBox(8);
            pBox.setAlignment(Pos.CENTER_LEFT);
            ProgressBar pb = new ProgressBar(Double.parseDouble(r[4].replace("%", "")) / 100.0);
            pb.setPrefWidth(120);
            pb.setStyle("-fx-accent: " + COLOR_PRIMARY + ";");
            Label pct = new Label(r[4]); pct.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            pBox.getChildren().addAll(pb, pct);

            row.getChildren().addAll(l1, l2, l3, l4, pBox);
            tableCard.getChildren().add(row);
        }

        container.getChildren().addAll(summaryRow, tableCard);
        scroll.setContent(container);

        VBox.setVgrow(scroll, Priority.ALWAYS);
        double w = (primaryStage != null && primaryStage.getWidth() > 0) ? primaryStage.getWidth() : 1360;
        double h = (primaryStage != null && primaryStage.getHeight() > 0) ? primaryStage.getHeight() : 860;
        mainScene = new Scene(root, w, h);
        return mainScene;
    }

    private VBox createSummaryCard(String title, String val, String sub, String color) {
        VBox c = new VBox(8);
        c.setPadding(new Insets(18));
        c.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 14px;");

        Label t = new Label(title); t.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13)); t.setTextFill(Color.web(COLOR_TEXT_MUTED));
        Label v = new Label(val); v.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22)); v.setTextFill(Color.web(COLOR_TEXT_MAIN));
        Label s = new Label(sub); s.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12)); s.setTextFill(Color.web(color));

        c.getChildren().addAll(t, v, s);
        return c;
    }

    private void handleAllocateTarget(VBox tableCard) {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Allocate New Target");
        dialog.setHeaderText("Set monthly sales quota for territory:");

        ButtonType allocateBtnType = new ButtonType("Allocate", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(allocateBtnType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField territoryF = new TextField();
        territoryF.setPromptText("Territory Area");
        TextField asmF = new TextField();
        asmF.setPromptText("Assigned ASM");
        TextField targetF = new TextField();
        targetF.setPromptText("Monthly Target (e.g. Rs. 50.0 Lakh)");

        grid.add(new Label("Territory Area:"), 0, 0);
        grid.add(territoryF, 1, 0);
        grid.add(new Label("Assigned ASM:"), 0, 1);
        grid.add(asmF, 1, 1);
        grid.add(new Label("Monthly Target:"), 0, 2);
        grid.add(targetF, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == allocateBtnType) {
                return new String[]{
                        territoryF.getText().isEmpty() ? "New Area" : territoryF.getText(),
                        asmF.getText().isEmpty() ? "Unassigned" : asmF.getText(),
                        targetF.getText().isEmpty() ? "Rs. 50.0 Lakh" : targetF.getText(),
                        "Rs. 0.0 Lakh",
                        "0.0%"
                };
            }
            return null;
        });

        dialog.showAndWait().ifPresent(r -> {
            HBox row = new HBox(12);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(10, 12, 10, 12));

            Label l1 = new Label(r[0]); l1.setPrefWidth(160); l1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
            Label l2 = new Label(r[1]); l2.setPrefWidth(140); l2.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
            Label l3 = new Label(r[2]); l3.setPrefWidth(120); l3.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
            Label l4 = new Label(r[3]); l4.setPrefWidth(120); l4.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));

            HBox pBox = new HBox(8);
            pBox.setAlignment(Pos.CENTER_LEFT);
            ProgressBar pb = new ProgressBar(0.0);
            pb.setPrefWidth(120);
            pb.setStyle("-fx-accent: " + COLOR_PRIMARY + ";");
            Label pct = new Label(r[4]); pct.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            pBox.getChildren().addAll(pb, pct);

            row.getChildren().addAll(l1, l2, l3, l4, pBox);
            tableCard.getChildren().add(row);
        });
    }
}
