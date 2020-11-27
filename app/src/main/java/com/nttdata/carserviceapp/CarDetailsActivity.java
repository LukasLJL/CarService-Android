package com.nttdata.carserviceapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CarDetailsActivity extends AppCompatActivity  {

    //declare all textViews
    private TextView textView_id;
    private TextView textView_marke;
    private TextView textView_model;
    private TextView textView_leistung;
    private TextView textView_gewicht;
    private TextView textView_drehmoment;
    private TextView textView_tueren;
    private TextView textView_farbe;
    private TextView textView_motor_art;
    private TextView textView_klasse;
    private ImageView carBrandImage;
    private CarBrandImageLoader carBrandImageLoader;

    private Car detailCar;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_details);
        Intent iin = getIntent();
        Bundle bundle = iin.getExtras();

        carBrandImageLoader = new CarBrandImageLoader();

        //init all TextViews
        textView_id = findViewById(R.id.car_detail_id);
        textView_marke = findViewById(R.id.car_detail_marke);
        textView_model = findViewById(R.id.car_detail_model);
        textView_leistung = findViewById(R.id.car_detail_leistung);
        textView_gewicht = findViewById(R.id.car_detail_gewicht);
        textView_drehmoment = findViewById(R.id.car_detail_drehmoment);
        textView_tueren = findViewById(R.id.car_detail_tueren);
        textView_farbe = findViewById(R.id.car_detail_farbe);
        textView_motor_art = findViewById(R.id.car_detail_motor_art);
        textView_klasse = findViewById(R.id.car_detail_klasse);
        carBrandImage = findViewById(R.id.car_detail_image);

        if (bundle != null){
            detailCar = (Car) bundle.get("CAR");
            textView_id.setText(detailCar.getId().toString());
            textView_marke.setText(detailCar.getMarke());
            textView_model.setText(detailCar.getModel());
            textView_leistung.setText(detailCar.getLeistung() + " ps");
            textView_gewicht.setText(detailCar.getGewicht() + " kg");
            textView_drehmoment.setText(detailCar.getDrehmoment() + " nm");
            textView_tueren.setText(String.valueOf(detailCar.getTueren()));
            textView_farbe.setText(detailCar.getFarbe());
            textView_motor_art.setText(detailCar.getMotor_art());
            textView_klasse.setText(detailCar.getKlasse());
            carBrandImageLoader.getImage(carBrandImage, detailCar.getMarke());

        }

    }

    public void editCarButton(View view) {
        Intent intent = new Intent(CarDetailsActivity.this, CarEditActivity.class);
        intent.putExtra("CAR", detailCar);
        startActivity(intent);
    }
}
