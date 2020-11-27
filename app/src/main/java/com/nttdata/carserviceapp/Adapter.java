package com.nttdata.carserviceapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ClickListener mClickListener;
    private ArrayList<Car> carList;
    private Context context;
    private CarBrandImageLoader carBrandImageLoader = new CarBrandImageLoader();


    public Adapter(Context ct, ArrayList<Car> localCarList, ClickListener clickListener) {
        context = ct;
        carList = localCarList;
        this.mClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new ViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.carID.setText(carList.get(position).getId().toString());
        holder.carMarke.setText(carList.get(position).getMarke());
        holder.carModel.setText(carList.get(position).getModel());
        carBrandImageLoader.getImage(holder, carList.get(position).getMarke());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView carID, carMarke, carModel;
        ImageView carImage;
        ClickListener clickListener;

        public ViewHolder(@NonNull View itemView, ClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            carID = itemView.findViewById(R.id.car_id);
            carMarke = itemView.findViewById(R.id.car_marke);
            carModel = itemView.findViewById(R.id.car_model);
            carImage = itemView.findViewById(R.id.carImageView);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return true;
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
