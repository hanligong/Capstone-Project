package com.action.trip.contentProvider;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hanyuezi on 18/3/20.
 */

public class SqlDbHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "trip.db";
    private static final int DB_VERSION = 1;

    public SqlDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TripContract.TripEntry.TABLE_NAME + " (" +
                TripContract.TripEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TripContract.TripEntry.COLUMN_LOCATION + " TEXT," +
                TripContract.TripEntry.COLUMN_ISSUE + " TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TripContract.TripEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
