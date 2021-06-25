package com.oog.thewikigame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;

import com.oog.thewikigame.R;
import com.oog.thewikigame.models.TwoLineIconButtonModel;
import com.oog.thewikigame.models.TwoLineModel;
import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;
import com.oog.thewikigame.models.TwoLineSwitchModel;
import com.oog.thewikigame.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        TwoLineSwitchModel sounds = new TwoLineSwitchModel("Sounds", "Toggle sounds on/off.") {
            @Override
            public void onToggle(boolean checked) {
                Logger.log(LogTag.SETTINGS_ACTIVITY, "Toggled sounds", checked ? "on" : "off");
            }
        };
        TwoLineSwitchModel darkTheme = new TwoLineSwitchModel("Dark Theme", "Sets the whole app to dark theme if applied.") {
            @Override
            public void onToggle(boolean checked) {
                Logger.log(LogTag.SETTINGS_ACTIVITY, "Toggled dark theme", checked ? "on" : "off");
            }
        };

        //TODO: change to prefrences' language


        TwoLineIconButtonModel language = new TwoLineIconButtonModel("Language", "Currently set to " + "English", AppCompatResources.getDrawable(this, R.drawable.ic_baseline_language_24)) {
            @Override
            public void onClickIcon() {
                Logger.log(LogTag.SETTINGS_ACTIVITY, "Clicked on language icon");
            }
        };

        binding.settingsAppbarToolbarId.setNavigationOnClickListener(view -> finish());

        binding.setSounds(sounds);
        binding.setDarkTheme(darkTheme);
        binding.setLanguage(language);

        binding.settingsResetItemId.setOnClickListener(view -> Logger.log(LogTag.SETTINGS_ACTIVITY, "Clicked on Reset all data."));

    }


}