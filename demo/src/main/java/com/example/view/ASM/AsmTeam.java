package com.example.view.ASM;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AsmTeam - MedTrack Pro (ASM Portal)
 * Dedicated view for managing field medical representatives, tracking daily
 * call coverage,
 * target quotas, and territory allocations.
 */
public class AsmTeam {

    private Stage primaryStage;
    private Scene mainScene;
    private final ObservableList<MrMember> masterTeamList = FXCollections.observableArrayList();
    private final ObservableList<MrMember> filteredTeamList = FXCollections.observableArrayList();

    private VBox teamCardsContainer;
    private TextField searchField;
    private ComboBox<String> territoryFilterCombo;
    private ComboBox<String> statusFilterCombo;

    // Design Tokens (Light Executive Theme)
    private static final String COLOR_PRIMARY = "#059669";
    private static final String COLOR_PRIMARY_CONTAINER = "#10B981";
    private static final String COLOR_BACKGROUND = "#F5F7FA";
    private static final String COLOR_SURFACE = "#FFFFFF";
    private static final String COLOR_SURFACE_LOW = "#ECFDF5";
    private static final String COLOR_ON_SURFACE = "#191B23";
    private static final String COLOR_SECONDARY = "#5C5F61";
    private static final String COLOR_OUTLINE_VARIANT = "#E2E8F0";
    private static final String COLOR_SUCCESS = "#16A34A";
    private static final String COLOR_SUCCESS_BG = "#DCFCE7";
    private static final String COLOR_DANGER = "#DC2626";
    private static final String COLOR_DANGER_BG = "#FEE2E2";
    private static final String COLOR_INFO = "#0284C7";
    private static final String COLOR_INFO_BG = "#E0F2FE";
    private static final String COLOR_WARNING = "#D97706";
    private static final String COLOR_WARNING_BG = "#FEF3C7";

    public AsmTeam() {
        initSampleTeam();
    }

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

        // Header & Toolbar
        HBox headerBox = createHeaderBox();

        // KPI Summary Cards
        HBox kpiBox = createTeamKPIs();

        // Filter Bar
        HBox filterCard = createFilterBar();

        // Reps Table / Cards Container
        VBox tableCard = createTeamTableCard();

        container.getChildren().addAll(headerBox, kpiBox, filterCard, tableCard);
        return container;
    }

    private HBox createHeaderBox() {
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(3);
        Label title = new Label("Medical Representative Team (North Zone)");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web(COLOR_ON_SURFACE));

        Label subtitle = new Label(
                "Supervise field activity, call quotas, sales achievements, and territory coverage.");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitle.setTextFill(Color.web(COLOR_SECONDARY));
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox btnGroup = new HBox(10);
        btnGroup.setAlignment(Pos.CENTER_RIGHT);

        Button broadcastBtn = new Button("Broadcast Notice");
        broadcastBtn.setPrefHeight(38);
        broadcastBtn.setPadding(new Insets(0, 16, 0, 16));
        broadcastBtn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        broadcastBtn.setStyle("-fx-background-color: white; -fx-text-fill: " + COLOR_ON_SURFACE + "; -fx-border-color: "
                + COLOR_OUTLINE_VARIANT + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-cursor: hand;");
        broadcastBtn.setOnAction(
                e -> showInfoAlert("Broadcast Notice", "Message broadcast dialog dispatched to all 12 field MRs."));

        Button addRepBtn = new Button("Add Field Rep");
        addRepBtn.setPrefHeight(38);
        addRepBtn.setPadding(new Insets(0, 16, 0, 16));
        addRepBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        addRepBtn.setStyle("-fx-background-color: " + COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-background-radius: 8px; -fx-cursor: hand;");
        addRepBtn.setOnAction(e -> showInfoAlert("Add Representative", "Field representative onboarding workflow initialized."));

        btnGroup.getChildren().addAll(broadcastBtn, addRepBtn);
        header.getChildren().addAll(titleBox, spacer, btnGroup);
        return header;
    }

    private HBox createTeamKPIs() {
        HBox box = new HBox(16);

        VBox kpi1 = createMiniKPICard("Total Active MRs", "12 Reps", "100% Territory Assigned", COLOR_PRIMARY);
        VBox kpi2 = createMiniKPICard("Avg Target Realization", "82.4%", "+4.1% vs Last Month", COLOR_SUCCESS);
        VBox kpi3 = createMiniKPICard("Total Field Calls Today", "78 Visits", "6.5 Visits / Rep Avg", COLOR_INFO);

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

    private HBox createFilterBar() {
        HBox card = new HBox(14);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(12, 16, 12, 16));
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: " + COLOR_OUTLINE_VARIANT + ";" +
                        "-fx-border-radius: 10px;");

        HBox searchBox = new HBox(8);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPrefWidth(260);
        searchBox.setPrefHeight(38);
        searchBox.setPadding(new Insets(0, 12, 0, 12));
        searchBox.setStyle(
                "-fx-background-color: " + COLOR_SURFACE_LOW + "; -fx-background-radius: 6px; -fx-border-color: "
                        + COLOR_OUTLINE_VARIANT + "; -fx-border-radius: 6px;");

        Label sIcon = new Label("");
        searchField = new TextField();
        searchField.setPromptText("Search rep by name or ID...");
        searchField.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_ON_SURFACE + ";");
        searchField.textProperty().addListener((obs, oldV, newV) -> applyFilters());
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchBox.getChildren().addAll(sIcon, searchField);

        territoryFilterCombo = new ComboBox<>();
        territoryFilterCombo.getItems().addAll("All Districts", "Central District", "North Sector", "East Sector", "South Hub", "West Zone");
        territoryFilterCombo.setValue("All Districts");
        territoryFilterCombo.setPrefHeight(38);
        territoryFilterCombo.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + COLOR_OUTLINE_VARIANT
                + "; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-cursor: hand;");
        territoryFilterCombo.setOnAction(e -> applyFilters());

        statusFilterCombo = new ComboBox<>();
        statusFilterCombo.getItems().addAll("All Statuses", "Active", "In Field", "On Leave");
        statusFilterCombo.setValue("All Statuses");
        statusFilterCombo.setPrefHeight(38);
        statusFilterCombo.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + COLOR_OUTLINE_VARIANT
                + "; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-cursor: hand;");
        statusFilterCombo.setOnAction(e -> applyFilters());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button clearBtn = new Button("Clear Filter");
        clearBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_PRIMARY + "; -fx-cursor: hand;");
        clearBtn.setOnAction(e -> {
            searchField.clear();
            territoryFilterCombo.setValue("All Districts");
            statusFilterCombo.setValue("All Statuses");
            applyFilters();
        });

        card.getChildren().addAll(searchBox, territoryFilterCombo, statusFilterCombo, spacer, clearBtn);
        return card;
    }

    private VBox createTeamTableCard() {
        VBox card = new VBox(0);
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: " + COLOR_OUTLINE_VARIANT + ";" +
                        "-fx-border-radius: 12px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        HBox headerRow = new HBox(12);
        headerRow.setAlignment(Pos.CENTER_LEFT);
        headerRow.setPrefHeight(44);
        headerRow.setPadding(new Insets(0, 16, 0, 16));
        headerRow.setStyle(
                "-fx-background-color: #F8FAFC; -fx-border-color: transparent transparent " + COLOR_OUTLINE_VARIANT
                        + " transparent; -fx-border-width: 0 0 1 0; -fx-background-radius: 12px 12px 0 0;");

        Label colRep = new Label("REPRESENTATIVE & ID");
        colRep.setPrefWidth(220);
        colRep.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        colRep.setTextFill(Color.web(COLOR_SECONDARY));

        Label colTerr = new Label("ASSIGNED TERRITORY");
        colTerr.setPrefWidth(180);
        colTerr.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        colTerr.setTextFill(Color.web(COLOR_SECONDARY));

        Label colCalls = new Label("TODAY'S CALLS");
        colCalls.setPrefWidth(120);
        colCalls.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        colCalls.setTextFill(Color.web(COLOR_SECONDARY));

        Label colTarget = new Label("MONTHLY TARGET PROGRESS");
        colTarget.setPrefWidth(220);
        colTarget.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        colTarget.setTextFill(Color.web(COLOR_SECONDARY));

        Label colStatus = new Label("STATUS");
        colStatus.setPrefWidth(100);
        colStatus.setAlignment(Pos.CENTER);
        colStatus.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        colStatus.setTextFill(Color.web(COLOR_SECONDARY));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label colAction = new Label("ACTIONS");
        colAction.setPrefWidth(120);
        colAction.setAlignment(Pos.CENTER_RIGHT);
        colAction.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        colAction.setTextFill(Color.web(COLOR_SECONDARY));

        headerRow.getChildren().addAll(colRep, colTerr, colCalls, colTarget, colStatus, sp, colAction);

        teamCardsContainer = new VBox(0);
        applyFilters();

        card.getChildren().addAll(headerRow, teamCardsContainer);
        return card;
    }

    private void applyFilters() {
        if (teamCardsContainer == null)
            return;
        teamCardsContainer.getChildren().clear();

        String search = searchField != null ? searchField.getText().toLowerCase().trim() : "";
        String terr = territoryFilterCombo != null ? territoryFilterCombo.getValue() : "All Territories";
        String status = statusFilterCombo != null ? statusFilterCombo.getValue() : "All Statuses";

        List<MrMember> filtered = masterTeamList.stream().filter(m -> {
            boolean matchesSearch = search.isEmpty() ||
                    m.name.toLowerCase().contains(search) ||
                    m.id.toLowerCase().contains(search) ||
                    m.territory.toLowerCase().contains(search);

            boolean matchesTerr = "All Territories".equals(terr) || m.territory.equalsIgnoreCase(terr);
            boolean matchesStatus = "All Statuses".equals(status) || m.status.equalsIgnoreCase(status);

            return matchesSearch && matchesTerr && matchesStatus;
        }).collect(Collectors.toList());

        filteredTeamList.setAll(filtered);

        if (filtered.isEmpty()) {
            VBox empty = new VBox(12);
            empty.setAlignment(Pos.CENTER);
            empty.setPadding(new Insets(48));
            Label msg = new Label("No field representatives found matching your filter criteria.");
            msg.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
            msg.setTextFill(Color.web(COLOR_SECONDARY));
            empty.getChildren().add(msg);
            teamCardsContainer.getChildren().add(empty);
            return;
        }

        for (int i = 0; i < filtered.size(); i++) {
            MrMember m = filtered.get(i);
            boolean isLast = (i == filtered.size() - 1);
            teamCardsContainer.getChildren().add(createMrRow(m, isLast));
        }
    }

    private HBox createMrRow(MrMember m, boolean isLast) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPrefHeight(64);
        row.setPadding(new Insets(8, 16, 8, 16));
        row.setStyle("-fx-background-color: white; -fx-border-color: transparent transparent " + COLOR_OUTLINE_VARIANT
                + " transparent; -fx-border-width: 0 0 " + (isLast ? "0" : "1") + " 0;");

        // Avatar
        StackPane avPane = new StackPane();
        avPane.setPrefSize(40, 40);
        Circle avCircle = new Circle(20, Color.web(m.colorHex));
        Label avInitials = new Label(m.initials);
        avInitials.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        avInitials.setTextFill(Color.WHITE);
        avPane.getChildren().addAll(avCircle, avInitials);

        VBox repInfo = new VBox(2);
        repInfo.setPrefWidth(168);
        Label nameLbl = new Label(m.name);
        nameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        nameLbl.setTextFill(Color.web(COLOR_ON_SURFACE));
        Label idLbl = new Label("ID: " + m.id + " • " + m.phone);
        idLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        idLbl.setTextFill(Color.web(COLOR_SECONDARY));
        repInfo.getChildren().addAll(nameLbl, idLbl);

        Label terrLbl = new Label(m.territory);
        terrLbl.setPrefWidth(180);
        terrLbl.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 12));
        terrLbl.setTextFill(Color.web(COLOR_ON_SURFACE));

        Label callsLbl = new Label(m.callsToday + " Visits");
        callsLbl.setPrefWidth(120);
        callsLbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        callsLbl.setTextFill(Color.web(COLOR_PRIMARY));

        VBox targetBox = new VBox(4);
        targetBox.setPrefWidth(220);
        ProgressBar pb = new ProgressBar(m.targetRatio);
        pb.setPrefWidth(200);
        pb.setStyle("-fx-accent: " + (m.targetRatio >= 0.85 ? COLOR_PRIMARY : COLOR_WARNING) + ";");
        Label tPct = new Label((int) (m.targetRatio * 100) + "% (" + m.salesAmount + " / " + m.targetAmount + ")");
        tPct.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        tPct.setTextFill(Color.web(COLOR_SECONDARY));
        targetBox.getChildren().addAll(pb, tPct);

        // Status Badge
        StackPane statusContainer = new StackPane();
        statusContainer.setPrefWidth(100);
        statusContainer.setAlignment(Pos.CENTER);
        Label statusBadge = new Label(m.status);
        statusBadge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        statusBadge.setPadding(new Insets(3, 10, 3, 10));
        if ("Active".equalsIgnoreCase(m.status)) {
            statusBadge.setStyle("-fx-background-color: " + COLOR_SUCCESS_BG + "; -fx-text-fill: " + COLOR_SUCCESS
                    + "; -fx-background-radius: 20px;");
        } else if ("In Field".equalsIgnoreCase(m.status)) {
            statusBadge.setStyle("-fx-background-color: " + COLOR_INFO_BG + "; -fx-text-fill: " + COLOR_INFO
                    + "; -fx-background-radius: 20px;");
        } else {
            statusBadge.setStyle("-fx-background-color: " + COLOR_WARNING_BG + "; -fx-text-fill: " + COLOR_WARNING
                    + "; -fx-background-radius: 20px;");
        }
        statusContainer.getChildren().add(statusBadge);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        HBox actionBtns = new HBox(8);
        actionBtns.setAlignment(Pos.CENTER_RIGHT);
        actionBtns.setPrefWidth(120);

        Button callBtn = new Button("Call");
        callBtn.setStyle("-fx-background-color: " + COLOR_SURFACE_LOW + "; -fx-text-fill: " + COLOR_PRIMARY
                + "; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-cursor: hand;");
        callBtn.setOnAction(e -> showInfoAlert("Connecting Call", "Dialing " + m.name + " (" + m.phone + ")"));

        actionBtns.getChildren().add(callBtn);

        row.getChildren().addAll(avPane, repInfo, terrLbl, callsLbl, targetBox, statusContainer, sp, actionBtns);

        row.setOnMouseEntered(e -> row.setStyle(
                "-fx-background-color: #F8FAFC; -fx-border-color: transparent transparent " + COLOR_OUTLINE_VARIANT
                        + " transparent; -fx-border-width: 0 0 " + (isLast ? "0" : "1") + " 0; -fx-cursor: hand;"));
        row.setOnMouseExited(e -> row.setStyle("-fx-background-color: white; -fx-border-color: transparent transparent "
                + COLOR_OUTLINE_VARIANT + " transparent; -fx-border-width: 0 0 " + (isLast ? "0" : "1") + " 0;"));

        return row;
    }

    private void openAddRepDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Add Field Representative");

        VBox root = new VBox(14);
        root.setPadding(new Insets(24));
        root.setStyle("-fx-background-color: #FFFFFF;");

        Label title = new Label("Register New Medical Representative");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

        TextField nameInput = new TextField();
        nameInput.setPromptText("Representative Full Name");

        TextField phoneInput = new TextField();
        phoneInput.setPromptText("Mobile Phone (e.g. +1 555-123-456)");

        ComboBox<String> terrInput = new ComboBox<>();
        terrInput.getItems().addAll("Central District", "North Sector", "East Sector", "West Zone", "South Hub");
        terrInput.setValue("Central District");
        terrInput.setMaxWidth(Double.MAX_VALUE);

        TextField targetInput = new TextField();
        targetInput.setPromptText("Monthly Sales Target ($ e.g. 10000)");

        HBox btnBox = new HBox(12);
        btnBox.setAlignment(Pos.CENTER_RIGHT);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: " + COLOR_SECONDARY + "; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> dialog.close());

        Button saveBtn = new Button("Register Rep");
        saveBtn.setStyle("-fx-background-color: " + COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-cursor: hand;");
        saveBtn.setOnAction(e -> {
            String name = nameInput.getText().trim();
            if (name.isEmpty())
                return;
            String phone = phoneInput.getText().trim().isEmpty() ? "+1 (555) 000-1122" : phoneInput.getText().trim();
            String initials = name.substring(0, Math.min(2, name.length())).toUpperCase();

            masterTeamList.add(0, new MrMember("MR-10" + (masterTeamList.size() + 1), name, terrInput.getValue(), 0,
                    0.0, "$0", "$10,000", "In Field", initials, COLOR_PRIMARY, phone));
            applyFilters();
            dialog.close();
            showInfoAlert("Representative Added", name + " successfully assigned to " + terrInput.getValue());
        });

        btnBox.getChildren().addAll(cancelBtn, saveBtn);

        root.getChildren().addAll(
                title,
                new Label("Full Name:"), nameInput,
                new Label("Phone:"), phoneInput,
                new Label("Territory Assignment:"), terrInput,
                new Label("Target Quota:"), targetInput,
                btnBox);

        Scene scene = new Scene(root, 420, 440);
        dialog.setScene(scene);
        dialog.showAndWait();
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
                createSidebarItem("My Team (MRs)", "", true),
                createSidebarItem("Territories", "", false),
                createSidebarItem("Approvals", "", false),
                createSidebarItem("Reports & Analytics", "", false),
                createSidebarItem("Settings", "", false));

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

        Label searchLbl = new Label("Area Sales Manager • Team Management & Oversight");
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

    private void initSampleTeam() {
        masterTeamList.clear();
        masterTeamList.add(new MrMember("MR-1001", "Alex Mercer", "Central District", 8, 0.94, "$35,720", "$38,000",
                "Active", "AM", COLOR_PRIMARY, "+1 (555) 234-5678"));
        masterTeamList.add(new MrMember("MR-1002", "Sarah Connor", "North Sector", 7, 0.88, "$28,160", "$32,000",
                "Active", "SC", "#3B82F6", "+1 (555) 345-6789"));
        masterTeamList.add(new MrMember("MR-1003", "David Miller", "East Sector", 6, 0.82, "$21,320", "$26,000",
                "In Field", "DM", "#8B5CF6", "+1 (555) 456-7890"));
        masterTeamList.add(new MrMember("MR-1004", "Priya Sharma", "West Zone", 5, 0.79, "$18,960", "$24,000", "Active",
                "PS", "#D97706", "+1 (555) 567-8901"));
        masterTeamList.add(new MrMember("MR-1005", "Robert Chen", "South Hub", 4, 0.75, "$15,000", "$20,000",
                "On Leave", "RC", "#475569", "+1 (555) 678-9012"));
    }

    public static class MrMember {
        public String id;
        public String name;
        public String territory;
        public int callsToday;
        public double targetRatio;
        public String salesAmount;
        public String targetAmount;
        public String status;
        public String initials;
        public String colorHex;
        public String phone;

        public MrMember(String id, String name, String territory, int callsToday,
                double targetRatio, String salesAmount, String targetAmount,
                String status, String initials, String colorHex, String phone) {
            this.id = id;
            this.name = name;
            this.territory = territory;
            this.callsToday = callsToday;
            this.targetRatio = targetRatio;
            this.salesAmount = salesAmount;
            this.targetAmount = targetAmount;
            this.status = status;
            this.initials = initials;
            this.colorHex = colorHex;
            this.phone = phone;
        }
    }
}
