package id.trydev.gapana.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    public static final String PREFS_NAME = "app_pref";

    private static final String FIRST_RUN = "first_run";

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

    public void resetPreference(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }


}