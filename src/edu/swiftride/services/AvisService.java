package edu.swiftride.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import edu.swiftride.entities.Avis;
import com.codename1.ui.events.ActionListener;
import java.util.ArrayList;
import edu.swiftride.utils.Statics;
import com.codename1.ui.Dialog;
import java.io.IOException;
import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import com.codename1.ui.Form;
import edu.swiftride.gui.ShowAvisForm;
import java.util.Map;

public class AvisService {

    private static final String ID_PARAM = "id";
    private static final String COMMENTAIRE_PARAM = "commentaire";
    private static final String ETOILE_PARAM = "etoile";
    private ArrayList<Avis> aviss;
    private ConnectionRequest req;
    private MultipartRequest request;
    public boolean resultOk;
    private static AvisService instance = null;

    public static AvisService getInstance() {
        if (instance == null) {
            instance = new AvisService();
        }
        return instance;
    }

    private AvisService() {
        req = new ConnectionRequest();
        request = new MultipartRequest();
    }

    public boolean addAvis(Avis a) {
        String url = Statics.BASE_URL + "/addavisj?commentaire=" + a.getCommentaire() + "&etoile=" + a.getEtoile();;
        MultipartRequest request = new MultipartRequest();
        request.setUrl(url);
        request.setPost(true);
        request.addArgument(COMMENTAIRE_PARAM, a.getCommentaire());
        request.addArgument(ETOILE_PARAM, Integer.toString(a.getEtoile()));
        request.setFailSilently(true);
        request.addResponseListener(evt -> {
            resultOk = request.getResponseCode() == 200;
            if (resultOk) {
                Dialog.show("Succès", "L'avis est ajouté avec succès", "OK", null);
                //Redirect
                Form showAvisForm = new ShowAvisForm();
                showAvisForm.show();

            } else {
                Dialog.show("Erreur", "Une erreur est survenue", "OK", null);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        try {
            if (request.getResponseCode() != 200) {
                throw new IOException("Response code: " + request.getResponseCode());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Dialog.show("Erreur", "Une erreur est survenue : " + ex.getMessage(), "OK", null);
            return false;
        }

        return resultOk;
    }

//methode d'affichage
    public ArrayList<Avis> getAllAvis() {
        String url = Statics.BASE_URL + "/avisj";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                aviss = parseAvis(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        for (Avis a : aviss) {
            System.out.println(a + "\n");

        }
        return aviss;
    }

    //parsing    
    public ArrayList<Avis> parseAvis(String jsonText) {

        try {
            aviss = new ArrayList<Avis>();
            JSONParser j = new JSONParser();
            Map<String, Object> avisListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) avisListJson.get("root");
            for (Map<String, Object> obj : list) {
                Avis a = new Avis();
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int) id);

                if (obj.get("commentaire") == null) {
                    a.setCommentaire("vide");
                } else {
                    a.setCommentaire(obj.get("commentaire").toString());
                }
                if (obj.get("etoile") == null) {
                    a.setEtoile(0);
                } else {
                    a.setEtoile((int) Float.parseFloat(obj.get("etoile").toString()));
                }

                aviss.add(a);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return aviss;
    }

    public boolean editAvis(Avis a) {
        String url = Statics.BASE_URL + "/modifavisj";
        req.setUrl(url);
        req.setPost(false);
        req.addArgument(ID_PARAM, String.valueOf(a.getId()));
        req.addArgument(COMMENTAIRE_PARAM, a.getCommentaire());
        req.addArgument(ETOILE_PARAM, Integer.toString(a.getEtoile()));
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public boolean deleteAvis(int id) {
        String url = Statics.BASE_URL + "/removeAvisj";
        req.setUrl(url);
        req.setPost(false);
        req.addArgument(ID_PARAM, String.valueOf(id));
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200; //si le code return 200
                //
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return resultOk;
    }

}
