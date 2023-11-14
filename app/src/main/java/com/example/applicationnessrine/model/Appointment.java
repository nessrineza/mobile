package com.example.applicationnessrine.model;


import java.io.Serializable;

public class Appointment implements Serializable {
    private long id;
    private String title;
    private String location;
    private String date;
    private String time;

    // Constructeur par défaut
    public Appointment() {
    }

    // Constructeur avec paramètres
    public Appointment(long id, String title, String location, String date, String time) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.date = date;
        this.time = time;
    }

    public Appointment(String s, String s1) {

    }

    // Getters et Setters pour id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Getters et Setters pour title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getters et Setters pour location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Getters et Setters pour date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getters et Setters pour time
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Méthode toString pour afficher les informations de l'objet
    @Override
    public String toString() {
        return "ID: " + id + "\nTitle: " + title + "\nLocation: " + location + "\nDate: " + date + "\nTime: " + time;
    }
}


