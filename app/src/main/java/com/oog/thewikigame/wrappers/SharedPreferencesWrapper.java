package com.oog.thewikigame.wrappers;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.Objects;


//TODO: Add documentation.
public class SharedPreferencesWrapper {

    private static final String SETTINGS = "SETTINGS";
    private static final String GAME = "GAME";
    private static final String USER ="USER";

    private final Context context;
    private final Preference preference;


    public enum Preference {
        SETTINGS,
        GAME,
        USER,
    }

    public SharedPreferencesWrapper(Context context, Preference preference) {
        this.context= context;
        this.preference = preference;
    }

    private static SharedPreferences getSharedPreference(Context context,Preference preference){
        switch (preference) {
            case SETTINGS:
                return context.getSharedPreferences(SETTINGS,Context.MODE_PRIVATE);
            case USER:
                return context.getSharedPreferences(USER,Context.MODE_PRIVATE);
            case GAME:
                return context.getSharedPreferences(GAME,Context.MODE_PRIVATE);
        }
        return null;
    }

    public String getString(String key, String defValue) {
        return getString(context,preference,key,defValue);
    }

    public void putString(String key, String value) {
        putString(context,preference,key,value);
    }

    public static String getString(Context context,Preference preference,String key, String defValue){
        return Objects.requireNonNull(getSharedPreference(context, preference)).getString(key,defValue);
    }

    public static void putString(Context context,Preference preference,String key,String value){
        Objects.requireNonNull(getSharedPreference(context, preference)).edit().putString(key,value).apply();
    }
}
