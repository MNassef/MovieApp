import android.widget.ArrayAdapter;

/*
package com.example.mohamednassef.movieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReviewFragment extends Fragment {


    public ArrayAdapter<String> mReviewsAdapter;


    public ReviewFragment() {

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle(getString(R.string.reviews_fragment_title));

        View rootReviewView = inflater.inflate(R.layout.fragment_review, container, false);

        String detailsJsonStr = null;
        String[] authors = null;
        String[] reviews = null;
        String[] Combined;


        Bundle args = getArguments();
        if (args != null && args.containsKey("detailsStr"))
            detailsJsonStr = args.getString("detailsStr");


        try {
            authors = getReviewAuthors(detailsJsonStr);
            reviews = getMovieReviews(detailsJsonStr);

        } catch (JSONException e) {
            Log.e("Log_TAG", e.getMessage(), e);
            e.printStackTrace();

        }

        if (authors.length > 0) {
            Combined = new String[authors.length];
            int i;

            for (i = 0; i < Combined.length; i++) {
                Combined[i] = authors[i] + " \n \n" + reviews[i];
            }

        } else {
            Combined = new String[1];
            Combined[0] = getString(R.string.noReviews);
        }


        List<String> reviewsAuthors = new ArrayList<String>(Arrays.asList(Combined));


        mReviewsAdapter =
                new ArrayAdapter<String>(getActivity(), R.layout.listview_item_reviews,
                        R.id.review_tview, reviewsAuthors);

        ListView reviewsListView = (ListView) rootReviewView.findViewById(R.id.listview_reviews);
        reviewsListView.setAdapter(mReviewsAdapter);


        return rootReviewView;
    }

}
*/