package com.jumpt57.quizz.controllers;


import java.util.Arrays;
import java.util.Collections;
import javax.servlet.http.Cookie;

import com.jumpt57.quizz.domain.Role;
import com.jumpt57.quizz.domain.User;
import com.jumpt57.quizz.dto.user.AuthUserDto;
import com.jumpt57.quizz.dto.user.ReadUserDto;
import com.jumpt57.quizz.exceptions.UnknownRoleException;
import com.jumpt57.quizz.security.TokenHelper;
import com.jumpt57.quizz.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    private static final String USER_NAME = "user";
    private static final String USER_PASSWORD = "qsdqs68sdcje57";
    private static final String ADMIN_USER_NAME = "admin";
    private static final String ADMIN_USER_PASSWORD = "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenHelper tokenHelper;

    @MockBean
    private UserService userService;

    private Cookie jwsCookie;

    @Before
    public void setUp(){
        jwsCookie = new Cookie("token", tokenHelper.generateToken(ADMIN_USER_NAME));

        User user = new User();
        user.setUsername(USER_NAME);
        user.setPassword(USER_PASSWORD);
        user.setRole(Role.ADMINISTRATOR);
        user.setEnabled(true);

        ReadUserDto user1 = ReadUserDto.builder()
                .username(USER_NAME)
                .enabled(true)
                .role(Role.ADMINISTRATOR.name())
                .build();

        Mockito.when(userService.loadUserByUsername(ADMIN_USER_NAME))
                .thenReturn(
                        AuthUserDto.builder()
                                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")))
                                .enabled(true)
                                .password(ADMIN_USER_PASSWORD)
                                .username(ADMIN_USER_NAME)
                                .build()

                );

        Mockito.when(userService.getUsersByRole(Role.ADMINISTRATOR.ordinal()))
                .thenReturn(Arrays.asList(user1));

        Mockito.when(userService.getUsers())
                .thenReturn(Arrays.asList(user1));

        Mockito.when(userService.getUsersByRole(2))
                .thenThrow(new UnknownRoleException("No such role identifier"));
    }


    @Test
    public void should_return_code_ok_for_all_users() throws Exception{
        this.mockMvc.perform(get("/users").cookie(jwsCookie)).andExpect(status().isOk());
    }

    @Test
    public void should_return_code_ok_for_role_admin() throws Exception{
        this.mockMvc.perform(get("/users/0").cookie(jwsCookie)).andExpect(status().isOk());
    }

    @Test
    public void should_return_code_ko_for_role_unknown() throws Exception{
        this.mockMvc.perform(get("/users/2").cookie(jwsCookie)).andExpect(status().isBadRequest());
    }

}
