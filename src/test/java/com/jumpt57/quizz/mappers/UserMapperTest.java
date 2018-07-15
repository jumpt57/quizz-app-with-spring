package com.jumpt57.quizz.mappers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.jumpt57.quizz.domain.Role;
import com.jumpt57.quizz.domain.User;
import com.jumpt57.quizz.dto.user.ReadUserDto;
import com.jumpt57.quizz.dto.user.WriteUserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
public class UserMapperTest {

    private static final String USER_NAME = "user";
    private static final String USER_PASSWORD = "qsdqs68sdcje57";

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void should_map_user_entity_to_read_user_dto() {
        // given
        User user = new User();
        user.setUsername(USER_NAME);
        user.setPassword(USER_PASSWORD);
        user.setRole(Role.ADMINISTRATOR);

        // when
        ReadUserDto readUserDto = userMapper.toReadUserDto(user);

        // then
        assertThat(readUserDto.getRole()).isEqualTo(user.getRole().name());
    }

    @Test
    public void should_map_user_entity_to_write_user_dto() {
        // given
        User user = new User();
        user.setUsername(USER_NAME);
        user.setPassword(USER_PASSWORD);
        user.setRole(Role.ADMINISTRATOR);

        // when
        WriteUserDto writeUserDto = userMapper.toWriteUserDto(user);

        // then
        assertThat(writeUserDto.getUsername()).isEqualTo(user.getUsername());
        assertThat(writeUserDto.getPassword()).isEqualTo(user.getPassword());
        assertThat(writeUserDto.getEnabled()).isEqualTo(user.getEnabled());
        assertThat(writeUserDto.getRole()).isEqualTo(user.getRole().ordinal());
    }

    @Test
    public void should_map_list_of_user_entities_to_read_user_dto() {
        // given
        User user1 = new User();
        user1.setUsername(USER_NAME);
        user1.setPassword(USER_PASSWORD);
        user1.setRole(Role.ADMINISTRATOR);

        User user2 = new User();
        user2.setUsername(USER_NAME + "2");
        user2.setPassword(USER_PASSWORD);
        user2.setRole(Role.ADMINISTRATOR);

        List<User> users = Arrays.asList(user1, user2);

        // when
        List<ReadUserDto> usersDto = users.stream()
                .map(userMapper::toReadUserDto)
                .collect(Collectors.toList());

        // then
        assertThat(usersDto.size()).isEqualTo(users.size());
    }

    @Test
    public void should_map_read_user_dto_to_user_entity(){
        // given
        ReadUserDto readUserDto = ReadUserDto.builder()
                .username(USER_NAME)
                .enabled(true)
                .role(Role.ADMINISTRATOR.name())
                .build();

        // when
        User userEntity = userMapper.toUserEntity(readUserDto);

        // then
        assertThat(userEntity.getUsername()).isEqualTo(readUserDto.getUsername());
        assertThat(userEntity.getEnabled()).isEqualTo(readUserDto.getEnabled());
        assertThat(userEntity.getRole().name()).isEqualTo(readUserDto.getRole());
    }

    @Test
    public void should_map_write_user_dto_to_user_entity(){
        // given
        WriteUserDto writeUserDto = WriteUserDto.builder()
                .username(USER_NAME)
                .password(USER_PASSWORD)
                .enabled(true)
                .role(Role.ADMINISTRATOR.ordinal())
                .build();

        // when
        User userEntity = userMapper.toUserEntity(writeUserDto);

        // then
        assertThat(userEntity.getUsername()).isEqualTo(writeUserDto.getUsername());
        assertThat(userEntity.getPassword()).isEqualTo(writeUserDto.getPassword());
        assertThat(userEntity.getEnabled()).isEqualTo(writeUserDto.getEnabled());
        assertThat(userEntity.getRole().ordinal()).isEqualTo(writeUserDto.getRole());
    }
}
