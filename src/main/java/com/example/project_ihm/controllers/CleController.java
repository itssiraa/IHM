package com.example.project_ihm.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class CleController {

    @FXML private Button playButton;

    @FXML
    private void initialize() {
        if (playButton != null) {
            playButton.setOnAction(e -> {
                try {
                    onRetourCarte();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    /**
     * Retourne à la salle principale quand le joueur clique sur "Retour à la carte"
     */
    @FXML
    private void onRetourCarte() throws IOException {
        // Retourner à la salle principale
        Parent sallePrincipalRoot = FXMLLoader.load(
                getClass().getResource("/com/example/project_ihm/fxml/SallePrincipalView.fxml")
        );

        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.getScene().setRoot(sallePrincipalRoot);
        stage.setTitle("Salle Principale");
    }
}