package com.action.trip.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.action.trip.R;
import com.action.trip.inter.DataParseInterface;
import com.action.trip.model.HttpObject;
import com.action.trip.model.TripModel;
import com.action.trip.util.DataUtil;
import com.action.trip.util.LocalDataTask;
import com.bluelinelabs.logansquare.LoganSquare;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.recommendIv1)
    ImageView mRecommendIv1;
    @BindView(R.id.recommendTv1)
    TextView mRecommendTv1;
    private TripModel tripModel1;

    @BindView(R.id.recommendIv2)
    ImageView mRecommendIv2;
    @BindView(R.id.recommendTv2)
    TextView mRecommendTv2;
    private TripModel tripModel2;

    @BindView(R.id.mainGreatRv)
    RecyclerView mGreatRv;

    @BindView(R.id.inputLocation)
    EditText mInputLocationEt;
    @BindView(R.id.search)
    Button mSearchBtn;

    private TripAdapter mHotAdapter;
    private List<TripModel> hotList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showToken();

        ButterKnife.bind(this);

        initData();

        tmpList = new ArrayList<>();
        hotList = new ArrayList<>();

        mHotAdapter = new TripAdapter(hotList);
        mGreatRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mGreatRv.setLayoutManager(new GridLayoutManager(this, 2));
        mGreatRv.setItemAnimator(new DefaultItemAnimator());
        mGreatRv.setAdapter(mHotAdapter);
        initHotData();
    }

    @OnClick(R.id.recommendIv1)
    public void clickImage1(){
        Bundle bundle = new Bundle();
        bundle.putParcelable("tripModel", tripModel1);
        Intent intent = new Intent(MainActivity.this, TripDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        startActivity(intent);
    }

    @OnClick(R.id.recommendIv2)
    public void clickImage2(){
        Bundle bundle = new Bundle();
        bundle.putParcelable("tripModel", tripModel2);
        Intent intent = new Intent(MainActivity.this, TripDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        startActivity(intent);
    }

    @OnClick(R.id.mainRecordTv)
    public void clickRecord(){
        startActivity(new Intent(this, RecordActivity.class));
    }

    @OnClick(R.id.mainBbsTv)
    public void clickBbs(){
        startActivity(new Intent(this, BBSActivity.class));
    }

    @OnClick(R.id.search)
    public void clickSearchBtn(){
        if (TextUtils.isEmpty(mInputLocationEt.getText().toString().trim())) {
            return;
        }
        searchLocation(mInputLocationEt.getText().toString().trim());
    }

    @OnClick(R.id.mainMoreTv)
    public void clickMore(){
        startActivity(new Intent(this, OtherWayTripActivity.class));
    }

    @OnClick(R.id.otherType1)
    public void clickOtherType1(){
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        Intent intent = new Intent(this, OtherWayTripActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.otherType2)
    public void clickOtherType2(){
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        Intent intent = new Intent(this, OtherWayTripActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.otherType3)
    public void clickOtherType3(){
        Bundle bundle = new Bundle();
        bundle.putInt("type", 3);
        Intent intent = new Intent(this, OtherWayTripActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void searchLocation(String inputLocation){
        if (null == tmpList || tmpList.isEmpty()) {
            return;
        }
        for (int i = 0;i<tmpList.size();i++) {
            if (tmpList.get(i).getLocation().contains(inputLocation)) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("tripModel", tmpList.get(i));
                Intent intent = new Intent(this, TripDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return;
            }
        }
    }


    // 初始化数据
    private void initData() {
        new LocalDataTask(this, new DataParseInterface() {
            @Override
            public void updateData(List<TripModel> list) {
                if (list.size() < 2) {
                    return;
                }
                tripModel1 = list.get(0);
                Picasso.with(MainActivity.this).load(list.get(0).getImage()).into(mRecommendIv1);
                mRecommendTv1.setText(list.get(0).getLocation());

                tripModel2 = list.get(1);
                Picasso.with(MainActivity.this).load(list.get(1).getImage()).into(mRecommendIv2);
                mRecommendTv2.setText(list.get(1).getLocation());
            }
        }).execute("recommend_data.txt");
    }

    // 初始化数据
    private void initHotData() {
        new LocalDataTask(this, new DataParseInterface() {
            @Override
            public void updateData(List<TripModel> list) {
                tmpList.clear();
                tmpList = list;
                Collections.sort(hotList, new Comparator<TripModel>(){
                    public int compare(TripModel arg0, TripModel arg1) {
                        if(arg0.getDegree()>arg1.getDegree()){
                            return 1;
                        }else if(arg0.getDegree()<arg1.getDegree()){
                            return -1;
                        }else{
                            return 0;
                        }
                    }
                });
                hotList.clear();
                hotList.add(tmpList.get(0));
                hotList.add(tmpList.get(1));
                mHotAdapter.notifyDataSetChanged();
            }
        }).execute("data.txt");
    }

    private List<TripModel> tmpList;

    class TripAdapter extends RecyclerView.Adapter<ViewHolder>{

        public TripAdapter(List<TripModel> list){
            this.adapterList = list;
        }

        private List<TripModel> adapterList;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.activity_main_item, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Picasso.with(MainActivity.this).load(adapterList.get(position).getImage()).into(holder.mItemIconIv);
            holder.mItemDescTv.setText(adapterList.get(position).getLocation());

            holder.mItemIconIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("tripModel", adapterList.get(position));
                    Intent intent = new Intent(MainActivity.this, TripDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return adapterList.size();
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









    /**
     * 推送
     */
    private void showToken(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]

//        Button subscribeButton = findViewById(R.id.subscribeButton);
//        subscribeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // [START subscribe_topics]
//                FirebaseMessaging.getInstance().subscribeToTopic("news");
//                // [END subscribe_topics]
//
//                // Log and toast
//                String msg = getString(R.string.msg_subscribed);
//                Log.d(TAG, msg);
//                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Button logTokenButton = findViewById(R.id.logTokenButton);
//        logTokenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get token
//                String token = FirebaseInstanceId.getInstance().getToken();
//
//                // Log and toast
//                String msg = getString(R.string.msg_token_fmt, token);
//                Log.d(TAG, msg);
//                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
