package com.nttdata.carserviceapp;

import java.io.Serializable;

public class Car implements Serializable {

    private Integer id;
    private String marke;
    private String model;
    private int gewicht;
    private int leistung;
    private int drehmoment;
    private String farbe;
    private int tueren;
    private String klasse;

    public Car() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getGewicht() {
        return gewicht;
    }

    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

    public int getLeistung() {
        return leistung;
    }

    public void setLeistung(int leistung) {
        this.leistung = leistung;
    }

    public int getDrehmoment() {
        return drehmoment;
    }

    public void setDrehmoment(int drehmoment) {
        this.drehmoment = drehmoment;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

    public int getTueren() {
        return tueren;
    }

    public void setTueren(int tueren) {
        this.tueren = tueren;
    }

    public String getKlasse() {
        return klasse;
    }

    public void setKlasse(String klasse) {
        this.klasse = klasse;
    }

    public String getMotor_art() {
        return motor_art;
    }

    public void setMotor_art(String motor_art) {
        this.motor_art = motor_art;
    }

    private String motor_art;



}
