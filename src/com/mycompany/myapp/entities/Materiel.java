/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author dhibi
 */
public class Materiel {
    
    private int id ; 
    private String nom ;
    private boolean disponibilite ;
    private int idgarage;
    private String description ;
    private String photo;

    public Materiel(int id, String nom, boolean disponibilite, int idgarage, String description, String photo) {
        this.id = id;
        this.nom = nom;
        this.disponibilite = disponibilite;
        this.idgarage = idgarage;
        this.description = description;
        this.photo = photo;
    }

    public Materiel() {
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public boolean isDisponibilite() {
        return disponibilite;
    }

    public int getIdgarage() {
        return idgarage;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDisponibilite(boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public void setIdgarage(int idgarage) {
        this.idgarage = idgarage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Materiel{" + "id=" + id + ", nom=" + nom + ", disponibilite=" + disponibilite + ", idgarage=" + idgarage + ", description=" + description + ", reference=" + photo + '}';
    }
    
    
    
    
}
