package com.oog.thewikigame.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.button_PLAY:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.button_GAMERECORDS:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.button_SETTINGS:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.button_ABOUT:
                intent = new Intent(this, MainActivity.class);
                break;
            default:
                return;
        }
        startActivity(intent);

    }
}