/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.swiftride.gui;

import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextComponentPassword;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import java.util.Calendar;


/**
 *
 * @author Andrew
 */
public class addUser extends Form{
    
    public addUser(Form inscription){
        
        setTitle("AddUser");
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
        Button uploadButton = new Button("Photo personnel");
/*uploadButton.addActionListener(e -> {
    if (FileChooser.isAvailable()) {
        // Create a new instance of the FileChooser
        FileChooser fc = FileChooser.create();
        
        // Set the mode to select a file for uploading
        fc.setMode(FileChooser.OPEN);
        
        // Set the file filter to only show specific file types
        fc.setFileFilter("application/pdf");
        
        // Set the action to perform when the user selects a file
        fc.addActionListener(evt -> {
            if (evt != null && evt.getSource() != null) {
                // Get the selected file
                String fileUrl = (String) evt.getSource();
                
                // TODO: Upload the file to the server
                
                // Show a success message to the user
                Dialog.show("Upload Successful", "File uploaded successfully.", "OK", null);
            }
        });
        
        // Show the FileChooser
        fc.show();
    }
});*/
        
        Button btnadd =new Button("Inscription");
        
        
        btnadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                if(tfname.getText().length()==0||tfprenom.getText().length()==0||
                        tfemail.getText().length()==0||tfpassword.getText().length()==0||
                        tfcin.getText().length()==0||tfnum_tel.getText().length()==0||
                        tfnum_permis.getText().length()==0||tfville.getText().length()==0){
                    Dialog.show("Alert","Il reste des champs vides","ok",null);
                }
                else if (tfville.getText() == null || !tfville.getText().isEmpty() || !contains(items, tfville.getText()))
                    Dialog.show("Alert","Ville invalide","ok",null);
                else{
                    
                    
                         Dialog.show("Alert","added successfuly","ok",null);
                    }
                   
                }
            }
);
        addAll(tfname,cb,btnadd,tfprenom,tfemail,tfpassword,tfcin,tfnum_tel,tfnum_permis,tfville,date_naiss);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_LEFT, ev->inscription.show());
     
        
    }
    
}
