package com.example.android.popularmovies.viewModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ian on 4/1/2017.
 */

public class MovieViewModel implements Parcelable {

    public static final String PARCELABLE_KEY = "MovieViewModel";

    public String MovieID;
    public String PosterURL;
    public String Title;
    public String ReleaseDate;
    public double VoteAverage;
    public String Overview;
    public boolean IsFavorite;

    public ReviewViewModel[] ReviewViewModels;
    public VideoViewModel[] VideoViewModels;

    public MovieViewModel(String movieID, String posterURL, String title, String releaseDate, double voteAverage, String overview) {
        MovieID = movieID;
        PosterURL = posterURL;
        Title = title;
        ReleaseDate = releaseDate;
        VoteAverage = voteAverage;
        Overview = overview;
    }

    public MovieViewModel(Parcel parcel) {
        MovieID = parcel.readString();
        PosterURL = parcel.readString();
        Title = parcel.readString();
        ReleaseDate = parcel.readString();
        VoteAverage = parcel.readDouble();
        Overview = parcel.readString();
        IsFavorite = parcel.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(MovieID);
        dest.writeString(PosterURL);
        dest.writeString(Title);
        dest.writeString(ReleaseDate);
        dest.writeDouble(VoteAverage);
        dest.writeString(Overview);
        dest.writeByte((byte)(IsFavorite ? 1 : 0));
    }

    static final Parcelable.Creator<MovieViewModel> CREATOR
            = new Parcelable.Creator<MovieViewModel>() {

        public MovieViewModel createFromParcel(Parcel in) {
            return new MovieViewModel(in);
        }

        public MovieViewModel[] newArray(int size) {
            return new MovieViewModel[size];
        }
    };
}
