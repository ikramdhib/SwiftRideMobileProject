/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.l10n.SimpleDateFormat;
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
import com.mycompany.myapp.controllers.ServiceMaintenace;
import com.mycompany.myapp.entities.Maintenance;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author dhibi
 */
public class ListMaintenance extends Form{
    
      public ListMaintenance() {
        
      setTitle("LES MAINTENANCES");
      setLayout(BoxLayout.y());
       Toolbar toolbar = getToolbar();
        Command exitCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Home().show();
            }
        };
        toolbar.addCommandToLeftBar(exitCommand);
      ArrayList<Maintenance> maintenances = ServiceMaintenace.getInstance().getAllMateriels();
      
      
      for(Maintenance m : maintenances){
          
          addElement(m);
          
      }
       
    }

    private void addElement(Maintenance m) {
        
        
        Date date = m.getDate_maintenance();
        Date datef =m.getFin_maintenance();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(date);
        String dateStringf = dateFormat.format(datef);
        
           Label lnom  = new Label(dateString);
            Label lnomf  = new Label(dateStringf);
          Label ltype  = new Label(m.getType());
           Label lai1 = new Label("DE : ");
           Label lai2 = new Label("JUSQU'A : ");
        
        Button btndelete =new Button(FontImage.MATERIAL_DELETE);
        Button btndetail = new Button(FontImage.MATERIAL_INFO);
          Button btnUpdate = new Button(FontImage.MATERIAL_UPDATE);
        
        
        btndelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                if(ServiceMaintenace.getInstance().deleteMaintenance(m.getId())){
                   Dialog.show("Alert","Maintenance Supprimer","ok",null);
                        }else {
                            Dialog.show("Alert","Err while connecting to server ","ok",null);
                        } 
                }
        });
        
        
        btndetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                System.out.println(m.getId());
              new  DetailMaintenance(m.getId()).show();
            }
        });
        
        
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                Maintenance mm =ServiceMaintenace.getInstance().getMaintenance(m.getId());
                
                System.out.println("tyttytyytytyt"+mm);
                
                new UpdateMaintenance(mm).show();
            }
        });
         
         Container buttonsContainer = new Container(new FlowLayout(Component.CENTER, Component.CENTER));
         
         buttonsContainer.addAll(btndelete , btndetail , btnUpdate);
    
        addAll(lai1,lnom,lai2,lnomf,ltype,buttonsContainer);
    }
    
}
