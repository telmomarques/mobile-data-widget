package pt.telmo.mobiledatawidget;

import android.widget.RemoteViews;

public abstract class RemoteImageButton
{
    RemoteViews remoteViews;
    int layoutID;
    int viewID;

    protected RemoteImageButton(RemoteViews remoteViews, int layoutID, int viewID)
    {
        this.remoteViews = remoteViews;
        this.layoutID = layoutID;
        this.viewID = viewID;
    }

    protected void setBackgroundResource(int resourceID)
    {
        this.remoteViews.setInt(this.viewID, "setBackgroundResource", resourceID);
    }

    protected void setImageResource(int resourceID)
    {
        this.remoteViews.setInt(this.viewID, "setImageResource", resourceID);
    }
}
