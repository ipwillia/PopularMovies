package com.example.android.popularmovies.contentProviders;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.popularmovies.viewModels.MovieViewModel;

/**
 * Created by wian on 7/17/2017.
 */

public class FavoritesDBUtilities {
    public static boolean addMovie(SQLiteDatabase db, MovieViewModel movieViewModel) {
        boolean success = true;

        ContentValues movieContentValues = new ContentValues();
        movieContentValues.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID, movieViewModel.MovieID);
        movieContentValues.put(FavoritesContract.FavoritesEntry.COLUMN_POSTER_URL, movieViewModel.PosterURL);
        movieContentValues.put(FavoritesContract.FavoritesEntry.COLUMN_TITLE, movieViewModel.Title);
        movieContentValues.put(FavoritesContract.FavoritesEntry.COLUMN_RELEASE_DATE, movieViewModel.ReleaseDate);
        movieContentValues.put(FavoritesContract.FavoritesEntry.COLUMN_VOTE_AVERAGE, movieViewModel.VoteAverage);
        movieContentValues.put(FavoritesContract.FavoritesEntry.COLUMN_OVERVIEW, movieViewModel.Overview);

        try
        {
            db.beginTransaction();
            db.insert(FavoritesContract.FavoritesEntry.TABLE_NAME, null, movieContentValues);
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            success = false;
        }
        finally
        {
            db.endTransaction();
        }

        return success;
    }

    public static boolean removeMovie(SQLiteDatabase db, MovieViewModel movieViewModel) {
        boolean success = true;

        try
        {
            db.beginTransaction();
            db.delete(FavoritesContract.FavoritesEntry.TABLE_NAME, FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID + "=" + movieViewModel.MovieID, null);
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            success = false;
        }
        finally
        {
            db.endTransaction();
        }

        return success;
    }

    public static MovieViewModel[] getAllMovies(SQLiteDatabase db) {
        MovieViewModel[] movieViewModels = null;

        Cursor cursor = db.query(FavoritesContract.FavoritesEntry.TABLE_NAME, null, null, null, null, null, FavoritesContract.FavoritesEntry.COLUMN_VOTE_AVERAGE);
        if (cursor != null) {
            movieViewModels = new MovieViewModel[cursor.getCount()];
            int index = 0;

            while (cursor.moveToNext()) {
                String movieID = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID));
                String posterURL = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoritesEntry.COLUMN_POSTER_URL));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoritesEntry.COLUMN_TITLE));
                String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoritesEntry.COLUMN_RELEASE_DATE));
                double voteAverage = cursor.getDouble(cursor.getColumnIndexOrThrow(FavoritesContract.FavoritesEntry.COLUMN_VOTE_AVERAGE));
                String overview = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoritesEntry.COLUMN_OVERVIEW));

                MovieViewModel singleMovieViewModel = new MovieViewModel(movieID, posterURL, title, releaseDate, voteAverage, overview);
                singleMovieViewModel.IsFavorite = true;

                movieViewModels[index] = singleMovieViewModel;

                index++;
            }
        }

        return movieViewModels;
    }
}
