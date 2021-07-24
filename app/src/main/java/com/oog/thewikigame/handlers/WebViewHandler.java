package com.oog.thewikigame.handlers;

import android.annotation.SuppressLint;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.snackbar.Snackbar;
import com.oog.thewikigame.R;
import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;

import org.jetbrains.annotations.NotNull;


/**
 * This class will hold and manage a {@link WebView} view and will load Wikipedia pages, inject special javascript scripts, it will
 * abstract the whole navigation and loading process.
 */
abstract public class WebViewHandler {

    //The wikipedia base url
    private static final String WIKI_BASE_URL = "m.wikipedia.org";
    //The endpoint for the articles
    private static final String WIKI_BASE_ARTICLE_ENDPOINT = "wiki";

    /**
     * This method will be called when a new article is loaded.<br/>
     * (It must be overridden)
     *
     * @param article the loaded article
     */
    abstract public void onLoadedArticle(String article);

    /**
     * This method returns the string reference for each gameLanguage
     *
     * @param lang the gameLanguage to get
     * @return a string value for the given gameLanguage.
     */
    private static String getLanguage(GameLanguage lang) {
        switch (lang) {
            case HEBREW:
                return "he";
            case FRENCH:
                return "fr";
            case ARABIC:
                return "ar";
            case SPANISH:
                return "sp";
            case RUSSIAN:
                return "ru";
            case ENGLISH:
            default:
                return "en";
        }
    }

    //The WebView this class handles
    private final WebView webView;
    //The gameLanguage of the wikipedia pages
    private final GameLanguage gameLanguage;
    //The current loaded url.

    private final boolean darkMode;

    private String currentArticle = null;

    /**
     * This constructor will take the supplied webview and override its methods to abstract its usage.<br/>
     * It will also handle all the web interactions including navigation, page handling and alerting errors.
     *
     * @param webView  The view to hold and manipulate
     * @param lang     The gameLanguage of the wikipedia game.
     */
    @SuppressLint("SetJavaScriptEnabled")
    public WebViewHandler(@NotNull WebView webView, GameLanguage lang) {
        this.gameLanguage = lang;
        this.webView = webView;
        this.darkMode = ThemeHandler.isDarkTheme(webView.getContext());
        WebViewClient mainClient = new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Logger.log(LogTag.HANDLERS, "Finished loading the page", url);
                if (darkMode) injectDarkMode();
                injectSimplifyContent();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String validURLPrefix = constructURL(null, gameLanguage);
                String currentURL = constructURL(currentArticle);

                String newURL = request.getUrl().toString();

                if (!newURL.startsWith(validURLPrefix)) {
                    view.loadUrl(currentURL);
                    Logger.log(LogTag.HANDLERS, "URL:", newURL, "doesn't match validURLPrefix:", validURLPrefix);
                    alertInvalidLink();
                    return false;
                }

                if (!currentURL.equals(newURL)) {
                    currentArticle = newURL.substring(validURLPrefix.length());
                    onLoadedArticle(currentArticle);
                }
                return false;
            }
        };

        webView.setWebViewClient(mainClient);
        webView.getSettings().setJavaScriptEnabled(true);

    }

    /**
     * This constructor will create a WebViewHandler tied to the supplied webview.<br/>
     * It sets the whole session to English.
     *
     * @param webView  The WebView to handle
     */
    public WebViewHandler(WebView webView) {
        this(webView, GameLanguage.ENGLISH);
    }

    /**
     * Loads a wikipedia article to the WebView.
     *
     * @param article The article endpoint to load
     * @param lang    The gameLanguage of the article
     */
    public void loadArticle(String article, GameLanguage lang) {
        currentArticle = article;
        String url = constructURL(article, lang);
        webView.loadUrl(url);
    }

    /**
     * Loads a wikipedia article to the WebView with a default gameLanguage of English.
     *
     * @param article The article endpoint to load
     */
    public void loadArticle(String article) {
        loadArticle(article, GameLanguage.ENGLISH);
    }

    /**
     * This method will construct a wikipedia friendly url with the given params
     *
     * @param article the article endpoint
     * @param lang    the gameLanguage of the page
     * @return the formatted url of the article.
     */
    public static @NotNull String constructURL(String article, GameLanguage lang) {
        if (article == null) article = "";
        return String.format("https://%s.%s/%s/%s",
                getLanguage(lang),
                WIKI_BASE_URL,
                WIKI_BASE_ARTICLE_ENDPOINT,
                article);
    }

    /**
     * This method will construct a wikipedia friendly url with the given article.<br/>
     * It will set to English.
     *
     * @param article the article endpoint
     * @return the formatted url of the article.
     */
    public static String constructURL(String article) {
        return constructURL(article, GameLanguage.ENGLISH);
    }


    private void injectDarkMode() {
        String js = "javascript:(function(){var style = document.querySelector(\"#injectInvert\");if(style) style.remove();else{style = document.createElement('style');style.id = \"injectInvert\";style.textContent = ` html, body{color:#555 !important;background:#ececec !important;} html, iframe{filter:invert(100%) !important;-webkit-filter:invert(100%) !important;} em,img,svg,form,image,video,audio,embed,object,button,canvas,figure:empty{opacity:0.85;filter:invert(100%) !important;-webkit-filter:invert(100%) !important;} form em,form img,form svg,form image,form video,form embed,form object,form button,form canvas,form figure:empty{filter:invert(0) !important;-webkit-filter:invert(0) !important;} [style*='background:url']:not(html):not(body):not(input),[style*='background:url']:not(html):not(body):not(input),[style*='background-image']:not(html):not(body):not(input){opacity:0.8;filter:invert(100%) !important;-webkit-filter:invert(100%) !important;} ::-moz-scrollbar{background:#28292a !important} ::-webkit-scrollbar{background:#28292a !important} ::-moz-scrollbar-track{background:#343637 !important} ::-webkit-scrollbar-track{background:#343637 !important} ::-webkit-scrollbar-thumb{background:#4d4e4f !important;border-left:1px solid #343637 !important;border-right:1px solid #343637 !important;} ::-moz-scrollbar-thumb{background:#4d4e4f !important;border-left:1px solid #343637 !important;border-right:1px solid #343637 !important;} `;document.head.append(style);} } )();";
        webView.loadUrl(js);
    }

    /**
     * This will inject a javascript snippet to the page that strips it down to the article only without the content around.
     */
    private void injectSimplifyContent() {
        String js = "javascript: var v = document.querySelector(\"#mw-mf-viewport\"); const a = ()=> v.parentNode.replaceChild(document.querySelector(\"main#content\"),v); a();";
        webView.loadUrl(js);
    }


    /**
     * This method will inject a javascript snippet to the page that toggles all texts' visibility except links.
     */
    public void toggleText() {
        String js = "javascript:(function(){var style = document.querySelector(\"#injectedWikiStyle\");if(style) style.remove();else {style = document.createElement('style');style.id = \"injectedWikiStyle\";style.textContent = \"* {color:rgba(0,0,0,0);}\";document.head.append(style);}})();";
        webView.loadUrl(js);
    }


    public void find(String text) {
        String js = "javascript:window.find(`" + text + "`)";
        webView.loadUrl(js);
    }


    /**
     * This method will pop a Snackbar with a given message and a dismiss button.
     */
    private void alertInvalidLink() {
        Snackbar snackbar;
        snackbar = Snackbar.make(webView, R.string.alert_invalid_link, Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.button_ok, (v) -> snackbar.dismiss()).show();
    }
}
