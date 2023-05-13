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
public class Accident {
     
    private int id;
    private String type;
    private String date;
    private String description ;
    private String lieu ;
    private int idVoiture;

    public Accident(String type, String lieu, String description, int idVoiture,String date) {
        this.type = type;
        
        this.lieu = lieu;
          this.description = description;
          this.idVoiture = idVoiture;
        this.date = date;
        
    }

    public Accident(String type, String description, String lieu, int idVoiture) {
        this.type = type;
        this.description = description;
        this.lieu = lieu;
        this.idVoiture = idVoiture;
    }

    @Override
    public String toString() {
        return "Accident{" + "id=" + id + ", type=" + type + ", date=" + date + ", description=" + description + ", lieu=" + lieu + ", idVoiture=" + idVoiture + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getIdVoiture() {
        return idVoiture;
    }

    public void setIdVoiture(int idVoiture) {
        this.idVoiture = idVoiture;
    }

    public Accident() {
    }

    public Accident(int id, String type, String date, String description, String lieu, int idVoiture) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.description = description;
        this.lieu = lieu;
        this.idVoiture = idVoiture;
    }
}
