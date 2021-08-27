package com.elghaied.android.forecast;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.elghaied.android.forecast.adapters.FavoriteAdapter;
import com.elghaied.android.forecast.model.City;
import com.elghaied.android.forecast.model.data.CityData;
import com.elghaied.android.forecast.model.data.Weather;
import com.elghaied.android.forecast.util.Util;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elghaied.android.forecast.databinding.ActivityFavoriteBinding;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.elghaied.android.forecast.util.Util.initFavoriteCities;

public class FavoriteActivity extends AppCompatActivity {
    private Context mContext = this;
    private ActivityFavoriteBinding binding;
    private RecyclerView mFavoriteItem;
    private ArrayList<CityData> mCities;
    private FavoriteAdapter mAdapter;
    private OkHttpClient mOkHttpClient;

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


                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Add a city");
                builder.setIcon(R.drawable.ic_baseline_add_box_24);


                Log.d("tester","it has been clicked");
                View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_favorite, null);
                final EditText editTextCity = (EditText) v.findViewById(R.id.edit_text_dialog_city);

                builder.setPositiveButton("add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        OnSearch(editTextCity.getText().toString());


//                        finish();
                    }
                });


                builder.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                });
                builder.setView(v);

                builder.create().show();
                mAdapter.notifyDataSetChanged();
            }
        });


        mCities = initFavoriteCities(mContext);

        Log.d("mCities",mCities.toString());
        mFavoriteItem = (RecyclerView) findViewById(R.id.favorite_recycler_view);
        mFavoriteItem.setLayoutManager(new GridLayoutManager(this, 1));
        mAdapter = new FavoriteAdapter(mContext,mCities);



        mFavoriteItem.setAdapter(mAdapter);



    }

    private void updateUi(String stringJson){



        try {
            // On parse à l'ancienne le Json
            JSONObject jsonObject = new JSONObject(stringJson);
            Log.d("tester", "the object" + jsonObject.toString());


            // Utilisation de GSON pour les résultats : City
            CityData result = new Gson().fromJson(jsonObject.toString(), CityData.class);
            result.setmStringJson(stringJson);
            mCities.add(result);

            Util.saveFavouriteCities(mContext,mCities);


            for (int i = 0; i < mCities.size(); i++) {
                Log.d("tester",mCities.get(i).toString());

            }

            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            failUI();
        }

    }

    public void failUI(){
        Toast.makeText(mContext,"IT DIDN'T WORK", Toast.LENGTH_SHORT).show();

    }




    public void OnSearch(String cityName){
        Request request = new Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=d8c3f7f9a97ed0efeac6b56b1160be04").build();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
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

        } else {
            Log.d("tester","failed");
        }
    }
}