package com.example.project_ihm.model;

public class Avatar {

    private String nom;
    private int coiffure;
    private Genre genre;
    private int tenue;

    public Avatar(String nom, int coiffure, Genre genre, int tenue) {
        this.nom = nom;
        this.coiffure = coiffure;
        this.genre = genre;
        this.tenue = tenue;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getCoiffure() {
        return coiffure;
    }
    public void setCoiffure(int coiffure) {
        this.coiffure = coiffure;
    }
    public Genre getGenre() {
        return genre;
    }
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    public int getTenue() {
        return tenue;
    }
    public void setTenue(int tenue) {
        this.tenue = tenue;
    }


}
