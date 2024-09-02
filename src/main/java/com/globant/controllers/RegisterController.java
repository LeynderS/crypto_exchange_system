package com.globant.controllers;

import com.globant.exceptions.EmailInUseException;
import com.globant.service.UserService;
import com.globant.views.ConsoleView;
import com.globant.exceptions.InvalidEmailException;

/**
 * RegisterController class is responsible for handling the registration process.
 */
class RegisterController {
    private final ConsoleView view;
    private final UserService userService;

    public RegisterController(ConsoleView view, UserService userService) {
        this.view = view;
        this.userService = userService;
    }

    public void execute(){
        while(true){
            try{
                String name = view.getNameInput();
                String email = view.getEmailInput();
                userService.validateEmail(email); // Check if email is in a correct format
                userService.emailInUse(email); // Check if email is already in use
                String password = view.getPasswordInput();
                do{
                    String confirmPassword = view.getPasswordConfirmation();
                    // Check if passwords match
                    if(!password.equals(confirmPassword)){
                        view.showError("Passwords do not match. Please repeat again.");
                        password = view.getPasswordInput();
                    }else{
                        break;
                    }
                } while (true);
                userService.registerUser(name, email, password); // Register user
                view.showSuccessMessage("Registration successful");
                break;
            }catch (InvalidEmailException e){
                view.showError("Invalid email. Please try again.");
            }catch (EmailInUseException e){
                view.showError("Email already in use. Please try again.");
            }
        }

    }
}
