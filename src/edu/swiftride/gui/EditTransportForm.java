package edu.swiftride.gui;

import com.codename1.l10n.ParseException;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import edu.swiftride.entities.MoyenTransport;
import edu.swiftride.services.TransportService;


 public class EditTransportForm extends Form {
    TransportService service = TransportService.getInstance();

    public EditTransportForm(MoyenTransport m) throws ParseException {
        TextField tfType = new TextField(m.getType(), "Type de transport");
TextField tfNumero = new TextField(String.valueOf(m.getNumerotrans()), "Numero de transport");

        add(tfType);
        add(tfNumero);

        Button submitButton = new Button("Submit");
        submitButton.addActionListener(e -> {
            String type = tfType.getText();
            m.setType(type);
            int numero_trans = Integer.parseInt(tfNumero.getText());

            m.setNumerotrans(numero_trans);

            service.editTransport(m);

            ListTransportsForm myForm = new ListTransportsForm();
            myForm.show();
        });

        Button goToFormButton = new Button("Go back");
        goToFormButton.addActionListener(e -> {
            ListTransportsForm myForm = new ListTransportsForm();
            myForm.show();
        });

        Button deleteButton = new Button("Delete");
        deleteButton.addActionListener(e -> {
            service.deleteTransport(m.getId());

            ListTransportsForm myForm = new ListTransportsForm();
            myForm.show();
        });

        add(deleteButton);
        add(goToFormButton);
        add(submitButton);
    }
}