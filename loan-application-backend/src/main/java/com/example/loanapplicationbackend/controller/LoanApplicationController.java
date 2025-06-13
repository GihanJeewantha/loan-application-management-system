package com.example.loanapplicationbackend.controller;

import com.example.loanapplicationbackend.model.LoanApplication;
import com.example.loanapplicationbackend.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing loan applications.
 * Exposes endpoints for CRUD operations on LoanApplication entities.
 * The @CrossOrigin annotation allows requests from different origins (e.g., your frontend HTML).
 */
@RestController // Marks this class as a Spring REST Controller.
@RequestMapping("/api/loans") // Base path for all endpoints in this controller.
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}) // Allows requests from any origin (for development).
// In production, you would specify your exact frontend domain, e.g., @CrossOrigin(origins = "http://localhost:3000")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    /**
     * Constructor for LoanApplicationController.
     * Spring injects the LoanApplicationService instance.
     * @param loanApplicationService The service layer for loan applications.
     */
    @Autowired
    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    /**
     * GET /api/loans
     * Retrieves all loan applications.
     * @return A ResponseEntity containing a list of LoanApplication objects and HTTP status 200 OK.
     */
    @GetMapping // Maps HTTP GET requests to /api/loans
    public ResponseEntity<List<LoanApplication>> getAllLoanApplications() {
        List<LoanApplication> loans = loanApplicationService.getAllLoanApplications();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    /**
     * GET /api/loans/{id}
     * Retrieves a single loan application by its ID.
     * @param id The ID of the loan application to retrieve.
     * @return A ResponseEntity with the LoanApplication object and HTTP status 200 OK if found,
     * or HTTP status 404 NOT FOUND if not found.
     */
    @GetMapping("/{id}") // Maps HTTP GET requests to /api/loans/{id}
    public ResponseEntity<LoanApplication> getLoanApplicationById(@PathVariable Long id) {
        Optional<LoanApplication> loan = loanApplicationService.getLoanApplicationById(id);
        return loan.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST /api/loans
     * Creates a new loan application.
     * @param loanApplication The LoanApplication object to create (sent in the request body).
     * @return A ResponseEntity with the created LoanApplication object and HTTP status 201 CREATED.
     */
    @PostMapping // Maps HTTP POST requests to /api/loans
    public ResponseEntity<LoanApplication> createLoanApplication(@RequestBody LoanApplication loanApplication) {
        LoanApplication createdLoan = loanApplicationService.createLoanApplication(loanApplication);
        return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
    }

    /**
     * PUT /api/loans/{id}
     * Updates an existing loan application.
     * @param id The ID of the loan application to update.
     * @param loanApplication The LoanApplication object with updated data (sent in the request body).
     * @return A ResponseEntity with the updated LoanApplication object and HTTP status 200 OK if found and updated,
     * or HTTP status 404 NOT FOUND if the original application was not found.
     */
    @PutMapping("/{id}") // Maps HTTP PUT requests to /api/loans/{id}
    public ResponseEntity<LoanApplication> updateLoanApplication(@PathVariable Long id, @RequestBody LoanApplication loanApplication) {
        LoanApplication updatedLoan = loanApplicationService.updateLoanApplication(id, loanApplication);
        if (updatedLoan != null) {
            return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * DELETE /api/loans/{id}
     * Deletes a loan application by its ID.
     * @param id The ID of the loan application to delete.
     * @return A ResponseEntity with HTTP status 204 NO CONTENT on successful deletion,
     * or HTTP status 404 NOT FOUND if the application to delete was not found.
     */
    @DeleteMapping("/{id}") // Maps HTTP DELETE requests to /api/loans/{id}
    public ResponseEntity<Void> deleteLoanApplication(@PathVariable Long id) {
        Optional<LoanApplication> loan = loanApplicationService.getLoanApplicationById(id);
        if (loan.isPresent()) {
            loanApplicationService.deleteLoanApplication(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

