package com.jumpt57.quizz.dto.user;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadUserDto {

    UUID id;

    String username;

    String role;

    Boolean enabled;

    String quote;

    String avatarUrl;

}
