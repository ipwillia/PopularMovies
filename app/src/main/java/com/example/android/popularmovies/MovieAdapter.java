package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.utilities.MovieDBUtilities;
import com.example.android.popularmovies.viewModels.MovieViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private MovieViewModel[] mMovieViewModels = new MovieViewModel[0];

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(MovieViewModel singleMovieViewModel);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder
        implements OnClickListener {

        @BindView(R.id.iv_movie_data) ImageView moviePosterImageView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieViewModel singleMovieViewModel = mMovieViewModels[adapterPosition];
            mClickHandler.onClick(singleMovieViewModel);
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
        MovieViewModel singleMovieViewModel = mMovieViewModels[position];

        String posterURL = singleMovieViewModel.PosterURL;
        Log.d(LOG_TAG, posterURL);

        Picasso.with(context)
                .load(posterURL)
                .placeholder(R.drawable.movieposter_loading)
                .error(R.drawable.movieposter_failed)
                .into(movieAdapterViewHolder.moviePosterImageView);
    }

    public int getItemCount() {
        int itemCount = 0;
        if (mMovieViewModels != null) {
            itemCount = mMovieViewModels.length;
        }
        return itemCount;
    }

    public void setMovieData(MovieViewModel[] movieViewModels) {
        mMovieViewModels = movieViewModels;
        notifyDataSetChanged();
    }
}
