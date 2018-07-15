package com.jumpt57.quizz.rest;

import com.jumpt57.quizz.dto.quote.QuoteDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface QuoteRest {

    @Headers("Content-Type: application/json")
    @GET("?method=getQuote&format=json&lang=en")
    Call<QuoteDto> getQuote(@Query("key") Integer key);

}
