package com.example.project_ihm.model;

import com.example.project_ihm.Jeu;

public class JeuAvecEtapes implements Jeu {

    private String nomJeu;
    private Boolean estTermine;
    private int etape;
    private int scoreNecessaire;
    private int scoreJoueur;
    private int nombreDeVies;
    private int nombreDeViesRestantes;

    public JeuAvecEtapes(String nomJeu, int scoreNecessaire, int nombreDeVies) {
        this.nomJeu = nomJeu;
        this.scoreNecessaire = scoreNecessaire;
        this.nombreDeVies = nombreDeVies;
        this.nombreDeViesRestantes = nombreDeVies;
        this.etape = 1;
        this.estTermine = false;
        this.scoreJoueur = 0;
    }

    @Override
    public void lancerJeu() {

    }

    @Override
    public Boolean estGagne() {
        return null;
    }

    @Override
    public String getNomJeu() {
        return nomJeu;
    }

    public int getEtape() {
        return etape;
    }

    public int getScoreNecessaire() {
        return scoreNecessaire;
    }

    public int getScoreJoueur() {
        return scoreJoueur;
    }

    public Boolean getEstTermine() {
        return estTermine;
    }


}
