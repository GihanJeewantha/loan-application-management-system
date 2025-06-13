# Loan Application Management System

This project is a simple, full-stack web application for managing loan applications. It features a Spring Boot backend with a RESTful API, a MySQL database for data persistence, and a pure HTML, CSS, and JavaScript frontend.

This system can serve as a showcase for core development skills, including backend API development, database integration, and basic frontend interaction, making it an excellent addition to a graduate trainee's portfolio.

## Features

* **Create Loan Applications**: Submit new loan applications with details like applicant name, loan amount, application date, status, email, phone, income, and credit score.
* **View All Applications**: Display a table of all existing loan applications.
* **Update Applications**: Edit the details of an existing loan application.
* **Delete Applications**: Remove loan applications from the system.
* **Responsive Design**: A basic responsive layout using Tailwind CSS ensures usability on different screen sizes.
* **RESTful API**: A well-structured backend API for CRUD operations on loan applications.

## Technologies Used

### Backend (Spring Boot)
* **Java 17+**
* **Spring Boot 3.3.1+**: Framework for building robust web applications.
* **Spring Data JPA**: Simplifies database access and provides an Object-Relational Mapping (ORM) layer.
* **MySQL**: Relational database for storing loan application data.
* **Maven**: Build automation tool.
* **Lombok**: Reduces boilerplate code (getters, setters, constructors).

### Frontend (Web)
* **HTML5**: Structure of the web page.
* **CSS3 (Tailwind CSS)**: For styling and responsive design.
* **JavaScript (ES6+)**: For dynamic behavior and interacting with the backend API.


## Setup and Running the Application

Follow these steps to get the application running on your local machine.

### Prerequisites

* **Java Development Kit (JDK) 17+**
* **Apache Maven 3.8+**
* **MySQL Server 8+**
* **MySQL Workbench (Optional but Recommended)**
* An Integrated Development Environment (IDE) like **IntelliJ IDEA** (Community Edition), Eclipse, or VS Code.

### 1. Database Setup (MySQL)

1.  Ensure your MySQL Server is running.
2.  Open MySQL Workbench or your MySQL command-line client.
3.  Execute the following SQL script to create the database and table:

    ```sql
    -- Create the database if it doesn't exist
    CREATE DATABASE IF NOT EXISTS loan_application_db;

    -- Use the newly created database
    USE loan_application_db;

    -- Create the loan_applications table
    CREATE TABLE IF NOT EXISTS loan_applications (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        applicant_name VARCHAR(255) NOT NULL,
        loan_amount DECIMAL(19, 2) NOT NULL,
        application_date DATE NOT NULL,
        status VARCHAR(50) NOT NULL, -- e.g., PENDING, APPROVED, REJECTED
        email VARCHAR(255),
        phone_number VARCHAR(20),
        income DECIMAL(19, 2),
        credit_score INT
    );

    -- Optional: Insert some sample data to test
    INSERT INTO loan_applications (applicant_name, loan_amount, application_date, status, email, phone_number, income, credit_score) VALUES
    ('Alice Smith', 50000.00, '2024-05-01', 'PENDING', 'alice.smith@example.com', '123-456-7890', 60000.00, 720),
    ('Bob Johnson', 120000.00, '2024-05-10', 'APPROVED', 'bob.j@example.com', '987-654-3210', 85000.00, 780),
    ('Charlie Brown', 25000.00, '2024-05-15', 'REJECTED', 'charlie.b@example.com', '555-123-4567', 40000.00, 600);
    ```

### 2. Spring Boot Backend Setup

1.  **Clone the repository:** (Once you push this project to GitHub)
    ```bash
    git clone [https://github.com/yourusername/loan-application-management-system.git](https://github.com/yourusername/loan-application-management-system.git)
    cd loan-application-management-system/loan-application-backend
    ```
    *(If you haven't pushed yet, just navigate to the `loan-application-backend` directory you unzipped from Spring Initializr)*

2.  **Configure `application.properties`**:
    Open `src/main/resources/application.properties` and update the database connection details:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/loan_application_db?useSSL=false&serverTimezone=UTC
    spring.datasource.username=YOUR_MYSQL_USERNAME # <--- IMPORTANT: Replace with your actual MySQL username
    spring.datasource.password=YOUR_MYSQL_PASSWORD # <--- IMPORTANT: Replace with your actual MySQL password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

    # JPA/Hibernate Configuration
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

    # Server Port
    server.port=8080

    # CORS Configuration
    spring.web.cors.enabled=true
    spring.web.cors.allowed-origins=*
    spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
    spring.web.cors.allowed-headers=*
    spring.web.cors.allow-credentials=true
    ```

3.  **Run the Spring Boot Application:**
    * **Using your IDE:** Open the `loan-application-backend` project in your IDE (e.g., IntelliJ IDEA). Locate `LoanApplicationBackendApplication.java` (in `src/main/java/com/mybank/loanapplicationbackend/`). Right-click on it and select "Run" or click the green play icon.
    * **Using Maven (Command Line):** Navigate to the `loan-application-backend` directory in your terminal and run:
        ```bash
        mvn spring-boot:run
        ```

    The application will start, and you should see logs indicating it's running on `http://localhost:8080`.

### 3. Access the Frontend

Once the Spring Boot backend is running, open your web browser and navigate to:

`http://localhost:8080/`

You should now see the Loan Application Management System interface, ready for interaction.

## API Endpoints (Backend)

The Spring Boot backend exposes the following RESTful endpoints:

| Method | Endpoint             | Description                       | Request Body (JSON)                                                                                                   | Response Body (JSON)                                                                                               |
| :----- | :------------------- | :-------------------------------- | :-------------------------------------------------------------------------------------------------------------------- | :----------------------------------------------------------------------------------------------------------------- |
| `GET`  | `/api/loans`         | Get all loan applications         | None                                                                                                                  | `[{ id, applicantName, loanAmount, ... }, ...]`                                                                    |
| `GET`  | `/api/loans/{id}`    | Get loan application by ID        | None                                                                                                                  | `{ id, applicantName, loanAmount, ... }` (or 404 if not found)                                                    |
| `POST` | `/api/loans`         | Create a new loan application     | `{ "applicantName": "John Doe", "loanAmount": 10000.00, "applicationDate": "2024-06-14", "status": "PENDING", ... }` | `{ id, applicantName, loanAmount, ... }` (newly created loan)                                                      |
| `PUT`  | `/api/loans/{id}`    | Update an existing loan application | `{ "applicantName": "John Doe", "loanAmount": 12000.00, "status": "APPROVED", ... }`                                   | `{ id, applicantName, loanAmount, ... }` (updated loan) (or 404 if not found)                                      |
| `DELETE`| `/api/loans/{id}`   | Delete a loan application by ID   | None                                                                                                                  | No Content (204) on success (or 404 if not found)                                                                  |

## Contributing

Feel free to fork this repository, make improvements, and submit pull requests.

## License

This project is open-source and available under the [MIT License](LICENSE).
