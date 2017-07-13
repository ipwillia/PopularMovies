package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.viewModels.ReviewViewModel;
import com.example.android.popularmovies.viewModels.ReviewViewModel;

/**
 * Created by wian on 7/13/2017.
 */

public class ReviewListAdapter extends BaseAdapter {
    Context context;
    ReviewViewModel[] mReviewViewModels;
    private static LayoutInflater layoutInflater = null;

    public ReviewListAdapter(Context context, ReviewViewModel[] reviewViewModels) {
        this.context = context;
        this.mReviewViewModels = reviewViewModels;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mReviewViewModels.length;
    }

    @Override
    public Object getItem(int position) {
        return mReviewViewModels[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View reviewView = convertView;
        if (reviewView == null) {
            reviewView = layoutInflater.inflate(R.layout.review_list_item, null);
        }

        TextView reviewAuthor = (TextView) reviewView.findViewById(R.id.tv_review_author);
        reviewAuthor.setText(mReviewViewModels[position].Author);

        TextView reviewContent = (TextView) reviewView.findViewById(R.id.tv_review_content);
        reviewContent.setText(mReviewViewModels[position].Content);

        return reviewView;
    }
}
