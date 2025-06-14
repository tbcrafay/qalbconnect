package com.qalbconnect.qalbconnect.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime; // Use LocalDateTime for more precise timestamps

@Entity
@Table(name = "qaza_prayer_entries") // Table to store individual Qaza prayer calculation entries
public class QazaPrayerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the User who made this calculation
    // Many prayer entries to one user (QazaPrayerEntry -> User)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column in qaza_prayer_entries table
    private User user; // The user associated with this qaza entry

    @Column(nullable = false)
    private LocalDate startDate; // When the user became baligh (input from user)

    @Column(nullable = false)
    private LocalDate endDate; // Current or desired ending date for calculation (input from user)

    @Column(nullable = false)
    private long totalCalendarDays; // Total days between dates, before gender-specific exclusions

    // New fields for gender-specific calculations
    @Column(nullable = false)
    private String gender; // "male" or "female"

    @Column // Can be null for male
    private Integer averagePeriodDays; // Nullable, only for female

    @Column // Can be null for male
    private Integer totalMonthlyCycles; // Nullable, only for female

    @Column(nullable = false)
    private long excludedPeriodDays; // 0 for male, calculated for female

    @Column(nullable = false)
    private long finalMissedDaysForCalculation; // This is the base for Fajr, Zuhr, etc.

    @Column(nullable = false)
    private long fajrCount;

    @Column(nullable = false)
    private long zuhrCount;

    @Column(nullable = false)
    private long asrCount;

    @Column(nullable = false)
    private long maghribCount;

    @Column(nullable = false)
    private long ishaCount;

    @Column(nullable = false)
    private long witrCount;

    @Column(nullable = false)
    private LocalDateTime calculationTimestamp; // Timestamp when this calculation was performed and saved

    // Default constructor is required by JPA
    public QazaPrayerEntry() {
    }

    // Comprehensive constructor for new entries
    public QazaPrayerEntry(User user, LocalDate startDate, LocalDate endDate, long totalCalendarDays,
                           String gender, Integer averagePeriodDays, Integer totalMonthlyCycles,
                           long excludedPeriodDays, long finalMissedDaysForCalculation,
                           long fajrCount, long zuhrCount, long asrCount,
                           long maghribCount, long ishaCount, long witrCount) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
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
        this.calculationTimestamp = LocalDateTime.now(); // Automatically set the timestamp
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public LocalDateTime getCalculationTimestamp() {
        return calculationTimestamp;
    }

    public void setCalculationTimestamp(LocalDateTime calculationTimestamp) {
        this.calculationTimestamp = calculationTimestamp;
    }
}