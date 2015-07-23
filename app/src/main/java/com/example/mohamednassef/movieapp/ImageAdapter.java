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
        //return mThumbIds.length;
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
        //if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_image, null);

            SquareImageView imageView = (SquareImageView) grid.findViewById(R.id.grid_image_view);
            if (moviePosters.get(position).toString().equals("http://i.imgur.com/DvpvklR.png"))
            {
                Picasso.with(mContext).load("http://i.imgur.com/DvpvklR.png").into(imageView);

            }
            else
            {
                Picasso.with(mContext).load("http://image.tmdb.org/t/p/w154/"+moviePosters.get(position).toString()).into(imageView);
            }


         /*else {
            grid = convertView;
        }*/

        return grid;
    }

}
