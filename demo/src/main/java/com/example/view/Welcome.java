package com.example.view;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class Welcome {

    public static Stage welcomeStage;
    private Scene welcomeScene;

    public static class App extends Application {
        @Override
        public void start(Stage stage) throws Exception {
            new Welcome().start(stage);
        }
    }

    public void start(Stage stage) throws Exception {
        welcomeStage = stage;

        StackPane rootStack = new StackPane();

        // -------------------------------------------------------------
        // 1. MAIN WELCOME CONTENT BORDERPANE
        // -------------------------------------------------------------
        BorderPane mainContent = createMainContent();
        mainContent.setOpacity(0); // initially invisible for splash transition

        // -------------------------------------------------------------
        // 2. FLASH / SPLASH SCREEN OVERLAY
        // -------------------------------------------------------------
        StackPane splashPane = createSplashScreen(mainContent, rootStack);

        rootStack.getChildren().addAll(mainContent, splashPane);

        double w = (welcomeStage.getWidth() > 0 && !Double.isNaN(welcomeStage.getWidth())) ? welcomeStage.getWidth()
                : 1360;
        double h = (welcomeStage.getHeight() > 0 && !Double.isNaN(welcomeStage.getHeight())) ? welcomeStage.getHeight()
                : 860;
        Scene scene = new Scene(rootStack, w, h);
        welcomeScene = scene;

        welcomeStage.setTitle("MRDesk - Medical Representative Workspace");
        welcomeStage.setScene(welcomeScene);
        welcomeStage.setMaximized(true);
        welcomeStage.show();
    }

    private StackPane createSplashScreen(BorderPane mainContent, StackPane rootStack) {
        StackPane splash = new StackPane();
        splash.setStyle("-fx-background-color: linear-gradient(to bottom right, #0F172A, #1E293B, #0F172A);");

        // Background glowing orbs
        Circle bgOrb1 = new Circle(280, Color.web("#2563EB", 0.18));
        bgOrb1.setTranslateX(-300);
        bgOrb1.setTranslateY(-180);

        Circle bgOrb2 = new Circle(240, Color.web("#06B6D4", 0.15));
        bgOrb2.setTranslateX(350);
        bgOrb2.setTranslateY(200);

        // Center Content Box
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(480);

        // Logo with Glow Effect
        ImageView logoView = null;
        URL logoUrl = getClass().getResource("/assets/images/logo.png");
        if (logoUrl != null) {
            Image logoImg = new Image(logoUrl.toExternalForm());
            logoView = new ImageView(logoImg);
            logoView.setFitWidth(120);
            logoView.setFitHeight(120);
            logoView.setPreserveRatio(true);

            DropShadow logoGlow = new DropShadow();
            logoGlow.setColor(Color.web("#38BDF8", 0.6));
            logoGlow.setRadius(30);
            logoGlow.setSpread(0.2);
            logoView.setEffect(logoGlow);
        }

        // Animated pulse scale for logo
        if (logoView != null) {
            ScaleTransition pulse = new ScaleTransition(Duration.seconds(1.2), logoView);
            pulse.setFromX(0.92);
            pulse.setFromY(0.92);
            pulse.setToX(1.05);
            pulse.setToY(1.05);
            pulse.setAutoReverse(true);
            pulse.setCycleCount(Timeline.INDEFINITE);
            pulse.play();
        }

        Label splashTitle = new Label("MRDesk");
        splashTitle.setStyle(
                "-fx-font-family: 'Segoe UI', Arial, sans-serif;" +
                        "-fx-font-size: 44px;" +
                        "-fx-font-weight: 900;" +
                        "-fx-text-fill: linear-gradient(to right, #38BDF8, #60A5FA);");

        Label splashTagline = new Label("Medical Representative Work & Schedule Management");
        splashTagline.setStyle(
                "-fx-font-family: 'Segoe UI', Arial, sans-serif;" +
                        "-fx-font-size: 15px;" +
                        "-fx-text-fill: #94A3B8;" +
                        "-fx-font-weight: 500;");

        // Progress Bar
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(320);
        progressBar.setPrefHeight(8);
        progressBar.setStyle(
                "-fx-accent: #38BDF8;" +
                        "-fx-control-inner-background: rgba(255, 255, 255, 0.1);" +
                        "-fx-background-radius: 10px;" +
                        "-fx-padding: 0;");

        Label statusLabel = new Label("Initializing MRDesk Workspace...");
        statusLabel.setStyle("-fx-font-family: 'Segoe UI', Arial; -fx-font-size: 13px; -fx-text-fill: #64748B;");

        if (logoView != null) {
            contentBox.getChildren().add(logoView);
        }
        contentBox.getChildren().addAll(splashTitle, splashTagline, progressBar, statusLabel);

        splash.getChildren().addAll(bgOrb1, bgOrb2, contentBox);

        // Timeline for Loading Simulation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(progressBar.progressProperty(), 0.0),
                        new KeyValue(statusLabel.textProperty(), "Initializing MRDesk Workspace...")),
                new KeyFrame(Duration.millis(600),
                        new KeyValue(progressBar.progressProperty(), 0.35),
                        new KeyValue(statusLabel.textProperty(), "Loading visit schedules & doctor list...")),
                new KeyFrame(Duration.millis(1200),
                        new KeyValue(progressBar.progressProperty(), 0.75),
                        new KeyValue(statusLabel.textProperty(), "Preparing medical representative dashboard...")),
                new KeyFrame(Duration.millis(1800),
                        new KeyValue(progressBar.progressProperty(), 1.0),
                        new KeyValue(statusLabel.textProperty(), "Workspace Ready!")));

        timeline.setOnFinished(e -> {
            // Fade out splash and fade in main content
            FadeTransition fadeSplash = new FadeTransition(Duration.millis(500), splash);
            fadeSplash.setFromValue(1.0);
            fadeSplash.setToValue(0.0);

            FadeTransition fadeMain = new FadeTransition(Duration.millis(600), mainContent);
            fadeMain.setFromValue(0.0);
            fadeMain.setToValue(1.0);

            TranslateTransition slideMain = new TranslateTransition(Duration.millis(600), mainContent);
            slideMain.setFromY(20);
            slideMain.setToY(0);

            ParallelTransition transition = new ParallelTransition(fadeSplash, fadeMain, slideMain);
            transition.setOnFinished(evt -> rootStack.getChildren().remove(splash));
            transition.play();
        });

        timeline.play();

        return splash;
    }

    private BorderPane createMainContent() {
        BorderPane root = new BorderPane();

        // Set Background Image with subtle overlay tint
        URL bgResource = getClass().getResource("/assets/images/welcome_Background.png");
        if (bgResource != null) {
            root.setStyle(
                    "-fx-background-image: url('" + bgResource.toExternalForm() + "');" +
                            "-fx-background-size: cover;" +
                            "-fx-background-position: center center;" +
                            "-fx-background-repeat: no-repeat;");
        } else {
            root.setStyle("-fx-background-color: linear-gradient(to bottom right, #F8FAFC, #EFF6FF);");
        }

        // -------------------------------------------------------------
        // TOP NAVBAR
        // -------------------------------------------------------------
        HBox topNavbar = createTopNavbar();
        root.setTop(topNavbar);

        // -------------------------------------------------------------
        // CENTER HERO & CARD LAYOUT (OPTIMIZED FOR MAXIMIZED SCREEN)
        // -------------------------------------------------------------
        StackPane centerWrapper = new StackPane();
        centerWrapper.setPadding(new Insets(30, 60, 40, 60));
        centerWrapper.setAlignment(Pos.CENTER);

        HBox mainContainer = new HBox(50);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setMaxWidth(1200); // Prevents over-stretching on 4K / Ultra-wide monitors

        // LEFT HERO SECTION
        VBox leftHero = createLeftHeroSection();
        HBox.setHgrow(leftHero, Priority.ALWAYS);

        // RIGHT ACTION CARD
        VBox rightCard = createRightActionCard();

        mainContainer.getChildren().addAll(leftHero, rightCard);
        centerWrapper.getChildren().add(mainContainer);

        root.setCenter(centerWrapper);

        return root;
    }

    private HBox createTopNavbar() {
        HBox navbar = new HBox(15);
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(20, 60, 10, 60));

        // Brand Logo
        URL logoUrl = getClass().getResource("/assets/images/logo.png");
        if (logoUrl != null) {
            ImageView navLogo = new ImageView(new Image(logoUrl.toExternalForm()));
            navLogo.setFitWidth(38);
            navLogo.setFitHeight(38);
            navLogo.setPreserveRatio(true);
            navbar.getChildren().add(navLogo);
        }

        Label brandTitle = new Label("MRDesk");
        brandTitle.setStyle(
                "-fx-font-family: 'Segoe UI', Arial, sans-serif;" +
                        "-fx-font-size: 24px;" +
                        "-fx-font-weight: 800;" +
                        "-fx-text-fill: #0F172A;");

        Label versionBadge = new Label("ENTERPRISE v2.0");
        versionBadge.setStyle(
                "-fx-font-family: 'Segoe UI', Arial;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: 700;" +
                        "-fx-background-color: #E0F2FE;" +
                        "-fx-text-fill: #0284C7;" +
                        "-fx-padding: 4px 10px;" +
                        "-fx-background-radius: 12px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label statusPill = new Label("🟢 System Online");
        statusPill.setStyle(
                "-fx-font-family: 'Segoe UI', Arial;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: 600;" +
                        "-fx-text-fill: #15803D;" +
                        "-fx-background-color: #DCFCE7;" +
                        "-fx-padding: 6px 14px;" +
                        "-fx-background-radius: 20px;");

        navbar.getChildren().addAll(brandTitle, versionBadge, spacer, statusPill);
        return navbar;
    }

    private VBox createLeftHeroSection() {
        VBox hero = new VBox(24);
        hero.setAlignment(Pos.CENTER_LEFT);
        hero.setMaxWidth(650);

        // Platform Badge
        Label tagBadge = new Label("🚀  INTELLIGENT MEDICAL REPRESENTATIVE PLATFORM");
        tagBadge.setStyle(
                "-fx-font-family: 'Segoe UI', Arial;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: 700;" +
                        "-fx-text-fill: #2563EB;" +
                        "-fx-background-color: rgba(37, 99, 235, 0.1);" +
                        "-fx-padding: 6px 14px;" +
                        "-fx-background-radius: 20px;");

        // Main Headline
        Label headline = new Label("Organize Visits.\nTrack Follow-ups.\nWork Smarter.");
        headline.setStyle(
                "-fx-font-family: 'Segoe UI', Arial, sans-serif;" +
                        "-fx-font-size: 46px;" +
                        "-fx-font-weight: 900;" +
                        "-fx-text-fill: #0F172A;" +
                        "-fx-line-spacing: 2px;");

        // Subtitle Description
        Label subHeadline = new Label(
                "Empowering pharmaceutical sales & medical representatives with modern daily schedule planning, real-time doctor visit tracking, and automated client follow-ups.");
        subHeadline.setWrapText(true);
        subHeadline.setStyle(
                "-fx-font-family: 'Segoe UI', Arial, sans-serif;" +
                        "-fx-font-size: 16px;" +
                        "-fx-text-fill: #475569;" +
                        "-fx-line-spacing: 5px;");

        // Feature Highlights Cards (Grid / Row)
        HBox featureRow = new HBox(16);
        featureRow.setPadding(new Insets(10, 0, 10, 0));

        VBox fCard1 = createFeatureTile("📅", "Daily Planning", "Schedule doctor visits & route coverage");
        VBox fCard2 = createFeatureTile("📊", "Visit Analytics", "Track sample distribution & details");
        VBox fCard3 = createFeatureTile("🔔", "Auto Follow-ups", "Timely reminders for doctor requests");

        HBox.setHgrow(fCard1, Priority.ALWAYS);
        HBox.setHgrow(fCard2, Priority.ALWAYS);
        HBox.setHgrow(fCard3, Priority.ALWAYS);

        featureRow.getChildren().addAll(fCard1, fCard2, fCard3);

        // Trust metrics bar
        HBox metricsRow = new HBox(25);
        metricsRow.setAlignment(Pos.CENTER_LEFT);

        Label m1 = new Label("⚡ 99.9% Reliable");
        m1.setStyle("-fx-font-size: 13px; -fx-font-weight: 600; -fx-text-fill: #64748B;");

        Label m2 = new Label("🔒 End-to-End Encrypted");
        m2.setStyle("-fx-font-size: 13px; -fx-font-weight: 600; -fx-text-fill: #64748B;");

        Label m3 = new Label("🏥 Doctor CRM Integrated");
        m3.setStyle("-fx-font-size: 13px; -fx-font-weight: 600; -fx-text-fill: #64748B;");

        metricsRow.getChildren().addAll(m1, m2, m3);

        hero.getChildren().addAll(tagBadge, headline, subHeadline, featureRow, metricsRow);
        return hero;
    }

    private VBox createFeatureTile(String icon, String title, String desc) {
        VBox tile = new VBox(8);
        tile.setPadding(new Insets(16));
        tile.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.85);" +
                        "-fx-background-radius: 16px;" +
                        "-fx-border-color: rgba(226, 232, 240, 0.9);" +
                        "-fx-border-radius: 16px;" +
                        "-fx-border-width: 1px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(15, 23, 42, 0.05), 10, 0, 0, 4);");

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 24px;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle(
                "-fx-font-family: 'Segoe UI', Arial;" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: 700;" +
                        "-fx-text-fill: #1E293B;");

        Label descLabel = new Label(desc);
        descLabel.setWrapText(true);
        descLabel.setStyle(
                "-fx-font-family: 'Segoe UI', Arial;" +
                        "-fx-font-size: 12px;" +
                        "-fx-text-fill: #64748B;");

        tile.getChildren().addAll(iconLabel, titleLabel, descLabel);
        return tile;
    }

    private VBox createRightActionCard() {
        VBox card = new VBox(22);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(42, 40, 42, 40));
        card.setPrefWidth(420);
        card.setMaxWidth(420);

        card.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.92);" +
                        "-fx-background-radius: 28px;" +
                        "-fx-border-color: rgba(255, 255, 255, 1.0);" +
                        "-fx-border-radius: 28px;" +
                        "-fx-border-width: 1.5px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(30, 58, 138, 0.16), 35, 0, 0, 10);");

        // Header logo icon inside card
        URL logoUrl = getClass().getResource("/assets/images/logo.png");
        if (logoUrl != null) {
            ImageView cardLogoView = new ImageView(new Image(logoUrl.toExternalForm()));
            cardLogoView.setFitWidth(64);
            cardLogoView.setFitHeight(64);
            cardLogoView.setPreserveRatio(true);
            card.getChildren().add(cardLogoView);
        }

        Label cardTitle = new Label("Welcome to MRDesk");
        cardTitle.setStyle(
                "-fx-font-family: 'Segoe UI', Arial, sans-serif;" +
                        "-fx-font-size: 26px;" +
                        "-fx-font-weight: 800;" +
                        "-fx-text-fill: #0F172A;");

        Label cardSub = new Label("Sign in to access your daily visits schedule and representative workspace.");
        cardSub.setWrapText(true);
        cardSub.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        cardSub.setStyle(
                "-fx-font-family: 'Segoe UI', Arial;" +
                        "-fx-font-size: 14px;" +
                        "-fx-text-fill: #64748B;" +
                        "-fx-line-spacing: 3px;");

        // Primary Action Button
        Button getStartedBtn = new Button("Get Started  →");
        getStartedBtn.setPrefWidth(330);
        getStartedBtn.setPrefHeight(52);
        getStartedBtn.setStyle(
                "-fx-font-family: 'Segoe UI', Arial;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: 700;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to right, #2563EB, #1D4ED8);" +
                        "-fx-background-radius: 14px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(37, 99, 235, 0.35), 15, 0, 0, 6);");

        // Hover Effect
        getStartedBtn.setOnMouseEntered(e -> getStartedBtn.setStyle(
                "-fx-font-family: 'Segoe UI', Arial;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: 700;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to right, #1D4ED8, #1E40AF);" +
                        "-fx-background-radius: 14px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(29, 78, 216, 0.45), 20, 0, 0, 8);"));

        getStartedBtn.setOnMouseExited(e -> getStartedBtn.setStyle(
                "-fx-font-family: 'Segoe UI', Arial;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: 700;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to right, #2563EB, #1D4ED8);" +
                        "-fx-background-radius: 14px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(37, 99, 235, 0.35), 15, 0, 0, 6);"));

        getStartedBtn.setOnAction(event -> {
            LoginPage loginPage = new LoginPage();
            welcomeStage.setScene(loginPage.createView(welcomeStage));
        });

        // Secondary Action Button (Create Account)
        Button createAccountBtn = new Button("Create New Account");
        createAccountBtn.setPrefWidth(330);
        createAccountBtn.setPrefHeight(46);
        createAccountBtn.setStyle(
                "-fx-font-family: 'Segoe UI', Arial;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: 600;" +
                        "-fx-text-fill: #334155;" +
                        "-fx-background-color: #F8FAFC;" +
                        "-fx-border-color: #E2E8F0;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-cursor: hand;");

        createAccountBtn.setOnMouseEntered(e -> createAccountBtn.setStyle(
                "-fx-font-family: 'Segoe UI', Arial;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: 600;" +
                        "-fx-text-fill: #0F172A;" +
                        "-fx-background-color: #F1F5F9;" +
                        "-fx-border-color: #CBD5E1;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-cursor: hand;"));

        createAccountBtn.setOnMouseExited(e -> createAccountBtn.setStyle(
                "-fx-font-family: 'Segoe UI', Arial;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: 600;" +
                        "-fx-text-fill: #334155;" +
                        "-fx-background-color: #F8FAFC;" +
                        "-fx-border-color: #E2E8F0;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-cursor: hand;"));

        createAccountBtn.setOnAction(event -> {
            SignUp signUp = new SignUp();
            welcomeStage.setScene(signUp.createView());
        });

        Label cardFooter = new Label("Secure • Organized • Productive");
        cardFooter.setStyle(
                "-fx-font-family: 'Segoe UI', Arial;" +
                        "-fx-font-size: 12px;" +
                        "-fx-text-fill: #94A3B8;");

        card.getChildren().addAll(cardTitle, cardSub, getStartedBtn, createAccountBtn, cardFooter);
        return card;
    }
}
