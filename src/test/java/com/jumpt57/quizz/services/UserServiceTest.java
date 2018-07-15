package com.jumpt57.quizz.services;


import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;

import com.amazonaws.util.StringInputStream;
import com.jumpt57.quizz.domain.Role;
import com.jumpt57.quizz.domain.User;
import com.jumpt57.quizz.dto.user.ReadUserDto;
import com.jumpt57.quizz.dto.user.WriteUserDto;
import com.jumpt57.quizz.mappers.UserMapper;
import com.jumpt57.quizz.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String USER_NAME = "username";
    private static final String USER_PASSWORD = "qsdqs68sdcje57";
    private static final UUID USER_ID = UUID.fromString("96592598-f7e3-4d3b-a350-e76976853aad");
    private static final String S3_URL = "https://s3.eu-west-3.amazonaws.com/storage.application.com/571ac1ea-c0ae-4859-b335-e768098060ef";

    @Mock
    private Logger logger;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FileArchiveService fileArchiveService;

    @Mock
    private AvatarService avatarService;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp(){
        // given

        Mockito.mock(UserMapper.class);

        User user = new User();
        user.setUsername(USER_NAME);
        user.setPassword(USER_PASSWORD);
        user.setRole(Role.ADMINISTRATOR);
        user.setAvatarUrl(S3_URL);
        user.setEnabled(true);

        User userPersisted = new User();
        userPersisted.setId(USER_ID);
        userPersisted.setUsername(USER_NAME);
        userPersisted.setPassword(USER_PASSWORD);
        userPersisted.setRole(Role.ADMINISTRATOR);
        userPersisted.setAvatarUrl(S3_URL);
        userPersisted.setEnabled(true);

        try (InputStream ip = new StringInputStream("test")) {

            Mockito.when(avatarService.retrieveImageData()).thenReturn(Optional.of(ip));

            Mockito.when(fileArchiveService.createFile(Optional.of(ip).get(), USER_NAME))
                    .thenReturn(S3_URL);

        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }

        Mockito.when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(user);

        Mockito.when(userRepository.findByRole(Role.ADMINISTRATOR))
                .thenReturn(Collections.singletonList(user));

        Mockito.when(userRepository.save(user))
                .thenReturn(userPersisted);

        Mockito.when(passwordEncoder.encode(user.getPassword()))
                .thenReturn(USER_PASSWORD);
    }

    @Test
    public void should_return_read_user_dto_when_get_user_by_name() {
        // when
        ReadUserDto simpleUser = userService.getUserByUsername(USER_NAME);

        // then
        assertThat(simpleUser.getUsername()).isEqualTo(USER_NAME);
        assertThat(simpleUser.getRole()).isEqualTo(Role.ADMINISTRATOR.name());
    }

    @Test
    public void should_return_a_list_of_userwhen_get_users_by_role() {
        // when
        final List<ReadUserDto> adminUsers = userService.getUsersByRole(Role.ADMINISTRATOR.ordinal());
        final List<ReadUserDto> slimitedUsers = userService.getUsersByRole(Role.LIMITED.ordinal());

        // then
        assertThat(adminUsers.size()).isEqualTo(1);
        assertThat(slimitedUsers.size()).isEqualTo(0);
    }

    @Test
    public void should_save_a_write_user_dto_from_write_user_dto() {
        // when
        WriteUserDto writeUserDto = WriteUserDto.builder()
                .username(USER_NAME)
                .password(USER_PASSWORD)
                .role(Role.ADMINISTRATOR.ordinal())
                .enabled(true)
                .build();

        final UUID id = userService.createUser(writeUserDto);

        // then
        assertThat(id).isEqualTo(USER_ID);
    }

}
