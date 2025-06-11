package com.qalbconnect.qalbconnect.dto;

import com.qalbconnect.qalbconnect.model.QazaPrayerEntry;
import java.time.format.DateTimeFormatter;

public class QazaCalculatorResponseDto {
    private long totalCalendarDays; // Total days between dates, before gender-specific exclusions
    private String gender;
    private Integer averagePeriodDays;
    private Integer totalMonthlyCycles;
    private long excludedPeriodDays;
    private long finalMissedDaysForCalculation; // This is the base for Fajr, Zuhr, etc.

    private long fajrCount;
    private long zuhrCount;
    private long asrCount;
    private long maghribCount;
    private long ishaCount;
    private long witrCount;
    private String statusMessage;
    private String startDateString;
    private String endDateString;
    private String calculationTimestampString;

    // Default constructor
    public QazaCalculatorResponseDto() {
    }

    // Full constructor (useful for new calculations)
    public QazaCalculatorResponseDto(long totalCalendarDays, String gender, Integer averagePeriodDays,
                                     Integer totalMonthlyCycles, long excludedPeriodDays,
                                     long finalMissedDaysForCalculation, long fajrCount, long zuhrCount,
                                     long asrCount, long maghribCount, long ishaCount, long witrCount,
                                     String statusMessage, String startDateString, String endDateString,
                                     String calculationTimestampString) {
        this.totalCalendarDays = totalCalendarDays;
        this.gender = gender;
        this.averagePeriodDays = averagePeriodDays;
        this.totalMonthlyCycles = totalMonthlyCycles;
        this.excludedPeriodDays = excludedPeriodDays;
        this.finalMissedDaysForCalculation = finalMissedDaysForCalculation;
        this.fajrCount = fajrCount;
        this.zuhrCount = zuhrCount;
        this.asrCount = asrCount;
        this.maghribCount = maghribCount;
        this.ishaCount = ishaCount;
        this.witrCount = witrCount;
        this.statusMessage = statusMessage;
        this.startDateString = startDateString;
        this.endDateString = endDateString;
        this.calculationTimestampString = calculationTimestampString;
    }


    /**
     * Factory method to create a Response DTO from a QazaPrayerEntry entity.
     * --- Design Pattern: Factory Method ---
     * This static method acts as a simple factory for creating DTOs from entities.
     */
    public static QazaCalculatorResponseDto fromEntity(QazaPrayerEntry entry) {
        QazaCalculatorResponseDto dto = new QazaCalculatorResponseDto();
        dto.setTotalCalendarDays(entry.getTotalCalendarDays());
        dto.setGender(entry.getGender());
        dto.setAveragePeriodDays(entry.getAveragePeriodDays());
        dto.setTotalMonthlyCycles(entry.getTotalMonthlyCycles());
        dto.setExcludedPeriodDays(entry.getExcludedPeriodDays());
        dto.setFinalMissedDaysForCalculation(entry.getFinalMissedDaysForCalculation());
        dto.setFajrCount(entry.getFajrCount());
        dto.setZuhrCount(entry.getZuhrCount());
        dto.setAsrCount(entry.getAsrCount());
        dto.setMaghribCount(entry.getMaghribCount());
        dto.setIshaCount(entry.getIshaCount());
        dto.setWitrCount(entry.getWitrCount());

        // Set status message for loaded entities (can be customized)
        dto.setStatusMessage("Loaded previous calculation from " + entry.getCalculationTimestamp().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

        // Format dates as strings for display in frontend
        dto.setStartDateString(entry.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        dto.setEndDateString(entry.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        dto.setCalculationTimestampString(entry.getCalculationTimestamp().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        return dto;
    }

    // Getters and Setters for all fields

    public long getTotalCalendarDays() {
        return totalCalendarDays;
    }

    public void setTotalCalendarDays(long totalCalendarDays) {
        this.totalCalendarDays = totalCalendarDays;
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

    public long getExcludedPeriodDays() {
        return excludedPeriodDays;
    }

    public void setExcludedPeriodDays(long excludedPeriodDays) {
        this.excludedPeriodDays = excludedPeriodDays;
    }

    public long getFinalMissedDaysForCalculation() {
        return finalMissedDaysForCalculation;
    }

    public void setFinalMissedDaysForCalculation(long finalMissedDaysForCalculation) {
        this.finalMissedDaysForCalculation = finalMissedDaysForCalculation;
    }

    public long getFajrCount() {
        return fajrCount;
    }

    public void setFajrCount(long fajrCount) {
        this.fajrCount = fajrCount;
    }

    public long getZuhrCount() {
        return zuhrCount;
    }

    public void setZuhrCount(long zuhrCount) {
        this.zuhrCount = zuhrCount;
    }

    public long getAsrCount() {
        return asrCount;
    }

    public void setAsrCount(long asrCount) {
        this.asrCount = asrCount;
    }

    public long getMaghribCount() {
        return maghribCount;
    }

    public void setMaghribCount(long maghribCount) {
        this.maghribCount = maghribCount;
    }

    public long getIshaCount() {
        return ishaCount;
    }

    public void setIshaCount(long ishaCount) {
        this.ishaCount = ishaCount;
    }

    public long getWitrCount() {
        return witrCount;
    }

    public void setWitrCount(long witrCount) {
        this.witrCount = witrCount;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public String getCalculationTimestampString() {
        return calculationTimestampString;
    }

    public void setCalculationTimestampString(String calculationTimestampString) {
        this.calculationTimestampString = calculationTimestampString;
    }
}