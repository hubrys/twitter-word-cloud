package com.hubrystyk.twitterwordcloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hubrystyk.twitterwordcloud.entities.TrendingTopic;
import com.hubrystyk.twitterwordcloud.ui.Navigator;
import com.hubrystyk.twitterwordcloud.ui.topicDetails.TopicDetailsFragment;
import com.hubrystyk.twitterwordcloud.ui.topicList.TrendingTopicsFragment;

public class MainActivity
        extends AppCompatActivity
        implements Navigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new TrendingTopicsFragment())
                    .commit();
        }
    }

    @Override
    public void showTopicDetails(TrendingTopic topic) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new TopicDetailsFragment())
                .addToBackStack(null)
                .commit();
    }
}
