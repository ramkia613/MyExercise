package com.ramki.myexercise.data.repository;

import com.ramki.myexercise.data.model.Fact;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ramki on 2/11/2018.
 */
public interface ApplicationAPI {
    String BASE_URL = "https://dl.dropboxusercontent.com";

    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<Fact> loadFact();

}
