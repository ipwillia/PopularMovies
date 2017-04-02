package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.viewModels.MovieViewModel;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    private final String LOG_TAG = MovieDetail.class.getSimpleName();

    static final String STATE_MOVIE_DETAIL_VIEWMODEL = "movie_poster_viewmodel";
    private MovieViewModel mMovieViewModel;

    private TextView mTitleTextView;
    private ImageView mPosterImageView;
    private TextView mReleaseDateTextView;
    private TextView mVoteAverageTextView;
    private TextView mOverviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //Get view elements
        Log.d(LOG_TAG, "Getting movie detail view elements");
        mTitleTextView = (TextView) findViewById(R.id.tv_movie_detail_title);
        mPosterImageView = (ImageView) findViewById(R.id.iv_movie_detail_poster);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_movie_detail_release_date);
        mVoteAverageTextView = (TextView) findViewById(R.id.tv_movie_detail_vote_average);
        mOverviewTextView = (TextView) findViewById(R.id.tv_movie_detail_overview);


        //Get movie viewmodel
        if(savedInstanceState != null) {
            Log.d(LOG_TAG, "Recovering movie detail from previous state");
            mMovieViewModel = (MovieViewModel) savedInstanceState.getParcelable(STATE_MOVIE_DETAIL_VIEWMODEL);
        } else {
            Log.d(LOG_TAG, "Loading movie detail from intent");
            Intent originatingIntent = getIntent();
            if(originatingIntent != null) {
                mMovieViewModel = (MovieViewModel) originatingIntent.getParcelableExtra(MovieViewModel.PARCELABLE_KEY);
            }
        }

        //Apply movie viewmodel
        if(mMovieViewModel != null) {
            Log.d(LOG_TAG, "Setting movie detail for movie id " + mMovieViewModel.MovieID);

            Log.d(LOG_TAG, "Setting title");
            mTitleTextView.setText(mMovieViewModel.Title);
            Log.d(LOG_TAG, "Setting poster");
            Picasso.with(this).load(mMovieViewModel.PosterURL).into(mPosterImageView);
            Log.d(LOG_TAG, "Setting release date");
            mReleaseDateTextView.setText(getString(R.string.label_release_date) + " " + mMovieViewModel.ReleaseDate);
            Log.d(LOG_TAG, "Setting vote average");
            mVoteAverageTextView.setText(getString(R.string.label_vote_average) + " " + Double.toString(mMovieViewModel.VoteAverage));
            Log.d(LOG_TAG, "Setting overview");
            mOverviewTextView.setText(mMovieViewModel.Overview);
        } else {
            Log.e(LOG_TAG, "Cannot load, mMovieViewModel is null");
        }

    }
}
