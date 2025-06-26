package com.example.csc325_firebase_webview_auth.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.csc325_firebase_webview_auth.view.App.scene;

public class SplashView {

    @FXML
    protected Button loginButton;

    @FXML
    protected Button registerButton;

    @FXML
    protected void login() {

        Stage stage = (Stage) loginButton.getScene().getWindow();

        try {
            scene = new Scene(App.loadFXML("/files/LoginScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void register() {

        Stage stage = (Stage) registerButton.getScene().getWindow();

        try {
            scene = new Scene(App.loadFXML("/files/RegisterScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.show();
    }
}
