/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.swiftride.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponentPassword;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import edu.swiftride.entities.User;
import edu.swiftride.services.UserService;
import edu.swiftride.utils.UserSession;
import java.io.IOException;

/**
 *
 * @author skann
 */
public class UserProfile extends Form {
    Image img;
    EncodedImage enc ; 
     public UserProfile(Form profile){
          
    setTitle("Profile");
   User currentUser = UserSession.getUser();
        setLayout(BoxLayout.y());
        TextField tfname = new TextField(currentUser.getNom());
        TextField tfprenom = new TextField(currentUser.getPrenom());
        TextField tfemail = new TextField(currentUser.getEmail());
        String[] items = {"Ariana", "Beja ", "Ben Arous ", "Bizerte", "Gabes", "Gafsa ", "Jendouba", "Kairouan", "Kasserine", "Kebili", "Kef", "Mahdia", "Manouba", "Medenine", "Monastir", "Nabeul", "Sfax", "Sidi Bou Zid", "Siliana", "Sousse", "Tataouine ", "Tozeur", "Tunis", "Zaghouan"};
          AutoCompleteTextField  tfville =new AutoCompleteTextField(items);   
        tfville.setText(currentUser.getVille());
        TextField tfnum_tel = new TextField(currentUser.getNum_tel());
        TextComponentPassword tfoldpassword = new TextComponentPassword().hint("mot de passe");
        TextComponentPassword tfnewpassword = new TextComponentPassword().hint("nouveau mot de passe");
         try {
            enc = EncodedImage.create("/load.png");   
        } catch (IOException ex) {
            System.out.println("err"+ex.getMessage());
        }
         // je passe l'imge from url dans Classe img
        Image photo_personnel_image = URLImage.createToStorage(enc, currentUser.getPhoto_personel_path(), currentUser.getPhoto_personel_path(), URLImage.RESIZE_SCALE_TO_FILL);
        Image photo_permis_image = URLImage.createToStorage(enc, currentUser.getPhoto_permis_path(), currentUser.getPhoto_permis_path(), URLImage.RESIZE_SCALE_TO_FILL);
        // l'image dans l'imageViewr
        ImageViewer photo_personnel_image_viewer = new ImageViewer(photo_personnel_image);
            ImageViewer photo_permis_image_viewer = new ImageViewer(photo_permis_image);
    
         Button btnModifier = new Button("Modifier profile");
         btnModifier.addActionListener(new ActionListener(){
              @Override
            public void actionPerformed(ActionEvent evt) {
              if(tfname.getText().length()==0||tfprenom.getText().length()==0||
                        tfemail.getText().length()==0||tfoldpassword.getText().length()==0||
                        tfnum_tel.getText().length()==0||tfnewpassword.getText().length()==0
                        ){
                    Dialog.show("Alert","Il reste des champs vides","ok",null);
                 }
            else {
             currentUser.setNom(tfname.getText());
             currentUser.setPrenom(tfprenom.getText());
             currentUser.setEmail(tfemail.getText());
             currentUser.setVille(tfville.getText());
             currentUser.setNum_tel(tfnum_tel.getText());
             currentUser.setPassword(tfnewpassword.getText());
             if(UserService.getinstance().updateUser(currentUser,tfoldpassword.getText()))
                 Dialog.show("Alert","Utilisateur Modifié","ok",null);
             else
                 Dialog.show("Alert","mot de passe incorrect","ok",null);
             
             
         }
              }
            
         }
         
         );
         Button btnlogout = new Button("Déconnecter");
         btnlogout.addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent evt) {
                new login(profile).show();
                UserSession.cleanUserSession();
            }
         });
        addAll(tfname,tfprenom,tfemail,tfville,tfnum_tel,tfoldpassword,tfnewpassword,photo_personnel_image_viewer,photo_permis_image_viewer,btnModifier,btnlogout);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_LEFT, ev->profile.show());
    }
}
