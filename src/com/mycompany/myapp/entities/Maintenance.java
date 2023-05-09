/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author dhibi
 */
public class Maintenance {
    
    private int id ;
    private Date date_maintenance  ;
    private String type ; 
    private Date fin_maintenance;
    private int id_garage ;
    private int id_voiture;

    public Maintenance(int id, Date date_maintenance, String type, Date fin_maintenance, int id_garage, int id_voiture) {
        this.id = id;
        this.date_maintenance = date_maintenance;
        this.type = type;
        this.fin_maintenance = fin_maintenance;
        this.id_garage = id_garage;
        this.id_voiture = id_voiture;
    }

    public Maintenance() {
    }

    public int getId() {
        return id;
    }

    public Date getDate_maintenance() {
        return date_maintenance;
    }

    public String getType() {
        return type;
    }

    public Date getFin_maintenance() {
        return fin_maintenance;
    }

    public int getId_garage() {
        return id_garage;
    }

    public int getId_voiture() {
        return id_voiture;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate_maintenance(Date date_maintenance) {
        this.date_maintenance = date_maintenance;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFin_maintenance(Date fin_maintenance) {
        this.fin_maintenance = fin_maintenance;
    }

    public void setId_garage(int id_garage) {
        this.id_garage = id_garage;
    }

    public void setId_voiture(int id_voiture) {
        this.id_voiture = id_voiture;
    }

    @Override
    public String toString() {
        return "Maintenance{" + "id=" + id + ", date_maintenance=" + date_maintenance + ", type=" + type + ", fin_maintenance=" + fin_maintenance + ", id_garage=" + id_garage + ", id_voiture=" + id_voiture + '}';
    }

  
    
    
    
    
}
