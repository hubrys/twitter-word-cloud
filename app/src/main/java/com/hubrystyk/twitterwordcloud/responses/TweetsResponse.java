package com.hubrystyk.twitterwordcloud.responses;

import com.hubrystyk.twitterwordcloud.entities.Tweet;

import java.util.List;

public class TweetsResponse {
    public static final String TAG = "TweetsResponse";
    private List<Tweet> statuses;

    public List<Tweet> getTweets() {
        return statuses;
    }
}
