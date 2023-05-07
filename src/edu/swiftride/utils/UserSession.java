/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.swiftride.utils;

import edu.swiftride.entities.User;


/**
 *
 * @author skann
 */
  public class UserSession {

    private static UserSession instance;

    private static User user;
    

    private UserSession(User user) {
        this.user=user;
    }

    public static UserSession getInstance() {
        if(instance == null) {
            instance = new UserSession(new User());
        }
        return instance;
    }



   
    public static void cleanUserSession() {
        UserSession.user = null;
       instance=null;
    }
    public static void updateUserSession(User user){
         instance = new UserSession(user);
    }

    public static User getUser() {
        return UserSession.user;
    }

    @Override
    public String toString() {
        return "UserSession{" +user.getEmail()+ '}';
    }

    public static void setUser(User user) {
        UserSession.user = user;
    }

  
}