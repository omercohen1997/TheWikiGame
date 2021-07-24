package com.oog.thewikigame.handlers;

import android.webkit.WebView;

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
    private long startTime = System.currentTimeMillis() / 1000;
    private long pauseTime = 0;
    private final GameConfig gameConfig;


    public static class GameConfig implements Serializable {
        private final String startArticle;
        private final String endArticle;
        private final GameLanguage gameLanguage;
        private final int timeLimit;
        private final int numOfJumps;
        private int numOfGoBack;
        private int numOfFindInText;
        private int numOfShowLinksOnly;


        public GameConfig(String startArticle,
                          String endArticle,
                          GameLanguage gameLanguage,
                          int numOfJumps,
                          int numOfGoBack,
                          int numOfFindInText,
                          int numOfShowLinksOnly,
                          int timeLimit) {
            this.startArticle = startArticle;
            this.endArticle = endArticle;
            this.gameLanguage = gameLanguage;
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

        public int getTimeLimitSeconds() {
            return timeLimit;
        }

        public GameLanguage getLanguage() {
            return gameLanguage;
        }

        @Override
        public String toString() {
            return "GameConfig{" +
                    "startArticle='" + startArticle + '\'' +
                    ", endArticle='" + endArticle + '\'' +
                    ", gameLanguage=" + gameLanguage +
                    ", timeLimit=" + timeLimit +
                    ", numOfJumps=" + numOfJumps +
                    ", numOfGoBack=" + numOfGoBack +
                    ", numOfFindInText=" + numOfFindInText +
                    ", numOfShowLinksOnly=" + numOfShowLinksOnly +
                    '}';
        }

    }


    public Game(WebView webView, GameConfig gameConfig) {
        webViewHandler = new WebViewHandler(webView, gameConfig.gameLanguage) {
            @Override
            public void onLoadedArticle(String article) {
                Logger.log(LogTag.HANDLERS, "Received next article from WebViewHandler:", article);
                getPage().exit();
                pushNewPage(article);
                if (gameConfig.getNumOfJumps() != UNLIMITED && pageStack.size() > gameConfig.getNumOfJumps()) {
                    failed();
                    return;
                }
                if (article.equals(gameConfig.endArticle)) {
                    getPage().exit();
                    onGameFinished(true, createPagesSummaryList());
                }
            }
        };
        this.gameConfig = gameConfig;
        pushNewPage(gameConfig.startArticle);
        webViewHandler.loadArticle(gameConfig.startArticle);
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
        if (getPage().useHelp(RescueType.FIND_IN_TEXT) == 0 && gameConfig.numOfFindInText != UNLIMITED) {
            if (gameConfig.numOfFindInText == 0) {
                onRescueUseFailed(RescueType.FIND_IN_TEXT, "No more show links only rescues.");
                return;
            }
            gameConfig.numOfFindInText--;
        }
        webViewHandler.find(text);
        onRescueUseSuccess(RescueType.FIND_IN_TEXT, gameConfig.getNumOfFindInText());
    }

    public void useGoBack() {
        if (pageStack.size() == 1) {
            onRescueUseFailed(RescueType.GO_BACK, "First article; nowhere to go back.");
            return;
        }
        if (gameConfig.numOfGoBack == 0) {
            onRescueUseFailed(RescueType.GO_BACK, "No more go back rescues.");
            return;
        }
        if (gameConfig.numOfGoBack != UNLIMITED) gameConfig.numOfGoBack--;

        pageStack.pop();
        getPage().enter();
        webViewHandler.loadArticle(getPage().getArticle());
        onRescueUseSuccess(RescueType.GO_BACK, gameConfig.getNumOfGoBack());
    }

    public void toggleShowLinksOnly() {
        if (getPage().useHelp(RescueType.SHOW_LINKS_ONLY) == 0 && gameConfig.numOfShowLinksOnly != UNLIMITED) {
            if (gameConfig.numOfShowLinksOnly == 0) {
                onRescueUseFailed(RescueType.SHOW_LINKS_ONLY, "No more show links only rescues.");
                return;
            }
            gameConfig.numOfShowLinksOnly--;
        }
        webViewHandler.toggleText();
        onRescueUseSuccess(RescueType.SHOW_LINKS_ONLY, gameConfig.getNumOfShowLinksOnly());
    }

    public void pause() {
        if (pauseTime == 0)
            pauseTime = System.currentTimeMillis() / 1000;
    }

    public void resume() {
        if (pauseTime != 0) {
            startTime += System.currentTimeMillis() / 1000 - pauseTime;
            pauseTime = 0;
        }
    }

    public long getTimeElapsedSeconds() {
        if (pauseTime != 0)
            return pauseTime - startTime;
        return System.currentTimeMillis() / 1000 - startTime;
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
     * This method will be invoked when the game is done.
     *
     * @param success         {@code true} if the game finished successfully, {@code false} otherwise.
     * @param pageSummaryList a list of all the visited pages (except returns) and their stats.
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
