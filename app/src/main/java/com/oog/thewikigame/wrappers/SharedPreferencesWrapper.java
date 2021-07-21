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

    public static SharedPreferencesWrapper make(Context context,Preference preference){
        return new SharedPreferencesWrapper(context,preference);
    }

    private SharedPreferencesWrapper(Context context, Preference preference) {
        this.context= context;
        this.preference = preference;
    }

    private SharedPreferences getSharedPreference(){
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
        return Objects.requireNonNull(getSharedPreference()).getString(key,defValue);
    }

    public void putString(String key, String value) {
        Objects.requireNonNull(getSharedPreference()).edit().putString(key,value).apply();
    }

    public long getLong(String key,long defValue){
        return Objects.requireNonNull(getSharedPreference()).getLong(key,defValue);
    }

    public void putLong(String key,long value){
        Objects.requireNonNull(getSharedPreference()).edit().putLong(key,value).apply();
    }


    public long getInt(String key,int defValue){
        return Objects.requireNonNull(getSharedPreference()).getInt(key,defValue);
    }

    public void putInt(String key,int value){
        Objects.requireNonNull(getSharedPreference()).edit().putInt(key,value).apply();
    }


}
