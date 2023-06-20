/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.controllers;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Garage;
import com.mycompany.myapp.entities.Materiel;
import com.mycompany.myapp.utils.MaterielsStatics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dhibi
 */
public class ServiceMateriel {
    
      
      Materiel  mat ;
    
    ArrayList<Materiel> materiels;
    
    ConnectionRequest con ;
    
    public boolean resultOK = true;
    
    //creer un var de mm type que la class 
    public static ServiceMateriel instance =null ;

    private ServiceMateriel() {
        
        con= new ConnectionRequest();
        
    }
    
    //creer meth de mm type que la class 
    public static ServiceMateriel getInstance(){
        
        if(instance==null){
            instance = new ServiceMateriel();
        }
        
        return instance;
    }
    
    
    //methode d'ajout
    public boolean addMateriel(Materiel m , int id){
        
            String nom =m.getNom();
            String description = m.getDescription();
            String photo= m.getPhoto();
      
            
            con.addArgument("nom",nom);
            con.addArgument("photo", photo);
            con.addArgument("description", description);
        
        String url = MaterielsStatics.ADD_MATERIEL+id;
        
        con.setUrl(url);
        
        con.setPost(true);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println(con.getResponseCode()+"rrr");
                if(con.getResponseCode()== 200){
                     resultOK=true;
                     System.out.println(resultOK+"****");
                con.removeResponseCodeListener(this);
                }
            }
        });
        
        NetworkManager.getInstance().addToQueue(con);
        System.out.println(resultOK+"eeeeeeee");
        return resultOK;
    }
    
    
    
    //methode de parse 
    public ArrayList<Materiel> parseMateriels(String json ) {
        
        materiels=new ArrayList<Materiel>();
        
        JSONParser j = new JSONParser();
        
        try {
            
            
            Map<String,Object>tasklistJson=j.parseJSON(new CharArrayReader(json.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>) tasklistJson.get("root");
        for(Map<String,Object> obj :list){
            
            Materiel m = new Materiel();
            float id = Float.parseFloat(obj.get("id").toString());
             
            m.setId((int) id);
            m.setDescription(obj.get("description").toString());
            m.setPhoto(obj.get("photo").toString());
            m.setNom(obj.get("nom").toString());
        
            materiels.add(m);
        }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        return materiels;
    }
    
    
    public Garage getGaragedeMateriel( int id){
        
       Garage g = new Garage();
        
        JSONParser parser = new JSONParser();
        
        String url = MaterielsStatics.GET_ONE+id;
        
        con.setUrl(url);
        
        con.setPost(true);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
        
          try {
              Map<String , Object> response = parser.
                    
              parseJSON(new InputStreamReader(new ByteArrayInputStream(new String(con.getResponseData()).getBytes())));
              
              Map<String,Object> sous_res =(Map<String,Object>) response.get("idGarage");
               float surface = Float.parseFloat(sous_res.get("surface").toString());
              
             g.setLocalisation(sous_res.get("localisation").toString());
             g.setMatricule_garage(sous_res.get("matriculeGarage").toString());
             g.setSurface((int) surface);
              
          } catch (IOException ex) {
              System.out.println(ex.getMessage());
          }
        
              con.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return g;
    }
    
        public Materiel getMateriel(int id ){
        
         Materiel mat = new Materiel();
        
        JSONParser parser = new JSONParser();
        
        String url = MaterielsStatics.GET_ONE+id;
        
            System.out.println(url);
        con.setUrl(url);
        
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
              
          try {
              String json = new String(con.getResponseData());
              Map<String , Object> response = parser.
                    
              parseJSON(new InputStreamReader(new ByteArrayInputStream(json.getBytes())));
              
              mat.setPhoto(response.get("photo").toString());
              mat.setNom(response.get("nom").toString());
              
          } catch (IOException ex) {
              System.out.println(ex.getMessage());
          }
        
              con.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return mat;
    }
    
    
    
    
    //method afficher all 
    public ArrayList<Materiel> getAllMateriels(){
        
        String url = MaterielsStatics.DISPLAY_ALL_MATERIEL;
        
        con.setUrl(url);
        
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                materiels = parseMateriels(new String(con.getResponseData()));
                con.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return materiels;
    }
    
    
    
     public boolean deleteMateriel(int id){
        
        String url = MaterielsStatics.DELETE_MATERIEL;
        
        con.setUrl(url);
        con.setPost(false);
        
        con.addArgument("id", id+"");
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK=con.getResponseCode() == 200;
                
                con.removeResponseListener(this);
            }
        });
        
         NetworkManager.getInstance().addToQueueAndWait(con);
         
         return resultOK;
    }
    
    
    
}
