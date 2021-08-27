package com.elghaied.android.forecast;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elghaied.android.forecast.model.data.CityData;
import com.elghaied.android.forecast.model.data.Weather;
import com.elghaied.android.forecast.util.Util;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private TextView mTextViewCityName;
    private TextView mTextViewCityDesc;
    private TextView mTextViewCityTemp;
    private ImageView mImageViewCityIcon;
    private Button mButtonFav;
    private Button mButtonFirst;
    private LinearLayout mLinearLayoutConnectionNotification;
    private Context mContext = this;
    private OkHttpClient mOkHttpClient;
    private int REQUEST_CODE = 100;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                ActivityCompat.requestPermissions((Activity) mContext, new
                        String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);

            GeoLocationAPI(location.getLongitude(),location.getLatitude());

        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// Permission Granted

            } else {
// Permission Denied
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewCityName =(TextView) findViewById(R.id.text_city_name);
        mTextViewCityDesc =(TextView) findViewById(R.id.text_city_desc);
        mTextViewCityTemp =(TextView) findViewById(R.id.text_city_temp);
        mImageViewCityIcon = findViewById(R.id.cloud_icon);
        mLinearLayoutConnectionNotification = (LinearLayout) findViewById(R.id.connection_notification);
        EditText mEditTextInputField = (EditText) findViewById(R.id.input_main_activity);


        // Buttons .
        mButtonFirst = (Button) findViewById(R.id.button_first);
        mButtonFirst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "Second button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mButtonFav = (Button) findViewById(R.id.button_fav);
        mButtonFav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Going to Favorite activity
                Intent intent = new Intent(mContext, FavoriteActivity.class);
                String inputField = mEditTextInputField.getText().toString();
                Log.d("test",inputField);
                intent.putExtra("inputField",inputField);
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
//        mTextViewCityName.setText(R.string.city_name);
//        mTextViewCityDesc.setText(R.string.city_desc);
//        mTextViewCityTemp.setText(R.string.city_temp);

    }


    public void SecondButton(View view) {
        Toast.makeText(this, "Second button clicked", Toast.LENGTH_SHORT).show();
    }



    public void GeoLocationAPI(Double geoLongitude, Double geoLatitude){

         Request request = new Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?lat="+ geoLatitude.toString() +"&lon="+ geoLongitude.toString()+"&appid=d8c3f7f9a97ed0efeac6b56b1160be04").build();



        mOkHttpClient  = new OkHttpClient();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("tester", "failed to fetch data");
            }
            @Override

            public void onResponse(Call call, Response response) throws IOException {




                if (response.isSuccessful()) {
                    final String stringJson = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.d("tester","Fetching data" + stringJson);
                            updateUi(stringJson);



                        }
                    });


                }

            }
        });


    }



    private void updateUi(String stringJson){



        try {



            // On parse à l'ancienne le Json
            JSONObject jsonObject = new JSONObject(stringJson);



            // Utilisation de GSON pour les résultats : City
            CityData city = new Gson().fromJson(jsonObject.toString(), CityData.class);
            city.setmStringJson(stringJson);
            Weather weather = city.getWeather().get(0);

            Picasso.get().load(Util.getIconPath(weather.getIcon())).into(mImageViewCityIcon);
            mTextViewCityName.setText(city.getName());
            mTextViewCityDesc.setText(weather.getDescription());
            mTextViewCityTemp.setText(weather.getMain());
//            mCities.add(result);
//
//            Util.saveFavouriteCities(mContext,mCities);


//            for (int i = 0; i < mCities.size(); i++) {
//                Log.d("tester",mCities.get(i).toString());
//
//            }
//
//            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            failUI();
        }

    }

    public void failUI(){
        Toast.makeText(mContext,"IT DIDN'T WORK", Toast.LENGTH_SHORT).show();

    }

}