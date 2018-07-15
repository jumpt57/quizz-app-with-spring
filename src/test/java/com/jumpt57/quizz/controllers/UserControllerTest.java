package com.jumpt57.quizz.controllers;

import java.util.Collections;
import java.util.UUID;
import javax.servlet.http.Cookie;

import com.jumpt57.quizz.domain.Role;
import com.jumpt57.quizz.dto.user.AuthUserDto;
import com.jumpt57.quizz.dto.user.ReadUserDto;
import com.jumpt57.quizz.dto.user.WriteUserDto;
import com.jumpt57.quizz.security.TokenHelper;
import com.jumpt57.quizz.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final String READ_USER_NAME = "user1";
    private static final String READ_USER_UUID = "c4ad72c0-30f4-11e8-b467-0ed5f89f718b";

    private static final String ADMIN_USER_NAME = "admin";
    private static final String ADMIN_USER_PASSWORD = "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.";

    private static final String WRITE_USER_PASSWORD = "123456789";
    private static final String WRITE_USER_NAME = "myUserWellFormed";
    private static final String WRITE_USER_UUID = "05aaa196-30fd-11e8-b467-0ed5f89f718b";

    private static final String WRITE_USER_NAME_M = "myUserMalFormed";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private Cookie jwsCookie;

    @Before
    public void setUp() {
        jwsCookie = new Cookie("token", tokenHelper.generateToken(ADMIN_USER_NAME));

        Mockito.when(userService.getUserByUsername(READ_USER_NAME))
                .thenReturn(
                        ReadUserDto.builder()
                                .id(UUID.fromString(READ_USER_UUID))
                                .enabled(true)
                                .role(Role.ADMINISTRATOR.name())
                                .username(READ_USER_NAME)
                                .build()
                );

        Mockito.when(userService.loadUserByUsername(ADMIN_USER_NAME))
                .thenReturn(
                        AuthUserDto.builder()
                                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")))
                                .enabled(true)
                                .password(ADMIN_USER_PASSWORD)
                                .username(ADMIN_USER_NAME)
                                .build()

                );

        Mockito.when(userService.createUser(WriteUserDto.builder()
                .enabled(true)
                .password(WRITE_USER_PASSWORD)
                .role(Role.ADMINISTRATOR.ordinal())
                .username(WRITE_USER_NAME)
                .build())
        ).thenReturn(UUID.fromString(WRITE_USER_UUID));

        Mockito.when(userService.createUser(WriteUserDto.builder()
                .enabled(true)
                .role(Role.ADMINISTRATOR.ordinal())
                .username(WRITE_USER_NAME_M)
                .build())
        ).thenThrow(new RuntimeException());

    }

    @Test
    public void should_return_code_ok_form_get_user_by_username() throws Exception {
        // given

        // that
        MvcResult result = this.mockMvc.perform(get("/user/user1")
                .cookie(jwsCookie))
                .andExpect(status().isOk()).andReturn();

        final ReadUserDto userDto = objectMapper.readValue(result.getResponse().getContentAsString(), ReadUserDto.class);

        // then
        assertThat(userDto.getId().toString()).isEqualTo(READ_USER_UUID);
        assertThat(userDto.getUsername()).isEqualTo(READ_USER_NAME);
    }

    @Test
    public void should_return_code_ok_for_create_user_well_formed() throws Exception {
        // given
        JSONObject user = new JSONObject();
        user.put("enabled", true);
        user.put("password", WRITE_USER_PASSWORD);
        user.put("role", Role.ADMINISTRATOR.ordinal());
        user.put("username", WRITE_USER_NAME);

        // than
        MvcResult result = this.mockMvc.perform(post("/user")
                .cookie(jwsCookie)
                .content(user.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        final UUID userUuid = objectMapper.readValue(result.getResponse().getContentAsString(), UUID.class);

        // then
        assertThat(userUuid).isEqualTo(UUID.fromString(WRITE_USER_UUID));
    }

    @Test
    public void should_return_code_ko_for_create_user_malformed() throws Exception {
        // given
        JSONObject user = new JSONObject();
        user.put("enabled", true);
        user.put("password", "");
        user.put("role", Role.ADMINISTRATOR.ordinal());
        user.put("username", "myUserMalformed");

        // then
        this.mockMvc.perform(post("/user")
                .cookie(jwsCookie)
                .content(user.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
