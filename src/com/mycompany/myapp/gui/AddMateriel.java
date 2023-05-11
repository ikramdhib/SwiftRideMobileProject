/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.controllers.ServiceMateriel;
import com.mycompany.myapp.entities.Materiel;

/**
 *
 * @author dhibi
 */
public class AddMateriel extends Form{
    
    public AddMateriel(int id ){
        setTitle("Ajouter Materiel pour garage N°"+id);
        setLayout(BoxLayout.y());
        
         Toolbar toolbar = getToolbar();
        Command exitCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Home().show();
            }
        };
        toolbar.addCommandToLeftBar(exitCommand);
        
        Label lname = new Label("Intitule :");
        TextField fnom = new TextField("", "Intitule");
        Label lref = new Label("Reference :");
        TextField fref = new TextField("", "reference");
        Label ldesc = new Label("Description :");
        TextField fdesc = new TextField("", "Description");
        Button btnAdd = new Button("Ajouter");
        
        btnAdd.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                 if(fdesc.getText().length()==0 || fref.getText().length()==0 ||
                      fnom.getText().length()==0 ){
                     Dialog.show("Alert", "tous les champs sont obligatoire", "OK",null);
                 }
                 else{
                     Materiel m = new Materiel();
                     m.setDescription(fdesc.getText());
                     m.setNom(fnom.getText());
                     m.setPhoto(fref.getText());
                     
                     if(ServiceMateriel.getInstance().addMateriel(m ,id)){
                        Dialog.show("INFO", "Ajouter avec succes", "ok",null);
                    }
                    else{
                         Dialog.show("alert", "Erreur de connection", "ok",null);
                    }
                 }
            }
        });
        
        addAll(lname,fnom,lref,fref,ldesc,fdesc,btnAdd);
    }
}
