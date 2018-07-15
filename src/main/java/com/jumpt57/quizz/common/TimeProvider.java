package com.jumpt57.quizz.common;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TimeProvider implements Serializable {

    public Date now(){
        return new Date();
    }

}