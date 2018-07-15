package com.jumpt57.quizz.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;

import com.jumpt57.quizz.rest.AvatarRest;
import lombok.RequiredArgsConstructor;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final Logger logger;

    private final AvatarRest avatarRest;

    public Optional<InputStream> retrieveImageData() {

        logger.info("Get avatar from remote API");

        try(ResponseBody res = avatarRest.retrieveImageData(new Random().nextInt(1084)).execute().body()) {

            return Optional.ofNullable(res.byteStream());

        } catch (IOException ex) {
            logger.error(ex.getMessage());
            return Optional.empty();
        }

    }

}
