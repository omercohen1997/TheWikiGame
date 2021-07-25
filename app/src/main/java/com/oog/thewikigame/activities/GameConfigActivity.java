package com.oog.thewikigame.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;

import com.oog.thewikigame.BR;
import com.oog.thewikigame.R;
import com.oog.thewikigame.databinding.ActivityGameConfigBinding;
import com.oog.thewikigame.handlers.Game;
import com.oog.thewikigame.handlers.GameConfigurationHandler;
import com.oog.thewikigame.handlers.GameLanguage;
import com.oog.thewikigame.models.IconButtonModel;
import com.oog.thewikigame.models.TwoLinePopupModel;
import com.oog.thewikigame.models.TwoLineSwitchTextFieldModel;
import com.oog.thewikigame.utilities.Logger;
import com.oog.thewikigame.utilities.WikiApiManager;

import java.util.ArrayList;
import java.util.List;

public class GameConfigActivity extends AppCompatActivity {

    ActivityGameConfigBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_config);

        binding.gameConfigToolbarId.setNavigationOnClickListener(v -> finish());

        initExpandableConfiguration();

        initArticleLoading();

        initGameLanguage();


        binding.setTimeLimitModel(initNumericConfigurationModel(
                R.string.game_config_text_time_limit,
                R.string.game_config_text_time_limit_description,
                R.string.game_config_hint_time_limit,
                GameConfigurationHandler.Configuration.TIME_LIMIT));


        binding.setJumpLimitModel(initNumericConfigurationModel(
                R.string.game_config_text_jump_limit,
                R.string.game_config_text_jump_limit_description,
                R.string.game_config_hint_jump_limit,
                GameConfigurationHandler.Configuration.JUMP_LIMIT));

        binding.setGoBackLimitModel(initNumericConfigurationModel(
                R.string.game_config_text_go_back_limit,
                R.string.game_config_text_go_back_limit_description,
                R.string.game_config_hint_uses,
                GameConfigurationHandler.Configuration.GO_BACK));

        binding.setShowLinksOnlyLimitModel(initNumericConfigurationModel(
                R.string.game_config_text_show_links_only_limit,
                R.string.game_config_text_show_links_only_limit_description,
                R.string.game_config_hint_uses,
                GameConfigurationHandler.Configuration.SHOW_LINKS_ONLY));

        binding.setFindInTextLimitModel(initNumericConfigurationModel(
                R.string.game_config_text_find_in_text_limit,
                R.string.game_config_text_find_in_text_limit_description,
                R.string.game_config_hint_uses,
                GameConfigurationHandler.Configuration.FIND_IN_TEXT));

        binding.gameConfigButtonPlayId.setOnClickListener(v -> startGame());
    }


    private void initExpandableConfiguration() {
        binding.gameConfigLayoutExpandableId.setVisibility(View.GONE);
        View.OnClickListener expandConfiguration = v -> {
            ObjectAnimator anim = ObjectAnimator.ofFloat(binding.gameConfigIconButtonExpandId, "rotation",
                    isCardExpanded() ? 180 : 360,
                    isCardExpanded() ? 0 : 180);
            anim.setDuration(350);
            anim.start();
            TransitionManager.beginDelayedTransition(binding.gameConfigLayoutExpandableId, new AutoTransition());
            binding.gameConfigLayoutExpandableId.setVisibility(isCardExpanded() ? View.GONE : View.VISIBLE);
        };

        binding.gameConfigLayoutConfigurationId.setOnClickListener(expandConfiguration);
        IconButtonModel iconButtonModel = new IconButtonModel(this, R.drawable.ic_baseline_expand_more_24, expandConfiguration);

        binding.setExpandIconButtonModel(iconButtonModel);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initArticleLoading() {
        refreshStartArticle();
        refreshEndArticle();
        binding.setStartArticleIconButtonModel(
                new IconButtonModel(this, R.drawable.ic_baseline_refresh_24, v -> refreshStartArticle()));
        binding.setEndArticleIconButtonModel(
                new IconButtonModel(this, R.drawable.ic_baseline_refresh_24, v -> refreshEndArticle()));

        binding.gameConfigTextInputEndArticleId.setEndIconOnClickListener(v->{
            Intent intent = new Intent(this,ArticlePreviewActivity.class);
            intent.putExtra(ArticlePreviewActivity.INTENT_ARTICLE_TAG,binding.getEndArticleString());
            intent.putExtra(ArticlePreviewActivity.INTENT_LANGUAGE_TAG,GameConfigurationHandler.getLanguage(this));
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshStartArticle() {
        binding.setPlayButtonLock(false);
        WikiApiManager.getRandomArticle(GameConfigurationHandler.getLanguage(this)).thenAccept(title -> {
            binding.setStartArticleString(title);
            binding.setPlayButtonLock(true);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshEndArticle() {
        binding.setPlayButtonLock(false);

        WikiApiManager.getRandomArticle(GameConfigurationHandler.getLanguage(this)).thenAccept(title -> {
            binding.setEndArticleString(title);
            binding.setPlayButtonLock(true);
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initGameLanguage() {

        List<String> languageStrings = new ArrayList<>(GameLanguage.values().length);
        for (GameLanguage gameLanguage : GameLanguage.values())
            languageStrings.add(getString(gameLanguage.resId));

        TwoLinePopupModel twoLinePopupModel = new TwoLinePopupModel(this,
                R.string.game_config_text_language,
                R.string.game_config_text_language_description);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, languageStrings);


        String presetLanguage = getString(GameConfigurationHandler.getLanguage(this).resId);

        twoLinePopupModel.setAdapter(adapter);
        binding.setLanguageSelectModel(twoLinePopupModel);
        binding.gameConfigLayoutLanguageId.listItemTwoLineTextPopupAutoCompleteTextViewId.setText(presetLanguage);
        binding.gameConfigLayoutLanguageId.listItemTwoLineTextPopupAutoCompleteTextViewId.setOnItemClickListener((parent, view, position, id) -> {
            try {
                GameConfigurationHandler.setLanguage(this, GameLanguage.values()[position]);
                refreshStartArticle();
                refreshEndArticle();
            } catch (Exception e) {
                Logger.log(e);
            }
        });
    }


    private TwoLineSwitchTextFieldModel initNumericConfigurationModel(
            @StringRes int title,
            @StringRes int description,
            @StringRes int hint,
            GameConfigurationHandler.Configuration configuration) {
        TwoLineSwitchTextFieldModel model = new TwoLineSwitchTextFieldModel(this, title, description) {{
            setInputType(InputType.TYPE_CLASS_NUMBER);
            setHint(getString(hint));
        }};
        int savedData = 0;
        try {
            savedData = (int) GameConfigurationHandler.getConfiguration(this, configuration);
        } catch (Exception e) {
            Logger.log(e);
        }
        if (savedData == Game.UNLIMITED)
            model.setChecked(false);
        else {
            model.setChecked(true);
            model.setData(String.valueOf(savedData));
        }
        model.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                invokeOnPropertyChange((TwoLineSwitchTextFieldModel) sender, propertyId, configuration);
            }
        });
        return model;
    }


    private void invokeOnPropertyChange(TwoLineSwitchTextFieldModel model,
                                        int propertyId, GameConfigurationHandler.Configuration configuration) {
        if (propertyId == BR.checked && !model.getChecked())
            GameConfigurationHandler.setConfiguration(this, configuration, Game.UNLIMITED);

        if (propertyId == BR.data && model.getChecked()) {
            try {
                if (model.getData().isEmpty()) {
                    model.setError(getString(R.string.game_config_error_field_empty));
                    binding.setPlayButtonLock(false);
                } else {
                    model.setError(null);
                    binding.setPlayButtonLock(true);
                    GameConfigurationHandler.setConfiguration(this, configuration, Integer.parseInt(model.getData()));
                }
            } catch (NumberFormatException e) {
                Logger.log(e);
            }
        }
    }

    private boolean isCardExpanded() {
        return binding.gameConfigLayoutExpandableId.getVisibility() == View.VISIBLE;
    }

    private void startGame() {

        Game.GameConfig gameConfig = new Game.GameConfig(
                binding.getStartArticleString(),
                binding.getEndArticleString(),
                GameConfigurationHandler.getLanguage(this),
                GameConfigurationHandler.getJumpLimit(this),
                GameConfigurationHandler.getGoBack(this),
                GameConfigurationHandler.getFindInText(this),
                GameConfigurationHandler.getShowLinksOnly(this),
                GameConfigurationHandler.getTimeLimit(this)
        );
        Intent newGameIntent = new Intent(this, GameActivity.class);
        newGameIntent.putExtra(GameActivity.INTENT_GAME_CONFIG, gameConfig);
        startActivity(newGameIntent);

    }

    private void lockPlayButton(boolean enabled) {
        binding.gameConfigButtonPlayId.setEnabled(enabled);
    }

}