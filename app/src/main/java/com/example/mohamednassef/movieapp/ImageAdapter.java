package com.example.mohamednassef.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mohamednassef on 7/11/15.
 */





public class ImageAdapter extends BaseAdapter {
    public Context mContext;
    public  List<String> moviePosters;




    public ImageAdapter(Context c, List<String> Posters) {
        this.mContext = c;
        this.moviePosters = Posters;
    }

    public int getCount() {
        return moviePosters.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        grid = inflater.inflate(R.layout.grid_image, parent,false);
        CustomImageView imageView = (CustomImageView) grid.findViewById(R.id.grid_image_view);
        Picasso.with(mContext).load(mContext.getString(R.string.url_Posters)+moviePosters.get(position).toString()).into(imageView);

        return grid;
    }

}
