/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.swiftride.gui;

import com.codename1.capture.Capture;
import com.codename1.io.Util;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextComponentPassword;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.ImageIO;
import edu.swiftride.entities.User;
import edu.swiftride.services.UserService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Calendar;




/**
 *
 * @author Andrew
 */
public class addUser extends Form{
    
    public addUser(Form inscription){
        User user=new User();
        setTitle("Inscription");
        setLayout(BoxLayout.y());
        TextField tfname = new TextField("","nom");
        TextField tfprenom = new TextField("","prenom");
        TextField tfemail = new TextField("","email");
        TextComponentPassword tfpassword = new TextComponentPassword().hint("mot de passe");
        TextField tfcin = new TextField("","cin",8,TextField.NUMERIC);
        TextField tfnum_tel = new TextField("","numéro de téléphone",8,TextField.NUMERIC);
        TextField tfnum_permis = new TextField("","numéro de permis",8,TextField.NUMERIC);
        String[] items = {"Ariana", "Beja ", "Ben Arous ", "Bizerte", "Gabes", "Gafsa ", "Jendouba", "Kairouan", "Kasserine", "Kebili", "Kef", "Mahdia", "Manouba", "Medenine", "Monastir", "Nabeul", "Sfax", "Sidi Bou Zid", "Siliana", "Sousse", "Tataouine ", "Tozeur", "Tunis", "Zaghouan"};
          AutoCompleteTextField  tfville =new AutoCompleteTextField(items);
        Picker date_naiss = new Picker();
date_naiss.setType(Display.PICKER_TYPE_DATE);
Calendar maxDate = Calendar.getInstance();
maxDate.add(Calendar.YEAR, -18);
date_naiss.setEndDate(maxDate.getTime());
        Button uploadPhotoPersonnel = new Button("Photo personnel");
  uploadPhotoPersonnel.addActionListener((evt) -> {
                String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);   
                byte[] photoData = null;
if (filePath != null) {
    try {
        Image image = Image.createImage(filePath); // create an Image object from the captured photo
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.getImageIO().save(image, baos, ImageIO.FORMAT_JPEG, 100); // save the image as a JPEG to a byte array
        photoData = baos.toByteArray(); // get the byte array of the JPEG image data
    } catch (IOException e) {
        e.printStackTrace();
    }
}
                user.setPhoto_personel(photoData);
        });
           Button uploadPhotoPermis = new Button("Photo permis");
  uploadPhotoPermis.addActionListener((evt) -> {
                String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);   
                 byte[] photoData = null;
if (filePath != null) {
    try {
        Image image = Image.createImage(filePath); // create an Image object from the captured photo
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.getImageIO().save(image, baos, ImageIO.FORMAT_JPEG, 100); // save the image as a JPEG to a byte array
        photoData = baos.toByteArray(); // get the byte array of the JPEG image data
    } catch (IOException e) {
        e.printStackTrace();
    }
}
                user.setPhoto_permis(photoData);
               
        });
        
        Button btnadd =new Button("Inscription");
        
        btnadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                if(tfname.getText().length()==0||tfprenom.getText().length()==0||
                        tfemail.getText().length()==0||tfpassword.getText().length()==0||
                        tfcin.getText().length()==0||tfnum_tel.getText().length()==0||
                        tfnum_permis.getText().length()==0){
                    Dialog.show("Alert","Il reste des champs vides","ok",null);
                }
                else if (!Arrays.asList(items).contains(tfville.getText()))
                    Dialog.show("Alert","Ville invalide","ok",null);
                else if(user.getPhoto_personel()==null)
                    Dialog.show("Alert","Séléctionner une photo personnel","ok",null);
                else if(user.getPhoto_permis()==null)
                    Dialog.show("Alert","Séléctionner une photo de permis","ok",null);
                else{
                    
                        user.setNom(tfname.getText());
                        user.setPrenom(tfprenom.getText());
                        user.setCin(tfcin.getText());
                        user.setDate_naiss(date_naiss.getDate());
                        /*String encodedEmail = Util.encodeUrl(tfemail.getText(), "UTF-8");
                        System.out.println("encoded");
                        System.out.println(encodedEmail);
                        System.out.println(tfemail.getText());*/
                        user.setEmail(tfemail.getText());   
                        user.setIdrole(2);
                        user.setNum_permis(tfnum_permis.getText());
                        user.setNum_tel(tfnum_tel.getText());
                        user.setPassword(tfpassword.getText());
                        user.setVille(tfville.getText());
                        if(UserService.getinstance().addUser(user)){
                            Dialog.show("Alert","Utilisateur ajouté","ok",null);
                        }else {
                            Dialog.show("Alert","Err while connecting to server ","ok",null);
                        }
                    
                    }
                   
                }
            }
);
        addAll(tfname,tfprenom,tfemail,tfpassword,tfcin,tfnum_tel,tfnum_permis,tfville,date_naiss,uploadPhotoPersonnel,uploadPhotoPermis,btnadd);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_LEFT, ev->inscription.show());
     
        
    }
    
}
