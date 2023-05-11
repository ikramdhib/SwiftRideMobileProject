package edu.swiftride.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import edu.swiftride.entities.MoyenTransport;
import edu.swiftride.services.TransportService;

public class AddTransportForm extends Form {

    public AddTransportForm(Form previous) {
        setTitle("Ajouter un moyen de transport");
        setLayout(BoxLayout.y());

        // TextField pour saisir le type de transport
        TextField tfType = new TextField("", "Type de transport");

        // TextField pour saisir le numéro de transport
        TextField tfNumero = new TextField("", "Numéro de transport");

        // Bouton pour ajouter le transport
        Button btnAjouter = new Button("Ajouter");

        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String type = tfType.getText();
                String numero_trans = tfNumero.getText();

                if (type.length() == 0 || numero_trans.length() == 0) {
                    Dialog.show("Erreur", "Veuillez remplir tous les champs", "OK", null);
                } else {
                    // Créer un objet MoyenTransport avec les valeurs saisies
                    MoyenTransport m = new MoyenTransport(type, Integer.parseInt(numero_trans));

                    // Appeler le service pour ajouter le moyen de transport
                    boolean result = TransportService.getInstance().addTransport(m);

                  
                }
            }
        });

        // Ajouter les composants à la forme
        addAll(tfType, tfNumero, btnAjouter);

        // Ajouter un bouton de retour
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ev -> previous.showBack());
    }
}