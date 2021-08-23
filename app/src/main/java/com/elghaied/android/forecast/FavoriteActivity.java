package com.elghaied.android.forecast;

import android.os.Bundle;

import com.elghaied.android.forecast.model.City;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.elghaied.android.forecast.databinding.ActivityFavoriteBinding;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;
    private TextView mTextViewFavoriteContent;
    private ArrayList<City> mCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mTextViewFavoriteContent = (TextView) findViewById(R.id.text_favorite_content);
        Bundle extras = getIntent().getExtras();
        String strMessage = extras.getString("inputField");

        Log.d("FavTest",strMessage);
        mTextViewFavoriteContent.setText(strMessage);

        mCities = new ArrayList<>();
        City city1 = new City("Montréal", "Légères pluies", "22°C", R.drawable.cloud);
        City city2 = new City("New York", "Ensoleillé", "22°C", R.drawable.cloud);
        City city3 = new City("Paris", "Nuageux", "24°C", R.drawable.cloud);
        City city4 = new City("Toulouse", "Pluies modérées", "20°C", R.drawable.weather_sunny_white);
        mCities.add(city1);
        mCities.add(city2);
        mCities.add(city3);
        mCities.add(city4);
    }
}