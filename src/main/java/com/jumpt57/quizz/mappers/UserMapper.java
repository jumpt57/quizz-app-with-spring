package com.jumpt57.quizz.mappers;

import com.jumpt57.quizz.domain.Role;
import com.jumpt57.quizz.domain.User;
import com.jumpt57.quizz.dto.user.AuthUserDto;
import com.jumpt57.quizz.dto.user.ReadUserDto;
import com.jumpt57.quizz.dto.user.WriteUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { Role.class })
public interface UserMapper {

    @Mapping(expression = "java(user.getRole().name())", target = "role")
    ReadUserDto toReadUserDto(User user);

    @Mapping(expression = "java(user.getRole().ordinal())", target = "role")
    WriteUserDto toWriteUserDto(User user);

    @Mapping(target = "authorities", ignore = true)
    AuthUserDto toAuthUserDto(User user);

    @Mapping(expression = "java(Role.get(user.getRole()))", target = "role")
    User toUserEntity(WriteUserDto user);

    @Mapping(expression = "java(Role.get(user.getRole()))", target = "role")
    User toUserEntity(ReadUserDto user);

}
