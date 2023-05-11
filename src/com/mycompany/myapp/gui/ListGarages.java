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
import com.mycompany.myapp.controllers.ServiceGarage;
import com.mycompany.myapp.entities.Garage;
import java.util.ArrayList;


/**
 *
 * @author dhibi
 */
public class ListGarages extends Form{

    public ListGarages() {
        
      setTitle("LES GARAGE");
      setLayout(BoxLayout.y());
      
       Toolbar toolbar = getToolbar();
        Command exitCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Home().show();
            }
        };
        toolbar.addCommandToLeftBar(exitCommand);
      ArrayList<Garage> garages = ServiceGarage.getInstance().getAllGarages();
      
      
      for(Garage g : garages){
          
          addElement(g);
          
      }
       
    }

    private void addElement(Garage g) {
        Label tfname = new Label(g.getMatricule_garage());
        Label tfprenom = new Label(g.getLocalisation());
        Label tfemail = new Label(""+g.getSurface());
        
        Label toto = new Label(tfname.getText()+" / "+tfprenom.getText()+" : "+ tfemail.getText());
       
        Button btnajout = new Button(FontImage.MATERIAL_ADD);
        Button btndelete =new Button(FontImage.MATERIAL_DELETE);
        
        btnajout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                new AddMateriel(g.getId()).show();
            }
        });
        
         btndelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(ServiceGarage.getInstance().deleteGarage(g.getId())){
                   Dialog.show("Alert","Garage Supprimer","ok",null);
                        }else {
                            Dialog.show("Alert","Err while connecting to server ","ok",null);
                        } 
                }
            
        });
         
          Container buttonsContainer = new Container(new FlowLayout(Component.CENTER, Component.CENTER));
         
         buttonsContainer.addAll(btndelete , btnajout);
    
        addAll(toto,buttonsContainer);
        
        
    }
    
}
