package pt.telmo.mobiledatawidget;

import android.widget.RemoteViews;

public class MobileDataChangeButton extends RemoteImageButton
{
    protected MobileDataChangeButton(RemoteViews remoteViews, int layoutID, int viewID)
    {
        super(remoteViews, layoutID, viewID);
    }

    public void changeState(boolean state)
    {
        int backgroundResourceID = R.drawable.buttonbackground;
        int imageResourceID = R.drawable.ic_action_network_cell_hollow;
        if(state)
        {
            backgroundResourceID = R.drawable.buttonbackgroundactive;
            imageResourceID = R.drawable.ic_action_network_cell;
        }

        this.setBackgroundResource(backgroundResourceID);
        this.setImageResource(imageResourceID);
    }
}
