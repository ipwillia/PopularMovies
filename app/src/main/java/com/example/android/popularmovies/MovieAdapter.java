package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private MoviePosterViewModel[] mMoviePosterViewModels = new MoviePosterViewModel[0];

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(MoviePosterViewModel singleMoviePosterViewModel);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder
        implements OnClickListener {

        ImageView moviePosterImageView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            moviePosterImageView = (ImageView) itemView.findViewById(R.id.iv_movie_data);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MoviePosterViewModel singleMoviePosterViewModel = mMoviePosterViewModels[adapterPosition];
            mClickHandler.onClick(singleMoviePosterViewModel);
        }
    }

    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int listItemLayoutID = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(listItemLayoutID, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Context context = movieAdapterViewHolder.itemView.getContext();
        MoviePosterViewModel singleMoviePosterViewModel = mMoviePosterViewModels[position];
        Picasso.with(context).load(singleMoviePosterViewModel.MoviePosterUrl).into(movieAdapterViewHolder.moviePosterImageView);
    }

    public int getItemCount() {
        int itemCount = 0;
        if (mMoviePosterViewModels != null) {
            itemCount = mMoviePosterViewModels.length;
        }
        return itemCount;
    }

    public void setMovieData(MoviePosterViewModel[] moviePosterViewModels) {
        mMoviePosterViewModels = moviePosterViewModels;
        notifyDataSetChanged();
    }
}
