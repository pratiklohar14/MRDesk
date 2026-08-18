package com.example.view.ASM;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * AsmSettings - MedTrack Pro (ASM Portal)
 * Dedicated view for Manager Profile Credentials, Zone Configurations, and Operational Preferences.
 */
public class AsmSettings {



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
    private static final String COLOR_SUCCESS_BG = "#DCFCE7";



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
        scrollPane.setStyle("-fx-background-color: " + COLOR_BACKGROUND + "; -fx-background: " + COLOR_BACKGROUND + "; -fx-border-color: transparent;");
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

        // ASM Manager Badge Card
        HBox badgeCard = createManagerBadgeCard();

        // 2-Column Settings (Personal & Managerial Controls)
        HBox columns = new HBox(20);

        VBox personalCard = createPersonalInfoCard();
        VBox zoneConfigCard = createZoneConfigCard();

        HBox.setHgrow(personalCard, Priority.ALWAYS);
        HBox.setHgrow(zoneConfigCard, Priority.ALWAYS);

        columns.getChildren().addAll(personalCard, zoneConfigCard);

        container.getChildren().addAll(headerBox, badgeCard, columns);
        return container;
    }

    private HBox createHeaderBox() {
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(3);
        Label title = new Label("Manager Profile & System Settings");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web(COLOR_ON_SURFACE));

        Label subtitle = new Label("Manage personal credentials, zone allocations, approval notification thresholds, and security.");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitle.setTextFill(Color.web(COLOR_SECONDARY));
        titleBox.getChildren().addAll(title, subtitle);

        header.getChildren().add(titleBox);
        return header;
    }

    private HBox createManagerBadgeCard() {
        HBox card = new HBox(20);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(24));
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: " + COLOR_OUTLINE_VARIANT + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);"
        );

        StackPane avPane = new StackPane();
        avPane.setPrefSize(72, 72);
        Circle circle = new Circle(36, Color.web(COLOR_PRIMARY));
        Label initials = new Label("MV");
        initials.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        initials.setTextFill(Color.WHITE);
        avPane.getChildren().addAll(circle, initials);

        VBox textBox = new VBox(4);
        Label name = new Label("Marcus Vance");
        name.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        name.setTextFill(Color.web(COLOR_ON_SURFACE));

        Label role = new Label("Area Sales Manager (ASM) • Executive ID: ASM-4401");
        role.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        role.setTextFill(Color.web(COLOR_SECONDARY));

        HBox tags = new HBox(8);
        Label tag1 = new Label("Territory: North Metropolitan Zone");
        tag1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        tag1.setPadding(new Insets(3, 8, 3, 8));
        tag1.setStyle("-fx-background-color: " + COLOR_SURFACE_LOW + "; -fx-text-fill: " + COLOR_PRIMARY + "; -fx-background-radius: 6px;");

        Label tag2 = new Label("Direct Reports: 12 MRs");
        tag2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        tag2.setPadding(new Insets(3, 8, 3, 8));
        tag2.setStyle("-fx-background-color: " + COLOR_SUCCESS_BG + "; -fx-text-fill: " + COLOR_SUCCESS + "; -fx-background-radius: 6px;");

        tags.getChildren().addAll(tag1, tag2);
        textBox.getChildren().addAll(name, role, tags);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button changePicBtn = new Button("Change Photo");
        changePicBtn.setStyle("-fx-background-color: white; -fx-border-color: " + COLOR_OUTLINE_VARIANT + "; -fx-border-radius: 8px; -fx-cursor: hand;");
        changePicBtn.setOnAction(e -> showInfoAlert("Profile Picture", "Avatar update dialog opened."));

        card.getChildren().addAll(avPane, textBox, sp, changePicBtn);
        return card;
    }

    private VBox createPersonalInfoCard() {
        VBox card = new VBox(14);
        card.setPadding(new Insets(24));
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: " + COLOR_OUTLINE_VARIANT + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);"
        );

        Label secTitle = new Label("Manager Account & Credentials");
        secTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        secTitle.setTextFill(Color.web(COLOR_ON_SURFACE));

        TextField nameF = createStyledField("Marcus Vance");
        TextField emailF = createStyledField("marcus.vance@medtrackpro.com");
        TextField phoneF = createStyledField("+1 (555) 888-9900");
        PasswordField passF = new PasswordField();
        passF.setText("asmpassword2025");
        passF.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: " + COLOR_OUTLINE_VARIANT + "; -fx-border-radius: 6px;");

        Button saveBtn = new Button("Save Profile Changes");
        saveBtn.setPrefHeight(38);
        saveBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        saveBtn.setStyle("-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white; -fx-background-radius: 6px; -fx-cursor: hand;");
        saveBtn.setOnAction(e -> showInfoAlert("Saved", "Account settings updated successfully."));

        card.getChildren().addAll(
                secTitle,
                new Label("Full Name:"), nameF,
                new Label("Official Email:"), emailF,
                new Label("Contact Number:"), phoneF,
                new Label("Password:"), passF,
                saveBtn
        );
        return card;
    }

    private VBox createZoneConfigCard() {
        VBox card = new VBox(14);
        card.setPadding(new Insets(24));
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: " + COLOR_OUTLINE_VARIANT + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);"
        );

        Label secTitle = new Label("Zone Controls & Notification Triggers");
        secTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        secTitle.setTextFill(Color.web(COLOR_ON_SURFACE));

        TextField zoneF = createStyledField("North Metropolitan Sector (5 Active Districts)");
        TextField hqF = createStyledField("Metro Health Science Center (North HQ)");

        CheckBox cb1 = new CheckBox("Instant Push Alerts for High-Priority Sample Requests");
        cb1.setSelected(true);

        CheckBox cb2 = new CheckBox("Daily 18:00 Call Completion Summary Broadcast");
        cb2.setSelected(true);

        CheckBox cb3 = new CheckBox("Require Dual-Signoff for Outstation Tour Programs");
        cb3.setSelected(false);

        Button savePrefBtn = new Button("Update Managerial Preferences");
        savePrefBtn.setPrefHeight(38);
        savePrefBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        savePrefBtn.setStyle("-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white; -fx-background-radius: 6px; -fx-cursor: hand;");
        savePrefBtn.setOnAction(e -> showInfoAlert("Preferences Saved", "Zone configurations updated."));

        card.getChildren().addAll(
                secTitle,
                new Label("Assigned Zone:"), zoneF,
                new Label("Headquarter Base:"), hqF,
                cb1, cb2, cb3,
                savePrefBtn
        );
        return card;
    }

    private TextField createStyledField(String text) {
        TextField tf = new TextField(text);
        tf.setStyle("-fx-padding: 8px; -fx-background-radius: 6px; -fx-border-color: " + COLOR_OUTLINE_VARIANT + "; -fx-border-radius: 6px; -fx-text-fill: " + COLOR_ON_SURFACE + ";");
        return tf;
    }

    private VBox createSideNavBar() {
        VBox sidebar = new VBox(14);
        sidebar.setPrefWidth(280);
        sidebar.setPadding(new Insets(24, 16, 20, 16));
        sidebar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: transparent " + COLOR_OUTLINE_VARIANT + " transparent transparent; -fx-border-width: 0 1 0 0;");

        HBox logoContainer = new HBox(12);
        logoContainer.setAlignment(Pos.CENTER_LEFT);
        StackPane logoBadge = new StackPane();
        logoBadge.setPrefSize(42, 42);
        logoBadge.setStyle("-fx-background-color: " + COLOR_PRIMARY + "; -fx-background-radius: 10px;");
        Label logoLetter = new Label("ASM");
        logoLetter.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        logoLetter.setTextFill(Color.WHITE);
        logoBadge.getChildren().add(logoLetter);
        logoContainer.getChildren().addAll(logoBadge, new VBox(1, new Label("MRDesk ASM"), new Label("Managerial Suite")));

        VBox navLinks = new VBox(6);
        navLinks.getChildren().addAll(
                createSidebarItem("Dashboard", "", false),
                createSidebarItem("My Team (MRs)", "", false),
                createSidebarItem("Territories", "", false),
                createSidebarItem("Approvals", "", false),
                createSidebarItem("Reports & Analytics", "", false),
                createSidebarItem("Settings", "", true)
        );

        sidebar.getChildren().addAll(logoContainer, navLinks);
        return sidebar;
    }

    private Button createSidebarItem(String title, String icon, boolean isActive) {
        Button btn = new Button(icon + "   " + title);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPrefHeight(40);
        btn.setPadding(new Insets(8, 14, 8, 14));
        btn.setFont(Font.font("Segoe UI", isActive ? FontWeight.BOLD : FontWeight.SEMI_BOLD, 13));
        if (isActive) {
            btn.setStyle("-fx-background-color: " + COLOR_SURFACE_LOW + "; -fx-text-fill: " + COLOR_PRIMARY + "; -fx-background-radius: 8px; -fx-border-color: transparent " + COLOR_PRIMARY + " transparent transparent; -fx-border-width: 0 4 0 0;");
        } else {
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_SECONDARY + "; -fx-background-radius: 8px; -fx-cursor: hand;");
        }
        btn.setOnAction(e -> handleNavClick(title));
        return btn;
    }

    private void handleNavClick(String title) {
        if (primaryStage == null) return;
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
        topBar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); -fx-border-color: transparent transparent " + COLOR_OUTLINE_VARIANT + " transparent; -fx-border-width: 0 0 1 0;");

        Label searchLbl = new Label("Area Sales Manager • Profile Credentials & Zone Config");
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
