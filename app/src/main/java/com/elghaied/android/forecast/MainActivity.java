package com.elghaied.android.forecast;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.elghaied.android.forecast.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private TextView mTextViewCityName;
    private TextView mTextViewCityDesc;
    private TextView mTextViewCityTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewCityName =(TextView) findViewById(R.id.text_city_name);
        mTextViewCityDesc =(TextView) findViewById(R.id.text_city_desc);
        mTextViewCityTemp =(TextView) findViewById(R.id.text_city_temp);


        mTextViewCityName.setText(R.string.city_name);
        mTextViewCityDesc.setText(R.string.city_desc);
        mTextViewCityTemp.setText(R.string.city_temp);
        Toast.makeText(this, "your class is connected", Toast.LENGTH_SHORT).show();

    }


}