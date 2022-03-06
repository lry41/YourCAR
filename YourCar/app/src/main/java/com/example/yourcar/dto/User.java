package com.example.yourcar.dto;

public class User {


   String nom, prenom, email,password,phoneNumber;

   public User(){

   }
    public User(String nom,String prenom,String email,String password,String phoneNumber) {
        this.nom = nom;
        this.prenom=prenom;
        this.password=password;
        this.email=email;
        this.phoneNumber=phoneNumber;

    }

    public User(String email , String phoneNumber,String nom, String prenom) {
        this.prenom=prenom;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.nom=nom;

    }

    public User(String email, String password) {
        this.password=password;
        this.email=email;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

