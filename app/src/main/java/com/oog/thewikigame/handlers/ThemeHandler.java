package com.oog.thewikigame.handlers;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;
import com.oog.thewikigame.wrappers.SharedPreferencesWrapper;

/**
 * This class will manage the theme of the app.
 * It can set and get the theme configuration in realtime.
 */
public class ThemeHandler {

    private static final String LIGHT = "LIGHT";
    private static final String DARK = "DARK";
    private static final String DEFAULT = "DEFAULT";
    private static final String THEME_KEY = "theme";

    private final Context context;

    public enum Theme {
        LIGHT,
        DARK,
        DEFAULT
    }


    /**
     * This will create a {@link ThemeHandler} instance with the given context.
     * @param context The context of the calling activity.
     */
    public ThemeHandler(Context context){
        this.context = context;
    }

    /**
     * This method will update the theme of the app to the saved theme in the preferences.
     */
    public void updateSystemToSavedTheme(){
        updateSystemTheme(context,getSavedTheme(context));
    }

    /**
     * This method will update the theme of the app and save it to the shared preferences
     * @param theme The theme to set the app to.
     */
    public void updateSystemTheme(Theme theme){
        updateSystemTheme(context,theme);
    }

    /**
     * This method will get the saved theme from the preferences.
     * @return the theme in the preferences.
     */
    public Theme getSavedTheme(){
        return getSavedTheme(context);
    }


    /*Static Methods*/


    /**
     * This method will update the theme of the app to the saved theme in the preferences.
     * @param context The context of the calling activity.
     */
    public static void updateSystemToSavedTheme(Context context) {
        updateSystemTheme(context,getSavedTheme(context));
    }

    /**
     * This method will update the theme of the app and save it to the shared preferences
     * @param context The context of the calling activity.
     * @param theme The theme to set the app to.
     */
    public static void updateSystemTheme(Context context, Theme theme) {
        String themeString;
        switch (theme) {
            case LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                themeString = LIGHT;
                break;
            case DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                themeString = DARK;
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                themeString = DEFAULT;
                break;
        }
        SharedPreferencesWrapper.putString(context, SharedPreferencesWrapper.Preference.SETTINGS,THEME_KEY,themeString);
    }

    /**
     * This method will get the saved theme from the preferences.
     * @param context The context of the calling activity.
     * @return the theme in the preferences.
     */
    public static Theme getSavedTheme(Context context) {
        String savedTheme = SharedPreferencesWrapper.getString(context, SharedPreferencesWrapper.Preference.SETTINGS, THEME_KEY, DEFAULT);
        Logger.log(LogTag.GENERAL,"");
        switch (savedTheme) {
            case LIGHT:
                return Theme.LIGHT;
            case DARK:
                return Theme.DARK;
            case DEFAULT:
            default:
                return Theme.DEFAULT;
        }
    }

}
