package com.oog.thewikigame.Utilities;

import android.util.Log;


public class Logger {

    public static void log(LogTag type,String ...msg){
        StringBuilder sb=new StringBuilder();
        for(String i: msg){
            sb.append(i).append(" ");
        }
        Log.d(type.toString(), sb.toString());
    }

}
