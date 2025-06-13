package com.example.loanapplicationbackend.repository;

import com.example.loanapplicationbackend.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for LoanApplication entities.
 * Extends JpaRepository to inherit basic CRUD (Create, Read, Update, Delete) operations.
 * Spring Data JPA automatically provides implementations for these methods at runtime.
 */
@Repository // Marks this interface as a Spring Data JPA repository component.
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    // JpaRepository provides methods like save(), findById(), findAll(), deleteById(), etc.
    // No implementation needed here; Spring Data JPA handles it.

    // Example of a custom query method (uncomment if you want to use it)
    // List<LoanApplication> findByStatus(String status);
}