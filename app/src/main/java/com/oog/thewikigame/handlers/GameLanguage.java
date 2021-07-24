package com.oog.thewikigame.handlers;

import androidx.annotation.StringRes;

import com.oog.thewikigame.R;

public enum GameLanguage {

    ENGLISH(R.string.game_config_language_english, "en"),
    HEBREW(R.string.game_config_language_hebrew, "he"),
    ARABIC(R.string.game_config_language_arabic, "ar"),
    RUSSIAN(R.string.game_config_language_russian, "ru"),
    FRENCH(R.string.game_config_language_french, "fr"),
    SPANISH(R.string.game_config_language_spanish, "sp");

    public final @StringRes
    int resId;
    public final String code;

    GameLanguage(@StringRes int resId, String code) {
        this.resId = resId;
        this.code = code;
    }

}
