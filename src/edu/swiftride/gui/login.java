/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.swiftride.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextComponentPassword;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import edu.swiftride.services.UserService;
import edu.swiftride.utils.UserSession;

/**
 *
 * @author skann
 */
public class login extends Form{
    public login(Form Connecter){
    setTitle("Connecter");
        setLayout(BoxLayout.y());
        TextField tfemail = new TextField("","email");
        TextComponentPassword tfpassword = new TextComponentPassword().hint("mot de passe");
        Button btnconnect =new Button("se Connecter");
        btnconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                 if(UserService.getinstance().loginUser(tfemail.getText(),tfpassword.getText()).getIdrole()==2){
                            Dialog.show("Alert","Client connecter","ok",null);
                            UserSession.setUser(UserService.getinstance().loginUser(tfemail.getText(),tfpassword.getText()));
                            new UserProfile(Connecter).show();
                        }else if(UserService.getinstance().loginUser(tfemail.getText(),tfpassword.getText()).getIdrole()==1) {
                            Dialog.show("Alert","Admin Connecter","ok",null);
                            UserSession.setUser(UserService.getinstance().loginUser(tfemail.getText(),tfpassword.getText()));
                            new ListUser(Connecter).show();
                        }
                 else
                      Dialog.show("Alert","email ou mot de passe incorrect ","ok",null);      
            }});
        addAll(tfemail,tfpassword,btnconnect);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_LEFT, ev->Connecter.show());
    }
}
