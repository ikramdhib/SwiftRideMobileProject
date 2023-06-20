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
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Garage;
import com.mycompany.myapp.entities.Maintenance;
import com.mycompany.myapp.entities.Voiture;
import com.mycompany.myapp.utils.MaintenanceStatics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dhibi
 */
public class ServiceMaintenace {
    
   
    
    ArrayList<Maintenance> Maintenances;
    
    ConnectionRequest con ;
    
    public boolean resultOK ;
    
    //creer un var de mm type que la class 
    public static ServiceMaintenace instance =null ;
    
     private ServiceMaintenace() {
        
        con= new ConnectionRequest();
        
    }
    
    //creer meth de mm type que la class 
    public static ServiceMaintenace getInstance(){
        
        if(instance==null){
            instance = new ServiceMaintenace();
        }
        
        return instance;
    }
    
    
    //Ajouter maintenance 
    public boolean addMaintenance(Maintenance m){
        
        System.out.println(m +"tyt");
        
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = dateFormat.format(m.getDate_maintenance());
            con.addArgument("type",m.getType());
            con.addArgument("idGarage", String.valueOf(m.getId_garage()));
            con.addArgument("idVoiture", String.valueOf(m.getId_voiture()));
            con.addArgument("dateMaintenance",dateString );
        
        String url = MaintenanceStatics.ADD_MAINTENANCE;
        
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
    public ArrayList<Maintenance> parseMaintenance(String json ){
        
        Maintenances=new ArrayList<Maintenance>();
        
        JSONParser j = new JSONParser();
        
        try {
            try {
            Map<String,Object>tasklistJson=j.parseJSON(new CharArrayReader(json.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>) tasklistJson.get("root");
        for(Map<String,Object> obj :list){
            
            //format 
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
           
           String dateString = (String) obj.get("dateMaintenance");
           String dateStringFin = (String) obj.get("finMaintenance");
                
           Date dateWithoutTimezone = dateFormat.parse(dateString);
           
           Date dateWithoutTimezonef = dateFormat.parse(dateStringFin);
           
           // Parse l'offset de fuseau horaire à partir de la chaîne de caractères
            String timezoneOffsetString = dateString.substring(dateString.length() - 6);
            int hoursOffset = Integer.parseInt(timezoneOffsetString.substring(0, 3));
            int minutesOffset = Integer.parseInt(timezoneOffsetString.substring(4));
            int totalOffsetMinutes = (hoursOffset * 60) + minutesOffset;
            
            
             String timezoneOffsetStringf = dateStringFin.substring(dateStringFin.length() - 6);
            int hoursOffsetf = Integer.parseInt(timezoneOffsetStringf.substring(0, 3));
            int minutesOffsetf = Integer.parseInt(timezoneOffsetStringf.substring(4));
            int totalOffsetMinutesf = (hoursOffsetf * 60) + minutesOffsetf;
            
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateWithoutTimezone);
            calendar.add(Calendar.MINUTE, -totalOffsetMinutes);

            Calendar calendarf = Calendar.getInstance();
            calendarf.setTime(dateWithoutTimezonef);
            calendarf.add(Calendar.MINUTE, -totalOffsetMinutesf);            
            
            Date finalDate = calendar.getTime();
            Date finalDatef = calendarf.getTime();
                
            
            Maintenance m = new Maintenance();
            float id = Float.parseFloat(obj.get("id").toString());
               m.setId((int) id);
               m.setDate_maintenance(finalDate);
               m.setFin_maintenance(finalDatef);
               m.setType(obj.get("type").toString());
        
            Maintenances.add(m);
        }
        } catch (ParseException ex) {
             System.out.println("0"+ex.getMessage());
                }
        
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        return Maintenances;
    }
    
    
       
    //method afficher all 
    public ArrayList<Maintenance> getAllMateriels(){
        
        String url = MaintenanceStatics.DISPLAY_ALL_MAINTENANCE;
        
        con.setUrl(url);
        
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                Maintenances = parseMaintenance(new String(con.getResponseData()));
                con.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return Maintenances;
    }
    
    
    
    public boolean deleteMaintenance(int id){
        
        String url = MaintenanceStatics.DELETE_MAINTENANCE;
        
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
    
    
      public Garage getGaragedeMateriel( int id){
        
       Garage g = new Garage();
        
        JSONParser parser = new JSONParser();
        
        String url = MaintenanceStatics.GET_ONE;
        con.addArgument("id", id+"");
        con.setUrl(url);
        con.setPost(false);
          System.out.println(url);
         System.out.println(con.getResponseCode()+"eere");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
        
          try {
               String json = new String(con.getResponseData());
               System.out.println(json);
              Map<String , Object> response = parser.
              parseJSON(new InputStreamReader(new ByteArrayInputStream(json.getBytes())));
              
              Map<String,Object> sous_res =(Map<String,Object>) response.get("idGarage");
              
              System.out.println(sous_res+"******");
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
      
      
      
        public Maintenance getMaintenance(int id ){
        
         Maintenance mati = new Maintenance();
        
        JSONParser parser = new JSONParser();
        
        String url = MaintenanceStatics.GET_ONE;
        con.addArgument("id",id+"");
        con.setUrl(url);
        
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
              
          try {
              try {
              String json = new String(con.getResponseData());
              Map<String , Object> response = parser.
                    
              parseJSON(new InputStreamReader(new ByteArrayInputStream(json.getBytes())));
              
              SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
           
           String dateString = (String) response.get("dateMaintenance");
           String dateStringFin = (String) response.get("finMaintenance");
                
           Date dateWithoutTimezone;
              
                  dateWithoutTimezone = dateFormat.parse(dateString);
             
           
           Date dateWithoutTimezonef = dateFormat.parse(dateStringFin);
           
           // Parse l'offset de fuseau horaire à partir de la chaîne de caractères
            String timezoneOffsetString = dateString.substring(dateString.length() - 6);
            int hoursOffset = Integer.parseInt(timezoneOffsetString.substring(0, 3));
            int minutesOffset = Integer.parseInt(timezoneOffsetString.substring(4));
            int totalOffsetMinutes = (hoursOffset * 60) + minutesOffset;
            
            
             String timezoneOffsetStringf = dateStringFin.substring(dateStringFin.length() - 6);
            int hoursOffsetf = Integer.parseInt(timezoneOffsetStringf.substring(0, 3));
            int minutesOffsetf = Integer.parseInt(timezoneOffsetStringf.substring(4));
            int totalOffsetMinutesf = (hoursOffsetf * 60) + minutesOffsetf;
            
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateWithoutTimezone);
            calendar.add(Calendar.MINUTE, -totalOffsetMinutes);

            Calendar calendarf = Calendar.getInstance();
            calendarf.setTime(dateWithoutTimezonef);
            calendarf.add(Calendar.MINUTE, -totalOffsetMinutesf);            
            
            Date finalDate = calendar.getTime();
            Date finalDatef = calendarf.getTime();
                
            
            float id = Float.parseFloat(response.get("id").toString());
            
             Map<String,Object> sous_res =(Map<String,Object>) response.get("idGarage");
              Map<String,Object> sous_ress =(Map<String,Object>) response.get("idVoiture");
               float idg = Float.parseFloat(sous_res.get("id").toString());
               float idv = Float.parseFloat(sous_ress.get("id").toString());
             
                mati.setId((int) id);
                mati.setId_garage((int) idg);
                mati.setId_voiture((int) idv);
               mati.setDate_maintenance(finalDate);
               mati.setFin_maintenance(finalDatef);
               mati.setType(response.get("type").toString());
        
                  System.out.println(mati+"****************************************************");  
              
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }   
              
              
          } catch (IOException ex) {
              System.out.println(ex.getMessage());
          }
        
              con.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        
        return mati;
    }
        
        
          public Voiture getVoitureMaint( int id){
        
              Voiture v = new Voiture();
        
        JSONParser parser = new JSONParser();
        
        String url = MaintenanceStatics.GET_ONE;
        
        con.addArgument("id",id+"");
        
        con.setUrl(url);
        
        con.setPost(true);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
        
          try {
              
              String json = new String(con.getResponseData());
              
              System.out.println(json+"66666666666666666666666666666");
              
              Map<String , Object> response = parser.
                    
              parseJSON(new InputStreamReader(new ByteArrayInputStream(json.getBytes())));
              
              Map<String,Object> sous_res =(Map<String,Object>) response.get("idVoiture");
             
            v.setMarque((String)sous_res.get("marque"));
            v.setMatricule((String)sous_res.get("matricule"));
              
          } catch (IOException ex) {
              System.out.println(ex.getMessage());
          }
        
              con.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return v;
    }
          
          
    public boolean updateMaintenance(int id , Date maintenanceDate){
        
        String url = MaintenanceStatics.UPDATE_MAINTENANCE;
        
        con.setUrl(url);
        
        con.setPost(true);
        
        
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(maintenanceDate);
        
        con.addArgument("dateMaintenance", dateString);
         con.addArgument("id", id+"");
        
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
        
        
        
    return resultOK ;
    }
    
}
