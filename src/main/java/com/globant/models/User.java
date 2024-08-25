package com.globant.models;

public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private Wallet wallet;

    public User(String name, String email, String password) {
        this.userId = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.wallet = new Wallet();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
