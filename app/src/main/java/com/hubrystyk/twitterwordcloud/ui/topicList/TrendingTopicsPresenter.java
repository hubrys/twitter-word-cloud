package com.hubrystyk.twitterwordcloud.ui.topicList;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hubrystyk.twitterwordcloud.entities.TrendingTopic;
import com.hubrystyk.twitterwordcloud.services.TwitterService;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

public class TrendingTopicsPresenter
        extends MvpNullObjectBasePresenter<TrendingTopicsView> {
    public static final String TAG = "TrendingTopicsPresenter";

    private TwitterService mTwitterService;

    public TrendingTopicsPresenter(TwitterService twitterService) {
        mTwitterService = twitterService;
    }

    public void loadTrendingTopics(final boolean pullToRefresh) {
        getView().showLoading(pullToRefresh);

        mTwitterService
                .getTrendingTopics()
                .continueWith(new Continuation<List<TrendingTopic>, Object>() {
                    @Override
                    public Object then(Task<List<TrendingTopic>> task) throws Exception {
                        if (task.isFaulted()) {
                            getView().showError(task.getError(), pullToRefresh);
                        } else {
                            getView().setData(task.getResult());
                            getView().showContent();
                        }
                        return null;
                    }
                });
    }
}
