package com.example.android.popularmovies.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmovies.MovieDetail;
import com.example.android.popularmovies.interfaces.FetchVideosTaskCaller;
import com.example.android.popularmovies.utilities.MovieDBJsonUtilities;
import com.example.android.popularmovies.utilities.MovieDBUtilities;
import com.example.android.popularmovies.utilities.NetworkUtilities;
import com.example.android.popularmovies.viewModels.VideoViewModel;

import java.net.URL;

/**
 * Created by wian on 7/7/2017.
 */

public class FetchVideosTask extends AsyncTask<String, Void, VideoViewModel[]> {

    private final String LOG_TAG = FetchVideosTask.class.getSimpleName();
    FetchVideosTaskCaller mfetchVideosTaskCaller;

    public FetchVideosTask(FetchVideosTaskCaller fetchVideosTaskCaller) {
        mfetchVideosTaskCaller = fetchVideosTaskCaller;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        mfetchVideosTaskCaller.videosDataRequestInitiated();
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
        URL movieRequestUrl = MovieDBUtilities.GetTrailersURL(movieID);

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
        if(videoViewModels != null) {
            mfetchVideosTaskCaller.receiveVideosData(videoViewModels);
        } else {
            mfetchVideosTaskCaller.errorLoadingVideosData();
        }
    }
}