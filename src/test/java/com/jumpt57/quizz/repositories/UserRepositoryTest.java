package com.jumpt57.quizz.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.jumpt57.quizz.domain.Role;
import com.jumpt57.quizz.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/test_sql_init.sql")
public class UserRepositoryTest {

    private static final List<String> USER_NAMES = Arrays.asList("admin", "user1", "user2");

    @Autowired
    private UserRepository userRepository;

    @Test
    public void should_return_user_entity_from_username() {
        // given

        // when
        User found = userRepository.findByUsername(USER_NAMES.get(0));

        // then
        assertThat(found.getUsername()).isEqualTo(USER_NAMES.get(0));
    }

    @Test
    public void should_return_list_of_users_of_1_when_find_by_role_admin() {
        // given

        // when
        List<User> usersFound = userRepository.findByRole(Role.ADMINISTRATOR);

        // then
        assertThat(usersFound.size()).isEqualTo(1);
    }


    @Test
    public void should_return_list_of_users_of_1_when_find_by_role_admin_ordinal() {
        // given

        // when
        List<User> usersFound = userRepository.findByRole(Role.get(0));

        // then
        assertThat(usersFound.size()).isEqualTo(1);
    }

    @Test
    public void should_return_list_of_users_of_2_when_find_by_role_limited() {
        // given

        // when
        List<User> usersFound = userRepository.findByRole(Role.LIMITED);

        // then
        assertThat(usersFound.size()).isEqualTo(2);
    }

    @Test
    public void shhould_return_a_list_of_3_users_entity() {
        // given

        // when
        List<User> usersFound = userRepository.findAll();

        // then
        assertThat(usersFound.size()).isEqualTo(3);
    }

    @Test
    public void should_return_user_updated_after_update(){
        // given
        final String newUserName = USER_NAMES.get(0) + "_updated";
        User user = userRepository.findByUsername(USER_NAMES.get(0));
        final UUID userId = user.getId();

        // when
        user.setUsername(newUserName);
        userRepository.saveAndFlush(user);

        // then
        User userUpdated = userRepository.findById(userId);
        assertThat(userUpdated.getUsername()).isEqualTo(newUserName);
    }

}
