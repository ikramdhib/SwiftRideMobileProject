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
    
        
    public UpdateMaintenance(int id){

        setTitle("PRENEZ UN RENDE-VOUS");
        
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
        
         Label ltype = new Label("Maintenance :");
         
          RadioButton radiobtn1 = new RadioButton("Entretien");
          RadioButton radiobtn2 = new RadioButton("Réparation");
         ButtonGroup groupr = new ButtonGroup(radiobtn1 , radiobtn2);
         
         Container buttonsContainer = new Container(new FlowLayout(Component.CENTER, Component.CENTER));
         
         buttonsContainer.addAll(radioButton1 , radioButton2 , radioButton3 , radioButton4);
         
         Container radioContainer = new Container(new FlowLayout(Component.CENTER, Component.CENTER));
         
         radioContainer.addAll(radiobtn1 , radiobtn2 );
         
         
         ArrayList<Garage> garages = ServiceGarage.getInstance().getAllGarages();
         
        String[] names = new String[garages.size()];
        
        for (int i = 0; i < garages.size(); i++) {
            names[i] =garages.get(i).getId()+" - " +garages.get(i).getMatricule_garage()+"/"+garages.get(i).getLocalisation();
        }
         
         ComboBox<String> myComboBox = new ComboBox<>(names);
         
         
         Button btnadd = new Button("Passer");
         
         btnadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                 if(date.getDate() == null || myComboBox.getSelectedIndex() == -1 ||
                      !group.isSelected() || !groupr.isSelected()){
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
            
            //recupere l'id garage
            
            String selectedChoice = (String) myComboBox.getSelectedItem();
             int firstSpaceIndex = selectedChoice.indexOf(" ");
             String garage = selectedChoice.substring(0, firstSpaceIndex);
             
             //recupere le type
            int i =  groupr.getSelectedIndex();
           Button selectedButtoni = groupr.getRadioButton(i);
           String type = (String) selectedButtoni.getText();
              
                     Maintenance m = new Maintenance();
                     m.setDate_maintenance(finalDate);
                     m.setId_garage(Integer.parseInt(garage));
                     m.setType(type);
                     m.setId_voiture(id);
                     
                     if(ServiceMaintenace.getInstance().addMaintenance(m)){
                        Dialog.show("INFO", "Ajouter avec succes", "ok",null);
                    }
                    else{
                         Dialog.show("alert", "Ce maintenance existe deja", "ok",null);
                    }
                     
                     } catch (ParseException ex) {
                         System.out.println(ex.getMessage());
                     }
                 }
         
                
            }
        });
        
        addAll(ldate , date ,buttonsContainer ,ltype , radioContainer ,myComboBox,btnadd );
        
  
    }
    
}
