package com.example.mohamednassef.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        String movieStr;
        String moviePostn;
        Intent intent = getActivity().getIntent();
        if (intent!=null && intent.hasExtra(intent.EXTRA_TEXT))
        {
            movieStr = intent.getStringExtra(intent.EXTRA_TEXT);
            moviePostn = intent.getStringExtra(intent.EXTRA_REFERRER);
            String nametmep = null;
            try {
               nametmep  = getMovieName(movieStr,Integer.parseInt(moviePostn));
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            Toast.makeText(getActivity(), nametmep,
                    Toast.LENGTH_LONG).show();

        }



        return inflater.inflate(R.layout.fragment_movie_details, container, false);

    }
}
