package com.jumpt57.quizz.repositories;

import java.util.List;
import java.util.UUID;

import com.jumpt57.quizz.domain.Role;
import com.jumpt57.quizz.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRole(Role role);

    User findByUsername(String login);

    User findById(UUID id);

}
