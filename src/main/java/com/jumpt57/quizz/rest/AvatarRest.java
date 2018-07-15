package com.jumpt57.quizz.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AvatarRest {

    @GET("/200/200/")
    Call<ResponseBody> retrieveImageData(@Query("image") Integer image);

}
