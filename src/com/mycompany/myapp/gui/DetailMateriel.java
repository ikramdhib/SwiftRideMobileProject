/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Command;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
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
      
       Toolbar toolbar = getToolbar();
        Command exitCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Home().show();
            }
        };
        toolbar.addCommandToLeftBar(exitCommand);
      
        Materiel m = ServiceMateriel.getInstance().getMateriel(id);
        
        Garage g = ServiceMateriel.getInstance().getGaragedeMateriel(id);
      
          
          addElement(m , g);
          
      }

    private void addElement(Materiel m, Garage g) {
        
        Label l1 = new Label("Le marteiel :"+m.getNom()+ "de reférence :  ");
         Label l2 = new Label("Disponible dans notre garage :  ");
         Label l3 = new Label(" situer à :  ");
         
        Label tfname = new Label(g.getMatricule_garage());
        Label tfprenom = new Label(g.getLocalisation());
        
       
    
            Label lnom  = new Label(m.getNom());
            Label lref = new Label(m.getPhoto());
         
         
    
        addAll(l1,lref,l2,tfname ,l3,tfprenom );
    }
       
    
    
}
