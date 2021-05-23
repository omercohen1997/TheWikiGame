package com.oog.thewikigame.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.oog.thewikigame.Handlers.WebViewHandler;
import com.oog.thewikigame.R;

public class WikipediaWebsiteFragment extends Fragment {


    private WebViewHandler webViewHandler;

    public WikipediaWebsiteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =inflater.inflate(R.layout.fragment_wikipedia_website, container, false);
        WebView webView = fragmentView.findViewById(R.id.wikipedia_website_webview_id);
        webViewHandler= new WebViewHandler(webView);
        webViewHandler.loadArticle("Goku");
        return fragmentView;
    }
}