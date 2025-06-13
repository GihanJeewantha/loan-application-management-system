package com.example.loanapplicationbackend.service;
import com.example.loanapplicationbackend.model.LoanApplication;
import com.example.loanapplicationbackend.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing loan applications.
 * This class encapsulates the business logic and orchestrates data access
 * through the LoanApplicationRepository.
 */
@Service // Marks this class as a Spring Service component, indicating it holds business logic.
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

    /**
     * Constructor for LoanApplicationService.
     * Spring's @Autowired annotation automatically injects an instance
     * of LoanApplicationRepository when this service is created.
     * @param loanApplicationRepository The repository for loan application data.
     */
    @Autowired
    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
    }

    /**
     * Retrieves all loan applications from the database.
     * @return A list of all LoanApplication objects.
     */
    public List<LoanApplication> getAllLoanApplications() {
        return loanApplicationRepository.findAll();
    }

    /**
     * Retrieves a single loan application by its unique ID.
     * @param id The ID of the loan application to retrieve.
     * @return An Optional containing the LoanApplication if found, or empty if not.
     */
    public Optional<LoanApplication> getLoanApplicationById(Long id) {
        return loanApplicationRepository.findById(id);
    }

    /**
     * Creates a new loan application.
     * Sets the application date to today and default status to "PENDING" if not provided.
     * @param loanApplication The LoanApplication object to be saved.
     * @return The saved LoanApplication object, now with an assigned ID.
     */
    public LoanApplication createLoanApplication(LoanApplication loanApplication) {
        if (loanApplication.getApplicationDate() == null) {
            loanApplication.setApplicationDate(LocalDate.now()); // Set current date if not provided
        }
        if (loanApplication.getStatus() == null || loanApplication.getStatus().isEmpty()) {
            loanApplication.setStatus("PENDING"); // Default status for new applications
        }
        return loanApplicationRepository.save(loanApplication);
    }

    /**
     * Updates an existing loan application.
     * Retrieves the existing application, updates its fields with the provided data,
     * and then saves the updated application back to the database.
     * @param id The ID of the loan application to update.
     * @param updatedApplication The LoanApplication object containing the new data.
     * @return The updated LoanApplication object if found, otherwise null.
     */
    public LoanApplication updateLoanApplication(Long id, LoanApplication updatedApplication) {
        return loanApplicationRepository.findById(id)
                .map(existingLoan -> {
                    // Update fields from the provided updatedApplication
                    existingLoan.setApplicantName(updatedApplication.getApplicantName());
                    existingLoan.setLoanAmount(updatedApplication.getLoanAmount());
                    existingLoan.setApplicationDate(updatedApplication.getApplicationDate());
                    existingLoan.setStatus(updatedApplication.getStatus());
                    existingLoan.setEmail(updatedApplication.getEmail());
                    existingLoan.setPhoneNumber(updatedApplication.getPhoneNumber());
                    existingLoan.setIncome(updatedApplication.getIncome());
                    existingLoan.setCreditScore(updatedApplication.getCreditScore());
                    // Save the updated entity
                    return loanApplicationRepository.save(existingLoan);
                })
                .orElse(null); // Return null if the original application was not found by ID.
    }

    /**
     * Deletes a loan application by its ID.
     * @param id The ID of the loan application to delete.
     */
    public void deleteLoanApplication(Long id) {
        loanApplicationRepository.deleteById(id);
    }
}
