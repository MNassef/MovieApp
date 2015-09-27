package com.example.mohamednassef.movieapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
public class MovieDetailsActivityFragment extends Fragment {


    private static String detailsJsonStr;
    private static boolean isFavorite;
    private final String LOG_TAG = MovieDetailsActivityFragment.class.getSimpleName();
    public ArrayAdapter<String> mTrailersAdapter;
    public ArrayAdapter<String> mReviewsAdapter;
    private FetchMovieDetailsTask fetchMovieDetailsTask;
    private String[] youtubeUrls;


    public MovieDetailsActivityFragment() {
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

    public static String[] getMovieTrailerLabels(String detailsJSONStr) throws JSONException {
        if (detailsJSONStr != null) {
            String[] trailerLabels;
            JSONObject detailsJSON = new JSONObject(detailsJSONStr);
            JSONObject trailersJSON = detailsJSON.getJSONObject("trailers");
            JSONArray youtubeTrailers = trailersJSON.getJSONArray("youtube");
            trailerLabels = new String[youtubeTrailers.length()];

            for (int i = 0; i < youtubeTrailers.length(); i++) {
                JSONObject temp = youtubeTrailers.getJSONObject(i);
                trailerLabels[i] = temp.getString("name");

            }

            return trailerLabels;
        } else {
            return null;
        }


    }


    public static String[] getMovieYoutubeUrl(String detailsJSONStr) throws JSONException {
        if (detailsJSONStr != null) {
            String[] youtubeUrls;
            JSONObject detailsJSON = new JSONObject(detailsJSONStr);
            JSONObject trailersJSON = detailsJSON.getJSONObject("trailers");
            JSONArray youtubeTrailers = trailersJSON.getJSONArray("youtube");
            youtubeUrls = new String[youtubeTrailers.length()];

            for (int i = 0; i < youtubeTrailers.length(); i++) {
                JSONObject temp = youtubeTrailers.getJSONObject(i);
                youtubeUrls[i] = temp.getString("source");

            }

            return youtubeUrls;
        } else {
            return null;
        }


    }



    public static String getMoviePoster(String movieJSONStr, int movieIndex) throws JSONException {
        String movPoster;
        JSONObject moviesJSON = new JSONObject(movieJSONStr);
        JSONArray results = moviesJSON.getJSONArray("results");
        JSONObject movie = results.getJSONObject(movieIndex);
        movPoster = movie.getString("poster_path");

        return movPoster;

    }

    public static String[] getMovieReviews(String detailsJSONStr) throws JSONException {
        if (detailsJSONStr != null) {
            String[] reviewsArray;
            JSONObject detailsJSON = new JSONObject(detailsJSONStr);
            JSONObject reviewsJSON = detailsJSON.getJSONObject("reviews");
            JSONArray reviewResults = reviewsJSON.getJSONArray("results");
            reviewsArray = new String[reviewResults.length()];

            for (int i = 0; i < reviewResults.length(); i++) {
                JSONObject temp = reviewResults.getJSONObject(i);
                reviewsArray[i] = temp.getString("content");

            }

            return reviewsArray;
        } else {
            return null;
        }


    }


    public static String[] getReviewAuthors(String detailsJSONStr) throws JSONException {
        if (detailsJSONStr != null) {
            String[] authorsArray;
            JSONObject detailsJSON = new JSONObject(detailsJSONStr);
            JSONObject reviewsJSON = detailsJSON.getJSONObject("reviews");
            JSONArray reviewResults = reviewsJSON.getJSONArray("results");
            authorsArray = new String[reviewResults.length()];

            for (int i = 0; i < reviewResults.length(); i++) {
                JSONObject temp = reviewResults.getJSONObject(i);
                authorsArray[i] = temp.getString("author");
                Log.v(Integer.toString(i), authorsArray[i]);

            }

            return authorsArray;
        } else {
            return null;
        }


    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


  /*      SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
*/
        View rootDetailView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        String movieStr;
        String moviePostn;
        final String moviePoster;
        String movieName;
        String movieOverview;
        String movieDate;
        String movieRating;
        String urlMovieDetails;
        final String movieId;
        String[] trailerLabels;


        mTrailersAdapter =
                new ArrayAdapter<String>(getActivity(), R.layout.listview_item_trailer,
                        R.id.trailer_tview, new ArrayList<String>());


        mReviewsAdapter =
                new ArrayAdapter<String>(getActivity(), R.layout.listview_item_reviews,
                        R.id.review_tview, new ArrayList<String>());


        fetchMovieDetailsTask = new FetchMovieDetailsTask();
        Bundle arguments = getArguments();
        
        if (arguments != null) {

            String[] paramsArray = arguments.getStringArray("Params");
            movieStr = paramsArray[0];
            moviePostn = paramsArray[1];
            urlMovieDetails = paramsArray[2];
            movieId = paramsArray[3];


            isFavorite = MyUtility.isFavorite(getActivity().getApplicationContext(), Integer.parseInt(movieId));


            if (isNetworkAvailable()) {
                fetchMovieDetailsTask.execute(urlMovieDetails);
            }


            try {
                movieName = getMovieName(movieStr, Integer.parseInt(moviePostn));
                moviePoster = getMoviePoster(movieStr, Integer.parseInt(moviePostn));
                movieOverview = getMovieOverview(movieStr, Integer.parseInt(moviePostn));
                movieDate = getMovieDate(movieStr, Integer.parseInt(moviePostn));
                movieRating = getMovieRating(movieStr, Integer.parseInt(moviePostn));
                trailerLabels = getMovieTrailerLabels(detailsJsonStr);


                ((TextView) rootDetailView.findViewById(R.id.tview_r1))
                        .setText(movieName);
                ((TextView) rootDetailView.findViewById(R.id.tview_r2_1))
                        .setText("Release Date \n " + movieDate);
                ((TextView) rootDetailView.findViewById(R.id.tview_r2_2))
                        .setText("Rating \n " + movieRating);
                ((TextView) rootDetailView.findViewById(R.id.tview_r3))
                        .setText(movieOverview);

                ImageView imageView = (ImageView) rootDetailView.findViewById(R.id.iview_r2);
                Picasso.with(getActivity()).load(getString(R.string.url_Posters) + moviePoster).into(imageView);

                final ImageButton favButton = (ImageButton) rootDetailView.findViewById(R.id.favorite);
                if (isFavorite) {
                    favButton.setBackgroundResource(R.drawable.btn_star_big_on_pressed);

                } else {
                    favButton.setBackgroundResource(R.drawable.btn_star_big_off_pressed);

                }


                ListView listView = (ListView) rootDetailView.findViewById(R.id.listview_trailers);
                listView.setAdapter(mTrailersAdapter);

                TextView listViewTitle = new TextView(getActivity());
                listViewTitle.setPadding(16, 0, 0, 0);
                listViewTitle.setText(getString(R.string.trailersListHeader));
                listView.addHeaderView(listViewTitle);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)

                    {
                        String videoId = youtubeUrls[position];
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_Base_URL) + videoId)));

                    }
                });


                // -----------------


                ListView reviewsListView = (ListView) rootDetailView.findViewById(R.id.listview_reviews);
                reviewsListView.setAdapter(mReviewsAdapter);

                TextView reviewsTitle = new TextView(getActivity());
                reviewsTitle.setPadding(16, 0, 0, 0);
                reviewsTitle.setText(getString(R.string.reviewsListHeader));
                reviewsListView.addHeaderView(reviewsTitle);


                //---------------------=




                favButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (!isFavorite) {
                            isFavorite = true;
                            favButton.setBackgroundResource(R.drawable.btn_star_big_on_pressed);



                            MyUtility.addFavoriteMovie(getActivity().getApplicationContext(), movieId, 0);
                            MyUtility.addFavoriteMovie(getActivity().getApplicationContext(), moviePoster, 1);
                            Toast.makeText(getActivity(), MyUtility.getFavoriteMoviesString(getActivity().getApplicationContext(), null, "posters"),
                                    Toast.LENGTH_LONG).show();


                        } else {
                            isFavorite = false;
                            favButton.setBackgroundResource(R.drawable.btn_star_big_off_pressed);
                            MyUtility.removeMovieFromFavorite(getActivity().getApplicationContext(), movieId, 0);
                            MyUtility.removeMovieFromFavorite(getActivity().getApplicationContext(), moviePoster, 1);
                            Toast.makeText(getActivity(), MyUtility.getFavoriteMoviesString(getActivity().getApplicationContext(), null, "posters"),
                                    Toast.LENGTH_LONG).show();

                        }

                    }
                });


            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

        }


        return rootDetailView;

    }


    public class FetchMovieDetailsTask extends AsyncTask<String, Void, String> {

        private final String LOG_TAG = FetchMovieDetailsTask.class.getSimpleName();


        @Override
        protected String doInBackground(String... params) {
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

                if (buffer.length() == 0) {
                    return null;
                }
                detailsJsonStr = buffer.toString();


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
            Log.v("details", detailsJsonStr);


            return detailsJsonStr;
        }


        protected void onPostExecute(String detailsJSONStr) {


            String[] currentTrailers = null;
            String[] authors = null;
            String[] reviews = null;
            String[] reviewsAuthorsCombined = null;

            try {
                currentTrailers = getMovieTrailerLabels(detailsJSONStr);
                youtubeUrls = getMovieYoutubeUrl(detailsJsonStr);
                authors = getReviewAuthors(detailsJsonStr);
                reviews = getMovieReviews(detailsJsonStr);

            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            if (currentTrailers != null) {
                mTrailersAdapter.clear();
                for (int i = 0; i < currentTrailers.length; i++) {
                    mTrailersAdapter.add(currentTrailers[i]);
                }
                mTrailersAdapter.notifyDataSetChanged();
            }


            mReviewsAdapter.clear();
            if (authors.length > 0) {

                reviewsAuthorsCombined = new String[authors.length];
                int i;

                for (i = 0; i < reviewsAuthorsCombined.length; i++) {
                    reviewsAuthorsCombined[i] = authors[i] + " \n \n" + reviews[i];
                    mReviewsAdapter.add(reviewsAuthorsCombined[i]);
                }

            } else {
                reviewsAuthorsCombined = new String[1];
                reviewsAuthorsCombined[0] = getString(R.string.noReviews);
            }
            mReviewsAdapter.notifyDataSetChanged();


        }
    }


}
