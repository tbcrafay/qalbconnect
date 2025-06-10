package com.qalbconnect.qalbconnect.service;

import com.qalbconnect.qalbconnect.dto.QazaCalculatorRequestDto;
import com.qalbconnect.qalbconnect.dto.QazaCalculatorResponseDto;
import com.qalbconnect.qalbconnect.model.QazaPrayerEntry;
import com.qalbconnect.qalbconnect.model.User; // Import the User entity
import com.qalbconnect.qalbconnect.repository.QazaPrayerEntryRepository; // Import the new repository
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // For transactional operations

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors; // For stream operations

@Service // Marks this class as a Spring Service. It will be a Singleton.
public class QazaCalculatorService {

    private final QazaPrayerEntryRepository qazaPrayerEntryRepository;
    private final UserService userService; // We'll need UserService to find the User object

    // Constructor Injection: Spring will inject the necessary repository and service instances.
    public QazaCalculatorService(QazaPrayerEntryRepository qazaPrayerEntryRepository, UserService userService) {
        this.qazaPrayerEntryRepository = qazaPrayerEntryRepository;
        this.userService = userService; // Inject UserService to fetch user details
    }

    /**
     * Calculates the Qaza prayers based on the provided start and end dates for a specific user,
     * and persists the result to the database.
     *
     * @param username The username of the currently logged-in user.
     * @param requestDto Contains the startDate (when user became baligh) and endDate.
     * @return A QazaCalculatorResponseDto with calculated prayer counts and a status message.
     * --- Design Pattern: Facade ---
     * This service method acts as a Facade to the underlying calculation and persistence logic.
     * --- Design Pattern: Builder (Implicit for Response DTO and Entity) ---
     * The construction of QazaCalculatorResponseDto and QazaPrayerEntry from various parts.
     * --- Design Pattern: Command (Implicit - Save Calculation Command) ---
     * This method encapsulates the action of performing and saving a calculation.
     */
    @Transactional // Ensures the calculation and saving are an atomic operation
    public QazaCalculatorResponseDto calculateAndSaveQazaPrayers(String username, QazaCalculatorRequestDto requestDto) {
        // Retrieve the User entity for the given username
        User user = userService.findUserByUsername(username)
                      .orElseThrow(() -> new RuntimeException("User not found: " + username));
        // Note: In a real app, you'd use a more specific exception like UserNotFoundException.

        LocalDate startDate = requestDto.getStartDate();
        LocalDate endDate = requestDto.getEndDate();

        // Initialize a response DTO for immediate display
        QazaCalculatorResponseDto response = new QazaCalculatorResponseDto();
        response.setStartDateString(startDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        response.setEndDateString(endDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")));


        // 1. Validate the input dates (server-side validation, complementing DTO annotations)
        if (endDate.isBefore(startDate)) {
            response.setStatusMessage("Error: Ending date cannot be before starting date!");
            response.setTotalDays(0); // Set counts to 0 in case of error
            response.setFajrCount(0);
            response.setZuhrCount(0);
            response.setAsrCount(0);
            response.setMaghribCount(0);
            response.setIshaCount(0);
            response.setWitrCount(0);
            return response; // Return early with error message
        }

        // 2. Calculate the total days between the two dates
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        // 3. Calculate missed prayers for each type (assuming 5 daily prayers + 1 Witr per day)
        long fajr = totalDays;
        long zuhr = totalDays;
        long asr = totalDays;
        long maghrib = totalDays;
        long isha = totalDays;
        long witr = totalDays;

        // 4. Create a QazaPrayerEntry entity to save the calculation
        QazaPrayerEntry newEntry = new QazaPrayerEntry(
            user,
            startDate,
            endDate,
            totalDays,
            fajr,
            zuhr,
            asr,
            maghrib,
            isha,
            witr
        );

        // 5. Persist the new entry to the database
        qazaPrayerEntryRepository.save(newEntry);

        // 6. Populate the response DTO with the calculated values
        response.setTotalDays(totalDays);
        response.setFajrCount(fajr);
        response.setZuhrCount(zuhr);
        response.setAsrCount(asr);
        response.setMaghribCount(maghrib);
        response.setIshaCount(isha);
        response.setWitrCount(witr);
        response.setStatusMessage("Calculation successful and saved!"); // Success message

        return response;
    }

    /**
     * Retrieves all QazaPrayerEntry records for a given user, ordered by most recent calculation.
     *
     * @param username The username of the user whose entries to retrieve.
     * @return A list of QazaCalculatorResponseDto, mapping from entity to DTO for presentation.
     * --- Design Pattern: Adapter (Implicit - Entity to DTO Conversion) ---
     * The stream mapping here adapts the QazaPrayerEntry entity to QazaCalculatorResponseDto.
     * --- Design Pattern: Null Object (Implicit via Empty List) ---
     * Returns an empty list if no entries are found for the user.
     */
    public List<QazaCalculatorResponseDto> getQazaEntriesForUser(String username) {
        User user = userService.findUserByUsername(username)
                      .orElseThrow(() -> new RuntimeException("User not found: " + username));
        List<QazaPrayerEntry> entries = qazaPrayerEntryRepository.findByUserOrderByCalculationTimestampDesc(user);

        // Convert entities to DTOs for the presentation layer
        return entries.stream()
                      .map(QazaCalculatorResponseDto::fromEntity) // Use the factory method in DTO
                      .collect(Collectors.toList());
    }
}