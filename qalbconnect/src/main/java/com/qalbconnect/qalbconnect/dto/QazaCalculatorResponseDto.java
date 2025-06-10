package com.qalbconnect.qalbconnect.dto;

import com.qalbconnect.qalbconnect.model.QazaPrayerEntry;
import java.time.format.DateTimeFormatter;

public class QazaCalculatorResponseDto {
    private long totalDays;
    private long fajrCount;
    private long zuhrCount;
    private long asrCount;
    private long maghribCount;
    private long ishaCount;
    private long witrCount;
    private String statusMessage;
    private String startDateString;
    private String endDateString;
    private String calculationTimestampString; // <--- ADD THIS NEW FIELD

    // Constructors (adjust if you have a full constructor for all fields)
    public QazaCalculatorResponseDto() {
    }

    // You might want to update this constructor if you use it to initialize all fields
    public QazaCalculatorResponseDto(long totalDays, long fajrCount, long zuhrCount, long asrCount,
                                     long maghribCount, long ishaCount, long witrCount, String statusMessage) {
        this.totalDays = totalDays;
        this.fajrCount = fajrCount;
        this.zuhrCount = zuhrCount;
        this.asrCount = asrCount;
        this.maghribCount = maghribCount;
        this.ishaCount = ishaCount;
        this.witrCount = witrCount;
        this.statusMessage = statusMessage;
    }

    // Factory method (Update this method)
    public static QazaCalculatorResponseDto fromEntity(QazaPrayerEntry entry) {
        QazaCalculatorResponseDto dto = new QazaCalculatorResponseDto();
        dto.setTotalDays(entry.getTotalDays());
        dto.setFajrCount(entry.getFajrCount());
        dto.setZuhrCount(entry.getZuhrCount());
        dto.setAsrCount(entry.getAsrCount());
        dto.setMaghribCount(entry.getMaghribCount());
        dto.setIshaCount(entry.getIshaCount());
        dto.setWitrCount(entry.getWitrCount());
        dto.setStatusMessage("Loaded previous calculation from " + entry.getCalculationTimestamp().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        dto.setStartDateString(entry.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        dto.setEndDateString(entry.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        dto.setCalculationTimestampString(entry.getCalculationTimestamp().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))); // <--- SET NEW FIELD
        return dto;
    }

    // New Getter and Setter for calculationTimestampString
    public String getCalculationTimestampString() {
        return calculationTimestampString;
    }

    public void setCalculationTimestampString(String calculationTimestampString) {
        this.calculationTimestampString = calculationTimestampString;
    }

    // ... (rest of your existing getters and setters)
    public long getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(long totalDays) {
        this.totalDays = totalDays;
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
}