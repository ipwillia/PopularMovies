package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ian on 4/1/2017.
 */

public class MoviePosterViewModel implements Parcelable {
    public String MovieID;
    public String MoviePosterUrl;

    public MoviePosterViewModel(String movieID, String moviePosterUrl) {
        MovieID = movieID;
        MoviePosterUrl = moviePosterUrl;
    }

    public MoviePosterViewModel(Parcel parcel) {
        MovieID = parcel.readString();
        MoviePosterUrl = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(MovieID);
        dest.writeString(MoviePosterUrl);
    }

    static final Parcelable.Creator<MoviePosterViewModel> CREATOR
            = new Parcelable.Creator<MoviePosterViewModel>() {

        public MoviePosterViewModel createFromParcel(Parcel in) {
            return new MoviePosterViewModel(in);
        }

        public MoviePosterViewModel[] newArray(int size) {
            return new MoviePosterViewModel[size];
        }
    };
}
