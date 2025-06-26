package com.example.csc325_firebase_webview_auth.view;

import com.google.firebase.auth.UserRecord;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.csc325_firebase_webview_auth.view.App.scene;

public class LoginView {

    @FXML
    protected Button loginButton;

    @FXML
    protected TextField emailField;

    @FXML
    protected TextField passwordField;

    @FXML
    protected void login() {

        Stage stage = (Stage) emailField.getScene().getWindow();

        try {
            scene = new Scene(App.loadFXML("/files/LandingScreen.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.show();
    }

}
