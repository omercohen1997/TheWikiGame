package com.oog.thewikigame.activities;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import com.oog.thewikigame.R;
import com.oog.thewikigame.databinding.ActivityGameConfigBinding;
import com.oog.thewikigame.handlers.Game;
import com.oog.thewikigame.handlers.GameLanguage;
import com.oog.thewikigame.handlers.WebViewHandler;
import com.oog.thewikigame.models.IconButtonModel;
import com.oog.thewikigame.models.TwoLinePopupModel;
import com.oog.thewikigame.models.TwoLineSwitchModel;
import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GameConfigActivity extends AppCompatActivity {

    ActivityGameConfigBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_config);


        View.OnClickListener expandConfiguration = v -> {
            ObjectAnimator anim = ObjectAnimator.ofFloat(binding.gameConfigIconButtonExpandId, "rotation",
                    isCardExpanded() ? 0 : 180,
                    isCardExpanded() ? 180 : 360);
            anim.setDuration(500);
            anim.start();
            TransitionManager.beginDelayedTransition(binding.gameConfigLayoutExpandableId, new AutoTransition());
            binding.gameConfigLayoutExpandableId.setVisibility(isCardExpanded() ? View.GONE : View.VISIBLE);
        };

        binding.gameConfigLayoutConfigurationId.setOnClickListener(expandConfiguration);
        IconButtonModel iconButtonModel = new IconButtonModel(this, R.drawable.ic_baseline_expand_more_24, expandConfiguration);

        binding.setExpandIconButtonModel(iconButtonModel);

        //TODO: Delete this.

        long timeLimit = TimeUnit.SECONDS.toMillis(3605);
        //long timeLimit =Game.UNLIMITED;
        int jumpLimit = 10;
        int backsLimit = 5;
        Game.GameConfig gameConfig = new Game.GameConfig("Son_Goku",
                "Monkey_King",
                GameLanguage.SPANISH,
                jumpLimit,
                backsLimit,
                Game.UNLIMITED,
                Game.UNLIMITED,
                timeLimit);

//        Intent gameIntent = new Intent(this, GameActivity.class);
//        gameIntent.putExtra(GameActivity.INTENT_GAME_CONFIG, gameConfig);
//        startActivity(gameIntent);

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


        @StringRes int[] languageResources = {
                R.string.game_config_language_english,
                R.string.game_config_language_hebrew,
                R.string.game_config_language_arabic,
                R.string.game_config_language_russian,
                R.string.game_config_language_spanish,
                R.string.game_config_language_french,
        };
        List<String> values = new ArrayList<>(languageResources.length);

        for (int resID : languageResources)
            values.add(getString(resID));


        TwoLinePopupModel twoLinePopupModel = new TwoLinePopupModel("Language", "Sets the game language for Wikipedia");
        twoLinePopupModel.setValues(values);
        binding.setLanguageTwoLinePopupModel(twoLinePopupModel);

        binding.setTimeLimitTwoLineSwitchModel(new TwoLineSwitchModel("Time Limit", "This will set a time limit to finish the game.") {
            @Override
            public void onToggle(boolean checked) {
                //TODO: Add time limit functionality.
            }
        });
    }


    private boolean isCardExpanded() {
        return binding.gameConfigLayoutExpandableId.getVisibility() == View.VISIBLE;
    }


}