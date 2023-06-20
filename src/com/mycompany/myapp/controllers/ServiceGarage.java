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
import com.mycompany.myapp.utils.GarageStatics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dhibi
 */
public class ServiceGarage {
  //  private static final String ID_PARAM = "id";
    
    ArrayList<Garage> garages;
    
    ConnectionRequest con ;
    
    public boolean resultOK = true;
    
    //creer un var de mm type que la class 
    public static ServiceGarage instance =null ;

    private ServiceGarage() {
        
        con= new ConnectionRequest();
        
    }
    
    //creer meth de mm type que la class 
    public static ServiceGarage getInstance(){
        
        if(instance==null){
            instance = new ServiceGarage();
        }
        
        return instance;
    }
    
    //methode d'ajout
    public boolean addGarage(Garage g){
      
        String matriculeGarage= g.getMatricule_garage();
        String localisation = g.getLocalisation();
        int surface = g.getSurface();
        
        con.addArgument("matriculeGarage", g.getMatricule_garage());
        con.addArgument("localisation", g.getLocalisation());
        con.addArgument("surface", ""+g.getSurface());
        
        String url = GarageStatics.ADD_GARAGE;
        
        con.setUrl(url);
        
        con.setPost(true);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK=con.getResponseCode()==200;
                
                con.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueue(con);
        
        return resultOK;
    }
    
    //methode de parse 
    public ArrayList<Garage> parseGarages(String json ) {
        
        garages=new ArrayList<Garage>();
        
        JSONParser j = new JSONParser();
        
        try {
            
            
            Map<String,Object>tasklistJson=j.parseJSON(new CharArrayReader(json.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>) tasklistJson.get("root");
        for(Map<String,Object> obj :list){
            
            Garage g = new Garage();
            float surface = Float.parseFloat(obj.get("surface").toString());
            float id = Float.parseFloat(obj.get("id").toString());
                   
           
             g.setMatricule_garage(obj.get("matriculeGarage").toString());
            g.setLocalisation(obj.get("localisation").toString());
            g.setSurface(0);
            g.setSurface((int)surface);
            g.setId((int) id);
            
        
            garages.add(g);
        }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        return garages;
    }
    
    
    //method afficher all 
    public ArrayList<Garage> getAllGarages(){
        
        String url = GarageStatics.DISPLAY_ALL_GARAGE;
        
        con.setUrl(url);
        
        con.setPost(true);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                garages = parseGarages(new String(con.getResponseData()));
                con.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return garages;
    }
    
    
    public boolean deleteGarage(int id){
        
        String url = GarageStatics.DELETE_GARAGE;
        
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
