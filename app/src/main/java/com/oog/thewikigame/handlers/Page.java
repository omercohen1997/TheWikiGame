package com.oog.thewikigame.handlers;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;

abstract public class Page {
    private final String article;
    private final int jumpNumber;
    private Time exitTime = null;
    private boolean usedShowLinksOnly = false;
    private boolean usedFindInText = false;
    private boolean usedReturn = false;


    /*
    Public methods
     */

    public Page(@NotNull String article, int jumpNumber) {
        this.article = article;
        this.jumpNumber = jumpNumber;
    }

    void useHelp(RescueType rescueType) {
        switch (rescueType) {
            case SHOW_LINKS_ONLY:
                if (!usedShowLinksOnly) {
                    usedShowLinksOnly = true;
                    break;
                }
                return;
            case FIND_IN_TEXT:
                if (!usedFindInText) {
                    usedFindInText = true;
                    break;
                }
                return;
            case RETURN:
                if (!usedReturn) {
                    usedReturn = true;
                    break;
                }
                return;
        }
        onUseHelp(rescueType);
    }

    void jumped(Time time) {
        exitTime = time;
    }

    /*
    Event listeners
     */
    abstract void onUseHelp(RescueType help);


    /*
    Serialization
     */
    public JSONObject serialize() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("article", article);
        json.put("jumpNumber", jumpNumber);
        //TODO:test this' validity
        json.put("exitTime", exitTime == null ? null : exitTime.getTime());
        json.put("usedShowLinksOnly", usedShowLinksOnly);
        json.put("usedFindInText", usedFindInText);
        json.put("usedReturn", usedReturn);

        return json;
    }

}
