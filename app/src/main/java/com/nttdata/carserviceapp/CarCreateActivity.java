package com.nttdata.carserviceapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CarCreateActivity extends AppCompatActivity {

    private EditText marke;
    private EditText model;
    private EditText leistung;
    private EditText gewicht;
    private EditText drehmoment;
    private EditText tueren;
    private EditText farbe;
    private EditText motor_art;
    private EditText klasse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_create);

        marke = findViewById(R.id.editText_CreateCar_marke);
        model = findViewById(R.id.editText_CreateCar_model);
        leistung = findViewById(R.id.editText_CreateCar_leistung);
        gewicht = findViewById(R.id.editText_CreateCar_gewicht);
        drehmoment = findViewById(R.id.editText_CreateCar_drehmoment);
        tueren = findViewById(R.id.editText_CreateCar_tueren);
        farbe = findViewById(R.id.editText_CreateCar_farbe);
        motor_art = findViewById(R.id.editText_CreateCar_motor_art);
        klasse = findViewById(R.id.editText_CreateCar_klasse);
    }


    public void createCarButton(View view) {
        Log.e("Create", "test");
    }
}
