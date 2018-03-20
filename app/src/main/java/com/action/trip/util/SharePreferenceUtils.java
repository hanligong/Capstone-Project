package com.action.trip.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hanyuezi on 17/11/24.
 */

public class SharePreferenceUtils {

    public static void saveStringSharePreference(Activity activity, String location){
        SharedPreferences sp = activity.getSharedPreferences("trip", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("location", location);
        editor.commit();
    }

    public static String getStringSharePreference(Activity activity){
        SharedPreferences sp = activity.getSharedPreferences("trip", Context.MODE_PRIVATE);
        return sp.getString("location", "");
    }

}
