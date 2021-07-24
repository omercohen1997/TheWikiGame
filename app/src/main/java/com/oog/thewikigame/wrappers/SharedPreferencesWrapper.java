package com.oog.thewikigame.wrappers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;

import java.io.Serializable;
import java.util.Objects;


//TODO: Add documentation.
public class SharedPreferencesWrapper {

    private static final String SETTINGS = "SETTINGS";
    private static final String GAME = "GAME";
    private static final String USER = "USER";

    private final Context context;
    private final Preference preference;


    public enum Preference {
        SETTINGS("SETTINGS"),
        GAME("GAME"),
        USER("USER"),
        ;

        public final String tag;

        Preference(String tag) {
            this.tag = tag;
        }
    }

    public static SharedPreferencesWrapper make(Context context, Preference preference) {
        return new SharedPreferencesWrapper(context, preference);
    }

    private SharedPreferencesWrapper(Context context, Preference preference) {
        this.context = context;
        this.preference = preference;
    }

    private SharedPreferences getSharedPreference() {
        return context.getSharedPreferences(preference.tag, Context.MODE_PRIVATE);
    }

    public String getString(String key, String defValue) {
        return Objects.requireNonNull(getSharedPreference()).getString(key, defValue);
    }

    public void putString(String key, String value) {
        Objects.requireNonNull(getSharedPreference()).edit().putString(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return Objects.requireNonNull(getSharedPreference()).getInt(key, defValue);
    }

    public void putInt(String key, int value) {
        Objects.requireNonNull(getSharedPreference()).edit().putInt(key, value).apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clearAllPreferences() {
        context.deleteSharedPreferences(preference.tag);
    }

}
