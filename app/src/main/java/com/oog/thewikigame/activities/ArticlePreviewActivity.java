package com.oog.thewikigame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.oog.thewikigame.R;
import com.oog.thewikigame.databinding.ActivityArticlePreviewBinding;
import com.oog.thewikigame.handlers.GameLanguage;
import com.oog.thewikigame.handlers.WebViewHandler;

public class ArticlePreviewActivity extends AppCompatActivity {

    public static final String INTENT_LANGUAGE_TAG = "LANGUAGE";
    public static final String INTENT_ARTICLE_TAG = "ARTICLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityArticlePreviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_article_preview);


        GameLanguage language = (GameLanguage) getIntent().getSerializableExtra(INTENT_LANGUAGE_TAG);
        String article = getIntent().getStringExtra(INTENT_ARTICLE_TAG);
        if (article == null || language == null) {
            finish();
            return;
        }

        binding.previewToolbarId.setNavigationOnClickListener(v -> finish());

        WebViewHandler webViewHandler = new WebViewHandler(binding.previewWebViewId, language) {
            @Override
            public void onLoadedArticle(String ignore) {
                loadArticle(article);
            }
        };
        webViewHandler.loadArticle(article);
    }
}