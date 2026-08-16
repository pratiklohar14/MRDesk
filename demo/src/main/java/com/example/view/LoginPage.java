package com.example.view;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import com.example.view.MR.MrDashboard;

public class LoginPage {

    private Scene loginScene;

    public Scene createView() {

        // ==========================================
        // 1. BACKGROUND CANVAS & AMBIENT ANIMATIONS
        // ==========================================
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #0F172A;"); // Deep slate blue base

        // Ambient glowing orbs background
        Circle bgOrb1 = new Circle(220, Color.web("#1D4ED8", 0.25));
        bgOrb1.setTranslateX(-400);
        bgOrb1.setTranslateY(-250);

        Circle bgOrb2 = new Circle(180, Color.web("#06B6D4", 0.20));
        bgOrb2.setTranslateX(420);
        bgOrb2.setTranslateY(220);

        Circle bgOrb3 = new Circle(140, Color.web("#3B82F6", 0.15));
        bgOrb3.setTranslateX(200);
        bgOrb3.setTranslateY(-300);

        // Floating continuous animation for background orbs
        createFloatingAnimation(bgOrb1, 0, -30, 25, 0, 6.0);
        createFloatingAnimation(bgOrb2, 0, 35, -20, 0, 7.5);
        createFloatingAnimation(bgOrb3, 20, 0, 0, 30, 8.0);

        StackPane backgroundPane = new StackPane(bgOrb1, bgOrb2, bgOrb3);
        backgroundPane.setPickOnBounds(false);

        // ==========================================
        // 2. MAIN CARD CONTAINER (SPLIT LAYOUT)
        // ==========================================
        HBox mainCard = new HBox();
        mainCard.setMaxWidth(960);
        mainCard.setMaxHeight(580);
        mainCard.setPrefSize(960, 580);
        mainCard.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background-radius: 24px;");

        // Drop shadow for the main card
        DropShadow cardShadow = new DropShadow();
        cardShadow.setColor(Color.rgb(0, 0, 0, 0.45));
        cardShadow.setRadius(35);
        cardShadow.setOffsetY(15);
        mainCard.setEffect(cardShadow);

        // ------------------------------------------
        // LEFT HERO PANEL (BRANDING & GRAPHICS)
        // ------------------------------------------
        VBox leftHero = new VBox(24);
        leftHero.setPrefWidth(440);
        leftHero.setMaxWidth(440);
        leftHero.setAlignment(Pos.CENTER_LEFT);
        leftHero.setPadding(new Insets(48, 44, 48, 44));
        leftHero.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #1E3A8A, #2563EB, #0284C7);" +
                        "-fx-background-radius: 24px 0px 0px 24px;");

        // Brand Icon / Logo Badge
        StackPane logoBadge = new StackPane();
        logoBadge.setPrefSize(58, 58);
        logoBadge.setMaxSize(58, 58);
        logoBadge.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.18);" +
                        "-fx-background-radius: 16px;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.3);" +
                        "-fx-border-radius: 16px;" +
                        "-fx-border-width: 1px;");

        URL logoUrl = getClass().getResource("/assets/images/logo.png");
        if (logoUrl != null) {
            ImageView logoView = new ImageView(new Image(logoUrl.toExternalForm()));
            logoView.setFitWidth(42);
            logoView.setFitHeight(42);
            logoView.setPreserveRatio(true);
            logoBadge.getChildren().add(logoView);
        } else {
            Label logoIcon = new Label("MR");
            logoIcon.setFont(Font.font("Arial", FontWeight.BOLD, 22));
            logoIcon.setTextFill(Color.WHITE);
            logoBadge.getChildren().add(logoIcon);
        }

        Label brandTitle = new Label("MRDesk");
        brandTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
        brandTitle.setTextFill(Color.WHITE);

        Label brandSubtitle = new Label("Medical Representative Workspace");
        brandSubtitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 15));
        brandSubtitle.setTextFill(Color.web("#93C5FD"));

        VBox brandBox = new VBox(6, logoBadge, brandTitle, brandSubtitle);

        Label heroDescription = new Label(
                "Welcome to your intelligent scheduling & visit tracking suite. Log in to manage appointments, doctor interactions, and daily routes seamlessly.");
        heroDescription.setWrapText(true);
        heroDescription.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        heroDescription.setTextFill(Color.web("#E0F2FE"));
        heroDescription.setLineSpacing(4);

        // Feature Highlights Badge Box
        VBox featureList = new VBox(12);
        featureList.getChildren().addAll(
                createFeaturePill("⚡ Dynamic Schedule Management"),
                createFeaturePill("📍 Real-time Doctor Visit Tracking"),
                createFeaturePill("🔒 Secure & Encrypted Workspace"));

        Region heroSpacer = new Region();
        heroSpacer.setMaxHeight(Double.MAX_VALUE);
        // VBox.setVGrow(heroSpacer, Priority.ALWAYS);

        Label heroFooter = new Label("MRDesk v2.4 • Powered by Modern JavaFX");
        heroFooter.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        heroFooter.setTextFill(Color.web("#93C5FD", 0.7));

        leftHero.getChildren().addAll(brandBox, heroDescription, featureList, heroSpacer, heroFooter);

        // ------------------------------------------
        // RIGHT FORM PANEL (LOGIN INPUTS)
        // ------------------------------------------
        VBox rightForm = new VBox(20);
        rightForm.setPrefWidth(520);
        rightForm.setMaxWidth(520);
        rightForm.setAlignment(Pos.CENTER_LEFT);
        rightForm.setPadding(new Insets(48, 50, 48, 50));
        rightForm.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 0px 24px 24px 0px;");

        // Form Title & Subtitle
        Label title = new Label("Welcome Back!");
        title.setStyle(
                "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-size: 30px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #0F172A;");

        Label subTitle = new Label("Please enter your details to sign in");
        subTitle.setStyle(
                "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-size: 14px;" +
                        "-fx-text-fill: #64748B;");

        VBox titleBox = new VBox(4, title, subTitle);

        // Username Field with Custom Container
        Label usernameLabel = new Label("Username or Email");
        usernameLabel.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #334155;");

        TextField username = new TextField();
        username.setPromptText("Enter your username");
        styleInputField(username);

        VBox usernameBox = new VBox(6, usernameLabel, username);

        // Password Field with Toggle Show/Hide Password
        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #334155;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        styleInputField(passwordField);

        TextField visiblePasswordField = new TextField();
        visiblePasswordField.setPromptText("Enter your password");
        styleInputField(visiblePasswordField);
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setVisible(false);

        // Bind bidirectionally
        passwordField.textProperty().bindBidirectional(visiblePasswordField.textProperty());

        Button togglePasswordBtn = new Button("👁");
        togglePasswordBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #64748B;" +
                        "-fx-font-size: 14px;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 0 8 0 0;");

        togglePasswordBtn.setOnAction(e -> {
            if (passwordField.isVisible()) {
                passwordField.setManaged(false);
                passwordField.setVisible(false);
                visiblePasswordField.setManaged(true);
                visiblePasswordField.setVisible(true);
                togglePasswordBtn.setText("🙈");
            } else {
                visiblePasswordField.setManaged(false);
                visiblePasswordField.setVisible(false);
                passwordField.setManaged(true);
                passwordField.setVisible(true);
                togglePasswordBtn.setText("👁");
            }
        });

        StackPane passwordInputStack = new StackPane();
        passwordInputStack.setAlignment(Pos.CENTER_RIGHT);
        passwordInputStack.getChildren().addAll(passwordField, visiblePasswordField, togglePasswordBtn);
        StackPane.setMargin(togglePasswordBtn, new Insets(0, 12, 0, 0));

        VBox passwordBox = new VBox(6, passwordLabel, passwordInputStack);

        // Forgot Password Button
        Button forgotPassword = new Button("Forgot Password?");
        forgotPassword.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #2563EB;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 0;");

        addHoverEffect(forgotPassword, "#2563EB", "#1D4ED8");

        forgotPassword.setOnAction(event -> {
            System.out.println("Forgot Password clicked");
        });

        HBox forgotBox = new HBox(forgotPassword);
        forgotBox.setAlignment(Pos.CENTER_RIGHT);

        // Login Button
        Button btn = new Button("Sign In  ➔");
        btn.setPrefHeight(46);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle(
                "-fx-background-color: linear-gradient(to right, #2563EB, #1D4ED8);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-cursor: hand;");

        // Login Button Hover & Click Animations
        addPrimaryButtonAnimations(btn);

        btn.setOnAction(event -> {
            System.out.println("Login clicked for user: " + username.getText());
            MrDashboard mrDashboard = new MrDashboard();
            Scene dashboardScene = mrDashboard.createView();
            if (Welcome.welcomeStage != null) {
                Welcome.welcomeStage.setScene(dashboardScene);
            } else {
                javafx.stage.Stage currentStage = (javafx.stage.Stage) btn.getScene().getWindow();
                if (currentStage != null) {
                    currentStage.setScene(dashboardScene);
                }
            }
        });

        // Sign Up Bottom Row (Preserving exact navigation handler)
        Label accountLabel = new Label("Don't have an account?");
        accountLabel.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: #64748B;");

        Button signUp = new Button("SignUp");
        signUp.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #2563EB;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;");

        addHoverEffect(signUp, "#2563EB", "#1D4ED8");

        // PRESERVED NAVIGATION HANDLER
        signUp.setOnAction(event -> {
            SignUp signUpPage = new SignUp();
            Scene signUpScene = signUpPage.createView();

            Welcome.welcomeStage.setScene(signUpScene);
        });

        HBox signUpBox = new HBox(6, accountLabel, signUp);
        signUpBox.setAlignment(Pos.CENTER);
        signUpBox.setPadding(new Insets(10, 0, 0, 0));

        rightForm.getChildren().addAll(
                titleBox,
                usernameBox,
                passwordBox,
                forgotBox,
                btn,
                signUpBox);

        mainCard.getChildren().addAll(leftHero, rightForm);

        root.getChildren().addAll(backgroundPane, mainCard);

        Scene sc = new Scene(root, Welcome.welcomeStage.getWidth(), Welcome.welcomeStage.getHeight());
        loginScene = sc;

        // Trigger entrance animations when scene is displayed
        playEntranceAnimations(leftHero, titleBox, usernameBox, passwordBox, forgotBox, btn, signUpBox);

        return loginScene;
    }

    // ==========================================
    // HELPER UI BUILDERS & STYLING
    // ==========================================
    private HBox createFeaturePill(String text) {
        HBox pill = new HBox();
        pill.setAlignment(Pos.CENTER_LEFT);
        pill.setPadding(new Insets(8, 14, 8, 14));
        pill.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.12);" +
                        "-fx-background-radius: 10px;");

        Label lbl = new Label(text);
        lbl.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
        lbl.setTextFill(Color.WHITE);

        pill.getChildren().add(lbl);
        return pill;
    }

    private void styleInputField(TextField field) {
        field.setPrefHeight(44);
        field.setMaxWidth(Double.MAX_VALUE);
        field.setStyle(
                "-fx-background-color: #F8FAFC;" +
                        "-fx-border-color: #E2E8F0;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-padding: 0 14px;" +
                        "-fx-font-size: 14px;" +
                        "-fx-text-fill: #0F172A;");

        // Focus glow micro-animation
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                field.setStyle(
                        "-fx-background-color: #FFFFFF;" +
                                "-fx-border-color: #2563EB;" +
                                "-fx-border-width: 2px;" +
                                "-fx-border-radius: 10px;" +
                                "-fx-background-radius: 10px;" +
                                "-fx-padding: 0 13px;" +
                                "-fx-font-size: 14px;" +
                                "-fx-text-fill: #0F172A;");
            } else {
                field.setStyle(
                        "-fx-background-color: #F8FAFC;" +
                                "-fx-border-color: #E2E8F0;" +
                                "-fx-border-width: 1px;" +
                                "-fx-border-radius: 10px;" +
                                "-fx-background-radius: 10px;" +
                                "-fx-padding: 0 14px;" +
                                "-fx-font-size: 14px;" +
                                "-fx-text-fill: #0F172A;");
            }
        });
    }

    private void addHoverEffect(Button button, String normalColor, String hoverColor) {
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: " + hoverColor + ";" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-underline: true;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 0;"));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: " + normalColor + ";" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-underline: false;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 0;"));
    }

    private void addPrimaryButtonAnimations(Button btn) {
        btn.setOnMouseEntered(e -> {
            btn.setStyle(
                    "-fx-background-color: linear-gradient(to right, #1D4ED8, #1E40AF);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-family: 'Segoe UI';" +
                            "-fx-font-size: 15px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 10px;" +
                            "-fx-cursor: hand;");
            ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
            st.setToX(1.02);
            st.setToY(1.02);
            st.play();
        });

        btn.setOnMouseExited(e -> {
            btn.setStyle(
                    "-fx-background-color: linear-gradient(to right, #2563EB, #1D4ED8);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-family: 'Segoe UI';" +
                            "-fx-font-size: 15px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 10px;" +
                            "-fx-cursor: hand;");
            ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        btn.setOnMousePressed(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), btn);
            st.setToX(0.97);
            st.setToY(0.97);
            st.play();
        });

        btn.setOnMouseReleased(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), btn);
            st.setToX(1.02);
            st.setToY(1.02);
            st.play();
        });
    }

    // ==========================================
    // ANIMATION ENGINES
    // ==========================================
    private void createFloatingAnimation(Node node, double fromX, double toX, double fromY, double toY,
            double durationSeconds) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(durationSeconds), node);
        tt.setFromX(fromX);
        tt.setToX(toX);
        tt.setFromY(fromY);
        tt.setToY(toY);
        tt.setCycleCount(Animation.INDEFINITE);
        tt.setAutoReverse(true);
        tt.play();
    }

    private void playEntranceAnimations(Node hero, Node... formItems) {
        // Left hero pane entrance slide & fade
        hero.setOpacity(0);
        hero.setTranslateX(-50);

        FadeTransition heroFade = new FadeTransition(Duration.millis(600), hero);
        heroFade.setToValue(1.0);

        TranslateTransition heroTranslate = new TranslateTransition(Duration.millis(600), hero);
        heroTranslate.setToX(0);

        ParallelTransition heroParallel = new ParallelTransition(heroFade, heroTranslate);

        // Staggered cascade entrance for right form components
        SequentialTransition formSequence = new SequentialTransition();
        for (int i = 0; i < formItems.length; i++) {
            Node item = formItems[i];
            item.setOpacity(0);
            item.setTranslateY(25);

            FadeTransition fade = new FadeTransition(Duration.millis(350), item);
            fade.setToValue(1.0);

            TranslateTransition translate = new TranslateTransition(Duration.millis(350), item);
            translate.setToY(0);

            ParallelTransition itemParallel = new ParallelTransition(fade, translate);
            formSequence.getChildren().add(itemParallel);
        }

        ParallelTransition fullEntrance = new ParallelTransition(heroParallel, formSequence);
        fullEntrance.setDelay(Duration.millis(100));
        fullEntrance.play();
    }
}