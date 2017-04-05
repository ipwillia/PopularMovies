package com.example.android.popularmovies.interfaces;

import com.example.android.popularmovies.viewModels.MovieViewModel;

/**
 * Created by Ian on 4/4/2017.
 */

public interface FetchMovieTaskCaller {
    public void movieDataRequestInitiated();
    public void receiveMovieData(MovieViewModel[] movieViewModels);
    public void errorLoadingMovieData();
}
