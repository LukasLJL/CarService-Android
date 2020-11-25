package com.nttdata.carserviceapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

    private EditText editText_marke;
    private EditText editText_model;
    private EditText editText_leistung;
    private EditText editText_gewicht;
    private EditText editText_drehmoment;
    private EditText editText_tueren;
    private EditText editText_farbe;
    private EditText editText_motor_art;
    private EditText editText_klasse;
    private CardView cardView_id;
    private Button editButton;
    private Button createButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_create);

        cardView_id = findViewById(R.id.cardView_car_ec_id);
        editButton = findViewById(R.id.button_car_edit);
        createButton = findViewById(R.id.button_car_create);

        cardView_id.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);
        createButton.setVisibility(View.VISIBLE);


        editText_marke = findViewById(R.id.editText_car_brand);
        editText_model = findViewById(R.id.editText_car_model);
        editText_leistung = findViewById(R.id.editText_car_power);
        editText_gewicht = findViewById(R.id.editText_car_weight);
        editText_drehmoment = findViewById(R.id.editText_car_torque);
        editText_tueren = findViewById(R.id.editText_car_doors);
        editText_farbe = findViewById(R.id.editText_car_color);
        editText_motor_art = findViewById(R.id.editText_car_engineTyp);
        editText_klasse = findViewById(R.id.editText_car_carTyp);
    }


    public void createCarButton(View view) throws JSONException {
        Car createCar = new Car();
        if (editText_marke != null && !(editText_marke.getText().length() == 0)) {
            createCar.setMarke(editText_marke.getText().toString());
        }
        if (editText_model != null && !(editText_model.getText().length() == 0)) {
            createCar.setModel(editText_model.getText().toString());
        }
        if (editText_gewicht !=null && !(editText_gewicht.getText().length() == 0)){
            createCar.setGewicht(Integer.parseInt(editText_gewicht.getText().toString()));
        }
        if (editText_leistung != null && !(editText_leistung.getText().length() == 0)) {
            createCar.setLeistung(Integer.parseInt((editText_leistung.getText()).toString()));
        }
        if (editText_drehmoment != null && !(editText_drehmoment.getText().length() == 0)) {
            createCar.setDrehmoment(Integer.parseInt(editText_drehmoment.getText().toString()));
        }
        if (editText_tueren != null && !(editText_tueren.getText().length() == 0)) {
            createCar.setTueren(Integer.parseInt(editText_tueren.getText().toString()));
        }
        if (editText_farbe != null && !(editText_farbe.getText().length() == 0)) {
            createCar.setFarbe(editText_farbe.getText().toString());
        }
        if (editText_motor_art != null && !(editText_motor_art.getText().length() == 0)) {
            createCar.setMotor_art(editText_motor_art.getText().toString());
        }
        if (editText_klasse != null && !(editText_klasse.getText().length() == 0)) {
            createCar.setKlasse(editText_klasse.getText().toString());
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
