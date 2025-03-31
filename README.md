# ssc-y24t2-backend-import-ai-copliot

ssc-y24t2-backend-import-ai-copliot created by GitHub Classroom

## Group Name: Import AI.Copilot

### Members:
- Chinanard Sathitseth 6481366
- Hanna Hahn 6481334
- Sareena Aulla 6481197
- Haicheng Wang 6580244

## Access Remote Server with Passwordless Login
```sh
ssh newuser@152.42.204.118
```

## Allow Ports
- HTTP and HTTPS
- 3306

# SSC Project - Git ReadMe

## Project Overview
SSC is an e-commerce platform featuring distinct functionalities for Admins and Buyers. Admins can manage inventory, assign roles, and track orders, while Buyers can browse and purchase products, completing transactions through a credit card payment system.

## Features & TODO List
- Login & Register
- Admin Homepage 
- Buyer Homepage which includes Dashboard
- Transaction page
- Shipping Status page
- Order Summary Page

### Login and Register
Login and register functionalities are combined into a single page.

## Payment Rules
The "Transaction" page allows users to fill in the address and credit card number. Validation rules include:
- Only 10-digit card numbers are allowed. If it is not 10 digits, the purchase fails.
- If the card number starts with **111**, the purchase succeeds.
- If the card number starts with any other number (e.g., 123, 011, 999), the purchase fails.

## Access Control and Redirection
- Unauthorized users attempting to access non-existent or restricted pages are automatically redirected to the buyer homepage.
- Users must log in before accessing any resources; otherwise, they are redirected to the login page.
- New users are automatically assigned the "Buyer" role upon registration.
- If a user does not exist, they must register before accessing any pages.
- Only Admins can access `/admin-home` to manage products and grant user roles.

## Credit Card Validation
- Only 10-digit card numbers are allowed.
- Buyers must fill in the cardholder name, CVC number, and expiration date.
- Card number prefix validation rules apply.
- A randomized tracking number is auto-generated.
- After a successful transaction, the order is placed and shipped.

## Tools & Technology Stack
### Development Tools & IDEs
- Version Control: GitHub
- IDEs: WebStorm (for Vue.js), IntelliJ IDEA (for Spring Boot), Visual Studio

### Backend
- Framework: Spring Boot 3.3.0
- Security: Spring Security, Thymeleaf Extras for Spring Security
- Data Management: Spring Data JPA, Spring Data JDBC, Spring Boot Data REST
- Database: MariaDB

### Hosting & Cloud Services
- Virtual Machine: DigitalOcean
- Cloud Storage: Google Cloud Storage (for image management)
- Storage Integration: Spring Cloud GCP Storage

