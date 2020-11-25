package com.nttdata.carserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class MainActivity extends AppCompatActivity implements Adapter.ClickListener {

    private ArrayList<Car> localCarList = new ArrayList<>();

    private int carPosition;
    private CarHandler carHandler;
    private ActionMode actionMode;
    private RecyclerView recyclerView;
    private Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());

        //setup CarHandler
        carHandler = new CarHandler();

        //download car from server
        getCars();

        //setup recyclerView adapter
        adapter = new Adapter(MainActivity.this, getLocalCarList(), MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getCars() {

        String url = "http://192.168.178.55:8080/car/list";
        RequestQueue queue = Volley.newRequestQueue(this);


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
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error getting Server Data", Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(request);
    }

    public void deleteCar(int id) {
        int carID = getRealCarID(id);
        String url = "http://192.168.178.55:8080/car/delete/" + carID;

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Respomse: ", response);
                        reloadData();
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
        return getLocalCarList().get(carPosition).getId();
    }

    public void reloadData(){
        localCarList.clear();
        recyclerView.removeAllViews();
        getCars();
    }


    public void refreshData(View view) {
        reloadData();
    }

    public void addCarButton(View view){
        Toast.makeText(this, "Create Car ", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, CarCreateActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position, View v) {
        //Toast Message
        Toast.makeText(this, "Selected Car " + getLocalCarList().get(position).getMarke(), Toast.LENGTH_SHORT).show();

        //Load CarDetailActivity
        Intent intent = new Intent(this, CarDetailsActivity.class);
        intent.putExtra("CAR", getLocalCarList().get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, View v) {
        if (actionMode != null) {
            actionMode.finish();
        }
        setCarPosition(position);
        actionMode = startSupportActionMode(actionModeCallback);
    }

    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.car_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            actionMode = mode;
            if (item.getItemId() == R.id.delete_car) {
                Log.e("INFO", "CAR-ID:" + getCarPosition() + " | RCAR-ID: " + getRealCarID(getCarPosition()));
                deleteCar(getCarPosition());
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };

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