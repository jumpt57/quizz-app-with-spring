package com.jumpt57.quizz.dto.user;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriteUserDto {

    @NotNull
    @Length(min = 5, max = 25)
    String username;


    @NotNull
    @Length(min = 7, max = 60)
    String password;

    @NotNull
    Integer role;

    @NotNull
    Boolean enabled;


}
