package smt.ort.houses.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import smt.ort.houses.model.LoginBodyRequest;

public interface LoginRestService {

    @POST("login/")
    Call<Void> login(@Body LoginBodyRequest body);

}
