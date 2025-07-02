package com.example.project_ihm.controllers;

import com.example.project_ihm.model.Avatar;
import com.example.project_ihm.model.Genre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.project_ihm.model.AvatarManager;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

public class GameManager {

    // Variables pour stocker les choix de l'avatar
    private static String selectedGender = "homme";
    private static String selectedHairstyle = "default";
    private static String selectedOutfit = "default";
    private static String selectedAvatarName = "Joueur";
    private static String selectedAvatarImage = "defaultAvatar.png";

    // Getters statiques pour accéder aux choix depuis d'autres contrôleurs
    public static String getSelectedGender() {
        return selectedGender;
    }

    public static String getSelectedHairstyle() {
        return selectedHairstyle;
    }

    public static String getSelectedOutfit() {
        return selectedOutfit;
    }

    public static String getSelectedAvatarImage() {
        return selectedAvatarImage;
    }

    public static String getSelectedAvatarName() {
        return selectedAvatarName;
    }

    // Classe principale pour gérer le menu du jeu
    @FXML private Button playButton;
    @FXML private Button createAvatarButton;
    @FXML private Button quitButton;

    //QuiDialogController qui gère la fenêtre de confirmation de fermeture
    @FXML private Button yesButton;
    @FXML private Button noButton;

    //GameMenuController
    @FXML private Button startGamesButton;
    @FXML private Button closeButtonG;
    @FXML private Button footButton;
    @FXML private Button quizButton;
    @FXML private Button dragonButton;
    @FXML private Button slideSolveButton;
    @FXML private Button clicExpressButton;

    //avatar List Controller
    @FXML private ToggleGroup avatarToggle;
    @FXML private RadioButton avatar1Radio;
    @FXML private RadioButton avatar2Radio;
    @FXML private RadioButton avatarDefaultRadio;
    @FXML private Button validateAvatarButton;
    @FXML private Button closeAvatarList;

    // Ajout des éléments pour l'affichage dynamique
    @FXML private HBox avatar1Box;
    @FXML private HBox avatar2Box;
    @FXML private ImageView avatar1Image;
    @FXML private ImageView avatar2Image;
    @FXML private Label avatar1Label;
    @FXML private Label avatar2Label;

    // ChooseGenderController
    @FXML private ToggleGroup genderToggle;
    @FXML private RadioButton maleRadio;
    @FXML private RadioButton femaleRadio;
    @FXML private Button nextButton;

    //choosehairstyleController
    @FXML private Button validateHairstyleButton;
    @FXML private Button closeChooseHairstyle;
    @FXML private Rectangle hairstyleRect1;
    @FXML private Rectangle hairstyleRect2;
    @FXML private ToggleGroup hairstyleToggle;
    @FXML private RadioButton hairstyle1Radio;
    @FXML private RadioButton hairstyle2Radio;

    // ChooseOutfitController
    @FXML private ToggleGroup outfitToggle;
    @FXML private RadioButton outfit1Radio;
    @FXML private RadioButton outfit2Radio;
    @FXML private Button nextOutfitButton;
    @FXML private ImageView outfitImage1;
    @FXML private ImageView outfitImage2;

    // AvatarConfirmationController
    @FXML private Rectangle avatarDisplay;
    @FXML private TextField avatarNameField;
    @FXML private Label genderLabel;
    @FXML private Label hairstyleLabel;
    @FXML private Label outfitLabel;
    @FXML private Button backButton;
    @FXML private Button confirmAvatarButton;
    @FXML private Button closeConfirmation;

    @FXML
    private void initialize() {
        // Initialiser les boutons de jeu
        setupGameButtons();

        if (avatarDefaultRadio != null) {
            avatarDefaultRadio.setSelected(true);
        }

        if (outfit1Radio != null) {
            outfit1Radio.setSelected(true);
        }

        // Initialiser les rectangles de coiffure
        if (hairstyleRect1 != null && hairstyleRect2 != null) {
            setupHairstyleRectangles();
        }

        if (avatarDefaultRadio != null) {
            avatarDefaultRadio.setSelected(true);
        }

        if (maleRadio != null && femaleRadio != null) {
            maleRadio.setSelected(true);
        }

        // Charger les images de tenues selon le genre
        if (outfitImage1 != null && outfitImage2 != null) {
            loadOutfitImages();
        }

        // Initialiser l'écran de confirmation si nécessaire
        if (avatarDisplay != null) {
            initializeAvatarConfirmation();
        }

        // Charger les avatars depuis le fichier si on est dans la liste des avatars
        if (avatar1Box != null && avatar2Box != null) {
            loadAvatarsFromFile();
        }
    }

    // Nouvelle méthode pour configurer les rectangles de coiffure
    private void setupHairstyleRectangles() {
        try {
            // Charger les images de coiffure selon le genre
            String hairstyle1Path, hairstyle2Path;

            if ("homme".equals(selectedGender)) {
                hairstyle1Path = "/com/example/project_ihm/images/C_H1.png";
                hairstyle2Path = "/com/example/project_ihm/images/C_H2.png";
            } else {
                hairstyle1Path = "/com/example/project_ihm/images/C_F1.png";
                hairstyle2Path = "/com/example/project_ihm/images/C_F2.png";
            }

            // Appliquer les images aux rectangles
            Image img1 = new Image(getClass().getResourceAsStream(hairstyle1Path));
            Image img2 = new Image(getClass().getResourceAsStream(hairstyle2Path));

            hairstyleRect1.setFill(new ImagePattern(img1));
            hairstyleRect2.setFill(new ImagePattern(img2));

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des images de coiffure: " + e.getMessage());
        }
    }

    // Nouvelle méthode pour charger les images de tenues
    private void loadOutfitImages() {
        try {
            String outfit1Path, outfit2Path;

            if ("homme".equals(selectedGender)) {
                outfit1Path = "/com/example/project_ihm/images/O_H1.png";
                outfit2Path = "/com/example/project_ihm/images/O_H2.png";
            } else {
                outfit1Path = "/com/example/project_ihm/images/O_F1.png";
                outfit2Path = "/com/example/project_ihm/images/O_F2.png";
            }

            Image img1 = new Image(getClass().getResourceAsStream(outfit1Path));
            Image img2 = new Image(getClass().getResourceAsStream(outfit2Path));

            outfitImage1.setImage(img1);
            outfitImage2.setImage(img2);

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des images de tenue: " + e.getMessage());
        }
    }

    @FXML
    private void onPlayButtonClicked(ActionEvent event) throws IOException {
        Parent avatarListRoot = FXMLLoader.load(
                getClass().getResource("/com/example/project_ihm/fxml/AvatarListView.fxml")
        );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(avatarListRoot);
        stage.setTitle("Sélectionner un avatar");
    }

    @FXML
    private void onCreateAvatarButtonClicked(ActionEvent event) throws IOException {
        // Quand on clique sur Créer avatar, afficher le choix du genre
        Parent chooseGenderRoot = FXMLLoader.load(
                getClass().getResource("/com/example/project_ihm/fxml/ChooseGenderView.fxml")
        ); // view pour choisir le genre

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Récupérer la scène actuelle
        stage.getScene().setRoot(chooseGenderRoot); // Changer la racine de la scène
        stage.setTitle("Choisir le genre");
    }

    @FXML
    private void onQuitButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/project_ihm/fxml/QuitDialogView.fxml")
        );
        Parent root = loader.load();

        Stage dialogStage = new Stage(); // Création d'une nouvelle fenêtre pour le dialogue de confirmation
        dialogStage.setTitle("Confirmation de fermeture");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(
                ((Stage) ((javafx.scene.Node) event.getSource())
                        .getScene().getWindow())
        );
        dialogStage.setScene(new Scene(root));
        dialogStage.setResizable(false); // Désactiver la redimensionnement de la fenêtre

        dialogStage.showAndWait();
    }

    @FXML
    private void onYesClicked() {
        Stage dialogStage = (Stage) yesButton.getScene().getWindow();
        dialogStage.close();
        System.exit(0);
    }

    @FXML
    private void onNoClicked() {
        Stage dialogStage = (Stage) noButton.getScene().getWindow();
        dialogStage.close();
    }

    //GameMenuController
    private void setupGameButtons() {
        // Configuration des boutons de jeu
        if (footButton != null) {
            footButton.setOnAction(e -> selectGame("Foot"));
        }
        if (quizButton != null) {
            quizButton.setOnAction(e -> selectGame("Quiz"));
        }
        if (dragonButton != null) {
            dragonButton.setOnAction(e -> selectGame("Dragon"));
        }
        if (slideSolveButton != null) {
            slideSolveButton.setOnAction(e -> selectGame("Slide & Solve"));
        }
        if (clicExpressButton != null) {
            clicExpressButton.setOnAction(e -> selectGame("Clic Express"));
        }
    }

    private void selectGame(String gameName) {
        System.out.println("Jeu sélectionné : " + gameName);
        // Ajouter la logique pour marquer le jeu comme sélectionné
    }

    @FXML
    private void onStartGamesClicked() {
        System.out.println("Démarrage des jeux...");
        // Ajouter la logique pour démarrer les jeux
    }

    @FXML
    private void onCloseButtonGClicked() {
        Stage stage = (Stage) closeButtonG.getScene().getWindow();
        stage.close();
    }

    // Avatar List Controller
    @FXML
    private void onValidateAvatarButtonClicked() throws IOException {
        RadioButton selected = (RadioButton) avatarToggle.getSelectedToggle();
        if (selected != null) {
            // Récupérer les données de l'avatar sélectionné
            if (selected == avatarDefaultRadio) {
                selectedAvatarName = "Joueur";
                selectedAvatarImage = "defaultAvatar.png";
            } else {
                // Récupérer les données depuis le userData du RadioButton
                String userData = (String) selected.getUserData();
                if (userData != null && userData.contains("|")) {
                    String[] parts = userData.split("\\|");
                    selectedAvatarName = parts[0];
                    selectedAvatarImage = parts[1];
                    selectedHairstyle = selectedAvatarImage.contains("_1_") ? "Coiffure 1" : "Coiffure 2";
                    selectedOutfit = selectedAvatarImage.contains("_1.png") ? "Tenue 1" : "Tenue 2";
                    selectedGender = selectedAvatarImage.contains("Homme") ? "homme" : "femme";

                    AvatarManager.getInstance().saveAvatar(
                            selectedGender.equals("homme") ? "Homme" : "Femme",
                            selectedHairstyle.equals("Coiffure 1") ? "1" : "2",
                            selectedOutfit.equals("Tenue 1") ? "1" : "2"
                    );
                }
            }

            System.out.println("Avatar choisi : " + selectedAvatarName + " - " + selectedAvatarImage);

            // Naviguer vers la salle principale
            Parent sallePrincipalRoot = FXMLLoader.load(
                    getClass().getResource("/com/example/project_ihm/fxml/SallePrincipalView.fxml")
            );

            Stage stage = (Stage) validateAvatarButton.getScene().getWindow();
            stage.getScene().setRoot(sallePrincipalRoot);
            stage.setTitle("Salle Principale");
        }
    }

    @FXML
    private void onretourAvatarButtonClicked(){
        try {
            // Retour à l'écran de création d'avatar
            Parent AccueilRoot = FXMLLoader.load(
                    getClass().getResource("/com/example/project_ihm/fxml/AccueilView.fxml")
            );

            Stage stage = (Stage) validateAvatarButton.getScene().getWindow();
            stage.getScene().setRoot(AccueilRoot);
            stage.setTitle("Jeu Éducatif - Menu Principal");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onCloseAvatarListClicked() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) closeAvatarList.getScene().getWindow();
        stage.close();
    }

    //chooseGenderController
    @FXML
    private void onNextClicked() throws IOException {
        RadioButton selected = (RadioButton) genderToggle.getSelectedToggle();
        if (selected != null) {
            // Sauvegarder le genre sélectionné
            if (selected == maleRadio) {
                selectedGender = "homme";
            } else {
                selectedGender = "femme";
            }

            System.out.println("Genre choisi : " + selectedGender);

            // Naviguer vers le choix de coiffure
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/project_ihm/fxml/ChooseHairstyleView.fxml")
            );
            Parent chooseHairstyleRoot = loader.load();

            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.getScene().setRoot(chooseHairstyleRoot);
            stage.setTitle("Choisir la coiffure");
        }
    }

    // ChooseHairstyleController
    @FXML
    private void onValidateHairstyleClicked() throws IOException {
        if (hairstyleToggle != null) {
            RadioButton selected = (RadioButton) hairstyleToggle.getSelectedToggle();
            if (selected != null) {
                if (selected == hairstyle1Radio) {
                    selectedHairstyle = "Coiffure 1";
                } else {
                    selectedHairstyle = "Coiffure 2";
                }
            }
        }

        System.out.println("Coiffure choisie : " + selectedHairstyle);

        // Naviguer vers le choix de tenue
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/project_ihm/fxml/ChooseOutfitView.fxml")
        );
        Parent chooseOutfitRoot = loader.load();

        Stage stage = (Stage) validateHairstyleButton.getScene().getWindow();
        stage.getScene().setRoot(chooseOutfitRoot);
        stage.setTitle("Choisir la tenue");
    }

    @FXML
    private void onCloseChooseHairstyleClicked() {
        Stage stage = (Stage) closeChooseHairstyle.getScene().getWindow();
        stage.close();
    }

    //chooseOutfitController
    @FXML
    private void onNextOutfitClicked() throws IOException {
        RadioButton selected = (RadioButton) outfitToggle.getSelectedToggle();
        if (selected != null) {
            if (selected == outfit1Radio) {
                selectedOutfit = "Tenue 1";
            } else {
                selectedOutfit = "Tenue 2";
            }

            System.out.println("Tenue choisie : " + selectedOutfit);

            // Naviguer vers l'écran de confirmation
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/project_ihm/fxml/AvatarConfirmationView.fxml")
            );
            Parent avatarConfirmationRoot = loader.load();

            Stage stage = (Stage) nextOutfitButton.getScene().getWindow();
            stage.getScene().setRoot(avatarConfirmationRoot);
            stage.setTitle("Confirmation de l'avatar");
        }
    }

    // AvatarConfirmationController methods
    private void initializeAvatarConfirmation() {
        // Mettre à jour les labels avec les choix effectués
        if (genderLabel != null) {
            genderLabel.setText("Genre : " + (selectedGender.equals("homme") ? "Homme" : "Femme"));
        }
        if (hairstyleLabel != null) {
            hairstyleLabel.setText("Coiffure : " + selectedHairstyle);
        }
        if (outfitLabel != null) {
            outfitLabel.setText("Tenue : " + selectedOutfit);
        }

        // Créer le chemin de l'image à partir des variables sélectionnées
        String avatarImagePath = String.format("/com/example/project_ihm/images/Avatar_%s_%s_%s.png",
                selectedGender.equals("homme") ? "Homme" : "Femme",
                selectedHairstyle.equals("Coiffure 1") ? "1" : "2",
                selectedOutfit.equals("Tenue 1") ? "1" : "2");

        try {
            avatarDisplay.setFill(new ImagePattern(new Image(getClass().getResourceAsStream(avatarImagePath))));
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image de l'avatar : " + e.getMessage());
        }

        // Save the avatar details in AvatarManager
        AvatarManager.getInstance().saveAvatar(
                selectedGender.equals("homme") ? "Homme" : "Femme",
                selectedHairstyle.equals("Coiffure 1") ? "1" : "2",
                selectedOutfit.equals("Tenue 1") ? "1" : "2"
        );
    }

    @FXML
    private void onBackButtonClicked() throws IOException {
        // Retour au choix de tenue
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/project_ihm/fxml/ChooseOutfitView.fxml")
        );
        Parent chooseOutfitRoot = loader.load();

        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.getScene().setRoot(chooseOutfitRoot);
        stage.setTitle("Choisir la tenue");
    }

    @FXML
    private void onSaveAvatarPopupButtonClicked() throws IOException {
        String avatarName = avatarNameField.getText().trim();
        if (avatarName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez entrer un nom pour votre avatar.");
            alert.showAndWait();
            return;
        }

        int coiffure = selectedHairstyle.equals("Coiffure 1") ? 1 : 2;
        int tenue = selectedOutfit.equals("Tenue 1") ? 1 : 2;
        String genreTexte = selectedGender.equals("homme") ? "homme" : "femme";

        Avatar avatar = new Avatar(
                avatarName,
                coiffure,
                "femme".equalsIgnoreCase(genreTexte) ? Genre.Femme : Genre.Homme,
                tenue
        );

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_ihm/fxml/EnoncerAvatar.fxml"));
        Parent root = loader.load();

        SaveAvatarController controller = loader.getController();
        controller.setAvatar(avatar);

        Stage dialog = new Stage();
        dialog.setScene(new Scene(root));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(confirmAvatarButton.getScene().getWindow());
        dialog.showAndWait();
    }

    @FXML
    private void onConfirmAvatarButtonClicked() throws IOException {
        String avatarName = avatarNameField.getText().trim();
        if (avatarName.isEmpty()) {
            avatarName = "Avatar par défaut";
        }

        System.out.println("Avatar confirmé !");
        System.out.println("Nom : " + avatarName);
        System.out.println("Genre : " + selectedGender);
        System.out.println("Coiffure : " + selectedHairstyle);
        System.out.println("Tenue : " + selectedOutfit);

        // Naviguer vers la salle principale
        Parent sallePrincipalRoot = FXMLLoader.load(
                getClass().getResource("/com/example/project_ihm/fxml/SallePrincipalView.fxml")
        );

        Stage stage = (Stage) confirmAvatarButton.getScene().getWindow();
        stage.getScene().setRoot(sallePrincipalRoot);
        stage.setTitle("Salle Principale");
    }

    @FXML
    private void onCloseConfirmationClicked() {
        Stage stage = (Stage) closeConfirmation.getScene().getWindow();
        stage.close();
    }

    private void loadAvatarsFromFile() {
        try {
            // Lire le fichier avatars.txt
            InputStream is = getClass().getResourceAsStream("/com/example/project_ihm/Donnee/avatars.txt");
            if (is == null) {
                System.err.println("Fichier avatars.txt non trouvé");
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            int avatarCount = 0;

            while ((line = reader.readLine()) != null && avatarCount < 2) {
                String[] parts = line.trim().split(" ");
                if (parts.length >= 2) {
                    String nom = parts[0];
                    String imageName = parts[1];

                    avatarCount++;

                    // Configurer l'avatar 1 ou 2
                    if (avatarCount == 1) {
                        setupAvatarBox(avatar1Box, avatar1Radio, avatar1Image, avatar1Label, nom, imageName);
                    } else if (avatarCount == 2) {
                        setupAvatarBox(avatar2Box, avatar2Radio, avatar2Image, avatar2Label, nom, imageName);
                    }
                }
            }
            reader.close();

        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier avatars.txt : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupAvatarBox(HBox box, RadioButton radio, ImageView imageView, Label label, String nom, String imageName) {
        try {
            // Rendre visible la box
            box.setVisible(true);
            box.setManaged(true);

            // Configurer le label
            label.setText(nom);

            // Charger l'image
            String imagePath = "/com/example/project_ihm/images/" + imageName;
            InputStream imgStream = getClass().getResourceAsStream(imagePath);
            if (imgStream != null) {
                Image img = new Image(imgStream);
                imageView.setImage(img);
            } else {
                System.err.println("Image non trouvée : " + imagePath);
                // Utiliser l'image par défaut si l'image n'est pas trouvée
                InputStream defaultStream = getClass().getResourceAsStream("/com/example/project_ihm/images/defaultAvatar.png");
                if (defaultStream != null) {
                    Image defaultImg = new Image(defaultStream);
                    imageView.setImage(defaultImg);
                }
            }

            // Stocker les données dans le RadioButton
            radio.setUserData(nom + "|" + imageName);

        } catch (Exception e) {
            System.err.println("Erreur lors de la configuration de l'avatar : " + e.getMessage());
        }
    }
}