package com.example.android.popularmovies;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Ian on 4/1/2017.
 */

public class NetworkUtilities {

    private static final String LOG_TAG = NetworkUtilities.class.getSimpleName();

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.d(LOG_TAG, "Opening connection");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            Log.d(LOG_TAG, "Getting input stream");
            InputStream in = urlConnection.getInputStream();

            Log.d(LOG_TAG, "Scanning");
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            Log.d(LOG_TAG, "Disconnecting");
            urlConnection.disconnect();
        }
    }
}
