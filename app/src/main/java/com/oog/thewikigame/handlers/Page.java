package com.oog.thewikigame.handlers;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;


public class Page {
    private final String article;

    private int usedShowLinksOnly = 0;
    private int usedFindInText = 0;

    long startTime, endTime;
    long elapsedTime = 0;


    /**
     * @param article The article of the page.
     */
    public Page(@NotNull String article) {
        this.article = article;
        enter();
    }

    /**
     * This method unlocks a rescue and tells if it's already unlocked
     *
     * @param rescueType The rescue to unlock and use
     * @return the state of the rescue.<br/>
     * <code>true</code> if already unlocked; <code>false</code> if it's first time unlock.
     */
    public int useHelp(RescueType rescueType) {
        int current = 0;
        switch (rescueType) {
            case SHOW_LINKS_ONLY:
                current = usedShowLinksOnly;
                usedShowLinksOnly++;
                break;
            case FIND_IN_TEXT:
                current = usedFindInText;
                usedFindInText++;
                break;
            case GO_BACK:
            default:
                break;
        }
        return current;
    }

    /**
     * This needs to be called every time the page is entered.
     */
    void enter() {
        startTime = System.currentTimeMillis();
    }

    /**
     * This needs to be called every time the page is exited (or switched)
     */
    void exit() {
        endTime = System.currentTimeMillis();
        elapsedTime += endTime - startTime;
    }

    public String getArticle() {
        return article;
    }

    public static class PageSummary {
        public final String article;
        public final long timeInPage;
        public final int didUseFindInText;
        public final int didUseShowLinksOnly;

        public PageSummary(String article, long timeInPage, int didUseFindInText, int didUseShowLinksOnly) {
            this.article = article;
            this.timeInPage = timeInPage;
            this.didUseFindInText = didUseFindInText;
            this.didUseShowLinksOnly = didUseShowLinksOnly;
        }

        @Override
        public String toString() {
            return "PageSummary{" +
                    "article='" + article + '\'' +
                    ", timeInPage=" + timeInPage +
                    ", didUseFindInText=" + didUseFindInText +
                    ", didUseShowLinksOnly=" + didUseShowLinksOnly +
                    '}';
        }
    }

    public PageSummary createPageSummary() {
        return new PageSummary(
                article,
                elapsedTime,
                usedFindInText,
                usedShowLinksOnly
        );
    }

}
