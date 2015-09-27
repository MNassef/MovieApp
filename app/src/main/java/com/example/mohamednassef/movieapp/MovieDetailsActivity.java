package com.example.mohamednassef.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MovieDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (savedInstanceState == null) {

            String movieStr = getIntent().getStringExtra(getIntent().EXTRA_TEXT);
            String moviePostn = getIntent().getStringExtra(getIntent().EXTRA_REFERRER);
            String urlMovieDetails = getIntent().getStringExtra(getIntent().ACTION_ASSIST);
            String movieId = getIntent().getStringExtra(getIntent().EXTRA_REFERRER_NAME);

            Bundle arguments = new Bundle();
            String[] argsParams = {movieStr, moviePostn, urlMovieDetails, movieId};
            arguments.putStringArray("Params", argsParams);

            MovieDetailsActivityFragment fragment = new MovieDetailsActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}