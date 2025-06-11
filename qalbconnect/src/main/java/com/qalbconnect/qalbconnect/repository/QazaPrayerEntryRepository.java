package com.qalbconnect.qalbconnect.repository;

import com.qalbconnect.qalbconnect.model.QazaPrayerEntry;
import com.qalbconnect.qalbconnect.model.User; // Import the User entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // NEW: Import Optional

@Repository // Marks this interface as a Spring Data JPA repository. This bean is a Singleton.
public interface QazaPrayerEntryRepository extends JpaRepository<QazaPrayerEntry, Long> {

    /**
     * Finds all QazaPrayerEntry records associated with a specific User,
     * ordered by calculation timestamp in descending order (most recent first).
     *
     * @param user The User entity for whom to retrieve the Qaza prayer entries.
     * @return A list of QazaPrayerEntry objects for the given user.
     * --- Design Pattern: Repository Pattern ---
     * This method is part of the Repository, abstracting data access for QazaPrayerEntry.
     */
    List<QazaPrayerEntry> findByUserOrderByCalculationTimestampDesc(User user);

    /**
     * NEW METHOD: Finds the single latest QazaPrayerEntry for a given user.
     * Used to get the most recent calculation to apply updates to.
     * Spring Data JPA will automatically implement this based on the method name.
     *
     * @param user The User entity for whom to retrieve the latest Qaza prayer entry.
     * @return An Optional containing the latest QazaPrayerEntry, or empty if none exists.
     */
    Optional<QazaPrayerEntry> findTopByUserOrderByCalculationTimestampDesc(User user);
}
