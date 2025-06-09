package com.qalbconnect.qalbconnect.repository;

import com.qalbconnect.qalbconnect.model.AsmaAlHusna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Marks this interface as a Spring Data JPA repository
public interface AsmaAlHusnaRepository extends JpaRepository<AsmaAlHusna, Long> {

    // You could add custom query methods here if needed, e.g.,
    // Optional<AsmaAlHusna> findByArabicName(String arabicName);
    // Optional<AsmaAlHusna> findByEnglishMeaning(String englishMeaning);

    // For this feature, JpaRepository's findAll() method will be sufficient
    // to retrieve all 99 names.
}