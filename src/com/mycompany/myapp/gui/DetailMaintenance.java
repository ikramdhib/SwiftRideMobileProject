/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Command;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.controllers.ServiceMaintenace;
import com.mycompany.myapp.entities.Garage;
import com.mycompany.myapp.entities.Maintenance;
import com.mycompany.myapp.entities.Voiture;
import java.util.Date;

/**
 *
 * @author dhibi
 */
public class DetailMaintenance extends Form{
    
    public  DetailMaintenance(int id){
        
        
        setTitle("Detail du Maintenance N°"+id);
        
        setLayout(BoxLayout.y());
      
        
       Toolbar toolbar = getToolbar();
        Command exitCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Home().show();
            }
        };
         toolbar.addCommandToLeftBar(exitCommand);
          
        Maintenance m=ServiceMaintenace.getInstance().getMaintenance(id) ;
        System.out.println("******y"+m);
        Garage g = ServiceMaintenace.getInstance().getGaragedeMateriel(id);
        System.out.println("******y"+g);
        Voiture v =ServiceMaintenace.getInstance().getVoitureMaint(id) ;
      System.out.println("******y"+v);
          
          addElement(m , g , v);
        
    }

    private void addElement(Maintenance m, Garage g, Voiture v) {
        
        System.out.println(m+"322222222222222222222222222222222");
        
          Label l1 = new Label("Le Rendez-vous commance a  :");
          
        Date date =m.getDate_maintenance();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(date);
        Label ldate = new Label(dateString);
          
        Date datef =m.getFin_maintenance();
        String dateStringf = dateFormat.format(datef);
        
         Label l2 = new Label("Jusqu'a :  "+dateStringf);
         Label l3 = new Label(" dans le garage : "+g.getMatricule_garage());
         Label l4 = new Label("situé en : "+g.getLocalisation());
         
         
        
       Label l5 = new Label("ce maintenance de type : "+m.getType().toUpperCase());
       
       Label l6 = new Label("fera sur la voiture :");
       Label l7 = new Label(v.getMatricule() +" / "+v.getMarque());
       
        addAll(l1,ldate,l2,l3,l4,l5,l6,l7 );
        
    }
    
}
