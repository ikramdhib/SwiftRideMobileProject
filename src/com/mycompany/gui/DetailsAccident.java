/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycomapny.entities.Accident;
import com.mycompany.services.ServiceAccident;

/**
 *
 * @author user
 */
public class DetailsAccident extends BaseForm {
    
    Form current;
    public DetailsAccident(Resources res , Accident a) {
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Details ACCIDENT");
        getContentPane().setScrollVisible(false);
        
        
       
        
        TextField type = new TextField(a.getType() , "type" , 20 , TextField.ANY);
        TextField description = new TextField(a.getDescription() , "description" , 20 , TextField.ANY);
        TextField lieu = new TextField(a.getLieu() , "lieu" , 20 , TextField.ANY);
 
        //etat bch na3mlo comobbox bon lazm admin ya3mlleha approuver mais just chnwarikom ComboBox
        
        
        
        
        
        
        
        type.setUIID("NewsTopLine");
        description.setUIID("NewsTopLine");
        lieu.setUIID("NewsTopLine");
        
        type.setSingleLineTextArea(true);
        description.setSingleLineTextArea(true);
        lieu.setSingleLineTextArea(true);
        
        Button liste = new Button("Liste");
       liste.setUIID("Button");
       
       //Event onclick btnModifer
      ServiceAccident.getInstance().DetailAccident(a.getId(),a);
      
      liste.addPointerPressedListener(l ->   { 
           
          new ListeAccidentForm(res).show();
       });
       //appel fonction modfier reclamation men service
       
       
       
       
      
        
      
        show();
        
        
    }
    
}
