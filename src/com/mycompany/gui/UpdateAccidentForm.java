/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycomapny.entities.Accident;
import com.mycompany.services.ServiceAccident;

/**
 *
 * @author user
 */
public class UpdateAccidentForm extends BaseForm {
    
    Form current;
    public UpdateAccidentForm(Resources res , Accident a) {
         super("UPDATE ACCIDENT",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
      
        
        
       createLineSeparator();
        
        TextField type = new TextField(a.getType() , "type" , 20 , TextField.ANY);
        TextField description = new TextField(a.getDescription() , "description" , 20 , TextField.ANY);
        TextField lieu = new TextField(a.getLieu() , "lieu" , 20 , TextField.ANY);
        Picker dateTimePicker = new Picker();
        dateTimePicker.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        dateTimePicker.setDate(dateTimePicker.getDate());
        //etat bch na3mlo comobbox bon lazm admin ya3mlleha approuver mais just chnwarikom ComboBox
        
        
        
          createLineSeparator();
        
        
        
        type.setUIID("NewsTopLine");
        description.setUIID("NewsTopLine");
        lieu.setUIID("NewsTopLine");
        
        type.setSingleLineTextArea(true);
        description.setSingleLineTextArea(true);
        lieu.setSingleLineTextArea(true);
          add(dateTimePicker);
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           a.setType(type.getText());
           a.setDescription(description.getText());
           a.setLieu(lieu.getText());
           a.setDate((dateTimePicker.getDate()).toString());
       
       //appel fonction modfier reclamation men service
       
       if(ServiceAccident.getInstance().modifierAcccident(a,a.getId())) { // if true
           new ListeAccidentForm(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListeAccidentForm(res).show();
       });
       
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label("");
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(type),
                createLineSeparator(),
                new FloatingHint(description),
                createLineSeparator(),
                new FloatingHint(lieu),
                createLineSeparator(),
                
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
        
        
    }
    
}
