
package com.oog.thewikigame.handlers;

import android.content.Context;
import android.content.res.Configuration;

import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;
import com.oog.thewikigame.wrappers.SharedPreferencesWrapper;

import java.util.Locale;


/**
 * This Class will manage the locale settings in game.
 * It is capable of setting and getting the locale in realtime.
 */
public class LocaleHandler {

    private static final String LANG_EN = "en";
    private static final String LANG_HE = "iw";
    private static final String LANGUAGE_KEY = "language";

    //This will hold the context of the initiator of the instance
    private final Context context;

    /**
     * This will create a {@link LocaleHandler} instance with the given context.
     *
     * @param context The context of the calling activity.
     */
    public LocaleHandler(Context context) {
        this.context = context;
    }

    public enum LocaleCode {
        EN_US,
        HE_IL
    }

    /**
     * This method will update the locale of the app and save it in the preferences.
     *
     * @param localeCode The locale code to set.
     */
    public void updateSystemLocale(LocaleCode localeCode) {
        updateSystemLocale(context, localeCode);
    }

    /**
     * This will update the app's locale configuration to the locale saved in shared preferences.
     */
    public void updateSystemToSavedLocale() {
        updateSystemLocale(getSavedLocale());
    }


    /**
     * This method will get the saved saved locale in the shared preferences.
     *
     * @return a LocalCode of the saved preferences' locale.
     */
    public LocaleCode getSavedLocale() {
        return getSavedLocale(context);
    }

    /**
     * @return a LocalCode of the current application's locale.
     */
    public LocaleCode getCurrentLocale() {
        return getCurrentLocale(context);
    }
    /*
    STATIC METHODS
    */

    /**
     * This method will update the locale of the app and save it in the preferences.
     *
     * @param context    The context of the calling activity.
     * @param localeCode The locale code to set.
     */
    public static void updateSystemLocale(Context context, LocaleCode localeCode) {
        Configuration configuration = context.getResources().getConfiguration();
        String lang;
        switch (localeCode) {
            case HE_IL:
                lang = LANG_HE;
                break;
            case EN_US:
            default:
                lang = LANG_EN;
                break;
        }
        Locale locale = new Locale.Builder().setLanguage(lang).build();
        configuration.setLocale(locale);
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        SharedPreferencesWrapper.make(context, SharedPreferencesWrapper.Preference.SETTINGS).putString(LANGUAGE_KEY, lang);
    }

    /**
     * This will update the app's locale configuration to the locale saved in shared preferences.
     *
     * @param context The context of the calling activity.
     */
    public static void updateSystemToSavedLocale(Context context) {
        updateSystemLocale(context, getSavedLocale(context));
    }

    /**
     * @param context The context of the calling activity.
     * @return a the LocaleCode of the saved preferences' locale.
     */
    public static LocaleCode getSavedLocale(Context context) {
        String savedLocale = SharedPreferencesWrapper.make(context, SharedPreferencesWrapper.Preference.SETTINGS).
                getString(LANGUAGE_KEY, LANG_EN);
        switch (savedLocale) {
            case LANG_HE:
                return LocaleCode.HE_IL;
            case LANG_EN:
            default:
                return LocaleCode.EN_US;
        }
    }

    /**
     * @param context - The context of the calling activity.
     * @return a LocalCode of the current application's locale.
     */
    public LocaleCode getCurrentLocale(Context context) {
        String currentLocaleLang = context.getResources().getConfiguration().locale.getLanguage();
        switch (currentLocaleLang) {
            case LANG_HE:
                return LocaleCode.HE_IL;
            case LANG_EN:
            default:
                return LocaleCode.EN_US;
        }
    }

}
