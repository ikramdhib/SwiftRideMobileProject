package edu.swiftride.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import edu.swiftride.gui.AddAvisForm;

/**
 *
 * @author SAMI
 */
public class Home extends Form {
    public Home(){
       
        setTitle("Inscription");
        setLayout(BoxLayout.y());
        add(new Label("choose an option "));
        Button b1 = new Button("ajouter ");
        Button b2 = new Button("afficher");
       
        b1.addActionListener(e -> {
        AddAvisForm AddAvisForm = new AddAvisForm(this);
        AddAvisForm.show();

    });
         b2.addActionListener(e -> {
            ShowAvisForm f = new ShowAvisForm();
            f.show();
        });
    addAll(b1,b2);
    }
    }