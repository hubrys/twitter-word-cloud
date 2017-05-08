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

import java.util.List;

public class TopicDetailsFragment
        extends MvpLceFragment<WordCloudView, List<WordCloud>, TopicDetailsView, TopicDetailsPresenter>
        implements TopicDetailsView {
    public static final String TAG = "TopicDetailsFragment";

    private String mQuery;

    public static TopicDetailsFragment create(String query) {
        Bundle args = new Bundle();
        args.putString("query", query);
        TopicDetailsFragment fragment = new TopicDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public TopicDetailsPresenter createPresenter() {
        return new TopicDetailsPresenter(ServiceProvider.getTwitterService());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuery = (String) getArguments().getSerializable("query");
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
        getPresenter().loadWordList(mQuery);
    }

    @Override
    public void setData(final List<WordCloud> data) {
        contentView.setColors(ColorTemplate.MATERIAL_COLORS);
        contentView.setDataSet(data);
        contentView.notifyDataSetChanged();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }
}
