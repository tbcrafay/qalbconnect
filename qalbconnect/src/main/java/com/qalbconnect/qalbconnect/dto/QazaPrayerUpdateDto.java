package com.qalbconnect.qalbconnect.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for updating Qaza prayer counts.
 * This object will carry the number of prayers a user has performed
 * for each type (Fajr, Zuhr, etc.) to decrement the outstanding Qaza count.
 */
public class QazaPrayerUpdateDto {

    // Using Integer wrappers as these might come as null from the form if not filled
    @Min(value = 0, message = "Fajr count cannot be negative")
    private Integer fajrPrayed = 0; // Default to 0 if not provided

    @Min(value = 0, message = "Zuhr count cannot be negative")
    private Integer zuhrPrayed = 0; // Default to 0 if not provided

    @Min(value = 0, message = "Asr count cannot be negative")
    private Integer asrPrayed = 0; // Default to 0 if not provided

    @Min(value = 0, message = "Maghrib count cannot be negative")
    private Integer maghribPrayed = 0; // Default to 0 if not provided

    @Min(value = 0, message = "Isha count cannot be negative")
    private Integer ishaPrayed = 0; // Default to 0 if not provided

    @Min(value = 0, message = "Witr count cannot be negative")
    private Integer witrPrayed = 0; // Default to 0 if not provided

    // Getters and Setters
    public Integer getFajrPrayed() {
        return fajrPrayed;
    }

    public void setFajrPrayed(Integer fajrPrayed) {
        this.fajrPrayed = (fajrPrayed != null) ? fajrPrayed : 0; // Ensure non-null, default to 0
    }

    public Integer getZuhrPrayed() {
        return zuhrPrayed;
    }

    public void setZuhrPrayed(Integer zuhrPrayed) {
        this.zuhrPrayed = (zuhrPrayed != null) ? zuhrPrayed : 0;
    }

    public Integer getAsrPrayed() {
        return asrPrayed;
    }

    public void setAsrPrayed(Integer asrPrayed) {
        this.asrPrayed = (asrPrayed != null) ? asrPrayed : 0;
    }

    public Integer getMaghribPrayed() {
        return maghribPrayed;
    }

    public void setMaghribPrayed(Integer maghribPrayed) {
        this.maghribPrayed = (maghribPrayed != null) ? maghribPrayed : 0;
    }

    public Integer getIshaPrayed() {
        return ishaPrayed;
    }

    public void setIshaPrayed(Integer ishaPrayed) {
        this.ishaPrayed = (ishaPrayed != null) ? ishaPrayed : 0;
    }

    public Integer getWitrPrayed() {
        return witrPrayed;
    }

    public void setWitrPrayed(Integer witrPrayed) {
        this.witrPrayed = (witrPrayed != null) ? witrPrayed : 0;
    }
}
