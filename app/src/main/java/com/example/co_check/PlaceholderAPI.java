package com.example.co_check;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaceholderAPI {

    @GET("indonesia")
    Call<List<DataPost>> getData();

}
