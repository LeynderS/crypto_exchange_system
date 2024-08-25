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
        User user = new User(name, email, password);
        currentUser = user;
        userRepository.save(user);
    }

    public void login(String email, String password) {
        try{
            User user = userRepository.getUserByEmail(email);
            if (user.getPassword().equals(password)) currentUser = user;
            else throw new InvalidCredentialsException();
        }catch (UserNotFoundException e){
            throw new InvalidCredentialsException();
        }
    }

    public void logout(){
        if (currentUser != null) currentUser = null;
    }

    /**
     * Validates that the email is in the correct format
     * @param email the email to validate
     */
    public void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException();
        }
    }

    public void emailInUse(String email){
        try{
            userRepository.getUserByEmail(email);
            throw new EmailInUseException();
        }catch (UserNotFoundException e){
            // Do nothing
        }
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn(){
        return currentUser != null;
    }
}
