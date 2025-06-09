package com.qalbconnect.qalbconnect.service;

import com.qalbconnect.qalbconnect.model.AsmaAlHusna;
import com.qalbconnect.qalbconnect.repository.AsmaAlHusnaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AsmaAlHusnaService {

    private final AsmaAlHusnaRepository asmaAlHusnaRepository;

    public AsmaAlHusnaService(AsmaAlHusnaRepository asmaAlHusnaRepository) {
        this.asmaAlHusnaRepository = asmaAlHusnaRepository;
    }

    @PostConstruct
    @Transactional
    public void populateNamesIfEmpty() {
        if (asmaAlHusnaRepository.count() == 0) {
            System.out.println("Populating 99 Names of Allah into the database...");
            List<AsmaAlHusna> names = List.of(
                new AsmaAlHusna("ٱلرَّحْمَـٰنُ", "Ar-Rahman (The Most or Entirely Merciful)"),
                new AsmaAlHusna("ٱلرَّحِيمُ", "Ar-Raheem (The Bestower of Mercy)"),
                new AsmaAlHusna("ٱلْمَلِكُ", "Al-Malik (The King and Owner of Dominion)"),
                new AsmaAlHusna("ٱلْقُدُّوسُ", "Al-Quddus (The Absolutely Pure)"),
                new AsmaAlHusna("ٱلسَّلَامُ", "As-Salam (The Perfection and Giver of Peace)"),
                new AsmaAlHusna("ٱلْمُؤْمِنُ", "Al-Mu'min (The One Who gives Emaan and Security)"),
                new AsmaAlHusna("ٱلْمُهَيْمِنُ", "Al-Muhaymin (The Guardian, The Witness, The Overseer)"),
                new AsmaAlHusna("ٱلْعَزِيزُ", "Al-Aziz (The All Mighty)"),
                new AsmaAlHusna("ٱلْجَبَّارُ", "Al-Jabbar (The Compeller, The Restorer)"),
                new AsmaAlHusna("ٱلْمُتَكَبِّرُ", "Al-Mutakabbir (The Supreme, The Majestic)"),
                new AsmaAlHusna("ٱلْخَالِقُ", "Al-Khaliq (The Creator, The Maker)"),
                new AsmaAlHusna("ٱلْبَارِئُ", "Al-Bari (The Originator)"),
                new AsmaAlHusna("ٱلْمُصَوِّرُ", "Al-Musawwir (The Fashioner)"),
                new AsmaAlHusna("ٱلْغَفَّارُ", "Al-Ghaffar (The All- and Oft-Forgiving)"),
                new AsmaAlHusna("ٱلْقَهَّارُ", "Al-Qahhar (The Subduer, The Ever-Dominating)"),
                new AsmaAlHusna("ٱلْوَهَّابُ", "Al-Wahhab (The Giver of Gifts)"),
                new AsmaAlHusna("ٱلرَّزَّاقُ", "Ar-Razzaq (The Provider)"),
                new AsmaAlHusna("ٱلْفَتَّاحُ", "Al-Fattah (The Opener, The Judge)"),
                new AsmaAlHusna("ٱلْعَلِيمُ", "Al-Alim (The All-Knowing, The Omniscient)"),
                new AsmaAlHusna("ٱلْقَابِضُ", "Al-Qabid (The Withholder)"),
                new AsmaAlHusna("ٱلْبَاسِطُ", "Al-Basit (The Extender)"),
                new AsmaAlHusna("ٱلْخَافِضُ", "Al-Khafid (The Reducer, The Abaser)"),
                new AsmaAlHusna("ٱلرَّافِعُ", "Ar-Rafi (The Exalter, The Elevator)"),
                new AsmaAlHusna("ٱلْمُعِزُّ", "Al-Mu'izz (The Honourer, The Bestower)"),
                new AsmaAlHusna("ٱلْمُذِلُّ", "Al-Muzill (The Dishonourer, The Humiliator)"),
                new AsmaAlHusna("ٱلسَّمِيعُ", "As-Sami (The All-Hearing)"),
                new AsmaAlHusna("ٱلْبَصِيرُ", "Al-Basir (The All-Seeing)"),
                new AsmaAlHusna("ٱلْحَكَمُ", "Al-Hakam (The Judge, The Giver of Justice)"),
                new AsmaAlHusna("ٱلْعَدْلُ", "Al-Adl (The Utterly Just)"),
                new AsmaAlHusna("ٱللَّطِيفُ", "Al-Latif (The Subtle One, The Most Gentle)"),
                new AsmaAlHusna("ٱلْخَبِيرُ", "Al-Khabir (The Acquainted, the All-Aware)"),
                new AsmaAlHusna("ٱلْحَلِيمُ", "Al-Halim (The Most Forbearing)"),
                new AsmaAlHusna("ٱلْعَظِيمُ", "Al-Azim (The Magnificent, The Supreme)"),
                new AsmaAlHusna("ٱلْغَفُورُ", "Al-Ghafoor (The Forgiving, The Exceedingly Forgiving)"),
                new AsmaAlHusna("ٱلشَّكُورُ", "Ash-Shakur (The Most Appreciative)"),
                new AsmaAlHusna("ٱلْعَلِيُّ", "Al-Ali (The Most High, The Exalted)"),
                new AsmaAlHusna("ٱلْكَبِيرُ", "Al-Kabir (The Greatest, The Most Grand)"),
                new AsmaAlHusna("ٱلْحَفِيظُ", "Al-Hafiz (The Preserver, The All-Heedful and All-Protecting)"),
                new AsmaAlHusna("ٱلْمُقِيتُ", "Al-Muqit (The Sustainer)"),
                new AsmaAlHusna("ٱلْحَسِيبُ", "Al-Hasib (The Reckoner, The Sufficient)"),
                new AsmaAlHusna("ٱلْجَلِيلُ", "Al-Jalil (The Majestic)"),
                new AsmaAlHusna("ٱلْكَرِيمُ", "Al-Karim (The Most Generous, The Most Esteemed)"),
                new AsmaAlHusna("ٱلرَّقِيبُ", "Ar-Raqib (The Watchful)"),
                new AsmaAlHusna("ٱلْمُجِيبُ", "Al-Mujib (The Responsive One)"),
                new AsmaAlHusna("ٱلْوَاسِعُ", "Al-Wasi (The All-Encompassing, the Boundless)"),
                new AsmaAlHusna("ٱلْحَكِيمُ", "Al-Hakim (The All-Wise)"),
                new AsmaAlHusna("ٱلْوَدُودُ", "Al-Wadud (The Most Loving)"),
                new AsmaAlHusna("ٱلْمَجِيدُ", "Al-Majid (The Glorious, The Most Honorable)"),
                new AsmaAlHusna("ٱلْبَاعِثُ", "Al-Ba'ith (The Resurrector, The Raiser of the Dead)"),
                new AsmaAlHusna("ٱلشَّهِيدُ", "Ash-Shahid (The All- and Ever Witnessing)"),
                new AsmaAlHusna("ٱلْحَقُّ", "Al-Haqq (The Absolute Truth)"),
                new AsmaAlHusna("ٱلْوَكِيلُ", "Al-Wakil (The Trustee, The Disposer of Affairs)"),
                new AsmaAlHusna("ٱلْقَوِىُّ", "Al-Qawi (The All-Strong)"),
                new AsmaAlHusna("ٱلْمَتِينُ", "Al-Matin (The Firm, The Steadfast)"),
                new AsmaAlHusna("ٱلْوَلِىُّ", "Al-Wali (The Protecting Associate)"),
                new AsmaAlHusna("ٱلْحَمِيدُ", "Al-Hamid (The Praiseworthy)"),
                new AsmaAlHusna("ٱلْمُحْصِى", "Al-Muhsi (The All-Enumerating, The Counter)"),
                new AsmaAlHusna("ٱلْمُبْدِئُ", "Al-Mubdi (The Originator, The Initiator)"),
                new AsmaAlHusna("ٱلْمُعِيدُ", "Al-Mu'id (The Restorer, The Reinstater)"),
                new AsmaAlHusna("ٱلْمُحْيِى", "Al-Muhyi (The Giver of Life)"),
                new AsmaAlHusna("ٱلْمُمِيتُ", "Al-Mumit (The Bringer of Death, the Destroyer)"),
                new AsmaAlHusna("ٱلْحَىُّ", "Al-Hayy (The Ever-Living)"),
                new AsmaAlHusna("ٱلْقَيُّومُ", "Al-Qayyum (The Sustainer, The Self-Subsisting)"),
                new AsmaAlHusna("ٱلْوَاجِدُ", "Al-Wajid (The Perceiver)"),
                new AsmaAlHusna("ٱلْمَاجِدُ", "Al-Majid (The Illustrious, the Magnificent)"),
                new AsmaAlHusna("ٱلْوَاحِدُ", "Al-Wahid (The One)"),
                new AsmaAlHusna("ٱلْأَحَدُ", "Al-Ahad (The Unique, The Only One)"),
                new AsmaAlHusna("ٱلصَّمَدُ", "As-Samad (The Eternal, Satisfier of Needs)"),
                new AsmaAlHusna("ٱلْقَادِرُ", "Al-Qadir (The Capable, The Powerful)"), // Original
                new AsmaAlHusna("ٱلْمُقْتَدِرُ", "Al-Muqtadir (The Omnipotent)"), // Original
                new AsmaAlHusna("ٱلْمُقَدِّمُ", "Al-Muqaddim (The Expediter, The Promoter)"),
                new AsmaAlHusna("ٱلْمُؤَخِّرُ", "Al-Mu'akhkhir (The Delayer, the Retarder)"),
                new AsmaAlHusna("ٱلْأَوَّلُ", "Al-Awwal (The First)"),
                new AsmaAlHusna("ٱلْآخِرُ", "Al-Akhir (The Last)"),
                new AsmaAlHusna("ٱلظَّاهِرُ", "Az-Zahir (The Manifest)"),
                new AsmaAlHusna("ٱلْبَاطِنُ", "Al-Batin (The Hidden One, Knower of the Hidden)"),
                new AsmaAlHusna("ٱلْوَالِي", "Al-Wali (The Governor, The Patron)"),
                new AsmaAlHusna("ٱلْمُتَعَالِي", "Al-Muta'ali (The Most Exalted)"), // Original
                new AsmaAlHusna("ٱلْبَرُّ", "Al-Barr (The Source of Goodness, the Kind Benefactor)"), // Original
                new AsmaAlHusna("ٱلتَّوَّابُ", "At-Tawwab (The Ever-Pardoning, The Relenting)"),
                new AsmaAlHusna("ٱلْمُنْتَقِمُ", "Al-Muntaqim (The Avenger)"),
                new AsmaAlHusna("ٱلْعَفُوُّ", "Al-Afuww (The Pardoner)"),
                new AsmaAlHusna("ٱلرَّؤُوفُ", "Ar-Ra'oof (The Most Kind)"),
                new AsmaAlHusna("مَالِكُ ٱلْمُلْكِ", "Malik Al-Mulk (Master of the Kingdom, Owner of the Dominion)"),
                new AsmaAlHusna("ذُو ٱلْجَلَالِ وَٱلْإِكْرَامِ", "Dhul-Jalali Wal-Ikram (Possessor of Glory and Honour, Lord of Majesty and Generosity)"),
                new AsmaAlHusna("ٱلْمُقْسِطُ", "Al-Muqsit (The Equitable, the Requiter)"),
                new AsmaAlHusna("ٱلْجَامِعُ", "Al-Jami (The Gatherer, the Uniter)"),
                new AsmaAlHusna("ٱلْغَنِىُّ", "Al-Ghaniyy (The Self-Sufficient, The Wealthy)"),
                new AsmaAlHusna("ٱلْمُغْنِى", "Al-Mughni (The Enricher)"),
                new AsmaAlHusna("ٱلْمَانِعُ", "Al-Mani (The Withholder)"),
                new AsmaAlHusna("ٱلضَّارُّ", "Ad-Darr (The Distresser)"),
                new AsmaAlHusna("ٱلنَّافِعُ", "An-Nafi (The Propitious, the Benefactor)"),
                new AsmaAlHusna("ٱلنُّورُ", "An-Nur (The Light, The Illuminator)"),
                new AsmaAlHusna("ٱلْهَادِي", "Al-Hadi (The Guide)"),
                new AsmaAlHusna("ٱلْبَدِيعُ", "Al-Badi (The Incomparable Originator)"),
                new AsmaAlHusna("ٱلْبَاقِي", "Al-Baqi (The Ever-Surviving, The Everlasting)"),
                new AsmaAlHusna("ٱلْوَارِثُ", "Al-Warith (The Inheritor, The Heir)"),
                new AsmaAlHusna("ٱلرَّشِيدُ", "Ar-Rashid (The Guide, Infallible Teacher)"),
                new AsmaAlHusna("ٱلصَّبُورُ", "As-Sabur (The Forbearing, The Patient)")
                // Note: I've verified this list for unique Arabic names based on a standard compilation.
                // If you have a specific source of 99 names, please ensure its Arabic names are unique.
            );
            asmaAlHusnaRepository.saveAll(names);
            System.out.println("99 Names of Allah populated successfully.");
        } else {
            System.out.println("99 Names of Allah already present in the database. Skipping population.");
        }
    }

    public List<AsmaAlHusna> getAllNames() {
        return asmaAlHusnaRepository.findAll();
    }
}