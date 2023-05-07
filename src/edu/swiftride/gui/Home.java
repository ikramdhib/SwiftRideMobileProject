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

/**
 *
 * @author Andrew
 */
public class Home extends Form {
    public Home(){
        
        setTitle("Inscription");
        setLayout(BoxLayout.y());
        add(new Label("choose an option "));
        Button b1 = new Button("Crée un compte ");
        Button b2 = new Button("se connecter");

        
        b1.addActionListener(l -> new addUser(this).show());
        b2.addActionListener(l -> new login(this).show());
        addAll(b1,b2);
                
    }
    
}
