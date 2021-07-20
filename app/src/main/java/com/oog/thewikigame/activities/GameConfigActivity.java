package com.oog.thewikigame.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oog.thewikigame.R;
import com.oog.thewikigame.databinding.ActivityGameConfigBinding;
import com.oog.thewikigame.handlers.Game;
import com.oog.thewikigame.handlers.ThemeHandler;
import com.oog.thewikigame.handlers.WebViewHandler;
import com.oog.thewikigame.models.IconButtonModel;
import com.oog.thewikigame.models.TwoLineSwitchModel;
import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class GameConfigActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityGameConfigBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_game_config);


        //TODO: Delete this.

        long timeLimit = TimeUnit.SECONDS.toMillis(3605);
        //long timeLimit =Game.UNLIMITED;
        int jumpLimit = 10;
        Game.GameConfig gameConfig = new Game.GameConfig("Son_Goku",
                "Monkey_King",
                WebViewHandler.Language.SPANISH,
                jumpLimit,
                Game.UNLIMITED,
                Game.UNLIMITED,
                Game.UNLIMITED,
                timeLimit);

        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra(GameActivity.INTENT_GAME_CONFIG, gameConfig);
        startActivity(gameIntent);















        binding.setStartArticleString("Son Goku");
        binding.setStartArticleIconButtonModel(new IconButtonModel(this, R.drawable.ic_baseline_refresh_24, v -> {

            binding.setStartArticleString(this.getString(R.string.text_loading));
            //TODO: Add web view integration.
            //TODO: delete this simulation.
            CompletableFuture.runAsync(() -> {
                binding.setStartArticleString(this.getString(R.string.text_loading));
                try {
                    Thread.sleep(3000);
                    binding.setStartArticleString("Makita");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }));


        TwoLineSwitchModel timeLimitSwitchModel = new TwoLineSwitchModel("Time Limit", "This will set a time limit to finish the game.") {
            @Override
            public void onToggle(boolean checked) {
                //TODO: Add time limit functionality.
            }
        };

        TwoLineSwitchModel findInTextSwitchModel = new TwoLineSwitchModel("Find in text", "to find in text") {
            @Override
            public void onToggle(boolean checked) {
                //TODO: do something.
            }
        };

        TwoLineSwitchModel openPornHub = new TwoLineSwitchModel("Open pornhub", "yup:") {
            @Override
            public void onToggle(boolean checked) {
                if (checked) {
                    try {
                        URL url = new URL("https://pornhub.com");
                        url.openConnection();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        binding.setTimeLimitTwoLineSwitchModel(timeLimitSwitchModel);
        binding.setPornhubHaHa(openPornHub);
        binding.setFindInTextSwitchModel(findInTextSwitchModel);
    }
}