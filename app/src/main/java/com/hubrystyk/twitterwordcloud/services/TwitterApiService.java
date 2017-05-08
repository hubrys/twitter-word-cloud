package com.hubrystyk.twitterwordcloud.services;


import com.hubrystyk.twitterwordcloud.responses.AuthResponse;
import com.hubrystyk.twitterwordcloud.responses.TrendingResponse;
import com.hubrystyk.twitterwordcloud.responses.TweetsResponse;

import java.util.List;

import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface TwitterApiService {
    @POST("/oauth2/token")
    Call<AuthResponse> getBearerToken(@Header("Authorization") String authHeader, @Body FormBody body);

    @Headers("Test: asdf")
    @GET("/1.1/trends/place.json?id=23424977")
    Call<List<TrendingResponse>> getTrendingTopics(@Header("Authorization") String bearerToken);

    @GET("/1.1/search/tweets.json")
    Call<TweetsResponse> getTweetsForQuery(@Header("Authorization") String bearerToken,
                                           @Query("q") String query);
}
