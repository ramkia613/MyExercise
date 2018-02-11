package com.ramki.myexercise.data.repository;

import android.arch.lifecycle.MutableLiveData;

import com.ramki.myexercise.BuildConfig;
import com.ramki.myexercise.data.model.Fact;

import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ramki on 2/11/2018.
 */
public class ApplicationRepository {
    private final int CONNECTION_TIMEOUT = 60;
    private ApplicationAPI applicationAPI;
    private static ApplicationRepository repository;

    private ApplicationRepository() {
        // Service log will be add for debug mode
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApplicationAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();
        applicationAPI = retrofit.create(ApplicationAPI.class);
    }

    public synchronized static ApplicationRepository getInstance() {
        if (repository == null) {
            if (repository == null) {
                repository = new ApplicationRepository();
            }
        }
        return repository;
    }

    public MutableLiveData<com.ramki.myexercise.data.model.Response> loadFact() {
        final MutableLiveData<com.ramki.myexercise.data.model.Response> factMutableLiveData = new MutableLiveData<>();
        applicationAPI.loadFact().enqueue(new Callback<Fact>() {
            @Override
            public void onResponse(Call<Fact> call, Response<Fact> response) {
                if (response.isSuccessful()) {
                    Fact fact = response.body();
                    factMutableLiveData.postValue(com.ramki.myexercise.data.model.Response.success(fact));
                } else {
                    if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        factMutableLiveData.postValue(com.ramki.myexercise.data.model.Response.error(null, new Throwable("Un authorized, please contact support team")));
                    } else {
                        String errorMessage = "Oops something went wrong!";
                        factMutableLiveData.postValue(com.ramki.myexercise.data.model.Response.error(null, new Throwable(errorMessage)));
                    }
                }

            }

            @Override
            public void onFailure(Call<Fact> call, Throwable t) {
                factMutableLiveData.postValue(com.ramki.myexercise.data.model.Response.error(null, new Throwable("Please check your internet connection!")));
            }
        });
        return factMutableLiveData;
    }
}
