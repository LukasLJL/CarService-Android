package com.nttdata.carserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements Adapter.ClickListener {

    private CarHandler carHandler;
    private ActionMode actionMode;
    private Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup recyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());

        //setup CarHandler
        carHandler = new CarHandler();

        //setup recyclerView adapter
        adapter = new Adapter(this, carHandler.getLocalCarList(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //download car from server
        carHandler.getCars(this);
    }


    public void refreshData(View view) {
        adapter.notifyDataSetChanged();
    }

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
        carHandler.setCarPosition(position);
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
                carHandler.deleteCar(carHandler.getCarPosition());
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

}