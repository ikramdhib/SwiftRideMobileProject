/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;

import com.codename1.ui.events.ActionListener;
import com.mycomapny.entities.Accident;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class ServiceAccident {
      //singleton 
    public static ServiceAccident instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceAccident getInstance() {
        if(instance == null )
            instance = new ServiceAccident();
        return instance ;
    }
    
    
    
    public ServiceAccident() {
        req = new ConnectionRequest();
        
    }
    //ajout 
    public void ajoutAccident(Accident accident) {
        
        String url =Statics.BASE_URL+"/createaccidentJSON?type="+accident.getType()+"&lieu="+accident.getLieu()+"&description="+accident.getDescription()+"&idVoiture="+accident.getIdVoiture(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
      
    //affichage
    
    public ArrayList<Accident>affichageAccident() {
        ArrayList<Accident> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/listeaccidentJSON";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapAccident = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapAccident.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Accident ac  = new Accident();
                        
                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());
                      
                         String type = obj.get("type").toString();
                        String lieu = obj.get("lieu").toString();
                        String description = obj.get("description").toString();
                       
                        
                        ac.setId((int)id);
                        ac.setType(type);
                        ac.setDescription(description);
                        ac.setLieu(lieu);
                     
                        
                        
                        //Date 
                        String dateString = "";
                 String dateStr = obj.get("date").toString();
                     if(dateStr.contains("timestamp") && dateStr.contains("}")){
                     String DateConverter = dateStr.substring(dateStr.indexOf("timestamp") + 10 , dateStr.lastIndexOf("}"));
                      Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    dateString = formatter.format(currentTime);
                     }
                     ac.setDate(dateString);

                        
                        //insert data into ArrayList result
                        result.add(ac);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;
        
        
    }
    
    
    
    //Detail Reclamation bensba l detail n5alihoa lel5r ba3d delete+update
    
    public Accident DetailAccident( int id , Accident accident) {
        
        String url = Statics.BASE_URL+"/accidentJSON/"+id;
        req.setUrl(url);
        
        String str  = new String(req.getResponseData());
        req.addResponseListener(((evt) -> {
        
            JSONParser jsonp = new JSONParser();
            try {
                
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                
                accident.setType(obj.get("type").toString());
                accident.setLieu(obj.get("lieu").toString());
                accident.setDescription(obj.get("description").toString());
                accident.setIdVoiture(Integer.parseInt(obj.get("idVoiture").toString()));
                
            }catch(IOException ex) {
                System.out.println("error related to sql :( "+ex.getMessage());
            }
            
            
            System.out.println("data === "+str);
            
            
            
        }));
        
              NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

              return accident;
        
        
    }


//Delete 
    public boolean deleteAccident(int id ) {
        String url = Statics.BASE_URL +"/deleteJSON/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }


//Update 
    public boolean modifierAcccident(Accident accident,int id) {
        String url = Statics.BASE_URL +"/updateJSON/"+id+"?type="+accident.getType()+"&description="+accident.getDescription()+"&lieu="+accident.getLieu();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }



}






