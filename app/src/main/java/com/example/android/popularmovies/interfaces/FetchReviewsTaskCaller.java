package com.example.android.popularmovies.interfaces;

import com.example.android.popularmovies.viewModels.ReviewViewModel;

/**
 * Created by wian on 7/7/2017.
 */

public interface FetchReviewsTaskCaller {
    public void reviewsDataRequestInitiated();
    public void receiveReviewsData(ReviewViewModel[] reviewViewModels);
    public void errorLoadingReviewsData();
}
