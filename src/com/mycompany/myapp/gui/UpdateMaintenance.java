/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.controllers.ServiceGarage;
import com.mycompany.myapp.controllers.ServiceMaintenace;
import com.mycompany.myapp.entities.Garage;
import com.mycompany.myapp.entities.Maintenance;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author dhibi
 */
public class UpdateMaintenance extends Form{
    
        
    public UpdateMaintenance(Maintenance m){

        setTitle("Mettre a  jour le maintenance N°"+m.getId());
        
         Toolbar toolbar = getToolbar();
        Command exitCommand = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Home().show();
            }
        };
        toolbar.addCommandToLeftBar(exitCommand);
        
        setLayout(BoxLayout.y());
        
        Label ldate = new Label("Date et heur du rendez-vous");
        
        Picker date = new Picker();
        
        RadioButton radioButton1 = new RadioButton("08:00:00");
        RadioButton radioButton2 = new RadioButton("10:30:00");
        RadioButton radioButton3 = new RadioButton("13:00:00");
        RadioButton radioButton4 = new RadioButton("15:00:00");

        // Ajouter les boutons radio à un groupe
        ButtonGroup group = new ButtonGroup(radioButton1, radioButton2, radioButton3, radioButton4);
        
       
         
         Container buttonsContainer = new Container(new FlowLayout(Component.CENTER, Component.CENTER));
         
         buttonsContainer.addAll(radioButton1 , radioButton2 , radioButton3 , radioButton4);
         
      
         
         
         Button btnadd = new Button("Modifier");
         
         btnadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                 if(date.getDate() == null || !group.isSelected() ){
                     Dialog.show("Alert", "tous les champs sont obligatoire", "OK",null);
                 }
                 else{
                     try {
                     
                     //recupere la date
                     Date d = date.getDate();
                    // Récupérer la valeur du bouton radio correspondant à l'index
                    int index =  group.getSelectedIndex();
                    Button selectedButton = group.getRadioButton(index);
                    String hour = (String) selectedButton.getText();
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = dateFormat.format(d);
                     
                    String finalStringDate=dateString.concat(" ").concat(hour);
                     SimpleDateFormat dateFormatt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     Date dateWithoutTimezone;
                      dateWithoutTimezone = dateFormatt.parse(finalStringDate);
                     
           
           // Parse l'offset de fuseau horaire à partir de la chaîne de caractères
            String timezoneOffsetString = dateString.substring(dateString.length() - 6);
            int hoursOffset = Integer.parseInt(timezoneOffsetString.substring(0, 3));
            int minutesOffset = Integer.parseInt(timezoneOffsetString.substring(4));
            int totalOffsetMinutes = (hoursOffset * 60) + minutesOffset;
            
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateWithoutTimezone);
            calendar.add(Calendar.MINUTE, -totalOffsetMinutes);          
            
            Date finalDate = calendar.getTime();
                         System.out.println(finalStringDate+"datedattttttt");
                         
                         System.out.println(m.getId()+"****8888**");
                     if(ServiceMaintenace.getInstance().updateMaintenance(m.getId(),finalDate)){
                        Dialog.show("INFO", "Ajouter avec succes", "ok",null);
                    }
                    else{
                         Dialog.show("alert", "connexion error", "ok",null);
                    }
                     
                     } catch (ParseException ex) {
                         System.out.println(ex.getMessage());
                     }
                 }
         
                
            }
        });
        
        addAll(ldate , date ,buttonsContainer ,btnadd );
        
  
    }
    
}
