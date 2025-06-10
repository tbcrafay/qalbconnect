package com.qalbconnect.qalbconnect.dto;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class QazaCalculatorRequestDto {

    @NotNull(message = "Start date cannot be empty")
    @PastOrPresent(message = "Start date cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Format expected from HTML date input
    private LocalDate startDate;

    @NotNull(message = "End date cannot be empty")
    @PastOrPresent(message = "End date cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Format expected from HTML date input
    private LocalDate endDate;

    // Constructors
    public QazaCalculatorRequestDto() {
        // Default to current date for endDate for convenience if desired, or leave null for user input
        this.endDate = LocalDate.now(); // Sensible default for a calculator
    }

    public QazaCalculatorRequestDto(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}