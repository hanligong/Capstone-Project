package com.action.trip.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.action.trip.R;
import com.action.trip.model.TripModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanyuezi on 18/3/19.
 */

public class TripDetailActivity extends BaseActivity{

    private TripModel tripModel;

    @BindView(R.id.detailIconIv)
    ImageView mIconIv;

    @BindView(R.id.detailDescTv)
    TextView mDescTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);

        setTitle("Trip Title");

        ButterKnife.bind(this);

        tripModel = getIntent().getExtras().getParcelable("tripModel");

        Picasso.with(this).load(tripModel.getImage()).into(mIconIv);
        mDescTv.setText(tripModel.getDesc());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = 0;
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
            case R.id.share:
                shareText(tripModel.getLocation(), tripModel.getDesc());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 分享文字内容
     *
     * @param dlgTitle
     *            分享对话框标题
     * @param content
     *            分享内容（文字）
     */
    private void shareText(String dlgTitle, String content) {
        if (content == null || "".equals(content)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);

        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题  
            startActivity(intent);
        }
    }
}
