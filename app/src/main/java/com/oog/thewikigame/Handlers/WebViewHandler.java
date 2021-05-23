package com.oog.thewikigame.Handlers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.snackbar.Snackbar;
import com.oog.thewikigame.R;
import com.oog.thewikigame.Utilities.LogTag;
import com.oog.thewikigame.Utilities.Logger;

import java.util.HashMap;
import java.util.Map;

public class WebViewHandler {

    private static final String WIKI_BASE_URL = "m.wikipedia.org";
    private static final String WIKI_BASE_ARTICLE_ENDPOINT = "wiki";

    private static final Map<Language, String> WIKI_LANGUAGE_MAP = new HashMap<Language, String>() {{
        put(Language.ENGLISH, "en");
        put(Language.HEBREW, "he");
        put(Language.FRENCH, "fr");
    }};

    enum Language {
        ENGLISH,
        HEBREW,
        FRENCH
    }

    private WebView webView;
    private Language activeLanguage;
    private String currentUrl = null;

    @SuppressLint("SetJavaScriptEnabled")
    public WebViewHandler(WebView webView, Language lang) {
        this.activeLanguage = lang;
        this.webView = webView;

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Logger.log(LogTag.WEB_VIEW_HANDLER,"Finished loading the page",url);
                injectSimplifyContent();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String prefix = constructURL(null, activeLanguage);

                if (!url.startsWith(prefix)) {
                    webView.loadUrl(currentUrl);
                    alert(R.string.alert_external_link);
                    return false;
                }
                currentUrl = url;
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);

    }

    public WebViewHandler(WebView webView) {
        this(webView, Language.ENGLISH);
    }

    public void loadArticle(String article, Language lang) {
        String url = constructURL(article, lang);
        currentUrl = url;
        webView.loadUrl(url);
    }

    public void loadArticle(String article) {
        loadArticle(article, Language.ENGLISH);
    }

    public static String constructURL(String article, Language lang) {
        if (article == null) article = "";
        return String.format("https://%s.%s/%s/%s",
                WIKI_LANGUAGE_MAP.get(lang),
                WIKI_BASE_URL,
                WIKI_BASE_ARTICLE_ENDPOINT,
                article);
    }

    public static String constructURL(String article) {
        return constructURL(article, Language.ENGLISH);
    }

    /*

     PRIVATE METHODS

     */

    private void injectSimplifyContent() {
        String js = "javascript:" +
                "document.body.innerHTML = document.getElementById(\"bodyContent\").outerHTML";
        webView.loadUrl(js);
    }


    private void alert(int resID){
        Snackbar snackbar;
        snackbar = Snackbar.make(webView,resID, Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.button_ok,(v)->snackbar.dismiss()).show();
    }

}
