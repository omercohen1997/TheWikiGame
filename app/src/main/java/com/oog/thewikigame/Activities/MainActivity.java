package com.oog.thewikigame.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oog.thewikigame.R;
import com.oog.thewikigame.Utilities.LogTag;
import com.oog.thewikigame.Utilities.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.log(LogTag.MAIN_ACTIVITY,"Created MainActivity");
    }
}