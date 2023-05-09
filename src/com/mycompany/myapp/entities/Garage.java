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
public class Garage {
    
    private int id ;
    private String matricule_garage ;
    private String localisation;
    private int surface;

    public Garage(int id, String matricule_garage, String localisation, int surface) {
        this.id = id;
        this.matricule_garage = matricule_garage;
        this.localisation = localisation;
        this.surface = surface;
    }

    public Garage() {
    }

    public int getId() {
        return id;
    }

    public String getMatricule_garage() {
        return matricule_garage;
    }

    public String getLocalisation() {
        return localisation;
    }

    public int getSurface() {
        return surface;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMatricule_garage(String matricule_garage) {
        this.matricule_garage = matricule_garage;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    @Override
    public String toString() {
        return "Garage{" + "id=" + id + ", matricule_garage=" + matricule_garage + ", localisation=" + localisation + ", surface=" + surface + '}';
    }
    
    
    
}
