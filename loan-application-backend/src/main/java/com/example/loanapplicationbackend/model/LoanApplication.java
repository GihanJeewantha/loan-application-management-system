package com.example.loanapplicationbackend.model;

import jakarta.persistence.*; // Use jakarta.persistence for Spring Boot 3+
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a single loan application in the system.
 * This class maps to the 'loan_applications' table in the database.
 * Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor)
 * are used to reduce boilerplate code for getters, setters, constructors, etc.
 */
@Entity // Marks this class as a JPA entity, indicating it maps to a database table.
@Table(name = "loan_applications") // Specifies the exact table name in the database.
@Data // Lombok: Generates getters, setters, equals, hashCode, and toString methods.
@NoArgsConstructor // Lombok: Generates a no-argument constructor (required by JPA).
@AllArgsConstructor // Lombok: Generates a constructor with all fields as arguments.
public class LoanApplication {

    @Id // Marks 'id' as the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures the ID to be auto-incremented by the database.
    private Long id;

    @Column(name = "applicant_name", nullable = false) // Maps to 'applicant_name' column; 'nullable = false' means it cannot be empty.
    private String applicantName;

    @Column(name = "loan_amount", nullable = false) // Maps to 'loan_amount' column; cannot be empty.
    private BigDecimal loanAmount;

    @Column(name = "application_date", nullable = false) // Maps to 'application_date' column; cannot be empty.
    private LocalDate applicationDate;

    @Column(name = "status", nullable = false) // Maps to 'status' column; cannot be empty.
    private String status; // Possible values: "PENDING", "APPROVED", "REJECTED"

    @Column(name = "email") // Maps to 'email' column.
    private String email;

    @Column(name = "phone_number") // Maps to 'phone_number' column.
    private String phoneNumber;

    @Column(name = "income") // Maps to 'income' column.
    private BigDecimal income;

    @Column(name = "credit_score") // Maps to 'credit_score' column.
    private Integer creditScore;
}

