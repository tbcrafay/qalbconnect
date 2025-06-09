package com.qalbconnect.qalbconnect.controller;

import com.qalbconnect.qalbconnect.model.AsmaAlHusna;
import com.qalbconnect.qalbconnect.service.AsmaAlHusnaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller // Marks this class as a Spring MVC controller. This bean is a Singleton.
@RequestMapping("/namesofAllah") // Base mapping for all methods in this controller
public class NamesOfAllahController {

    private final AsmaAlHusnaService asmaAlHusnaService;

    // Constructor Injection: Spring injects the AsmaAlHusnaService instance (a Singleton)
    public NamesOfAllahController(AsmaAlHusnaService asmaAlHusnaService) {
        this.asmaAlHusnaService = asmaAlHusnaService;
    }

    /**
     * Handles the GET request for the /namesofAllah page.
     * Retrieves all 99 Names of Allah from the service and adds them to the model for display.
     * --- Design Pattern: Singleton ---
     * The `NamesOfAllahController` itself is a Singleton managed by Spring.
     * --- Design Pattern: Facade (Service Layer) ---
     * The controller interacts with the `AsmaAlHusnaService` which acts as a Facade to the underlying
     * data retrieval logic (from the repository), simplifying the controller's interaction.
     * --- Design Pattern: Command (Implicit) ---
     * This method implicitly acts as a "Display Names of Allah" command handler.
     */
    @GetMapping // Maps this method to GET requests for /namesofAllah (due to class-level @RequestMapping)
    public String displayNamesOfAllah(Model model) {
        List<AsmaAlHusna> names = asmaAlHusnaService.getAllNames();
        model.addAttribute("names", names); // Add the list of names to the model
        return "namesofAllah"; // Return the name of the Thymeleaf template (namesofAllah.html)
    }
}