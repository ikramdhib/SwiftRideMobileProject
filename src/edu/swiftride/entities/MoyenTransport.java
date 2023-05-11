/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.swiftride.entities;

/**
 *
 * @author Ines
 */
public class MoyenTransport {
    
private int id; 
private String type;
private int numero_trans;




public MoyenTransport(){

}
public MoyenTransport( String type, int numero_trans) {
      
        this.type= type;
        this.numero_trans= numero_trans;
}

   
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getNumerotrans() {
        return numero_trans;
    }

  
    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNumerotrans(int numero_trans) {
        this.numero_trans = numero_trans;
    }

    @Override
    public String toString() {
        return "MoyenTransport{" + "id=" + id + ", type=" + type + ", numero_trans=" + numero_trans + '}';
    }

}


