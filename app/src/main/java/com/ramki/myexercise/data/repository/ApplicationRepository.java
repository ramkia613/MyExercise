package com.ramki.myexercise.data.repository;

import android.arch.lifecycle.LiveData;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ramki on 2/11/2018.
 */
public class ApplicationRepository {
    private ApplicationAPI applicationAPI;
    private static ApplicationRepository repository;

    private ApplicationRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApplicationAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
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

}
