package com.nttdata.carserviceapp;

import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class CarBrandImageLoader {
    private void getImagesFromInternet(ImageView imageView, String brand) {
        //Check if Image can be Found on the Internet
        switch (brand) {
            case "VW":
                Picasso.get().load("https://www.carlogos.org/logo/Volkswagen-logo.png")
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(imageView);
                break;
            case "Mercedes":
                Picasso.get().load("https://www.carlogos.org/logo/Mercedes-Benz-logo.png")
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(imageView);
                break;
            default:
                Log.e("URL", "https://www.carlogos.org/car-logos/" + brand.toLowerCase() + "-logo.png");
                Picasso.get()
                        .load("https://www.carlogos.org/car-logos/" + brand.toLowerCase() + "-logo.png")
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(imageView);
                Log.e("Error", "Brand not found");

        }
    }

    public void getImage(Adapter.ViewHolder holder, String brand) {
        getImagesFromInternet(holder.carImage, brand);
    }

    public void getImage(ImageView imageView, String brand) {
        getImagesFromInternet(imageView, brand);
    }
}
