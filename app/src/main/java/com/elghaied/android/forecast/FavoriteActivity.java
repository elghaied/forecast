package com.elghaied.android.forecast;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.elghaied.android.forecast.databinding.ActivityFavoriteBinding;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;
    private TextView mTextViewFavoriteContent;

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
    }
}