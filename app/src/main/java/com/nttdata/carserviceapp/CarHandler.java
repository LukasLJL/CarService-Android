package com.nttdata.carserviceapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class CarHandler {
    private ArrayList<Car> localCarList = new ArrayList<>();
    private int carPosition;


    public void getCars(Context context) {

        String url = "http://192.168.178.55:8080/car/list";
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (Iterator<String> it = response.keys(); it.hasNext(); ) {
                            String car_key = it.next();
                            JSONObject car = response.getJSONObject(car_key);

                            Car tempCar = new Car();

                            tempCar.setId(car.getInt("id"));
                            tempCar.setMarke(car.getString("marke"));
                            tempCar.setModel(car.getString("model"));
                            tempCar.setFarbe(car.getString("farbe"));
                            tempCar.setGewicht(car.getInt("gewicht"));
                            tempCar.setDrehmoment(car.getInt("drehmoment"));
                            tempCar.setLeistung(car.getInt("leistung"));
                            tempCar.setTueren(car.getInt("tueren"));
                            tempCar.setKlasse(car.getString("klasse"));
                            tempCar.setMotor_art(car.getString("motor_art"));

                            localCarList.add(tempCar);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error getting Server Data", Toast.LENGTH_LONG).show();
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(request);
    }

    public void deleteCar(int id) {
        Log.d("Delete", "deleteCar: " + id);
    }

    public ArrayList<Car> getLocalCarList() {
        return localCarList;
    }

    public int getCarPosition() {
        return carPosition;
    }

    public void setCarPosition(int carPosition) {
        this.carPosition = carPosition;
    }
}
