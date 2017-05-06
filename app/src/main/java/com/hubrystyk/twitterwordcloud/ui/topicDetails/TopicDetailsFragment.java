package com.hubrystyk.twitterwordcloud.ui.topicDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment;
import com.hubrystyk.twitterwordcloud.R;
import com.hubrystyk.twitterwordcloud.services.ServiceProvider;

import net.alhazmy13.wordcloud.ColorTemplate;
import net.alhazmy13.wordcloud.WordCloud;
import net.alhazmy13.wordcloud.WordCloudView;

import java.util.ArrayList;
import java.util.List;

public class TopicDetailsFragment
        extends MvpLceFragment<WordCloudView, List<String>, TopicDetailsView, TopicDetailsPresenter>
        implements TopicDetailsView {
    public static final String TAG = "TopicDetailsFragment";

    @Override
    public TopicDetailsPresenter createPresenter() {
        return new TopicDetailsPresenter(ServiceProvider.getTwitterService());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.topic_details_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData(false);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadWordList();
    }

    @Override
    public void setData(List<String> data) {
        List<WordCloud> words = new ArrayList<>();
        int position = 0;
        for (String string : data) {
            words.add(new WordCloud(string, ++position));
        }
        contentView.setColors(ColorTemplate.MATERIAL_COLORS);
        contentView.setDataSet(words);
        contentView.notifyDataSetChanged();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }
}
