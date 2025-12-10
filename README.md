# 🌍 Smart Travel Booking Platform

### ITS 4243 – Microservices and Cloud Computing | Assignment 02

**University of Sri Jayewardenepura – Faculty of Technology – Department of ICT**

---

## 📌 1. Project Introduction

This project is a **distributed Smart Travel Booking Platform** built using **Spring Boot microservices architecture**.
It consists of **six independent microservices**, each running on a separate port with its **own dedicated MySQL database**, and communicating via:

* ✅ **REST Controllers**
* ✅ **WebClient**
* ✅ **Feign Client**
* ❌ **No RestTemplate (Strict Assignment Rule)**

The **Booking Service** acts as the **Main Orchestrator** that coordinates all other services.

---

## 🧩 2. Microservices Overview (Final & Corrected)

| Service Name               | Port | Database Name             | Responsibility                | Communication                            |
| -------------------------- | ---- | ------------------------- | ----------------------------- | ---------------------------------------- |
| **User Service**           | 8081 | `user_service_db`         | Manage users                  | Booking → User via **WebClient**         |
| **Flight Service**         | 8082 | `flight_service_db`       | Flight availability & pricing | Booking → Flight via **Feign Client**    |
| **Hotel Service**          | 8083 | `hotel_service_db`        | Hotel availability & pricing  | Booking → Hotel via **Feign Client**     |
| **Booking Service**        | 8084 | `booking_service_db`      | Main Orchestrator             | Calls all services                       |
| **Payment Service**        | 8085 | `payment_service_db`      | Handle payments               | Booking → Payment via **WebClient**      |
| ✅ **Notification Service** | 8086 | `notification_service_db` | Stores & sends notifications  | Booking → Notification via **WebClient** |

✅ **All 6 services use their own dedicated MySQL databases**

---

## 🛠️ 3. Technologies Used

* Java 17
* Spring Boot 3+
* Spring Web
* Spring WebFlux (WebClient)
* OpenFeign
* Spring Data JPA
* MySQL (XAMPP)
* Maven
* Swagger / SpringDoc OpenAPI
* Postman

---

## 💾 4. Database Setup (Using XAMPP + phpMyAdmin)

### ✅ Step 1: Start XAMPP

Start:

* ✅ Apache
* ✅ MySQL

Open:

```
http://localhost/phpmyadmin
```

---

### ✅ Step 2: Create Required Databases

```sql
CREATE DATABASE user_service_db;
CREATE DATABASE flight_service_db;
CREATE DATABASE hotel_service_db;
CREATE DATABASE booking_service_db;
CREATE DATABASE payment_service_db;
CREATE DATABASE notification_service_db;
```

✅ Tables are **auto-created by Spring Boot using JPA**

---

## 📁 5. Project Folder Structure

```
smart-travel-platform/
├── user-service/
├── flight-service/
├── hotel-service/
├── booking-service/
├── payment-service/
└── notification-service/
```

Each service includes:

* controller
* service
* repository
* dto
* entity
* config
* exception

---

## 📥 6. How to Clone the Project from GitHub

```bash
git clone https://github.com/lakshanravindu21/smart-travel-platform.git
cd smart-travel-platform
```

---

## ⚙️ 7. How to Run Each Microservice ✅ (AS REQUESTED)

✅ Open **6 separate terminals**

For each service:

```bash
cd user-service
mvn clean install
mvn spring-boot:run
```

```bash
cd flight-service
mvn clean install
mvn spring-boot:run
```

```bash
cd hotel-service
mvn clean install
mvn spring-boot:run
```

```bash
cd booking-service
mvn clean install
mvn spring-boot:run
```

```bash
cd payment-service
mvn clean install
mvn spring-boot:run
```

```bash
cd notification-service
mvn clean install
mvn spring-boot:run
```

✅ All services must be running before testing bookings.

---

## 🌐 8. Inter-Service Communication Flow (Assignment Required)

| From                   | To        | 
| ---------------------- | --------- | 
| Booking → User         | WebClient |            
| Booking → Flight       | Feign     |            
| Booking → Hotel        | Feign     |            
| Booking → Payment      | WebClient |            
| Booking → Notification | WebClient |            

---

## 🔄 9. Complete Booking Workflow

### ✅ Step 1: Client sends booking request

```json
{
  "userId": 1,
  "flightId": 200,
  "hotelId": 55,
  "travelDate": "2025-01-10"
}
```

### ✅ Step 2: User validation (WebClient)

### ✅ Step 3: Flight check (Feign)

### ✅ Step 4: Hotel check (Feign)

### ✅ Step 5: Cost calculation

### ✅ Step 6: Booking saved as PENDING

### ✅ Step 7: Payment processed (WebClient)

### ✅ Step 8: Notification sent (WebClient)

### ✅ Step 9: Booking updated to CONFIRMED

---

## 🧪 10. Testing Using Postman

| Service      | Endpoint                                              |
| ------------ | ----------------------------------------------------- |
| User         | `http://localhost:8081/api/users/{id}`                |
| Flight       | `http://localhost:8082/api/flights/{id}/availability` |
| Hotel        | `http://localhost:8083/api/hotels/{id}/availability`  |
| Booking      | `http://localhost:8084/api/bookings`                  |
| Payment      | `http://localhost:8085/api/payments`                  |
| Notification | `http://localhost:8086/api/notifications`             |

---

## 📤 11. Postman Collection

✅ The exported Postman Collection is uploaded here:

```
/postman/smart-travel-platform.postman_collection.json
```

---

## 🧱 12. Architecture Diagram (For PDF)

```
                ┌──────────────────┐
                │   User Service   │
                └─────────▲────────┘
                          │ WebClient
                          │
┌───────────────┐   Feign │             Feign   ┌──────────────────┐
│ Flight Service │◄────────┤  Booking Service   ├────────►│ Hotel Service │
└───────────────┘         │  (Orchestrator)    │        └──────────────────┘
                          │
                          │ WebClient
                          ▼
                 ┌──────────────────┐
                 │ Payment Service  │
                 └─────────▲────────┘
                           │ WebClient
                           ▼
              ┌──────────────────────────┐
              │ Notification Service      │
              │ (With Database Logging)   │
              └──────────────────────────┘
```

---

## ✅ 13. Assignment Requirements Checklist (Fully Matched)

✔ 6 Independent Microservices
✔ ✅ Separate Databases for **ALL Services (Including Notification)**
✔ Feign Clients used (Flight & Hotel)
✔ WebClient used (User, Payment & Notification)
✔ REST APIs Only (No RestTemplate)
✔ MySQL + JPA Integration
✔ Postman Collection Submitted
✔ Architecture Diagram Included
✔ GitHub Submission
✔ Screenshots of Successful Execution

---

## 👨‍💻 14. Author

**G.N.R. Lakshan**
ICT Undergraduate
University of Sri Jayewardenepura
GitHub: https://github.com/lakshanravindu21/Smart-Travel-Booking-Platform.git

---

✅ **End of README**

