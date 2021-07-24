package com.oog.thewikigame.utilities;

import android.util.Log;

import java.util.Arrays;


public class Logger {

    public static void log(LogTag tag, Object... messages) {
        StringBuilder sb = new StringBuilder();
        for (Object i : messages) {
            try {
                sb.append(i.toString()).append(" ");
            } catch (NullPointerException e) {
                sb.append("[NULLPTR] ");
            }catch (Exception e){
                sb.append("[ERROR] ");
            }
        }
        Log.d(tag.toString(), sb.toString());
    }

    public static <E extends Exception> void log(Exception e){
        log(LogTag.EXCEPTION,
                Arrays.toString(e.getStackTrace()));
    }

}
