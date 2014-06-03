package pt.telmo.mobiledatawidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class MobileDataWidgetProvider extends AppWidgetProvider
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);

        RemoteViews remoteViews = this.getRemoteViews(context);
        MobileData mobileData = MobileData.getInstance(context);
        MobileDataChangeButton mobileDataChangeButton = new MobileDataChangeButton(remoteViews, R.layout.mobiledatawidgetlayout, R.id.mobileDataButton);

        if(intent.getAction().equals("ACTION_MOBILE_DATA_CHANGE"))
        {
            boolean mobileDataState = mobileData.toggle();
            mobileDataChangeButton.changeState(mobileDataState);
            this.updateWidget(context, remoteViews);
        }
        else if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE"))
        {
            NetworkInfo networkInfo = intent.getExtras().getParcelable("networkInfo");
            if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                boolean mobileDataState = mobileData.getCurrentState();
                mobileDataChangeButton.changeState(mobileDataState);
                this.updateWidget(context, remoteViews);
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        int appWidgetID;
        RemoteViews remoteViews = this.getRemoteViews(context);

        for(int i=0; i<appWidgetIds.length; i++)
        {
            appWidgetID = appWidgetIds[i];

            Intent changeMobileDataStateIntent = new Intent(context, MobileDataWidgetProvider.class);
            changeMobileDataStateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetID);
            changeMobileDataStateIntent.setAction("ACTION_MOBILE_DATA_CHANGE");

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, changeMobileDataStateIntent, 0);

            remoteViews.setOnClickPendingIntent(R.id.mobileDataButton, pendingIntent);
        }

        new MobileDataChangeButton(remoteViews, R.layout.mobiledatawidgetlayout, R.id.mobileDataButton).changeState(false);

        boolean mobileDataState = MobileData.getInstance(context).getCurrentState();
        new MobileDataChangeButton(remoteViews, R.layout.mobiledatawidgetlayout, R.id.mobileDataButton).changeState(mobileDataState);

        this.updateWidget(appWidgetManager, appWidgetIds, remoteViews);
    }

    private void updateWidget(AppWidgetManager appWidgetManager, int[] appWidgetIds, RemoteViews remoteViews)
    {
        for(int i=0; i<appWidgetIds.length; i++)
        {
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
    }

    private void updateWidget(Context context, RemoteViews remoteViews)
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName appWidget = new ComponentName(context.getPackageName(), MobileDataWidgetProvider.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(appWidget);

        this.updateWidget(appWidgetManager, appWidgetIds, remoteViews);
    }

    private RemoteViews getRemoteViews(Context context)
    {
        return new RemoteViews(context.getPackageName(), R. layout.mobiledatawidgetlayout);
    }
}
