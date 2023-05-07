/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.swiftride.entities;



import java.util.Date;
/**
 *
 * @author skann
 */
public class User {

    private int id;
    private int idrole;
    private String nom;
    private String prenom;
    private String cin;
    private Date date_naiss;
    private String age;

   
    private byte[] photo_personel;
    private byte[] photo_permis;
     private String photo_personel_path;
    private String photo_permis_path;

    public String getPhoto_personel_path() {
        return photo_personel_path;
    }

    public void setPhoto_personel_path(String photo_personel_path) {
        this.photo_personel_path = photo_personel_path;
    }

    public String getPhoto_permis_path() {
        return photo_permis_path;
    }

    public void setPhoto_permis_path(String photo_permis_path) {
        this.photo_permis_path = photo_permis_path;
    }
    private String num_permis;
    private String ville;
    private String num_tel;
    private String email;
    private String password;
    public User(String nom, String prenom, String cin, Date date_naiss,String age, byte[] photo_personel, byte[] photo_permis, String num_permis, String ville, String num_tel, int idrole, String email, String password) {
        
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.date_naiss = date_naiss;
        this.age=age;
        this.photo_personel = photo_personel;
        this.photo_permis = photo_permis;
        this.num_permis = num_permis;
        this.ville = ville;
        this.num_tel = num_tel;
        this.idrole=idrole;
        this.email = email;
        this.password = password;
    }

   

    public User() {
    }

    public User(String email, String password) {
    }

    public User(int id, String nom, String prenom, String cin, Date date_naiss, String num_permis, String ville, String num_tel, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.date_naiss = date_naiss;
        this.num_permis = num_permis;
        this.ville = ville;
        this.num_tel = num_tel;
        this.email = email;
    }

   
   
    public User(String i) {
        this.nom = i;
    }

   

  
  
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
  public int getIdrole() {
        return idrole;
    }

    public void setIdrole(int idrole) {
        this.idrole = idrole;
    }
    public String getNom() {
        return nom;
    }
     public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

 

    public byte[] getPhoto_personel() {
        return photo_personel;
    }

    public void setPhoto_personel(byte[] photo_personel) {
        this.photo_personel = photo_personel;
    }

    public byte[] getPhoto_permis() {
        return photo_permis;
    }

    public void setPhoto_permis(byte[] photo_permis) {
        this.photo_permis = photo_permis;
    }

    public String getNum_permis() {
        return num_permis;
    }

    public void setNum_permis(String num_permis) {
        this.num_permis = num_permis;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
public Date getDate_naiss(){
    return this.date_naiss;
}
public void setDate_naiss(Date date_naiss){
    this.date_naiss=date_naiss;
}

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", idrole=" + idrole + ", nom=" + nom + ", prenom=" + prenom + ", cin=" + cin + ", date_naiss=" + date_naiss + ", age=" + age + ", photo_personel=" + photo_personel + ", photo_permis=" + photo_permis + ", photo_personel_path=" + photo_personel_path + ", photo_permis_path=" + photo_permis_path + ", num_permis=" + num_permis + ", ville=" + ville + ", num_tel=" + num_tel + ", email=" + email + ", password=" + password + '}';
    }

  
   



    
    
}
