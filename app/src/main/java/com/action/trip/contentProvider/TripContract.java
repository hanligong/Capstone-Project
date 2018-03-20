package com.action.trip.contentProvider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.bluelinelabs.logansquare.annotation.JsonField;

/**
 * Created by hanyuezi on 18/3/20.
 */

public class TripContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.action.trip";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "plants" directory
    public static final String PATH_TRIPS = "trip";

    public static final class TripEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRIPS).build();

        public static final String TABLE_NAME = "trip";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_ISSUE = "desc";
    }
}
