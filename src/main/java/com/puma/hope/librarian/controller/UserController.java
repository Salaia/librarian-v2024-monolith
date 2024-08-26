package com.puma.hope.librarian.controller;

import com.puma.hope.librarian.model.Book;
import com.puma.hope.librarian.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.puma.hope.librarian.model.User;
import com.puma.hope.librarian.service.UserService;

import java.util.*;

@RestController
@Tag(name = "Users", description = "Requests for users")
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {
    UserService userService;
    BookService bookService;

    @PostMapping
    @Operation(summary = "Create user")
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") @Positive Long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") @Positive Long userId,
                          @PathVariable("friendId") Long friendId) {
        return userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable("id") @Positive Long userId,
                             @PathVariable("friendId") Long friendId) {
        return userService.removeFriend(userId, friendId);
    }

    @GetMapping("{id}/friends")
    public List<User> findFriends(@PathVariable("id") @Positive Long userId) {
        return userService.findFriends(userId);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable("id") @Positive Long userId,
                                        @PathVariable("otherId") @Positive Long otherUserId) {
        return userService.findCommonFriends(userId, otherUserId);
    }

    @GetMapping("/{id}/recommendations")
    public List<Book> recommendBooks(@PathVariable(value = "id") Long userId) {
        return bookService.getRecommendBooks(userId);
    }
}
