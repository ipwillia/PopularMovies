package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.adapters.ReviewListAdapter;
import com.example.android.popularmovies.adapters.VideoListAdapter;
import com.example.android.popularmovies.interfaces.FetchReviewsTaskCaller;
import com.example.android.popularmovies.interfaces.FetchVideosTaskCaller;
import com.example.android.popularmovies.tasks.FetchReviewsTask;
import com.example.android.popularmovies.tasks.FetchVideosTask;
import com.example.android.popularmovies.utilities.ListViewUtilities;
import com.example.android.popularmovies.viewModels.MovieViewModel;
import com.example.android.popularmovies.viewModels.ReviewViewModel;
import com.example.android.popularmovies.viewModels.VideoViewModel;
import com.squareup.picasso.Picasso;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.popularmovies.utilities.ListViewUtilities.JustifyListViewHeightBasedOnChildren;

public class MovieDetail extends AppCompatActivity implements FetchVideosTaskCaller, FetchReviewsTaskCaller {

    private final String LOG_TAG = MovieDetail.class.getSimpleName();

    static final String STATE_MOVIE_DETAIL_VIEWMODEL = "movie_poster_viewmodel";
    private MovieViewModel mMovieViewModel;

    @BindView(R.id.tv_movie_detail_title) TextView mTitleTextView;
    @BindView(R.id.iv_movie_detail_poster) ImageView mPosterImageView;
    @BindView(R.id.tv_movie_detail_release_date) TextView mReleaseDateTextView;
    @BindView(R.id.tv_movie_detail_vote_average) TextView mVoteAverageTextView;
    @BindView(R.id.tv_movie_detail_overview) TextView mOverviewTextView;

    @BindView(R.id.lv_videos) ListView mVideosListView;
    @BindView(R.id.pb_videos_loading_indicator) ProgressBar mVideosLoadingProgressBar;
    @BindView(R.id.tv_videos_error_message) TextView mVideosErrorMessageTextView;

    @BindView(R.id.lv_reviews) ListView mReviewsListView;
    @BindView(R.id.pb_reviews_loading_indicator) ProgressBar mReviewsLoadingProgressBar;
    @BindView(R.id.tv_reviews_error_message) TextView mReviewsErrorMessageTextView;

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

            //Videos
            if(mMovieViewModel.VideoViewModels != null)
            {
                Log.d(LOG_TAG, "MovieViewModel already has Videos");
                receiveVideosData(mMovieViewModel.VideoViewModels);
            }
            else
            {
                Log.d(LOG_TAG, "MovieViewModel has no Videos");
                new FetchVideosTask(this).execute(mMovieViewModel.MovieID);
            }

            //Reviews
            if(mMovieViewModel.ReviewViewModels != null)
            {
                Log.d(LOG_TAG, "MovieViewModel already has Reviews");
                receiveReviewsData(mMovieViewModel.ReviewViewModels);
            }
            else
            {
                Log.d(LOG_TAG, "MovieViewModel has no Reviews");
                new FetchReviewsTask(this).execute(mMovieViewModel.MovieID);
            }
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



    //region Videos

    private void showVideosLoadingIndicator() {
        mVideosLoadingProgressBar.setVisibility(View.VISIBLE);
        mVideosListView.setVisibility(View.GONE);
    }

    private void hideVideosLoadingIndicator() {
        mVideosLoadingProgressBar.setVisibility(View.GONE);
    }

    private void showVideosErrorMessageTextView() {
        mVideosErrorMessageTextView.setVisibility(View.VISIBLE);
        mVideosListView.setVisibility(View.GONE);
    }

    private void showVideosListView() {
        mVideosListView.setVisibility(View.VISIBLE);
        mVideosLoadingProgressBar.setVisibility(View.GONE);
        mVideosErrorMessageTextView.setVisibility(View.GONE);
    }

    public void videosDataRequestInitiated() {
        showVideosLoadingIndicator();
    }

    public void receiveVideosData(VideoViewModel[] videoViewModels) {
        hideVideosLoadingIndicator();
        Log.d(LOG_TAG, "Got " + videoViewModels.length + " videos");
        mMovieViewModel.VideoViewModels = videoViewModels;

        //Populate view
        mVideosListView.setAdapter(new VideoListAdapter(this, videoViewModels));
        JustifyListViewHeightBasedOnChildren(mVideosListView);
        mVideosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoViewModel videoViewModel = (VideoViewModel) parent.getItemAtPosition(position);
                Uri videoUri = videoViewModel.GetUri();
                if(videoUri != null) {
                    startActivity(new Intent(Intent.ACTION_VIEW, videoUri));
                }
            }
        });
        showVideosListView();
    }

    public void errorLoadingVideosData() {
        hideVideosLoadingIndicator();
        showVideosErrorMessageTextView();
    }

    //endregion

    //region Reviews

    private void showReviewsLoadingIndicator() {
        mReviewsLoadingProgressBar.setVisibility(View.VISIBLE);
        mReviewsListView.setVisibility(View.GONE);
    }

    private void hideReviewsLoadingIndicator() {
        mReviewsLoadingProgressBar.setVisibility(View.GONE);
    }

    private void showReviewsErrorMessageTextView() {
        mReviewsErrorMessageTextView.setVisibility(View.VISIBLE);
        mReviewsListView.setVisibility(View.GONE);
    }

    private void showReviewsListView() {
        mReviewsListView.setVisibility(View.VISIBLE);
        mReviewsLoadingProgressBar.setVisibility(View.GONE);
        mReviewsErrorMessageTextView.setVisibility(View.GONE);
    }

    public void reviewsDataRequestInitiated() {
        showReviewsLoadingIndicator();
    }

    public void receiveReviewsData(ReviewViewModel[] reviewViewModels) {
        hideReviewsLoadingIndicator();
        Log.d(LOG_TAG, "Got " + reviewViewModels.length + " reviews");
        mMovieViewModel.ReviewViewModels = reviewViewModels;

        //Populate view
        mReviewsListView.setAdapter(new ReviewListAdapter(this, reviewViewModels));
        JustifyListViewHeightBasedOnChildren(mReviewsListView);
        showReviewsListView();
    }

    public void errorLoadingReviewsData() {
        hideReviewsLoadingIndicator();
        showReviewsErrorMessageTextView();
    }

    //endregion

}
