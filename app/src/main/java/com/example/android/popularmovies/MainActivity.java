package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.popularmovies.adapters.MovieAdapter;
import com.example.android.popularmovies.interfaces.FetchMovieTaskCaller;
import com.example.android.popularmovies.tasks.FetchMoviesTask;
import com.example.android.popularmovies.utilities.MovieDBUtilities;
import com.example.android.popularmovies.viewModels.MovieViewModel;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, AdapterView.OnItemSelectedListener, FetchMovieTaskCaller {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingProgressBar;
    private Spinner mSortTypeSpinner;

    private String mMovieSortType;
    private MovieViewModel[] mMovieViewModels = null;

    static final String STATE_SORT_TYPE = "sort_type";
    static final String STATE_MOVIE_POSTER_VIEWMODELS = "movie_poster_viewmodels";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Store views in variables
        Log.d(LOG_TAG, "Getting views");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);


        //Set up layout manager
        Log.d(LOG_TAG, "Constructing layout manager");
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        Log.d(LOG_TAG, "Requesting layout");
        layoutManager.requestLayout();
        Log.d(LOG_TAG, "Setting layout manager");
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setHasFixedSize(true); //Not super sure about this one

        //Set up the recycler view
        Log.d(LOG_TAG, "Initializing MovieAdapter");
        mMovieAdapter = new MovieAdapter(this);
        Log.d(LOG_TAG, "Setting MovieAdapter");
        mRecyclerView.setAdapter(mMovieAdapter);

        //Load movie data
        if(savedInstanceState != null) {
            Log.d(LOG_TAG, "Recovering from previous state");
            mMovieSortType = savedInstanceState.getString(STATE_SORT_TYPE);
            mMovieViewModels = (MovieViewModel[]) savedInstanceState.getParcelableArray(STATE_MOVIE_POSTER_VIEWMODELS);
            mMovieAdapter.setMovieData(mMovieViewModels);
        } else {
            Log.d(LOG_TAG, "Loading fresh movie data");
            mMovieSortType = getString(R.string.sort_option_popular);
            loadMovieData();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Log.d(LOG_TAG, "Putting movie request type in bundle");
        savedInstanceState.putString(STATE_SORT_TYPE, mMovieSortType);
        Log.d(LOG_TAG, "Putting movie poster view models in bundle");
        savedInstanceState.putParcelableArray(STATE_MOVIE_POSTER_VIEWMODELS, mMovieViewModels);
    }

    private void loadMovieData() {
        showMovieDataView();

        if(mMovieSortType == getString(R.string.sort_option_popular)) {
            new FetchMoviesTask(this).execute(MovieDBUtilities.MOVIE_DB_POPULAR_SERVICE);
        } else if(mMovieSortType == getString(R.string.sort_option_top_rated)) {
            new FetchMoviesTask(this).execute(MovieDBUtilities.MOVIE_DB_TOP_RATED_SERVICE);
        } else if(mMovieSortType == getString(R.string.sort_option_favorites)) {
            //Do nothing
        }
    }

    public void onClick(MovieViewModel singleMovieViewModel) {
        Context context = this;
        Class destinationClass = MovieDetail.class;
        Intent intentToStartMovieDetailActivity = new Intent(context, destinationClass);
        intentToStartMovieDetailActivity.putExtra(MovieViewModel.PARCELABLE_KEY, singleMovieViewModel);
        startActivity(intentToStartMovieDetailActivity);
    }

    private void showMovieDataView() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessageTextView() {
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void showLoadingIndicator() {
        mLoadingProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
    }

    //Interface methods now that FetchMoviesTask is in another thread
    public void movieDataRequestInitiated() {
        showLoadingIndicator();
    }

    public void receiveMovieData(MovieViewModel[] movieViewModels) {
        hideLoadingIndicator();
        showMovieDataView();
        mMovieViewModels = movieViewModels;
        mMovieAdapter.setMovieData(mMovieViewModels);
    }

    public void errorLoadingMovieData() {
        hideLoadingIndicator();
        showErrorMessageTextView();
    }

    //Menu methods
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_list_menu, menu);

        MenuItem spinnerMenuItem = menu.findItem(R.id.sp_menu_sort);
        mSortTypeSpinner = (Spinner) MenuItemCompat.getActionView(spinnerMenuItem);

        //Set up menu spinner
        Log.d(LOG_TAG, "Creating sortMenuAdapter");
        ArrayAdapter<CharSequence> sortMenuAdapter = ArrayAdapter.createFromResource(this, R.array.sort_options_array, android.R.layout.simple_spinner_item);
        Log.d(LOG_TAG, "Setting dropwdown resource");
        sortMenuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.d(LOG_TAG, "Setting sortMenuAdapter");
        mSortTypeSpinner.setAdapter(sortMenuAdapter);
        Log.d(LOG_TAG, "Setting spinner position");
        mSortTypeSpinner.setSelection(sortMenuAdapter.getPosition(mMovieSortType), false);
        Log.d(LOG_TAG, "Setting on selected item changed listener for spinner");
        mSortTypeSpinner.setOnItemSelectedListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();

        if(menuItemId == R.id.action_refresh) {
            mMovieAdapter.setMovieData(null);
            loadMovieData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Spinner methods

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        mMovieSortType = selectedItem;

        Log.d(LOG_TAG, "Selected spinner item " + selectedItem);

        loadMovieData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}