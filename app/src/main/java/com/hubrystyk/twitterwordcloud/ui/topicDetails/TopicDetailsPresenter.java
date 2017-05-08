package com.hubrystyk.twitterwordcloud.ui.topicDetails;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hubrystyk.twitterwordcloud.services.TwitterService;

import net.alhazmy13.wordcloud.WordCloud;

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

    public void loadWordList(String query) {
        getView().showLoading(false);

        mTwitterService
                .getTrendingTopicWords(query)
                .continueWith(new Continuation<List<WordCloud>, Object>() {
                    @Override
                    public Object then(Task<List<WordCloud>> task) throws Exception {
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
