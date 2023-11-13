package com.example.myapplication1.models;

import java.util.List;

public class Service {
    private int id;
    private String nom;
    private List<Offre> offres;

    public Service(int id, String nom, List<Offre> offres) {
        this.id = id;
        this.nom = nom;
        this.offres = offres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Offre> getOffres() {
        return offres;
    }

    public void setOffres(List<Offre> offres) {
        this.offres = offres;
    }
}