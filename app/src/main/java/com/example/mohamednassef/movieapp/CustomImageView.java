package com.example.mohamednassef.movieapp;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by mohamednassef on 7/14/15.
 */

public class CustomImageView extends ImageView {

    Context mContext;
    public CustomImageView(Context context) {
        super(context);
        this.mContext = context;
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext=context;
    }

    @Override


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        int width = size.x;


        TypedArray styledAttributes = mContext.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int mActionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        int statusBarHeight;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusBarHeight = getResources().getDimensionPixelSize(resourceId);


        //Using Code from: http://stackoverflow.com/questions/15055458/detect-7-inch-and-10-inch-tablet-programmatically

        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;


        float widthDpi = metrics.xdpi;
        float heightDpi = metrics.ydpi;
        float widthInches = widthPixels / widthDpi;
        float heightInches = heightPixels / heightDpi;

        double diagonalInches = Math.sqrt(
                (widthInches * widthInches)
                        + (heightInches * heightInches));




        if (diagonalInches >= 9) // 10''

        {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setMeasuredDimension(getMeasuredWidth(), (int) Math.round((height - mActionBarHeight - statusBarHeight) / 3));
            } else {
                setMeasuredDimension(getMeasuredWidth(), (int) Math.round((height - mActionBarHeight - statusBarHeight) / 4));
            }

        } else if (diagonalInches >= 6) // 7''
        {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setMeasuredDimension(getMeasuredWidth(), (int) Math.round((height - mActionBarHeight - statusBarHeight) / 2));
            } else {
                setMeasuredDimension(getMeasuredWidth(), (int) Math.round((height - mActionBarHeight - statusBarHeight) / 4));
            }

        }
        else
        {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setMeasuredDimension(getMeasuredWidth(), (int) Math.round((height - mActionBarHeight - statusBarHeight)));
            } else {
                setMeasuredDimension(getMeasuredWidth(), (int) Math.round((height - mActionBarHeight - statusBarHeight) / 2));
            }

        }


    }

}