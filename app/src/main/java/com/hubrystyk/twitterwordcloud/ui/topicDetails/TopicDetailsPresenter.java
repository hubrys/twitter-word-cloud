package com.hubrystyk.twitterwordcloud.ui.topicDetails;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hubrystyk.twitterwordcloud.services.TwitterService;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

public class TopicDetailsPresenter
        extends MvpNullObjectBasePresenter<TopicDetailsView> {
    public static final String TAG = "TopicDetailsPresenter";

    private TwitterService mTwitterService;

    public TopicDetailsPresenter(TwitterService twitterService) {
        mTwitterService = twitterService;
    }

    public void loadWordList() {
        getView().showLoading(false);

        mTwitterService
                .getTrendingTopicWords(null)
                .continueWith(new Continuation<List<String>, Object>() {
                    @Override
                    public Object then(Task<List<String>> task) throws Exception {
                        if (task.isFaulted()) {
                            getView().showError(task.getError(), false);
                        } else {
                            getView().setData(task.getResult());
                            getView().showContent();
                        }
                        return null;
                    }
                });
    }
}
