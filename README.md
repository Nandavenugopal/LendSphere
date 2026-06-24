# 🚀 LendSphere: Next-Gen Loan Lifecycle & Ledger System

<div align="center">
  <img alt="Java" src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot" />
  <img alt="PostgreSQL" src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" />
  <img alt="Docker" src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white" />
</div>

<br/>

**LendSphere** is a powerful backend system that simulates a real-world micro-lending platform. It manages the complete loan lifecycle with strict financial correctness, robust auditability, and production-grade safeguards. 

Built with enterprise-ready architecture in mind, this project emphasizes **backend engineering depth**, precision arithmetic, and secure operations.

---

## 🔥 Core Capabilities

*   **📈 Loan Product Configuration**: Define dynamic loan products with strict validation rules.
*   **📝 Automated Workflow**: Loan application workflow with automated credit scoring.
*   **⚙️ Strict State Machine**: Enforced loan states: `APPLIED` → `APPROVED` → `ACTIVE` → `CLOSED`.
*   **🧮 Precision Calculations**: EMI schedule generation using `BigDecimal` precision to prevent floating-point errors.
*   **⏱️ Scheduled Operations**: Automated overdue detection and penalty accrual via scheduled background jobs.
*   **💧 Waterfall Repayment**: Intelligent payment allocation (Penalties → Interest → Principal).
*   **🗄️ Immutable Ledger**: Traceable and tamper-proof financial ledger (Disbursement, Repayment, Penalty).
*   **🔒 Security First**: JWT authentication, stateless sessions, and role-based access control (RBAC).
*   **⚡ Idempotency**: Safe, idempotent payment processing ensuring exactly-once behavior.
*   **🛡️ Robust Error Handling**: Centralized exception handling via Controller Advice.

---

## 🏗️ Architecture Overview

The system is designed with a clear separation of concerns:

```text
[ Controller Layer (REST) ]
           ↓
[ Service Layer (Business Rules) ]
           ↓
[ Repository Layer (Spring Data JPA) ]
           ↓
[ PostgreSQL Database (Dockerized) ]
```

### Key Modules:
- **Background Jobs**: `OverdueScheduler`, Penalty Accrual Engine.
- **Security**: JWT Filter, Password Encryption, Stateless Authentication.
- **Financial Integrity**: `BigDecimal` arithmetic, Immutable Ledger, Idempotency Keys.

---

## 💻 Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.x
- **Security:** Spring Security + JWT
- **Persistence:** Spring Data JPA / Hibernate
- **Database:** PostgreSQL (Dockerized)
- **Build Tool:** Maven
- **API Documentation:** Swagger / OpenAPI

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven
- Docker & Docker Compose

### Running Locally

1. **Spin up the database:**
   ```bash
   docker compose up -d
   ```
2. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
3. **Explore the APIs:**
   Navigate to the Swagger UI:  
   [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🧪 Demo Flow

1. **Auth:** Login to the system and receive your `<JWT_TOKEN>`.
2. **Setup:** Create a new loan product configuration.
3. **Apply:** Submit a loan application.
4. **Approve:** Credit scoring engine approves the loan.
5. **Disburse:** Funds are disbursed (recorded in the immutable ledger).
6. **Schedule:** View the auto-generated EMI repayment schedule.
7. **Repay:** Make repayments (test the idempotency!).
8. **Audit:** Observe the ledger updates and penalty accruals.
9. **Close:** Loan auto-closes upon full repayment.

---
