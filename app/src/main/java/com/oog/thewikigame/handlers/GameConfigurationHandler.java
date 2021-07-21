package com.oog.thewikigame.handlers;

import android.content.Context;

import com.oog.thewikigame.wrappers.SharedPreferencesWrapper;

public class GameConfigurationHandler {

    private static final GameLanguage DEFAULT_LANGUAGE = GameLanguage.ENGLISH;
    private static final long DEFAULT_TIME_LIMIT = 600;
    private static final long DEFAULT_JUMP_LIMIT = 10;
    private static final int DEFAULT_GO_BACK = Game.UNLIMITED;
    private static final int DEFAULT_FIND_IN_TEXT = Game.UNLIMITED;
    private static final int DEFAULT_SHOW_LINKS_ONLY = Game.UNLIMITED;


    public enum Configurations {
        LANGUAGE,
        TIME_LIMIT,
        JUMP_LIMIT,
        GO_BACK,
        FIND_IN_TEXT,
        SHOW_LINKS_ONLY
    }

    public static void setLanguage(Context context, GameLanguage language) {
        setConfiguration(context, Configurations.LANGUAGE, language);
    }

    public static GameLanguage getLanguage(Context context) {
        return (GameLanguage) getConfiguration(context, Configurations.LANGUAGE);
    }


    public static void setJumpLimit(Context context, int jumpLimit) {
        setConfiguration(context, Configurations.JUMP_LIMIT, jumpLimit);
    }

    public static long getJumpLimit(Context context) {
        return (long)getConfiguration(context, Configurations.JUMP_LIMIT);
    }

    public static void setTimeLimit(Context context, long timeLimitInSeconds) {
        setConfiguration(context, Configurations.TIME_LIMIT, timeLimitInSeconds);
    }

    public static long getTimeLimit(Context context){
        return (long) getConfiguration(context,Configurations.TIME_LIMIT);
    }

    public static void setFindInText(Context context, int findInTextCount){
        setConfiguration(context,Configurations.FIND_IN_TEXT,findInTextCount);
    }
    public static int getFindInText(Context context){
        return  (int)getConfiguration(context,Configurations.FIND_IN_TEXT);
    }

    public static void setShowLinksOnly(Context context, int showLinksOnlyCount){
        setConfiguration(context,Configurations.SHOW_LINKS_ONLY,showLinksOnlyCount);
    }
    public static int getShowLinksOnly(Context context){
        return  (int)getConfiguration(context,Configurations.SHOW_LINKS_ONLY);
    }

    public static void setGoBack(Context context, int goBackCount){
        setConfiguration(context,Configurations.GO_BACK,goBackCount);
    }
    public static int getGoBack(Context context){
        return  (int)getConfiguration(context,Configurations.GO_BACK);
    }

    private static void setConfiguration(Context context, Configurations configuration, Object value) {
        SharedPreferencesWrapper sharedPreferencesWrapper = SharedPreferencesWrapper.make(context, SharedPreferencesWrapper.Preference.GAME);
        switch (configuration) {
            case LANGUAGE:
                sharedPreferencesWrapper.putString(configuration.toString(), ((GameLanguage) value).toString());
                break;
            case TIME_LIMIT:
            case JUMP_LIMIT:
                sharedPreferencesWrapper.putLong(configuration.toString(), (long) value);
                break;
            case GO_BACK:
            case FIND_IN_TEXT:
            case SHOW_LINKS_ONLY:
                sharedPreferencesWrapper.putInt(configuration.toString(), (int) value);
                break;
        }
    }

    private static Object getConfiguration(Context context, Configurations configurations) {
        SharedPreferencesWrapper sharedPreferencesWrapper = SharedPreferencesWrapper.make(context, SharedPreferencesWrapper.Preference.GAME);
        switch (configurations) {
            case LANGUAGE:
                try {
                    return GameLanguage.valueOf(sharedPreferencesWrapper.getString(configurations.toString(), DEFAULT_LANGUAGE.toString()));
                } catch (IllegalArgumentException e) {
                    return GameLanguage.ENGLISH;
                }
            case TIME_LIMIT:
                return sharedPreferencesWrapper.getLong(configurations.toString(), DEFAULT_TIME_LIMIT);
            case JUMP_LIMIT:
                return sharedPreferencesWrapper.getLong(configurations.toString(), DEFAULT_JUMP_LIMIT);
            case GO_BACK:
                return sharedPreferencesWrapper.getInt(configurations.toString(), DEFAULT_GO_BACK);
            case FIND_IN_TEXT:
                return sharedPreferencesWrapper.getInt(configurations.toString(), DEFAULT_FIND_IN_TEXT);
            case SHOW_LINKS_ONLY:
                return sharedPreferencesWrapper.getInt(configurations.toString(), DEFAULT_SHOW_LINKS_ONLY);
            default:
                throw new IllegalStateException("Unexpected value: " + configurations);
        }
    }


}
