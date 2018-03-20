package com.action.trip.util;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.action.trip.inter.DataParseInterface;
import com.action.trip.model.HttpObject;
import com.action.trip.model.TripModel;
import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;
import java.util.List;

/**
 * Created by hanyuezi on 18/3/20.
 */

public class LocalDataTask extends AsyncTask<String, Void, List<TripModel>>{
    private Context context;
    private DataParseInterface dataParseInterface;

    public LocalDataTask(Context context, DataParseInterface dataParseInterface){
        this.context = context;
        this.dataParseInterface = dataParseInterface;
    }

    @Override
    protected List<TripModel> doInBackground(String... strings) {
        String data = DataUtil.getAssetsData(context, strings[0]);
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        HttpObject httpObject = null;
        try {
            httpObject = LoganSquare.parse(data, HttpObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return httpObject.getList();
    }

    @Override
    protected void onPostExecute(List<TripModel> list) {
        super.onPostExecute(list);
        if (null == list || list.isEmpty()) {
            return;
        }
        dataParseInterface.updateData(list);
    }

}
