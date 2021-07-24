package com.oog.thewikigame.handlers;

import android.content.Context;

import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;
import com.oog.thewikigame.wrappers.SharedPreferencesWrapper;

public class GameConfigurationHandler {

    private static final GameLanguage DEFAULT_GAME_LANGUAGE = GameLanguage.ENGLISH;
    private static final int DEFAULT_TIME_LIMIT = 600;
    private static final int DEFAULT_JUMP_LIMIT = 10;
    private static final int DEFAULT_GO_BACK = Game.UNLIMITED;
    private static final int DEFAULT_FIND_IN_TEXT = Game.UNLIMITED;
    private static final int DEFAULT_SHOW_LINKS_ONLY = Game.UNLIMITED;


    public enum Configuration {
        LANGUAGE,
        TIME_LIMIT,
        JUMP_LIMIT,
        GO_BACK,
        FIND_IN_TEXT,
        SHOW_LINKS_ONLY
    }

    public static void setLanguage(Context context, GameLanguage gameLanguage) {
        setConfiguration(context, Configuration.LANGUAGE, gameLanguage);
    }

    public static GameLanguage getLanguage(Context context) {
        return (GameLanguage) getConfiguration(context, Configuration.LANGUAGE);
    }


    public static void setJumpLimit(Context context, int jumpLimit) {
        setConfiguration(context, Configuration.JUMP_LIMIT, jumpLimit);
    }

    public static int getJumpLimit(Context context) {
        return (int) getConfiguration(context, Configuration.JUMP_LIMIT);
    }

    public static void setTimeLimit(Context context, int timeLimitInSeconds) {
        setConfiguration(context, Configuration.TIME_LIMIT, timeLimitInSeconds);
    }

    public static int getTimeLimit(Context context) {
        return (int) getConfiguration(context, Configuration.TIME_LIMIT);
    }

    public static void setFindInText(Context context, int findInTextCount) {
        setConfiguration(context, Configuration.FIND_IN_TEXT, findInTextCount);
    }

    public static int getFindInText(Context context) {
        return (int) getConfiguration(context, Configuration.FIND_IN_TEXT);
    }

    public static void setShowLinksOnly(Context context, int showLinksOnlyCount) {
        setConfiguration(context, Configuration.SHOW_LINKS_ONLY, showLinksOnlyCount);
    }

    public static int getShowLinksOnly(Context context) {
        return (int) getConfiguration(context, Configuration.SHOW_LINKS_ONLY);
    }

    public static void setGoBack(Context context, int goBackCount) {
        setConfiguration(context, Configuration.GO_BACK, goBackCount);
    }

    public static int getGoBack(Context context) {
        return (int) getConfiguration(context, Configuration.GO_BACK);
    }


    public static void setConfiguration(Context context, Configuration configuration, Object value) {
        SharedPreferencesWrapper sharedPreferencesWrapper = SharedPreferencesWrapper.make(context, SharedPreferencesWrapper.Preference.GAME);
        switch (configuration) {
            case LANGUAGE:
                sharedPreferencesWrapper.putString(configuration.toString(), ((GameLanguage) value).toString());
                break;
            case TIME_LIMIT:
            case JUMP_LIMIT:
            case GO_BACK:
            case FIND_IN_TEXT:
            case SHOW_LINKS_ONLY:
                sharedPreferencesWrapper.putInt(configuration.toString(), (int) value);
                break;
        }
    }

    public static Object getConfiguration(Context context, Configuration configuration) {
        SharedPreferencesWrapper sharedPreferencesWrapper = SharedPreferencesWrapper.make(context, SharedPreferencesWrapper.Preference.GAME);
        int defaultInt;
        switch (configuration) {
            case LANGUAGE:
                try {
                    return GameLanguage.valueOf(sharedPreferencesWrapper.getString(configuration.toString(), DEFAULT_GAME_LANGUAGE.toString()));
                } catch (IllegalArgumentException e) {
                    return GameLanguage.ENGLISH;
                }
            case TIME_LIMIT:
                defaultInt = DEFAULT_TIME_LIMIT;
                break;
            case JUMP_LIMIT:
                defaultInt = DEFAULT_JUMP_LIMIT;
                break;
            case GO_BACK:
                defaultInt = DEFAULT_GO_BACK;
                break;
            case FIND_IN_TEXT:
                defaultInt = DEFAULT_FIND_IN_TEXT;
                break;
            case SHOW_LINKS_ONLY:
                defaultInt = DEFAULT_SHOW_LINKS_ONLY;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + configuration);
        }
        return sharedPreferencesWrapper.getInt(configuration.toString(), defaultInt);
    }


}
