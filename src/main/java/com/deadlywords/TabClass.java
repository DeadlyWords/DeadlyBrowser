package com.deadlywords;

import javafx.concurrent.Worker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TabClass extends Tab {

    private WebEngine engine;

    public WebView getWebView() {
        return webView;
    }

    private WebView webView = new WebView();


    public TabClass(String homepage, ProgressBar progressBar, Label label){

        this.setText(homepage);
        engine = webView.getEngine();

        // Add-Listener ProgressBar
        engine.getLoadWorker().progressProperty().addListener((obs, oldProgress, newProgress) ->{
            progressBar.setVisible(true);
            progressBar.setProgress(obs.getValue().doubleValue());
        });

        // Add-Listener Statuslabel
        engine.getLoadWorker().stateProperty().addListener((observableValue, state, t1)->{
            if(state == Worker.State.SUCCEEDED){
                label.setText("succeeDED");
            }
            if(state == Worker.State.RUNNING){
                label.setVisible(true);
                label.setText("Page Load Succeeded!");
                new Thread(() ->{
                    try {
                        Thread.sleep(3000);
                        progressBar.setVisible(false);
                        label.setVisible(false);
                    } catch (InterruptedException e) {
                    }
                }).start();
            }
            if(state == Worker.State.FAILED){
                label.setText("Page Load Failed!");
                // TODO Html header to complete
                webView.getEngine().loadContent("<title>Not Found!</title><h1>Not found!</h1><h2>This page is not on the internet</h2>");
                this.setGraphic(null);
            }
        });

        //TODO refactor to method
        webView.setLayoutX(-1);
        webView.setLayoutY(128);
        webView.setPrefHeight(700);
        webView.setPrefWidth(1604);
        loadFavicon(homepage);
        this.setContent(webView);
        webView.getEngine().load(homepage);
    }

    public void setUrlAndLoad(String urlInput){
        webView.getEngine().load(urlInput);
        loadFavicon(urlInput);
        this.setText(urlInput);
    }

    /*
    public void setWebViewPref(){

    }
     */

    void loadFavicon(String location) {
        try {
            if(location.startsWith("https://")){
                location = location.substring(8);
            }
            String faviconUrl = String.format("http://www.google.com/s2/favicons?domain_url=%s", URLEncoder.encode(location, "UTF-8"));
            Image favicon = new Image(faviconUrl, true);
            ImageView iv = new ImageView(favicon);
            this.setGraphic(iv);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex); // not expected
        }
    }

    public String getUrl() {
        return webView.getEngine().getLocation();
    }



}

