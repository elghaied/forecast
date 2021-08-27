package com.elghaied.android.forecast.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elghaied.android.forecast.R;
import com.elghaied.android.forecast.model.City;
import com.elghaied.android.forecast.model.data.CityData;
import com.elghaied.android.forecast.model.data.Main;
import com.elghaied.android.forecast.model.data.Weather;
import com.elghaied.android.forecast.util.Util;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<CityData> mCities;
    // Constructor
    public FavoriteAdapter(Context argContext, ArrayList<CityData> argCities) {
        setmCities(argCities);
        setmContext(argContext);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_city, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {


        CityData city = mCities.get(position);
        holder.cityData = city;
        holder.mItemName.setText(city.getName());
        Weather weather = city.getWeather().get(0);
        Log.d("tester",weather.getIcon());
        String imgPath = Util.getIconPath( weather.getIcon()) ;
        Log.d("tester",imgPath);
        Picasso.get().load(imgPath).into(holder.mItemImage);

        holder.mItemDescription.setText(weather.getDescription());
        Main main = city.getMain();
        holder.mItemTemperature.setText(main.getTemp().toString());


    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }


    // Classe holder qui contient la vue d’un item
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mItemImage;
        TextView mItemName;
        TextView mItemDescription;
        TextView mItemTemperature;
        CityData cityData;

        public ViewHolder(View view) {
            super(view);
            setmItemImage(view.findViewById(R.id.item_city_picture));
            setmItemName(view.findViewById(R.id.item_city_name));
            setmItemDescription(view.findViewById(R.id.item_city_description));
            setmItemTemperature(view.findViewById(R.id.item_city_temperature));

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    mCities.remove(cityData);
                    Util.saveFavouriteCities(mContext,mCities);
                    notifyDataSetChanged();
                    return false;
                }
            });
        }

        public ImageView getmItemImage() {
            return mItemImage;
        }

        public void setmItemImage(ImageView mItemImage) {
            this.mItemImage = mItemImage;
        }

        public TextView getmItemName() {
            return mItemName;
        }

        public void setmItemName(TextView mItemName) {
            this.mItemName = mItemName;
        }

        public TextView getmItemDescription() {
            return mItemDescription;
        }

        public void setmItemDescription(TextView mItemDescription) {
            this.mItemDescription = mItemDescription;
        }

        public TextView getmItemTemperature() {
            return mItemTemperature;
        }

        public void setmItemTemperature(TextView mItemTemperature) {
            this.mItemTemperature = mItemTemperature;
        }


    }




    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<CityData> getmCities() {
        return mCities;
    }

    public void setmCities(ArrayList<CityData> mCities) {
        this.mCities = mCities;
    }
}