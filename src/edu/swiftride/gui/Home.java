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
       
        setTitle("Home");
        setLayout(BoxLayout.y());
        add(new Label("choisit une option"));
        Button b1 = new Button("Ajouter Avis");
        Button b2 = new Button("Afficher avis");
       
        b1.addActionListener(e -> {
            AddAvisForm AddAvisForm = new AddAvisForm(this);
            AddAvisForm.show();
        });
        
        b2.addActionListener(e -> {
            ShowAvisForm f = new ShowAvisForm();
            f.show();
        });
        
        Button b3 = new Button("View Stats");
        b3.addActionListener(e -> {
            StatistiquePieForm statistiquePieForm = new StatistiquePieForm();
            statistiquePieForm.createPieChartForm(); // call the createPieChartForm method here
            statistiquePieForm.show();
        });    
        addAll(b1, b2, b3);
    }
}
