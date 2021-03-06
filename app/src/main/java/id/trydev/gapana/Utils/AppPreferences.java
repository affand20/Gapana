package id.trydev.gapana.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    public static final String PREFS_NAME = "app_pref";

    private static final String FIRST_RUN = "first_run";
    private static final String LAST_LATITUDE = "last_latitude";
    private static final String LAST_LONGITUDE = "last_longitude";

    private static final String TOGLE_NOTIF = "togle_notif";
    private static final String TOGLE_NOTIF2 = "togle_notif2";

    private final SharedPreferences prefs;

    public AppPreferences(Context context){
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setFirstRun(int first_run){
        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(FIRST_RUN, token);
        editor.putInt(FIRST_RUN, first_run);
        editor.apply();
    }

    public int getFirstRun(){
        return prefs.getInt(FIRST_RUN,0);
    }

    //------------------------------------------------------------------------
    public void setTogleNotif(int togle_notif){
        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(FIRST_RUN, token);
        editor.putInt(TOGLE_NOTIF, togle_notif);
        editor.apply();
    }

    public int getTogleNotif(){
        return prefs.getInt(TOGLE_NOTIF,0);
    }

    public void setTogleNotif2(int togle_notif){
        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(FIRST_RUN, token);
        editor.putInt(TOGLE_NOTIF2, togle_notif);
        editor.apply();
    }

    public int getTogleNotif2(){
        return prefs.getInt(TOGLE_NOTIF2,0);
    }

    //-----------------------------------------------------------

    public void setLastLatitude(String latitude){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LAST_LATITUDE, latitude);
        editor.apply();
    }

    public Double getLastLatitude(){
        return Double.parseDouble(prefs.getString(LAST_LATITUDE, null));
    }

    public void setLastLongitude(String longitude){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LAST_LONGITUDE, longitude);
        editor.apply();
    }

    public Double getLastLongitude(){
        return Double.parseDouble(prefs.getString(LAST_LONGITUDE, null));
    }

    public void resetPreference(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }


}