package com.action.trip.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by hanyuezi on 18/3/19.
 */

public class BaseActivity extends AppCompatActivity {

    public void setTitle(String title){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
    }
}
