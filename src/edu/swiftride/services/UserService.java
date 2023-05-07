/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.swiftride.services;
import com.codename1.components.ImageViewer;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.EncodedImage;

import com.codename1.ui.events.ActionListener;
import edu.swiftride.entities.User;
import edu.swiftride.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.codename1.ui.Image;
import java.util.Map;

/**
 *
 * @author skann
 */
public class UserService {
      private static final String ID_PARAM = "id";
      private static final String NOM_PARAM = "nom";
    private static final String PRENOM_PARAM = "prenom";
          private static final String CIN_PARAM = "cin";
      private static final String DATE_NAISS_PARAM = "date_naiss";
      private static final String NUM_PERMIS_PARAM = "num_permis";
      private static final String VILLE_PARAM = "ville";
      private static final String NUM_TEL_PARAM = "num_tel";
      private static final String LOGIN_PARAM = "login";
      private static final String MDP_PARAM = "mdp";
      private static final String OLDMDP_PARAM = "oldmdp";
      private static final String PHOTO_PERSONNEL_PARAM = "photo_personnel_data";
      private static final String PHOTO_PERMIS_PARAM = "photo_permis_data";
User user;
      ArrayList<User> users ;
    ConnectionRequest req;
    MultipartRequest request;
    public boolean resultOk;
     Image img;
     Image img_photo_personnel;
     Image img_photo_permis;
    EncodedImage enc ; 
    //2  creer un attribut de type de la classe en question (static)
    public static UserService instance = null;
    
    
    //Singleton => Design Pattern qui permet de creer une seule instance d'un objet 
    
    
    //1 rendre le constructeur private
    private UserService() {
        req = new ConnectionRequest();
        request = new MultipartRequest();
    }
    
    
    //3 la methode qu'elle va ramplacer le constructeur 
    public static UserService getinstance(){
        if(instance == null){
            instance = new UserService();    
        }
        return instance;
    }
    
    public User loginUser(String email,String password){
              String url = Statics.LOGIN_URL;
              req.setUrl(url);
              req.setPost(false);
              req.addArgument(LOGIN_PARAM,email);
              req.addArgument(MDP_PARAM, password);
              
              
req.addResponseListener(new ActionListener<NetworkEvent>() {
    @Override
    public void actionPerformed(NetworkEvent evt) {
        try {
            resultOk = req.getResponseCode() == 200; //si le code return 200
            //
            
            Map<String, Object> responseData = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(req.getResponseData()), "UTF-8"));
            String message = new String(req.getResponseData());
      
            if(message.equals("email ou mot de passe incorrect"))
                user = new User();
            else
              user = parseUser(new String(req.getResponseData()));
             System.out.println(user);
            req.removeResponseListener(this);
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
});
              NetworkManager.getInstance().addToQueueAndWait(req);
              return user;
       
        
        
    }
    
    
    
    
    
    
    
    
    //methode d'ajout
    public boolean addUser(User u){
        String url = Statics.ADD_USER_URL;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(u.getDate_naiss());
        request.setUrl(url);
        request.setPost(false);
        request.addArgument(NOM_PARAM,u.getNom() );
        request.addArgument(PRENOM_PARAM, u.getPrenom());
        request.addArgument(DATE_NAISS_PARAM,dateString  );
        request.addArgument(CIN_PARAM, u.getCin()); 
        request.addArgument(NUM_PERMIS_PARAM, u.getNum_permis());
        request.addArgument(VILLE_PARAM, u.getVille());
        request.addArgument(NUM_TEL_PARAM, u.getNum_tel());
        request.addArgument(LOGIN_PARAM, u.getEmail());
        request.addArgument(MDP_PARAM, u.getPassword());
        //--------------------upload photo-------------------------
        
        request.addData(PHOTO_PERSONNEL_PARAM, u.getPhoto_personel(), "image/jpeg");
        request.addData(PHOTO_PERMIS_PARAM, u.getPhoto_permis(), "image/jpeg");
        //request.addArgument(PHOTO_PERSONNEL_PARAM, u.getPhoto_personel());
        //request.addArgument(PHOTO_PERSONNEL_PARAM, u.getPhoto_permis());
        request.setFailSilently(true);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = request.getResponseCode() == 200; //si le code return 200
                //
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOk;
       
        
        
    }
    
        public boolean updateUser(User u,String oldPassword){
              
              String url = Statics.UPDATE_USER_URL;
              req.setUrl(url);
              req.setPost(false);
              req.addArgument(ID_PARAM,String.valueOf(u.getId()));
               req.addArgument(NOM_PARAM,u.getNom() );
              req.addArgument(PRENOM_PARAM, u.getPrenom());
              req.addArgument(VILLE_PARAM, u.getVille());
              req.addArgument(NUM_TEL_PARAM, u.getNum_tel());
              req.addArgument(LOGIN_PARAM, u.getEmail());
              req.addArgument(MDP_PARAM, u.getPassword());
              req.addArgument(OLDMDP_PARAM, oldPassword);
              
            
req.addResponseListener(new ActionListener<NetworkEvent>() {
    @Override
    public void actionPerformed(NetworkEvent evt) {
        resultOk = req.getResponseCode() == 200; //si le code return 200
                      //
                      System.out.println(resultOk);
                      req.removeResponseListener(this);
    }
});
              NetworkManager.getInstance().addToQueueAndWait(req);
              return resultOk;
          
          
       
        
        
    }

    public ArrayList<User> parseUsers(String jsonText) {
        try {
            users = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> usersListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) usersListJson.get("root");
            for (Map<String, Object> obj : list) {
                User u = new User();
                float id = Float.parseFloat(obj.get("id").toString());
                
               
                u.setId((int) id);
                u.setNom(obj.get("nom").toString());
                u.setPrenom(obj.get("prenom").toString());
                u.setEmail(obj.get("login").toString());
                u.setCin(obj.get("cin").toString());
                u.setNum_permis(obj.get("num_permis").toString());
                u.setNum_tel(obj.get("num_tel").toString());
                u.setVille(obj.get("ville").toString());
                
                /*if (obj.get("name") == null) {
                    t.setName("null");
                } else {
                    t.setName(obj.get("name").toString());
                }*/
                users.add(u);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }
    
       public User parseUser(String jsonText) {
            User u = new User();
        try {

            JSONParser j = new JSONParser();
            Map<String, Object> obj
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

                float id = Float.parseFloat(obj.get("id").toString());
                 Map<String, Object> role = (Map<String, Object>) obj.get("role");
                 float idrole = Float.parseFloat(role.get("id").toString());
            System.out.println(obj.get("photoPersonel").toString());
        // l'image dans l'imageViewr
                 System.out.println(idrole);
                u.setId((int) id);
                u.setNom(obj.get("nom").toString());
                u.setPrenom(obj.get("prenom").toString());
                u.setEmail(obj.get("login").toString());
                u.setCin(obj.get("cin").toString());
                u.setNum_permis(obj.get("numPermis").toString());
                u.setNum_tel(obj.get("numTel").toString());
                u.setVille(obj.get("ville").toString());
                u.setPhoto_personel_path(obj.get("photoPersonel").toString());
                u.setPhoto_permis_path(obj.get("photoPermis").toString());
                u.setIdrole((int) idrole);
                
                /*if (obj.get("name") == null) {
                    t.setName("null");
                } else {
                    t.setName(obj.get("name").toString());
                }*/
                
            return u;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
           System.out.println(u);
        return u;
    }
    
    //methode d'affichage
     public ArrayList<User> getAllUsers(){
          String url = Statics.USER_LIST_URL;
          req.setUrl(url);
          req.setPost(false);
          req.addResponseListener(new ActionListener<NetworkEvent>() {
              @Override
              public void actionPerformed(NetworkEvent evt) {
                  users = parseUsers(new String(req.getResponseData()));
                  req.removeResponseListener(this);
              }
          });
          NetworkManager.getInstance().addToQueueAndWait(req);
         
         
         return users;
     }
       public boolean deleteUser(int id){
          String url = Statics.DELETE_USER_URL;
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
