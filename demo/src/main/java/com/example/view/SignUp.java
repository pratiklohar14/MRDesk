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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
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

public class SignUp {

    private Scene signUpScene;

    public Scene createView() {

        // ==========================================
        // 1. BACKGROUND CANVAS & AMBIENT ANIMATIONS
        // ==========================================
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #0F172A;"); // Deep slate blue base

        // Ambient glowing orbs background
        Circle bgOrb1 = new Circle(240, Color.web("#2563EB", 0.22));
        bgOrb1.setTranslateX(-420);
        bgOrb1.setTranslateY(200);

        Circle bgOrb2 = new Circle(190, Color.web("#06B6D4", 0.18));
        bgOrb2.setTranslateX(400);
        bgOrb2.setTranslateY(-220);

        Circle bgOrb3 = new Circle(150, Color.web("#3B82F6", 0.15));
        bgOrb3.setTranslateX(-180);
        bgOrb3.setTranslateY(-280);

        // Continuous floating animations
        createFloatingAnimation(bgOrb1, 0, 30, -20, 10, 7.0);
        createFloatingAnimation(bgOrb2, 0, -35, 25, 0, 8.0);
        createFloatingAnimation(bgOrb3, 20, 0, 0, -30, 9.0);

        StackPane backgroundPane = new StackPane(bgOrb1, bgOrb2, bgOrb3);
        backgroundPane.setPickOnBounds(false);

        // ==========================================
        // 2. MAIN CARD CONTAINER (SPLIT LAYOUT)
        // ==========================================
        HBox mainCard = new HBox();
        mainCard.setMaxWidth(980);
        mainCard.setMaxHeight(620);
        mainCard.setPrefSize(980, 620);
        mainCard.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background-radius: 24px;");

        DropShadow cardShadow = new DropShadow();
        cardShadow.setColor(Color.rgb(0, 0, 0, 0.45));
        cardShadow.setRadius(35);
        cardShadow.setOffsetY(15);
        mainCard.setEffect(cardShadow);

        // ------------------------------------------
        // LEFT HERO PANEL (BRANDING & INCENTIVES)
        // ------------------------------------------
        VBox leftHero = new VBox(24);
        leftHero.setPrefWidth(420);
        leftHero.setMaxWidth(420);
        leftHero.setAlignment(Pos.CENTER_LEFT);
        leftHero.setPadding(new Insets(44, 40, 44, 40));
        leftHero.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #1E3A8A, #2563EB, #0284C7);" +
                        "-fx-background-radius: 24px 0px 0px 24px;");

        // Logo Badge
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

        Label brandSubtitle = new Label("Join the Medical Rep Platform");
        brandSubtitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 15));
        brandSubtitle.setTextFill(Color.web("#93C5FD"));

        VBox brandBox = new VBox(6, logoBadge, brandTitle, brandSubtitle);

        Label heroDescription = new Label(
                "Create your account to unlock automated itinerary planning, doctor database management, sample tracking, and real-time reporting.");
        heroDescription.setWrapText(true);
        heroDescription.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        heroDescription.setTextFill(Color.web("#E0F2FE"));
        heroDescription.setLineSpacing(4);

        // Feature Highlights List
        VBox featureList = new VBox(12);
        featureList.getChildren().addAll(
                createFeaturePill("🎯 Smart Doctor Visit Routing"),
                createFeaturePill("📊 Real-time Call Analytics"),
                createFeaturePill("📦 Sample & Gift Inventory"),
                createFeaturePill("🔔 Automated Daily Reminders"));

        Region heroSpacer = new Region();

        Label heroFooter = new Label("MRDesk Enterprise • Secure Registration");
        heroFooter.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        heroFooter.setTextFill(Color.web("#93C5FD", 0.7));

        leftHero.getChildren().addAll(brandBox, heroDescription, featureList, heroSpacer, heroFooter);

        // ------------------------------------------
        // RIGHT FORM PANEL (MULTI-FIELD FORM)
        // ------------------------------------------
        VBox rightForm = new VBox(14);
        rightForm.setPrefWidth(600);
        rightForm.setMaxWidth(600);
        rightForm.setAlignment(Pos.TOP_LEFT);
        rightForm.setPadding(new Insets(36, 44, 36, 44));
        rightForm.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 0px 24px 24px 0px;");

        // Title Header
        Label title = new Label("Create Account");
        title.setStyle(
                "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #0F172A;");

        Label subTitle = new Label("Fill in your information to get started");
        subTitle.setStyle(
                "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-size: 13px;" +
                        "-fx-text-fill: #64748B;");

        VBox titleBox = new VBox(2, title, subTitle);

        // --- ROW 1: Full Name & Email ---
        Label fullNameLabel = createFieldLabel("Full Name");
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("John Doe");
        styleInputField(fullNameField);
        VBox fullNameBox = new VBox(4, fullNameLabel, fullNameField);

        Label emailLabel = createFieldLabel("Email Address");
        TextField emailField = new TextField();
        emailField.setPromptText("john.doe@pharma.com");
        styleInputField(emailField);
        VBox emailBox = new VBox(4, emailLabel, emailField);

        HBox row1 = new HBox(14, fullNameBox, emailBox);

        // --- ROW 2: Phone Number & Role/Territory ---
        Label phoneLabel = createFieldLabel("Phone Number");
        TextField phoneField = new TextField();
        phoneField.setPromptText("+1 (555) 019-2834");
        styleInputField(phoneField);
        VBox phoneBox = new VBox(4, phoneLabel, phoneField);

        Label roleLabel = createFieldLabel("Role / Designation");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll(
                "Medical Representative (MR)",
                "Area Sales Manager (ASM)",
                "Regional Sales Manager (RSM)",
                "Admin");
        roleComboBox.getSelectionModel().selectFirst();
        styleComboBox(roleComboBox);
        VBox roleBox = new VBox(4, roleLabel, roleComboBox);

        HBox row2 = new HBox(14, phoneBox, roleBox);

        // --- ROW 3: Password & Confirm Password ---
        Label passwordLabel = createFieldLabel("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Create password");
        styleInputField(passwordField);

        TextField visiblePasswordField = new TextField();
        visiblePasswordField.setPromptText("Create password");
        styleInputField(visiblePasswordField);
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setVisible(false);
        passwordField.textProperty().bindBidirectional(visiblePasswordField.textProperty());

        Button togglePasswordBtn = createPasswordToggleButton(passwordField, visiblePasswordField);
        StackPane passwordStack = new StackPane(passwordField, visiblePasswordField, togglePasswordBtn);
        passwordStack.setAlignment(Pos.CENTER_RIGHT);
        StackPane.setMargin(togglePasswordBtn, new Insets(0, 10, 0, 0));
        VBox passwordBox = new VBox(4, passwordLabel, passwordStack);

        Label confirmLabel = createFieldLabel("Confirm Password");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Re-enter password");
        styleInputField(confirmPasswordField);

        TextField visibleConfirmField = new TextField();
        visibleConfirmField.setPromptText("Re-enter password");
        styleInputField(visibleConfirmField);
        visibleConfirmField.setManaged(false);
        visibleConfirmField.setVisible(false);
        confirmPasswordField.textProperty().bindBidirectional(visibleConfirmField.textProperty());

        Button toggleConfirmBtn = createPasswordToggleButton(confirmPasswordField, visibleConfirmField);
        StackPane confirmStack = new StackPane(confirmPasswordField, visibleConfirmField, toggleConfirmBtn);
        confirmStack.setAlignment(Pos.CENTER_RIGHT);
        StackPane.setMargin(toggleConfirmBtn, new Insets(0, 10, 0, 0));
        VBox confirmBox = new VBox(4, confirmLabel, confirmStack);

        HBox row3 = new HBox(14, passwordBox, confirmBox);

        // --- TERMS & CONDITIONS CHECKBOX ---
        CheckBox termsCheck = new CheckBox("I agree to the Terms of Service & Privacy Policy");
        termsCheck.setStyle(
                "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-size: 12px;" +
                        "-fx-text-fill: #475569;" +
                        "-fx-cursor: hand;");

        // --- SUBMIT BUTTON ---
        Button signUpBtn = new Button("Register Account  ➔");
        signUpBtn.setPrefHeight(44);
        signUpBtn.setMaxWidth(Double.MAX_VALUE);
        signUpBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #2563EB, #1D4ED8);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-cursor: hand;");
        addPrimaryButtonAnimations(signUpBtn);

        signUpBtn.setOnAction(e -> {
            System.out.println(
                    "Registration submitted for: " + fullNameField.getText() + " (" + emailField.getText() + ")");
            MrDashboard mrDashboard = new MrDashboard();
            Welcome.welcomeStage.setScene(mrDashboard.createView());
        });

        // --- ALREADY HAVE AN ACCOUNT LOGIN ROW ---
        Label hasAccountLabel = new Label("Already have an account?");
        hasAccountLabel.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: #64748B;");

        Button loginBtn = new Button("Sign In");
        loginBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #2563EB;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;");
        addHoverEffect(loginBtn, "#2563EB", "#1D4ED8");

        // NAVIGATION BACK TO LOGIN PAGE
        loginBtn.setOnAction(event -> {
            LoginPage loginPage = new LoginPage();
            Scene loginScene = loginPage.createView();
            Welcome.welcomeStage.setScene(loginScene);
        });

        HBox loginBox = new HBox(6, hasAccountLabel, loginBtn);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(4, 0, 0, 0));

        rightForm.getChildren().addAll(
                titleBox,
                row1,
                row2,
                row3,
                termsCheck,
                signUpBtn,
                loginBox);

        mainCard.getChildren().addAll(leftHero, rightForm);
        root.getChildren().addAll(backgroundPane, mainCard);

        Scene sc = new Scene(root, 1200, 700);
        signUpScene = sc;

        // Trigger entrance animations when scene displays
        playEntranceAnimations(leftHero, titleBox, row1, row2, row3, termsCheck, signUpBtn, loginBox);

        return signUpScene;
    }

    // ==========================================
    // HELPER UI BUILDERS & STYLING
    // ==========================================
    private Label createFieldLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #334155;");
        return lbl;
    }

    private HBox createFeaturePill(String text) {
        HBox pill = new HBox();
        pill.setAlignment(Pos.CENTER_LEFT);
        pill.setPadding(new Insets(7, 12, 7, 12));
        pill.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.12);" +
                        "-fx-background-radius: 8px;");

        Label lbl = new Label(text);
        lbl.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
        lbl.setTextFill(Color.WHITE);

        pill.getChildren().add(lbl);
        return pill;
    }

    private void styleInputField(TextField field) {
        field.setPrefHeight(40);
        field.setMaxWidth(Double.MAX_VALUE);
        field.setStyle(
                "-fx-background-color: #F8FAFC;" +
                        "-fx-border-color: #E2E8F0;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 0 12px;" +
                        "-fx-font-size: 13px;" +
                        "-fx-text-fill: #0F172A;");

        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                field.setStyle(
                        "-fx-background-color: #FFFFFF;" +
                                "-fx-border-color: #2563EB;" +
                                "-fx-border-width: 2px;" +
                                "-fx-border-radius: 8px;" +
                                "-fx-background-radius: 8px;" +
                                "-fx-padding: 0 11px;" +
                                "-fx-font-size: 13px;" +
                                "-fx-text-fill: #0F172A;");
                } else {
                field.setStyle(
                        "-fx-background-color: #F8FAFC;" +
                                "-fx-border-color: #E2E8F0;" +
                                "-fx-border-width: 1px;" +
                                "-fx-border-radius: 8px;" +
                                "-fx-background-radius: 8px;" +
                                "-fx-padding: 0 12px;" +
                                "-fx-font-size: 13px;" +
                                "-fx-text-fill: #0F172A;");
                }
        });
        }

        private void styleComboBox(ComboBox<String> combo) {
        combo.setPrefHeight(40);
        combo.setMaxWidth(Double.MAX_VALUE);
        combo.setStyle(
                "-fx-background-color: #F8FAFC;" +
                        "-fx-border-color: #E2E8F0;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-font-size: 13px;");
        }

        private Button createPasswordToggleButton(PasswordField pass, TextField visible) {
        Button toggleBtn = new Button("👁");
        toggleBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #64748B;" +
                        "-fx-font-size: 13px;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 0 6 0 0;");

        toggleBtn.setOnAction(e -> {
            if (pass.isVisible()) {
                pass.setManaged(false);
                pass.setVisible(false);
                visible.setManaged(true);
                visible.setVisible(true);
                toggleBtn.setText("🙈");
            } else {
                visible.setManaged(false);
                visible.setVisible(false);
                pass.setManaged(true);
                pass.setVisible(true);
                toggleBtn.setText("👁");
            }
        });

        return toggleBtn;
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
        hero.setOpacity(0);
        hero.setTranslateX(-50);

        FadeTransition heroFade = new FadeTransition(Duration.millis(600), hero);
        heroFade.setToValue(1.0);

        TranslateTransition heroTranslate = new TranslateTransition(Duration.millis(600), hero);
        heroTranslate.setToX(0);

        ParallelTransition heroParallel = new ParallelTransition(heroFade, heroTranslate);

        SequentialTransition formSequence = new SequentialTransition();
        for (Node item : formItems) {
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
