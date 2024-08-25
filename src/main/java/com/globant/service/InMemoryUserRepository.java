package com.globant.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.globant.models.User;

public class InMemoryUserRepository {
    private final Map<String, User> users = new HashMap<>();

    /**
     * Saves a user in the repository
     *
     * @param user the user to save
     */
    public void save(User user) {
        users.put(user.getEmail(), user);
    }

    /**
     * Retrieves a user by email
     *
     * @param email the email to search for
     * @return the user with the given email
     * @throws UserNotFoundException if the user is not found
     */
    public User getUserByEmail(String email) {
        return Optional.ofNullable(users.get(email)).orElseThrow(UserNotFoundException::new);
    }
}
