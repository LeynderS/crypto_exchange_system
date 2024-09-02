package com.globant.service;

import com.globant.exceptions.EmailInUseException;
import com.globant.exceptions.InvalidCredentialsException;
import com.globant.exceptions.InvalidEmailException;
import com.globant.exceptions.UserNotFoundException;
import com.globant.models.User;
import com.globant.repositories.InMemoryUserRepository;

import java.util.regex.Pattern;

public class UserService {
    private final InMemoryUserRepository userRepository;
    // Email regex pattern to validate email format
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private User currentUser;
    public UserService(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
        userRepository.save(new User("leynder", "leynder@gmail.com", "leynder"));
        userRepository.save(new User("as", "as@gmail.com", "as"));
        currentUser = null;
    }

    /**
     * Registers a new user
     * @param name the name of the user
     * @param email the email of the user
     * @param password the password of the user
     */
    public void registerUser(String name, String email, String password) {
        User user = new User(name, email, password);
        currentUser = user;
        userRepository.save(user);
    }

    /**
     * Logs in a user
     * @param email the email of the user
     * @param password the password of the user
     */
    public void login(String email, String password) {
        try{
            User user = userRepository.getUserByEmail(email);
            if (user.getPassword().equals(password)) currentUser = user;
            else throw new InvalidCredentialsException();
        }catch (UserNotFoundException e){
            throw new InvalidCredentialsException();
        }
    }

    // Logout the current user
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

    /**
     * Checks if the email is already in use
     * @param email the email to check
     */
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

    /**
     * Checks if a user is logged in
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn(){
        return currentUser != null;
    }
}
