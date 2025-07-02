package com.example.project_ihm.controllers;

import com.example.project_ihm.model.AvatarManager;
import com.example.project_ihm.model.FootModel;
import com.example.project_ihm.model.ProgressionJeu;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Contrôleur pour le jeu de football.
 * Gère la logique du jeu, les animations et les interactions utilisateur.
 */
public class FootController {
    // Éléments UI
    @FXML private ImageView avatarImage;
    @FXML private ImageView ballonImage;
    @FXML private Line arrowLine;
    @FXML private Polygon arrowHead;
    @FXML private Label scoreLabel;
    @FXML private Label instructionLabel;
    @FXML private Button backButton;

    // Obstacles (cages de but)
    @FXML private Rectangle firstGoal;
    private double firstGoalInitialX;
    private double firstGoalInitialY;

    @FXML private Rectangle secondGoal;
    private double secondGoalInitialX;
    private double secondGoalInitialY;

    // Variables pour les animations
    private Timeline firstGoalTimeline;
    private Timeline doubleGoalTimeline;
    private boolean movingRight = true;
    private boolean movingRight1 = true;
    private boolean movingRight2 = false;

    // Modèle du jeu
    private FootModel gameModel;
    private AnimationTimer arrowAnimation;
    private AnimationTimer ballAnimation;

    // État du jeu
    private int etape = 1; // Niveau actuel du jeu

    // ==================== INITIALISATION ====================

    /**
     * Initialise le contrôleur et configure le jeu.
     */
    @FXML
    private void initialize() {
        firstGoalInitialX = firstGoal.getLayoutX();
        firstGoalInitialY = firstGoal.getLayoutY();
        secondGoalInitialX = secondGoal.getLayoutX();
        secondGoalInitialY = secondGoal.getLayoutY();
        gameModel = new FootModel(ballonImage, arrowLine, arrowHead);

        // Configuration basée sur l'étape actuelle
        applyEtapeConfiguration();

        // Configuration des animations
        loadPlayerAvatar();
        setupArrowAnimation();
        setupBallAnimation();
        startArrowAnimation();

    }

    // ==================== CONFIGURATION DU JEU ====================

    /**
     * Définit l'étape (niveau) actuelle du jeu.
     * @param etape Le numéro de l'étape (1, 2 ou 3)
     */
    public void setEtape(int etape) {
        this.etape = etape;
        applyEtapeConfiguration();
    }

    /**
     * Applique la configuration spécifique à l'étape actuelle.
     */
    private void applyEtapeConfiguration() {


        // Configure la visibilité des cages
        firstGoal.setVisible(etape >= 2);
        secondGoal.setVisible(etape >= 3);

        // Arrête les animations existantes
        if (firstGoalTimeline != null) firstGoalTimeline.stop();
        if (doubleGoalTimeline != null) doubleGoalTimeline.stop();

        // Lance les animations appropriées
        if (etape == 2) {
            startFirstGoalAnimation();
        } else if (etape == 3) {
            startDoubleGoalAnimation();
        }
    }

    // ==================== GESTION DES ÉVÉNEMENTS ====================

    @FXML
    private void onMousePressed(MouseEvent event) {
        // Peut être implémenté pour des interactions supplémentaires
    }

    @FXML
    private void onMouseDragged(MouseEvent event) {
        // Peut être implémenté pour des interactions supplémentaires
    }

    /**
     * Gère le relâchement de la souris (tir du ballon).
     */
    @FXML
    private void onMouseReleased(MouseEvent event) {
        if (!gameModel.isCanShoot() || gameModel.isBallMoving()) return;

        double mouseX = event.getX();
        double mouseY = event.getY();

        // Vérifie si le clic est dans la zone du but
        if (mouseX >= gameModel.GOAL_X_MIN && mouseX <= gameModel.GOAL_X_MAX &&
                mouseY >= gameModel.GOAL_Y_MIN && mouseY <= gameModel.GOAL_Y_MAX) {
            shootBall();
        } else {
            instructionLabel.setText("Cliquez dans la zone du but pour tirer !");

            // Réinitialise le message après 2 secondes
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                instructionLabel.setText("Visez et cliquez dans le but pour tirer !");
            }));
            timeline.play();
        }
    }

    private void loadPlayerAvatar() {
        try {
            AvatarManager avatarManager = AvatarManager.getInstance();
            avatarManager.updateAvatarImageView(avatarImage,true);

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'avatar : " + e.getMessage());
        }
    }


    /**
     * Gère le clic sur le bouton de retour.
     */
    @FXML
    private void onBackButtonClicked() throws IOException {
        ballAnimation.stop();
        arrowAnimation.stop();

        // Charge la vue de la salle principale
        Parent sallePrincipalRoot = FXMLLoader.load(
                getClass().getResource("/com/example/project_ihm/fxml/SallePrincipalView.fxml")
        );

        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.getScene().setRoot(sallePrincipalRoot);
        stage.setTitle("Salle Principale");
    }

    // ==================== ANIMATIONS ====================

    /**
     * Configure l'animation de la flèche de direction.
     */
    private void setupArrowAnimation() {
        arrowAnimation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameModel.updateArrowRotation();
            }
        };
    }

    /**
     * Configure l'animation du ballon.
     */
    private void setupBallAnimation() {
        ballAnimation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameModel.updateBallPosition();

                // Détection des collisions
                // Vérification des collisions pour l'étape 3
                if (etape == 3 && (gameModel.checkCollision(firstGoal) ||
                        gameModel.checkCollision(secondGoal))) {
                    handleDoubleObstacleCollision();
                    return;
                }

                // Vérification des collisions pour l'étape 2
                if (etape == 2 && gameModel.checkCollision(firstGoal)) {
                    handleCollisionWithObstacle(firstGoal);
                    return;
                }
                // Vérifie si c'est un but
                else if (gameModel.isGoal()) {
                    handleGoal();
                }
                // Vérifie si le tir est raté
                else if (gameModel.isMissed()) {
                    handleMiss();
                }
            }
        };
    }

    /**
     * Anime la première cage (niveau 2).
     */
    private void startFirstGoalAnimation() {
        // Réinitialisation à la position de départ
        firstGoal.setLayoutX(firstGoalInitialX);
        firstGoal.setLayoutY(firstGoalInitialY);
        double minX = -350;  // bord gauche de la cage
        double maxX = 350;   // bord droit de la cage
        double speed = 2;    // vitesse du gardien

        firstGoalTimeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            double currentX = firstGoal.getX();

            if (movingRight) {
                firstGoal.setX(currentX + speed);
                if (currentX + firstGoal.getWidth() >= maxX) {
                    movingRight = false;
                }
            } else {
                firstGoal.setX(currentX - speed);
                if (currentX <= minX) {
                    movingRight = true;
                }
            }
        }));
        firstGoalTimeline.setCycleCount(Timeline.INDEFINITE);
        firstGoalTimeline.play();
    }

    private void startSecondGoalAnimation() {
        // Réinitialisation à la position de départ
        secondGoal.setLayoutX(secondGoalInitialX);
        secondGoal.setLayoutY(secondGoalInitialY);
        double minX = -350;  // Mêmes limites que firstGoal
        double maxX = 350;
        double speed = 2;    // Même vitesse

        Timeline secondTimeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            double currentX = secondGoal.getX();

            if (movingRight2) {
                secondGoal.setX(currentX + speed);
                if (currentX + secondGoal.getWidth() >= maxX) {
                    movingRight2 = false;
                }
            } else {
                secondGoal.setX(currentX - speed);
                if (currentX <= minX) {
                    movingRight2 = true;
                }
            }
        }));
        secondTimeline.setCycleCount(Timeline.INDEFINITE);
        secondTimeline.play();
    }

    private void startDoubleGoalAnimation() {
        // Utilisez les mêmes variables de contrôle pour les deux cages
        movingRight = true;  // Pour firstGoal
        movingRight2 = false; // Pour secondGoal (sens opposé)

        // Animation pour firstGoal
        firstGoalTimeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            double currentX = firstGoal.getX();
            if (movingRight) {
                firstGoal.setX(currentX + 2);
                if (currentX + firstGoal.getWidth() >= 350) movingRight = false;
            } else {
                firstGoal.setX(currentX - 2);
                if (currentX <= -350) movingRight = true;
            }
        }));
        firstGoalTimeline.setCycleCount(Timeline.INDEFINITE);

        // Animation pour secondGoal (identique mais avec movingRight2)
        doubleGoalTimeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            double currentX = secondGoal.getX();
            if (movingRight2) {
                secondGoal.setX(currentX + 2);
                if (currentX + secondGoal.getWidth() >= 350) movingRight2 = false;
            } else {
                secondGoal.setX(currentX - 2);
                if (currentX <= -350) movingRight2 = true;
            }
        }));
        doubleGoalTimeline.setCycleCount(Timeline.INDEFINITE);

        firstGoalTimeline.play();
        doubleGoalTimeline.play();
    }

    /**
     * Anime les deux cages (niveau 3).
     */

    /**
     * Démarre l'animation de la flèche.
     */
    private void startArrowAnimation() {
        if (gameModel.isCanShoot() && !gameModel.isBallMoving()) {
            arrowLine.setVisible(true);
            arrowHead.setVisible(true);
            arrowAnimation.start();
        }
    }

    /**
     * Arrête l'animation de la flèche.
     */
    private void stopArrowAnimation() {
        arrowAnimation.stop();
        arrowLine.setVisible(false);
        arrowHead.setVisible(false);
    }

    // ==================== LOGIQUE DU JEU ====================

    /**
     * Tire le ballon dans la direction actuelle.
     */
    private void shootBall() {
        stopArrowAnimation();

        double speed = 10;
        gameModel.setBallVelocity(
                speed * Math.cos(gameModel.getCurrentAngle()),
                speed * Math.sin(gameModel.getCurrentAngle())
        );

        gameModel.setCanShoot(false);
        gameModel.setBallMoving(true);

        ballAnimation.start();
    }

    /**
     * Gère un but marqué.
     */
    private void handleGoal() {
        ballAnimation.stop();

        gameModel.incrementScore();
        scoreLabel.setText("Score: " + gameModel.getScore() + "/3");
        instructionLabel.setText("BUT ! " + gameModel.getScore() + "/3");

        if (gameModel.isGameWon()) {
            handleVictory();
            // Met à jour la progression du jeu
            switch (etape) {
                case 1: ProgressionJeu.setEtape1Terminee(true); break;
                case 2: ProgressionJeu.setEtape2Terminee(true); break;
            }
        } else {
            // Réinitialise le jeu après un délai
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> resetGame()));
            timeline.play();
        }
    }

    /**
     * Gère un tir raté.
     */
    private void handleMiss() {
        ballAnimation.stop();
        instructionLabel.setText("Raté ! Essayez encore !");

        // Réinitialise le jeu après un délai
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> resetGame()));
        timeline.play();
    }

    /**
     * Réinitialise le jeu pour un nouveau tir.
     */
    private void resetGame() {
        gameModel.resetBall();
        if (etape >= 2) {
            // Réinitialisation à la position d'origine
            firstGoal.setLayoutX(firstGoalInitialX);
            firstGoal.setLayoutY(firstGoalInitialY);
            movingRight = true;
        }

        if (etape >= 3) {
            // Réinitialise secondGoal
            secondGoal.setLayoutX(secondGoalInitialX);
            secondGoal.setLayoutY(secondGoalInitialY);
            movingRight1 = true;
            movingRight2 = false;
        }
        instructionLabel.setText("Visez et cliquez dans le but pour tirer !");
        startArrowAnimation();
    }

    /**
     * Gère la victoire du joueur.
     */
    private void handleVictory() {
        instructionLabel.setText("VICTOIRE ! Vous avez marqué 3 buts !");

        // Charge l'écran de victoire après un délai
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            try {
                loadVictoryPage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }));
        timeline.play();
    }

    /**
     * Charge la page de victoire.
     */
    private void loadVictoryPage() throws IOException {
        String fxmlPath;
        if (etape == 3) {
            fxmlPath = "/com/example/project_ihm/fxml/TresorView.fxml";
        } else {
            fxmlPath = "/com/example/project_ihm/fxml/CleView.fxml";
        }

        Parent victoryRoot = FXMLLoader.load(getClass().getResource(fxmlPath));

        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.getScene().setRoot(victoryRoot);

        String titre = (etape == 3) ? "Trésor trouvé !" : "Victoire - Niveau " + etape + " Complété !";
        stage.setTitle(titre);
}

    private void handleCollisionWithObstacle(Rectangle obstacle) {
        // Arrête l'animation de l'obstacle
        if (firstGoalTimeline != null) {
            firstGoalTimeline.stop();
        }

        // Arrête le ballon
        ballAnimation.stop();

        // Affiche un message spécial
        instructionLabel.setText("Obstacle touché ! Collision avec le ballon !");

        // Réinitialise après un délai
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            resetGame();
            // Redémarre l'animation si nécessaire
            if (etape >= 2) {
                startFirstGoalAnimation();
            }
        }));
        timeline.play();
    }

    /**
     * Gère la collision avec un des obstacles en étape 3
     */
    private void handleDoubleObstacleCollision() {
        // 1. Arrêter immédiatement le ballon
        gameModel.setBallVelocity(0, 0);
        ballAnimation.stop();

        // 2. Arrêter toutes les animations d'obstacles
        if (firstGoalTimeline != null) firstGoalTimeline.stop();
        if (doubleGoalTimeline != null) doubleGoalTimeline.stop();

        // 3. Afficher le message
        instructionLabel.setText("Obstacle touché ! Collision avec le ballon !");

        // 4. Réinitialiser après délai
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            resetGame();
            if (etape >= 3) {
                startDoubleGoalAnimation();
            }
        }));
        timeline.play();
    }
}