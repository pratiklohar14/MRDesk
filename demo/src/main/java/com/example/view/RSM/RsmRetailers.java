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
 * RsmRetailers - MedTrack Pro (RSM Portal)
 * Dedicated view for Active Retailers & Pharmacy Network Database.
 */
public class RsmRetailers {

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
        VBox sidebar = new RsmDashBoard().createSideNavBar(primaryStage, "Retailers");
        root.setLeft(sidebar);

        // Main Body
        VBox mainBox = new VBox(0);

        HBox topBar = new HBox(16);
        topBar.setPrefHeight(72);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 28, 0, 28));
        topBar.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: transparent transparent " + COLOR_BORDER + " transparent; -fx-border-width: 0 0 1 0;");

        Label title = new Label("Regional Active Retailers & Pharmacy Directory");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setTextFill(Color.web(COLOR_TEXT_MAIN));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        // Retailers Roster Table Card
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 14px; -fx-border-color: " + COLOR_BORDER + "; -fx-border-radius: 14px;");

        Button addRetailerBtn = new Button("+ Add New Retailer");
        addRetailerBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        addRetailerBtn.setStyle("-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white; -fx-background-radius: 8px; -fx-padding: 8px 18px; -fx-cursor: hand;");
        addRetailerBtn.setOnAction(e -> handleAddRetailer(card));

        topBar.getChildren().addAll(title, sp, addRetailerBtn);
        mainBox.getChildren().add(topBar);

        // Content Scroll
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: " + COLOR_BG_CANVAS + "; -fx-background: " + COLOR_BG_CANVAS + "; -fx-border-color: transparent;");

        VBox container = new VBox(20);
        container.setPadding(new Insets(24, 28, 28, 28));

        Label cardTitle = new Label("Active Pharmacy Outlets (1,248 Total)");
        cardTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        cardTitle.setTextFill(Color.web(COLOR_TEXT_MAIN));
        card.getChildren().add(cardTitle);

        String[][] retailerRows = {
                {"Sharma Traders", "Lucknow Central", "Ramesh Sharma", "9876543210", "Rs. 4.2 Lakh", "Verified"},
                {"Apollo Pharmacy #14", "Kanpur Mall", "Dr. A. Verma", "9876543211", "Rs. 6.8 Lakh", "Verified"},
                {"MedPlus Chemists", "Varanasi Chowk", "Sunil Gupta", "9876543212", "Rs. 5.1 Lakh", "Verified"},
                {"Care & Cure Medical", "Agra Fort Area", "Vikas Malhotra", "9876543213", "Rs. 3.9 Lakh", "Verified"},
                {"National Drug House", "Allahabad City", "Prakash Yadav", "9876543214", "Rs. 4.7 Lakh", "Verified"}
        };

        HBox tHead = new HBox(12);
        tHead.setPadding(new Insets(8, 12, 8, 12));
        tHead.setStyle("-fx-background-color: #F3E8FF; -fx-background-radius: 8px;");

        Label th1 = new Label("Retailer Name"); th1.setPrefWidth(160); th1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th2 = new Label("Location Area"); th2.setPrefWidth(140); th2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th3 = new Label("Owner / Contact"); th3.setPrefWidth(140); th3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th4 = new Label("Phone"); th4.setPrefWidth(120); th4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th5 = new Label("Monthly Orders"); th5.setPrefWidth(120); th5.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label th6 = new Label("Status"); th6.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));

        tHead.getChildren().addAll(th1, th2, th3, th4, th5, th6);
        card.getChildren().add(tHead);

        for (String[] r : retailerRows) {
            HBox row = new HBox(12);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(10, 12, 10, 12));

            Label l1 = new Label(r[0]); l1.setPrefWidth(160); l1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
            Label l2 = new Label(r[1]); l2.setPrefWidth(140); l2.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
            Label l3 = new Label(r[2]); l3.setPrefWidth(140); l3.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
            Label l4 = new Label(r[3]); l4.setPrefWidth(120); l4.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
            Label l5 = new Label(r[4]); l5.setPrefWidth(120); l5.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));

            Label l6 = new Label(r[5]);
            l6.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
            l6.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #16A34A; -fx-padding: 3 8; -fx-background-radius: 6px;");

            row.getChildren().addAll(l1, l2, l3, l4, l5, l6);
            card.getChildren().add(row);
        }

        container.getChildren().add(card);
        scroll.setContent(container);

        VBox.setVgrow(scroll, Priority.ALWAYS);
        double w = (primaryStage != null && primaryStage.getWidth() > 0) ? primaryStage.getWidth() : 1360;
        double h = (primaryStage != null && primaryStage.getHeight() > 0) ? primaryStage.getHeight() : 860;
        mainScene = new Scene(root, w, h);
        return mainScene;
    }

    private void handleAddRetailer(VBox tableCard) {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Add New Retailer");
        dialog.setHeaderText("Enter details for the new pharmacy outlet:");

        ButtonType addButtonType = new ButtonType("Add Retailer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameF = new TextField();
        nameF.setPromptText("Retailer Name");
        TextField locationF = new TextField();
        locationF.setPromptText("Location Area");
        TextField ownerF = new TextField();
        ownerF.setPromptText("Owner / Contact");
        TextField phoneF = new TextField();
        phoneF.setPromptText("Phone Number");
        TextField ordersF = new TextField();
        ordersF.setPromptText("Monthly Orders (e.g. Rs. 4.5 Lakh)");

        grid.add(new Label("Retailer Name:"), 0, 0);
        grid.add(nameF, 1, 0);
        grid.add(new Label("Location:"), 0, 1);
        grid.add(locationF, 1, 1);
        grid.add(new Label("Owner:"), 0, 2);
        grid.add(ownerF, 1, 2);
        grid.add(new Label("Phone:"), 0, 3);
        grid.add(phoneF, 1, 3);
        grid.add(new Label("Orders:"), 0, 4);
        grid.add(ordersF, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new String[]{
                        nameF.getText().isEmpty() ? "New Retailer" : nameF.getText(),
                        locationF.getText().isEmpty() ? "Regional District" : locationF.getText(),
                        ownerF.getText().isEmpty() ? "Owner Name" : ownerF.getText(),
                        phoneF.getText().isEmpty() ? "9876543219" : phoneF.getText(),
                        ordersF.getText().isEmpty() ? "Rs. 0.0 Lakh" : ordersF.getText(),
                        "Verified"
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
            Label l3 = new Label(r[2]); l3.setPrefWidth(140); l3.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
            Label l4 = new Label(r[3]); l4.setPrefWidth(120); l4.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
            Label l5 = new Label(r[4]); l5.setPrefWidth(120); l5.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));

            Label l6 = new Label(r[5]);
            l6.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
            l6.setStyle("-fx-background-color: #DCFCE7; -fx-text-fill: #16A34A; -fx-padding: 3 8; -fx-background-radius: 6px;");

            row.getChildren().addAll(l1, l2, l3, l4, l5, l6);
        });
    }
}
