package com.qalbconnect.qalbconnect.controller; // Assuming 'controller' package

import com.qalbconnect.qalbconnect.service.TasbeehService; // Import your TasbeehService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // This annotation is crucial for Spring to recognize it as a controller
public class TasbeehController { // Class name changed from TasbeehCounterController to TasbeehController

    private final TasbeehService tasbeehService;

    public TasbeehController(TasbeehService tasbeehService) { // Constructor name also updated
        this.tasbeehService = tasbeehService;
    }

    /**
     * Handles the GET request to display the Tasbeeh Counter page.
     * Accessible at /tasbeehcounter.
     */
    @GetMapping("/tasbeehcounter")
    public String showTasbeehCounter(Model model) {
        // Get the current count from the session-scoped TasbeehService
        model.addAttribute("currentCount", tasbeehService.getCurrentCountForDisplay());
        return "tasbeehCounter"; // Refers to src/main/resources/templates/tasbeehCounter.html
    }

    /**
     * Handles the POST request from the tasbeehCounter.html form when a button is clicked.
     * The 'action' parameter from the hidden input determines which service method to call.
     * Accessible at /tasbeehcounter.
     */
    @PostMapping("/tasbeehcounter")
    public String handleTasbeehAction(@RequestParam("action") String action) {
        switch (action) {
            case "increment":
                tasbeehService.increment();
                break;
            case "reset":
                tasbeehService.reset();
                break;
            case "undo":
                tasbeehService.undo();
                break;
            default:
                System.err.println("Unknown tasbeeh action received: " + action);
                break;
        }
        // Redirect back to the GET endpoint to refresh the page with the updated count
        return "redirect:/tasbeehcounter";
    }
}
