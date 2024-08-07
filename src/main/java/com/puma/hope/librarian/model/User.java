package com.puma.hope.librarian.model;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    Long id;

    @Email(message = "Invalid email!")
    String email;

    @Pattern(regexp = "[^ ]*", message = "There must be no space symbols in login!")
    @NotEmpty(message = "Login may not be empty!")
    String login;

    // Была мысль написать рандомайзер, предлагающий варианты логина
    // Это дело бэка, а не фронта, потому что проверять бы на уникальность,
    // прежде чем предлагать юзеру
    @NotEmpty(message = "Name may not be empty!")
    String name;

    @Past(message = "Birth date may not be in future!")
    LocalDate birthday;

    final Set<Long> friendsIds = new HashSet<>();
}