package com.hubrystyk.twitterwordcloud.services;

import com.hubrystyk.twitterwordcloud.entities.TrendingTopic;
import com.hubrystyk.twitterwordcloud.entities.Tweet;
import com.hubrystyk.twitterwordcloud.responses.AuthResponse;
import com.hubrystyk.twitterwordcloud.responses.TrendingResponse;
import com.hubrystyk.twitterwordcloud.responses.TweetsResponse;

import net.alhazmy13.wordcloud.WordCloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bolts.Continuation;
import bolts.Task;
import okhttp3.FormBody;

public class TwitterService {
    public static final String TAG = "TwitterService";
    private String mBearerToken;//= "AAAAAAAAAAAAAAAAAAAAAJIR0gAAAAAAtVVwYj9IVqAMsossiY%2Bwwau5Dfw%3Dsa7Un3HE5b3X6JVyAQEFsyzTJEvb2getf5k8vz8s93qV5fV8eD";
    private TwitterApiService mApiService;

    TwitterService(TwitterApiService apiService) {
        mApiService = apiService;
    }

    public Task<List<TrendingTopic>> getTrendingTopics() {
        return getBearerToken()
                .onSuccessTask(new Continuation<String, Task<TrendingResponse>>() {
                    @Override
                    public Task<TrendingResponse> then(Task<String> task) throws Exception {
                        return getTrendingTopics(task.getResult());
                    }
                })
                .onSuccess(new Continuation<TrendingResponse, List<TrendingTopic>>() {
                    @Override
                    public List<TrendingTopic> then(Task<TrendingResponse> task) throws Exception {
                        return task.getResult().getTrends();
                    }
                });
    }

    public Task<List<Tweet>> getTweetsForQuery(final String query) {
        return getBearerToken()
                .onSuccessTask(new Continuation<String, Task<TweetsResponse>>() {
                    @Override
                    public Task<TweetsResponse> then(Task<String> task) throws Exception {
                        return RetrofitToTask.fromCall(
                                mApiService.getTweetsForQuery(task.getResult(), query)
                        );
                    }
                })
                .onSuccess(new Continuation<TweetsResponse, List<Tweet>>() {
                    @Override
                    public List<Tweet> then(Task<TweetsResponse> task) throws Exception {
                        return task.getResult().getTweets();
                    }
                });
    }

    public Task<List<WordCloud>> getTrendingTopicWords(String query) {
        return getTweetsForQuery(query)
                .onSuccess(new Continuation<List<Tweet>, List<WordCloud>>() {
                    @Override
                    public List<WordCloud> then(Task<List<Tweet>> task) throws Exception {
                        return calculateWordCloud(task.getResult());
                    }
                });
    }

    private Task<String> getBearerToken() {
        if (mBearerToken != null) {
            return Task.forResult(mBearerToken);
        }

        FormBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();
        return RetrofitToTask
                .fromCall(mApiService.getBearerToken(authToken, body))
                .onSuccess(new Continuation<AuthResponse, String>() {
                    @Override
                    public String then(Task<AuthResponse> task) throws Exception {
                        mBearerToken = "Bearer " + task.getResult().access_token;
                        return mBearerToken;
                    }
                });
    }

    private Task<TrendingResponse> getTrendingTopics(String bearerToken) {
        return RetrofitToTask
                .fromCall(mApiService.getTrendingTopics(bearerToken))
                .onSuccess(new Continuation<List<TrendingResponse>, TrendingResponse>() {
                    @Override
                    public TrendingResponse then(Task<List<TrendingResponse>> task) throws Exception {
                        return task.getResult().get(0);
                    }
                });
    }

    private List<WordCloud> calculateWordCloud(List<Tweet> tweets) {
        Map<String, WordCloud> wordCounts = new HashMap<>();
        ArrayList<WordCloud> words = new ArrayList<>();

        for (Tweet tweet : tweets) {
            String text = tweet.getText().toLowerCase().replaceAll("[ ,.]+", " ");
            for (String word : text.split("[ ]+")) {
                if (word.startsWith("http")) {
                    continue;
                }

                WordCloud cloud = wordCounts.get(word);
                if (cloud == null) {
                    cloud = new WordCloud(word, 0);
                    wordCounts.put(word, cloud);
                    words.add(cloud);
                }
                cloud.setWeight(cloud.getWeight() + 1);
            }
        }

        Collections.sort(words, new Comparator<WordCloud>() {
            @Override
            public int compare(WordCloud o1, WordCloud o2) {
                return o2.getWeight() - o1.getWeight();
            }
        });

        return words;
    }
}
