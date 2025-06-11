package com.qalbconnect.qalbconnect.controller;

import com.qalbconnect.qalbconnect.dto.QazaCalculatorRequestDto;
import com.qalbconnect.qalbconnect.dto.QazaCalculatorResponseDto;
import com.qalbconnect.qalbconnect.dto.QazaPrayerUpdateDto;
import com.qalbconnect.qalbconnect.dto.QazaPrayerAddDto; // NEW: Import new DTO
import com.qalbconnect.qalbconnect.service.QazaCalculatorService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/qazaumri")
public class QazaCalculatorController {

    private final QazaCalculatorService qazaCalculatorService;

    public QazaCalculatorController(QazaCalculatorService qazaCalculatorService) {
        this.qazaCalculatorService = qazaCalculatorService;
    }

    /**
     * Handles the GET request for the Qaza Umri Calculator page.
     * Displays the form for inputting dates and fetches past calculations for the logged-in user.
     */
    @GetMapping
    public String showQazaCalculatorPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        model.addAttribute("request", new QazaCalculatorRequestDto());
        model.addAttribute("response", new QazaCalculatorResponseDto());
        model.addAttribute("updateRequest", new QazaPrayerUpdateDto());
        model.addAttribute("addRequest", new QazaPrayerAddDto()); // NEW: Add add DTO for new form

        List<QazaCalculatorResponseDto> history = qazaCalculatorService.getQazaEntriesForUser(username);
        model.addAttribute("history", history);

        if (model.containsAttribute("statusMessage")) {
            model.addAttribute("statusMessage", model.getAttribute("statusMessage"));
            model.addAttribute("statusClass", model.getAttribute("statusClass"));
        }

        return "qazaCalculator";
    }

    /**
     * Handles the POST request for Qaza Umri calculation.
     */
    @PostMapping
    public String calculateAndSaveQazaPrayers(@ModelAttribute("request") @Valid QazaCalculatorRequestDto requestDto,
                                             BindingResult bindingResult,
                                             Model model,
                                             RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (bindingResult.hasErrors()) {
            model.addAttribute("response", new QazaCalculatorResponseDto());
            model.addAttribute("updateRequest", new QazaPrayerUpdateDto());
            model.addAttribute("addRequest", new QazaPrayerAddDto()); // Ensure addRequest is in model
            List<QazaCalculatorResponseDto> history = qazaCalculatorService.getQazaEntriesForUser(username);
            model.addAttribute("history", history);
            return "qazaCalculator";
        }

        QazaCalculatorResponseDto currentResponse = qazaCalculatorService.calculateAndSaveQazaPrayers(username, requestDto);

        redirectAttributes.addFlashAttribute("statusMessage", currentResponse.getStatusMessage());
        redirectAttributes.addFlashAttribute("statusClass", currentResponse.getStatusMessage().contains("Error") ? "error" : "success");

        return "redirect:/qazaumri";
    }

    /**
     * Handles the POST request for updating Qaza prayer counts (decreasing).
     */
    @PostMapping("/updatePrayers")
    public String updatePrayers(@ModelAttribute("updateRequest") @Valid QazaPrayerUpdateDto updateDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error ->
                redirectAttributes.addFlashAttribute("statusMessage", "Validation Error on Update: " + error.getDefaultMessage())
            );
            redirectAttributes.addFlashAttribute("statusClass", "error");
            return "redirect:/qazaumri";
        }

        QazaCalculatorResponseDto response = qazaCalculatorService.updateQazaPrayers(username, updateDto);

        redirectAttributes.addFlashAttribute("statusMessage", response.getStatusMessage());
        redirectAttributes.addFlashAttribute("statusClass", response.getStatusMessage().contains("Error") ? "error" : "success");

        return "redirect:/qazaumri";
    }

    /**
     * NEW METHOD: Handles the POST request for adding missed Qaza prayer counts (increasing).
     * This receives input from the add form, increments the latest Qaza entry,
     * saves a new entry, and redirects back to refresh the page.
     *
     * @param addDto Contains the number of prayers newly missed for each type.
     * @param bindingResult For validation errors on addDto.
     * @param redirectAttributes For passing flash messages after redirect.
     * @return Redirects to the main Qaza Calculator page.
     */
    @PostMapping("/addMissedPrayers") // Specific endpoint for adding missed prayers
    public String addMissedPrayers(@ModelAttribute("addRequest") @Valid QazaPrayerAddDto addDto,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error ->
                redirectAttributes.addFlashAttribute("statusMessage", "Validation Error on Adding Missed: " + error.getDefaultMessage())
            );
            redirectAttributes.addFlashAttribute("statusClass", "error");
            return "redirect:/qazaumri";
        }

        QazaCalculatorResponseDto response = qazaCalculatorService.addMissedQazaPrayers(username, addDto);

        redirectAttributes.addFlashAttribute("statusMessage", response.getStatusMessage());
        redirectAttributes.addFlashAttribute("statusClass", response.getStatusMessage().contains("Error") ? "error" : "success");

        return "redirect:/qazaumri";
    }
}
