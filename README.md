# Library Management System

A comprehensive Spring Boot REST API application for managing library operations with location-aware borrowing records based on Rwandan administrative divisions.

## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Entity Relationships](#entity-relationships)
- [DTO Structure](#dto-structure)
- [API Endpoints](#api-endpoints)
- [Setup Instructions](#setup-instructions)
- [Testing the API](#testing-the-api)

---

## Project Overview

The Library Management System is a dynamic web application that helps library staff organize books, manage borrowing and returns, and track borrowers efficiently. The system includes location-aware features that track borrowers by Rwandan administrative divisions (Province → District → Sector → Cell → Village).

### Key Capabilities
- Full CRUD operations for all entities
- Location-based queries and filtering
- Book availability tracking
- Borrowing and return management
- Overdue book tracking
- Pagination and sorting support

---

## Features

### Core Functionality
✅ **5 Main Entities**: Book, Borrower, BorrowRecord, User, Location  
✅ **Full CRUD Operations**: Create, Read, Update, Delete for all entities  
✅ **DTOs for All Entities**: Clean separation of API and data layers  
✅ **Service Layer**: Business logic and DTO ↔ Entity conversion  
✅ **Repository Layer**: Spring Data JPA with custom queries  
✅ **Location-Aware**: Track borrowers and records by administrative divisions  
✅ **Pagination & Sorting**: Efficient data retrieval  

### Relationship Types Implemented
- **One-to-One**: User ↔ Location
- **One-to-Many / Many-to-One**: Borrower → Location, Book → BorrowRecord, Borrower → BorrowRecord
- **Many-to-Many**: Book ↔ Borrower (via BorrowRecord join table)

---

## Technology Stack

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring Data JPA**: Database persistence
- **Spring Web**: REST API
- **Spring Validation**: Input validation

- **PostgreSQL**: Production database (optional)
- **Lombok**: Reduce boilerplate code
- **Maven**: Dependency management

---

## Entity Relationships

### Entity Diagram

```
Location (Province, District, Sector, Cell, Village)
    ↑ (One-to-One)
    |
User (firstName, lastName, email, phoneNumber, role)

Location
    ↑ (Many-to-One)
    |
Borrower (firstName, lastName, email, phoneNumber, membershipNumber)
    |
    | (One-to-Many)
    ↓
BorrowRecord (borrowDate, dueDate, returnDate, status, notes)
    ↑
    | (Many-to-One)
Book (isbn, title, author, publisher, publicationYear, category, availableCopies, totalCopies)
```

### Relationship Details

1. **User ↔ Location** (One-to-One)
   - Each user has exactly one location
   - Used for staff/librarian address tracking

2. **Borrower → Location** (Many-to-One)
   - Multiple borrowers can share a location
   - Enables location-based queries

3. **Book ↔ Borrower** (Many-to-Many via BorrowRecord)
   - A book can be borrowed by many borrowers over time
   - A borrower can borrow many books
   - BorrowRecord acts as the join table with additional metadata

4. **Book → BorrowRecord** (One-to-Many)
   - A book can have multiple borrow records

5. **Borrower → BorrowRecord** (One-to-Many)
   - A borrower can have multiple borrow records

---

## DTO Structure

### Purpose of DTOs
DTOs (Data Transfer Objects) provide clean API contracts and prevent exposure of internal entity structure. All services handle DTO ↔ Entity conversion.

### Available DTOs

#### 1. **LocationDTO**
```json
{
  "id": 1,
  "province": "Kigali City",
  "district": "Gasabo",
  "sector": "Remera",
  "cell": "Rukiri I",
  "village": "Amahoro"
}
```

#### 2. **UserDTO**
```json
{
  "id": 1,
  "firstName": "Uwase",
  "lastName": "Diane",
  "email": "uwase.diane@auca.ac.rw",
  "phoneNumber": "+250788123456",
  "role": "LIBRARIAN",
  "location": { /* LocationDTO */ }
}
```

#### 3. **BookDTO**
```json
{
  "id": 1,
  "isbn": "978-3-16-148410-0",
  "title": "Introduction to Algorithms",
  "author": "Thomas H. Cormen",
  "publisher": "MIT Press",
  "publicationYear": 2009,
  "category": "Computer Science",
  "availableCopies": 5,
  "totalCopies": 10
}
```

#### 4. **BorrowerDTO**
```json
{
  "id": 1,
  "firstName": "Mugabo",
  "lastName": "Eric",
  "email": "mugabo.eric@gmail.com",
  "phoneNumber": "+250788654321",
  "membershipNumber": "LIB2024001",
  "location": { /* LocationDTO */ }
}
```

#### 5. **BorrowRecordDTO** (Full Details)
```json
{
  "id": 1,
  "book": { /* BookDTO */ },
  "borrower": { /* BorrowerDTO */ },
  "borrowDate": "2024-01-15",
  "dueDate": "2024-02-15",
  "returnDate": null,
  "status": "BORROWED",
  "notes": "First-time borrower"
}
```

#### 6. **BorrowRecordSummaryDTO** (Simplified with Location)
```json
{
  "id": 1,
  "bookId": 1,
  "bookTitle": "Introduction to Algorithms",
  "bookIsbn": "978-3-16-148410-0",
  "borrowerId": 1,
  "borrowerName": "Mugabo Eric",
  "borrowerMembershipNumber": "LIB2024001",
  "borrowerProvince": "Kigali City",
  "borrowerDistrict": "Gasabo",
  "borrowerSector": "Remera",
  "borrowDate": "2024-01-15",
  "dueDate": "2024-02-15",
  "returnDate": null,
  "status": "BORROWED"
}
```

---

## API Endpoints

### Base URL
```
http://localhost:8080/api
```

### 1. Location Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/locations` | Create new location |
| GET | `/locations` | Get all locations |
| GET | `/locations?paginated=true` | Get locations with pagination |
| GET | `/locations/{id}` | Get location by ID |
| PUT | `/locations/{id}` | Update location |
| DELETE | `/locations/{id}` | Delete location |
| GET | `/locations/province/{province}` | Get locations by province |
| GET | `/locations/district/{district}` | Get locations by district |
| GET | `/locations/province/{province}/district/{district}` | Get by province and district |
| GET | `/locations/hierarchy/provinces` | Get all provinces |
| GET | `/locations/hierarchy/provinces/{province}/districts` | Get districts by province |
| GET | `/locations/hierarchy/districts/{district}/sectors` | Get sectors by district |

### 2. User Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users` | Create new user |
| GET | `/users` | Get all users |
| GET | `/users?paginated=true` | Get users with pagination |
| GET | `/users/{id}` | Get user by ID |
| PUT | `/users/{id}` | Update user |
| DELETE | `/users/{id}` | Delete user |
| GET | `/users/search?name={name}` | Search users by name |
| GET | `/users/role/{role}` | Get users by role |
| GET | `/users/province/{province}` | Get users by province |
| GET | `/users/district/{district}` | Get users by district |
| GET | `/users/province/{province}/district/{district}` | Get by province and district |

### 3. Book Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/books` | Create new book |
| GET | `/books` | Get all books |
| GET | `/books?paginated=true` | Get books with pagination |
| GET | `/books/{id}` | Get book by ID |
| PUT | `/books/{id}` | Update book |
| DELETE | `/books/{id}` | Delete book |
| GET | `/books/search?query={query}` | Search by title or author |
| GET | `/books/search/title?title={title}` | Search by title |
| GET | `/books/search/author?author={author}` | Search by author |
| GET | `/books/category/{category}` | Get books by category |
| GET | `/books/available` | Get available books |
| GET | `/books/year/{year}` | Get books by publication year |

### 4. Borrower Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/borrowers` | Create new borrower |
| GET | `/borrowers` | Get all borrowers |
| GET | `/borrowers?paginated=true` | Get borrowers with pagination |
| GET | `/borrowers/{id}` | Get borrower by ID |
| PUT | `/borrowers/{id}` | Update borrower |
| DELETE | `/borrowers/{id}` | Delete borrower |
| GET | `/borrowers/search?name={name}` | Search borrowers by name |
| GET | `/borrowers/province/{province}` | Get borrowers by province |
| GET | `/borrowers/district/{district}` | Get borrowers by district |
| GET | `/borrowers/sector/{sector}` | Get borrowers by sector |
| GET | `/borrowers/province/{province}/district/{district}` | Get by province and district |

### 5. Borrow Record Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/borrowrecords` | Create borrow record (borrow book) |
| GET | `/borrowrecords` | Get all borrow records |
| GET | `/borrowrecords?paginated=true` | Get records with pagination |
| GET | `/borrowrecords/summary` | Get summary with location info |
| GET | `/borrowrecords/{id}` | Get borrow record by ID |
| PUT | `/borrowrecords/{id}` | Update borrow record |
| PUT | `/borrowrecords/{id}/return` | Return book |
| DELETE | `/borrowrecords/{id}` | Delete borrow record |
| GET | `/borrowrecords/borrower/{borrowerId}` | Get by borrower |
| GET | `/borrowrecords/book/{bookId}` | Get by book |
| GET | `/borrowrecords/status/{status}` | Get by status |
| GET | `/borrowrecords/overdue` | Get overdue records |
| GET | `/borrowrecords/province/{province}` | Get by province |
| GET | `/borrowrecords/district/{district}` | Get by district |
| GET | `/borrowrecords/sector/{sector}` | Get by sector |
| GET | `/borrowrecords/province/{province}/district/{district}` | Get by province and district |

---



1. **Clone or navigate to the project directory**
```bash
cd D:\SpringBoot\26025_Library_Management_System
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```



---

## Testing the API

### Example: Create a Location
```bash
POST http://localhost:8080/api/locations
Content-Type: application/json

{
  "province": "Kigali City",
  "district": "Gasabo",
  "sector": "Remera",
  "cell": "Rukiri I",
  "village": "Amahoro"
}
```

### Example: Create a Borrower
```bash
POST http://localhost:8080/api/borrowers
Content-Type: application/json

{
  "firstName": "Mugabo",
  "lastName": "Eric",
  "email": "mugabo.eric@gmail.com",
  "phoneNumber": "+250788654321",
  "membershipNumber": "LIB2024001",
  "location": {
    "province": "Kigali City",
    "district": "Gasabo",
    "sector": "Remera",
    "cell": "Rukiri I",
    "village": "Amahoro"
  }
}
```

### Example: Create a Book
```bash
POST http://localhost:8080/api/books
Content-Type: application/json

{
  "isbn": "978-3-16-148410-0",
  "title": "Introduction to Algorithms",
  "author": "Thomas H. Cormen",
  "publisher": "MIT Press",
  "publicationYear": 2009,
  "category": "Computer Science",
  "availableCopies": 5,
  "totalCopies": 10
}
```

### Example: Borrow a Book
```bash
POST http://localhost:8080/api/borrowrecords
Content-Type: application/json

{
  "book": {
    "id": 1
  },
  "borrower": {
    "id": 1
  },
  "borrowDate": "2024-01-15",
  "dueDate": "2024-02-15",
  "status": "BORROWED",
  "notes": "First-time borrower"
}
```

### Example: Get Borrowers by Province
```bash
GET http://localhost:8080/api/borrowers/province/Kigali%20City
```

### Example: Get Borrow Records by District with Location Info
```bash
GET http://localhost:8080/api/borrowrecords/district/Gasabo
```

### Example: Search Books
```bash
GET http://localhost:8080/api/books/search?query=Algorithm
```

### Example: Get Overdue Books
```bash
GET http://localhost:8080/api/borrowrecords/overdue
```

---

## Project Structure

```
src/main/java/rw/ac/rca/library/
├── LibraryManagementSystemApplication.java
├── entity/
│   ├── Location.java
│   ├── User.java
│   ├── Book.java
│   ├── Borrower.java
│   └── BorrowRecord.java
├── dto/
│   ├── LocationDTO.java
│   ├── UserDTO.java
│   ├── BookDTO.java
│   ├── BorrowerDTO.java
│   ├── BorrowRecordDTO.java
│   └── BorrowRecordSummaryDTO.java
├── repository/
│   ├── LocationRepository.java
│   ├── UserRepository.java
│   ├── BookRepository.java
│   ├── BorrowerRepository.java
│   └── BorrowRecordRepository.java
├── service/
│   ├── LocationService.java
│   ├── UserService.java
│   ├── BookService.java
│   ├── BorrowerService.java
│   └── BorrowRecordService.java
└── controller/
    ├── LocationController.java
    ├── UserController.java
    ├── BookController.java
    ├── BorrowerController.java
    └── BorrowRecordController.java
```

---

## Key Features Implemented

### ✅ Full CRUD Operations
- All entities support Create, Read, Update, Delete operations
- Validation at DTO level using Jakarta Validation
- Exception handling for not found and duplicate entries

### ✅ DTO Pattern
- Clean separation between API contracts (DTOs) and database entities
- Service layer handles all conversions
- Nested DTOs for related entities

### ✅ Location-Aware Queries
- Query borrowers by province, district, or sector
- Query borrow records with borrower location information
- Hierarchical location queries (provinces → districts → sectors)

### ✅ Pagination & Sorting
- Optional pagination on all major endpoints
- Configurable page size and sort order
- Default sorting by relevant fields

### ✅ Repository Custom Queries
- Spring Data JPA method naming conventions
- Custom JPQL queries for complex operations
- Existential queries for validation

### ✅ Relationship Management
- One-to-One: User ↔ Location
- Many-to-One: Borrower → Location
- Many-to-Many: Book ↔ Borrower (via BorrowRecord)
- Proper cascade operations

### ✅ Business Logic
- Book availability tracking (decrease on borrow, increase on return)
- Duplicate prevention (ISBN, email, membership number)
- Status management (BORROWED, RETURNED, OVERDUE)
- Overdue record detection

---




---

## License

This project is created for educational purposes.
