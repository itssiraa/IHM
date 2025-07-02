package com.example.project_ihm.controllers;

import com.example.project_ihm.model.ProgressionJeu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import com.example.project_ihm.model.AvatarManager;

public class SallePrincipalController {

    @FXML private ImageView avatarImage;
    @FXML private Circle zone1;
    @FXML private Circle zone2;
    @FXML private Circle zone3;
    @FXML private Label statusLabel;

    // Position initiale de l'avatar
    private double initialX;
    private double initialY;

    @FXML
    private void initialize() {
        // Sauvegarder la position initiale
        initialX = avatarImage.getLayoutX();
        initialY = avatarImage.getLayoutY();

        // Les zones sont invisibles, juste des zones de drop
        setupDropZones();

        // Charger l'avatar personnalisé du joueur
        loadPlayerAvatar();
    }

    private void setupDropZones() {
        // S'assurer que les zones sont bien invisibles mais actives
        zone1.setOpacity(0);
        zone2.setOpacity(0);
        zone3.setOpacity(0);
    }

    private void loadPlayerAvatar() {
        try {
            // Utiliser l'AvatarManager pour charger l'avatar actuel
            AvatarManager avatarManager = AvatarManager.getInstance();
            avatarManager.updateAvatarImageView(avatarImage, true); // false pour l'image de face

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'avatar : " + e.getMessage());
        }
    }

    @FXML
    private void handleMousePressed(MouseEvent event) {
        avatarImage.setOpacity(0.8);
    }

    @FXML
    private void handleDragDetected(MouseEvent event) {
        // Démarrer le drag and drop
        Dragboard db = avatarImage.startDragAndDrop(TransferMode.MOVE);

        // Mettre du contenu dans le dragboard
        ClipboardContent content = new ClipboardContent();
        content.putString("avatar");
        db.setContent(content);

        // Utiliser l'image de l'avatar comme image de drag
        db.setDragView(avatarImage.getImage(), 40, 50);

        event.consume();
    }

    @FXML
    private void handleDragOver(DragEvent event) {
        // Accepter le drag si c'est l'avatar
        if (event.getDragboard().hasString() &&
                event.getDragboard().getString().equals("avatar")) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @FXML
    private void handleDragDropped1(DragEvent event) {
        handleDragDropped(event, 1);
    }

    @FXML
    private void handleDragDropped2(DragEvent event) {
        handleDragDropped(event, 2);
    }

    @FXML
    private void handleDragDropped3(DragEvent event) {
        handleDragDropped(event, 3);
    }

    private void handleDragDropped(DragEvent event, int etape) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasString() && db.getString().equals("avatar")) {
            // Vérifie la progression
            if ((etape == 2 && !ProgressionJeu.isEtape1Terminee()) ||
                    (etape == 3 && !ProgressionJeu.isEtape2Terminee())) {
                statusLabel.setText("Tu dois finir l'étape " + (etape - 1) + " d'abord !");
            } else {
                // Lancer l'étape
                lancerEtape(etape);
                success = true;
            }
        }

        // Réinitialiser l'opacité
        avatarImage.setOpacity(1.0);

        event.setDropCompleted(success);
        event.consume();
    }

    private void lancerEtape(int etape) {
        statusLabel.setText("Lancement de l'étape " + etape + "...");

        // Animation de retour à la position initiale
        TranslateTransition resetTransition = new TranslateTransition(Duration.millis(500), avatarImage);
        resetTransition.setToX(0);
        resetTransition.setToY(0);
        resetTransition.play();

        // Lancer le jeu correspondant à l'étape après un délai
        resetTransition.setOnFinished(e -> {
            switch (etape) {
                case 1:
                    System.out.println("Lancement de l'étape 1");
                    statusLabel.setText("Étape 1 lancée !");
                    lancerMiniJeu(1);
                    break;
                case 2:
                    System.out.println("Lancement de l'étape 2");
                    statusLabel.setText("Étape 2 lancée !");
                    lancerMiniJeu(2);
                    break;
                case 3:
                    System.out.println("Lancement de l'étape 3");
                    statusLabel.setText("Étape 3 lancée !");
                    lancerMiniJeu(3);
                    break;
            }
        });
    }

    private void lancerMiniJeu(int numeroJeu) {
        try {
            // D'abord afficher la page d'explication
            String explicationPath = "";
            String titre = "";

            switch (numeroJeu) {
                case 1:
                    explicationPath = "/com/example/project_ihm/fxml/ExplicationJeux1.fxml";
                    titre = "Explication - Niveau 1";
                    break;
                case 2:
                    explicationPath = "/com/example/project_ihm/fxml/ExplicationJeux2.fxml";
                    titre = "Explication - Niveau 2";
                    break;
                case 3:
                    explicationPath = "/com/example/project_ihm/fxml/ExplicationJeux3.fxml";
                    titre = "Explication - Niveau 3";
                    break;
            }

            if (!explicationPath.isEmpty()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(explicationPath));
                Parent explicationRoot = loader.load();

                // Passer l'étape au contrôleur d'explication
                ExplicationController controller = loader.getController();
                controller.setEtape(numeroJeu);

                Stage stage = (Stage) avatarImage.getScene().getWindow();
                stage.getScene().setRoot(explicationRoot);
                stage.setTitle(titre);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du lancement de la page d'explication : " + e.getMessage());
            e.printStackTrace();
        }
    }
}