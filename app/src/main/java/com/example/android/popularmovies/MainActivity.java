package com.example.android.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Store views in variables
        Log.d(LOG_TAG, "Getting views");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        //Set up layout manager
        Log.d(LOG_TAG, "Constructing layout manager");
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        Log.d(LOG_TAG, "Requesting layout");
        layoutManager.requestLayout();
        Log.d(LOG_TAG, "Setting layout manager");
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setHasFixedSize(true); //Not super sure about this one

        //Set up the recycler view
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        //Load movie data
        loadMovieData();
    }

    private void loadMovieData() {
        showMovieDataView();

        new FetchMoviesTask().execute(MovieDBUtilities.RequestType.POPULAR);
    }

    public void onClick(MoviePosterViewModel singleMoviePosterViewModel) {
        Context context = this;
        Toast.makeText(context, singleMoviePosterViewModel.MovieID, Toast.LENGTH_SHORT).show();
        //TODO: Implement second activity
    }

    private void showMovieDataView() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessageTextView() {
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void showLoadingIndicator() {
        mLoadingProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
    }

    public class FetchMoviesTask extends AsyncTask<MovieDBUtilities.RequestType, Void, MoviePosterViewModel[]> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingIndicator();
        }

        @Override
        protected MoviePosterViewModel[] doInBackground(MovieDBUtilities.RequestType... params) {
            if (params.length == 0) {
                Log.e(LOG_TAG, "No params specified for doInBackground");
                return null;
            }

            MoviePosterViewModel[] moviePosterViewModels = null;
            MovieDBUtilities.RequestType requestType = params[0];
            URL movieRequestUrl = MovieDBUtilities.GetMoviesURL(requestType);


            try {
                String jsonMovieResponse = NetworkUtilities.getResponseFromHttpUrl(movieRequestUrl);
                moviePosterViewModels = MovieDBJsonUtilities.getMoviePosterViewModelsFromJson(MainActivity.this, jsonMovieResponse);
            } catch(Exception e) {
                Log.e(LOG_TAG, "Network error: " + e.toString());
            }

            return moviePosterViewModels;
        }

        @Override
        protected void onPostExecute(MoviePosterViewModel[] moviePosterViewModels) {
            hideLoadingIndicator();
            if(moviePosterViewModels != null) {
                showMovieDataView();
                mMovieAdapter.setMovieData(moviePosterViewModels);
            } else {
                showErrorMessageTextView();
            }
        }
    }
}