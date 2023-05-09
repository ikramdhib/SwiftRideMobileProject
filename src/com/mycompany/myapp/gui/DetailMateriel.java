/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.controllers.ServiceMateriel;
import com.mycompany.myapp.entities.Garage;
import com.mycompany.myapp.entities.Materiel;

/**
 *
 * @author dhibi
 */
public class DetailMateriel extends Form{
    
    public DetailMateriel( int id ) {
        
      setTitle("LES MATERIELS");
      setLayout(BoxLayout.y());
      
        Materiel m = ServiceMateriel.getInstance().getMateriel(id);
        
        Garage g = ServiceMateriel.getInstance().getGaragedeMateriel(id);
      
          
          addElement(m , g);
          
      }

    private void addElement(Materiel m, Garage g) {
        
        
         Label tfname = new Label(g.getMatricule_garage());
        Label tfprenom = new Label(g.getLocalisation());
        Label tfemail = new Label(""+g.getSurface());
        
        Label toto = new Label(tfname.getText()+" / "+tfprenom.getText()+" : "+ tfemail.getText());
    
            Label lnom  = new Label(m.getNom());
            Label lref = new Label(m.getPhoto());
         
            Label totos = new Label(lnom.getText()+" / "+lref.getText());
         
    
        addAll(toto , totos);
    }
       
    
    
}
