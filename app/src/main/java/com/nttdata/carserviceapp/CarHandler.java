package com.nttdata.carserviceapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class CarHandler {
    private ArrayList<Car> localCarList = new ArrayList<>();
    private int carPosition;
    private Context localContext;


    public void getCars(Context context) {

        String url = "http://192.168.178.20:8080/car/list";
        RequestQueue queue = Volley.newRequestQueue(context);
        localContext = context;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
                                Log.e("CAR:", tempCar.getId().toString());
                                localCarList.add(tempCar);
                                Log.e("CarList:", localCarList.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        int carID = getRealCarID(id);
        String url = "http://192.168.178.20:8080/car/delete/" + carID;

        Log.e("DELETE", "Call");

        RequestQueue queue = Volley.newRequestQueue(localContext);

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Respomse: ", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", error.getMessage());
                    }
                });
        queue.add(deleteRequest);


    }

    public int getRealCarID(int carPosition) {
        return localCarList.get(carPosition).getId();
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
