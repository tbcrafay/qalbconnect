package com.qalbconnect.qalbconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // Good practice to explicitly name your table, often plural
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing ID
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100) // Store hashed password, so length should be sufficient
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    // Default constructor is required by JPA
    public User() {
        this.registrationDate = LocalDateTime.now(); // Set current time on creation
    }

    // Constructor for convenience (optional, but good for testing/init)
    public User(String username, String password, String email) {
        this(); // Calls the default constructor to set registrationDate
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    // Usually, you wouldn't set registration date manually after creation,
    // but a setter is sometimes included for ORM frameworks.
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", registrationDate=" + registrationDate +
               '}';
    }
}