package com.action.trip.activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.action.trip.R;
import com.action.trip.contentProvider.TripContract;
import com.action.trip.model.BbsModel;
import com.action.trip.model.TripModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanyuezi on 18/3/19.
 */
public class BBSActivity extends BaseActivity {

    @BindView(R.id.mainBbsRv)
    RecyclerView mGreatRv;

    private BbsAdapter mAdapter;
    private List<BbsModel> list;

    private int selectPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bbs);

        setTitle("BBS");

        ButterKnife.bind(this);

        list = new ArrayList<>();
        mGreatRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mGreatRv.setLayoutManager(new GridLayoutManager(this, 1));
        mGreatRv.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new BbsAdapter();
        mGreatRv.setAdapter(mAdapter);

        Cursor cursor = getContentResolver().query(TripContract.TripEntry.CONTENT_URI, null, null, null, null);
        if (cursor == null || cursor.getCount() < 1) return;
        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            BbsModel bbsModel = new BbsModel();
            bbsModel.setIssue(cursor.getString(cursor.getColumnIndex(TripContract.TripEntry.COLUMN_ISSUE)));
            bbsModel.setLocation(cursor.getString(cursor.getColumnIndex(TripContract.TripEntry.COLUMN_LOCATION)));
            list.add(bbsModel);
        }

        cursor.close();
        mAdapter.notifyDataSetChanged();

        if (null != getIntent().getExtras()) {
            selectPosition = getIntent().getExtras().getInt("position");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = 0;
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class BbsAdapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.activity_bbs_item, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (position == selectPosition) {
                holder.mLl.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            } else {
                holder.mLl.setBackgroundColor(getResources().getColor(R.color.white));
            }
            holder.mLocationTv.setText(list.get(position).getLocation());
            holder.mIssueIv.setText(list.get(position).getIssue());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.bbsItemLl)
        LinearLayout mLl;
        @BindView(R.id.bbsItemLocationTv)
        TextView mLocationTv;
        @BindView(R.id.bbsItemIssueTv)
        TextView mIssueIv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
