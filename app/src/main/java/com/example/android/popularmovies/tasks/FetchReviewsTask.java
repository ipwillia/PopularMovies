package com.example.android.popularmovies.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmovies.interfaces.FetchReviewsTaskCaller;
import com.example.android.popularmovies.utilities.MovieDBJsonUtilities;
import com.example.android.popularmovies.utilities.MovieDBUtilities;
import com.example.android.popularmovies.utilities.NetworkUtilities;
import com.example.android.popularmovies.viewModels.ReviewViewModel;

import java.net.URL;

/**
 * Created by wian on 7/7/2017.
 */

public class FetchReviewsTask extends AsyncTask<String, Void, ReviewViewModel[]> {

    private final String LOG_TAG = FetchReviewsTask.class.getSimpleName();
    FetchReviewsTaskCaller mfetchReviewsTaskCaller;

    public FetchReviewsTask(FetchReviewsTaskCaller fetchReviewsTaskCaller) {
        mfetchReviewsTaskCaller = fetchReviewsTaskCaller;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        mfetchReviewsTaskCaller.reviewsDataRequestInitiated();
    }

    @Override
    protected ReviewViewModel[] doInBackground(String... params) {
        if (params.length == 0) {
            Log.e(LOG_TAG, "No params specified for doInBackground");
            return null;
        }

        ReviewViewModel[] reviewViewModels = null;
        String movieID = params[0];

        Log.d(LOG_TAG, "Making request for reviews for ID " + movieID);
        URL movieRequestUrl = MovieDBUtilities.GetReviewsURL(movieID);

        try {
            String jsonMovieResponse = NetworkUtilities.getResponseFromHttpUrl(movieRequestUrl);
            reviewViewModels = MovieDBJsonUtilities.getReviewViewModelsFromJson(jsonMovieResponse);
        } catch(Exception e) {
            Log.e(LOG_TAG, "Network error: " + e.toString());
        }

        return reviewViewModels;
    }

    @Override
    protected void onPostExecute(ReviewViewModel[] reviewViewModels) {
        if(reviewViewModels != null) {
            mfetchReviewsTaskCaller.receiveReviewsData(reviewViewModels);
        } else {
            mfetchReviewsTaskCaller.errorLoadingReviewsData();
        }
    }
}