package com.qalbconnect.qalbconnect.dto;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank; // For String validation
import jakarta.validation.constraints.Pattern; // For gender validation
import jakarta.validation.constraints.Min; // For numeric range validation
import jakarta.validation.constraints.Max; // For numeric range validation

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

    @NotBlank(message = "Gender cannot be empty")
    @Pattern(regexp = "male|female", message = "Gender must be 'male' or 'female'")
    private String gender; // "male" or "female"

    // These fields are optional (nullable in DB for male users), but validated if provided
    @Min(value = 3, message = "Average period days must be between 3 and 10")
    @Max(value = 10, message = "Average period days must be between 3 and 10")
    private Integer averagePeriodDays; // Can be null, only for female

    @Min(value = 0, message = "Total monthly cycles cannot be negative")
    private Integer totalMonthlyCycles; // Can be null, only for female

    // Constructors
    public QazaCalculatorRequestDto() {
        this.endDate = LocalDate.now(); // Sensible default for a calculator
    }

    public QazaCalculatorRequestDto(LocalDate startDate, LocalDate endDate, String gender,
                                   Integer averagePeriodDays, Integer totalMonthlyCycles) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.gender = gender;
        this.averagePeriodDays = averagePeriodDays;
        this.totalMonthlyCycles = totalMonthlyCycles;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAveragePeriodDays() {
        return averagePeriodDays;
    }

    public void setAveragePeriodDays(Integer averagePeriodDays) {
        this.averagePeriodDays = averagePeriodDays;
    }

    public Integer getTotalMonthlyCycles() {
        return totalMonthlyCycles;
    }

    public void setTotalMonthlyCycles(Integer totalMonthlyCycles) {
        this.totalMonthlyCycles = totalMonthlyCycles;
    }
}