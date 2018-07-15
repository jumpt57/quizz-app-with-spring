package com.jumpt57.quizz.domain;

import java.util.Arrays;

import com.jumpt57.quizz.exceptions.UnknownRoleException;

public enum Role {

    ADMINISTRATOR,
    LIMITED;

    public static Role get(Integer index) {
        return Arrays.asList(Role.values()).stream()
                .filter(role -> role.ordinal() == index)
                .findFirst()
                .orElseThrow(UnknownRoleException::new);

    }

    public static Role get(String name) {
        return Arrays.asList(Role.values()).stream()
                .filter(role -> role.name().equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(UnknownRoleException::new);

    }

}
