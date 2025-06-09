package com.qalbconnect.qalbconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "asma_al_husna") // Table to store the 99 Names of Allah
public class AsmaAlHusna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing ID
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String arabicName; // The Arabic name of Allah

    @Column(nullable = false, length = 255)
    private String englishMeaning; // The English meaning of the name

    // Default constructor is required by JPA
    public AsmaAlHusna() {
    }

    // Constructor for convenience when populating data
    public AsmaAlHusna(String arabicName, String englishMeaning) {
        this.arabicName = arabicName;
        this.englishMeaning = englishMeaning;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getEnglishMeaning() {
        return englishMeaning;
    }

    public void setEnglishMeaning(String englishMeaning) {
        this.englishMeaning = englishMeaning;
    }

    @Override
    public String toString() {
        return "AsmaAlHusna{" +
               "id=" + id +
               ", arabicName='" + arabicName + '\'' +
               ", englishMeaning='" + englishMeaning + '\'' +
               '}';
    }
}