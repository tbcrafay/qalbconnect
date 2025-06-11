// src/main/resources/static/js/qazaCalculator.js

document.addEventListener('DOMContentLoaded', function() {
    // Set max date for date input to today
    const dateToInput = document.getElementById('dateTo');
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); // Month is 0-indexed
    const day = String(today.getDate()).padStart(2, '0');
    dateToInput.setAttribute('max', `${year}-${month}-${day}`);

    // If endDate is initially empty, set it to today's date for user convenience
    if (dateToInput.value === '') {
        dateToInput.value = `${year}-${month}-${day}`;
    }

    // --- JavaScript for Gender-specific input fields ---
    const genderMaleRadio = document.getElementById('genderMale');
    const genderFemaleRadio = document.getElementById('genderFemale');
    const femaleDetailsDiv = document.getElementById('femaleDetails');
    const averagePeriodDaysInput = document.getElementById('averagePeriodDays');
    const totalMonthlyCyclesInput = document.getElementById('totalMonthlyCycles');

    /**
     * Toggles the visibility of female-specific input fields and manages their required status.
     * This function is called when a gender radio button is changed or on initial page load.
     */
    function toggleFemaleDetails() {
        if (genderFemaleRadio.checked) {
            femaleDetailsDiv.style.display = 'block'; // Show the div
            // Make fields required when visible
            averagePeriodDaysInput.setAttribute('required', 'true');
            totalMonthlyCyclesInput.setAttribute('required', 'true');
        } else {
            femaleDetailsDiv.style.display = 'none'; // Hide the div
            // Remove required attribute and clear values when hidden
            averagePeriodDaysInput.removeAttribute('required');
            totalMonthlyCyclesInput.removeAttribute('required');
            averagePeriodDaysInput.value = '';
            totalMonthlyCyclesInput.value = '';
        }
    }

    // Add event listeners to gender radio buttons
    genderMaleRadio.addEventListener('change', toggleFemaleDetails);
    genderFemaleRadio.addEventListener('change', toggleFemaleDetails);

    // Initial check on page load to set the correct display state
    // Note: /*[[${request.gender}]]*/ is a Thymeleaf inline JS expression.
    // It will be replaced by the actual value from the model *before* the JS runs in the browser.
    // If the page is loaded without a 'request' object (e.g., initial GET), this might be an empty string.
    const initialGenderValue = (typeof request !== 'undefined' && request.gender) ? request.gender : '';

    if (initialGenderValue === 'female') {
        genderFemaleRadio.checked = true;
    } else {
        // Default to male if no gender is explicitly set or if it's 'male'
        genderMaleRadio.checked = true;
    }
    toggleFemaleDetails(); // Call once on load to set initial state based on default or model value

    // Handle status message fade-out
    const statusMessageDiv = document.querySelector('.status-message');
    if (statusMessageDiv) {
        setTimeout(() => {
            statusMessageDiv.classList.add('fade-out');
        }, 5000); // Start fade out after 5 seconds
    }
});

/**
 * Function to clear the main calculation form and reset the page to its initial state.
 * This is called by the "Clear Form" button.
 */
function clearFormAndResults() {
    // Reset the form to its default values
    document.getElementById('qazaCalculatorForm').reset();

    // Manually reset gender selection to male and hide female details
    document.getElementById('genderMale').checked = true;
    document.getElementById('femaleDetails').style.display = 'none';
    document.getElementById('averagePeriodDays').removeAttribute('required');
    document.getElementById('totalMonthlyCycles').removeAttribute('required');
    document.getElementById('averagePeriodDays').value = '';
    document.getElementById('totalMonthlyCycles').value = '';

    // Redirect to the main page to clear results and history rendered by Thymeleaf
    window.location.href = /*[[@{/qazaumri}]]*/ '/qazaumri';
}

/**
 * Resets the "Update Prayers" form to all zeros.
 * This is called by the "Reset Update Form" button.
 */
function resetUpdateForm() {
    const updateForm = document.getElementById('updateQazaForm');
    if (updateForm) {
        updateForm.reset();
        updateForm.querySelectorAll('input[type="number"]').forEach(input => {
            input.value = '0';
        });
    }
}

/**
 * NEW FUNCTION: Resets the "Add Missed Prayers" form to all zeros.
 * This is called by the "Reset Add Form" button.
 */
function resetAddForm() {
    const addMissedForm = document.getElementById('addMissedForm');
    if (addMissedForm) {
        addMissedForm.reset();
        addMissedForm.querySelectorAll('input[type="number"]').forEach(input => {
            input.value = '0';
        });
    }
}
