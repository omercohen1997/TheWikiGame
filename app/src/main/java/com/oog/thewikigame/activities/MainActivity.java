package com.oog.thewikigame.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.oog.thewikigame.R;
import com.oog.thewikigame.databinding.ActivityMainBinding;
import com.oog.thewikigame.handlers.LocaleHandler;
import com.oog.thewikigame.handlers.ThemeHandler;
import com.oog.thewikigame.utilities.ActivityResultUtil;

public class MainActivity extends AppCompatActivity {

    private LocaleHandler.LocaleCode localeCode;

    private ActivityResultLauncher<Intent> settingsActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ThemeHandler.updateSystemToSavedTheme(this);
        LocaleHandler.updateSystemToSavedLocale(this);
        super.onCreate(savedInstanceState);

        localeCode = LocaleHandler.getSavedLocale(this);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        settingsActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == ActivityResultUtil.RESULT_UPDATE) {
                if (!localeCode.equals(LocaleHandler.getSavedLocale(this))) recreate();
            }
        });

        binding.mainButtonPlayId.setOnClickListener(v ->
                startActivity(new Intent(this, GameConfigActivity.class)));

        binding.mainButtonRecordsId.setOnClickListener(v ->
                startActivity(new Intent(this, GameRecordsActivity.class)));

        binding.mainButtonSettingsId.setOnClickListener(v -> {
            Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
            settingsActivityIntent.putExtra("LocaleCode", localeCode);
            settingsActivityResultLauncher.launch(settingsActivityIntent);
        });

        binding.mainButtonAboutId.setOnClickListener(v ->
                startActivity(new Intent(this, AboutActivity.class)));

        //TODO: Implement records.
        binding.mainButtonRecordsId.setOnClickListener(v ->
                startActivity(new Intent(this, GameRecordsActivity.class)));
    }
}