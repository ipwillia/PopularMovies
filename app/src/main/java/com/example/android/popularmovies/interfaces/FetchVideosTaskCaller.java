package com.example.android.popularmovies.interfaces;

import com.example.android.popularmovies.viewModels.VideoViewModel;

/**
 * Created by wian on 7/7/2017.
 */

public interface FetchVideosTaskCaller {
    public void videosDataRequestInitiated();
    public void receiveVideosData(VideoViewModel[] videoViewModels);
    public void errorLoadingVideosData();
}
