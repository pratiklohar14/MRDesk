package com.example.view.MR;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MrDailySchedule {

    public VBox createPage() {
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

    private VBox createDetailedScheduleItem(String time, String docName, String spec, String location,
            String agenda, String statusStr, String statusColor, String statusBg) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-width: 1px;");

        HBox top = new HBox(14);
        top.setAlignment(Pos.CENTER_LEFT);

        Label timeLbl = new Label(time);
        timeLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        timeLbl.setTextFill(Color.web("#2563EB"));
        timeLbl.setPrefWidth(85);

        VBox docDetails = new VBox(2);
        Label nameLbl = new Label(docName);
        nameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        nameLbl.setTextFill(Color.web("#0F172A"));

        Label specLbl = new Label(spec + " • " + location);
        specLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        specLbl.setTextFill(Color.web("#64748B"));
        docDetails.getChildren().addAll(nameLbl, specLbl);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label badge = new Label(statusStr);
        badge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        badge.setTextFill(Color.web(statusColor));
        badge.setStyle("-fx-background-color: " + statusBg + "; -fx-padding: 4px 12px; -fx-background-radius: 20px;");

        top.getChildren().addAll(timeLbl, docDetails, sp, badge);

        HBox bot = new HBox(12);
        bot.setAlignment(Pos.CENTER_LEFT);

        Label agendaLbl = new Label("Agenda: " + agenda);
        agendaLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        agendaLbl.setTextFill(Color.web("#475569"));

        Region sp2 = new Region();
        HBox.setHgrow(sp2, Priority.ALWAYS);

        Button startBtn = createStyledButton("Start Visit Call", "#2563EB", "white");
        bot.getChildren().addAll(agendaLbl, sp2, startBtn);

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
