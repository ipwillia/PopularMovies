package com.example.android.popularmovies.viewModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ian on 4/2/2017.
 */

public class ReviewViewModel implements Parcelable {
    public String Author;
    public String Content;
    public String URL;

    public ReviewViewModel(String author, String content, String url) {
        Author = author;
        Content = content;
        URL = url;
    }

    public ReviewViewModel(Parcel parcel) {
        Author = parcel.readString();
        Content = parcel.readString();
        URL = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Author);
        dest.writeString(Content);
        dest.writeString(URL);
    }

    static final Parcelable.Creator<ReviewViewModel> CREATOR
            = new Parcelable.Creator<ReviewViewModel>() {

        public ReviewViewModel createFromParcel(Parcel in) {
            return new ReviewViewModel(in);
        }

        public ReviewViewModel[] newArray(int size) {
            return new ReviewViewModel[size];
        }
    };
}
