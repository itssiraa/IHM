package com.example.project_ihm.controllers;

import com.example.project_ihm.model.Avatar;
import com.example.project_ihm.model.AvatarManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SaveAvatarController {

    @FXML private Button yesButton;
    @FXML private Button noButton;

    private Avatar avatarToSave;

    public void setAvatar(Avatar avatar) {
        this.avatarToSave = avatar;
    }

    @FXML
    private void onYesClicked() {
        // Sauvegarder l'avatar
        if (avatarToSave != null) {
            boolean saved = AvatarManager.saveAvatar(avatarToSave);
            if (saved) {
                System.out.println("Avatar enregistré avec succès !");
            } else {
                System.out.println("Erreur : Un avatar avec ce nom existe déjà.");
            }
        }

        // Fermer la fenêtre de dialogue
        Stage dialogStage = (Stage) yesButton.getScene().getWindow();
        dialogStage.close();

        // Naviguer vers la salle principale
        try {
            navigateToSallePrincipale();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onNoClicked() {
        // Ne pas sauvegarder, fermer la fenêtre
        Stage dialogStage = (Stage) noButton.getScene().getWindow();
        dialogStage.close();

        // Naviguer vers la salle principale quand même
        try {
            navigateToSallePrincipale();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToSallePrincipale() throws IOException {
        Parent sallePrincipalRoot = FXMLLoader.load(
                getClass().getResource("/com/example/project_ihm/fxml/SallePrincipalView.fxml")
        );

        // Obtenir la fenêtre principale (pas la fenêtre de dialogue)
        Stage mainStage = (Stage) Stage.getWindows().stream()
                .filter(window -> window instanceof Stage && !((Stage) window).getModality().equals(Modality.APPLICATION_MODAL))
                .findFirst()
                .orElse(null);

        if (mainStage != null) {
            mainStage.getScene().setRoot(sallePrincipalRoot);
            mainStage.setTitle("Salle Principale");
        }
    }
}