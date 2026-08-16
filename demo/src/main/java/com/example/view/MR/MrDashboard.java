package com.example.view.MR;

import com.example.view.Welcome;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
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
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MrDashboard {

    private Scene dashboardScene;
    private BorderPane root;
    private ScrollPane scrollPane;
    private String activeSection = "Dashboard";
    private final List<Button> navButtonsList = new ArrayList<>();

    public Scene createView() {

        // ==========================================
        // 1. MAIN CONTAINER & BACKGROUND (LIGHT ENTERPRISE THEME)
        // ==========================================
        root = new BorderPane();
        root.setStyle("-fx-background-color: #F8FAFC;");

        // ------------------------------------------
        // LEFT SIDEBAR NAVIGATION (DEEP ROYAL BLUE)
        // ------------------------------------------
        VBox sidebar = new VBox(12);
        sidebar.setPrefWidth(260);
        sidebar.setPadding(new Insets(24, 16, 20, 16));
        sidebar.setStyle("-fx-background-color: linear-gradient(to bottom, #1E3A8A, #1D4ED8);");

        // Logo & App Title Header
        HBox logoContainer = new HBox(12);
        logoContainer.setAlignment(Pos.CENTER_LEFT);
        logoContainer.setPadding(new Insets(0, 8, 16, 8));

        StackPane logoBadge = new StackPane();
        logoBadge.setPrefSize(42, 42);
        logoBadge.setMaxSize(42, 42);
        logoBadge.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.2);" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.4);" +
                        "-fx-border-radius: 12px;" +
                        "-fx-border-width: 1px;");

        ImageView logoView = getLogoView();
        if (logoView != null) {
            logoView.setFitWidth(28);
            logoView.setFitHeight(28);
            logoView.setPreserveRatio(true);
            logoBadge.getChildren().add(logoView);
        } else {
            Label logoLabel = new Label("✚");
            logoLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
            logoLabel.setTextFill(Color.WHITE);
            logoBadge.getChildren().add(logoLabel);
        }

        Label appTitle = new Label("MRDesk");
        appTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        appTitle.setTextFill(Color.WHITE);

        Label appSub = new Label("Medical Representative Management System");
        appSub.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 10));
        appSub.setTextFill(Color.web("#93C5FD"));

        VBox titleBox = new VBox(2, appTitle, appSub);
        logoContainer.getChildren().addAll(logoBadge, titleBox);

        // Sidebar Navigation Links (7 items, strictly <= 8)
        navButtonsList.clear();
        VBox navLinks = new VBox(6);
        navLinks.getChildren().addAll(
                createSidebarItem("Dashboard", "🏠", true),
                createSidebarItem("Daily Schedule", "📅", false),
                createSidebarItem("Doctors", "🩺", false),
                createSidebarItem("Hospitals / Clinics", "🏥", false),
                createSidebarItem("Product Report", "📦", false),
                createSidebarItem("Reports", "📊", false),
                createSidebarItem("Settings", "⚙️", false));

        Region sidebarSpacer = new Region();
        VBox.setVgrow(sidebarSpacer, Priority.ALWAYS);

        // Bottom User Profile Card with Logout Handler
        HBox profileCard = new HBox(12);
        profileCard.setAlignment(Pos.CENTER_LEFT);
        profileCard.setPadding(new Insets(12));
        profileCard.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.12);" +
                        "-fx-background-radius: 14px;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.15);" +
                        "-fx-border-radius: 14px;" +
                        "-fx-border-width: 1px;" +
                        "-fx-cursor: hand;");

        profileCard.setOnMouseClicked(e -> {
            try {
                Welcome welcome = new Welcome();
                welcome.start(Welcome.welcomeStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        StackPane avatarPane = new StackPane();
        avatarPane.setPrefSize(40, 40);
        avatarPane.setStyle("-fx-background-color: #3B82F6; -fx-background-radius: 20px;");
        Label avatarText = new Label("PD");
        avatarText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        avatarText.setTextFill(Color.WHITE);
        avatarPane.getChildren().add(avatarText);

        Label userName = new Label("Pratik Deshmukh");
        userName.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        userName.setTextFill(Color.WHITE);

        Label userRole = new Label("Medical Representative");
        userRole.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        userRole.setTextFill(Color.web("#93C5FD"));

        Label onlineDot = new Label("● Online (Logout)");
        onlineDot.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 10));
        onlineDot.setTextFill(Color.web("#34D399"));

        VBox userDetails = new VBox(2, userName, userRole, onlineDot);
        profileCard.getChildren().addAll(avatarPane, userDetails);

        sidebar.getChildren().addAll(logoContainer, navLinks, sidebarSpacer, profileCard);
        root.setLeft(sidebar);

        // Initial Center View (Dashboard Overview)
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #F8FAFC; -fx-background: #F8FAFC;");

        root.setCenter(scrollPane);

        // Load Default View
        navigateToSection("Dashboard");

        dashboardScene = new Scene(root, 1280, 800);
        return dashboardScene;
    }

    // ==========================================
    // DYNAMIC NAVIGATION SWITCHER & ANIMATION
    // ==========================================
    private void navigateToSection(String sectionName) {
        this.activeSection = sectionName;

        // Update button styles
        for (Button btn : navButtonsList) {
            if (btn.getText().contains(sectionName)) {
                btn.setStyle(
                        "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: bold; " +
                                "-fx-text-fill: white; -fx-background-color: linear-gradient(to right, #3B82F6, #2563EB); "
                                +
                                "-fx-background-radius: 12px; -fx-cursor: hand;");
            } else {
                btn.setStyle(
                        "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: 500; " +
                                "-fx-text-fill: #93C5FD; -fx-background-color: transparent; " +
                                "-fx-background-radius: 12px; -fx-cursor: hand;");
            }
        }

        // Build target page node
        VBox pageContent;
        switch (sectionName) {
            case "Daily Schedule":
                pageContent = createDailySchedulePage();
                break;
            case "Doctors":
                pageContent = createDoctorsPage();
                break;
            case "Hospitals / Clinics":
                pageContent = createHospitalsPage();
                break;
            case "Product Report":
                pageContent = createProductReportPage();
                break;
            case "Reports":
                pageContent = createReportsPage();
                break;
            case "Settings":
                pageContent = createSettingsPage();
                break;
            case "Dashboard":
            default:
                pageContent = createDashboardOverview();
                break;
        }

        scrollPane.setContent(pageContent);

        // Apply smooth entrance fade animation
        FadeTransition fadeIn = new FadeTransition(Duration.millis(250), pageContent);
        fadeIn.setFromValue(0.2);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    private ImageView getLogoView() {
        URL logoUrl = getClass().getResource("/assets/images/logo.png");
        if (logoUrl != null) {
            return new ImageView(new Image(logoUrl.toExternalForm()));
        }
        return null;
    }

    private Button createSidebarItem(String text, String icon, boolean active) {
        Button btn = new Button(icon + "   " + text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(12, 16, 12, 16));

        if (active) {
            btn.setStyle(
                    "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: bold; " +
                            "-fx-text-fill: white; -fx-background-color: linear-gradient(to right, #3B82F6, #2563EB); "
                            +
                            "-fx-background-radius: 12px; -fx-cursor: hand;");
        } else {
            btn.setStyle(
                    "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: 500; " +
                            "-fx-text-fill: #93C5FD; -fx-background-color: transparent; " +
                            "-fx-background-radius: 12px; -fx-cursor: hand;");
        }

        btn.setOnMouseEntered(e -> {
            if (!btn.getText().contains(activeSection)) {
                btn.setStyle(
                        "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: 600; " +
                                "-fx-text-fill: white; -fx-background-color: rgba(255, 255, 255, 0.12); " +
                                "-fx-background-radius: 12px; -fx-cursor: hand;");
            }
        });

        btn.setOnMouseExited(e -> {
            if (!btn.getText().contains(activeSection)) {
                btn.setStyle(
                        "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: 500; " +
                                "-fx-text-fill: #93C5FD; -fx-background-color: transparent; " +
                                "-fx-background-radius: 12px; -fx-cursor: hand;");
            }
        });

        btn.setOnAction(e -> navigateToSection(text));
        navButtonsList.add(btn);
        return btn;
    }

    // ==========================================
    // PAGE 1: 🏠 DASHBOARD OVERVIEW
    // ==========================================
    private VBox createDashboardOverview() {
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(24, 28, 24, 28));

        // Top Header Row
        HBox topHeader = new HBox();
        topHeader.setAlignment(Pos.CENTER_LEFT);

        Label welcomeTitle = new Label("Good Morning, Pratik! 👋");
        welcomeTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        welcomeTitle.setTextFill(Color.web("#0F172A"));

        Label welcomeSub = new Label("Here's what's happening with your work today.");
        welcomeSub.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        welcomeSub.setTextFill(Color.web("#64748B"));

        VBox greetingBox = new VBox(4, welcomeTitle, welcomeSub);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Label dateBadge = new Label("📅 Friday, 9 May 2025");
        dateBadge.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-font-weight: bold; " +
                        "-fx-text-fill: #475569; -fx-background-color: #F1F5F9; " +
                        "-fx-padding: 8px 16px; -fx-background-radius: 12px; " +
                        "-fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-border-width: 1px;");

        topHeader.getChildren().addAll(greetingBox, headerSpacer, dateBadge);
        mainContent.getChildren().add(topHeader);

        // Top 5 Stat Cards Row
        GridPane statGrid = new GridPane();
        statGrid.setHgap(16);
        statGrid.setVgap(16);

        VBox card1 = createTopStatCard("Today's Visits", "8", "Total Scheduled", "#3B82F6", "📅", "#EFF6FF");
        VBox card2 = createTopStatCard("Completed Visits", "5", "Today Completed", "#10B981", "✅", "#ECFDF5");
        VBox card3 = createTopStatCard("Pending Visits", "3", "Yet to Complete", "#F59E0B", "⏳", "#FEF3C7");
        VBox card4 = createTopStatCard("Tasks Due Today", "2", "Pending Tasks", "#F43F5E", "📋", "#FFE4E6");
        VBox card5 = createTopStatCard("Products Detailed", "14", "Showcased", "#8B5CF6", "📦", "#F3E8FF");

        GridPane.setHgrow(card1, Priority.ALWAYS);
        GridPane.setHgrow(card2, Priority.ALWAYS);
        GridPane.setHgrow(card3, Priority.ALWAYS);
        GridPane.setHgrow(card4, Priority.ALWAYS);
        GridPane.setHgrow(card5, Priority.ALWAYS);

        statGrid.add(card1, 0, 0);
        statGrid.add(card2, 1, 0);
        statGrid.add(card3, 2, 0);
        statGrid.add(card4, 3, 0);
        statGrid.add(card5, 4, 0);

        mainContent.getChildren().add(statGrid);

        // Two Column Main Content
        HBox contentColumns = new HBox(20);

        VBox leftCol = new VBox(20);
        HBox.setHgrow(leftCol, Priority.ALWAYS);
        leftCol.getChildren().addAll(
                createDailyScheduleCard(),
                createTaskSummaryCard());

        VBox rightCol = new VBox(20);
        rightCol.setPrefWidth(360);
        rightCol.getChildren().addAll(
                createRecentActivitiesCard(),
                createProductReportCard());

        contentColumns.getChildren().addAll(leftCol, rightCol);
        mainContent.getChildren().add(contentColumns);

        // Footer Text
        Label footerText = new Label("© 2025 MRDesk. All rights reserved. • v1.0.0");
        footerText.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        footerText.setTextFill(Color.web("#94A3B8"));
        footerText.setAlignment(Pos.CENTER);
        footerText.setMaxWidth(Double.MAX_VALUE);

        mainContent.getChildren().add(footerText);
        return mainContent;
    }

    // ==========================================
    // PAGE 2: 📅 DAILY SCHEDULE PAGE
    // ==========================================
    private VBox createDailySchedulePage() {
        VBox page = new VBox(20);
        page.setPadding(new Insets(24, 28, 24, 28));

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("📅 Daily Schedule & Doctor Visits");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#0F172A"));

        Label sub = new Label("View, track, and manage your scheduled doctor calls for today");
        sub.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        sub.setTextFill(Color.web("#64748B"));

        VBox headBox = new VBox(4, title, sub);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button addVisitBtn = createStyledButton("+ Schedule New Visit", "#2563EB", "white");
        topRow.getChildren().addAll(headBox, sp, addVisitBtn);

        // Filter & Search Bar
        HBox filterBar = new HBox(12);
        filterBar.setAlignment(Pos.CENTER_LEFT);
        filterBar.setPadding(new Insets(16));
        filterBar.setStyle(
                "-fx-background-color: white; -fx-background-radius: 14px; -fx-border-color: #E2E8F0; -fx-border-width: 1px;");

        TextField searchInput = new TextField();
        searchInput.setPromptText("🔍 Search doctor or hospital name...");
        searchInput.setPrefWidth(300);
        searchInput.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-padding: 8px 12px; -fx-background-radius: 8px; -fx-border-color: #CBD5E1; -fx-border-radius: 8px;");

        Button filterAll = createPillButton("All (8)", true);
        Button filterCompleted = createPillButton("Completed (5)", false);
        Button filterPending = createPillButton("Pending (3)", false);

        filterBar.getChildren().addAll(searchInput, filterAll, filterCompleted, filterPending);

        // Schedule Visit Cards List
        VBox scheduleList = new VBox(14);
        scheduleList.getChildren().addAll(
                createDetailedScheduleItem("09:00 AM", "Dr. Amit Sharma", "Cardiologist",
                        "City Care Hospital • Room 204", "Product Discussion", "Completed", "#10B981", "#DCFCE7"),
                createDetailedScheduleItem("11:30 AM", "Dr. Rahul Patil", "Neurologist", "ABC Clinic • Suite 12",
                        "Product Overview & Samples", "Pending", "#F59E0B", "#FEF3C7"),
                createDetailedScheduleItem("02:00 PM", "Dr. Sneha Joshi", "Pediatrician", "Health Plus Clinic • Ward B",
                        "New Product Intro", "Pending", "#F59E0B", "#FEF3C7"),
                createDetailedScheduleItem("04:30 PM", "Dr. Vikram Rao", "General Physician",
                        "Sunshine Hospital • OPD 5", "Product Report Follow-up", "Scheduled", "#3B82F6", "#DBEAFE"));

        page.getChildren().addAll(topRow, filterBar, scheduleList);
        return page;
    }

    // ==========================================
    // PAGE 3: 🩺 DOCTORS DIRECTORY PAGE
    // ==========================================
    private VBox createDoctorsPage() {
        VBox page = new VBox(20);
        page.setPadding(new Insets(24, 28, 24, 28));

        Label title = new Label("🩺 Doctors Directory");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#0F172A"));

        Label sub = new Label("Directory of affiliated medical specialists, clinic contacts, and visit histories");
        sub.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        sub.setTextFill(Color.web("#64748B"));

        VBox headBox = new VBox(4, title, sub);

        // Filter Tags Row
        HBox tagsRow = new HBox(10);
        tagsRow.getChildren().addAll(
                createPillButton("All Specialties", true),
                createPillButton("Cardiology", false),
                createPillButton("Neurology", false),
                createPillButton("Pediatrics", false),
                createPillButton("General Medicine", false));

        // Doctors Grid (2 Columns)
        GridPane docGrid = new GridPane();
        docGrid.setHgap(16);
        docGrid.setVgap(16);

        VBox doc1 = createDoctorCard("Dr. Amit Sharma", "Cardiologist", "City Care Hospital", "12 Visits Completed",
                "+1 555-0192", "#3B82F6");
        VBox doc2 = createDoctorCard("Dr. Rahul Patil", "Neurologist", "ABC Clinic", "8 Visits Completed",
                "+1 555-0144", "#10B981");
        VBox doc3 = createDoctorCard("Dr. Sneha Joshi", "Pediatrician", "Health Plus Clinic", "15 Visits Completed",
                "+1 555-0178", "#8B5CF6");
        VBox doc4 = createDoctorCard("Dr. Vikram Rao", "General Physician", "Sunshine Hospital", "6 Visits Completed",
                "+1 555-0123", "#F59E0B");

        GridPane.setHgrow(doc1, Priority.ALWAYS);
        GridPane.setHgrow(doc2, Priority.ALWAYS);
        GridPane.setHgrow(doc3, Priority.ALWAYS);
        GridPane.setHgrow(doc4, Priority.ALWAYS);

        docGrid.add(doc1, 0, 0);
        docGrid.add(doc2, 1, 0);
        docGrid.add(doc3, 0, 1);
        docGrid.add(doc4, 1, 1);

        page.getChildren().addAll(headBox, tagsRow, docGrid);
        return page;
    }

    // ==========================================
    // PAGE 4: 🏥 HOSPITALS / CLINICS PAGE
    // ==========================================
    private VBox createHospitalsPage() {
        VBox page = new VBox(20);
        page.setPadding(new Insets(24, 28, 24, 28));

        Label title = new Label("🏥 Hospitals & Medical Centers");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#0F172A"));

        Label sub = new Label("Healthcare institutions, clinic locations, and affiliated medical departments");
        sub.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        sub.setTextFill(Color.web("#64748B"));

        VBox headBox = new VBox(4, title, sub);

        VBox hospList = new VBox(14);
        hospList.getChildren().addAll(
                createHospitalCard("City Care Hospital", "Super Specialty Hospital", "104 Health Ave, Central District",
                        "45 Affiliated Doctors", "18 Visits This Month", "#3B82F6"),
                createHospitalCard("ABC Clinic", "Polyclinic & Diagnostic Center", "45 Park Street, North Sector",
                        "12 Affiliated Doctors", "14 Visits This Month", "#10B981"),
                createHospitalCard("Health Plus Clinic", "Pediatric & Family Care", "88 Sunrise Blvd, West Zone",
                        "18 Affiliated Doctors", "22 Visits This Month", "#8B5CF6"),
                createHospitalCard("Sunshine Hospital", "General Healthcare Center",
                        "12 Medical Center Way, East Sector", "60 Affiliated Doctors", "30 Visits This Month",
                        "#F59E0B"));

        page.getChildren().addAll(headBox, hospList);
        return page;
    }

    // ==========================================
    // PAGE 5: 📦 PRODUCT REPORT PAGE
    // ==========================================
    private VBox createProductReportPage() {
        VBox page = new VBox(20);
        page.setPadding(new Insets(24, 28, 24, 28));

        Label title = new Label("📦 Product Report & Detailing Catalog");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#0F172A"));

        Label sub = new Label("Track pharmaceutical products showcased to doctors and sample distribution logs");
        sub.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        sub.setTextFill(Color.web("#64748B"));

        VBox headBox = new VBox(4, title, sub);

        GridPane pGrid = new GridPane();
        pGrid.setHgap(16);
        pGrid.setVgap(16);

        VBox pCard1 = createProductDetailTile("CardioSafe 50mg", "Cardiology", 12, 450, 0.80, "#3B82F6");
        VBox pCard2 = createProductDetailTile("NeuroPlus 25mg", "Neurology", 8, 300, 0.60, "#06B6D4");
        VBox pCard3 = createProductDetailTile("GlucoCare 500mg", "Endocrinology", 6, 250, 0.45, "#6366F1");
        VBox pCard4 = createProductDetailTile("RespiraCalm Inhaler", "Pulmonology", 4, 200, 0.30, "#0EA5E9");

        GridPane.setHgrow(pCard1, Priority.ALWAYS);
        GridPane.setHgrow(pCard2, Priority.ALWAYS);
        GridPane.setHgrow(pCard3, Priority.ALWAYS);
        GridPane.setHgrow(pCard4, Priority.ALWAYS);

        pGrid.add(pCard1, 0, 0);
        pGrid.add(pCard2, 1, 0);
        pGrid.add(pCard3, 0, 1);
        pGrid.add(pCard4, 1, 1);

        page.getChildren().addAll(headBox, pGrid);
        return page;
    }

    // ==========================================
    // PAGE 6: 📊 REPORTS & ANALYTICS PAGE
    // ==========================================
    private VBox createReportsPage() {
        VBox page = new VBox(20);
        page.setPadding(new Insets(24, 28, 24, 28));

        Label title = new Label("📊 Performance Reports & Analytics");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#0F172A"));

        Label sub = new Label("Comprehensive representative analytics, visit completion metrics, and export tools");
        sub.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        sub.setTextFill(Color.web("#64748B"));

        VBox headBox = new VBox(4, title, sub);

        // Stats Summary Cards Row
        GridPane rGrid = new GridPane();
        rGrid.setHgap(16);

        VBox r1 = createTopStatCard("Monthly Visits", "128 / 140", "91.4% Target Achieved", "#10B981", "📈", "#ECFDF5");
        VBox r2 = createTopStatCard("Doctor Coverage", "92%", "46/50 Doctors Visited", "#3B82F6", "🎯", "#EFF6FF");
        VBox r3 = createTopStatCard("Avg Calls / Day", "6.4", "+0.8 vs Last Month", "#8B5CF6", "⚡", "#F3E8FF");

        GridPane.setHgrow(r1, Priority.ALWAYS);
        GridPane.setHgrow(r2, Priority.ALWAYS);
        GridPane.setHgrow(r3, Priority.ALWAYS);

        rGrid.add(r1, 0, 0);
        rGrid.add(r2, 1, 0);
        rGrid.add(r3, 2, 0);

        // Action Center Card
        VBox exportCard = new VBox(16);
        exportCard.setPadding(new Insets(20));
        exportCard.setStyle(
                "-fx-background-color: white; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-width: 1px;");

        Label expTitle = new Label("📥 Download & Export Reports");
        expTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        expTitle.setTextFill(Color.web("#0F172A"));

        HBox btnBox = new HBox(12);
        Button pdfBtn = createStyledButton("📄 Export PDF Monthly Summary", "#2563EB", "white");
        Button excelBtn = createStyledButton("📊 Export Excel Doctor Logs", "#10B981", "white");

        btnBox.getChildren().addAll(pdfBtn, excelBtn);
        exportCard.getChildren().addAll(expTitle, btnBox);

        page.getChildren().addAll(headBox, rGrid, exportCard);
        return page;
    }

    // ==========================================
    // PAGE 7: ⚙️ SETTINGS PAGE
    // ==========================================
    private VBox createSettingsPage() {
        VBox page = new VBox(20);
        page.setPadding(new Insets(24, 28, 24, 28));

        Label title = new Label("⚙️ Settings & Account Preferences");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#0F172A"));

        Label sub = new Label("Manage personal profile details, notification preferences, and application settings");
        sub.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        sub.setTextFill(Color.web("#64748B"));

        VBox headBox = new VBox(4, title, sub);

        VBox profileForm = new VBox(16);
        profileForm.setPadding(new Insets(24));
        profileForm.setStyle(
                "-fx-background-color: white; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-width: 1px;");

        Label formTitle = new Label("👤 Representative Profile Information");
        formTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        formTitle.setTextFill(Color.web("#0F172A"));

        GridPane formGrid = new GridPane();
        formGrid.setHgap(16);
        formGrid.setVgap(14);

        TextField nameF = new TextField("Pratik Deshmukh");
        TextField emailF = new TextField("pratik.deshmukh@mrdesk.com");
        TextField phoneF = new TextField("+1 555-0199");
        TextField regionF = new TextField("North Zone District 4");

        styleFormInput(nameF);
        styleFormInput(emailF);
        styleFormInput(phoneF);
        styleFormInput(regionF);

        formGrid.add(new Label("Full Name:"), 0, 0);
        formGrid.add(nameF, 1, 0);
        formGrid.add(new Label("Email Address:"), 0, 1);
        formGrid.add(emailF, 1, 1);
        formGrid.add(new Label("Phone Number:"), 0, 2);
        formGrid.add(phoneF, 1, 2);
        formGrid.add(new Label("Assigned Territory:"), 0, 3);
        formGrid.add(regionF, 1, 3);

        CheckBox cb1 = new CheckBox("Enable Daily Visit Reminders");
        cb1.setSelected(true);
        CheckBox cb2 = new CheckBox("Enable Automated Product Report Sync");
        cb2.setSelected(true);

        Button saveBtn = createStyledButton("Save Changes", "#2563EB", "white");

        profileForm.getChildren().addAll(formTitle, formGrid, cb1, cb2, saveBtn);

        page.getChildren().addAll(headBox, profileForm);
        return page;
    }

    // ==========================================
    // UI BUILDER & ANIMATION HELPERS
    // ==========================================
    private VBox createTopStatCard(String title, String val, String sub, String colorHex, String icon, String bgHex) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(18, 16, 18, 16));
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-border-color: #E2E8F0; -fx-border-width: 1px; -fx-border-radius: 16px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 10, 0, 0, 4);");

        addCardHoverEffect(card);

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(38, 38);
        iconBox.setStyle("-fx-background-color: " + bgHex + "; -fx-background-radius: 10px;");
        Label iconLbl = new Label(icon);
        iconLbl.setStyle("-fx-font-size: 18px;");
        iconBox.getChildren().add(iconLbl);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        topRow.getChildren().addAll(iconBox, sp);

        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        titleLbl.setTextFill(Color.web("#64748B"));

        Label valLbl = new Label(val);
        valLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        valLbl.setTextFill(Color.web("#0F172A"));

        Label subLbl = new Label(sub);
        subLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        subLbl.setTextFill(Color.web("#94A3B8"));

        card.getChildren().addAll(topRow, titleLbl, valLbl, subLbl);
        return card;
    }

    private VBox createDailyScheduleCard() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-border-color: #E2E8F0; -fx-border-width: 1px; -fx-border-radius: 16px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 10, 0, 0, 4);");

        addCardHoverEffect(card);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("📅 Daily Schedule");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 17));
        title.setTextFill(Color.web("#0F172A"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button viewAllBtn = new Button("View All ➔");
        viewAllBtn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #2563EB; -fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-cursor: hand;");
        viewAllBtn.setOnAction(e -> navigateToSection("Daily Schedule"));

        header.getChildren().addAll(title, sp, viewAllBtn);

        HBox tableHeader = new HBox(12);
        tableHeader.setPadding(new Insets(8, 12, 8, 12));
        tableHeader.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 8px;");

        Label colTime = new Label("Time");
        colTime.setPrefWidth(90);
        colTime.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        colTime.setTextFill(Color.web("#64748B"));

        Label colDoc = new Label("Doctor");
        colDoc.setPrefWidth(140);
        colDoc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        colDoc.setTextFill(Color.web("#64748B"));

        Label colHosp = new Label("Hospital / Clinic");
        colHosp.setPrefWidth(160);
        colHosp.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        colHosp.setTextFill(Color.web("#64748B"));

        Label colPurp = new Label("Purpose");
        HBox.setHgrow(colPurp, Priority.ALWAYS);
        colPurp.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        colPurp.setTextFill(Color.web("#64748B"));

        Label colStat = new Label("Status");
        colStat.setPrefWidth(100);
        colStat.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        colStat.setTextFill(Color.web("#64748B"));

        tableHeader.getChildren().addAll(colTime, colDoc, colHosp, colPurp, colStat);

        VBox rows = new VBox(8);
        rows.getChildren().addAll(
                createScheduleRow("09:00 AM", "Dr. Amit Sharma", "City Care Hospital", "Product Discussion",
                        "Completed", "#10B981", "#DCFCE7"),
                createScheduleRow("11:30 AM", "Dr. Rahul Patil", "ABC Clinic", "Product Overview", "Pending", "#F59E0B",
                        "#FEF3C7"),
                createScheduleRow("02:00 PM", "Dr. Sneha Joshi", "Health Plus Clinic", "New Product Intro", "Pending",
                        "#F59E0B", "#FEF3C7"),
                createScheduleRow("04:30 PM", "Dr. Vikram Rao", "Sunshine Hospital", "Product Report Follow-up",
                        "Scheduled", "#3B82F6", "#DBEAFE"));

        card.getChildren().addAll(header, tableHeader, rows);
        return card;
    }

    private HBox createScheduleRow(String time, String doc, String hosp, String purp, String status, String statusColor,
            String bgHex) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 12, 10, 12));
        row.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10px; " +
                        "-fx-border-color: #F1F5F9; -fx-border-width: 1px; -fx-border-radius: 10px; -fx-cursor: hand;");

        row.setOnMouseEntered(e -> row.setStyle(
                "-fx-background-color: #F8FAFC; -fx-background-radius: 10px; " +
                        "-fx-border-color: #CBD5E1; -fx-border-width: 1px; -fx-border-radius: 10px; -fx-cursor: hand;"));

        row.setOnMouseExited(e -> row.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10px; " +
                        "-fx-border-color: #F1F5F9; -fx-border-width: 1px; -fx-border-radius: 10px; -fx-cursor: hand;"));

        Label t = new Label(time);
        t.setPrefWidth(90);
        t.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        t.setTextFill(Color.web("#334155"));

        Label d = new Label(doc);
        d.setPrefWidth(140);
        d.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        d.setTextFill(Color.web("#0F172A"));

        Label h = new Label(hosp);
        h.setPrefWidth(160);
        h.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        h.setTextFill(Color.web("#64748B"));

        Label p = new Label(purp);
        HBox.setHgrow(p, Priority.ALWAYS);
        p.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        p.setTextFill(Color.web("#475569"));

        Label sBadge = new Label(status);
        sBadge.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 11px; -fx-font-weight: bold; " +
                        "-fx-text-fill: " + statusColor + "; -fx-background-color: " + bgHex + "; " +
                        "-fx-padding: 4px 10px; -fx-background-radius: 8px;");
        sBadge.setPrefWidth(100);
        sBadge.setAlignment(Pos.CENTER);

        row.getChildren().addAll(t, d, h, p, sBadge);
        return row;
    }

    private VBox createTaskSummaryCard() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-border-color: #E2E8F0; -fx-border-width: 1px; -fx-border-radius: 16px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 10, 0, 0, 4);");

        addCardHoverEffect(card);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("📋 Task Summary");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 17));
        title.setTextFill(Color.web("#0F172A"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button viewAllBtn = new Button("View All ➔");
        viewAllBtn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #2563EB; -fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-cursor: hand;");
        viewAllBtn.setOnAction(e -> navigateToSection("Reports"));

        header.getChildren().addAll(title, sp, viewAllBtn);

        HBox chartBody = new HBox(30);
        chartBody.setAlignment(Pos.CENTER_LEFT);

        StackPane donutStack = new StackPane();
        donutStack.setPrefSize(140, 140);

        Arc arcCompleted = new Arc(70, 70, 60, 60, 90, -150);
        arcCompleted.setType(ArcType.OPEN);
        arcCompleted.setStroke(Color.web("#10B981"));
        arcCompleted.setStrokeWidth(16);
        arcCompleted.setFill(null);

        Arc arcPending = new Arc(70, 70, 60, 60, -60, -150);
        arcPending.setType(ArcType.OPEN);
        arcPending.setStroke(Color.web("#F59E0B"));
        arcPending.setStrokeWidth(16);
        arcPending.setFill(null);

        Arc arcInProgress = new Arc(70, 70, 60, 60, -210, -60);
        arcInProgress.setType(ArcType.OPEN);
        arcInProgress.setStroke(Color.web("#3B82F6"));
        arcInProgress.setStrokeWidth(16);
        arcInProgress.setFill(null);

        Circle centerHole = new Circle(48, Color.WHITE);

        Label centerVal = new Label("12");
        centerVal.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        centerVal.setTextFill(Color.web("#0F172A"));

        Label centerSub = new Label("Total");
        centerSub.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        centerSub.setTextFill(Color.web("#94A3B8"));

        VBox centerBox = new VBox(0, centerVal, centerSub);
        centerBox.setAlignment(Pos.CENTER);

        donutStack.getChildren().addAll(arcCompleted, arcPending, arcInProgress, centerHole, centerBox);

        VBox legend = new VBox(12);
        legend.setAlignment(Pos.CENTER_LEFT);
        legend.getChildren().addAll(
                createLegendItem("Completed", "5 (42%)", "#10B981"),
                createLegendItem("Pending", "5 (42%)", "#F59E0B"),
                createLegendItem("In Progress", "2 (16%)", "#3B82F6"));

        chartBody.getChildren().addAll(donutStack, legend);
        card.getChildren().addAll(header, chartBody);
        return card;
    }

    private HBox createLegendItem(String label, String value, String colorHex) {
        HBox item = new HBox(10);
        item.setAlignment(Pos.CENTER_LEFT);

        Circle dot = new Circle(5, Color.web(colorHex));

        Label lbl = new Label(label);
        lbl.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
        lbl.setTextFill(Color.web("#475569"));
        lbl.setPrefWidth(90);

        Label val = new Label(value);
        val.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        val.setTextFill(Color.web("#0F172A"));

        item.getChildren().addAll(dot, lbl, val);
        return item;
    }

    private VBox createRecentActivitiesCard() {
        VBox card = new VBox(14);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-border-color: #E2E8F0; -fx-border-width: 1px; -fx-border-radius: 16px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 10, 0, 0, 4);");

        addCardHoverEffect(card);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("⚡ Recent Activities");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        title.setTextFill(Color.web("#0F172A"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        header.getChildren().addAll(title, sp);

        VBox feed = new VBox(10);
        feed.getChildren().addAll(
                createActivityItem("Visit completed with Dr. Amit Sharma", "City Care Hospital", "09:45 AM", "✅"),
                createActivityItem("Product report submitted for CardioSafe", "Product Details Updated", "11:40 AM",
                        "📦"),
                createActivityItem("Task completed: Prepare monthly report", "Doctor Analytics", "01:15 PM", "📋"),
                createActivityItem("New visit scheduled with Dr. Sneha Joshi", "Health Plus Clinic", "02:30 PM", "📅"));

        card.getChildren().addAll(header, feed);
        return card;
    }

    private HBox createActivityItem(String mainText, String subText, String time, String icon) {
        HBox item = new HBox(12);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10));
        item.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 10px;");

        StackPane iconPane = new StackPane();
        iconPane.setPrefSize(32, 32);
        iconPane.setStyle(
                "-fx-background-color: white; -fx-background-radius: 8px; -fx-border-color: #E2E8F0; -fx-border-width: 1px;");
        Label iconLbl = new Label(icon);
        iconLbl.setStyle("-fx-font-size: 14px;");
        iconPane.getChildren().add(iconLbl);

        VBox texts = new VBox(2);
        Label mText = new Label(mainText);
        mText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        mText.setTextFill(Color.web("#1E293B"));

        Label sText = new Label(subText);
        sText.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        sText.setTextFill(Color.web("#64748B"));

        texts.getChildren().addAll(mText, sText);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label timeLbl = new Label(time);
        timeLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        timeLbl.setTextFill(Color.web("#94A3B8"));

        item.getChildren().addAll(iconPane, texts, sp, timeLbl);
        return item;
    }

    private VBox createProductReportCard() {
        VBox card = new VBox(14);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-border-color: #E2E8F0; -fx-border-width: 1px; -fx-border-radius: 16px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 10, 0, 0, 4);");

        addCardHoverEffect(card);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("📦 Product Report (Top Detailed)");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        title.setTextFill(Color.web("#0F172A"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button viewAllBtn = new Button("View All ➔");
        viewAllBtn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #2563EB; -fx-font-family: 'Segoe UI'; -fx-font-size: 12px; -fx-font-weight: bold; -fx-cursor: hand;");
        viewAllBtn.setOnAction(e -> navigateToSection("Product Report"));

        header.getChildren().addAll(title, sp, viewAllBtn);

        VBox list = new VBox(12);
        list.getChildren().addAll(
                createProductBar("CardioSafe", 12, 15, "#3B82F6"),
                createProductBar("NeuroPlus", 8, 15, "#06B6D4"),
                createProductBar("GlucoCare", 6, 15, "#6366F1"),
                createProductBar("RespiraCalm", 4, 15, "#0EA5E9"));

        card.getChildren().addAll(header, list);
        return card;
    }

    private VBox createProductBar(String name, int count, int max, String colorHex) {
        VBox box = new VBox(4);

        HBox top = new HBox();
        Label nLbl = new Label(name);
        nLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        nLbl.setTextFill(Color.web("#334155"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label cLbl = new Label(String.valueOf(count));
        cLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        cLbl.setTextFill(Color.web("#0F172A"));

        top.getChildren().addAll(nLbl, sp, cLbl);

        ProgressBar pBar = new ProgressBar((double) count / max);
        pBar.setMaxWidth(Double.MAX_VALUE);
        pBar.setStyle("-fx-accent: " + colorHex + ";");

        box.getChildren().addAll(top, pBar);
        return box;
    }

    private VBox createDetailedScheduleItem(String time, String doc, String spec, String location, String purpose,
            String status, String statusColor, String bgHex) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(16));
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 14px; -fx-border-color: #E2E8F0; -fx-border-width: 1px;");
        addCardHoverEffect(card);

        HBox top = new HBox();
        top.setAlignment(Pos.CENTER_LEFT);

        Label tLbl = new Label("⏰ " + time);
        tLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        tLbl.setTextFill(Color.web("#2563EB"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label sBadge = new Label(status);
        sBadge.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + statusColor
                        + "; -fx-background-color: " + bgHex + "; -fx-padding: 4px 12px; -fx-background-radius: 8px;");

        top.getChildren().addAll(tLbl, sp, sBadge);

        Label dLbl = new Label(doc + " (" + spec + ")");
        dLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        dLbl.setTextFill(Color.web("#0F172A"));

        Label locLbl = new Label("🏥 " + location);
        locLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        locLbl.setTextFill(Color.web("#64748B"));

        Label purpLbl = new Label("📌 Purpose: " + purpose);
        purpLbl.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
        purpLbl.setTextFill(Color.web("#334155"));

        HBox actions = new HBox(8);
        actions.setAlignment(Pos.CENTER_RIGHT);
        Button completeBtn = createStyledButton("Mark Complete", "#10B981", "white");
        Button rescheduleBtn = createStyledButton("Reschedule", "#F1F5F9", "#475569");
        actions.getChildren().addAll(rescheduleBtn, completeBtn);

        card.getChildren().addAll(top, dLbl, locLbl, purpLbl, actions);
        return card;
    }

    private VBox createDoctorCard(String name, String spec, String hospital, String visits, String phone,
            String accentHex) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(18));
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-width: 1px;");
        addCardHoverEffect(card);

        HBox top = new HBox(12);
        top.setAlignment(Pos.CENTER_LEFT);

        StackPane avatar = new StackPane();
        avatar.setPrefSize(44, 44);
        avatar.setStyle("-fx-background-color: " + accentHex + "; -fx-background-radius: 22px;");
        Label aTxt = new Label(name.replaceAll("Dr\\. ", "").substring(0, 1));
        aTxt.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        aTxt.setTextFill(Color.WHITE);
        avatar.getChildren().add(aTxt);

        Label nLbl = new Label(name);
        nLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        nLbl.setTextFill(Color.web("#0F172A"));

        Label sLbl = new Label(spec);
        sLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        sLbl.setTextFill(Color.web("#64748B"));

        VBox info = new VBox(2, nLbl, sLbl);
        top.getChildren().addAll(avatar, info);

        Label hLbl = new Label("🏥 " + hospital);
        hLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        hLbl.setTextFill(Color.web("#475569"));

        Label pLbl = new Label("📞 " + phone + " • " + visits);
        pLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        pLbl.setTextFill(Color.web("#94A3B8"));

        Button schedBtn = createStyledButton("Schedule Visit", accentHex, "white");
        schedBtn.setMaxWidth(Double.MAX_VALUE);

        card.getChildren().addAll(top, hLbl, pLbl, schedBtn);
        return card;
    }

    private VBox createHospitalCard(String name, String type, String address, String doctorsCount, String visitCount,
            String accentHex) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(18));
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-width: 1px;");
        addCardHoverEffect(card);

        HBox top = new HBox();
        top.setAlignment(Pos.CENTER_LEFT);

        Label nLbl = new Label("🏥 " + name);
        nLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        nLbl.setTextFill(Color.web("#0F172A"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label tBadge = new Label(type);
        tBadge.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: "
                + accentHex + "; -fx-background-color: #F1F5F9; -fx-padding: 4px 10px; -fx-background-radius: 8px;");

        top.getChildren().addAll(nLbl, sp, tBadge);

        Label aLbl = new Label("📍 " + address);
        aLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        aLbl.setTextFill(Color.web("#64748B"));

        HBox statsRow = new HBox(20);
        Label dCount = new Label("👨‍⚕️ " + doctorsCount);
        dCount.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        dCount.setTextFill(Color.web("#334155"));

        Label vCount = new Label("📊 " + visitCount);
        vCount.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        vCount.setTextFill(Color.web("#334155"));

        statsRow.getChildren().addAll(dCount, vCount);

        card.getChildren().addAll(top, aLbl, statsRow);
        return card;
    }

    private VBox createProductDetailTile(String name, String category, int count, int samplesLeft, double ratio,
            String accentHex) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(18));
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-width: 1px;");
        addCardHoverEffect(card);

        Label nLbl = new Label("📦 " + name);
        nLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        nLbl.setTextFill(Color.web("#0F172A"));

        Label cLbl = new Label("Category: " + category);
        cLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        cLbl.setTextFill(Color.web("#64748B"));

        HBox stats = new HBox(20);
        Label dCount = new Label("Detailed: " + count + " times");
        dCount.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        dCount.setTextFill(Color.web("#0F172A"));

        Label sCount = new Label("Samples: " + samplesLeft + " units");
        sCount.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 12));
        sCount.setTextFill(Color.web("#10B981"));

        stats.getChildren().addAll(dCount, sCount);

        ProgressBar pBar = new ProgressBar(ratio);
        pBar.setMaxWidth(Double.MAX_VALUE);
        pBar.setStyle("-fx-accent: " + accentHex + ";");

        card.getChildren().addAll(nLbl, cLbl, stats, pBar);
        return card;
    }

    private Button createPillButton(String text, boolean active) {
        Button btn = new Button(text);
        if (active) {
            btn.setStyle(
                    "-fx-font-family: 'Segoe UI'; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #2563EB; -fx-background-radius: 20px; -fx-padding: 6px 14px; -fx-cursor: hand;");
        } else {
            btn.setStyle(
                    "-fx-font-family: 'Segoe UI'; -fx-font-size: 12px; -fx-font-weight: medium; -fx-text-fill: #64748B; -fx-background-color: #F1F5F9; -fx-background-radius: 20px; -fx-padding: 6px 14px; -fx-cursor: hand;");
        }
        return btn;
    }

    private Button createStyledButton(String text, String bgHex, String textHex) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-font-weight: bold; " +
                        "-fx-text-fill: " + textHex + "; -fx-background-color: " + bgHex + "; " +
                        "-fx-background-radius: 10px; -fx-padding: 8px 16px; -fx-cursor: hand;");

        ScaleTransition stEnter = new ScaleTransition(Duration.millis(120), btn);
        stEnter.setToX(1.03);
        stEnter.setToY(1.03);

        ScaleTransition stExit = new ScaleTransition(Duration.millis(120), btn);
        stExit.setToX(1.0);
        stExit.setToY(1.0);

        btn.setOnMouseEntered(e -> stEnter.playFromStart());
        btn.setOnMouseExited(e -> stExit.playFromStart());

        return btn;
    }

    private void styleFormInput(TextField tf) {
        tf.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-padding: 8px 12px; -fx-background-radius: 8px; -fx-border-color: #CBD5E1; -fx-border-radius: 8px;");
    }

    private void addCardHoverEffect(Node card) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(15, 23, 42, 0.04));
        shadow.setRadius(10);
        shadow.setOffsetY(4);
        card.setEffect(shadow);

        TranslateTransition ttEnter = new TranslateTransition(Duration.millis(150), card);
        ttEnter.setToY(-4);

        TranslateTransition ttExit = new TranslateTransition(Duration.millis(150), card);
        ttExit.setToY(0);

        card.setOnMouseEntered(e -> {
            shadow.setRadius(20);
            shadow.setColor(Color.rgb(15, 23, 42, 0.08));
            ttEnter.playFromStart();
        });

        card.setOnMouseExited(e -> {
            shadow.setRadius(10);
            shadow.setColor(Color.rgb(15, 23, 42, 0.04));
            ttExit.playFromStart();
        });
    }
}