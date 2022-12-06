package com.example.aanaxee;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserServies {
    @POST("users/")
    Call<UserResponse> saveUser(@Body UserHelperClass userRequest);
}
