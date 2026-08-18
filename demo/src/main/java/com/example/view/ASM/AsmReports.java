package com.example.view.ASM;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * AsmReports - MedTrack Pro (ASM Portal)
 * Dedicated view for Regional Revenue Analytics, Representative Performance
 * Rankings,
 * and Executive Audit Exports.
 */
public class AsmReports {

    private Stage primaryStage;
    private Scene mainScene;

    // Tokens
    private static final String COLOR_PRIMARY = "#059669";
    private static final String COLOR_BACKGROUND = "#F5F7FA";
    private static final String COLOR_SURFACE = "#FFFFFF";
    private static final String COLOR_SURFACE_LOW = "#ECFDF5";
    private static final String COLOR_ON_SURFACE = "#191B23";
    private static final String COLOR_SECONDARY = "#5C5F61";
    private static final String COLOR_OUTLINE_VARIANT = "#E2E8F0";
    private static final String COLOR_SUCCESS = "#16A34A";
    private static final String COLOR_INFO = "#0284C7";

    public Scene createView() {
        return createView(primaryStage != null ? primaryStage : null);
    }

    public Scene createView(Stage stage) {
        if (stage != null) {
            this.primaryStage = stage;
            this.primaryStage.setMaximized(true);
        }

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BACKGROUND + ";");

        // Sidebar
        VBox sidebar = createSideNavBar();
        root.setLeft(sidebar);

        // Top Header
        HBox topNavBar = createTopNavBar();
        root.setTop(topNavBar);

        // Center Content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: " + COLOR_BACKGROUND + "; -fx-background: " + COLOR_BACKGROUND
                + "; -fx-border-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scrollPane.setContent(createContentNode(stage));
        double w = (primaryStage != null && primaryStage.getWidth() > 0) ? primaryStage.getWidth() : 1360;
        double h = (primaryStage != null && primaryStage.getHeight() > 0) ? primaryStage.getHeight() : 860;
        mainScene = new Scene(root, w, h);
        return mainScene;
    }

    public Node createContentNode(Stage stage) {
        if (stage != null) {
            this.primaryStage = stage;
        }

        VBox container = new VBox(20);
        container.setPadding(new Insets(24, 28, 40, 28));
        container.setStyle("-fx-background-color: " + COLOR_BACKGROUND + ";");

        // Header
        HBox headerBox = createHeaderBox();

        // Performance Highlights
        HBox kpiBox = createReportsKPIs();

        // Export Center Section
        VBox exportSection = createExportReportsSection();

        // Monthly Territory Sales Bar Chart Breakdown
        VBox chartCard = createMonthlyPerformanceCard();

        container.getChildren().addAll(headerBox, kpiBox, exportSection, chartCard);
        return container;
    }

    private HBox createHeaderBox() {
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(3);
        Label title = new Label("Regional Sales & Team Analytics");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web(COLOR_ON_SURFACE));

        Label subtitle = new Label(
                "Comprehensive quarterly performance audits, revenue realizations, and field productivity.");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitle.setTextFill(Color.web(COLOR_SECONDARY));
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button quickPdfBtn = new Button("Export Regional Summary");
        quickPdfBtn.setPrefHeight(38);
        quickPdfBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        quickPdfBtn.setStyle("-fx-background-color: " + COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-padding: 0 16px; -fx-background-radius: 8px; -fx-cursor: hand;");
        quickPdfBtn.setOnAction(e -> handleExportPDF());

        header.getChildren().addAll(titleBox, spacer, quickPdfBtn);
        return header;
    }

    private HBox createReportsKPIs() {
        HBox box = new HBox(16);

        VBox kpi1 = createMiniKPICard("North Zone Revenue", "$104,400", "+12% MoM Lift", COLOR_PRIMARY);
        VBox kpi2 = createMiniKPICard("Quota Achievement", "87.0%", "Target: $120,000", COLOR_SUCCESS);
        VBox kpi3 = createMiniKPICard("Doctor Retention", "94.5%", "High Loyalty Index", COLOR_INFO);

        HBox.setHgrow(kpi1, Priority.ALWAYS);
        HBox.setHgrow(kpi2, Priority.ALWAYS);
        HBox.setHgrow(kpi3, Priority.ALWAYS);

        box.getChildren().addAll(kpi1, kpi2, kpi3);
        return box;
    }

    private VBox createMiniKPICard(String title, String mainVal, String subVal, String colorHex) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16));
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: " + COLOR_OUTLINE_VARIANT + ";" +
                        "-fx-border-radius: 10px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        Label tLbl = new Label(title);
        tLbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        tLbl.setTextFill(Color.web(COLOR_SECONDARY));

        Label vLbl = new Label(mainVal);
        vLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        vLbl.setTextFill(Color.web(COLOR_ON_SURFACE));

        Label sLbl = new Label(subVal);
        sLbl.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 11));
        sLbl.setTextFill(Color.web(colorHex));

        card.getChildren().addAll(tLbl, vLbl, sLbl);
        return card;
    }

    private VBox createExportReportsSection() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: " + COLOR_OUTLINE_VARIANT + ";" +
                        "-fx-border-radius: 12px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        Label title = new Label("Export Formats & Regional Audits");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        title.setTextFill(Color.web(COLOR_ON_SURFACE));

        HBox btnGrid = new HBox(12);

        Button b1 = createReportDownloadButton("Regional Monthly PDF Audit", COLOR_PRIMARY);
        b1.setOnAction(e -> handleExportPDF());

        Button b2 = createReportDownloadButton("Excel Rep Call Performance Matrix", COLOR_SUCCESS);
        b2.setOnAction(e -> handleExportExcel());

        Button b3 = createReportDownloadButton("Territory Target vs Realization Deck", "#7C3AED");
        b3.setOnAction(e -> showInfoAlert("Deck Export", "Presentation slides for North Zone review exported."));

        btnGrid.getChildren().addAll(b1, b2, b3);
        card.getChildren().addAll(title, btnGrid);
        return card;
    }

    private Button createReportDownloadButton(String text, String colorHex) {
        Button btn = new Button(text);
        btn.setPrefHeight(42);
        btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        btn.setStyle("-fx-background-color: " + colorHex
                + "; -fx-text-fill: white; -fx-padding: 0 16px; -fx-background-radius: 8px; -fx-cursor: hand;");
        return btn;
    }

    private VBox createMonthlyPerformanceCard() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: " + COLOR_OUTLINE_VARIANT + ";" +
                        "-fx-border-radius: 12px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        HBox head = new HBox(10);
        head.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("Monthly Revenue Growth Trends (North Zone)");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        title.setTextFill(Color.web(COLOR_ON_SURFACE));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Label quarterTag = new Label("Q1 - Q2 Revenue Track");
        quarterTag.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        quarterTag.setPadding(new Insets(3, 8, 3, 8));
        quarterTag.setStyle("-fx-background-color: " + COLOR_SURFACE_LOW + "; -fx-text-fill: " + COLOR_PRIMARY
                + "; -fx-background-radius: 4px;");

        head.getChildren().addAll(title, sp, quarterTag);

        // Visual Bars
        HBox barsBox = new HBox(20);
        barsBox.setAlignment(Pos.BOTTOM_CENTER);
        barsBox.setPadding(new Insets(20, 10, 10, 10));

        String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun" };
        int[] vals = { 65, 78, 72, 92, 88, 104 };

        for (int i = 0; i < months.length; i++) {
            VBox col = new VBox(6);
            col.setAlignment(Pos.BOTTOM_CENTER);
            HBox.setHgrow(col, Priority.ALWAYS);

            Label valLbl = new Label("$" + vals[i] + "K");
            valLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
            valLbl.setTextFill(Color.web(COLOR_SECONDARY));

            Region bar = new Region();
            bar.setPrefWidth(48);
            bar.setMaxWidth(58);
            bar.setPrefHeight(vals[i] * 1.3);
            bar.setStyle("-fx-background-color: " + (i == months.length - 1 ? COLOR_PRIMARY : "rgba(5, 150, 105, 0.45)")
                    + "; -fx-background-radius: 6px 6px 0 0;");

            Label mLbl = new Label(months[i]);
            mLbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
            mLbl.setTextFill(Color.web(COLOR_ON_SURFACE));

            col.getChildren().addAll(valLbl, bar, mLbl);
            barsBox.getChildren().add(col);
        }

        card.getChildren().addAll(head, barsBox);
        return card;
    }

    private void handleExportPDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Regional Performance Audit (PDF)");
        fileChooser.setInitialFileName("ASM_North_Zone_Performance_Audit.pdf");
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            showInfoAlert("PDF Generated", "Performance report saved to " + file.getName());
        }
    }

    private void handleExportExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Rep Matrix (Excel)");
        fileChooser.setInitialFileName("ASM_North_Rep_Activity_Matrix.xlsx");
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            showInfoAlert("Excel Spreadsheet Created", "MR call matrix exported successfully.");
        }
    }

    private VBox createSideNavBar() {
        VBox sidebar = new VBox(14);
        sidebar.setPrefWidth(280);
        sidebar.setPadding(new Insets(24, 16, 20, 16));
        sidebar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: transparent " + COLOR_OUTLINE_VARIANT
                + " transparent transparent; -fx-border-width: 0 1 0 0;");

        HBox logoContainer = new HBox(12);
        logoContainer.setAlignment(Pos.CENTER_LEFT);
        StackPane logoBadge = new StackPane();
        logoBadge.setPrefSize(42, 42);
        logoBadge.setStyle("-fx-background-color: " + COLOR_PRIMARY + "; -fx-background-radius: 10px;");
        Label logoLetter = new Label("ASM");
        logoLetter.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        logoLetter.setTextFill(Color.WHITE);
        logoBadge.getChildren().add(logoLetter);
        logoContainer.getChildren().addAll(logoBadge,
                new VBox(1, new Label("MRDesk ASM"), new Label("Managerial Suite")));

        VBox navLinks = new VBox(6);
        navLinks.getChildren().addAll(
                createSidebarItem("Dashboard", "", false),
                createSidebarItem("My Team (MRs)", "", false),
                createSidebarItem("Territories", "", false),
                createSidebarItem("Approvals", "", false),
                createSidebarItem("Reports & Analytics", "", true),
                createSidebarItem("Settings", "", false));

        sidebar.getChildren().addAll(logoContainer, navLinks);
        return sidebar;
    }

    private Button createSidebarItem(String title, String icon, boolean isActive) {
        Button btn = new Button((icon.isEmpty() ? "" : icon + "   ") + title);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPrefHeight(40);
        btn.setPadding(new Insets(8, 14, 8, 14));
        btn.setFont(Font.font("Segoe UI", isActive ? FontWeight.BOLD : FontWeight.SEMI_BOLD, 13));
        if (isActive) {
            btn.setStyle("-fx-background-color: " + COLOR_SURFACE_LOW + "; -fx-text-fill: " + COLOR_PRIMARY
                    + "; -fx-background-radius: 8px; -fx-border-color: transparent " + COLOR_PRIMARY
                    + " transparent transparent; -fx-border-width: 0 4 0 0;");
        } else {
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_SECONDARY
                    + "; -fx-background-radius: 8px; -fx-cursor: hand;");
        }
        btn.setOnAction(e -> handleNavClick(title));
        return btn;
    }

    private void handleNavClick(String title) {
        if (primaryStage == null)
            return;
        if ("Dashboard".equals(title)) {
            primaryStage.setScene(new AsmDashBoard().createView(primaryStage));
        } else if ("My Team (MRs)".equals(title)) {
            primaryStage.setScene(new AsmTeam().createView(primaryStage));
        } else if ("Territories".equals(title)) {
            primaryStage.setScene(new AsmTerritories().createView(primaryStage));
        } else if ("Approvals".equals(title)) {
            primaryStage.setScene(new AsmApprovals().createView(primaryStage));
        } else if ("Reports & Analytics".equals(title)) {
            primaryStage.setScene(new AsmReports().createView(primaryStage));
        } else if ("Settings".equals(title)) {
            primaryStage.setScene(new AsmSettings().createView(primaryStage));
        }
    }

    private HBox createTopNavBar() {
        HBox topBar = new HBox(16);
        topBar.setPrefHeight(64);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 24, 0, 24));
        topBar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); -fx-border-color: transparent transparent "
                + COLOR_OUTLINE_VARIANT + " transparent; -fx-border-width: 0 0 1 0;");

        Label searchLbl = new Label("Area Sales Manager • Regional Audit & Analytics Center");
        searchLbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        searchLbl.setTextFill(Color.web(COLOR_SECONDARY));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label greeting = new Label("Marcus Vance (ASM - North)");
        greeting.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        greeting.setTextFill(Color.web(COLOR_ON_SURFACE));

        topBar.getChildren().addAll(searchLbl, spacer, greeting);
        return topBar;
    }

    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
