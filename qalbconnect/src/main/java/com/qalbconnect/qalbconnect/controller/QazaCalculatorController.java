package com.qalbconnect.qalbconnect.controller;

import com.qalbconnect.qalbconnect.dto.QazaCalculatorRequestDto;
import com.qalbconnect.qalbconnect.dto.QazaCalculatorResponseDto;
import com.qalbconnect.qalbconnect.service.QazaCalculatorService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication; // Import for getting current user details
import org.springframework.security.core.context.SecurityContextHolder; // Import for getting security context
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller // Marks this class as a Spring MVC controller. This bean is a Singleton.
@RequestMapping("/qazaumri") // Base mapping for all methods in this controller
public class QazaCalculatorController {

    private final QazaCalculatorService qazaCalculatorService;

    // Constructor Injection: Spring injects the QazaCalculatorService instance (a Singleton)
    public QazaCalculatorController(QazaCalculatorService qazaCalculatorService) {
        this.qazaCalculatorService = qazaCalculatorService;
    }

    /**
     * Handles the GET request for the Qaza Umri Calculator page.
     * Displays the form for inputting dates and fetches past calculations for the logged-in user.
     * --- Design Pattern: Singleton ---
     * The `QazaCalculatorController` itself is a Singleton.
     * --- Design Pattern: Command (Implicit) ---
     * This method acts as a "Show Qaza Calculator Form with History" command.
     */
    @GetMapping
    public String showQazaCalculatorPage(Model model) {
        // Get the currently logged-in user's username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // This gives us the username

        // Initialize DTOs for the current calculation form and its result display
        model.addAttribute("request", new QazaCalculatorRequestDto());
        model.addAttribute("response", new QazaCalculatorResponseDto()); // Empty response for initial load

        // Fetch and add historical calculations for the logged-in user
        List<QazaCalculatorResponseDto> history = qazaCalculatorService.getQazaEntriesForUser(username);
        model.addAttribute("history", history); // Add the list of past entries to the model

        return "qazaCalculator"; // Return the name of the Thymeleaf template (qazaCalculator.html)
    }

    /**
     * Handles the POST request for Qaza Umri calculation.
     * Processes the submitted date inputs, performs the calculation, saves it,
     * and re-displays the form with the new results and updated history.
     * --- Design Pattern: Facade (Service Layer) ---
     * The controller interacts with the `QazaCalculatorService` which acts as a Facade
     * to the core calculation and persistence logic.
     * --- Design Pattern: Command (Implicit) ---
     * This method acts as a "Calculate and Save Qaza Prayers" command.
     * --- Design Pattern: Data Transfer Object (DTO) ---
     * `QazaCalculatorRequestDto` and `QazaCalculatorResponseDto` are used for clean data transfer.
     */
    @PostMapping
    public String calculateAndSaveQazaPrayers(@ModelAttribute("request") @Valid QazaCalculatorRequestDto requestDto,
                                             BindingResult bindingResult,
                                             Model model) {

        // Get the currently logged-in user's username for saving the entry
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Check for validation errors on the request DTO
        if (bindingResult.hasErrors()) {
            // If there are validation errors, re-fetch history and return to the form
            model.addAttribute("response", new QazaCalculatorResponseDto()); // Keep response empty or previous
            List<QazaCalculatorResponseDto> history = qazaCalculatorService.getQazaEntriesForUser(username);
            model.addAttribute("history", history);
            return "qazaCalculator";
        }

        // Perform calculation and save using the service
        QazaCalculatorResponseDto currentResponse = qazaCalculatorService.calculateAndSaveQazaPrayers(username, requestDto);

        // Add both request and the new current response DTOs to the model for display
        model.addAttribute("request", requestDto); // Keep the entered dates in the form
        model.addAttribute("response", currentResponse); // Show the result of the latest calculation

        // Fetch and add updated historical calculations after saving
        List<QazaCalculatorResponseDto> updatedHistory = qazaCalculatorService.getQazaEntriesForUser(username);
        model.addAttribute("history", updatedHistory);

        return "qazaCalculator"; // Return the same template to display results and updated history
    }
}