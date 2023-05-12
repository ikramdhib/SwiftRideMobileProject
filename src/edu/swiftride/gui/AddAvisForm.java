package edu.swiftride.gui;

import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import edu.swiftride.entities.Avis;
import edu.swiftride.services.AvisService;


/**
 *
 * @author Sami
 */
public class AddAvisForm extends Form {

    public AddAvisForm(Form previous) {
        setTitle("Ajouter un Avis");
        setLayout(BoxLayout.y());

        // TextField pour saisir le commentaire
        TextField tfCommentaire = new TextField("", "commentaire");

        // TextField pour saisir l'etoile
        TextField tfEtoile = new TextField("", "etoile");
        tfEtoile.setConstraint(TextField.NUMERIC);

        // Bouton pour ajouter le transport
        Button btnAjouter = new Button("Ajouter");

    btnAjouter.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt) {
        String commentaire = tfCommentaire.getText();
        int etoile = Integer.parseInt(tfEtoile.getText());

        // Vérifier si le commentaire contient des gros mots
        if (commentaire.contains("fat") || commentaire.contains("stupid")) {
            Dialog.show("Erreur", "Le commentaire contient des gros mots", "OK", null);
            return;
        }

        // Vérifier si l'étoile est entre 1 et 5
        if (etoile < 1 || etoile > 5) {
            Dialog.show("Erreur", "L'étoile doit être entre 1 et 5", "OK", null);
            return;
        }

        // Créer un objet Avis avec les valeurs saisies
        Avis a = new Avis(commentaire, etoile);

        // Appeler le service pour ajouter l'avis
        boolean result = AvisService.getInstance().addAvis(a);

        if (result) {
            // Afficher un message de succès
            Dialog.show("Succès", "L'avis a été ajouté avec succès", "OK", null);
        }
    }
});


        // Ajouter les composants à la forme
        addAll(tfCommentaire, tfEtoile, btnAjouter);

        // Ajouter un bouton de retour
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ev -> previous.showBack());
    }
}
