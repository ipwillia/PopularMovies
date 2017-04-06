package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.utilities.MovieDBJsonUtilities;
import com.example.android.popularmovies.utilities.MovieDBUtilities;
import com.example.android.popularmovies.utilities.NetworkUtilities;
import com.example.android.popularmovies.viewModels.MovieViewModel;
import com.example.android.popularmovies.viewModels.VideoViewModel;
import com.squareup.picasso.Picasso;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetail extends AppCompatActivity {

    private final String LOG_TAG = MovieDetail.class.getSimpleName();

    static final String STATE_MOVIE_DETAIL_VIEWMODEL = "movie_poster_viewmodel";
    private MovieViewModel mMovieViewModel;

    @BindView(R.id.tv_movie_detail_title) TextView mTitleTextView;
    @BindView(R.id.iv_movie_detail_poster) ImageView mPosterImageView;
    @BindView(R.id.tv_movie_detail_release_date) TextView mReleaseDateTextView;
    @BindView(R.id.tv_movie_detail_vote_average) TextView mVoteAverageTextView;
    @BindView(R.id.tv_movie_detail_overview) TextView mOverviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //Get view elements
        Log.d(LOG_TAG, "Binding view elements with butterknife");
        ButterKnife.bind(this);

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


            mTitleTextView.setText(mMovieViewModel.Title);

            Picasso.with(this).load(mMovieViewModel.PosterURL).into(mPosterImageView);

            String releaseDateContent = getString(R.string.label_release_date) + " " + mMovieViewModel.ReleaseDate;
            mReleaseDateTextView.setText(releaseDateContent);

            String voteAverageContent = getString(R.string.label_vote_average) + " " + Double.toString(mMovieViewModel.VoteAverage) + " " + getString(R.string.postfix_vote_average);
            mVoteAverageTextView.setText(voteAverageContent);

            mOverviewTextView.setText(mMovieViewModel.Overview);
        } else {
            Log.e(LOG_TAG, "Cannot load, mMovieViewModel is null");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Log.d(LOG_TAG, "Putting movie view model in bundle");
        savedInstanceState.putParcelable(STATE_MOVIE_DETAIL_VIEWMODEL, mMovieViewModel);
    }

    /*public class FetchTrailersTask extends AsyncTask<String, Void, VideoViewModel[]> {

        private final String LOG_TAG = MovieDetail.FetchTrailersTask.class.getSimpleName();

        protected void onPreExecute() {
            super.onPreExecute();
            //showLoadingIndicator();
        }

        @Override
        protected VideoViewModel[] doInBackground(String... params) {
            if (params.length == 0) {
                Log.e(LOG_TAG, "No params specified for doInBackground");
                return null;
            }

            VideoViewModel[] videoViewModels = null;
            String movieID = params[0];

            Log.d(LOG_TAG, "Making request for trailers for ID " + movieID);
            URL movieRequestUrl = MovieDBUtilities.GetMoviesURL(movieID);



            try {
                String jsonMovieResponse = NetworkUtilities.getResponseFromHttpUrl(movieRequestUrl);
                videoViewModels = MovieDBJsonUtilities.getVideoViewModelsFromJson(jsonMovieResponse);
            } catch(Exception e) {
                Log.e(LOG_TAG, "Network error: " + e.toString());
            }

            return videoViewModels;
        }

        @Override
        protected void onPostExecute(VideoViewModel[] videoViewModels) {
            hideLoadingIndicator();
            if(videoViewModels != null) {
                showMovieDataView();
                mMovieViewModels = movieViewModels;
                mMovieAdapter.setMovieData(mMovieViewModels);
            } else {
                showErrorMessageTextView();
            }
        }
    }*/
}
