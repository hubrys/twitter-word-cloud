package com.hubrystyk.twitterwordcloud.services;

public class ServiceProvider {
    public static final String TAG = "ServiceProvider";

    public static TwitterService getTwitterService() {
        return new TwitterService();
    }
}
