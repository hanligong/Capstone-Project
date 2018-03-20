package com.action.trip.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.action.trip.R;
import com.action.trip.contentProvider.TripContract;
import com.action.trip.util.SharePreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hanyuezi on 18/3/19.
 */
public class RecordActivity extends BaseActivity {
    @BindView(R.id.detailIconIv)
    ImageView mIconIv;
    @BindView(R.id.detailDescTv)
    EditText mDescTv;
    @BindView(R.id.detailLocationTv)
    TextView mLocationTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_record);

        setTitle("Record");

        ButterKnife.bind(this);

        SharePreferenceUtils.saveStringSharePreference(this, "");
        mIconIv.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(SharePreferenceUtils.getStringSharePreference(this))) {
            mLocationTv.setText(SharePreferenceUtils.getStringSharePreference(this));
        }
    }

    @OnClick(R.id.detailReleashBtn)
    public void clickReleashBtn(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TripContract.TripEntry.COLUMN_LOCATION, mLocationTv.getText().toString());
        contentValues.put(TripContract.TripEntry.COLUMN_ISSUE, mDescTv.getText().toString());
        getContentResolver().insert(TripContract.TripEntry.CONTENT_URI, contentValues);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = 0;
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
            case R.id.mapLocation:
                startActivity(new Intent(RecordActivity.this, MapsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
