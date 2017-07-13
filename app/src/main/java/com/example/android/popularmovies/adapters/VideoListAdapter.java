package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.viewModels.VideoViewModel;

/**
 * Created by wian on 7/13/2017.
 */

public class VideoListAdapter extends BaseAdapter {
    Context context;
    VideoViewModel[] mVideoViewModels;
    private static LayoutInflater layoutInflater = null;

    public VideoListAdapter(Context context, VideoViewModel[] videoViewModels) {
        this.context = context;
        this.mVideoViewModels = videoViewModels;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mVideoViewModels.length;
    }

    @Override
    public Object getItem(int position) {
        return mVideoViewModels[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View videoView = convertView;
        if (videoView == null) {
            videoView = layoutInflater.inflate(R.layout.video_list_item, null);
        }

        TextView videoTitle = (TextView) videoView.findViewById(R.id.tv_trailer_title);
        videoTitle.setText(mVideoViewModels[position].Name);

        return videoView;
    }
}
