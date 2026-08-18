package com.example;

import com.example.view.Welcome;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main {

    public static void main(String[] args) {
        Application.launch(FXApp.class, args);
    }

    public static class FXApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            new Welcome().start(primaryStage);
        }
    }
}
