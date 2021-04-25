package com.exam.resultprocess;

import android.content.*;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void set(String key,String value) {
        prefs.edit().putString(key, value).commit();
    }

    public String get(String key) {
        String value = prefs.getString(key,"");
        return value;
    }

    public void clear(){
        prefs.edit().clear();
    }
}

