/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycomapny.entities.Accident;
import com.mycomapny.entities.Voiture;
import com.mycompany.services.ServiceAccident;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

/**
 *
 * @author user
 */
public class AjoutAccidentForm extends Form{
    private List<Voiture> availableCars;
    Form current;
    public AjoutAccidentForm(Resources res ) {
        super("Ajout Accident",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
       
        getContentPane().setScrollVisible(false);
        
        
         tb.addSearchCommand(e ->  {
            
        });
        
        Tabs swipe = new Tabs();
        
        Label s1 = new Label();
        Label s2 = new Label();
        //
        addTab(swipe,s1, res.getImage("acc.png"),"","",res);
        //
        
         swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Mes Accidens", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Ajouter accident", barGroup);
        liste.setUIID("SelectBar");
      
        RadioButton home = RadioButton.createToggle("home", barGroup);
        home.setUIID("SelectBar");


        
        mesListes.addActionListener((e) -> {
               
        
       
        ListeAccidentForm listaccidentForm = new ListeAccidentForm(res);
        listaccidentForm.show();
            refreshTheme();
        });

        
        liste.addActionListener((e) -> {
       AjoutAccidentForm ajoutaccident = new AjoutAccidentForm(res);
        ajoutaccident.show();
            refreshTheme();
});
       home.addActionListener((e) -> {
               
       
       Home homein = new Home(res);
        homein.show();
            refreshTheme();
        });
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn( mesListes, liste,home)
                
        ));

       
        
        
        
        
         ComboBox<String> myCombo = new ComboBox<>("Collisions frontales","Collisions arrière","Accidents latéraux","Réactions en chaîne","Roulements");
         add(myCombo);
        
        TextField lieu = new TextField("", "entrer lieu!!");
        lieu.setUIID("TextFieldBlack");
        addStringValue("lieu",lieu);
        
        TextField description = new TextField("", "entrer description!!");
        description.setUIID("TextFieldBlack");
        addStringValue("Description",description);
        
        
        
      ArrayList<Voiture> voiture = ServiceAccident.getInstance().getAllid();
         
        String[] names = new String[voiture.size()];
        
        for (int i = 0; i < voiture.size(); i++) {
            names[i] =voiture.get(i).getId()+"";
        }
         
         ComboBox<String> myComboBox = new ComboBox<>(names);
        
        add( myComboBox);
      
         Picker dateTimePicker = new Picker();
        dateTimePicker.setType(Display.PICKER_TYPE_DATE_AND_TIME);
         
        
        dateTimePicker.setUIID("TextFieldBlack");
        addStringValue("dateTimePicker",dateTimePicker);
         
        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);
        
        
        Button listeAccident = new Button("Liste");
        addStringValue("", listeAccident);
        //onclick button event 

        btnAjouter.addActionListener((e) -> {
            
            
            try {
                if(lieu.getText().equals("") || description.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                }
                
                else {
                    InfiniteProgress ip = new InfiniteProgress();; //Loading  after insert data
                
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                     // Create a LocalDateTime object for the current date and time
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String selectedChoice = (String) myComboBox.getSelectedItem();
             String id = selectedChoice;
         String selectedtype = (String) myCombo.getSelectedItem();
             
          // Convert the String to an int
          int voitureValue = Integer.parseInt(id);
        // Create a new Accident object using the data entered by the user and the current date and time
                    
                     Accident a = new Accident(selectedtype,
                                  String.valueOf(lieu.getText()).toString(),String.valueOf(description.getText()).toString(),voitureValue,
                                  format.format(dateTimePicker.getDate()));
                     System.out.println("data  accident == "+a);

        // Add the new Accident object to the database using the service class
                   ServiceAccident.getInstance().ajoutAccident(a);
                    
                   
                    
                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout
                    
                    //ba3d ajout net3adaw lel ListREclamationForm
                  new ListeAccidentForm(res).show();
                     
                    
                    refreshTheme();//Actualisation
                            
                }
                
            }catch(Exception ex ) {
                ex.printStackTrace();
            }
            

         
            
            
            
        });
        
        listeAccident.addActionListener((e) -> {
          new ListeAccidentForm(res).show();
          });
       
    }
    
    
    
     private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String text, String string0, Resources res) {
       int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        
        if(image.getHeight() < size) {
            image = image.scaledHeight(size);
    }
           
        if(image.getHeight() > Display.getInstance().getDisplayHeight() / 2 ) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overLay = new Label("","ImageOverlay");
        
        
        Container page1 = 
                LayeredLayout.encloseIn(
                imageScale,
                        overLay,
                        BorderLayout.south(
                        BoxLayout.encloseY(
                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                        )
                    )
                );
        
        swipe.addTab("",res.getImage("acc.png"), page1);
    } 
    
    
    public void bindButtonSelection(Button btn , Label l ) {
        
        btn.addActionListener(e-> {
        if(btn.isSelected()) {
            updateArrowPosition(btn,l);
        }
    });
    }
 
    private void updateArrowPosition(Button btn, Label l) {
        
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()  / 2  - l.getWidth() / 2 );
        l.getParent().repaint();
    }
}
