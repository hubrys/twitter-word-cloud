package com.hubrystyk.twitterwordcloud.services;

import android.app.Application;
import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProvider {
    public static final String TAG = "ServiceProvider";

    private static Context sContext;
    private static TwitterApiService sTwitterApiService;

    public static void init(Application application) {
        sContext = application;
    }

    public static TwitterService getTwitterService() {
        return new TwitterService(getTwitterApiService());
    }

    private static TwitterApiService getTwitterApiService() {
        if (sTwitterApiService == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.twitter.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            sTwitterApiService = retrofit.create(TwitterApiService.class);
        }
        return sTwitterApiService;
    }
}
