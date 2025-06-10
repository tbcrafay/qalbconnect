package com.qalbconnect.qalbconnect.repository;

import com.qalbconnect.qalbconnect.model.QazaPrayerEntry;
import com.qalbconnect.qalbconnect.model.User; // Import the User entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}