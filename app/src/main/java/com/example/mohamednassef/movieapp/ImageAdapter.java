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
        View grid = convertView;
        ImageViewHolder imageViewHolder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = inflater.inflate(R.layout.grid_image, parent, false);
            imageViewHolder = new ImageViewHolder(grid);
            grid.setTag(imageViewHolder);

        } else {
            imageViewHolder = (ImageViewHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(mContext.getString(R.string.url_Posters) + moviePosters.get(position).toString()).into(imageViewHolder.customImageView);

        return grid;
    }

}
