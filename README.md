# Secondhand E-commerce Platform - Brand Name: Rewear

## Frontend repository: [Frontend Repo](https://github.com/Jac3ie/secondhand-ecommerce-rewear-frontend.git)

## Group Name: Import AI.Copilot

### Members:
- Chinanard Sathitseth 6481366 
- Hanna Hahn 6481334
- Sareena Aulla 6481197 
- Haicheng Wang 6580244

## Overview
Backend for **Rewear**, a second-hand e-commerce web app providing REST APIs for admin / buyer experiences.

### What this backend does
- Provides **session-based authentication** and role-based endpoints using **Spring Security**.
- Exposes REST APIs for:
  - **Admin**: product management (CRUD), sold product review, list all buyers to uplift priviledges
  - **Buyer**: product browsing + search, checkout/purchase flow (simulated)
  - **Account**: address management, payment method save/retrieve, order history
- Persists data in **MariaDB** via **Spring Data JPA**, and integrates **Google Cloud Storage (GCS)** for product image upload/access.

## Tech Stack (Backend)
- Spring Boot 3
- Spring Security (session-based auth)
- Spring Data JPA (Hibernate) + MariaDB JDBC driver
- MariaDB running on Docker
- GCS for image storage

## Payment Rules
> Note: This project simulated transactions for course demonstration, not a real payment integration.

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

## API Overview

Default: `http://localhost:8081`

### Auth
- `POST /api/login`
- `POST /api/logout`
- `GET /api/whoami`

### Registration
- `POST /api/register`

### Buyer Product
- `GET /api/products`
- `GET /api/products/search?query=...`

### Admin Product
- `GET /api/admin/products`
- `POST /api/admin/products`
- `PUT /api/admin/products/{id}`
- `DELETE /api/admin/products/{id}`
- `GET /api/admin/products/sold`

### Orders
- `GET /api/orders/history`

### Address
- `GET /api/address`
- `POST /api/address`

### Payment
- `GET /api/payments`
- `POST /api/payments`
- `POST /api/payments/purchase/{productId}`

## Usage (Local)

### 1) Start Database (MariaDB via Docker)
> Note: need a docker compose file to bring DB up. Will come back to update the docker file to create a skeleton DB.

Example (Docker Compose):
```bash
docker compose up -d
```

### 2) Configure application.properties referred to the .example one in the project root
- Initialize GCS bucket on GCP & add bucket credentials to local environment
- Fill in DB URL/user/password and bucket name
- Verify the DB connection status

### 3) Run Backend
```bash
mvn spring-boot:run
```

### 4) Quick API Test via Postman (referred to API Overview above):
<img width="1345" height="750" alt="image" src="https://github.com/user-attachments/assets/237cb24f-79fd-4fd1-b01a-ee43396c9429" />


