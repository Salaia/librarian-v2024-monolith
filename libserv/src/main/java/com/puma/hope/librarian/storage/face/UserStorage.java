package com.puma.hope.librarian.storage.face;

import com.puma.hope.librarian.model.User;

import java.util.List;

public interface UserStorage {
    User create(User user);

    User update(User user);

    List<User> findAll();

    User findUserById(Long id);

    void addFriend(Long userId, Long friendId);

    List<User> findFriends(Long userId);

    List<User> findCommonFriends(Long userId, Long otherUserId);

    User removeFriend(Long userId, Long friendId);

    void checkUserExistence(Long id);
}
