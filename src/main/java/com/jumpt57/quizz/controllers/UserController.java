package com.jumpt57.quizz.controllers;

import java.util.UUID;
import javax.validation.Valid;

import com.jumpt57.quizz.dto.user.ReadUserDto;
import com.jumpt57.quizz.dto.user.WriteUserDto;
import com.jumpt57.quizz.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.*;

@RestController
@RequestMapping(value = UserController.API_NAME)
@RequiredArgsConstructor
public class UserController {

    static final String API_NAME = "/user";

    private final UserService userService;

    @GetMapping(value = "/{login}")
    @ApiOperation(
            value = "Get user by login",
            response = ResponseEntity.class,
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ReadUserDto> getSimpleUserByLogin(
            @PathVariable(value = "login") String login) {
        return new ResponseEntity<>(userService.getUserByUsername(login), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(
            value = "Create user",
            response = ResponseEntity.class,
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<UUID> createUser(@Valid @RequestBody WriteUserDto user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }


}
