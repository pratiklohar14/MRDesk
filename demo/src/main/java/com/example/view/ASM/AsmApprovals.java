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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AsmApprovals - MedTrack Pro (ASM Portal)
 * Dedicated view for managing representative approval requests: Sample
 * requisitions,
 * expense travel vouchers, doctor symposium sponsorships, and tour programs.
 */
public class AsmApprovals {

    private Stage primaryStage;
    private Scene mainScene;
    private final ObservableList<ApprovalItem> masterApprovalList = FXCollections.observableArrayList();
    private final ObservableList<ApprovalItem> filteredApprovalList = FXCollections.observableArrayList();

    private VBox approvalCardsContainer;
    private TextField searchField;
    private ComboBox<String> typeFilterCombo;
    private ComboBox<String> statusFilterCombo;

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
    private static final String COLOR_DANGER = "#DC2626";
    private static final String COLOR_DANGER_BG = "#FEE2E2";
    private static final String COLOR_WARNING = "#D97706";
    private static final String COLOR_WARNING_BG = "#FEF3C7";
    private static final String COLOR_INFO = "#0284C7";

    public AsmApprovals() {
        initSampleApprovals();
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

        // Header
        HBox headerBox = createHeaderBox();

        // KPI Summary
        HBox kpiBox = createApprovalKPIs();

        // Filter Bar
        HBox filterCard = createFilterBar();

        // Approval List Container
        approvalCardsContainer = new VBox(12);
        applyFilters();

        container.getChildren().addAll(headerBox, kpiBox, filterCard, approvalCardsContainer);
        return container;
    }

    private HBox createHeaderBox() {
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(3);
        Label title = new Label("Manager Approval Center");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web(COLOR_ON_SURFACE));

        Label subtitle = new Label(
                "Authorize field expense claims, sample allocations, leave requests, and monthly tour itineraries.");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitle.setTextFill(Color.web(COLOR_SECONDARY));
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button batchApproveBtn = new Button("Approve All Pending");
        batchApproveBtn.setPrefHeight(38);
        batchApproveBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        batchApproveBtn.setStyle("-fx-background-color: " + COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-padding: 0 16px; -fx-background-radius: 8px; -fx-cursor: hand;");
        batchApproveBtn.setOnAction(e -> {
            for (ApprovalItem item : masterApprovalList) {
                if ("Pending".equalsIgnoreCase(item.status)) {
                    item.status = "Approved";
                }
            }
            applyFilters();
            showInfoAlert("Batch Approval", "All pending requests approved.");
        });

        header.getChildren().addAll(titleBox, spacer, batchApproveBtn);
        return header;
    }

    private HBox createApprovalKPIs() {
        HBox box = new HBox(16);

        VBox kpi1 = createMiniKPICard("Pending Requests", "3 Items", "Requires Action", COLOR_PRIMARY);
        VBox kpi2 = createMiniKPICard("Approved This Month", "14 Claims", "92% Approval Rate", COLOR_SUCCESS);
        VBox kpi3 = createMiniKPICard("Total Budget Value", "$3,420", "Within Allocation", COLOR_INFO);

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
        searchField.setPromptText("Search rep or request notes...");
        searchField.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_ON_SURFACE + ";");
        searchField.textProperty().addListener((obs, oldV, newV) -> applyFilters());
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchBox.getChildren().addAll(sIcon, searchField);

        typeFilterCombo = new ComboBox<>();
        typeFilterCombo.getItems().addAll("All Types", "Sample Request", "Expense Claim", "Tour Plan", "Leave Request");
        typeFilterCombo.setValue("All Types");
        typeFilterCombo.setPrefHeight(38);
        typeFilterCombo.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + COLOR_OUTLINE_VARIANT
                + "; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-cursor: hand;");
        typeFilterCombo.setOnAction(e -> applyFilters());

        statusFilterCombo = new ComboBox<>();
        statusFilterCombo.getItems().addAll("All Statuses", "Pending", "Approved", "Rejected");
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
            typeFilterCombo.setValue("All Types");
            statusFilterCombo.setValue("All Statuses");
            applyFilters();
        });

        card.getChildren().addAll(searchBox, typeFilterCombo, statusFilterCombo, spacer, clearBtn);
        return card;
    }

    private void applyFilters() {
        if (approvalCardsContainer == null)
            return;
        approvalCardsContainer.getChildren().clear();

        String search = searchField != null ? searchField.getText().toLowerCase().trim() : "";
        String typeF = typeFilterCombo != null ? typeFilterCombo.getValue() : "All Types";
        String statusF = statusFilterCombo != null ? statusFilterCombo.getValue() : "All Statuses";

        List<ApprovalItem> filtered = masterApprovalList.stream().filter(item -> {
            boolean matchesSearch = search.isEmpty() ||
                    item.repName.toLowerCase().contains(search) ||
                    item.territory.toLowerCase().contains(search) ||
                    item.description.toLowerCase().contains(search);

            boolean matchesType = "All Types".equals(typeF) || item.type.equalsIgnoreCase(typeF);
            boolean matchesStatus = "All Statuses".equals(statusF) || item.status.equalsIgnoreCase(statusF);

            return matchesSearch && matchesType && matchesStatus;
        }).collect(Collectors.toList());

        for (ApprovalItem item : filtered) {
            approvalCardsContainer.getChildren().add(createApprovalCard(item));
        }
    }

    private VBox createApprovalCard(ApprovalItem item) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(16));
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: " + COLOR_OUTLINE_VARIANT + ";" +
                        "-fx-border-radius: 12px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        HBox topBox = new HBox(12);
        topBox.setAlignment(Pos.CENTER_LEFT);

        StackPane iconPane = new StackPane();
        iconPane.setPrefSize(42, 42);
        iconPane.setStyle("-fx-background-color: " + COLOR_SURFACE_LOW + "; -fx-background-radius: 10px;");
        Label iconLbl = new Label(item.icon.isEmpty() ? "A" : item.icon);
        iconLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        iconPane.getChildren().add(iconLbl);

        VBox repInfo = new VBox(2);
        Label nameLbl = new Label(item.repName + " • " + item.territory);
        nameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        nameLbl.setTextFill(Color.web(COLOR_ON_SURFACE));

        Label typeLbl = new Label(item.type + " • " + item.timeAgo);
        typeLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        typeLbl.setTextFill(Color.web(COLOR_SECONDARY));
        repInfo.getChildren().addAll(nameLbl, typeLbl);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label statusBadge = new Label(item.status);
        statusBadge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        statusBadge.setPadding(new Insets(4, 10, 4, 10));
        if ("Approved".equalsIgnoreCase(item.status)) {
            statusBadge.setStyle("-fx-background-color: " + COLOR_SUCCESS_BG + "; -fx-text-fill: " + COLOR_SUCCESS
                    + "; -fx-background-radius: 20px;");
        } else if ("Rejected".equalsIgnoreCase(item.status)) {
            statusBadge.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #DC2626; -fx-background-radius: 20px;");
        } else {
            statusBadge.setStyle("-fx-background-color: #FEF3C7; -fx-text-fill: #D97706; -fx-background-radius: 20px;");
        }

        topBox.getChildren().addAll(iconPane, repInfo, sp, statusBadge);

        Label detailsLbl = new Label(item.description);
        detailsLbl.setWrapText(true);
        detailsLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        detailsLbl.setTextFill(Color.web(COLOR_ON_SURFACE));

        HBox actionBtns = new HBox(10);
        actionBtns.setAlignment(Pos.CENTER_RIGHT);

        if ("Pending".equalsIgnoreCase(item.status)) {
            Button rejectBtn = new Button("Reject");
            rejectBtn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
            rejectBtn.setStyle("-fx-background-color: white; -fx-border-color: #CBD5E1; -fx-text-fill: #64748B"
                    + "; -fx-background-radius: 6px; -fx-border-radius: 6px; -fx-cursor: hand;");
            rejectBtn.setOnAction(e -> {
                item.status = "Rejected";
                applyFilters();
            });

            Button approveBtn = new Button("Approve");
            approveBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            approveBtn.setStyle("-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white"
                    + "; -fx-background-radius: 6px; -fx-cursor: hand;");
            approveBtn.setOnAction(e -> {
                item.status = "Approved";
                applyFilters();
            });

            actionBtns.getChildren().addAll(rejectBtn, approveBtn);
        }

        card.getChildren().addAll(topBox, detailsLbl, actionBtns);
        return card;
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
                createSidebarItem("Approvals", "", true),
                createSidebarItem("Reports & Analytics", "", false),
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
        btn.setOnAction(e -> {
            // Logic handled by calling class or main controller
        });
        return btn;
    }

    private HBox createTopNavBar() {
        HBox topBar = new HBox(16);
        topBar.setPrefHeight(64);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 24, 0, 24));
        topBar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); -fx-border-color: transparent transparent "
                + COLOR_OUTLINE_VARIANT + " transparent; -fx-border-width: 0 0 1 0;");

        Label searchLbl = new Label("Area Sales Manager • Approvals");
        searchLbl.setTextFill(Color.web(COLOR_SECONDARY));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button notifBtn = new Button("N");
        notifBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

        topBar.getChildren().addAll(searchLbl, spacer, notifBtn);
        return topBar;
    }

    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void initSampleApprovals() {
        masterApprovalList.clear();
        masterApprovalList.add(new ApprovalItem("REQ", "Sample Request", "Alex Mercer", "Central District",
                "50x CardioPro 50mg Samples for Metro General Cardiology trial", "Requested 2h ago", "Pending"));
        masterApprovalList.add(new ApprovalItem("EXP", "Expense Claim", "Sarah Connor", "North Sector",
                "$140 Outstation Fuel & Inter-city Travel Allowance voucher", "Requested 5h ago", "Pending"));
        masterApprovalList.add(new ApprovalItem("TRP", "Tour Plan", "David Miller", "East Sector",
                "Monthly Tour Program covering 18 clinics in East District", "Requested 1d ago", "Pending"));
        masterApprovalList.add(new ApprovalItem("LVE", "Leave Request", "Robert Chen", "South Hub",
                "Personal Medical Leave (2 Days: 18-19 Aug)", "Requested 2d ago", "Approved"));
        masterApprovalList.add(new ApprovalItem("BGT", "Doctor Meeting Budget", "Priya Sharma", "West Zone",
                "$250 Clinical Breakfast Meeting with 6 Pediatricians", "Requested 3d ago", "Approved"));
    }

    public static class ApprovalItem {
        public String icon;
        public String type;
        public String repName;
        public String territory;
        public String description;
        public String timeAgo;
        public String status;

        public ApprovalItem(String icon, String type, String repName, String territory,
                String description, String timeAgo, String status) {
            this.icon = icon;
            this.type = type;
            this.repName = repName;
            this.territory = territory;
            this.description = description;
            this.timeAgo = timeAgo;
            this.status = status;
        }
    }
}
