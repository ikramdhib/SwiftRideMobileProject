/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.swiftride.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import edu.swiftride.gui.AddTransportForm;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author Ines
 */
public class Home extends Form {
    public Home(){
       
        setTitle("SWIFT RIDE");
        setLayout(BoxLayout.y());
        add(new Label("choose an option "));
        Button b1 = new Button("AJOUTER ");
        Button b2 = new Button("AFFICHER");
       
         b1.addActionListener(e -> {
            AddTransportForm addTransportForm = new AddTransportForm(this);
            addTransportForm.show();
            
        });
         b2.addActionListener(e -> {
            ListTransportsForm f = new ListTransportsForm();
            f.show();
        });
     
   
        addAll(b1,b2);
}
}
