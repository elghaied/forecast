package com.elghaied.android.forecast;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private TextView mTextViewCityName;
    private TextView mTextViewCityDesc;
    private TextView mTextViewCityTemp;
    private LinearLayout mLinearLayoutConnectionNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewCityName =(TextView) findViewById(R.id.text_city_name);
        mTextViewCityDesc =(TextView) findViewById(R.id.text_city_desc);
        mTextViewCityTemp =(TextView) findViewById(R.id.text_city_temp);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(this, "you are connected", Toast.LENGTH_SHORT).show();
            mLinearLayoutConnectionNotification.setVisibility(View.INVISIBLE);
        } else {
            mLinearLayoutConnectionNotification.setVisibility(View.VISIBLE);
        }

        mTextViewCityName.setText(R.string.city_name);
        mTextViewCityDesc.setText(R.string.city_desc);
        mTextViewCityTemp.setText(R.string.city_temp);

    }


}