/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.controllers.ServiceMateriel;
import com.mycompany.myapp.entities.Materiel;
import java.util.ArrayList;

/**
 *
 * @author dhibi
 */
public class ListMateriels extends Form{
    
    
     public ListMateriels() {
        
      setTitle("LES MATERIELS");
      setLayout(BoxLayout.y());
      ArrayList<Materiel> maateriels = ServiceMateriel.getInstance().getAllMateriels();
      
       Toolbar toolbar = getToolbar();
        Command exitCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Home().show();
            }
        };
        toolbar.addCommandToLeftBar(exitCommand);
      for(Materiel g : maateriels){
          
          addElement(g);
          
      }
       
    }

    private void addElement(Materiel m) {
        
            Label lnom  = new Label(m.getNom());
            Label lref = new Label(m.getPhoto());
            Label ldesc = new Label(m.getDescription());
        
        Label toto = new Label(lnom.getText()+" / "+lref.getText()+" : "+ lref.getText());
        Button btndelete =new Button(FontImage.MATERIAL_DELETE);
        Button btndetail = new Button(FontImage.MATERIAL_INFO);
         btndelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(ServiceMateriel.getInstance().deleteMateriel(m.getId())){
                   Dialog.show("Alert","Materiel Supprimer","ok",null);
                        }else {
                            Dialog.show("Alert","Err while connecting to server ","ok",null);
                        } 
                }

        
            
        });
         
         btndetail.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new DetailMateriel(m.getId()).show();
                }
            });
         
         
         Container buttonsContainer = new Container(new FlowLayout(Component.CENTER, Component.CENTER));
         
         buttonsContainer.addAll(btndelete , btndetail);
    
        addAll(toto,buttonsContainer);
        
    }
}
