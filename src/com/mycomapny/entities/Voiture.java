/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycomapny.entities;

/**
 *
 * @author user
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;

/**
 *
 * @author emna
 */
public class Voiture {
    
    
   private int id ;
   private String marque ;
   private String model ;
   private String etat_technique ;
   private String couleur ;
   private String matricule ;
   private int kilometrage;
   private double prix ;
   private int id_entreprise;
   private int id_utilisateur;
   
   

    public Voiture(int id, String marque, String model, String etat_technique, String couleur, String matricule, int kilometrage, double prix, int id_entreprise, int id_utilisateur) {
        this.id = id;
        this.marque = marque;
        this.model = model;
        this.etat_technique = etat_technique;
        this.couleur = couleur;
        this.matricule = matricule;
        this.kilometrage = kilometrage;
        this.prix = prix;
        this.id_entreprise = id_entreprise;
        this.id_utilisateur = id_utilisateur;
        
    }

   

    public Voiture() {
    }

    public int getId() {
        return id;
    }

    public String getMarque() {
        return marque;
    }

    public String getModel() {
        return model;
    }

    public String getEtat_technique() {
        return etat_technique;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getMatricule() {
        return matricule;
    }


    public double getPrix() {
        return prix;
    }

    public int getId_entreprise() {
        return id_entreprise;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setEtat_technique(String etat_technique) {
        this.etat_technique = etat_technique;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

   

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setId_entreprise(int id_entreprise) {
        this.id_entreprise = id_entreprise;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }
    

    @Override
    public String toString() {
        return id + " - " + marque ;
    }
   
    
    
    
}
