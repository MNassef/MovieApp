package com.example.mohamednassef.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment {

    public ImageAdapter imageAdapter;
    public FetchPostersTask fetch;


    public MovieFragment() {
    }

    public static String[] getPosters(String movieJSONStr, int nMovies) throws JSONException {
        String[] Posters = new String[nMovies];
        for (int i = 0; i < nMovies; i++) {
            JSONObject moviesJSON = new JSONObject(movieJSONStr);
            JSONArray results = moviesJSON.getJSONArray("results");
            JSONObject movie = results.getJSONObject(i);
            Posters[i] = movie.getString("poster_path");
        }
        return Posters;

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        /*inflater.inflate(R.menu.menu_movie_details, menu);*/
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        int item_selected = item.getItemId();
        if (item_selected == R.id.refresh) {
            updateGrid();
            return true;
        }

        */


        return super.onOptionsItemSelected(item);
    }

    public void updateGrid() {
        fetch = new FetchPostersTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sorting_order = prefs.getString(getString(R.string.sorting_key),
                getString(R.string.sort_mostpopular));
        String url;
        if (sorting_order.equals(getString(R.string.sort_mostpopular))) {
            url = getString(R.string.url_MP);
        } else {
            url = getString(R.string.url_HR);

        }


        fetch.execute(url);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridview = (GridView) rootView.findViewById(R.id.grid_view);

        imageAdapter = new ImageAdapter(getActivity(), new ArrayList<String>());
        gridview.setAdapter(imageAdapter);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String movies = fetch.moviesJsonStr;

                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, movies);
                intent.putExtra(Intent.EXTRA_REFERRER,Integer.toString(position));
                startActivity(intent);
            }
        });

        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        updateGrid();
    }

    public class FetchPostersTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchPostersTask.class.getSimpleName();
        String moviesJsonStr = null;

        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                String[] Posters;
                if (buffer.length() == 0) {
                    return null;
                }
                moviesJsonStr = buffer.toString();

                try {
                    Posters = getPosters(moviesJsonStr, 4);
                    return Posters;


                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }


            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }


        protected void onPostExecute(String[] result) {
            if (result != null) {
                imageAdapter.moviePosters.clear();
                for (int i = 0; i < result.length; i++) {
                    imageAdapter.moviePosters.add(result[i]);
                }
                imageAdapter.notifyDataSetChanged();
            }
        }
    }
}
