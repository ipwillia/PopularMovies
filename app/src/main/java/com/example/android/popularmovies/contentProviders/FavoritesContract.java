package com.example.android.popularmovies.contentProviders;

import android.provider.BaseColumns;

/**
 * Created by wian on 7/17/2017.
 */

public class FavoritesContract {
    public static final class FavoritesEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_MOVIE_ID = "movieID";
        public static final String COLUMN_POSTER_URL = "posterURL";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_OVERVIEW = "overview";
    }
}
