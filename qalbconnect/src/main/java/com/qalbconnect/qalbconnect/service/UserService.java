package com.qalbconnect.qalbconnect.service;

import com.qalbconnect.qalbconnect.model.User;
import com.qalbconnect.qalbconnect.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // Spring Security's interface
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList; // Used for authorities (no roles for now)

@Service // Marks this class as a Spring Service. By default, Spring manages it as a Singleton.
public class UserService implements UserDetailsService { // Implementing UserDetailsService for Spring Security

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor Injection: Spring will inject the UserRepository and BCryptPasswordEncoder instances.
    // Since UserService is a @Service, Spring manages its lifecycle as a Singleton.
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user with hashed password.
     * @param username The desired username.
     * @param password The raw password (will be hashed).
     * @param email The user's email address.
     * @return The registered User object, or null if registration fails (e.g., username/email already exists).
     */
    public User registerUser(String username, String password, String email) {
        // --- Design Pattern: Null Object (via Optional's effective handling) / Defensive Programming ---
        if (userRepository.existsByUsername(username)) {
            System.err.println("Registration failed: Username '" + username + "' already exists.");
            return null;
        }
        if (userRepository.existsByEmail(email)) {
            System.err.println("Registration failed: Email '" + email + "' already exists.");
            return null;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password)); // Hash the password!
        newUser.setRegistrationDate(LocalDateTime.now());

        return userRepository.save(newUser); // Save the new user to the database
    }

    /**
     * Loads user-specific data used by Spring Security for authentication.
     * This is the core method for the **Adapter Pattern**:
     * It adapts our `com.qalbconnect.qalbconnect.model.User` to Spring Security's `UserDetails` interface.
     * @param username The username of the user to load.
     * @return A UserDetails object representing the user.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Return a Spring Security UserDetails object.
        // Since you don't have roles for now, we can pass an empty list for authorities.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // This is the HASHED password from the DB
                new ArrayList<>() // No roles/authorities for now
        );
    }
}