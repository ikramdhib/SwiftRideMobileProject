package edu.swiftride.gui;


import com.codename1.l10n.ParseException;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.MultiList;
import com.codename1.ui.util.Resources;
import edu.swiftride.entities.MoyenTransport;
import edu.swiftride.services.TransportService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ListTransportsForm extends Form {
         private MultiList moyenList;
         MoyenTransport selectedMoyen = null;


    public ListTransportsForm() {

        moyenList = new MultiList(new DefaultListModel<>());
        add(moyenList);
        getAllMoyens();
        
        Toolbar toolbar = new Toolbar();
        setToolbar(toolbar);
        toolbar.addCommandToSideMenu("ajouter moyen transport", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Show the add avis form
                AddTransportForm myForm = new AddTransportForm(ListTransportsForm.this);
                myForm.show();
            }
        });

    }

    private void getAllMoyens() {
        TransportService service = TransportService.getInstance();
        ArrayList<MoyenTransport> transports = service.getAllMoyens();
        DefaultListModel<Map<String, Object>> model = (DefaultListModel<Map<String, Object>>) moyenList.getModel();
        model.removeAll();
        for (MoyenTransport m : transports) {
            Map<String, Object> item = new HashMap<>();
            item.put("Line1", m.getId());
            item.put("Line2", "Type : " + m.getType());
            item.put("Line3", "Numero_trans : " + m.getNumerotrans());

            
            model.addItem(item);
        }
        moyenList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Map<String, Object> selectedItem = (Map<String, Object>) moyenList.getSelectedItem();
                    int moyenId = (int) selectedItem.get("Line1");
                    MoyenTransport selectedMoyen = null;
                    for (MoyenTransport m : transports) {
                        if (m.getId()== moyenId) {
                            selectedMoyen = m;
                            break;
                        }
                    }
                    EditTransportForm myForm2 = new EditTransportForm(selectedMoyen);
                    myForm2.show();
                       } catch (ParseException ex) {
                    System.out.println(ex);
                }
            }
        });
 

    }
 
}


