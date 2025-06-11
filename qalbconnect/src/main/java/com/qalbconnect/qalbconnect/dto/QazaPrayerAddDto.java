package com.qalbconnect.qalbconnect.dto;

import jakarta.validation.constraints.Min;

/**
 * DTO for adding missed Qaza prayer counts.
 * This object will carry the number of prayers a user has missed
 * for each type (Fajr, Zuhr, etc.) to increment the outstanding Qaza count.
 */
public class QazaPrayerAddDto {

    // Using Integer wrappers as these might come as null from the form if not filled
    @Min(value = 0, message = "Fajr missed count cannot be negative")
    private Integer fajrMissed = 0; // Default to 0 if not provided

    @Min(value = 0, message = "Zuhr missed count cannot be negative")
    private Integer zuhrMissed = 0; // Default to 0 if not provided

    @Min(value = 0, message = "Asr missed count cannot be negative")
    private Integer asrMissed = 0; // Default to 0 if not provided

    @Min(value = 0, message = "Maghrib missed count cannot be negative")
    private Integer maghribMissed = 0; // Default to 0 if not provided

    @Min(value = 0, message = "Isha missed count cannot be negative")
    private Integer ishaMissed = 0; // Default to 0 if not provided

    @Min(value = 0, message = "Witr missed count cannot be negative")
    private Integer witrMissed = 0; // Default to 0 if not provided

    // Getters and Setters
    public Integer getFajrMissed() {
        return fajrMissed;
    }

    public void setFajrMissed(Integer fajrMissed) {
        this.fajrMissed = (fajrMissed != null) ? fajrMissed : 0;
    }

    public Integer getZuhrMissed() {
        return zuhrMissed;
    }

    public void setZuhrMissed(Integer zuhrMissed) {
        this.zuhrMissed = (zuhrMissed != null) ? zuhrMissed : 0;
    }

    public Integer getAsrMissed() {
        return asrMissed;
    }

    public void setAsrMissed(Integer asrMissed) {
        this.asrMissed = (asrMissed != null) ? asrMissed : 0;
    }

    public Integer getMaghribMissed() {
        return maghribMissed;
    }

    public void setMaghribMissed(Integer maghribMissed) {
        this.maghribMissed = (maghribMissed != null) ? maghribMissed : 0;
    }

    public Integer getIshaMissed() {
        return ishaMissed;
    }

    public void setIshaMissed(Integer ishaMissed) {
        this.ishaMissed = (ishaMissed != null) ? ishaMissed : 0;
    }

    public Integer getWitrMissed() {
        return witrMissed;
    }

    public void setWitrMissed(Integer witrMissed) {
        this.witrMissed = (witrMissed != null) ? witrMissed : 0;
    }
}
