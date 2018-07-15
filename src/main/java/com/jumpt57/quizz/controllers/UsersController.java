package com.jumpt57.quizz.controllers;


import java.util.List;
import java.util.Optional;

import com.jumpt57.quizz.dto.user.ReadUserDto;
import com.jumpt57.quizz.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.*;

@RestController
@RequestMapping(value = UsersController.API_NAME)
@RequiredArgsConstructor
public class UsersController {

    static final String API_NAME = "/users";

    private final UserService userService;

    @GetMapping
    @ApiOperation(
            value = "Get all users",
            response = ResponseEntity.class,
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    protected ResponseEntity<List<ReadUserDto>> getSimpleUsers() {

        return Optional.ofNullable(userService.getUsers())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping(value = "/{role}")
    @ApiOperation(
            value = "Get all users by role",
            response = ResponseEntity.class,
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<ReadUserDto>> getSimpleUsersByProfile(
            @PathVariable(value = "role") Integer role) {


        return Optional.ofNullable(userService.getUsersByRole(role))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());

    }


}
