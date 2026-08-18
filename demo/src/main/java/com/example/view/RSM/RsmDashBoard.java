package com.example.view.RSM;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import com.example.view.Welcome;

/**
 * RsmDashBoard - MedTrack Pro (RSM Portal)
 * Dedicated view for Regional Sales Manager Executive Dashboard.
 */
public class RsmDashBoard {

    private Stage primaryStage;
    private Scene dashboardScene;
    private ScrollPane scrollPane;

    // RSM Palette Tokens matching reference design
    private static final String COLOR_PRIMARY = "#7C3AED";         // Deep Purple Accent
    private static final String COLOR_PRIMARY_DARK = "#4C1D95";    // Sidebar Deep Gradient Dark
    private static final String COLOR_PRIMARY_LIGHT = "#8B5CF6";   // Active Highlight
    private static final String COLOR_BG_CANVAS = "#F8FAFC";      // Soft neutral canvas
    private static final String COLOR_SURFACE = "#FFFFFF";        // Card surface
    private static final String COLOR_TEXT_MAIN = "#1E1B4B";      // Deep navy/violet text
    private static final String COLOR_TEXT_MUTED = "#64748B";     // Muted gray
    private static final String COLOR_BORDER = "#E9D5FF";         // Soft lavender border
    private static final String COLOR_SUCCESS = "#16A34A";        // Positive green
    private static final String COLOR_PURPLE_BG = "#F3E8FF";       // Soft purple badge container

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

        // 1. Sidebar Navigation
        VBox sidebar = createSideNavBar("Dashboard");
        root.setLeft(sidebar);

        // 2. Main Body (TopBar + Scrollable Canvas)
        VBox mainBox = new VBox(0);

        HBox topNavBar = createTopHeaderBar();
        mainBox.getChildren().add(topNavBar);

        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: " + COLOR_BG_CANVAS + "; -fx-background: " + COLOR_BG_CANVAS
                + "; -fx-border-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox contentContainer = new VBox(20);
        contentContainer.setPadding(new Insets(24, 28, 28, 28));

        // Section 1: KPI Cards Row (4 Top Cards)
        HBox kpiRow = createKPICardsRow();

        // Section 2: Middle Analytics Row (Sales Trend & Top Performing Areas)
        HBox middleRow = new HBox(20);
        VBox salesTrendCard = createSalesTrendCard();
        VBox topAreasCard = createTopAreasCard();
        HBox.setHgrow(salesTrendCard, Priority.ALWAYS);
        middleRow.getChildren().addAll(salesTrendCard, topAreasCard);

        // Section 3: Bottom Row (ASM Performance Table & Recent Activities)
        HBox bottomRow = new HBox(20);
        VBox asmPerfCard = createAsmPerformanceCard();
        VBox recentActivitiesCard = createRecentActivitiesCard();
        HBox.setHgrow(asmPerfCard, Priority.ALWAYS);
        bottomRow.getChildren().addAll(asmPerfCard, recentActivitiesCard);

        contentContainer.getChildren().addAll(kpiRow, middleRow, bottomRow);
        scrollPane.setContent(contentContainer);

        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        mainBox.getChildren().add(scrollPane);

        double w = (primaryStage != null && primaryStage.getWidth() > 0) ? primaryStage.getWidth() : 1360;
        double h = (primaryStage != null && primaryStage.getHeight() > 0) ? primaryStage.getHeight() : 860;
        dashboardScene = new Scene(root, w, h);
        return dashboardScene;
    }

    // ==========================================
    // TOP HEADER BAR
    // ==========================================
    private HBox createTopHeaderBar() {
        HBox topBar = new HBox(16);
        topBar.setPrefHeight(72);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 28, 0, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: transparent transparent " + COLOR_BORDER
                + " transparent; -fx-border-width: 0 0 1 0;");

        // Hamburger Menu Icon
        Label menuIcon = new Label("=");
        menuIcon.setStyle("-fx-font-size: 20px; -fx-text-fill: " + COLOR_TEXT_MUTED + "; -fx-cursor: hand;");

        // Welcome Titles
        VBox titleBox = new VBox(2);
        Label mainTitle = new Label("Welcome back, Amit Sharma!");
        mainTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        mainTitle.setTextFill(Color.web(COLOR_TEXT_MAIN));

        Label subtitle = new Label("Here's your region performance overview.");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        subtitle.setTextFill(Color.web(COLOR_TEXT_MUTED));
        titleBox.getChildren().addAll(mainTitle, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Date Period ComboBox Selector
        ComboBox<String> dateSelector = new ComboBox<>();
        dateSelector.getItems().addAll("May 2025", "Apr 2025", "Mar 2025", "Q2 2025");
        dateSelector.setValue("May 2025");
        dateSelector.setPrefHeight(38);
        dateSelector.setStyle(
                "-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-weight: bold; -fx-font-size: 13px; -fx-cursor: hand;");

        // Notification Bell Icon with Badge
        StackPane notifPane = new StackPane();
        Button bellBtn = new Button("N");
        bellBtn.setStyle("-fx-background-color: #F1F5F9; -fx-font-size: 16px; -fx-background-radius: 10px; -fx-pref-width: 38px; -fx-pref-height: 38px; -fx-cursor: hand;");
        bellBtn.setOnAction(e -> handleNavClick("Alerts"));

        Label notifBadge = new Label("3");
        notifBadge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        notifBadge.setTextFill(Color.WHITE);
        notifBadge.setStyle("-fx-background-color: #EC4899; -fx-background-radius: 10px; -fx-padding: 1 5;");
        StackPane.setAlignment(notifBadge, Pos.TOP_RIGHT);
        StackPane.setMargin(notifBadge, new Insets(-2, -2, 0, 0));
        notifPane.getChildren().addAll(bellBtn, notifBadge);

        topBar.getChildren().addAll(menuIcon, titleBox, spacer, dateSelector, notifPane);
        return topBar;
    }

    // ==========================================
    // TOP 4 KPI CARDS
    // ==========================================
    private HBox createKPICardsRow() {
        HBox row = new HBox(16);

        // 1. Total Sales (MTD)
        VBox kpi1 = createKpiCard("Total Sales (MTD)", "Rs. 2.45 Cr", "+ 18.6% vs Apr 2025", true, "S", null, 0.0);

        // 2. Target (MTD) with Progress Bar
        VBox kpi2 = createKpiCard("Target (MTD)", "Rs. 3.00 Cr", "72.4% Achieved", false, "T", COLOR_PRIMARY, 0.724);

        // 3. Total Orders (MTD)
        VBox kpi3 = createKpiCard("Total Orders (MTD)", "856", "+ 15.2% vs Apr 2025", true, "O", null, 0.0);

        // 4. Active Retailers
        VBox kpi4 = createKpiCard("Active Retailers", "1,248", "+ 11.3% vs Apr 2025", true, "R", null, 0.0);

        HBox.setHgrow(kpi1, Priority.ALWAYS);
        HBox.setHgrow(kpi2, Priority.ALWAYS);
        HBox.setHgrow(kpi3, Priority.ALWAYS);
        HBox.setHgrow(kpi4, Priority.ALWAYS);

        row.getChildren().addAll(kpi1, kpi2, kpi3, kpi4);
        return row;
    }

    private VBox createKpiCard(String title, String mainValue, String subText, boolean isGrowth, String iconEmoji, String progressColor, double progressVal) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER
                + "; -fx-border-radius: 14px; -fx-effect: dropshadow(gaussian, rgba(124, 58, 237, 0.04), 10, 0, 0, 3);");

        HBox topHead = new HBox(12);
        topHead.setAlignment(Pos.CENTER_LEFT);

        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(42, 42);
        iconBox.setStyle("-fx-background-color: " + COLOR_PURPLE_BG + "; -fx-background-radius: 12px;");
        Label iconLbl = new Label(iconEmoji);
        iconLbl.setStyle("-fx-font-size: 18px;");
        iconBox.getChildren().add(iconLbl);

        VBox titleValBox = new VBox(2);
        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        titleLbl.setTextFill(Color.web(COLOR_TEXT_MUTED));

        Label valLbl = new Label(mainValue);
        valLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        valLbl.setTextFill(Color.web(COLOR_TEXT_MAIN));
        titleValBox.getChildren().addAll(titleLbl, valLbl);

        topHead.getChildren().addAll(iconBox, titleValBox);

        card.getChildren().add(topHead);

        if (progressColor != null) {
            VBox progBox = new VBox(6);
            Label progText = new Label(subText);
            progText.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
            progText.setTextFill(Color.web(COLOR_TEXT_MAIN));

            ProgressBar pb = new ProgressBar(progressVal);
            pb.setMaxWidth(Double.MAX_VALUE);
            pb.setPrefHeight(8);
            pb.setStyle("-fx-accent: " + progressColor + "; -fx-control-inner-background: #F1F5F9;");

            progBox.getChildren().addAll(progText, pb);
            card.getChildren().add(progBox);
        } else {
            Label subLbl = new Label(subText);
            subLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            subLbl.setTextFill(isGrowth ? Color.web(COLOR_SUCCESS) : Color.web(COLOR_TEXT_MUTED));
            card.getChildren().add(subLbl);
        }

        return card;
    }

    // ==========================================
    // MIDDLE ROW: SALES TREND CHART
    // ==========================================
    private VBox createSalesTrendCard() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER
                + "; -fx-border-radius: 14px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.03), 10, 0, 0, 2);");

        HBox head = new HBox(12);
        head.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Sales Trend");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        title.setTextFill(Color.web(COLOR_TEXT_MAIN));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        // Legend items
        HBox legend = new HBox(16);
        legend.setAlignment(Pos.CENTER_RIGHT);

        HBox leg1 = new HBox(6);
        leg1.setAlignment(Pos.CENTER_LEFT);
        Circle dot1 = new Circle(4, Color.web(COLOR_PRIMARY));
        Label l1 = new Label("Current Year");
        l1.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        l1.setTextFill(Color.web(COLOR_TEXT_MAIN));
        leg1.getChildren().addAll(dot1, l1);

        HBox leg2 = new HBox(6);
        leg2.setAlignment(Pos.CENTER_LEFT);
        Circle dot2 = new Circle(4, Color.web("#CBD5E1"));
        Label l2 = new Label("Previous Year");
        l2.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        l2.setTextFill(Color.web(COLOR_TEXT_MUTED));
        leg2.getChildren().addAll(dot2, l2);

        ComboBox<String> chartFilter = new ComboBox<>();
        chartFilter.getItems().addAll("MTD", "QTD", "YTD");
        chartFilter.setValue("MTD");
        chartFilter.setPrefHeight(32);
        chartFilter.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 6px; -fx-font-weight: bold;");

        legend.getChildren().addAll(leg1, leg2, chartFilter);
        head.getChildren().addAll(title, sp, legend);

        // Interactive Trend Visualization Canvas
        Pane chartPane = new Pane();
        chartPane.setPrefHeight(200);

        // Y Axis Labels
        String[] yLabels = {"4 Cr", "3 Cr", "2 Cr", "1 Cr", "0"};
        double[] yPositions = {10, 50, 90, 130, 170};
        for (int i = 0; i < yLabels.length; i++) {
            Label yLbl = new Label(yLabels[i]);
            yLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
            yLbl.setTextFill(Color.web(COLOR_TEXT_MUTED));
            yLbl.setLayoutX(0);
            yLbl.setLayoutY(yPositions[i] - 6);

            Line gridLine = new Line(40, yPositions[i], 580, yPositions[i]);
            gridLine.setStroke(Color.web("#F1F5F9"));
            gridLine.setStrokeWidth(1);
            chartPane.getChildren().addAll(yLbl, gridLine);
        }

        // X Axis Months
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun"};
        double[] xPos = {60, 160, 260, 360, 460, 560};

        for (int i = 0; i < months.length; i++) {
            Label xLbl = new Label(months[i]);
            xLbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
            xLbl.setTextFill(Color.web(COLOR_TEXT_MUTED));
            xLbl.setLayoutX(xPos[i] - 10);
            xLbl.setLayoutY(180);
            chartPane.getChildren().add(xLbl);
        }

        // Current Year Data Line (Purple)
        double[] currentY = {145, 120, 100, 80, 50, 40};
        for (int i = 0; i < currentY.length - 1; i++) {
            Line line = new Line(xPos[i], currentY[i], xPos[i + 1], currentY[i + 1]);
            line.setStroke(Color.web(COLOR_PRIMARY));
            line.setStrokeWidth(3);

            Circle nodeCircle = new Circle(xPos[i], currentY[i], 5, Color.web(COLOR_PRIMARY));
            nodeCircle.setStroke(Color.WHITE);
            nodeCircle.setStrokeWidth(2);
            chartPane.getChildren().addAll(line, nodeCircle);
        }
        Circle lastNode = new Circle(xPos[currentY.length - 1], currentY[currentY.length - 1], 5, Color.web(COLOR_PRIMARY));
        lastNode.setStroke(Color.WHITE);
        lastNode.setStrokeWidth(2);
        chartPane.getChildren().add(lastNode);

        // Previous Year Data Line (Gray)
        double[] prevY = {160, 140, 130, 115, 95, 80};
        for (int i = 0; i < prevY.length - 1; i++) {
            Line line = new Line(xPos[i], prevY[i], xPos[i + 1], prevY[i + 1]);
            line.setStroke(Color.web("#CBD5E1"));
            line.setStrokeWidth(2);

            Circle nodeCircle = new Circle(xPos[i], prevY[i], 4, Color.web("#CBD5E1"));
            chartPane.getChildren().addAll(line, nodeCircle);
        }
        Circle lastPrevNode = new Circle(xPos[prevY.length - 1], prevY[prevY.length - 1], 4, Color.web("#CBD5E1"));
        chartPane.getChildren().add(lastPrevNode);

        card.getChildren().addAll(head, chartPane);
        return card;
    }

    // ==========================================
    // MIDDLE ROW: TOP PERFORMING AREAS
    // ==========================================
    private VBox createTopAreasCard() {
        VBox card = new VBox(14);
        card.setPrefWidth(340);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER
                + "; -fx-border-radius: 14px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.03), 10, 0, 0, 2);");

        Label title = new Label("Top Performing Areas (MTD)");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        title.setTextFill(Color.web(COLOR_TEXT_MAIN));

        card.getChildren().add(title);

        String[][] areas = {
                {"1", "Lucknow", "Rs. 28.6 Lakh", "+ 22%"},
                {"2", "Kanpur", "Rs. 24.3 Lakh", "+ 18%"},
                {"3", "Varanasi", "Rs. 21.7 Lakh", "+ 16%"},
                {"4", "Agra", "Rs. 19.8 Lakh", "+ 14%"},
                {"5", "Allahabad", "Rs. 17.2 Lakh", "+ 12%"}
        };

        for (String[] area : areas) {
            HBox itemRow = new HBox(12);
            itemRow.setAlignment(Pos.CENTER_LEFT);
            itemRow.setPadding(new Insets(8, 12, 8, 12));
            itemRow.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 8px;");

            Label rankLbl = new Label(area[0]);
            rankLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            rankLbl.setTextFill(Color.web(COLOR_TEXT_MUTED));
            rankLbl.setPrefWidth(16);

            Label nameLbl = new Label(area[1]);
            nameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
            nameLbl.setTextFill(Color.web(COLOR_TEXT_MAIN));

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Label salesLbl = new Label(area[2]);
            salesLbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
            salesLbl.setTextFill(Color.web(COLOR_TEXT_MAIN));

            Label growthLbl = new Label(area[3]);
            growthLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            growthLbl.setTextFill(Color.web(COLOR_SUCCESS));

            itemRow.getChildren().addAll(rankLbl, nameLbl, sp, salesLbl, growthLbl);
            card.getChildren().add(itemRow);
        }

        return card;
    }

    // ==========================================
    // BOTTOM ROW: ASM PERFORMANCE TABLE
    // ==========================================
    private VBox createAsmPerformanceCard() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER
                + "; -fx-border-radius: 14px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.03), 10, 0, 0, 2);");

        Label title = new Label("ASM Performance (MTD)");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        title.setTextFill(Color.web(COLOR_TEXT_MAIN));
        card.getChildren().add(title);

        // Table Headers
        HBox headerRow = new HBox(12);
        headerRow.setPadding(new Insets(8, 12, 8, 12));
        headerRow.setStyle("-fx-background-color: #F3E8FF; -fx-background-radius: 8px;");

        Label h1 = new Label("ASM");
        h1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        h1.setPrefWidth(140);
        h1.setTextFill(Color.web(COLOR_TEXT_MAIN));

        Label h2 = new Label("Sales (Rs.)");
        h2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        h2.setPrefWidth(100);
        h2.setTextFill(Color.web(COLOR_TEXT_MAIN));

        Label h3 = new Label("Target (Rs.)");
        h3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        h3.setPrefWidth(100);
        h3.setTextFill(Color.web(COLOR_TEXT_MAIN));

        Label h4 = new Label("Achievement");
        h4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        h4.setTextFill(Color.web(COLOR_TEXT_MAIN));

        headerRow.getChildren().addAll(h1, h2, h3, h4);
        card.getChildren().add(headerRow);

        // ASM Rows matching image
        Object[][] asmData = {
                {"Rahul Verma", "Rs. 58.6 Lakh", "Rs. 75.0 Lakh", 0.781, "78.1%"},
                {"Neha Singh", "Rs. 48.2 Lakh", "Rs. 60.0 Lakh", 0.803, "80.3%"},
                {"Suresh Yadav", "Rs. 46.7 Lakh", "Rs. 60.0 Lakh", 0.778, "77.8%"},
                {"Pooja Sharma", "Rs. 41.3 Lakh", "Rs. 55.0 Lakh", 0.751, "75.1%"},
                {"Amit Tiwari", "Rs. 38.9 Lakh", "Rs. 50.0 Lakh", 0.778, "77.8%"}
        };

        for (Object[] row : asmData) {
            HBox dataRow = new HBox(12);
            dataRow.setAlignment(Pos.CENTER_LEFT);
            dataRow.setPadding(new Insets(8, 12, 8, 12));

            HBox asmUser = new HBox(8);
            asmUser.setAlignment(Pos.CENTER_LEFT);
            asmUser.setPrefWidth(140);

            StackPane avatar = new StackPane();
            avatar.setPrefSize(28, 28);
            avatar.setStyle("-fx-background-color: #E2E8F0; -fx-background-radius: 14px;");
            Label avIcon = new Label("P");
            avatar.getChildren().add(avIcon);

            Label name = new Label((String) row[0]);
            name.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
            name.setTextFill(Color.web(COLOR_TEXT_MAIN));
            asmUser.getChildren().addAll(avatar, name);

            Label sales = new Label((String) row[1]);
            sales.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
            sales.setPrefWidth(100);
            sales.setTextFill(Color.web(COLOR_TEXT_MAIN));

            Label target = new Label((String) row[2]);
            target.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
            target.setPrefWidth(100);
            target.setTextFill(Color.web(COLOR_TEXT_MUTED));

            HBox progBox = new HBox(8);
            progBox.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(progBox, Priority.ALWAYS);

            ProgressBar pb = new ProgressBar((double) row[3]);
            pb.setPrefWidth(100);
            pb.setPrefHeight(8);
            pb.setStyle("-fx-accent: " + COLOR_PRIMARY + "; -fx-control-inner-background: #F1F5F9;");

            Label pct = new Label((String) row[4]);
            pct.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            pct.setTextFill(Color.web(COLOR_TEXT_MAIN));

            progBox.getChildren().addAll(pb, pct);

            dataRow.getChildren().addAll(asmUser, sales, target, progBox);
            card.getChildren().add(dataRow);
        }

        // View All Button
        Button viewAllBtn = new Button("View All ASM Performance");
        viewAllBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        viewAllBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_PRIMARY + "; -fx-border-color: "
                + COLOR_PRIMARY + "; -fx-border-radius: 8px; -fx-padding: 8px 24px; -fx-cursor: hand;");
        viewAllBtn.setOnAction(e -> handleNavClick("ASM Performance"));

        HBox btnBox = new HBox(viewAllBtn);
        btnBox.setAlignment(Pos.CENTER);
        card.getChildren().add(btnBox);

        return card;
    }

    // ==========================================
    // BOTTOM ROW: RECENT ACTIVITIES
    // ==========================================
    private VBox createRecentActivitiesCard() {
        VBox card = new VBox(16);
        card.setPrefWidth(380);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER
                + "; -fx-border-radius: 14px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.03), 10, 0, 0, 2);");

        Label title = new Label("Recent Activities");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        title.setTextFill(Color.web(COLOR_TEXT_MAIN));
        card.getChildren().add(title);

        String[][] activities = {
                {"OK", "#DCFCE7", "#16A34A", "Order Completed", "Order #ORD12345 completed by Rajesh Kumar", "10:30 AM"},
                {"R", "#F3E8FF", "#7C3AED", "New Retailer Added", "New retailer \"Sharma Traders\" added in Lucknow", "09:15 AM"},
                {"T", "#F3E8FF", "#7C3AED", "Target Updated", "Target updated for Kanpur area", "Yesterday"},
                {"A", "#FEF3C7", "#D97706", "Low Stock Alert", "Product A stock is running low in Agra", "Yesterday"}
        };

        for (String[] act : activities) {
            HBox row = new HBox(12);
            row.setAlignment(Pos.CENTER_LEFT);

            StackPane iconBg = new StackPane();
            iconBg.setPrefSize(34, 34);
            iconBg.setStyle("-fx-background-color: " + act[1] + "; -fx-background-radius: 17px;");
            Label icon = new Label(act[0]);
            icon.setStyle("-fx-font-size: 14px;");
            iconBg.getChildren().add(icon);

            VBox infoBox = new VBox(2);
            Label actTitle = new Label(act[3]);
            actTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
            actTitle.setTextFill(Color.web(COLOR_TEXT_MAIN));

            Label actSub = new Label(act[4]);
            actSub.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
            actSub.setTextFill(Color.web(COLOR_TEXT_MUTED));
            infoBox.getChildren().addAll(actTitle, actSub);

            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Label timeLbl = new Label(act[5]);
            timeLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
            timeLbl.setTextFill(Color.web(COLOR_TEXT_MUTED));

            row.getChildren().addAll(iconBg, infoBox, sp, timeLbl);
            card.getChildren().add(row);
        }

        // View All Activities Button
        Button viewAllActBtn = new Button("View All Activities");
        viewAllActBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        viewAllActBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_PRIMARY + "; -fx-border-color: "
                + COLOR_PRIMARY + "; -fx-border-radius: 8px; -fx-padding: 8px 24px; -fx-cursor: hand;");
        viewAllActBtn.setOnAction(e -> handleNavClick("Alerts"));

        HBox btnBox = new HBox(viewAllActBtn);
        btnBox.setAlignment(Pos.CENTER);
        card.getChildren().add(btnBox);

        return card;
    }

    // ==========================================
    // REUSABLE SIDEBAR NAVIGATION
    // ==========================================
    public VBox createSideNavBar(String activeItem) {
        return createSideNavBar(this.primaryStage, activeItem);
    }

    public VBox createSideNavBar(Stage stage, String activeItem) {
        if (stage != null) {
            this.primaryStage = stage;
        } else if (this.primaryStage == null && Welcome.welcomeStage != null) {
            this.primaryStage = Welcome.welcomeStage;
        }
        VBox sidebar = new VBox(12);
        sidebar.setPrefWidth(260);
        sidebar.setPadding(new Insets(24, 16, 20, 16));
        sidebar.setStyle("-fx-background-color: linear-gradient(to bottom, #2E1065, #4C1D95, #3B0764);");

        // Brand Logo Container
        HBox logoContainer = new HBox(12);
        logoContainer.setAlignment(Pos.CENTER_LEFT);
        logoContainer.setPadding(new Insets(0, 8, 16, 8));

        StackPane logoBadge = new StackPane();
        logoBadge.setPrefSize(40, 40);
        logoBadge.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-background-radius: 10px;");
        Label logoLetter = new Label("RSM");
        logoLetter.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
        logoBadge.getChildren().add(logoLetter);

        VBox logoText = new VBox(1);
        Label mainBrand = new Label("RSM Dashboard");
        mainBrand.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        mainBrand.setTextFill(Color.WHITE);

        Label subBrand = new Label("Regional Sales Manager");
        subBrand.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        subBrand.setTextFill(Color.web("#C4B5FD"));

        logoText.getChildren().addAll(mainBrand, subBrand);
        logoContainer.getChildren().addAll(logoBadge, logoText);

        // Navigation Links
        VBox navLinks = new VBox(6);
        navLinks.getChildren().addAll(
                createSidebarItem("Dashboard", "🏠", "Dashboard".equals(activeItem), null),
                createSidebarItem("Targets", "🎯", "Targets".equals(activeItem), null),
                createSidebarItem("Team Performance", "👥", "Team Performance".equals(activeItem), null),
                createSidebarItem("ASM Performance", "📊", "ASM Performance".equals(activeItem), null),
                createSidebarItem("Retailers", "🏬", "Retailers".equals(activeItem), null),
                createSidebarItem("Sales Performance", "📈", "Sales Performance".equals(activeItem), null),
                createSidebarItem("Reports", "📄", "Reports".equals(activeItem), null),
                createSidebarItem("Alerts", "🔔", "Alerts".equals(activeItem), "3"),
                createSidebarItem("Settings", "⚙️", "Settings".equals(activeItem), null)
        );

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // RSM Profile Badge at Bottom
        HBox profileCard = new HBox(12);
        profileCard.setAlignment(Pos.CENTER_LEFT);
        profileCard.setPadding(new Insets(12));
        profileCard.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-background-radius: 12px;");

        StackPane avatar = new StackPane();
        avatar.setPrefSize(38, 38);
        avatar.setStyle("-fx-background-color: #6D28D9; -fx-background-radius: 19px;");
        Label avText = new Label("AS");
        avText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
        avatar.getChildren().add(avText);

        VBox userDetails = new VBox(2);
        Label userName = new Label("Amit Sharma");
        userName.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        userName.setTextFill(Color.WHITE);

        Label userRole = new Label("RSM - North Region");
        userRole.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        userRole.setTextFill(Color.web("#A78BFA"));

        userDetails.getChildren().addAll(userName, userRole);
        profileCard.getChildren().addAll(avatar, userDetails);

        sidebar.getChildren().addAll(logoContainer, navLinks, spacer, profileCard);
        return sidebar;
    }

    private Button createSidebarItem(String title, String iconEmoji, boolean isActive, String badgeCount) {
        HBox content = new HBox(12);
        content.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label(iconEmoji);
        icon.setStyle("-fx-font-size: 15px;");

        Label label = new Label(title);
        label.setFont(Font.font("Segoe UI", isActive ? FontWeight.BOLD : FontWeight.SEMI_BOLD, 13));
        label.setTextFill(isActive ? Color.WHITE : Color.web("#DDD6FE"));

        content.getChildren().addAll(icon, label);

        if (badgeCount != null) {
            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);

            Label badge = new Label(badgeCount);
            badge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
            badge.setTextFill(Color.WHITE);
            badge.setStyle("-fx-background-color: #C084FC; -fx-background-radius: 10px; -fx-padding: 1 7;");
            content.getChildren().addAll(sp, badge);
        }

        Button btn = new Button();
        btn.setGraphic(content);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(42);
        btn.setPadding(new Insets(8, 14, 8, 14));

        if (isActive) {
            btn.setStyle("-fx-background-color: #7C3AED; -fx-background-radius: 10px; -fx-cursor: hand;");
        } else {
            btn.setStyle("-fx-background-color: transparent; -fx-background-radius: 10px; -fx-cursor: hand;");
        }

        btn.setOnAction(e -> handleNavClick(title));
        return btn;
    }

    private void handleNavClick(String title) {
        Stage targetStage = (primaryStage != null) ? primaryStage : Welcome.welcomeStage;
        if (targetStage == null) return;

        if ("Dashboard".equals(title)) {
            targetStage.setScene(new RsmDashBoard().createView(targetStage));
        } else if ("Targets".equals(title)) {
            targetStage.setScene(new RsmTargets().createView(targetStage));
        } else if ("Team Performance".equals(title)) {
            targetStage.setScene(new RsmTeamPerformance().createView(targetStage));
        } else if ("ASM Performance".equals(title)) {
            targetStage.setScene(new RsmAsmPerformance().createView(targetStage));
        } else if ("Retailers".equals(title)) {
            targetStage.setScene(new RsmRetailers().createView(targetStage));
        } else if ("Sales Performance".equals(title)) {
            targetStage.setScene(new RsmSalesPerformance().createView(targetStage));
        } else if ("Reports".equals(title)) {
            targetStage.setScene(new RsmReports().createView(targetStage));
        } else if ("Alerts".equals(title)) {
            targetStage.setScene(new RsmAlerts().createView(targetStage));
        } else if ("Settings".equals(title)) {
            targetStage.setScene(new RsmSettings().createView(targetStage));
        }
    }
}
