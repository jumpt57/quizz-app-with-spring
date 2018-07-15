package com.jumpt57.quizz.dto.quote;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuoteDto {

    String quoteText;

    String quoteAuthor;

    String senderName;

    String senderLink;

    String quoteLink;

}
