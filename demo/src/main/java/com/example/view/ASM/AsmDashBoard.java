package com.example.view.ASM;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.net.URL;

public class AsmDashBoard {

    private Scene dashboardScene;

    public Scene createView() {

        // ==========================================
        // 1. BACKGROUND CANVAS & AMBIENT ANIMATIONS
        // ==========================================
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #0F172A;");

        // Ambient glowing orbs background for executive managerial atmosphere
        Circle bgOrb1 = new Circle(240, Color.web("#10B981", 0.18)); // Emerald glow
        bgOrb1.setTranslateX(-450);
        bgOrb1.setTranslateY(-250);

        Circle bgOrb2 = new Circle(200, Color.web("#06B6D4", 0.16)); // Cyan glow
        bgOrb2.setTranslateX(450);
        bgOrb2.setTranslateY(250);

        Circle bgOrb3 = new Circle(160, Color.web("#6366F1", 0.14)); // Indigo glow
        bgOrb3.setTranslateX(50);
        bgOrb3.setTranslateY(-300);

        // Floating animations
        createFloatingAnimation(bgOrb1, 0, 40, -30, 15, 8.5);
        createFloatingAnimation(bgOrb2, 0, -35, 35, -10, 9.5);
        createFloatingAnimation(bgOrb3, 20, -10, 0, -25, 10.5);

        StackPane backgroundPane = new StackPane(bgOrb1, bgOrb2, bgOrb3);
        backgroundPane.setPickOnBounds(false);

        // ==========================================
        // 2. MAIN DASHBOARD LAYOUT (BORDER PANE)
        // ==========================================
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(16));

        // ------------------------------------------
        // TOP NAVIGATION BAR
        // ------------------------------------------
        HBox topBar = new HBox(12);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(14, 24, 14, 24));
        topBar.setStyle(
                "-fx-background-color: rgba(15, 23, 42, 0.75);" +
                        "-fx-background-radius: 20px;" +
                        "-fx-border-color: rgba(16, 185, 129, 0.25);" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 20px;");

        StackPane logoBadge = new StackPane();
        logoBadge.setPrefSize(44, 44);
        logoBadge.setMaxSize(44, 44);
        logoBadge.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #059669, #0D9488);" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.3);" +
                        "-fx-border-radius: 12px;" +
                        "-fx-border-width: 1px;");

        ImageView logoView = getLogoView();
        if (logoView != null) {
            logoView.setFitWidth(32);
            logoView.setFitHeight(32);
            logoView.setPreserveRatio(true);
            logoBadge.getChildren().add(logoView);
        } else {
            Label logoLabel = new Label("ASM");
            logoLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
            logoLabel.setTextFill(Color.WHITE);
            logoBadge.getChildren().add(logoLabel);
        }

        Label title = new Label("MRDesk");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        title.setTextFill(Color.WHITE);

        Label subTitle = new Label("Area Sales Manager (ASM) Portal");
        subTitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        subTitle.setTextFill(Color.web("#94A3B8"));

        VBox titleBox = new VBox(2, title, subTitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label greeting = new Label("Welcome back, Marcus Vance (ASM - North Zone)");
        greeting.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));
        greeting.setTextFill(Color.web("#CBD5E1"));

        Button alertBtn = createIconButton("🔔 3 Alerts", "rgba(239, 68, 68, 0.2)", "#EF4444");
        Button profileBtn = createTextButton("Profile", "#10B981");
        profileBtn.setPadding(new Insets(8, 16, 8, 16));

        topBar.getChildren().addAll(logoBadge, titleBox, spacer, greeting, alertBtn, profileBtn);
        borderPane.setTop(topBar);

        // ------------------------------------------
        // SIDE NAVIGATION MENU
        // ------------------------------------------
        VBox sideMenu = new VBox(10);
        sideMenu.setPadding(new Insets(24, 18, 24, 18));
        sideMenu.setPrefWidth(230);
        sideMenu.setStyle(
                "-fx-background-color: rgba(15, 23, 42, 0.65);" +
                        "-fx-background-radius: 20px;" +
                        "-fx-border-color: rgba(16, 185, 129, 0.25);" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 20px;");

        BorderPane.setMargin(sideMenu, new Insets(16, 16, 0, 0));

        sideMenu.getChildren().addAll(
                createNavButton("Dashboard", "🏠", true),
                createNavButton("My Team (MRs)", "👥", false),
                createNavButton("Territories", "📍", false),
                createNavButton("Target & KRA", "🎯", false),
                createNavButton("Approvals (3)", "✅", false),
                createNavButton("Reports & Analytics", "📊", false),
                createNavButton("Settings", "⚙️", false)
        );

        Region menuSpacer = new Region();
        VBox.setVgrow(menuSpacer, Priority.ALWAYS);
        Button logoutBtn = createNavButton("Logout", "🚪", false);

        sideMenu.getChildren().addAll(menuSpacer, logoutBtn);
        borderPane.setLeft(sideMenu);

        // ------------------------------------------
        // CENTER CONTENT AREA
        // ------------------------------------------
        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(0, 0, 0, 16));

        // 1. Executive Banner
        contentBox.getChildren().add(createHeroBanner());

        // 2. Stat Cards Grid (4 Columns)
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(16);
        statsGrid.setVgap(16);

        VBox card1 = createStatCard("$104,400", "Territory Revenue ($120K)", "#10B981", "💰", "+12% MoM");
        VBox card2 = createStatCard("12 / 12", "Active Field Reps (MRs)", "#3B82F6", "👥", "100% Active");
        VBox card3 = createStatCard("348", "Doctor Visits Covered", "#8B5CF6", "🩺", "87% Target");
        VBox card4 = createStatCard("1,250 Units", "Samples Distributed", "#F59E0B", "📦", "92% Efficiency");

        GridPane.setHgrow(card1, Priority.ALWAYS);
        GridPane.setHgrow(card2, Priority.ALWAYS);
        GridPane.setHgrow(card3, Priority.ALWAYS);
        GridPane.setHgrow(card4, Priority.ALWAYS);

        statsGrid.add(card1, 0, 0);
        statsGrid.add(card2, 1, 0);
        statsGrid.add(card3, 2, 0);
        statsGrid.add(card4, 3, 0);

        contentBox.getChildren().add(statsGrid);

        // 3. Two-Column Layout (Left: Team & Territory Performance, Right: Pending Approvals & Actions)
        HBox mainColumns = new HBox(20);

        VBox leftColumn = new VBox(20);
        HBox.setHgrow(leftColumn, Priority.ALWAYS);
        leftColumn.getChildren().addAll(
                createTeamPerformanceSection(),
                createTerritoryProgressSection()
        );

        VBox rightColumn = new VBox(20);
        rightColumn.setPrefWidth(360);
        rightColumn.getChildren().addAll(
                createApprovalsSection(),
                createQuickActionCard()
        );

        mainColumns.getChildren().addAll(leftColumn, rightColumn);
        contentBox.getChildren().add(mainColumns);

        // ScrollPane Container
        ScrollPane scrollContent = new ScrollPane(contentBox);
        scrollContent.setFitToWidth(true);
        scrollContent.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        BorderPane.setMargin(scrollContent, new Insets(16, 0, 0, 0));

        borderPane.setCenter(scrollContent);

        root.getChildren().addAll(backgroundPane, borderPane);
        dashboardScene = new Scene(root, 1280, 768);
        return dashboardScene;
    }

    private ImageView getLogoView() {
        URL logoUrl = getClass().getResource("/assets/images/logo.png");
        if (logoUrl != null) {
            return new ImageView(new Image(logoUrl.toExternalForm()));
        }
        return null;
    }

    private void createFloatingAnimation(Circle circle, double fromX, double toX, double fromY, double toY, double duration) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(duration), circle);
        transition.setFromX(fromX);
        transition.setToX(toX);
        transition.setFromY(fromY);
        transition.setToY(toY);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();
    }

    private Button createTextButton(String text, String colorHex) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-font-weight: bold; " +
                        "-fx-text-fill: white; -fx-background-color: " + colorHex + "; " +
                        "-fx-background-radius: 10px; -fx-cursor: hand;"
        );
        return btn;
    }

    private Button createIconButton(String text, String bgStyle, String textHex) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 12px; -fx-font-weight: bold; " +
                        "-fx-text-fill: " + textHex + "; -fx-background-color: " + bgStyle + "; " +
                        "-fx-background-radius: 10px; -fx-padding: 6 12 6 12; -fx-cursor: hand;"
        );
        return btn;
    }

    private Button createNavButton(String text, String icon, boolean active) {
        Button btn = new Button(icon + "   " + text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(12, 16, 12, 16));

        if (active) {
            btn.setStyle(
                    "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: bold; " +
                            "-fx-text-fill: white; -fx-background-color: linear-gradient(to right, #059669, #047857); " +
                            "-fx-background-radius: 12px; -fx-cursor: hand;"
            );
        } else {
            btn.setStyle(
                    "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: 500; " +
                            "-fx-text-fill: #94A3B8; -fx-background-color: transparent; " +
                            "-fx-background-radius: 12px; -fx-cursor: hand;"
            );
            btn.setOnMouseEntered(e -> btn.setStyle(
                    "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: 600; " +
                            "-fx-text-fill: white; -fx-background-color: rgba(255, 255, 255, 0.08); " +
                            "-fx-background-radius: 12px; -fx-cursor: hand;"
            ));
            btn.setOnMouseExited(e -> btn.setStyle(
                    "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: 500; " +
                            "-fx-text-fill: #94A3B8; -fx-background-color: transparent; " +
                            "-fx-background-radius: 12px; -fx-cursor: hand;"
            ));
        }

        return btn;
    }

    private VBox createHeroBanner() {
        VBox banner = new VBox(12);
        banner.setPadding(new Insets(24));
        banner.setStyle(
                "-fx-background-color: linear-gradient(to right, #064E3B, #047857, #0D9488); " +
                        "-fx-background-radius: 20px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(16, 185, 129, 0.3), 20, 0, 0, 6);"
        );

        Label heading = new Label("Area Manager Command Center 🚀");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        heading.setTextFill(Color.WHITE);

        Label sub = new Label("North Zone efficiency is up 14% this month! 12 Medical Representatives actively covering 4 key districts today.");
        sub.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        sub.setTextFill(Color.web("#D1FAE5"));

        HBox statsPillRow = new HBox(12);
        statsPillRow.setAlignment(Pos.CENTER_LEFT);
        statsPillRow.getChildren().addAll(
                createBadge("Active MRs: 12/12", "rgba(255, 255, 255, 0.2)", "#FFFFFF"),
                createBadge("Monthly Target: $120,000", "rgba(255, 255, 255, 0.2)", "#FFFFFF"),
                createBadge("Achievement: 87%", "#F59E0B", "#0F172A")
        );

        banner.getChildren().addAll(heading, sub, statsPillRow);
        return banner;
    }

    private Label createBadge(String text, String bg, String textColor) {
        Label lbl = new Label(text);
        lbl.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 12px; -fx-font-weight: bold; " +
                        "-fx-text-fill: " + textColor + "; -fx-background-color: " + bg + "; " +
                        "-fx-padding: 4px 12px; -fx-background-radius: 12px;"
        );
        return lbl;
    }

    private VBox createStatCard(String value, String title, String accentColor, String icon, String tagText) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: rgba(15, 23, 42, 0.75); " +
                        "-fx-background-radius: 18px; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.1); " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 18px;"
        );

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label iconLbl = new Label(icon);
        iconLbl.setStyle("-fx-font-size: 22px;");

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label tagLbl = new Label(tagText);
        tagLbl.setStyle("-fx-text-fill: " + accentColor + "; -fx-font-size: 12px; -fx-font-weight: bold; -fx-background-color: rgba(255,255,255,0.06); -fx-padding: 3 8 3 8; -fx-background-radius: 8;");

        topRow.getChildren().addAll(iconLbl, sp, tagLbl);

        Label valLbl = new Label(value);
        valLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        valLbl.setTextFill(Color.WHITE);

        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        titleLbl.setTextFill(Color.web("#94A3B8"));

        card.getChildren().addAll(topRow, valLbl, titleLbl);
        return card;
    }

    private VBox createTeamPerformanceSection() {
        VBox sec = new VBox(16);
        sec.setPadding(new Insets(20));
        sec.setStyle(
                "-fx-background-color: rgba(15, 23, 42, 0.75); " +
                        "-fx-background-radius: 20px; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.1); " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 20px;"
        );

        Label secTitle = new Label("👥 Medical Representative Field Activity");
        secTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        secTitle.setTextFill(Color.WHITE);

        VBox list = new VBox(10);
        list.getChildren().addAll(
                createTeamMemberRow("Alex Mercer", "Central District", "8 Visits", 0.94, "Active", "#10B981"),
                createTeamMemberRow("Sarah Connor", "North Sector", "7 Visits", 0.88, "Active", "#10B981"),
                createTeamMemberRow("David Miller", "East Sector", "6 Visits", 0.82, "In Field", "#3B82F6"),
                createTeamMemberRow("Priya Sharma", "West Zone", "5 Visits", 0.79, "Active", "#10B981"),
                createTeamMemberRow("Robert Chen", "South Hub", "4 Visits", 0.75, "On Leave", "#F59E0B")
        );

        sec.getChildren().addAll(secTitle, list);
        return sec;
    }

    private HBox createTeamMemberRow(String name, String territory, String visits, double progress, String status, String statusColor) {
        HBox row = new HBox(16);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 16, 12, 16));
        row.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.04); " +
                        "-fx-background-radius: 14px; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.06); " +
                        "-fx-border-radius: 14px; " +
                        "-fx-border-width: 1px;"
        );

        Label avatar = new Label(name.substring(0, 1));
        avatar.setStyle("-fx-background-color: linear-gradient(to bottom right, #059669, #0D9488); -fx-text-fill: white; -fx-font-weight: bold; -fx-alignment: center; -fx-min-width: 36; -fx-min-height: 36; -fx-background-radius: 18;");

        Label nameLbl = new Label(name);
        nameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        nameLbl.setTextFill(Color.WHITE);

        Label terrLbl = new Label(territory);
        terrLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        terrLbl.setTextFill(Color.web("#94A3B8"));

        VBox nameBox = new VBox(2, nameLbl, terrLbl);
        nameBox.setPrefWidth(140);

        Label visitsLbl = new Label(visits);
        visitsLbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        visitsLbl.setTextFill(Color.web("#CBD5E1"));
        visitsLbl.setPrefWidth(80);

        VBox progressBox = new VBox(4);
        ProgressBar pBar = new ProgressBar(progress);
        pBar.setPrefWidth(120);
        pBar.setStyle("-fx-accent: #10B981;");
        Label pctLbl = new Label((int)(progress * 100) + "% Target");
        pctLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        pctLbl.setTextFill(Color.web("#94A3B8"));
        progressBox.getChildren().addAll(pBar, pctLbl);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label statusBadge = new Label(status);
        statusBadge.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 11px; -fx-font-weight: bold; " +
                        "-fx-text-fill: " + statusColor + "; -fx-background-color: rgba(255, 255, 255, 0.08); " +
                        "-fx-padding: 4px 10px; -fx-background-radius: 10px;"
        );

        row.getChildren().addAll(avatar, nameBox, visitsLbl, progressBox, sp, statusBadge);
        return row;
    }

    private VBox createTerritoryProgressSection() {
        VBox sec = new VBox(16);
        sec.setPadding(new Insets(20));
        sec.setStyle(
                "-fx-background-color: rgba(15, 23, 42, 0.75); " +
                        "-fx-background-radius: 20px; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.1); " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 20px;"
        );

        Label secTitle = new Label("📍 District Target & Sales Breakdown");
        secTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        secTitle.setTextFill(Color.WHITE);

        VBox list = new VBox(12);
        list.getChildren().addAll(
                createTerritoryRow("Central District", "$34,000 / $38,000", 0.895, "#10B981"),
                createTerritoryRow("North Sector", "$28,500 / $32,000", 0.890, "#3B82F6"),
                createTerritoryRow("East Sector", "$22,400 / $26,000", 0.861, "#8B5CF6"),
                createTerritoryRow("West Zone", "$19,500 / $24,000", 0.812, "#F59E0B")
        );

        sec.getChildren().addAll(secTitle, list);
        return sec;
    }

    private VBox createTerritoryRow(String district, String sales, double ratio, String barColor) {
        VBox box = new VBox(6);

        HBox top = new HBox();
        Label distLbl = new Label(district);
        distLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        distLbl.setTextFill(Color.WHITE);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label salesLbl = new Label(sales + " (" + (int)(ratio * 100) + "%)");
        salesLbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        salesLbl.setTextFill(Color.web("#94A3B8"));

        top.getChildren().addAll(distLbl, sp, salesLbl);

        ProgressBar pBar = new ProgressBar(ratio);
        pBar.setMaxWidth(Double.MAX_VALUE);
        pBar.setStyle("-fx-accent: " + barColor + ";");

        box.getChildren().addAll(top, pBar);
        return box;
    }

    private VBox createApprovalsSection() {
        VBox sec = new VBox(14);
        sec.setPadding(new Insets(20));
        sec.setStyle(
                "-fx-background-color: rgba(15, 23, 42, 0.75); " +
                        "-fx-background-radius: 20px; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.1); " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 20px;"
        );

        Label secTitle = new Label("⚡ Pending Approvals (3)");
        secTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        secTitle.setTextFill(Color.WHITE);

        VBox items = new VBox(10);
        items.getChildren().addAll(
                createApprovalItem("Sample Request", "Alex Mercer • 50x Cardiac Samples", "Requested 2h ago"),
                createApprovalItem("Expense Claim", "Sarah Connor • $140 Travel Allowance", "Requested 5h ago"),
                createApprovalItem("Tour Plan", "David Miller • Monthly Route Plan", "Requested 1d ago")
        );

        sec.getChildren().addAll(secTitle, items);
        return sec;
    }

    private VBox createApprovalItem(String type, String desc, String time) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(12));
        card.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.04); " +
                        "-fx-background-radius: 12px; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.06); " +
                        "-fx-border-radius: 12px; " +
                        "-fx-border-width: 1px;"
        );

        HBox top = new HBox();
        Label typeLbl = new Label(type);
        typeLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        typeLbl.setTextFill(Color.web("#F59E0B"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label timeLbl = new Label(time);
        timeLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        timeLbl.setTextFill(Color.web("#64748B"));

        top.getChildren().addAll(typeLbl, sp, timeLbl);

        Label descLbl = new Label(desc);
        descLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        descLbl.setTextFill(Color.web("#CBD5E1"));

        HBox actionBtns = new HBox(8);
        actionBtns.setAlignment(Pos.CENTER_RIGHT);

        Button approveBtn = createIconButton("Approve", "#10B981", "#FFFFFF");
        Button reviewBtn = createIconButton("Review", "rgba(255,255,255,0.08)", "#CBD5E1");

        actionBtns.getChildren().addAll(reviewBtn, approveBtn);

        card.getChildren().addAll(top, descLbl, actionBtns);
        return card;
    }

    private VBox createQuickActionCard() {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: rgba(15, 23, 42, 0.75); " +
                        "-fx-background-radius: 20px; " +
                        "-fx-border-color: rgba(16, 185, 129, 0.2); " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 20px;"
        );

        Label title = new Label("🚀 Manager Quick Actions");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        title.setTextFill(Color.WHITE);

        Button action1 = createActionRow("🎯 Assign Monthly Target", "Set KRA for MRs");
        Button action2 = createActionRow("📢 Broadcast Team Announcement", "Send notice to all MRs");
        Button action3 = createActionRow("📊 Generate Regional Report", "Export PDF/Excel summary");

        card.getChildren().addAll(title, action1, action2, action3);
        return card;
    }

    private Button createActionRow(String title, String subtitle) {
        VBox b = new VBox(2);
        Label t = new Label(title);
        t.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        t.setTextFill(Color.WHITE);

        Label s = new Label(subtitle);
        s.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        s.setTextFill(Color.web("#94A3B8"));

        b.getChildren().addAll(t, s);

        Button btn = new Button();
        btn.setGraphic(b);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(10, 14, 10, 14));
        btn.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.05); " +
                        "-fx-background-radius: 12px; -fx-cursor: hand;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: rgba(16, 185, 129, 0.15); " +
                        "-fx-background-radius: 12px; -fx-cursor: hand;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.05); " +
                        "-fx-background-radius: 12px; -fx-cursor: hand;"
        ));

        return btn;
    }
}
