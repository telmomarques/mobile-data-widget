package pt.telmo.mobiledatawidget;

import android.content.Context;
import android.net.ConnectivityManager;
import java.lang.reflect.Method;

//TODO: Report exceptions so the developer can fix them
public class MobileData
{
    private static MobileData instance;
    private ConnectivityManager connectivityManager;
    private Method setMobileDataEnabledMethod;
    private Method getMobileDataEnabled;

    private MobileData(Context context)
    {
        try
        {
            this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Class connectivityManagerClass = Class.forName(this.connectivityManager.getClass().getName());

            this.getMobileDataEnabled = connectivityManagerClass.getDeclaredMethod("getMobileDataEnabled");
            this.getMobileDataEnabled.setAccessible(true);
            this.setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            this.setMobileDataEnabledMethod.setAccessible(true);
        }
        catch(Exception ex)
        {
            //Pokemon exception handling: gotta catch 'em all
        }
    }

    public boolean toggle()
    {
        boolean currentState = this.getCurrentState();
        try
        {
            this.setMobileDataEnabledMethod.invoke(this.connectivityManager, !currentState);
            currentState = !currentState;
        }
        catch(Exception ex)
        {}

        return currentState;
    }

    public boolean getCurrentState()
    {
        boolean state = false;
        try
        {
            state = (Boolean)this.getMobileDataEnabled.invoke(this.connectivityManager);
        }
        catch(Exception ex)
        {}

        return state;
    }

    public static MobileData getInstance(Context context)
    {
        if(MobileData.instance == null)
        {
            MobileData.instance = new MobileData(context);
        }

        return MobileData.instance;
    }
}
