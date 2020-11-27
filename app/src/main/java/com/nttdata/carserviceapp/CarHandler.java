package com.nttdata.carserviceapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.PreferenceManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class CarHandler {

    public final String defaultIP = "192.168.178.55";
    private String API_IP;

    private ArrayList<Car> localCarList = new ArrayList<>();
    private int carPosition;

    public void setIPFromSettings (Context context){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        API_IP = "http://" + settings.getString("API-IP", getDefaultIP()) + ":8080/car";
    }


    public void getAllCars(Context context, Adapter adapter, Context mainActivityContext) {
        String getURL = API_IP + "/list";

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getURL, null,
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

                                localCarList.add(tempCar);
                            }
                            Collections.sort(localCarList, new Comparator<Car>() {
                                @Override
                                public int compare(Car o1, Car o2) {
                                    return o1.getId().compareTo(o2.getId());
                                }
                            });

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mainActivityContext, "Error getting Server Data", Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(request);
    }

    public void getSingleCar(Context context, Adapter adapter, Context mainActivityContext, int carID){
        String getURL = API_IP + "/list/" + carID;

        RequestQueue queue = Volley.newRequestQueue(context);

        localCarList.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getURL, null,
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

                                localCarList.add(tempCar);
                            }

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mainActivityContext, "Error getting Server Data", Toast.LENGTH_LONG).show();
                        Toast.makeText(mainActivityContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(request);
    }

    public void deleteCar(Context context, int id, MainActivity mainActivity) {
        int carID = getRealCarID(id);

        String url = API_IP + "/delete/" + carID;

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mainActivity.reloadData();
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

    public void editCar(Context context, Car editCar) throws JSONException {
        String url = API_IP + "/edit";
        RequestQueue queue = Volley.newRequestQueue(context);

        Gson gson = new Gson();
        String carJSON = gson.toJson(editCar);

        JSONObject json = new JSONObject(carJSON);

        JsonObjectRequest editRequest = new JsonObjectRequest(Request.Method.PUT, url, json,
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

        queue.add(editRequest);
    }

    public void createCar(Context context, Car createCar) throws JSONException {

        String url = API_IP + "/create";
        RequestQueue queue = Volley.newRequestQueue(context);

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

    public String getDefaultIP() {
        return defaultIP;
    }
}
