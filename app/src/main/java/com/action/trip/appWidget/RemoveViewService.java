package com.action.trip.appWidget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.action.trip.R;
import com.action.trip.contentProvider.TripContract;
import com.action.trip.model.BbsModel;
import java.util.ArrayList;

public class RemoveViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteviewFactoryImp(this, intent);
    }

    private static ArrayList<BbsModel> data;


    class RemoteviewFactoryImp implements RemoteViewsFactory {

        private Intent requestIntent;
        private Context requestContext;


        public RemoteviewFactoryImp(Context context, Intent intent) {
            requestContext = context;
            requestIntent = intent;
        }

        @Override
        public void onCreate() {
            data = new ArrayList<>();


            Cursor cursor = getContentResolver().query(TripContract.TripEntry.CONTENT_URI, null, null, null, null);
            if (cursor == null || cursor.getCount() < 1) return;
            cursor.moveToFirst();

            while (cursor.moveToNext()) {
                BbsModel bbsModel = new BbsModel();
                bbsModel.setIssue(cursor.getString(cursor.getColumnIndex(TripContract.TripEntry.COLUMN_ISSUE)));
                bbsModel.setLocation(cursor.getString(cursor.getColumnIndex(TripContract.TripEntry.COLUMN_LOCATION)));
                data.add(bbsModel);
            }
            cursor.close();
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(requestContext.getPackageName(), R.layout.service_removeview_item);
            remoteViews.setTextViewText(R.id.removeViewItemTv, data.get(position).getLocation());

            //listview的点击事件
            Intent intent = new Intent();
            intent.putExtra("position", position);
            remoteViews.setOnClickFillInIntent(R.id.removeViewItemTv, intent);

            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
