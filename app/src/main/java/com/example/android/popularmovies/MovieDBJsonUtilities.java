package com.example.android.popularmovies;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ian on 4/1/2017.
 */

public final class MovieDBJsonUtilities {

    private static final String LOG_TAG = MovieDBJsonUtilities.class.getSimpleName();

    private static final String TMDB_RESULTS = "results";

    private static final String TMDB_POSTER_PATH = "poster_path";
    private static final String TMDB_MOVIE_ID = "id";

    public static MoviePosterViewModel[] getMoviePosterViewModelsFromJson(Context context, String movieResponseJsonString)
            throws JSONException {
        MoviePosterViewModel[] moviePosterViewModels = null;

        Log.d(LOG_TAG, "Getting movie response JSON object");
        JSONObject movieResponseJson = new JSONObject(movieResponseJsonString);

        Log.d(LOG_TAG, "Getting movie JSON object array");
        JSONArray movieResultsJson = movieResponseJson.getJSONArray(TMDB_RESULTS);

        int movieCount = movieResultsJson.length();
        Log.d(LOG_TAG, "Found " + movieCount + " movies");

        moviePosterViewModels = new MoviePosterViewModel[movieResultsJson.length()];

        for(int i = 0; i < movieCount; i++) {
            JSONObject singleMovieJson = movieResultsJson.getJSONObject(i);

            String movieID = singleMovieJson.getString(TMDB_MOVIE_ID);
            String relativePosterPath = singleMovieJson.getString(TMDB_POSTER_PATH);

            String fullPosterPath = MovieDBUtilities.GetFullImageURL(relativePosterPath);

            MoviePosterViewModel singleMoviePosterViewModel = new MoviePosterViewModel(movieID, fullPosterPath);
            moviePosterViewModels[i] = singleMoviePosterViewModel;
        }

        return moviePosterViewModels;
    }
}
