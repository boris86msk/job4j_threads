package ru.job4j.pool;

public class UserPool {
    private String userName;
    private String email;

    public UserPool(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
