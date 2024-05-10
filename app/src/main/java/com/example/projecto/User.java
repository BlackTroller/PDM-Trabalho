package com.example.projecto;

public class User {

    private final String username;
    private final int points;

    public User(String username, int points) {
        this.username = username;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", points=" + points +
                '}';
    }
}
