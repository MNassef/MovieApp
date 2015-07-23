package com.example.mohamednassef.movieapp;

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
import android.widget.GridView;
import android.widget.Toast;

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
        inflater.inflate(R.menu.menu_movie_fragment, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int item_selected = item.getItemId();
        if (item_selected == R.id.refresh) {
            updateGrid();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void updateGrid() {
        FetchPostersTask fetch = new FetchPostersTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sorting_order = prefs.getString(getString(R.string.sorting_key),
                getString(R.string.sort_mostpopular));
        Toast.makeText(getActivity(), sorting_order,
                Toast.LENGTH_LONG).show();
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


        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        updateGrid();
    }

    public class FetchPostersTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchPostersTask.class.getSimpleName();

        @Override
        protected String[] doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast vote_average.desc
                //URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=d2d5f7382930f7cf1e176da00f076953");
                URL url = new URL(params[0]);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
                String[] Posters;
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();

                try {
                    Posters = getPosters(forecastJsonStr, 6);
                    return Posters;


                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }


            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
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
                //imageAdapter.updateResults(result);
                imageAdapter.moviePosters.clear();

                for (int i = 0; i < result.length; i++) {
                    imageAdapter.moviePosters.add(result[i]);
                }
                imageAdapter.notifyDataSetChanged();
            }
        }
    }
}
