package com.example.android.popularmovies.utilities;

import android.content.Context;
import android.util.Log;

import com.example.android.popularmovies.viewModels.MovieViewModel;

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
    private static final String TMDB_TITLE = "title";
    private static final String TMDB_RELEASE_DATE = "release_date";
    private static final String TMDB_VOTE_AVERAGE = "vote_average";
    private static final String TMDB_OVERVIEW = "overview";

    public static MovieViewModel[] getMoviePosterViewModelsFromJson(Context context, String movieResponseJsonString)
            throws JSONException {
        MovieViewModel[] movieViewModels = null;

        Log.d(LOG_TAG, "Getting movie response JSON object");
        JSONObject movieResponseJson = new JSONObject(movieResponseJsonString);

        Log.d(LOG_TAG, "Getting movie JSON object array");
        JSONArray movieResultsJson = movieResponseJson.getJSONArray(TMDB_RESULTS);

        int movieCount = movieResultsJson.length();
        Log.d(LOG_TAG, "Found " + movieCount + " movies");

        movieViewModels = new MovieViewModel[movieResultsJson.length()];

        for(int i = 0; i < movieCount; i++) {
            JSONObject singleMovieJson = movieResultsJson.getJSONObject(i);

            String movieID = singleMovieJson.getString(TMDB_MOVIE_ID);
            String relativePosterPath = singleMovieJson.getString(TMDB_POSTER_PATH);
            String title = singleMovieJson.getString(TMDB_TITLE);
            String releaseDate = singleMovieJson.getString(TMDB_RELEASE_DATE);
            double voteAverage = singleMovieJson.getDouble(TMDB_VOTE_AVERAGE);
            String overview = singleMovieJson.getString(TMDB_OVERVIEW);

            String fullPosterPath = MovieDBUtilities.GetFullImageURL(relativePosterPath);

            MovieViewModel singleMovieViewModel = new MovieViewModel(movieID, fullPosterPath, title, releaseDate, voteAverage, overview);
            movieViewModels[i] = singleMovieViewModel;
        }

        return movieViewModels;
    }
}
