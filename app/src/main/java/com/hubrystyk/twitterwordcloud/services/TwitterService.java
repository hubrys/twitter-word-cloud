package com.hubrystyk.twitterwordcloud.services;

import com.hubrystyk.twitterwordcloud.entities.TrendingTopic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bolts.Task;

public class TwitterService {
    public static final String TAG = "TwitterService";

    TwitterService() {
    }

    public Task<List<TrendingTopic>> getTrendingTopics() {
        List<TrendingTopic> topics = new ArrayList<>();
        topics.add(new TrendingTopic("Unicorns"));
        topics.add(new TrendingTopic("Apples"));
        topics.add(new TrendingTopic("Programming"));
        topics.add(new TrendingTopic("Haircuts"));
        return Task.forResult(topics);
    }

    public Task<List<String>> getTrendingTopicWords(TrendingTopic topic) {
        List<String> strings = Arrays.asList(
                "Test",
                "Test",
                "Test",
                "Another",
                "Another",
                "Help"
        );

        return Task.forResult(strings);
    }
}
