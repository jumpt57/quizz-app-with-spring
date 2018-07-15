package com.jumpt57.quizz.services;

import java.io.IOException;
import java.util.Random;
import org.slf4j.Logger;

import com.jumpt57.quizz.dto.quote.QuoteDto;
import com.jumpt57.quizz.rest.QuoteRest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final Logger logger;

    private final QuoteRest quoteRest;

    public String getQuote() {

        logger.info("Get quote from remote API");

        Call<QuoteDto> call = quoteRest.getQuote(new Random().nextInt(1000000));

        try {

            final Response<QuoteDto> res = call.execute();

            final QuoteDto quote = res.body();

            if(quote != null){
                return String.format("%s%s", quote.getQuoteText() , quote.getQuoteAuthor());
            }

            return null;

        } catch (IOException ex) {
            logger.error(ex.getMessage());
            return null;
        }

    }

}
