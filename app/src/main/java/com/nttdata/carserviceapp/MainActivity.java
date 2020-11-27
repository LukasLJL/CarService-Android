package com.nttdata.carserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity implements Adapter.ClickListener {

    private CarHandler carHandler;
    private ActionMode actionMode;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View selectedItemView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        refreshMainActivity();

        //setup recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());

        //setup CarHandler
        carHandler = new CarHandler();

        carHandler.setIPFromSettings(this);

        //setup recyclerView adapter
        adapter = new Adapter(MainActivity.this, carHandler.getLocalCarList(), MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //download car from server
        carHandler.getAllCars(this, adapter, MainActivity.this);
    }


    //Toolbar Show Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint("Search for Car ID");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("Searched for", query);
                searchForSingleCar(Integer.parseInt(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                reloadData();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //Toolbar Menu Handle Click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            Toast.makeText(this, "SETTINGS", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //Pull-Down to Refresh
    private void refreshMainActivity() {
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        reloadData();
                        //Refresh Animation for 0.5s
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }, 500);
                    }
                }
        );
    }

    public void searchForSingleCar(int id){
        carHandler.getLocalCarList().clear();
        recyclerView.removeAllViews();
        carHandler.setIPFromSettings(this);
        carHandler.getSingleCar(this, adapter, MainActivity.this, id);
    }



    public void reloadData() {
        carHandler.getLocalCarList().clear();
        recyclerView.removeAllViews();
        carHandler.setIPFromSettings(this);
        carHandler.getAllCars(this, adapter, MainActivity.this);
    }


    public void addCarButton(View view) {
        Toast.makeText(this, "Create Car ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CarCreateActivity.class);
        startActivity(intent);
    }

    //ItemClick Stuff for RecyclerView..

    @Override
    public void onItemClick(int position, View v) {
        //Toast Message
        Toast.makeText(this, "Selected Car " + carHandler.getLocalCarList().get(position).getMarke(), Toast.LENGTH_SHORT).show();

        //Load CarDetailActivity
        Intent intent = new Intent(this, CarDetailsActivity.class);
        intent.putExtra("CAR", carHandler.getLocalCarList().get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, View v) {
        if (actionMode != null) {
            actionMode.finish();
        }
        ConstraintLayout selectedItem = v.findViewById(R.id.car_item);
        selectedItem.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        selectedItemView = v;
        carHandler.setCarPosition(position);
        actionMode = startSupportActionMode(actionModeCallback);
    }

    //Stuff for Navigation Bar

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
                Log.e("INFO", "CAR-ID:" + carHandler.getCarPosition() + " | RCAR-ID: " + carHandler.getRealCarID(carHandler.getCarPosition()));
                carHandler.deleteCar(MainActivity.this, carHandler.getCarPosition(), MainActivity.this);
                mode.finish();
                return true;
            } else if (item.getItemId() == R.id.edit_car) {
                Intent intent = new Intent(MainActivity.this, CarEditActivity.class);
                intent.putExtra("CAR", carHandler.getLocalCarList().get(carHandler.getCarPosition()));
                startActivity(intent);
                mode.finish();
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            ConstraintLayout selectedItem = selectedItemView.findViewById(R.id.car_item);
            selectedItem.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white));
            actionMode = null;
        }
    };

}