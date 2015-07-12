package com.example.mohamednassef.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mohamednassef on 7/11/15.
 */


public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    // references to our images
    private Integer[] mThumbIds = { R.drawable.images3, R.drawable.images, R.drawable.images2};
    private String [] mThumbtxt = {"Ocean_1","Ocean_2","Ocean_3"};


    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
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
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            //imageView = new ImageView(mContext);
            /*
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            */
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_image, null);
            TextView textView = (TextView) grid.findViewById(R.id.text_view);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image_view);
            textView.setText(mThumbtxt[position]);
            imageView.setImageResource(mThumbIds[position]);



        } else {
            grid = convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);
        return grid;
    }



}
