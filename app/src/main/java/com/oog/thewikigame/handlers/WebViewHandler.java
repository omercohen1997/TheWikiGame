package com.oog.thewikigame.handlers;

import android.annotation.SuppressLint;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;
import com.oog.thewikigame.R;
import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class WebViewHandler {

    private static final String WIKI_BASE_URL = "m.wikipedia.org";
    private static final String WIKI_BASE_ARTICLE_ENDPOINT = "wiki";

    enum Language {
        ENGLISH,
        HEBREW,
        FRENCH,
        ARABIC,
        SPANISH
    }

    private static final Map<Language, String> WIKI_LANGUAGE_MAP = new HashMap<Language, String>() {{
        put(Language.ENGLISH, "en");
        put(Language.HEBREW, "he");
        put(Language.FRENCH, "fr");
        put(Language.ARABIC, "ar");
        put(Language.SPANISH, "es");
    }};


    private final WebView webView;
    private Language activeLanguage;

    private String currentUrl = null;

    @SuppressLint("SetJavaScriptEnabled")
    public WebViewHandler(@NotNull WebView webView, Language lang) {
        this.activeLanguage = lang;
        this.webView = webView;

        WebViewClient mainClient = new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Logger.log(LogTag.WEB_VIEW_HANDLER, "Finished loading the page", url);
                injectSimplifyContent();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String prefix = constructURL(null, activeLanguage);
                String url = request.getUrl().toString();

                if (!url.startsWith(prefix)) {
                    view.loadUrl(currentUrl);
                    Logger.log(LogTag.WEB_VIEW_HANDLER,"URL:",url,"doesn't match prefix:",prefix);
                    alert(R.string.alert_invalid_link);
                    return false;
                }
                currentUrl = url;
                return super.shouldOverrideUrlLoading(view, request);
            }
        };

        webView.setWebViewClient(mainClient);
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

    public static @NotNull String constructURL(String article, Language lang) {
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
                "var v = document.querySelector(\"#mw-mf-viewport\");" +
                "const a = ()=>v.parentNode.replaceChild(document.querySelector(\"main#content\"),v);" +
                "a();";
        webView.loadUrl(js);
    }


    private void alert(@StringRes int resID) {
        Snackbar snackbar;
        snackbar = Snackbar.make(webView, resID, Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.button_ok,(v) -> snackbar.dismiss()).show();
    }
}
