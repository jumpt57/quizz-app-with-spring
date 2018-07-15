package com.jumpt57.quizz.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;

import com.jumpt57.quizz.domain.Role;
import com.jumpt57.quizz.domain.User;
import com.jumpt57.quizz.dto.user.AuthUserDto;
import com.jumpt57.quizz.dto.user.ReadUserDto;
import com.jumpt57.quizz.dto.user.WriteUserDto;
import com.jumpt57.quizz.mappers.UserMapper;
import com.jumpt57.quizz.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final Logger logger;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final QuoteService quoteService;

    private final AvatarService avatarService;

    private final FileArchiveService fileArchiveService;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public ReadUserDto getUserByUsername(String username) {
        logger.info("Get user by username for {}", username);

        return userMapper.toReadUserDto(userRepository.findByUsername(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Load user details for auth by username for {}", username);

        User user = userRepository.findByUsername(username);

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", user.getRole().name())));

        AuthUserDto authUserDto = userMapper.toAuthUserDto(user);
        authUserDto.setAuthorities(authorities);

        return authUserDto;
    }

    public List<ReadUserDto> getUsers() {
        logger.info("Get all users");

        return userRepository.findAll().stream()
                .map(userMapper::toReadUserDto)
                .peek(u -> u.setQuote(quoteService.getQuote()))
                .collect(Collectors.toList());
    }

    public List<ReadUserDto> getUsersByRole(Integer role) {
        logger.info("Get users by role for {}", role);

        return userRepository.findByRole(Role.get(role))
                .stream()
                .map(userMapper::toReadUserDto)
                .collect(Collectors.toList());
    }

    public UUID createUser(WriteUserDto user) {
        logger.info("Create user {}", user.getUsername());

        User userEntity = userMapper.toUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        avatarService.retrieveImageData()
                .ifPresent(file ->
                        userEntity.setAvatarUrl(fileArchiveService.createFile(file, user.getUsername()))
                );

        return userRepository.save(userEntity).getId();
    }

}
