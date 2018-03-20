package com.action.trip.appWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.action.trip.R;
import com.action.trip.activity.BBSActivity;

/**
 * Implementation of App Widget functionality.
 */
public class TripAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.trip_app_widget);

        Intent removeViewIntent = new Intent(context, RemoveViewService.class);
        views.setRemoteAdapter(R.id.appWidgetLv, removeViewIntent);

        Intent intent = new Intent(context, BBSActivity.class);
        PendingIntent removeViewPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setPendingIntentTemplate(R.id.appWidgetLv, removeViewPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

