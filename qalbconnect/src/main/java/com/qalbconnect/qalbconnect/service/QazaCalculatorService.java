package com.qalbconnect.qalbconnect.service;

import com.qalbconnect.qalbconnect.dto.QazaCalculatorRequestDto;
import com.qalbconnect.qalbconnect.dto.QazaCalculatorResponseDto;
import com.qalbconnect.qalbconnect.dto.QazaPrayerUpdateDto;
import com.qalbconnect.qalbconnect.dto.QazaPrayerAddDto; // NEW: Import the new Add DTO
import com.qalbconnect.qalbconnect.model.QazaPrayerEntry;
import com.qalbconnect.qalbconnect.model.User;
import com.qalbconnect.qalbconnect.repository.QazaPrayerEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

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
        User user = userService.findUserByUsername(username)
                      .orElseThrow(() -> new RuntimeException("User not found: " + username));

        LocalDate startDate = requestDto.getStartDate();
        LocalDate endDate = requestDto.getEndDate();
        String gender = requestDto.getGender();
        Integer averagePeriodDays = requestDto.getAveragePeriodDays();
        Integer totalMonthlyCycles = requestDto.getTotalMonthlyCycles();

        QazaCalculatorResponseDto response = new QazaCalculatorResponseDto();
        response.setStartDateString(startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        response.setEndDateString(endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        response.setGender(gender);

        if (endDate.isBefore(startDate)) {
            response.setStatusMessage("Error: Ending date cannot be before starting date!");
            response.setTotalCalendarDays(0);
            response.setFinalMissedDaysForCalculation(0);
            response.setExcludedPeriodDays(0);
            response.setFajrCount(0); response.setZuhrCount(0); response.setAsrCount(0);
            response.setMaghribCount(0); response.setIshaCount(0); response.setWitrCount(0);
            response.setTotalRemainingPrayers(0); // Set total to 0 for error case
            return response;
        }

        long totalCalendarDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        long excludedPeriodDays = 0;
        long finalMissedDaysForCalculation = totalCalendarDays;

        if ("female".equals(gender)) {
            if (averagePeriodDays == null || totalMonthlyCycles == null ||
                averagePeriodDays < 3 || averagePeriodDays > 10) {
                response.setStatusMessage("Error: For females, average period days (3-10) and total monthly cycles are required.");
                response.setTotalCalendarDays(totalCalendarDays);
                response.setFinalMissedDaysForCalculation(0);
                response.setExcludedPeriodDays(0);
                response.setFajrCount(0); response.setZuhrCount(0); response.setAsrCount(0);
                response.setMaghribCount(0); response.setIshaCount(0); response.setWitrCount(0);
                response.setTotalRemainingPrayers(0); // Set total to 0 for error case
                return response;
            }

            excludedPeriodDays = (long) totalMonthlyCycles * averagePeriodDays;
            finalMissedDaysForCalculation = totalCalendarDays - excludedPeriodDays;

            if (finalMissedDaysForCalculation < 0) {
                finalMissedDaysForCalculation = 0;
            }

            response.setAveragePeriodDays(averagePeriodDays);
            response.setTotalMonthlyCycles(totalMonthlyCycles);
        } else {
            response.setAveragePeriodDays(null);
            response.setTotalMonthlyCycles(null);
            response.setExcludedPeriodDays(0);
        }

        long fajr = finalMissedDaysForCalculation;
        long zuhr = finalMissedDaysForCalculation;
        long asr = finalMissedDaysForCalculation;
        long maghrib = finalMissedDaysForCalculation;
        long isha = finalMissedDaysForCalculation;
        long witr = finalMissedDaysForCalculation;

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

        qazaPrayerEntryRepository.save(newEntry);

        response.setTotalCalendarDays(totalCalendarDays);
        response.setExcludedPeriodDays(excludedPeriodDays);
        response.setFinalMissedDaysForCalculation(finalMissedDaysForCalculation);
        response.setFajrCount(fajr);
        response.setZuhrCount(zuhr);
        response.setAsrCount(asr);
        response.setMaghribCount(maghrib);
        response.setIshaCount(isha);
        response.setWitrCount(witr);
        response.setStatusMessage("Initial Qaza calculation successful and saved!");

        long totalRemainingPrayersInitial = fajr + zuhr + asr + maghrib + isha + witr;
        response.setTotalRemainingPrayers(totalRemainingPrayersInitial);

        response.setCalculationTimestampString(newEntry.getCalculationTimestamp().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

        return response;
    }

    /**
     * Decrements Qaza prayer counts based on prayers performed and saves a new entry.
     */
    @Transactional
    public QazaCalculatorResponseDto updateQazaPrayers(String username, QazaPrayerUpdateDto updateDto) {
        User user = userService.findUserByUsername(username)
                      .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Optional<QazaPrayerEntry> latestEntryOptional = qazaPrayerEntryRepository.findTopByUserOrderByCalculationTimestampDesc(user);

        if (latestEntryOptional.isEmpty()) {
            QazaCalculatorResponseDto response = new QazaCalculatorResponseDto();
            response.setStatusMessage("Error: No previous Qaza calculation found to update. Please perform an initial calculation first.");
            response.setFajrCount(0); response.setZuhrCount(0); response.setAsrCount(0);
            response.setMaghribCount(0); response.setIshaCount(0); response.setWitrCount(0);
            response.setTotalRemainingPrayers(0);
            return response;
        }

        QazaPrayerEntry latestEntry = latestEntryOptional.get();

        // Calculate new individual counts by subtracting prayers performed
        long newFajr = Math.max(0, latestEntry.getFajrCount() - updateDto.getFajrPrayed());
        long newZuhr = Math.max(0, latestEntry.getZuhrCount() - updateDto.getZuhrPrayed());
        long newAsr = Math.max(0, latestEntry.getAsrCount() - updateDto.getAsrPrayed());
        long newMaghrib = Math.max(0, latestEntry.getMaghribCount() - updateDto.getMaghribPrayed());
        long newIsha = Math.max(0, latestEntry.getIshaCount() - updateDto.getIshaPrayed());
        long newWitr = Math.max(0, latestEntry.getWitrCount() - updateDto.getWitrPrayed());

        long prayersPerformedSum = updateDto.getFajrPrayed() + updateDto.getZuhrPrayed() +
                                   updateDto.getAsrPrayed() + updateDto.getMaghribPrayed() +
                                   updateDto.getIshaPrayed() + updateDto.getWitrPrayed();

        QazaPrayerEntry updatedEntry = new QazaPrayerEntry(
            user,
            latestEntry.getStartDate(),
            latestEntry.getEndDate(),
            latestEntry.getTotalCalendarDays(),
            latestEntry.getGender(),
            latestEntry.getAveragePeriodDays(),
            latestEntry.getTotalMonthlyCycles(),
            latestEntry.getExcludedPeriodDays(),
            latestEntry.getFinalMissedDaysForCalculation(),
            newFajr,
            newZuhr,
            newAsr,
            newMaghrib,
            newIsha,
            newWitr
        );

        qazaPrayerEntryRepository.save(updatedEntry);

        QazaCalculatorResponseDto response = QazaCalculatorResponseDto.fromEntity(updatedEntry);

        long totalRemainingPrayers = newFajr + newZuhr + newAsr + newMaghrib + newIsha + newWitr;
        response.setTotalRemainingPrayers(totalRemainingPrayers);

        if (prayersPerformedSum > 0) {
            response.setStatusMessage("Qaza prayers updated successfully! Decremented by " + prayersPerformedSum + " prayers. Total remaining: " + totalRemainingPrayers);
        } else {
            response.setStatusMessage("No prayers were decremented. Please enter values > 0 to update.");
        }

        return response;
    }

    /**
     * NEW METHOD: Adds missed Qaza prayer counts based on newly missed prayers and saves a new entry.
     * This method retrieves the latest QazaPrayerEntry for the user, applies the increments,
     * and persists the modified state as a *new* QazaPrayerEntry.
     *
     * @param username The username of the currently logged-in user.
     * @param addDto Contains the number of prayers newly missed for each type.
     * @return A QazaCalculatorResponseDto reflecting the updated counts, or an error message.
     */
    @Transactional
    public QazaCalculatorResponseDto addMissedQazaPrayers(String username, QazaPrayerAddDto addDto) {
        User user = userService.findUserByUsername(username)
                      .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Optional<QazaPrayerEntry> latestEntryOptional = qazaPrayerEntryRepository.findTopByUserOrderByCalculationTimestampDesc(user);

        if (latestEntryOptional.isEmpty()) {
            QazaCalculatorResponseDto response = new QazaCalculatorResponseDto();
            response.setStatusMessage("Error: No previous Qaza calculation found to add missed prayers to. Please perform an initial calculation first.");
            response.setFajrCount(0); response.setZuhrCount(0); response.setAsrCount(0);
            response.setMaghribCount(0); response.setIshaCount(0); response.setWitrCount(0);
            response.setTotalRemainingPrayers(0);
            return response;
        }

        QazaPrayerEntry latestEntry = latestEntryOptional.get();

        // Calculate new individual counts by ADDING missed prayers
        long newFajr = latestEntry.getFajrCount() + addDto.getFajrMissed();
        long newZuhr = latestEntry.getZuhrCount() + addDto.getZuhrMissed();
        long newAsr = latestEntry.getAsrCount() + addDto.getAsrMissed();
        long newMaghrib = latestEntry.getMaghribCount() + addDto.getMaghribMissed();
        long newIsha = latestEntry.getIshaCount() + addDto.getIshaMissed();
        long newWitr = latestEntry.getWitrCount() + addDto.getWitrMissed();

        // Calculate the sum of prayers that were just missed
        long prayersMissedSum = addDto.getFajrMissed() + addDto.getZuhrMissed() +
                                addDto.getAsrMissed() + addDto.getMaghribMissed() +
                                addDto.getIshaMissed() + addDto.getWitrMissed();

        // Create a *new* QazaPrayerEntry based on the latest one
        // The start and end dates remain the same as the original calculation context
        QazaPrayerEntry updatedEntry = new QazaPrayerEntry(
            user,
            latestEntry.getStartDate(),
            latestEntry.getEndDate(),
            latestEntry.getTotalCalendarDays(),
            latestEntry.getGender(),
            latestEntry.getAveragePeriodDays(),
            latestEntry.getTotalMonthlyCycles(),
            latestEntry.getExcludedPeriodDays(),
            latestEntry.getFinalMissedDaysForCalculation(), // This is the original missed days. It doesn't change on additions.
            newFajr,
            newZuhr,
            newAsr,
            newMaghrib,
            newIsha,
            newWitr
        );

        qazaPrayerEntryRepository.save(updatedEntry);

        // Populate the response DTO from the newly saved entry
        QazaCalculatorResponseDto response = QazaCalculatorResponseDto.fromEntity(updatedEntry);

        // Calculate total remaining prayers for the response DTO
        long totalRemainingPrayers = newFajr + newZuhr + newAsr + newMaghrib + newIsha + newWitr;
        response.setTotalRemainingPrayers(totalRemainingPrayers);

        if (prayersMissedSum > 0) {
            response.setStatusMessage("Missed prayers added successfully! Incremented by " + prayersMissedSum + " prayers. New total: " + totalRemainingPrayers);
        } else {
            response.setStatusMessage("No prayers were incremented. Please enter values > 0 to add.");
        }

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

        return entries.stream()
                      .map(QazaCalculatorResponseDto::fromEntity)
                      .collect(Collectors.toList());
    }
}
