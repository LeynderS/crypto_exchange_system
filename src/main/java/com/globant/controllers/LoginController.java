package com.globant.controllers;

import com.globant.exceptions.InvalidCredentialsException;
import com.globant.exceptions.InvalidEmailException;
import com.globant.service.UserService;
import com.globant.views.ConsoleView;
 class LoginController {
    private final ConsoleView view;
    private final UserService userService;

    public LoginController(ConsoleView view, UserService userService) {
        this.view = view;
        this.userService = userService;
    }

    public void execute(){
        while(true){
            try{
                String email = view.getEmailInput();
                userService.validateEmail(email);
                String password = view.getPasswordInput();
                userService.login(email, password);
                view.showSuccessMessage("Login successful");
                break;
            }catch (InvalidEmailException e){
                view.showError("Invalid email. Please try again.");
            }catch (InvalidCredentialsException e){
                view.showError("Invalid credentials. Please try again.");
                break;
            }
        }

    }
}
