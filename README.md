# Invoicing Backend

## Overview
This is a Spring Boot-based backend service for managing invoices. It includes user authentication, role-based access control (RBAC), and integration with MySQL as the database.

## Features
- User authentication and authorization (Admin and User roles)
- Role-based access control (RBAC)
- Invoice management
- MySQL database support
- RESTful API for frontend integration

## Technologies Used
- Java (Spring Boot)
- MySQL (Database)
- Maven (Build Tool)

## Prerequisites
Ensure you have the following installed:
- Java 17+
- Maven
- MySQL database

## Installation & Setup

### 1. Clone the Repository
```sh
git clone https://github.com/sandaru-sdm/invoicing.git
cd invoicing
```

### 2. Configure Environment Variables
Create an `.env` file and set your environment variables:
```
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/invoicing
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password
```

### 3. Build and Run the Application
```sh
mvn clean install
mvn spring-boot:run
```

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - Register a new user (Admin only)

### Invoice Management
- `POST /api/invoices` - Create a new invoice
- `GET /api/invoices/{id}` - Get invoice details
- `PUT /api/invoices/{id}` - Update invoice
- `DELETE /api/invoices/{id}` - Delete an invoice

## License
This project is licensed under the MIT License.
