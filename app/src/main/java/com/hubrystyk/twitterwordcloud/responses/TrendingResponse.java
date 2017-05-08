package com.hubrystyk.twitterwordcloud.responses;

import com.hubrystyk.twitterwordcloud.entities.TrendingTopic;

import java.util.List;

public class TrendingResponse {
    private List<TrendingTopic> trends;

    public List<TrendingTopic> getTrends() {
        return trends;
    }
}
