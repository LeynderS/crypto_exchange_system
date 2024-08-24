package com.globant.service;

import com.globant.models.User;

public class UserService {
    private final InMemoryUserRepository userRepository;
    private User currentUser;
    public UserService(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
        currentUser = null;
    }

    public void registerUser(String name, String email, String password) {
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


}
