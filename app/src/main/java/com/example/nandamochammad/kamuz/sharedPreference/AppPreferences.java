package com.example.nandamochammad.kamuz.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.nandamochammad.kamuz.R;

public class AppPreferences {

    Context context;

    SharedPreferences prefs;

    public AppPreferences(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input){
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.app_first_run);
        editor.putBoolean(key, input);
        editor.commit();
    }

    public Boolean getFirstRun() {
        String key = context.getResources().getString(R.string.app_first_run);
        Boolean cek = prefs.getBoolean(key, true);
        Log.d("RSULT", "Get Boolean "+cek);
        return cek;


    }

}
