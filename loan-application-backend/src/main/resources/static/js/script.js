// src/main/resources/static/js/script.js

// Base URL for the backend API
const API_BASE_URL = 'http://localhost:8080/api/loans';

// Get references to DOM elements
const loanForm = document.getElementById('loanForm');
const loanTableBody = document.getElementById('loanTableBody');
const messageBox = document.getElementById('messageBox');
const noLoansMessage = document.getElementById('noLoansMessage');
const updateButton = document.getElementById('updateButton');
const submitButton = loanForm.querySelector('button[type="submit"]'); // The "Add Loan" button
const cancelButton = document.getElementById('cancelUpdateButton');

let editingLoanId = null; // Variable to store the ID of the loan being edited

/**
 * Displays a message to the user in a dedicated message box.
 * @param {string} message The message to display.
 * @param {string} type The type of message ('success' or 'error').
 */
function showMessage(message, type) {
    messageBox.textContent = message;
    messageBox.className = `mb-4 p-3 rounded-md text-center ${type}`; // Apply Tailwind classes
    messageBox.classList.remove('hidden');
    // Hide message after 5 seconds
    setTimeout(() => {
        messageBox.classList.add('hidden');
        messageBox.textContent = '';
    }, 5000);
}

/**
 * Fetches all loan applications from the backend and displays them in the table.
 */
async function fetchAndDisplayLoans() {
    try {
        const response = await fetch(API_BASE_URL);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const loans = await response.json();
        loanTableBody.innerHTML = ''; // Clear existing table rows

        if (loans.length === 0) {
            noLoansMessage.classList.remove('hidden');
            return;
        } else {
            noLoansMessage.classList.add('hidden');
        }

        loans.forEach(loan => {
            const row = loanTableBody.insertRow();
            row.dataset.id = loan.id; // Store loan ID on the row for easy access

            row.innerHTML = `
                <td class="py-2 px-4 border-b border-gray-200">${loan.id}</td>
                <td class="py-2 px-4 border-b border-gray-200">${loan.applicantName}</td>
                <td class="py-2 px-4 border-b border-gray-200">$${loan.loanAmount.toFixed(2)}</td>
                <td class="py-2 px-4 border-b border-gray-200">${loan.applicationDate}</td>
                <td class="py-2 px-4 border-b border-gray-200">${loan.status}</td>
                <td class="py-2 px-4 border-b border-gray-200">${loan.email || 'N/A'}</td>
                <td class="py-2 px-4 border-b border-gray-200">${loan.phoneNumber || 'N/A'}</td>
                <td class="py-2 px-4 border-b border-gray-200">$${loan.income ? loan.income.toFixed(2) : 'N/A'}</td>
                <td class="py-2 px-4 border-b border-gray-200">${loan.creditScore || 'N/A'}</td>
                <td class="py-2 px-4 border-b border-gray-200 flex space-x-2">
                    <button class="action-button edit-btn" onclick="editLoan(${loan.id})">Edit</button>
                    <button class="action-button delete-btn" onclick="deleteLoan(${loan.id})">Delete</button>
                </td>
            `;
        });
    } catch (error) {
        console.error('Error fetching loan applications:', error);
        showMessage('Failed to load loan applications. Please ensure the backend is running.', 'error');
    }
}

/**
 * Handles form submission for adding or updating a loan application.
 * @param {Event} event The form submission event.
 */
loanForm.addEventListener('submit', async (event) => {
    event.preventDefault(); // Prevent default form submission

    const formData = new FormData(loanForm);
    const loanData = {
        applicantName: formData.get('applicantName'),
        loanAmount: parseFloat(formData.get('loanAmount')),
        applicationDate: formData.get('applicationDate'),
        status: formData.get('status'),
        email: formData.get('email'),
        phoneNumber: formData.get('phoneNumber'),
        income: formData.get('income') ? parseFloat(formData.get('income')) : null,
        creditScore: formData.get('creditScore') ? parseInt(formData.get('creditScore')) : null
    };

    try {
        let response;
        if (editingLoanId) {
            // Update existing loan
            response = await fetch(`${API_BASE_URL}/${editingLoanId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(loanData)
            });
            if (response.ok) {
                showMessage('Loan application updated successfully!', 'success');
            } else {
                 throw new Error(`Failed to update loan. Status: ${response.status}`);
            }
        } else {
            // Create new loan
            response = await fetch(API_BASE_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(loanData)
            });
            if (response.ok) {
                showMessage('Loan application added successfully!', 'success');
            } else {
                throw new Error(`Failed to add loan. Status: ${response.status}`);
            }
        }

        loanForm.reset(); // Clear the form fields
        resetFormForAdd(); // Switch back to 'Add' mode
        fetchAndDisplayLoans(); // Refresh the list

    } catch (error) {
        console.error('Error submitting loan application:', error);
        showMessage(`Error: ${error.message}`, 'error');
    }
});

/**
 * Populates the form with data of a selected loan for editing.
 * @param {number} id The ID of the loan to edit.
 */
async function editLoan(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const loan = await response.json();

        // Populate form fields
        document.getElementById('applicantName').value = loan.applicantName;
        document.getElementById('loanAmount').value = loan.loanAmount;
        document.getElementById('applicationDate').value = loan.applicationDate; // Assumes YYYY-MM-DD format
        document.getElementById('status').value = loan.status;
        document.getElementById('email').value = loan.email || '';
        document.getElementById('phoneNumber').value = loan.phoneNumber || '';
        document.getElementById('income').value = loan.income || '';
        document.getElementById('creditScore').value = loan.creditScore || '';

        // Store the ID of the loan being edited
        editingLoanId = loan.id;

        // Change button visibility for update mode
        submitButton.classList.add('hidden');
        updateButton.classList.remove('hidden');
        cancelButton.classList.remove('hidden');

        showMessage(`Editing Loan ID: ${id}`, 'success');

    } catch (error) {
        console.error('Error fetching loan for edit:', error);
        showMessage('Failed to load loan for editing.', 'error');
    }
}

/**
 * Deletes a loan application.
 * @param {number} id The ID of the loan to delete.
 */
async function deleteLoan(id) {
    if (!confirm('Are you sure you want to delete this loan application?')) {
        return; // User cancelled
    }

    try {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'DELETE'
        });
        if (response.status === 204) { // 204 No Content is returned for successful deletion
            showMessage('Loan application deleted successfully!', 'success');
            fetchAndDisplayLoans(); // Refresh the list
        } else if (response.status === 404) {
            showMessage('Loan application not found.', 'error');
        } else {
            throw new Error(`Failed to delete loan. Status: ${response.status}`);
        }
    } catch (error) {
        console.error('Error deleting loan application:', error);
        showMessage(`Error: ${error.message}`, 'error');
    }
}

/**
 * Resets the form and switches back to "Add Loan" mode.
 */
function resetFormForAdd() {
    editingLoanId = null;
    loanForm.reset();
    submitButton.classList.remove('hidden');
    updateButton.classList.add('hidden');
    cancelButton.classList.add('hidden');
}

// Event listener for the Cancel Update button
cancelButton.addEventListener('click', resetFormForAdd);

// Initial fetch and display of loans when the page loads
document.addEventListener('DOMContentLoaded', fetchAndDisplayLoans);

