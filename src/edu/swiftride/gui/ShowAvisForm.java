package edu.swiftride.gui;

import com.codename1.l10n.ParseException;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.MultiList;
import edu.swiftride.entities.Avis;
import edu.swiftride.services.AvisService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.codename1.l10n.ParseException;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.MultiList;
import edu.swiftride.entities.Avis;
import edu.swiftride.services.AvisService;
import java.util.HashMap;
import java.util.Map;

public class ShowAvisForm extends Form {

    private MultiList avisList;
    Avis selectedAvis = null;

    public ShowAvisForm() {
        // Use BorderLayout to center the MultiList
        setLayout(new BorderLayout());
        avisList = new MultiList(new DefaultListModel<>());

        add(BorderLayout.CENTER, avisList);

        getAllAvis();

        // Create  toolbar
        Toolbar toolbar = new Toolbar();
        setToolbar(toolbar);
        toolbar.addCommandToSideMenu("Home", FontImage.createMaterial(FontImage.MATERIAL_HOME, toolbar.getUnselectedStyle()), e -> {
            new Home().show();
        });
        
        toolbar.addCommandToSideMenu("ajouter un avis", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Show the add avis form
                AddAvisForm myForm = new AddAvisForm(ShowAvisForm.this);
                myForm.show();
            }
        });

        toolbar.addCommandToSideMenu("Statistique", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Show the add avis form
                StatistiquePieForm statForm = new StatistiquePieForm();
                statForm.createPieChartForm();
                statForm.show();
            }
        });
    }

 private void getAllAvis() {
    AvisService service = AvisService.getInstance();
    ArrayList<Avis> aviss = service.getAllAvis();
    DefaultListModel<Map<String, Object>> model = (DefaultListModel<Map<String, Object>>) avisList.getModel();
    model.removeAll();

    // Calculate the average of etoile and total number of avis
    int totalAvis = aviss.size();
    double sumEtoile = 0;
    for (Avis a : aviss) {
        sumEtoile += a.getEtoile();
    }
    double avgEtoile = (totalAvis > 0) ? sumEtoile / totalAvis : 0;

    // Create map items for the average and total number of avis
    Map<String, Object> avgItem = new HashMap<>();
    avgItem.put("Line1", "Moyenne d'avis /" + Math.round(avgEtoile));
    model.addItem(avgItem);

    Map<String, Object> totalItem = new HashMap<>();
    totalItem.put("Line1",+ totalAvis+ "/avis");
    model.addItem(totalItem);

    // Add map items for each Avis
    for (Avis a : aviss) {
        Map<String, Object> item = new HashMap<>();
        item.put("Line1", a.getId());
        item.put("Line2", "commentaire : " + a.getCommentaire());
        item.put("Line3", "etoile : " + a.getEtoile());

        model.addItem(item);
    }

    // Attach the action listener to the MultiList
    avisList.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                Map<String, Object> selectedItem = (Map<String, Object>) avisList.getSelectedItem();
                int avisId = (int) selectedItem.get("Line1");
                Avis selectedAvis = null;
                for (Avis a : aviss) {
                    if (a.getId() == avisId) {
                        selectedAvis = a;
                        break;
                    }
                }
                EditAvisForm myForm2 = new EditAvisForm(selectedAvis);
                myForm2.show();
            } catch (ParseException ex) {
                System.out.println(ex);
            }
        }
    });
}



}
