package edu.swiftride.gui;

import com.codename1.l10n.ParseException;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import edu.swiftride.entities.Avis;
import edu.swiftride.services.AvisService;

public class EditAvisForm extends Form {

    AvisService service = AvisService.getInstance();

    public EditAvisForm(Avis a) throws ParseException {
        TextField tfCommentaire = new TextField(a.getCommentaire(), "commentaire");
        TextField tfEtoile = new TextField(String.valueOf(a.getEtoile()), "etoile");

        add(tfCommentaire);
        add(tfEtoile);

        //button
        Button submitButton = new Button("Submit");
        submitButton.addActionListener(e -> {
            String commentaire = tfCommentaire.getText();
            int etoile = 0;

            // Contrôle de saisie de commentaire
            if (commentaire.contains("fat") || commentaire.contains("stupid")) {
                Dialog.show("Erreur", "Le commentaire ne doit pas contenir des gros mots comme 'fat' ou 'stupid'", "OK", null);
                return;
            }

            // Contrôle de saisie d'étoile
            if (tfEtoile.getText().length() == 0) {
                Dialog.show("Erreur", "Le champ d'étoile ne doit pas être vide", "OK", null);
                return;
            }
            
            try {
                etoile = Integer.parseInt(tfEtoile.getText());
            } catch (NumberFormatException ex) {
                Dialog.show("Erreur", "La valeur saisie pour l'étoile doit être numérique", "OK", null);
                return;
            }

            if (etoile < 1 || etoile > 5) {
                Dialog.show("Erreur", "La valeur saisie pour l'étoile doit être comprise entre 1 et 5", "OK", null);
                return;
            }

            a.setCommentaire(commentaire);
            a.setEtoile(etoile);

            service.editAvis(a);

            ShowAvisForm myForm = new ShowAvisForm();
            myForm.show();
        });

        Button goToFormButton = new Button("Go back");
        goToFormButton.addActionListener(e -> {
            ShowAvisForm myForm = new ShowAvisForm();
            myForm.show();
        });

        Button deleteButton = new Button("Delete");
        deleteButton.addActionListener(e -> {
            service.deleteAvis(a.getId());

            ShowAvisForm myForm = new ShowAvisForm();
            myForm.show();
        });

        add(deleteButton);
        add(goToFormButton);
        add(submitButton);
    }
}
