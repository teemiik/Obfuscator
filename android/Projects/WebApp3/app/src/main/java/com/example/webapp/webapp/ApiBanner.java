package com.example.webapp.webapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiBanner {

    @GET
    Call<Banner> getBanner(@Url String path);

}
