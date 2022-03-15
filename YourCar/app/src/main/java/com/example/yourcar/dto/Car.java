package com.example.yourcar.dto;

import android.util.Log;

import com.example.yourcar.ConnectionDB.ConnectionBdCar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Car {
    String TAG_BD ="ERROR_BD_CAR";
    private String marque_car, model_car, carburant,url;
    private String description,numProprio;
    private int kilometrage, chevaux, annee,prix;

    private String anneeText,kmText,prixText,chevauText;



    public Car() {
    }

    public Car(String marque_car, String model_car,int annee,int chevaux,int kilometrage,int prix,String carburant, String url, String description,String numProprio){
        this.annee=annee;
        this.chevaux=chevaux;
        this.model_car=model_car;
        this.prix=prix;
        this.url=url;
        this.description=description;
        this.marque_car=marque_car;
        this.kilometrage=kilometrage;
        this.carburant=carburant;
        this.numProprio=numProprio;
    }



    public String getMarque_car() {
        return marque_car;
    }

    public void setMarque_car(String marque_car) {
        this.marque_car = marque_car;
    }

    public String getModel_car() {
        return model_car;
    }

    public void setModel_car(String model_car) {
        this.model_car = model_car;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }

    public int getChevaux() {
        return chevaux;
    }

    public void setChevaux(int chevaux) {
        this.chevaux = chevaux;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumProprio() {
        return numProprio;
    }

    public void setNumProprio(String numProprio) {
        this.numProprio = numProprio;
    }

    public String getAnneeText() {
        return anneeText;
    }

    public void setAnneeText(String anneeText) {
        this.anneeText = anneeText;
    }

    public String getKmText() {
        return kmText;
    }

    public void setKmText(String kmText) {
        this.kmText = kmText;
    }

    public String getPrixText() {
        return prixText;
    }

    public void setPrixText(String prixText) {
        this.prixText = prixText;
    }

    public String getChevauText() {
        return chevauText;
    }

    public void setChevauText(String chevauText) {
        this.chevauText = chevauText;
    }
}
