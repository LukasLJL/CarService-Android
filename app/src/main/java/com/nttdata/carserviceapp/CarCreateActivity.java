package com.nttdata.carserviceapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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


    public void createCarButton(View view) throws JSONException {
        Car createCar = new Car();
        if (marke != null && !(marke.getText().length() == 0)) {
            createCar.setMarke(marke.getText().toString());
        }
        if (model != null && !(model.getText().length() == 0)) {
            createCar.setModel(model.getText().toString());
        }
        if (gewicht !=null && !(gewicht.getText().length() == 0)){
            Log.e("ERRORORROROR", String.valueOf(gewicht.getText().toString().length()));
            createCar.setGewicht(Integer.parseInt(gewicht.getText().toString()));
        }
        if (leistung != null && !(leistung.getText().length() == 0)) {
            createCar.setLeistung(Integer.parseInt((leistung.getText()).toString()));
        }
        if (drehmoment != null && !(drehmoment.getText().length() == 0)) {
            createCar.setDrehmoment(Integer.parseInt(drehmoment.getText().toString()));
        }
        if (tueren != null && !(tueren.getText().length() == 0)) {
            createCar.setTueren(Integer.parseInt(tueren.getText().toString()));
        }
        if (farbe != null && !(farbe.getText().length() == 0)) {
            createCar.setFarbe(farbe.getText().toString());
        }
        if (motor_art != null && !(motor_art.getText().length() == 0)) {
            createCar.setMotor_art(motor_art.getText().toString());
        }
        if (klasse != null && !(klasse.getText().length() == 0)) {
            createCar.setKlasse(klasse.getText().toString());
        }


        String url = "http://192.168.178.55:8080/car/create";
        RequestQueue queue = Volley.newRequestQueue(this);

        Gson gson = new Gson();
        String carJSON = gson.toJson(createCar);

        JSONObject json = new JSONObject(carJSON);


        Log.e("JSON", carJSON.toString());

        JsonObjectRequest createRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response Create", response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());

                    }
                });

        queue.add(createRequest);
    }
}
