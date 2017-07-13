package com.example.android.popularmovies.utilities;

import android.content.Context;
import android.util.Log;

import com.example.android.popularmovies.viewModels.MovieViewModel;
import com.example.android.popularmovies.viewModels.ReviewViewModel;
import com.example.android.popularmovies.viewModels.VideoViewModel;

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

    private static final String TMDB_VIDEO_NAME = "name";
    private static final String TMDB_VIDEO_KEY = "key";
    private static final String TMDB_VIDEO_SITE = "site";

    private static final String TMDB_REVIEW_AUTHOR = "author";
    private static final String TMDB_REVIEW_CONTENT = "content";
    private static final String TMDB_REVIEW_URL = "url";

    public static MovieViewModel[] getMovieViewModelsFromJson(String movieResponseJsonString)
            throws JSONException {
        MovieViewModel[] movieViewModels = null;

        JSONObject movieResponseJson = new JSONObject(movieResponseJsonString);

        JSONArray movieResultsJson = movieResponseJson.getJSONArray(TMDB_RESULTS);

        int movieCount = movieResultsJson.length();

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

    public static VideoViewModel[] getVideoViewModelsFromJson(String videoResponseJsonString)
            throws JSONException {
        VideoViewModel[] videoViewModels = null;

        JSONObject videoResponseJson = new JSONObject(videoResponseJsonString);

        JSONArray videoResultsJson = videoResponseJson.getJSONArray(TMDB_RESULTS);

        int movieCount = videoResultsJson.length();
        Log.d(LOG_TAG, "Found " + movieCount + " videos");

        videoViewModels = new VideoViewModel[videoResultsJson.length()];

        for(int i = 0; i < movieCount; i++) {
            JSONObject singleVideoJson = videoResultsJson.getJSONObject(i);

            String name = singleVideoJson.getString(TMDB_VIDEO_NAME);
            String key = singleVideoJson.getString(TMDB_VIDEO_KEY);
            String site = singleVideoJson.getString(TMDB_VIDEO_SITE);

            VideoViewModel singleVideoViewModel = new VideoViewModel(name, key, site);
            videoViewModels[i] = singleVideoViewModel;
        }

        return videoViewModels;
    }

    public static ReviewViewModel[] getReviewViewModelsFromJson(String reviewResponseJsonString)
            throws JSONException {
        ReviewViewModel[] reviewViewModels = null;

        JSONObject reviewResponseJson = new JSONObject(reviewResponseJsonString);

        JSONArray reviewResultsJson = reviewResponseJson.getJSONArray(TMDB_RESULTS);

        int movieCount = reviewResultsJson.length();
        Log.d(LOG_TAG, "Found " + movieCount + " reviews");

        reviewViewModels = new ReviewViewModel[reviewResultsJson.length()];

        for(int i = 0; i < movieCount; i++) {
            JSONObject singleReviewJson = reviewResultsJson.getJSONObject(i);

            String author = singleReviewJson.getString(TMDB_REVIEW_AUTHOR);
            String content = singleReviewJson.getString(TMDB_REVIEW_CONTENT);
            String url = singleReviewJson.getString(TMDB_REVIEW_URL);

            ReviewViewModel singleReviewViewModel = new ReviewViewModel(author, content, url);
            reviewViewModels[i] = singleReviewViewModel;
        }

        return reviewViewModels;
    }
}
