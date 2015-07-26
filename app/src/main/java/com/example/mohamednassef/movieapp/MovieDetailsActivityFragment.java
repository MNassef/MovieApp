package com.example.mohamednassef.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsActivityFragment extends Fragment {

    private final String LOG_TAG = MovieDetailsActivityFragment.class.getSimpleName();

    public MovieDetailsActivityFragment() {
    }



    public static String getMoviePoster(String movieJSONStr, int movieIndex) throws JSONException {
        String movPoster;
        JSONObject moviesJSON = new JSONObject(movieJSONStr);
        JSONArray results = moviesJSON.getJSONArray("results");
        JSONObject movie = results.getJSONObject(movieIndex);
        movPoster = movie.getString("poster_path");

        return movPoster;

    }


    public static String getMovieName(String movieJSONStr, int movieIndex) throws JSONException {
        String movName;
        JSONObject moviesJSON = new JSONObject(movieJSONStr);
        JSONArray results = moviesJSON.getJSONArray("results");
        JSONObject movie = results.getJSONObject(movieIndex);
        movName = movie.getString("title");

        return movName;

    }

    public static String getMovieOverview(String movieJSONStr, int movieIndex) throws JSONException {
        String movPlot;
        JSONObject moviesJSON = new JSONObject(movieJSONStr);
        JSONArray results = moviesJSON.getJSONArray("results");
        JSONObject movie = results.getJSONObject(movieIndex);
        movPlot = movie.getString("overview");

        return movPlot;

    }

    public static String getMovieDate(String movieJSONStr, int movieIndex) throws JSONException {
        String movDate;
        JSONObject moviesJSON = new JSONObject(movieJSONStr);
        JSONArray results = moviesJSON.getJSONArray("results");
        JSONObject movie = results.getJSONObject(movieIndex);
        movDate = movie.getString("release_date");

        return movDate;

    }


    public static String getMovieRating(String movieJSONStr, int movieIndex) throws JSONException {
        String movRating;
        JSONObject moviesJSON = new JSONObject(movieJSONStr);
        JSONArray results = moviesJSON.getJSONArray("results");
        JSONObject movie = results.getJSONObject(movieIndex);
        movRating = movie.getString("vote_average");

        return movRating;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootDetailView = inflater.inflate(R.layout.fragment_movie_details,container,false);
        String movieStr;
        String moviePostn;

        String moviePoster = null;
        String movieName = null;
        String movieOverview = null;
        String movieDate = null;
        String movieRating = null;


        Intent intent = getActivity().getIntent();
        if (intent!=null && intent.hasExtra(intent.EXTRA_TEXT))
        {
            movieStr = intent.getStringExtra(intent.EXTRA_TEXT);
            moviePostn = intent.getStringExtra(intent.EXTRA_REFERRER);

            try
            {
                movieName  = getMovieName(movieStr, Integer.parseInt(moviePostn));
                moviePoster  = getMoviePoster(movieStr, Integer.parseInt(moviePostn));
                movieOverview  = getMovieOverview(movieStr, Integer.parseInt(moviePostn));
                movieDate  = getMovieDate(movieStr, Integer.parseInt(moviePostn));
                movieRating  = getMovieRating(movieStr, Integer.parseInt(moviePostn));

                ((TextView) rootDetailView.findViewById(R.id.tview_r1))
                        .setText(movieName);
                ((TextView) rootDetailView.findViewById(R.id.tview_r2_1))
                        .setText("Release Date \n " + movieDate);
                ((TextView) rootDetailView.findViewById(R.id.tview_r2_2))
                        .setText("Rating \n " + movieRating);
                ((TextView) rootDetailView.findViewById(R.id.tview_r3))
                        .setText(movieOverview);

                ImageView imageView = (ImageView) rootDetailView.findViewById(R.id.iview_r2);
                Picasso.with(getActivity()).load(getString(R.string.url_Posters)+moviePoster).into(imageView);

            }
            catch (JSONException e)
            {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

        }



        return rootDetailView;

    }
}
