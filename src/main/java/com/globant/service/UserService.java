package com.globant.service;

import com.globant.models.User;

import java.util.regex.Pattern;

public class UserService {
    private final InMemoryUserRepository userRepository;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private User currentUser;
    public UserService(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
        currentUser = null;
    }

    public void registerUser(String name, String email, String password) {
        validateEmail(email);
        if(userRepository.getUserByEmail(email) != null){
            throw new EmailInUseException();
        }
        User user = new User(name, email, password);
        userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.getUserByEmail(email);
        if (user.getPassword().equals(password)) {
            currentUser = user;
            return user;
        } else {
            throw new InvalidCredentialsException();
        }
    }

    public void logout(User user){
        if (currentUser != null) currentUser = null;
    }

    public void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidCredentialsException();
        }
    }
}
