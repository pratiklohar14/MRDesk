package com.example.view.MR;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class MrDoctors {

    public VBox createPage() {
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

    private VBox createDoctorCard(String name, String spec, String hospital, String visits, String phone,
            String colorHex) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-width: 1px;");

        HBox top = new HBox(12);
        top.setAlignment(Pos.CENTER_LEFT);

        StackPane avatar = new StackPane();
        Circle bgCircle = new Circle(22, Color.web(colorHex, 0.15));
        Label initials = new Label(name.replace("Dr. ", "").substring(0, 1));
        initials.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        initials.setTextFill(Color.web(colorHex));
        avatar.getChildren().addAll(bgCircle, initials);

        VBox details = new VBox(2);
        Label nameLbl = new Label(name);
        nameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        nameLbl.setTextFill(Color.web("#0F172A"));

        Label specLbl = new Label(spec + " • " + hospital);
        specLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        specLbl.setTextFill(Color.web("#64748B"));

        details.getChildren().addAll(nameLbl, specLbl);
        top.getChildren().addAll(avatar, details);

        HBox bot = new HBox(10);
        bot.setAlignment(Pos.CENTER_LEFT);

        Label visitLbl = new Label("📊 " + visits);
        visitLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        visitLbl.setTextFill(Color.web("#475569"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button callBtn = createStyledButton("📞 Call", "#F1F5F9", "#334155");
        Button scheduleBtn = createStyledButton("📅 Schedule", "#2563EB", "white");

        bot.getChildren().addAll(visitLbl, sp, callBtn, scheduleBtn);
        card.getChildren().addAll(top, bot);
        return card;
    }

    private Button createPillButton(String text, boolean active) {
        Button btn = new Button(text);
        if (active) {
            btn.setStyle(
                    "-fx-font-family: 'Segoe UI'; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #2563EB; -fx-background-radius: 20px; -fx-padding: 6px 14px; -fx-cursor: hand;");
        } else {
            btn.setStyle(
                    "-fx-font-family: 'Segoe UI'; -fx-font-size: 12px; -fx-font-weight: 500; -fx-text-fill: #64748B; -fx-background-color: #F1F5F9; -fx-background-radius: 20px; -fx-padding: 6px 14px; -fx-cursor: hand;");
        }
        return btn;
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
