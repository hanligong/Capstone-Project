package com.action.trip.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.action.trip.R;
import com.action.trip.inter.DataParseInterface;
import com.action.trip.model.HttpObject;
import com.action.trip.model.TripModel;
import com.action.trip.util.DataUtil;
import com.action.trip.util.LocalDataTask;
import com.bluelinelabs.logansquare.LoganSquare;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hanyuezi on 18/3/20.
 */

public class OtherWayTripActivity extends BaseActivity{

    @BindView(R.id.otherRv)
    RecyclerView mOtherRv;

    private int type = 1;

    private TypeTripAdapter mAdapter;
    private List<TripModel> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_trip);

        setTitle("Location");
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        if (null != bundle) {
            type = bundle.getInt("type");
        }

        tmpList = new ArrayList<>();
        mList = new ArrayList<>();

        mAdapter = new TypeTripAdapter();
        mOtherRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mOtherRv.setLayoutManager(new GridLayoutManager(this, 1));
        mOtherRv.setItemAnimator(new DefaultItemAnimator());
        mOtherRv.setAdapter(mAdapter);
        initData();
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

    private void initData() {
        new LocalDataTask(this, new DataParseInterface() {
            @Override
            public void updateData(List<TripModel> list) {
                tmpList.clear();
                tmpList = list;
                mList.clear();
                if (type == 1 || type == 2) {
                    mList.addAll(searchRelativeLotion(type, tmpList));
                } else {
                    mList.addAll(tmpList);
                }
                mAdapter.notifyDataSetChanged();
            }
        }).execute("data.txt");
    }

    private List<TripModel> tmpList;

    private List<TripModel> searchRelativeLotion(int type, List<TripModel> tmpList){
        List<TripModel> finalList = new ArrayList<>();
        for (int i = 0;i<tmpList.size();i++) {
            if (type == tmpList.get(i).getType()) {
                finalList.add(tmpList.get(i));
            }
        }
        return finalList;
    }

    @OnClick(R.id.otherType0)
    public void clickDegree(){
        if (null == tmpList || tmpList.isEmpty()) {
            return;
        }
        mList.clear();
        type = 3;
        Collections.sort(tmpList, new Comparator<TripModel>() {
            @Override
            public int compare(TripModel tripModel, TripModel t1) {
                if (tripModel.getDegree() > t1.getDegree()) {
                    return 1;
                } else if (tripModel.getDegree() < t1.getDegree()){
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        mList.addAll(tmpList);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.otherType1)
    public void clickType1(){
        if (null == tmpList || tmpList.isEmpty()) {
            return;
        }
        mList.clear();
        type = 1;
        mList.addAll(searchRelativeLotion(1, tmpList));
        mAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.otherType2)
    public void clickType2(){
        if (null == tmpList || tmpList.isEmpty()) {
            return;
        }
        mList.clear();
        type = 2;
        mList.addAll(searchRelativeLotion(2, tmpList));
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.otherType3)
    public void clickType3(){
        if (null == tmpList || tmpList.isEmpty()) {
            return;
        }
        mList.clear();
        type = 3;
        mList.addAll(tmpList);
        mAdapter.notifyDataSetChanged();
    }


    class TypeTripAdapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.activity_main_item, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Picasso.with(OtherWayTripActivity.this).load(mList.get(position).getImage()).into(holder.mItemIconIv);
            holder.mItemDescTv.setText(mList.get(position).getLocation());

            holder.mItemIconIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("tripModel", mList.get(position));
                    Intent intent = new Intent(OtherWayTripActivity.this, TripDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.mainItemDescTv)
        TextView mItemDescTv;
        @BindView(R.id.mainItemIconIv)
        ImageView mItemIconIv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
