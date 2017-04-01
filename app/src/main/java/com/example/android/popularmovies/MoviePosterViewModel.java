package com.example.android.popularmovies;

/**
 * Created by Ian on 4/1/2017.
 */

public class MoviePosterViewModel {
    public String MovieID;
    public String MoviePosterUrl;

    public MoviePosterViewModel(String movieID, String moviePosterUrl) {
        MovieID = movieID;
        MoviePosterUrl = moviePosterUrl;
    }
}
