package com.jumpt57.quizz.config;

import com.jumpt57.quizz.rest.AvatarRest;
import com.jumpt57.quizz.rest.QuoteRest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Bean
    public QuoteRest quoteRest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.forismatic.com/api/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(QuoteRest.class);
    }

    @Bean
    public AvatarRest avatarRest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://picsum.photos")
                .build();

        return retrofit.create(AvatarRest.class);
    }

}
