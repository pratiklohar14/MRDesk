package com.example.view.MR;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MrClinics {

    public VBox createPage() {
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

    private VBox createHospitalCard(String name, String type, String address, String docs, String visits,
            String colorHex) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-width: 1px;");

        HBox top = new HBox(12);
        top.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("🏥");
        icon.setFont(Font.font(24));

        VBox details = new VBox(2);
        Label nameLbl = new Label(name);
        nameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        nameLbl.setTextFill(Color.web("#0F172A"));

        Label subLbl = new Label(type + " • " + address);
        subLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        subLbl.setTextFill(Color.web("#64748B"));

        details.getChildren().addAll(nameLbl, subLbl);
        top.getChildren().addAll(icon, details);

        HBox bot = new HBox(16);
        bot.setAlignment(Pos.CENTER_LEFT);

        Label docsLbl = new Label("👥 " + docs);
        docsLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        docsLbl.setTextFill(Color.web("#475569"));

        Label visitsLbl = new Label("📅 " + visits);
        visitsLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        visitsLbl.setTextFill(Color.web("#475569"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button viewDocsBtn = createStyledButton("View Doctors", "#2563EB", "white");
        bot.getChildren().addAll(docsLbl, visitsLbl, sp, viewDocsBtn);

        card.getChildren().addAll(top, bot);
        return card;
    }

    private Button createStyledButton(String text, String bgColor, String textColor) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + textColor
                        + "; -fx-background-color: " + bgColor
                        + "; -fx-background-radius: 8px; -fx-padding: 8px 16px; -fx-cursor: hand;");
        return btn;
    }
}
