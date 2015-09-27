package com.example.mohamednassef.movieapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements MovieFragment.Callback {


    private static final String MOVIE_DETAIL_FRAGMENT_TAG = "MDF";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (findViewById(R.id.movie_detail_container) != null) {

            Toast.makeText(this, "twopane", Toast.LENGTH_LONG).show();
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new MovieDetailsActivityFragment(), MOVIE_DETAIL_FRAGMENT_TAG)
                        .commit();
            }

        } else


        {
            Toast.makeText(this, "onepane", Toast.LENGTH_LONG).show();
            mTwoPane = false;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(String cbMovies, String cbMoviePos, String cbUrlMovieDetails, String cbMovieID) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            String[] argsParams = {cbMovies, cbMoviePos, cbUrlMovieDetails, cbMovieID};
            args.putStringArray("Params", argsParams);

            MovieDetailsActivityFragment newFragment = new MovieDetailsActivityFragment();
            newFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, newFragment, MOVIE_DETAIL_FRAGMENT_TAG).commit();


        } else {
            Intent intent = new Intent(this, MovieDetailsActivity.class)
                    .putExtra(Intent.EXTRA_TEXT, cbMovies);
            intent.putExtra(Intent.EXTRA_REFERRER, cbMoviePos);
            intent.putExtra(Intent.ACTION_ASSIST, cbUrlMovieDetails);
            intent.putExtra(Intent.EXTRA_REFERRER_NAME, cbMovieID);
            startActivity(intent);
        }
    }
}
