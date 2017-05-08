package com.hubrystyk.twitterwordcloud.services;

import bolts.Task;
import bolts.TaskCompletionSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitToTask<Type>
        implements Callback<Type> {

    private final TaskCompletionSource<Type> mTask;

    public RetrofitToTask() {
        mTask = new TaskCompletionSource<Type>();
    }

    public Task<Type> getTask() {
        return mTask.getTask();
    }

    @Override
    public void onResponse(Call<Type> call, Response<Type> response) {
        if (response.isSuccessful()) {
            mTask.setResult(response.body());
        } else {
            mTask.setError(new Exception("Network Request Failed"));
        }
    }

    @Override
    public void onFailure(Call<Type> call, Throwable t) {
        mTask.setError(new Exception("Network Request Failed"));
    }

    public static <Type> Task<Type> fromCall(Call<Type> retrofitCall) {
        RetrofitToTask<Type> task = new RetrofitToTask<>();
        retrofitCall.enqueue(task);
        return task.getTask();
    }
}