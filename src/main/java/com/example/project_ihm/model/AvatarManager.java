package com.example.project_ihm.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class AvatarManager {

    // Instance unique (Singleton)
    private static AvatarManager instance;

    // Avatar actuel
    private Avatar currentAvatar;

    // Constructeur privé
    private AvatarManager() {
        currentAvatar = new Avatar("Joueur", 3, Genre.Homme, 3);
    }

    // Obtenir l'instance unique
    public static AvatarManager getInstance() {
        if (instance == null) {
            instance = new AvatarManager();
        }
        return instance;
    }

    public static boolean saveAvatar(Avatar avatarToSave) {
        // Chemin du fichier où enregistrer les avatars
        File file = new File("src/main/resources/com/example/project_ihm/Donnee/avatars.txt");

        try {
            // Créer le fichier s’il n’existe pas
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // crée le dossier Donnee si besoin
                file.createNewFile();
            }

            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines) {
                if (line.startsWith(avatarToSave.getNom() + " ")) {
                    return false;
                }
            }

            String genre = avatarToSave.getGenre() == Genre.Homme ? "Homme" : "Femme";
            String newLine = String.format("%s Avatar_%s_%d_%d.png\n",
                    avatarToSave.getNom(), genre,
                    avatarToSave.getCoiffure(), avatarToSave.getTenue());

            Files.write(Paths.get(file.toURI()), newLine.getBytes(), StandardOpenOption.APPEND);

            return true;

        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de l'avatar : " + e.getMessage());
            return false;
        }
    }
    public void saveAvatar(String selectedGender, String selectedHairstyle, String selectedOutfit) {
        currentAvatar.setCoiffure(Integer.parseInt(selectedHairstyle));
        currentAvatar.setGenre("femme".equalsIgnoreCase(selectedGender) ? Genre.Femme : Genre.Homme);
        currentAvatar.setTenue(Integer.parseInt(selectedOutfit));

    }

    // Créer un nouvel avatar
    public void createAvatar(String nom, String genre, int coiffure, int tenue) {
        Genre g = "femme".equalsIgnoreCase(genre) ? Genre.Femme : Genre.Homme;
        currentAvatar = new Avatar(nom, coiffure, g, tenue);
    }

    // Obtenir l'avatar actuel
    public Avatar getCurrentAvatar() {
        return currentAvatar;
    }

    // Obtenir le chemin de l'image de l'avatar
    public String getAvatarImagePath(boolean isBackView) {
        // 1. Cas par défaut si aucun avatar sélectionné
        if (currentAvatar == null) {
            return isBackView ? "/com/example/project_ihm/images/Avatar_Homme_3_3_Dos.png"
                    : "/com/example/project_ihm/images/Avatar_Homme_3_3.png";
        }

        // 2. Cas avatar personnalisé
        String gender = currentAvatar.getGenre() == Genre.Homme ? "Homme" : "Femme";
        String path = String.format("/com/example/project_ihm/images/Avatar_%s_%d_%d%s.png",
                gender,
                currentAvatar.getCoiffure(),
                currentAvatar.getTenue(),
                isBackView ? "_Dos" : "");

        // Vérifie si le fichier existe
        if (getClass().getResource(path) != null) {
            return path;
        }

        // 3. Fallback vers l'avatar par défaut
        return isBackView ? "/com/example/project_ihm/images/Avatar_Homme_3_3_Dos.png"
                : "/com/example/project_ihm/images/Avatar_Homme_3_3.png";
    }

    public Image composeAvatarImage() {
        try {
            // Dimensions de l'avatar
            int width = 200;
            int height = 300;

            // Créer un canvas pour composer l'image
            Canvas canvas = new Canvas(width, height);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            // Charger et dessiner les différentes parties
            String gender = currentAvatar.getGenre() == Genre.Homme ? "H" : "F";

            // 1. Base du corps
            String bodyPath = String.format("/com/example/project_ihm/images/body_%s.png", gender);
            Image bodyImage = loadImage(bodyPath);
            if (bodyImage != null) {
                gc.drawImage(bodyImage, 0, 0, width, height);
            }

            // 2. Coiffure
            String hairPath = String.format("/com/example/project_ihm/images/C_%s%d.png",
                    gender, currentAvatar.getCoiffure());
            Image hairImage = loadImage(hairPath);
            if (hairImage != null) {
                gc.drawImage(hairImage, 0, 0, width, height);
            }

            // 3. Tenue
            String outfitPath = String.format("/com/example/project_ihm/images/O_%s%d.png",
                    gender, currentAvatar.getTenue());
            Image outfitImage = loadImage(outfitPath);
            if (outfitImage != null) {
                gc.drawImage(outfitImage, 0, 0, width, height);
            }

            // Créer une image à partir du canvas
            WritableImage composedImage = new WritableImage(width, height);
            canvas.snapshot(null, composedImage);

            return composedImage;

        } catch (Exception e) {
            System.err.println("Erreur lors de la composition de l'avatar : " + e.getMessage());
            return loadImage("/com/example/project_ihm/images/av.png");
        }
    }

    private Image loadImage(String path) {
        try {
            if (getClass().getResource(path) != null) {
                return new Image(getClass().getResourceAsStream(path));
            }
        } catch (Exception e) {
            System.err.println("Impossible de charger l'image : " + path);
        }
        return null;
    }

    // Mettre à jour l'ImageView avec l'avatar actuel
    public void updateAvatarImageView(ImageView imageView, boolean Dos) {
        String path = getAvatarImagePath(Dos);
        try {
            Image avatarImage = new Image(getClass().getResourceAsStream(path));
            imageView.setImage(avatarImage);
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de l'avatar : " + e.getMessage());
        }
    }
}