package com.example.project_ihm.controllers;

import com.example.project_ihm.model.AvatarManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class ExplicationController {

    @FXML private Button yesButton;
    @FXML private Label pieceLabel;
    @FXML private ImageView avatarImage;

    private int etape;

    @FXML
    private void initialize() {
        loadPlayerAvatar();
        // Configuration du bouton
        if (yesButton != null) {
            yesButton.setOnAction(e -> {
                try {
                    onCommencerDefi();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    public void setEtape(int etape) {
        this.etape = etape;
        // Mettre à jour le label si nécessaire
        if (pieceLabel != null) {
            pieceLabel.setText("Pièce " + etape);
        }
    }

    @FXML
    private void onCommencerDefi() throws IOException {
        // Charger le jeu de foot avec la bonne étape
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_ihm/fxml/FootView.fxml"));
        Parent footRoot = loader.load();

        // Passer l'étape au contrôleur de foot
        FootController footController = loader.getController();
        footController.setEtape(etape);

        // Naviguer vers le jeu
        Stage stage = (Stage) yesButton.getScene().getWindow();
        stage.getScene().setRoot(footRoot);
        stage.setTitle("Foot - Étape " + etape);
    }
    private void loadPlayerAvatar() {
        try {
            AvatarManager avatarManager = AvatarManager.getInstance();
            avatarManager.updateAvatarImageView(avatarImage,false);

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'avatar : " + e.getMessage());
        }
    }
}