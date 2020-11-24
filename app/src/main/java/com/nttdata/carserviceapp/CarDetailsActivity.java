package com.nttdata.carserviceapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CarDetailsActivity extends AppCompatActivity  {

    //declare all textViews
    private TextView id;
    private TextView marke;
    private TextView model;
    private TextView leistung;
    private TextView gewicht;
    private TextView drehmoment;
    private TextView tueren;
    private TextView farbe;
    private TextView motor_art;
    private TextView klasse;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_details);
        Intent iin = getIntent();
        Bundle bundle = iin.getExtras();

        //init all TextViews
        id = findViewById(R.id.car_detail_id);
        marke = findViewById(R.id.car_detail_marke);
        model = findViewById(R.id.car_detail_model);
        leistung = findViewById(R.id.car_detail_leistung);
        gewicht = findViewById(R.id.car_detail_gewicht);
        drehmoment = findViewById(R.id.car_detail_drehmoment);
        tueren = findViewById(R.id.car_detail_tueren);
        farbe = findViewById(R.id.car_detail_farbe);
        motor_art = findViewById(R.id.car_detail_motor_art);
        klasse = findViewById(R.id.car_detail_klasse);

        if (bundle != null){
            Car detailCar = (Car) bundle.get("CAR");
            id.setText(detailCar.getId().toString());
            marke.setText(detailCar.getMarke());
            model.setText(detailCar.getModel());
            leistung.setText(detailCar.getLeistung() + " ps");
            gewicht.setText(detailCar.getGewicht() + " kg");
            drehmoment.setText(detailCar.getDrehmoment() + " nm");
            tueren.setText(String.valueOf(detailCar.getTueren()));
            farbe.setText(detailCar.getFarbe());
            motor_art.setText(detailCar.getMotor_art());
            klasse.setText(detailCar.getKlasse());
        }

    }
}
