package com.example.android.popularmovies.utilities;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by wian on 7/13/2017.
 */

public class ListViewUtilities {

    /*
    Taken from this StackOverflow post on 7/13/17:
    https://stackoverflow.com/questions/4338185/how-to-get-a-non-scrollable-listview
     */
    public static void JustifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount()));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}
