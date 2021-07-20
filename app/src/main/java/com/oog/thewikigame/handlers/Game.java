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
    private final long startTime = System.currentTimeMillis();
    private final GameConfig gameConfig;


    public static class GameConfig implements Serializable {
        private final String startArticle;
        private final String endArticle;
        private final WebViewHandler.Language language;
        private final int numOfJumps;
        private int numOnReturns;
        private int numOfFindInText;
        private int numOfShowLinksOnly;
        private final long timeLimit;


        public GameConfig(String startArticle,
                          String endArticle,
                          WebViewHandler.Language language,
                          int numOfJumps,
                          int numOnReturns,
                          int numOfFindInText,
                          int numOfShowLinksOnly,
                          long timeLimit) {
            this.startArticle = startArticle;
            this.endArticle = endArticle;
            this.language = language;
            this.numOfJumps = numOfJumps;
            this.numOnReturns = numOnReturns;
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

        public int getNumOnReturns() {
            return numOnReturns;
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

        public WebViewHandler.Language getLanguage() {
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


    public Page getPage() {
        return pageStack.peek();
    }

    public int getJump() {
        return pageStack.size() - 1;
    }


    public void findInText(String text) {
        webViewHandler.find(text);
    }

    public void goBack() {
        if (pageStack.size() > 1) {
            pageStack.pop();
            getPage().enter();
            webViewHandler.loadArticle(getPage().getArticle());
        }
    }

    public void toggleShowLinksOnly() {
        webViewHandler.toggleText();
    }

    public long getTimeElapsedMillis() {
        return System.currentTimeMillis() - startTime;
    }

    public boolean isRescueValid(RescueType rescueType) {
        if (pageStack.peek().useHelp(rescueType) > 0) return true;
        switch (rescueType) {
            case SHOW_LINKS_ONLY:
                if (gameConfig.numOfShowLinksOnly == 0) return false;
                if (gameConfig.numOfShowLinksOnly != UNLIMITED)
                    gameConfig.numOfShowLinksOnly--;
                break;
            case FIND_IN_TEXT:
                if (gameConfig.numOfFindInText == 0) return false;
                if (gameConfig.numOfFindInText != UNLIMITED) gameConfig.numOfFindInText--;
                break;
            case GO_BACK:
                if (gameConfig.numOnReturns == 0) return false;
                if (gameConfig.numOnReturns != UNLIMITED) gameConfig.numOnReturns--;
                break;
        }
        return true;
    }

    public void failed() {
        onGameFinished(false, createPagesSummaryList());
    }


    /**
     * This method will be invoked when the game is finished.
     */
    protected abstract void onGameFinished(boolean success, List<Page.PageSummary> pageSummaryList);


    private List<Page.PageSummary> createPagesSummaryList() {
        List<Page.PageSummary> list = new ArrayList<>();
        for (Page page : pageStack) {
            list.add(page.createPageSummary());
        }
        return list;
    }


}
