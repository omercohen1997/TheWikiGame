package com.oog.thewikigame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.oog.thewikigame.R;
import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.log(LogTag.MAIN_ACTIVITY,"Created MainActivity");
    }

    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.main_button_play_id:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.main_button_records_id:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.main_button_settings_id:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.main_button_about_id:
                intent = new Intent(this, MainActivity.class);
                break;
            default:
                return;
        }
        startActivity(intent);

    }
}