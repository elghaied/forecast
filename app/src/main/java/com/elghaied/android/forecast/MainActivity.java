package com.elghaied.android.forecast;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private TextView mTextViewCityName;
    private TextView mTextViewCityDesc;
    private TextView mTextViewCityTemp;
    private Button mButtonFav;
    private Button mButtonFirst;
    private LinearLayout mLinearLayoutConnectionNotification;
    private Context context =this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewCityName =(TextView) findViewById(R.id.text_city_name);
        mTextViewCityDesc =(TextView) findViewById(R.id.text_city_desc);
        mTextViewCityTemp =(TextView) findViewById(R.id.text_city_temp);
        mLinearLayoutConnectionNotification = (LinearLayout) findViewById(R.id.connection_notification);


        // Buttons .
        mButtonFirst = (Button) findViewById(R.id.button_first);
        mButtonFirst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context, "Second button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mButtonFav = (Button) findViewById(R.id.button_fav);
        mButtonFav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Going to Favorite activity 
                Intent intent = new Intent(context, FavoriteActivity.class);
                startActivity(intent);
            }
        });

        // Testing connection .
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(this, "you are connected", Toast.LENGTH_SHORT).show();
            mLinearLayoutConnectionNotification.setVisibility(View.INVISIBLE);
        } else {
            mLinearLayoutConnectionNotification.setVisibility(View.VISIBLE);
        }


        // Assign text to TextView elements.
        mTextViewCityName.setText(R.string.city_name);
        mTextViewCityDesc.setText(R.string.city_desc);
        mTextViewCityTemp.setText(R.string.city_temp);

    }


    public void SecondButton(View view) {
        Toast.makeText(this, "Second button clicked", Toast.LENGTH_SHORT).show();
    }


}