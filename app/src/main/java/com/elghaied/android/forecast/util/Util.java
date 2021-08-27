package com.elghaied.android.forecast.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.elghaied.android.forecast.FavoriteActivity;
import com.elghaied.android.forecast.adapters.FavoriteAdapter;
import com.elghaied.android.forecast.model.data.CityData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public  class  Util {
    private static final String PREFS_NAME = "sharedPrefs";
    private static final String PREFS_FAVORITE_CITIES = "cityJson";

    public static void saveFavouriteCities(Context context, ArrayList<CityData> cities) {


        JSONArray jsonArrayCities = new JSONArray();
        for (int i = 0; i < cities.size(); i++) {
            jsonArrayCities.put(cities.get(i).mStringJson);
        }
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(Util.PREFS_FAVORITE_CITIES, jsonArrayCities.toString());
        editor.apply();
    }

    public static ArrayList<CityData> initFavoriteCities(Context context) {
        ArrayList<CityData> cities = new ArrayList<>();

        SharedPreferences preferences = context.getSharedPreferences(Util.PREFS_NAME,
                Context.MODE_PRIVATE);

        try {
            JSONArray jsonArray = new JSONArray(preferences.getString(Util.PREFS_FAVORITE_CITIES,
                    ""));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCity = new JSONObject(jsonArray.getString(i));
                CityData city =  new Gson().fromJson(jsonObjectCity.toString(), CityData.class);
                city.setmStringJson(jsonObjectCity.toString());
                cities.add(city);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("mCities",cities.toString());
        return cities;
    }

    public static String getIconPath(String icon){
        return "https://openweathermap.org/img/w/" + icon + ".png";
    }

}
