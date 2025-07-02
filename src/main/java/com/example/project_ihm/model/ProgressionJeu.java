package com.example.project_ihm.model;

public class ProgressionJeu {
    private static boolean etape1Terminee = false;
    private static boolean etape2Terminee = false;

    public static boolean isEtape1Terminee() {
        return etape1Terminee;
    }

    public static void setEtape1Terminee(boolean terminee) {
        etape1Terminee = terminee;
    }

    public static boolean isEtape2Terminee() {
        return etape2Terminee;
    }

    public static void setEtape2Terminee(boolean terminee) {
        etape2Terminee = terminee;
    }
}
