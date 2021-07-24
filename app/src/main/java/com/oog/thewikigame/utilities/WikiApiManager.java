package com.oog.thewikigame.utilities;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.oog.thewikigame.handlers.GameLanguage;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class WikiApiManager {
    private static final String SUFFIX = ".wikipedia.org/w/api.php?action=query&format=json&generator=random&grnnamespace=0&prop=info&inprop=url|displaytitle";


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static CompletableFuture<String> getRandomArticle(GameLanguage language) {
        return CompletableFuture.supplyAsync(() -> {
            String langCode = language.code;
            try {
                URL url = new URL("https://" + langCode + SUFFIX);
                try (InputStream in = url.openConnection().getInputStream()) {
                    String response = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining(""));
                    JSONObject object = new JSONObject(response).getJSONObject("query").getJSONObject("pages");
                    object = object.getJSONObject((String) Objects.requireNonNull(object.names()).get(0));
                    return trimURL(object.getString("canonicalurl"));
                } catch (Exception e) {
                    Logger.log(e);
                    return null;
                }
            } catch (Exception ignored) {
                return null;
            }
        });
    }

    private static String trimURL(String url) {
        String trimmed = url.substring("https://xx.wikipedia.org/wiki/".length());
        try {
            return URLDecoder.decode(
                    trimmed, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Logger.log(e);
            return trimmed;
        }
    }
}
