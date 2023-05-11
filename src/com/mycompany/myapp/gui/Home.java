/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Command;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;

/**
 *
 * @author dhibi
 */
public class Home extends Form{
    
    public Home(){
            setTitle("SWIFTRIDE");
            
            
            
            Label bienVenu = new Label("BIENVENUS DANS NOTRE APPLICATION");
             Label APP = new Label("SWIFT RIDE");
            setLayout(BoxLayout.y());
            
            Toolbar toolbar = getToolbar();
            Command addgarageCmd = new Command("Ajouter Garage"){
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new AddGarage().show();
                }
            };
            Command listCmdGarage = new Command("Consulter Garage"){
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new ListGarages().show();
                }
            };
            
            Command consultMaiteriel = new Command("Consulter Materiels") {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new ListMateriels().show();
                }
            };
            Command addMaintenance = new Command("Ajouter Maintenance"){
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new AddMaintenance(1).show();
                }
            };
            Command consultMaint = new Command("Consulter Maintenances"){
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new ListMaintenance().show();
                }
            };
            
           
            
            toolbar.addCommandToOverflowMenu(addgarageCmd);
            toolbar.addCommandToOverflowMenu(listCmdGarage);
            toolbar.addCommandToOverflowMenu(consultMaiteriel);
            toolbar.addCommandToOverflowMenu(addMaintenance);
            toolbar.addCommandToOverflowMenu(consultMaint);
            
            addAll(bienVenu,APP);
       
    }
    
}
