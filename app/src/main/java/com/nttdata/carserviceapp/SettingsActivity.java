package com.nttdata.carserviceapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    private EditText editText_settings_apiip;
    private SharedPreferences settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        //Activate "Back" Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Initial EdiText
        editText_settings_apiip = findViewById(R.id.editText_settings_apiip);

        editText_settings_apiip.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                safeSettings(v);
            }
            return false;
        });

        //Get SharedPreferences
        settings = PreferenceManager.getDefaultSharedPreferences(this);


        CarHandler carHandler = new CarHandler();

        editText_settings_apiip.setText(settings.getString("API-IP", carHandler.getDefaultIP()));
    }

    public void safeSettings(View view){
        SharedPreferences.Editor toSave = settings.edit();
        toSave.putString("API-IP", editText_settings_apiip.getText().toString());
        toSave.apply();
    }


}
