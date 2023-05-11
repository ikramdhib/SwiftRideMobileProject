/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.swiftride.services;




import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import edu.swiftride.entities.MoyenTransport;
import com.codename1.ui.events.ActionListener;
import java.util.ArrayList;
import edu.swiftride.utils.Statics;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import edu.swiftride.gui.ListTransportsForm;
import java.io.IOException;
import java.util.Map;

/**
 
 * @author Ines
 */

    public class TransportService {
    private static final String ID_PARAM = "id";
    private static final String TYPE_PARAM = "type";
    private static final String NUMERO_TRANS_PARAM = "numero_trans";
    private ArrayList<MoyenTransport> transports ;
    private ConnectionRequest req;
    private MultipartRequest request;
    public boolean resultOk;
    private static TransportService instance = null;
    
   
    public static TransportService getInstance(){
        if(instance == null){
            instance = new TransportService();    
        }
        return instance;
    }
    
     private TransportService() {
        req = new ConnectionRequest();
        request = new MultipartRequest();
    }
    
 //methode d'affichage
     public ArrayList<MoyenTransport> getAllMoyens(){
          String url = Statics.BASE_URL+"/moyentransportmobile";
          req.setUrl(url);
          req.setPost(false);
          req.addResponseListener(new ActionListener<NetworkEvent>() {
              @Override
              public void actionPerformed(NetworkEvent evt) {
                  transports = parseMoyens(new String(req.getResponseData()));
                  req.removeResponseListener(this);
              }
          });
          NetworkManager.getInstance().addToQueueAndWait(req);
          for(MoyenTransport m : transports){
              System.out.println(m+"\n");
              
          }
         return transports;
     }  
     
     //parsing    
      public ArrayList<MoyenTransport> parseMoyens(String jsonText){

         try {
            transports = new ArrayList<MoyenTransport>();
             JSONParser j = new JSONParser();
             Map<String, Object> moyenListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) moyenListJson.get("root");
            for (Map<String, Object> obj : list) {
                MoyenTransport m = new MoyenTransport();
                float id = Float.parseFloat(obj.get("id").toString());
                m.setId((int) id);
            
                if (obj.get("type") == null) {
                    m.setType("vide");
                } else {
                    m.setType(obj.get("type").toString());
                }
            if (obj.get("numero_trans") == null) {
                m.setNumerotrans(0);
            } else {
                m.setNumerotrans((int) Float.parseFloat(obj.get("numero_trans").toString()));
            }
            
                transports.add(m); 
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return transports;
    }  
      
     
    public boolean addTransport(MoyenTransport m) {
     String url = Statics.BASE_URL+"/addmoyentransportmobile?type="+m.getType()+"&numero_trans="+m.getNumerotrans();; 
     MultipartRequest request = new MultipartRequest();  
      request.setUrl(url);
      request.setPost(true);
      request.addArgument(TYPE_PARAM, m.getType());
      request.addArgument(NUMERO_TRANS_PARAM, Integer.toString(m.getNumerotrans()));
      request.setFailSilently(true);
      request.addResponseListener(evt -> {
        resultOk = request.getResponseCode() == 200;
        if (resultOk) {
            Dialog.show("Succès", "Le moyen de transport est ajouté avec succès", "OK", null);
            
           
                //Redirect
                Form ListTransportsForm = new ListTransportsForm();
                ListTransportsForm.show();
            
            
            
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

    
    public boolean editTransport(MoyenTransport m){
        String url = Statics.BASE_URL+"/modifMoyenTransportmobile";
        req.setUrl(url);
        req.setPost(false);
        req.addArgument(ID_PARAM, String.valueOf(m.getId()));
        req.addArgument(TYPE_PARAM, m.getType());
        req.addArgument(NUMERO_TRANS_PARAM, Integer.toString(m.getNumerotrans()));
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
    
   public boolean deleteTransport(int id){
        String url = Statics.BASE_URL+"/removeMoyenTransportmobile";
        req.setUrl(url);
        req.setPost(false);
        req.addArgument(ID_PARAM,String.valueOf(id));
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