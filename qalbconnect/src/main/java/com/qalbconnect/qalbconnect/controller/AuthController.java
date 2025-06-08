package com.qalbconnect.qalbconnect.controller;

import com.qalbconnect.qalbconnect.dto.UserLoginDto;
import com.qalbconnect.qalbconnect.dto.UserRegistrationDto;
import com.qalbconnect.qalbconnect.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // Marks this class as a Spring MVC controller. This bean is a Singleton.
@RequestMapping // Base request mapping, can be empty or specific if all methods share a path
public class AuthController {

    private final UserService userService;

    // Constructor Injection: Spring injects the UserService instance (a Singleton)
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles the GET request for the application's root URL ("/").
     * This will display your main index page with login/register buttons.
     */
    @GetMapping("/")
    public String showIndexPage() {
        return "index"; // Returns the name of the Thymeleaf template (index.html)
    }

    /**
     * Handles the GET request for the registration page.
     * Displays the registration form.
     * --- Design Pattern: Command (Implicit / Potential Refinement) ---
     * This method implicitly acts as a "Show Registration Form" command handler.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto()); // Add empty DTO to bind form data
        return "register"; // Return the name of the Thymeleaf template (register.html)
    }

    /**
     * Handles the POST request for user registration.
     * Processes the submitted registration form data.
     * --- Design Pattern: Command (Implicit / Potential Refinement) ---
     * This method handles the "Register User" command.
     * --- Design Pattern: Facade (Service Layer) ---
     * The controller interacts with the `UserService` which acts as a Facade to the underlying
     * repository and business logic, simplifying the controller's interaction.
     * --- Design Pattern: Singleton ---
     * The `AuthController` itself is a Singleton managed by Spring.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserRegistrationDto registrationDto,
                               BindingResult result,
                               Model model) {
        // Check for validation errors from the DTO
        if (result.hasErrors()) {
            return "register"; // If errors, return to the registration form to display them
        }

        // Attempt to register the user via UserService
        if (userService.registerUser(registrationDto.getUsername(), registrationDto.getPassword(), registrationDto.getEmail()) == null) {
            // Handle case where registration failed (e.g., username/email already exists)
            model.addAttribute("registrationError", "Username or email already exists!");
            return "register"; // Return to the form with an error message
        }

        // Redirect to login page upon successful registration
        return "redirect:/login?registered";
    }

    /**
     * Handles the GET request for the login page.
     * Displays the login form.
     * --- Design Pattern: Command (Implicit / Potential Refinement) ---
     * This method implicitly acts as a "Show Login Form" command handler.
     */
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        // Spring Security will handle the actual login POST.
        // We just provide the model attribute for the form.
        model.addAttribute("user", new UserLoginDto());
        return "login"; // Return the name of the Thymeleaf template (login.html)
    }

    /**
     * Handles the GET request for the home page.
     * This page requires authentication (configured in SecurityConfig).
     * This is where the user will be redirected after successful login.
     * It should contain the logout functionality (link/button).
     */
    @GetMapping("/home")
    public String home() {
        return "home"; // Return the name of the Thymeleaf template (home.html)
    }
}