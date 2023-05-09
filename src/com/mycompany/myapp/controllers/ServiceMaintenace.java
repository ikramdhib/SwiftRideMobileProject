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
import com.mycompany.myapp.entities.Maintenance;
import com.mycompany.myapp.utils.MaintenanceStatics;
import java.io.IOException;
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
    
    
}
