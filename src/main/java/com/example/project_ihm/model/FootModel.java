package com.example.project_ihm.model;

import javafx.animation.AnimationTimer;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class FootModel {
    private double ballCenterX;
    private double ballCenterY;

    private static final double MIN_ANGLE = Math.toRadians(-160);
    private static final double MAX_ANGLE = Math.toRadians(-10);
    private boolean increasingAngle = true;
    private double currentAngle = -Math.PI / 2;
    private double angleSpeed = 0.02;

    private double ballVelocityX = 0;
    private double ballVelocityY = 0;

    private int score = 0;
    private boolean canShoot = true;
    private boolean ballMoving = false;

    public static final double GOAL_X_MIN = 185;
    public static final double GOAL_X_MAX = 613;
    public static final double GOAL_Y_MIN = 50;
    public static final double GOAL_Y_MAX = 150;
    private static final double ARROW_LENGTH = 100;

    private ImageView ballonImage;
    private Line arrowLine;
    private Polygon arrowHead;

    public FootModel(ImageView ballonImage, Line arrowLine, Polygon arrowHead) {
        this.ballonImage = ballonImage;
        this.arrowLine = arrowLine;
        this.arrowHead = arrowHead;

        this.ballCenterX = ballonImage.getLayoutX() + ballonImage.getFitWidth() / 2;
        this.ballCenterY = ballonImage.getLayoutY() + ballonImage.getFitHeight() / 2;
    }

    // Getters and Setters
    public double getBallCenterX() { return ballCenterX; }
    public double getBallCenterY() { return ballCenterY; }
    public double getCurrentAngle() { return currentAngle; }
    public boolean isCanShoot() { return canShoot; }
    public boolean isBallMoving() { return ballMoving; }
    public int getScore() { return score; }

    public void setCanShoot(boolean canShoot) { this.canShoot = canShoot; }
    public void setBallMoving(boolean ballMoving) { this.ballMoving = ballMoving; }
    public void setBallVelocity(double vx, double vy) {
        this.ballVelocityX = vx;
        this.ballVelocityY = vy;
    }

    public void incrementScore() { score++; }

    // Game logic methods
    public void updateArrowRotation() {
        if (increasingAngle) {
            currentAngle += angleSpeed;
            if (currentAngle >= MAX_ANGLE) {
                currentAngle = MAX_ANGLE;
                increasingAngle = false;
            }
        } else {
            currentAngle -= angleSpeed;
            if (currentAngle <= MIN_ANGLE) {
                currentAngle = MIN_ANGLE;
                increasingAngle = true;
            }
        }

        double endX = ballCenterX + ARROW_LENGTH * Math.cos(currentAngle);
        double endY = ballCenterY + ARROW_LENGTH * Math.sin(currentAngle);

        arrowLine.setStartX(ballCenterX);
        arrowLine.setStartY(ballCenterY);
        arrowLine.setEndX(endX);
        arrowLine.setEndY(endY);

        updateArrowHead(endX, endY);
    }

    private void updateArrowHead(double endX, double endY) {
        double angle = Math.atan2(endY - ballCenterY, endX - ballCenterX);
        double arrowHeadLength = 15;
        double arrowAngle = Math.PI / 6;

        double x1 = endX - arrowHeadLength * Math.cos(angle - arrowAngle);
        double y1 = endY - arrowHeadLength * Math.sin(angle - arrowAngle);
        double x2 = endX - arrowHeadLength * Math.cos(angle + arrowAngle);
        double y2 = endY - arrowHeadLength * Math.sin(angle + arrowAngle);

        arrowHead.getPoints().clear();
        arrowHead.getPoints().addAll(new Double[]{
                endX, endY,
                x1, y1,
                x2, y2
        });
    }

    public void updateBallPosition() {
        double newX = ballonImage.getLayoutX() + ballVelocityX;
        double newY = ballonImage.getLayoutY() + ballVelocityY;



        ballonImage.setLayoutX(newX);
        ballonImage.setLayoutY(newY);
    }
    public boolean isGoal() {
        Bounds bounds = ballonImage.localToScene(ballonImage.getBoundsInLocal());

        double ballCenterX = bounds.getMinX() + bounds.getWidth() / 2;
        double ballCenterY = bounds.getMinY() + bounds.getHeight() / 2;

        return ballCenterX >= GOAL_X_MIN && ballCenterX <= GOAL_X_MAX &&
                ballCenterY >= GOAL_Y_MIN && ballCenterY <= GOAL_Y_MAX;
    }


    /*public boolean isGoal() {
        double ballX = ballonImage.getLayoutX() + ballonImage.getFitWidth() / 2;
        double ballY = ballonImage.getLayoutY() + ballonImage.getFitHeight() / 2;

        return ballX >= GOAL_X_MIN && ballX <= GOAL_X_MAX &&
                ballY >= GOAL_Y_MIN && ballY <= GOAL_Y_MAX;
    }*/

    public boolean isMissed() {
        double newX = ballonImage.getLayoutX();
        double newY = ballonImage.getLayoutY();

        return newX < -100 || newX > 900 || newY < -100 || newY > 700;
    }

    public void resetBall() {
        ballonImage.setLayoutX(370);
        ballonImage.setLayoutY(380);

        ballCenterX = ballonImage.getLayoutX() + ballonImage.getFitWidth() / 2;
        ballCenterY = ballonImage.getLayoutY() + ballonImage.getFitHeight() / 2;

        ballVelocityX = 0;
        ballVelocityY = 0;

        ballMoving = false;
        canShoot = true;

        currentAngle = -Math.PI / 2;
    }

    public boolean isGameWon() {
        return score >= 3;
    }

    public boolean checkCollision(Rectangle obstacle) {
        // Obtenir les bounds dans l'espace de la scène
        Bounds ballBounds = ballonImage.localToScene(ballonImage.getBoundsInLocal());
        Bounds obstacleBounds = obstacle.localToScene(obstacle.getBoundsInLocal());

        // Debug des positions et tailles
        System.out.println("Ballon bounds: " + ballBounds);
        System.out.println("Obstacle bounds: " + obstacleBounds);

        return ballBounds.intersects(obstacleBounds);
    }

    /**
     * Méthode utilitaire pour limiter une valeur entre un min et un max
     */
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}


