package com.example.android.popularmovies.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmovies.interfaces.FetchMovieTaskCaller;
import com.example.android.popularmovies.utilities.MovieDBJsonUtilities;
import com.example.android.popularmovies.utilities.MovieDBUtilities;
import com.example.android.popularmovies.utilities.NetworkUtilities;
import com.example.android.popularmovies.viewModels.MovieViewModel;

import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by Ian on 4/4/2017.
 */

public class FetchMoviesTask extends AsyncTask<String, Void, MovieViewModel[]> {

    private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
    FetchMovieTaskCaller mFetchMovieTaskCaller;

    public FetchMoviesTask(FetchMovieTaskCaller fetchMovieTaskCaller) {
        mFetchMovieTaskCaller = fetchMovieTaskCaller;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        mFetchMovieTaskCaller.movieDataRequestInitiated();
    }

    @Override
    protected MovieViewModel[] doInBackground(String... params) {
        if (params.length == 0) {
            Log.e(LOG_TAG, "No params specified for doInBackground");
            return null;
        }

        MovieViewModel[] movieViewModels = null;
        String requestService = params[0];

        Log.d(LOG_TAG, "Making request " + requestService);
        URL movieRequestUrl = MovieDBUtilities.GetMoviesURL(requestService);

        try {
            String jsonMovieResponse = NetworkUtilities.getResponseFromHttpUrl(movieRequestUrl);
            movieViewModels = MovieDBJsonUtilities.getMovieViewModelsFromJson(jsonMovieResponse);
        } catch(Exception e) {
            Log.e(LOG_TAG, "Network error: " + e.toString());
        }

        return movieViewModels;
    }

    @Override
    protected void onPostExecute(MovieViewModel[] movieViewModels) {
        if(movieViewModels != null) {
            mFetchMovieTaskCaller.receiveMovieData(movieViewModels);
        } else {
            mFetchMovieTaskCaller.errorLoadingMovieData();
        }
    }
}