package com.example.android.popularmovies.viewModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ian on 4/2/2017.
 */

public class VideoViewModel implements Parcelable {
    public String Name;
    public String Key;
    public String Site;

    public VideoViewModel(String name, String key, String site) {
        Name = name;
        Key = key;
        Site = site;
    }

    public VideoViewModel(Parcel parcel) {
        Name = parcel.readString();
        Key = parcel.readString();
        Site = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Key);
        dest.writeString(Site);
    }

    static final Parcelable.Creator<VideoViewModel> CREATOR
            = new Parcelable.Creator<VideoViewModel>() {

        public VideoViewModel createFromParcel(Parcel in) {
            return new VideoViewModel(in);
        }

        public VideoViewModel[] newArray(int size) {
            return new VideoViewModel[size];
        }
    };
}
