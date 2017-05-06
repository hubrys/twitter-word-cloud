package com.hubrystyk.twitterwordcloud.ui.topicList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment;
import com.hubrystyk.twitterwordcloud.R;
import com.hubrystyk.twitterwordcloud.entities.TrendingTopic;
import com.hubrystyk.twitterwordcloud.services.ServiceProvider;
import com.hubrystyk.twitterwordcloud.ui.Navigator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrendingTopicsFragment
        extends MvpLceFragment<RecyclerView, List<TrendingTopic>, TrendingTopicsView, TrendingTopicsPresenter>
        implements TrendingTopicsView {
    public static final String TAG = "TrendingTopicsFragment";

    @BindView(R.id.contentView) RecyclerView mRecycler;

    private Adapter mAdapter;

    @Override
    public TrendingTopicsPresenter createPresenter() {
        return new TrendingTopicsPresenter(ServiceProvider.getTwitterService());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.trending_topics_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new Adapter();
        contentView.setAdapter(mAdapter);
        contentView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData(false);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public void setData(List<TrendingTopic> data) {
        mAdapter.setTopics(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadTrendingTopics(pullToRefresh);
    }

    static class Holder
            extends RecyclerView.ViewHolder {
        @BindView(R.id.name) TextView name;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class Adapter
            extends RecyclerView.Adapter<Holder> {
        private List<TrendingTopic> mTopics;

        public void setTopics(List<TrendingTopic> topics) {
            mTopics = topics;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mTopics == null ? 0 : mTopics.size();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.trending_topics_item, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(final Holder holder, int position) {
            TrendingTopic topic = mTopics.get(position);
            holder.name.setText(topic.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Navigator) getActivity()).showTopicDetails(
                            mTopics.get(holder.getAdapterPosition())
                    );
                }
            });
        }
    }
}
