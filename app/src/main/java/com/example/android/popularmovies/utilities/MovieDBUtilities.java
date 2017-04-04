package com.example.android.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.data.MovieDBAPIKey;

import java.net.URL;

/**
 * Created by Ian on 4/1/2017.
 */

public class MovieDBUtilities {

    private static final String LOG_TAG = MovieDBUtilities.class.getSimpleName();



    private static final String BASE_MOVIE_DB_URL = "http://api.themoviedb.org/3/movie/";
    public static final String MOVIE_DB_POPULAR_SERVICE = "popular";
    public static final String MOVIE_DB_TOP_RATED_SERVICE = "top_rated";
    public static final String MOVIE_DB_VIDEO_SERVICE = "videos";
    public static final String MOVIE_DB_REVIEW_SERVICE = "reviews";

    private static final String MOVIE_DB_API_KEY_PARAM = "api_key";

    private static final String BASE_MOVIE_DB_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String MOVIE_DB_IMAGE_SIZE = "w185";

    public static URL GetMoviesURL(String service) {

        Log.d(LOG_TAG, "Service Type " + service);

        Uri builtUri = Uri.parse(BASE_MOVIE_DB_URL + service).buildUpon()
                .appendQueryParameter(MOVIE_DB_API_KEY_PARAM, MovieDBAPIKey.MOVIE_DB_API_KEY)
                .build();


        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d(LOG_TAG, url.toString());
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Forming URL " + e.toString());
        }

        return url;
    }

    public static URL GetTrailersURL(String movieID) {

        Uri builtUri = Uri.parse(BASE_MOVIE_DB_URL + movieID + "/" + MOVIE_DB_VIDEO_SERVICE).buildUpon()
                .appendQueryParameter(MOVIE_DB_API_KEY_PARAM, MovieDBAPIKey.MOVIE_DB_API_KEY)
                .build();


        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d(LOG_TAG, url.toString());
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Forming URL " + e.toString());
        }

        return url;
    }

    public static URL GetReviewsURL(String movieID) {

        Uri builtUri = Uri.parse(BASE_MOVIE_DB_URL + movieID + "/" + MOVIE_DB_REVIEW_SERVICE).buildUpon()
                .appendQueryParameter(MOVIE_DB_API_KEY_PARAM, MovieDBAPIKey.MOVIE_DB_API_KEY)
                .build();


        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d(LOG_TAG, url.toString());
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Forming URL " + e.toString());
        }

        return url;
    }

    public static String GetFullImageURL(String relativeImageURL) {
        return BASE_MOVIE_DB_IMAGE_URL + MOVIE_DB_IMAGE_SIZE + "/" + relativeImageURL;
    }
}
