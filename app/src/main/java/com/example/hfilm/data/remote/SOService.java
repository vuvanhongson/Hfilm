package com.example.hfilm.data.remote;

import android.content.Intent;

import com.example.hfilm.data.model.SOAnswersResponse;
import com.example.hfilm.data.model.SOAnswersResponseLogin;
import com.example.hfilm.data.model.SOAnswersResponseRegister;
import com.example.hfilm.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SOService {

    @FormUrlEncoded
    @Headers("app_token: dCuW7UQMbdvpcBDfzolAOSGFIcAec11a")
    @POST("user/registry")
    Call<SOAnswersResponseLogin> postRegister (@Field("full_name") String full_name, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @Headers("app_token: dCuW7UQMbdvpcBDfzolAOSGFIcAec11a")
    @POST("user/social-login")
    Call<SOAnswersResponseLogin> postLoginFaceboook (@Field("full_name") String full_name,
                                                     @Field("email") String email,
                                                     @Field("facebook_id") String password);


    @FormUrlEncoded
    @Headers("app_token: dCuW7UQMbdvpcBDfzolAOSGFIcAec11a")
    @POST("user/login")
    Call<SOAnswersResponseLogin> postLogin (@Field("email") String email, @Field("password") String pass);

    @Headers("app_token: dCuW7UQMbdvpcBDfzolAOSGFIcAec11a")
    @GET("movie/list?page=1&per_page=10")
    Call<SOAnswersResponse> getAnswers();

    @Headers("app_token: dCuW7UQMbdvpcBDfzolAOSGFIcAec11a")
    @GET("movie/list")
    Call<SOAnswersResponse> getAnswers2(@Query("page") String page, @Query("per_page") String per_page);



}

