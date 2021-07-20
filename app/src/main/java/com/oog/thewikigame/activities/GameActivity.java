package com.oog.thewikigame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.oog.thewikigame.R;
import com.oog.thewikigame.databinding.ActivityGameBinding;
import com.oog.thewikigame.exceptions.NoRescueException;
import com.oog.thewikigame.handlers.Game;
import com.oog.thewikigame.handlers.Page;
import com.oog.thewikigame.handlers.RescueType;
import com.oog.thewikigame.handlers.ThemeHandler;
import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

//TODO: Implement catch back button.
//TODO: Implement Exit.
//TODO: Fix ShowLinksOnly in dark theme.

public class GameActivity extends AppCompatActivity {

    public static final String INTENT_GAME_CONFIG = "CONFIG";
    ActivityGameBinding binding;
    Game game;
    public Game.GameConfig gameConfig;
    Handler timeHandler;
    String findText = "";
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game);

        gameConfig = (Game.GameConfig) getIntent().getExtras().get(INTENT_GAME_CONFIG);

        if (gameConfig == null) {
            finish();
            return;
        }

        timeHandler = new Handler();


        //TODO: handle this properly.
        ThemeHandler.isDarkTheme(this);


        game = new Game(binding.gameWebView, "Son_Goku", "Monkey_King", gameConfig) {
            @Override
            protected void onGameFinished(boolean success, List<Page.PageSummary> pageSummaryList) {
                for (Page.PageSummary pageSummary : pageSummaryList)
                    Logger.log(LogTag.HANDLERS, pageSummary);
            }
        };
        binding.gameToolbarId.getMenu().findItem(R.id.game_menu_go_back_id).setOnMenuItemClickListener(this::goBack);
        binding.gameToolbarId.getMenu().findItem(R.id.game_menu_hide_text_id).setOnMenuItemClickListener(this::showLinksOnly);
        binding.gameToolbarId.getMenu().findItem(R.id.game_menu_find_id).setOnMenuItemClickListener(this::findInText);
        loopTimer();


    }


    private boolean goBack(MenuItem item) {
        try {
            game.goBack();
        } catch (NoRescueException e) {
            Snackbar.make(binding.getRoot(), "No More Go Back rescues", Snackbar.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean showLinksOnly(MenuItem item) {
        try {
            game.toggleShowLinksOnly();
        } catch (NoRescueException e) {
            Snackbar.make(binding.getRoot(), "No More ShowLinksOnly rescues", Snackbar.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean findInText(MenuItem item) {
        if (!game.isRescueValid(RescueType.FIND_IN_TEXT)) {
            Snackbar.make(binding.getRoot(), "No More FindInText", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
        View dialogView = LayoutInflater.from(dialogBuilder.getContext()).inflate(R.layout.dialog_view_find_in_text, null);
        EditText textField = dialogView.findViewById(R.id.dialog_find_in_text_input_id);
        textField.setText(findText);
        dialogBuilder.setTitle(R.string.game_find_in_text);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setNeutralButton(R.string.button_cancel, null);

        dialogBuilder.setPositiveButton(R.string.game_button_find, (dialogInterface, v) ->
                game.findInText(findText = textField.getText().toString()));

        dialogBuilder.show();
        return false;
    }


    /**
     * This method will update the title every 200ms and
     * call game failed on time end.
     */
    private void loopTimer() {
        timeHandler.postDelayed(() -> {
            long limit;
            if (gameConfig.getTimeLimit() != Game.UNLIMITED) {
                limit = gameConfig.getTimeLimit() - game.getTimeElapsedMillis();
                if (limit <= 0) game.failed();
                else loopTimer();
            } else {
                limit = game.getTimeElapsedMillis();
                loopTimer();
            }
            binding.gameToolbarId.setTitle(milliTimeToString(limit) + " " + jumpsToString());
        }, 200);
    }

    private String jumpsToString() {
        if (gameConfig.getNumOfJumps() == Game.UNLIMITED)
            return game.getJump() + "/∞";
        else return game.getJump() + "/" + gameConfig.getNumOfJumps();
    }

    private String milliTimeToString(long milliTime) {
        long hours = TimeUnit.MILLISECONDS.toHours(milliTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliTime) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliTime) % 60;
        String hoursString = hours > 0 ? (hours < 10 ? "0" + hours : "" + hours) + ":" : "";
        String minutesString = (minutes < 10 ? "0" + minutes : "" + minutes) + ":";
        String secondsString = seconds < 10 ? "0" + seconds : "" + seconds;
        return hoursString + minutesString + secondsString;
    }


}