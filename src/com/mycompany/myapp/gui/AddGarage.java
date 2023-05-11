/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.controllers.ServiceGarage;
import com.mycompany.myapp.entities.Garage;

/**
 *
 * @author dhibi
 */
public class AddGarage extends Form{

    public AddGarage() {
        
        setTitle("Ajouter Garage");
        setLayout(BoxLayout.y());
        
        
        Toolbar toolbar = getToolbar();
        Command exitCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Home().show();
            }
        };
        toolbar.addCommandToLeftBar(exitCommand);
        
        TextField matricule = new TextField("","Matricule");
        TextField surface = new TextField("","Surface");
        TextField localisation = new TextField(""," Localisation");
        Button btn1 = new Button("Ajouter");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(matricule.getText().length()==0 || surface.getText().length()==0 
                        || localisation.getText().length()==0){
                    Dialog.show("Alert", "tous les champs sont obligatoire", "OK",null);
                }
                else{
                    Garage g = new Garage();
                    g.setLocalisation(localisation.getText());
                    g.setMatricule_garage(matricule.getText());
                    g.setSurface(Integer.parseInt(surface.getText().toString()));
                    
                    if(ServiceGarage.getInstance().addGarage(g)){
                        Dialog.show("INFO", "Ajouter avec succes", "ok",null);
                    }
                    else{
                         Dialog.show("alert", "Erreur de connection", "ok",null);
                    }
                }
            }
        });
        
        addAll(matricule,surface,localisation,btn1);
        
        
    }

}
