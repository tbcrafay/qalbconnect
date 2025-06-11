package com.qalbconnect.qalbconnect.service;

import com.qalbconnect.qalbconnect.dto.QazaCalculatorRequestDto;
import com.qalbconnect.qalbconnect.dto.QazaCalculatorResponseDto;
import com.qalbconnect.qalbconnect.model.QazaPrayerEntry;
import com.qalbconnect.qalbconnect.model.User;
import com.qalbconnect.qalbconnect.repository.QazaPrayerEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QazaCalculatorService {

    private final QazaPrayerEntryRepository qazaPrayerEntryRepository;
    private final UserService userService;

    public QazaCalculatorService(QazaPrayerEntryRepository qazaPrayerEntryRepository, UserService userService) {
        this.qazaPrayerEntryRepository = qazaPrayerEntryRepository;
        this.userService = userService;
    }

    /**
     * Calculates the Qaza prayers based on the provided start and end dates, gender,
     * and female-specific period details, then persists the result to the database.
     *
     * @param username The username of the currently logged-in user.
     * @param requestDto Contains all input data (dates, gender, period details).
     * @return A QazaCalculatorResponseDto with calculated prayer counts and a status message.
     */
    @Transactional
    public QazaCalculatorResponseDto calculateAndSaveQazaPrayers(String username, QazaCalculatorRequestDto requestDto) {
        // Retrieve the User entity for the given username
        User user = userService.findUserByUsername(username)
                      .orElseThrow(() -> new RuntimeException("User not found: " + username));

        LocalDate startDate = requestDto.getStartDate();
        LocalDate endDate = requestDto.getEndDate();
        String gender = requestDto.getGender();
        Integer averagePeriodDays = requestDto.getAveragePeriodDays();
        Integer totalMonthlyCycles = requestDto.getTotalMonthlyCycles();

        // Initialize a response DTO for immediate display
        QazaCalculatorResponseDto response = new QazaCalculatorResponseDto();
        // Always set these for consistent display, even on error
        response.setStartDateString(startDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        response.setEndDateString(endDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        response.setGender(gender); // Set gender in response DTO for display

        // 1. Basic date validation
        if (endDate.isBefore(startDate)) {
            response.setStatusMessage("Error: Ending date cannot be before starting date!");
            // Set all counts to 0 in case of error
            response.setTotalCalendarDays(0);
            response.setFinalMissedDaysForCalculation(0);
            response.setExcludedPeriodDays(0);
            response.setFajrCount(0); response.setZuhrCount(0); response.setAsrCount(0);
            response.setMaghribCount(0); response.setIshaCount(0); response.setWitrCount(0);
            return response; // Return early with error message
        }

        long totalCalendarDays = ChronoUnit.DAYS.between(startDate, endDate) + 1; // Total days in the period

        long excludedPeriodDays = 0;
        long finalMissedDaysForCalculation = totalCalendarDays; // Default to total days for males

        // 2. Gender-specific calculation logic
        if ("female".equals(gender)) {
            // Validate female-specific inputs. DTO annotations provide basic validation,
            // but this adds a redundant check for robustness before calculation.
            if (averagePeriodDays == null || totalMonthlyCycles == null ||
                averagePeriodDays < 3 || averagePeriodDays > 10) {
                response.setStatusMessage("Error: For females, average period days (3-10) and total monthly cycles are required.");
                response.setTotalCalendarDays(totalCalendarDays); // Still show total days
                response.setFinalMissedDaysForCalculation(0);
                response.setExcludedPeriodDays(0);
                response.setFajrCount(0); response.setZuhrCount(0); response.setAsrCount(0);
                response.setMaghribCount(0); response.setIshaCount(0); response.setWitrCount(0);
                return response;
            }

            excludedPeriodDays = (long) totalMonthlyCycles * averagePeriodDays;
            finalMissedDaysForCalculation = totalCalendarDays - excludedPeriodDays;

            if (finalMissedDaysForCalculation < 0) {
                finalMissedDaysForCalculation = 0; // Ensure no negative missed days
            }

            response.setAveragePeriodDays(averagePeriodDays);
            response.setTotalMonthlyCycles(totalMonthlyCycles);
        } else {
            // For male users, explicitly set period related fields to null/0 in response DTO
            response.setAveragePeriodDays(null);
            response.setTotalMonthlyCycles(null);
            response.setExcludedPeriodDays(0); // Explicitly 0 for males
        }

        // 3. Calculate prayer counts based on final missed days
        long fajr = finalMissedDaysForCalculation;
        long zuhr = finalMissedDaysForCalculation;
        long asr = finalMissedDaysForCalculation;
        long maghrib = finalMissedDaysForCalculation;
        long isha = finalMissedDaysForCalculation;
        long witr = finalMissedDaysForCalculation;

        // 4. Populate the QazaPrayerEntry entity for persistence
        QazaPrayerEntry newEntry = new QazaPrayerEntry(
            user,
            startDate,
            endDate,
            totalCalendarDays,
            gender,
            averagePeriodDays,
            totalMonthlyCycles,
            excludedPeriodDays,
            finalMissedDaysForCalculation,
            fajr,
            zuhr,
            asr,
            maghrib,
            isha,
            witr
        );

        // 5. Persist the new entry to the database
        qazaPrayerEntryRepository.save(newEntry);

        // 6. Populate the response DTO with the calculated values and gender details
        response.setTotalCalendarDays(totalCalendarDays);
        response.setExcludedPeriodDays(excludedPeriodDays);
        response.setFinalMissedDaysForCalculation(finalMissedDaysForCalculation);
        response.setFajrCount(fajr);
        response.setZuhrCount(zuhr);
        response.setAsrCount(asr);
        response.setMaghribCount(maghrib);
        response.setIshaCount(isha);
        response.setWitrCount(witr);
        response.setStatusMessage("Calculation successful and saved!");
        response.setCalculationTimestampString(newEntry.getCalculationTimestamp().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))); // Set for current response too


        return response;
    }

    /**
     * Retrieves all QazaPrayerEntry records for a given user, ordered by most recent calculation.
     *
     * @param username The username of the user whose entries to retrieve.
     * @return A list of QazaCalculatorResponseDto, mapping from entity to DTO for presentation.
     */
    public List<QazaCalculatorResponseDto> getQazaEntriesForUser(String username) {
        User user = userService.findUserByUsername(username)
                      .orElseThrow(() -> new RuntimeException("User not found: " + username));
        List<QazaPrayerEntry> entries = qazaPrayerEntryRepository.findByUserOrderByCalculationTimestampDesc(user);

        // Convert entities to DTOs for the presentation layer
        return entries.stream()
                      .map(QazaCalculatorResponseDto::fromEntity)
                      .collect(Collectors.toList());
    }
}