/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.swiftride.gui;

import com.codename1.ui.CheckBox;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import edu.swiftride.entities.User;
import edu.swiftride.services.UserService;


import java.util.ArrayList;

/**
 *
 * @author Andrew
 */
public class ListUser extends Form{
    public ListUser(Form previous){
        setTitle("List Task");
        setLayout(BoxLayout.y());
        
        ArrayList<User> users = UserService.getinstance().getAllUsers();
        for (User t :users){
            addElement(t);
        }
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_LEFT, ev->previous.show());

    }

    public void addElement(User u) {
        CheckBox cb = new CheckBox(u.getNom());
        cb.setEnabled(false);
        add(cb);
        
        
    }
    
}
