/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.swiftride.gui;

import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import edu.swiftride.entities.User;
import edu.swiftride.services.UserService;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

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
        Label tfname = new Label(u.getNom());
        Label tfprenom = new Label(u.getPrenom());
        Label tfemail = new Label(u.getEmail());
        Label tfcin = new Label(u.getCin());
        Label tfnum_tel = new Label(u.getNum_tel());
        Label tfnum_permis = new Label(u.getNum_permis());
        Button btndelete =new Button("delete");
         btndelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
       if(UserService.getinstance().deleteUser(u.getId())){
                            Dialog.show("Alert","Utilisateur Supprimer","ok",null);
                        }else {
                            Dialog.show("Alert","Err while connecting to server ","ok",null);
                        }
            }
         });
        addAll(tfname,tfprenom,tfemail,tfcin,tfnum_tel,tfnum_permis,btndelete);
        
        
    }
    
}
