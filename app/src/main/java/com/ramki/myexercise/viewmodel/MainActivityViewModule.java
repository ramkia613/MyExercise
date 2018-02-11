package com.ramki.myexercise.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.ramki.myexercise.data.model.Response;
import com.ramki.myexercise.data.repository.ApplicationRepository;

/**
 * Created by Ramki on 2/11/2018.
 */
public class MainActivityViewModule extends ViewModel {
    private ApplicationRepository repository;
    private MutableLiveData<Response> factLiveData;

    public MainActivityViewModule() {
        repository = ApplicationRepository.getInstance();
        if (factLiveData == null) {
            factLiveData = new MutableLiveData<>();
            refreshFact();
        }
    }

    public LiveData<Response> loadFact() {
        return factLiveData;
    }

    public LiveData<Response> refreshFact() {
        repository.loadFact().observeForever(new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                factLiveData.setValue(response);
            }
        });
        return factLiveData;
    }

}
