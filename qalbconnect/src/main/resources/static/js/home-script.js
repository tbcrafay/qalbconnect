// src/main/resources/static/js/home-script.js

document.addEventListener("DOMContentLoaded", () => {
    const quotes = [
        "Indeed, with hardship [comes] ease. (Quran 94:6)",
        "The best among you are those who have the best manners and character. (Hadith)",
        "And He found you lost and guided [you]. (Quran 93:7)",
        "The strong man is not the one who can overpower others; the strong man is the one who controls himself when angry. (Hadith)",
        "So be patient. Indeed, the promise of Allah is truth. (Quran 30:60)"
    ];

    const randomIndex = Math.floor(Math.random() * quotes.length);
    const randomQuote = quotes[randomIndex];

    const quoteElement = document.getElementById("randomQuote");
    if (quoteElement) { // Check if the element exists before setting textContent
        quoteElement.textContent = randomQuote;
    }
});
