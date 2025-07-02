package com.example.project_ihm.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TresorController {

    @FXML
    private Button playButton;

    @FXML
    private Button quitButton;

    @FXML
    private void initialize() {
        playButton.setOnAction(e -> {
            try {
                onRetourCarte();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        quitButton.setOnAction(e -> onQuitterJeu());
    }

    private void onRetourCarte() throws IOException {
        Parent sallePrincipalRoot = FXMLLoader.load(
                getClass().getResource("/com/example/project_ihm/fxml/SallePrincipalView.fxml")
        );

        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.getScene().setRoot(sallePrincipalRoot);
        stage.setTitle("Salle Principale");
    }

    private void onQuitterJeu() {
        System.exit(0); // permet de quitter
    }
}
