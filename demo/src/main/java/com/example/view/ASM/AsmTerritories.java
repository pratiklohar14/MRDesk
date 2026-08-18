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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AsmTerritories - MedTrack Pro (ASM Portal)
 * Dedicated view for managing Zone territories, assigning field
 * representatives,
 * and monitoring hospital / clinic market penetration.
 */
public class AsmTerritories {

    private Stage primaryStage;
    private Scene mainScene;
    private final ObservableList<TerritoryItem> masterTerrList = FXCollections.observableArrayList();
    private final ObservableList<TerritoryItem> filteredTerrList = FXCollections.observableArrayList();

    private GridPane terrGrid;
    private TextField searchField;

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

    public AsmTerritories() {
        initSampleTerritories();
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

        // Territory KPI Overview
        HBox kpiBox = createTerritoryKPIs();

        // Filter Bar
        HBox filterCard = createFilterBar();

        // Territories Grid
        terrGrid = new GridPane();
        terrGrid.setHgap(16);
        terrGrid.setVgap(16);
        applyFilters();

        container.getChildren().addAll(headerBox, kpiBox, filterCard, terrGrid);
        return container;
    }

    private HBox createHeaderBox() {
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(3);
        Label title = new Label("Territory & District Management");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web(COLOR_ON_SURFACE));

        Label subtitle = new Label("Configure geographic boundaries, hospital accounts, and sales quotas.");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitle.setTextFill(Color.web(COLOR_SECONDARY));
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addTerrBtn = new Button("Add District Zone");
        addTerrBtn.setPrefHeight(38);
        addTerrBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        addTerrBtn.setStyle("-fx-background-color: " + COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-padding: 0 16px; -fx-background-radius: 8px; -fx-cursor: hand;");
        addTerrBtn.setOnAction(e -> openAddTerritoryDialog());

        header.getChildren().addAll(titleBox, spacer, addTerrBtn);
        return header;
    }

    private HBox createTerritoryKPIs() {
        HBox box = new HBox(16);

        VBox kpi1 = createMiniKPICard("Total Districts", "5 Zones", "100% Coverage Active", COLOR_PRIMARY);
        VBox kpi2 = createMiniKPICard("Key Medical Hubs", "28 Hospitals", "Affiliated Clinics & OPDs", COLOR_SUCCESS);
        VBox kpi3 = createMiniKPICard("Total Regional Quota", "$336,000", "80.8% YTD Realization", COLOR_INFO);

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

    private static final String COLOR_WARNING = "#D97706";

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
        searchField.setPromptText("Search district, lead rep, or hospital...");
        searchField.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_ON_SURFACE + ";");
        searchField.textProperty().addListener((obs, oldV, newV) -> applyFilters());
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchBox.getChildren().addAll(sIcon, searchField);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button clearBtn = new Button("Clear Filter");
        clearBtn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: " + COLOR_PRIMARY + "; -fx-cursor: hand;");
        clearBtn.setOnAction(e -> {
            searchField.clear();
            applyFilters();
        });

        card.getChildren().addAll(searchBox, spacer, clearBtn);
        return card;
    }

    private void applyFilters() {
        if (terrGrid == null)
            return;
        terrGrid.getChildren().clear();

        String search = searchField != null ? searchField.getText().toLowerCase().trim() : "";

        List<TerritoryItem> filtered = masterTerrList.stream().filter(item -> {
            return search.isEmpty() ||
                    item.districtName.toLowerCase().contains(search) ||
                    item.leadRep.toLowerCase().contains(search) ||
                    item.keyHospitals.toLowerCase().contains(search);
        }).collect(Collectors.toList());

        int col = 0;
        int row = 0;
        for (TerritoryItem item : filtered) {
            VBox card = createTerritoryCard(item);
            GridPane.setHgrow(card, Priority.ALWAYS);
            terrGrid.add(card, col, row);

            col++;
            if (col >= 2) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createTerritoryCard(TerritoryItem item) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(18));
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: " + COLOR_OUTLINE_VARIANT + ";" +
                        "-fx-border-radius: 12px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        HBox topBox = new HBox(8);
        topBox.setAlignment(Pos.CENTER_LEFT);

        Label dName = new Label(item.districtName);
        dName.setFont(Font.font("Segoe UI", FontWeight.BOLD, 17));
        dName.setTextFill(Color.web(COLOR_ON_SURFACE));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label repPill = new Label("Lead: " + item.leadRep);
        repPill.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        repPill.setPadding(new Insets(3, 8, 3, 8));
        repPill.setStyle("-fx-background-color: " + COLOR_SURFACE_LOW + "; -fx-text-fill: " + COLOR_PRIMARY
                + "; -fx-background-radius: 6px;");

        topBox.getChildren().addAll(dName, sp, repPill);

        Label hospLbl = new Label("Key Hubs: " + item.keyHospitals);
        hospLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        hospLbl.setTextFill(Color.web(COLOR_SECONDARY));

        HBox statsRow = new HBox(16);
        Label s1 = new Label("Doctors: " + item.doctorCount);
        s1.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        s1.setTextFill(Color.web(COLOR_ON_SURFACE));

        Label s2 = new Label("Hospitals: " + item.hospitalCount);
        s2.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        s2.setTextFill(Color.web(COLOR_ON_SURFACE));

        Label s3 = new Label("Coverage: " + item.coveragePct);
        s3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        s3.setTextFill(Color.web(COLOR_SUCCESS));

        statsRow.getChildren().addAll(s1, s2, s3);

        VBox progressBox = new VBox(4);
        ProgressBar pb = new ProgressBar(item.salesRatio);
        pb.setMaxWidth(Double.MAX_VALUE);
        pb.setStyle("-fx-accent: " + (item.salesRatio >= 0.80 ? COLOR_PRIMARY : COLOR_WARNING) + ";");

        HBox pLbls = new HBox();
        Label p1 = new Label("Sales Realization: " + item.currentSales + " / " + item.targetSales);
        p1.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        p1.setTextFill(Color.web(COLOR_SECONDARY));

        Region pSp = new Region();
        HBox.setHgrow(pSp, Priority.ALWAYS);

        Label p2 = new Label((int) (item.salesRatio * 100) + "%");
        p2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        p2.setTextFill(Color.web(COLOR_ON_SURFACE));

        pLbls.getChildren().addAll(p1, pSp, p2);
        progressBox.getChildren().addAll(pb, pLbls);

        card.getChildren().addAll(topBox, hospLbl, statsRow, progressBox);

        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: #F8FAFC;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: " + COLOR_PRIMARY + ";" +
                        "-fx-border-radius: 12px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(5,150,105,0.08), 10, 0, 0, 3);"));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: " + COLOR_OUTLINE_VARIANT + ";" +
                        "-fx-border-radius: 12px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 8, 0, 0, 2);"));

        return card;
    }

    private void openAddTerritoryDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Add Territory District");

        VBox root = new VBox(14);
        root.setPadding(new Insets(24));
        root.setStyle("-fx-background-color: #FFFFFF;");

        Label title = new Label("Add Territory Zone");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

        TextField nameInput = new TextField();
        nameInput.setPromptText("District Name (e.g. Northeast Sector)");

        TextField repInput = new TextField();
        repInput.setPromptText("Assigned Lead MR");

        TextField hospInput = new TextField();
        hospInput.setPromptText("Key Hospitals (e.g. City Care, St. Mary)");

        TextField targetInput = new TextField();
        targetInput.setPromptText("Monthly Quota ($ e.g. 30000)");

        HBox btnBox = new HBox(12);
        btnBox.setAlignment(Pos.CENTER_RIGHT);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: " + COLOR_SECONDARY + "; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> dialog.close());

        Button saveBtn = new Button("Save District");
        saveBtn.setStyle("-fx-background-color: " + COLOR_PRIMARY
                + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-cursor: hand;");
        saveBtn.setOnAction(e -> {
            String name = nameInput.getText().trim();
            if (name.isEmpty())
                return;

            masterTerrList.add(new TerritoryItem(name, repInput.getText().trim(), hospInput.getText().trim(), 45, 8,
                    "85%", "$0", "$30,000", 0.0));
            applyFilters();
            dialog.close();
            showInfoAlert("Territory Added", name + " registered under North Zone.");
        });

        btnBox.getChildren().addAll(cancelBtn, saveBtn);

        root.getChildren().addAll(
                title,
                new Label("District Name:"), nameInput,
                new Label("Lead Representative:"), repInput,
                new Label("Major Hospital Hubs:"), hospInput,
                new Label("Monthly Revenue Target:"), targetInput,
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
                createSidebarItem("My Team (MRs)", "", false),
                createSidebarItem("Territories", "", true),
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

        Label searchLbl = new Label("Area Sales Manager • Territory Distribution & Hubs");
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

    private void initSampleTerritories() {
        masterTerrList.clear();
        masterTerrList.add(new TerritoryItem("Central District", "Alex Mercer",
                "Metro General Hospital, St. Jude Medical Center", 112, 14, "94.2%", "$34,000", "$38,000", 0.895));
        masterTerrList.add(new TerritoryItem("North Sector", "Sarah Connor",
                "Cityville North Clinic, Memorial Pavilion", 88, 11, "91.0%", "$28,500", "$32,000", 0.890));
        masterTerrList.add(new TerritoryItem("East Sector", "David Miller",
                "Eastside Orthopedic, Children's Specialty Hospital", 76, 9, "89.5%", "$22,400", "$26,000", 0.861));
        masterTerrList.add(new TerritoryItem("West Zone", "Priya Sharma", "West End Healthcare, Neurological Institute",
                72, 8, "88.0%", "$19,500", "$24,000", 0.812));
    }

    public static class TerritoryItem {
        public String districtName;
        public String leadRep;
        public String keyHospitals;
        public int doctorCount;
        public int hospitalCount;
        public String coveragePct;
        public String currentSales;
        public String targetSales;
        public double salesRatio;

        public TerritoryItem(String districtName, String leadRep, String keyHospitals,
                int doctorCount, int hospitalCount, String coveragePct,
                String currentSales, String targetSales, double salesRatio) {
            this.districtName = districtName;
            this.leadRep = leadRep;
            this.keyHospitals = keyHospitals;
            this.doctorCount = doctorCount;
            this.hospitalCount = hospitalCount;
            this.coveragePct = coveragePct;
            this.currentSales = currentSales;
            this.targetSales = targetSales;
            this.salesRatio = salesRatio;
        }
    }
}
