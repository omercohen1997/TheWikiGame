package com.oog.thewikigame.handlers;

import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

abstract public class Game {

    public static final int UNLIMITED = -1;

    private final Stack<Page> pageStack = new Stack<>();
    private final WebViewHandler webViewHandler;
    private final long startTime = System.currentTimeMillis();
    private final GameConfig gameConfig;


    public static class GameConfig implements Serializable {
        private final String startArticle;
        private final String endArticle;
        private final GameLanguage language;
        private final int numOfJumps;
        private int numOfGoBack;
        private int numOfFindInText;
        private int numOfShowLinksOnly;
        private final long timeLimit;


        public GameConfig(String startArticle,
                          String endArticle,
                          GameLanguage language,
                          int numOfJumps,
                          int numOfGoBack,
                          int numOfFindInText,
                          int numOfShowLinksOnly,
                          long timeLimit) {
            this.startArticle = startArticle;
            this.endArticle = endArticle;
            this.language = language;
            this.numOfJumps = numOfJumps;
            this.numOfGoBack = numOfGoBack;
            this.numOfFindInText = numOfFindInText;
            this.numOfShowLinksOnly = numOfShowLinksOnly;
            this.timeLimit = timeLimit;
        }


        public String getStartArticle() {
            return startArticle;
        }

        public String getEndArticle() {
            return endArticle;
        }

        public int getNumOfJumps() {
            return numOfJumps;
        }

        public int getNumOfGoBack() {
            return numOfGoBack;
        }

        public int getNumOfFindInText() {
            return numOfFindInText;
        }

        public int getNumOfShowLinksOnly() {
            return numOfShowLinksOnly;
        }

        public long getTimeLimit() {
            return timeLimit;
        }

        public GameLanguage getLanguage() {
            return language;
        }
    }


    public Game(WebView webView, String startArticle, String endArticle, GameConfig gameConfig) {
        webViewHandler = new WebViewHandler(webView) {
            @Override
            public void onLoadedArticle(String article) {
                Logger.log(LogTag.HANDLERS, "Received next article from WebViewHandler:", article);
                pageStack.peek().exit();
                pushNewPage(article);
                if (article.equals(endArticle)) {
                    pageStack.peek().exit();
                    onGameFinished(true, createPagesSummaryList());
                }
            }
        };
        this.gameConfig = gameConfig;
        pushNewPage(startArticle);
        webViewHandler.loadArticle(startArticle);
    }

    /**
     * This method will push a new page to the stack
     *
     * @param article the article of the new page to push
     */
    private void pushNewPage(String article) {
        Page nextPage = new Page(article);
        pageStack.push(nextPage);
    }

    public GameConfig getGameStats() {
        return this.gameConfig;
    }

    public Page getPage() {
        return pageStack.peek();
    }

    public int getJump() {
        return pageStack.size() - 1;
    }


    public void useFindInText(String text) {
        if (getPage().useHelp(RescueType.FIND_IN_TEXT) == 0 || gameConfig.numOfFindInText != UNLIMITED) {
            if (gameConfig.numOfFindInText == 0) {
                onRescueUseFailed(RescueType.FIND_IN_TEXT, "No more show links only rescues.");
                return;
            }
            gameConfig.numOfFindInText--;
        }
        webViewHandler.find(text);
        onRescueUseSuccess(RescueType.FIND_IN_TEXT,gameConfig.getNumOfFindInText());
    }

    public void useGoBack() {
        if (pageStack.size() == 1) {
            onRescueUseFailed(RescueType.GO_BACK, "First article; nowhere to go back.");
            return;
        }
        if (getPage().useHelp(RescueType.GO_BACK) == 0) {
            if (gameConfig.numOfGoBack == 0) {
                onRescueUseFailed(RescueType.GO_BACK, "No more go back rescues.");
                return;
            }
            if (gameConfig.numOfGoBack != UNLIMITED) gameConfig.numOfGoBack--;
        }
        pageStack.pop();
        getPage().enter();
        webViewHandler.loadArticle(getPage().getArticle());
        onRescueUseSuccess(RescueType.GO_BACK, gameConfig.getNumOfGoBack());
    }

    public void toggleShowLinksOnly() {
        if (getPage().useHelp(RescueType.SHOW_LINKS_ONLY) == 0) {
            if (gameConfig.numOfShowLinksOnly == 0) {
                onRescueUseFailed(RescueType.SHOW_LINKS_ONLY, "No more show links only rescues.");
                return;
            }
            if (gameConfig.numOfShowLinksOnly != UNLIMITED)
                gameConfig.numOfShowLinksOnly--;
        }
        webViewHandler.toggleText();
        onRescueUseSuccess(RescueType.SHOW_LINKS_ONLY, gameConfig.getNumOfShowLinksOnly());
    }

    public long getTimeElapsedMillis() {
        return System.currentTimeMillis() - startTime;
    }

    public void failed() {
        onGameFinished(false, createPagesSummaryList());
    }


    private List<Page.PageSummary> createPagesSummaryList() {
        List<Page.PageSummary> list = new ArrayList<>();
        for (Page page : pageStack) {
            list.add(page.createPageSummary());
        }
        return list;
    }


    /**
     * This method will be invoked when the game is finished.
     */
    protected abstract void onGameFinished(boolean success, List<Page.PageSummary> pageSummaryList);


    /**
     * This method will be invoked on each rescue use and needs to be overridden to handle.
     *
     * @param rescueType The rescue that was used.
     * @param amountLeft How many more rescues are left.
     */
    protected abstract void onRescueUseSuccess(RescueType rescueType, int amountLeft);


    /**
     * This method will be invoked when a rescue couldn't be used successfully and the reason why.
     *
     * @param rescueType the rescue that failed
     * @param reason     why it failed.
     */
    protected abstract void onRescueUseFailed(RescueType rescueType, CharSequence reason);


}
