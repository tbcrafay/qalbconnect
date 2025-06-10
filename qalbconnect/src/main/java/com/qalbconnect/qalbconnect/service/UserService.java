package com.qalbconnect.qalbconnect.service;

import com.qalbconnect.qalbconnect.model.User;
import com.qalbconnect.qalbconnect.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional; // <--- Import Optional

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

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
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRegistrationDate(LocalDateTime.now());

        return userRepository.save(newUser);
    }

    /**
     * Loads user-specific data used by Spring Security for authentication.
     * @param username The username of the user to load.
     * @return A UserDetails object representing the user.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username) // This is now a call to UserRepository's method
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    /**
     * Finds a User entity by username.
     * This method is specifically for retrieving our domain User object.
     * --- Design Pattern: Null Object (via Optional) ---
     * Returns an Optional, forcing explicit handling of non-existence.
     * @param username The username to search for.
     * @return An Optional containing the User if found, or an empty Optional if not found.
     */
    public Optional<User> findUserByUsername(String username) { // <--- ADD THIS METHOD
        return userRepository.findByUsername(username); // Rely on UserRepository for actual lookup
    }
}