# Shopizer E-Commerce Platform - Architecture Diagrams

## System Architecture Overview

```
╔═══════════════════════════════════════════════════════════════════════════╗
║                    SHOPIZER E-COMMERCE PLATFORM                           ║
║                         (Headless Architecture)                           ║
╚═══════════════════════════════════════════════════════════════════════════╝

┌─────────────────────────────────────────────────────────────────────────────┐
│                              CLIENT LAYER                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌──────────────────────┐         ┌──────────────────────┐                │
│  │   Customer Browser   │         │    Admin Browser     │                │
│  │   (End Users)        │         │   (Store Managers)   │                │
│  └──────────┬───────────┘         └──────────┬───────────┘                │
│             │                                 │                             │
└─────────────┼─────────────────────────────────┼─────────────────────────────┘
              │                                 │
              │ HTTP/HTTPS                      │ HTTP/HTTPS
              │                                 │
┌─────────────┼─────────────────────────────────┼─────────────────────────────┐
│             │    PRESENTATION LAYER           │                             │
├─────────────┼─────────────────────────────────┼─────────────────────────────┤
│             │                                 │                             │
│  ┌──────────▼──────────┐         ┌───────────▼──────────┐                 │
│  │  Customer Frontend  │         │   Admin Frontend     │                 │
│  │  ┌───────────────┐  │         │  ┌────────────────┐  │                 │
│  │  │   React.js    │  │         │  │   Angular 13   │  │                 │
│  │  │   Port: 3000  │  │         │  │   Port: 4200   │  │                 │
│  │  └───────────────┘  │         │  └────────────────┘  │                 │
│  │                     │         │                      │                 │
│  │  Components:        │         │  Modules:            │                 │
│  │  • Product Catalog  │         │  • Dashboard         │                 │
│  │  • Shopping Cart    │         │  • Product Mgmt      │                 │
│  │  • Checkout         │         │  • Order Mgmt        │                 │
│  │  • User Account     │         │  • Customer Mgmt     │                 │
│  │  • Search           │         │  • Store Config      │                 │
│  └─────────┬───────────┘         └───────────┬──────────┘                 │
│            │                                  │                             │
└────────────┼──────────────────────────────────┼─────────────────────────────┘
             │                                  │
             │ REST API (JSON)                  │ REST API (JSON)
             │                                  │
┌────────────┼──────────────────────────────────┼─────────────────────────────┐
│            │      APPLICATION LAYER           │                             │
├────────────┼──────────────────────────────────┼─────────────────────────────┤
│            │                                  │                             │
│  ┌─────────▼──────────────────────────────────▼──────────┐                 │
│  │           Backend API (Spring Boot)                   │                 │
│  │              Port: 8080                               │                 │
│  │  ┌─────────────────────────────────────────────────┐  │                 │
│  │  │           REST Controllers                      │  │                 │
│  │  │  ┌──────────┐ ┌──────────┐ ┌──────────┐       │  │                 │
│  │  │  │ Product  │ │ Category │ │   Cart   │       │  │                 │
│  │  │  │   API    │ │   API    │ │   API    │  ...  │  │                 │
│  │  │  └────┬─────┘ └────┬─────┘ └────┬─────┘       │  │                 │
│  │  └───────┼────────────┼────────────┼──────────────┘  │                 │
│  │          │            │            │                  │                 │
│  │  ┌───────▼────────────▼────────────▼──────────────┐  │                 │
│  │  │           Business Services                    │  │                 │
│  │  │  ┌──────────┐ ┌──────────┐ ┌──────────┐       │  │                 │
│  │  │  │ Product  │ │ Category │ │   Cart   │       │  │                 │
│  │  │  │ Service  │ │ Service  │ │ Service  │  ...  │  │                 │
│  │  │  └────┬─────┘ └────┬─────┘ └────┬─────┘       │  │                 │
│  │  └───────┼────────────┼────────────┼──────────────┘  │                 │
│  │          │            │            │                  │                 │
│  │  ┌───────▼────────────▼────────────▼──────────────┐  │                 │
│  │  │           Data Access Layer (JPA)              │  │                 │
│  │  │  ┌──────────┐ ┌──────────┐ ┌──────────┐       │  │                 │
│  │  │  │ Product  │ │ Category │ │   Cart   │       │  │                 │
│  │  │  │   Repo   │ │   Repo   │ │   Repo   │  ...  │  │                 │
│  │  │  └────┬─────┘ └────┬─────┘ └────┬─────┘       │  │                 │
│  │  └───────┼────────────┼────────────┼──────────────┘  │                 │
│  └──────────┼────────────┼────────────┼──────────────────┘                 │
│             │            │            │                                    │
└─────────────┼────────────┼────────────┼────────────────────────────────────┘
              │            │            │
              │ Hibernate/JPA           │
              │            │            │
┌─────────────┼────────────┼────────────┼────────────────────────────────────┐
│             │    DATA LAYER           │                                    │
├─────────────┼────────────┼────────────┼────────────────────────────────────┤
│             │            │            │                                    │
│  ┌──────────▼────────────▼────────────▼──────────┐                        │
│  │         H2 Database (In-Memory)               │                        │
│  │                                               │                        │
│  │  Tables:                                      │                        │
│  │  ├─ PRODUCT                                   │                        │
│  │  ├─ CATEGORY                                  │                        │
│  │  ├─ PRODUCT_CATEGORY                          │                        │
│  │  ├─ PRODUCT_AVAILABILITY                      │                        │
│  │  ├─ PRODUCT_PRICE                             │                        │
│  │  ├─ CUSTOMER                                  │                        │
│  │  ├─ SALES_ORDER                               │                        │
│  │  ├─ ORDER_PRODUCT                             │                        │
│  │  ├─ SHOPPING_CART                             │                        │
│  │  ├─ SHOPPING_CART_ITEM                        │                        │
│  │  ├─ MERCHANT_STORE                            │                        │
│  │  ├─ USER                                      │                        │
│  │  └─ ...                                       │                        │
│  └───────────────────────────────────────────────┘                        │
│                                                                            │
└────────────────────────────────────────────────────────────────────────────┘
```

---

## Component Interaction Flow

### 1. Customer Shopping Flow

```
┌──────────┐
│ Customer │
└────┬─────┘
     │
     │ 1. Browse Products
     ▼
┌─────────────────┐
│  React Shop     │
│  (Port 3000)    │
└────┬────────────┘
     │
     │ 2. GET /api/v1/products?store=DEFAULT
     ▼
┌─────────────────┐
│  Backend API    │
│  (Port 8080)    │
└────┬────────────┘
     │
     │ 3. Query Database
     ▼
┌─────────────────┐
│  H2 Database    │
└────┬────────────┘
     │
     │ 4. Return Products
     ▼
┌─────────────────┐
│  Backend API    │
└────┬────────────┘
     │
     │ 5. JSON Response
     ▼
┌─────────────────┐
│  React Shop     │
└────┬────────────┘
     │
     │ 6. Display Products
     ▼
┌──────────┐
│ Customer │
└──────────┘
```

### 2. Admin Product Creation Flow

```
┌──────────┐
│  Admin   │
└────┬─────┘
     │
     │ 1. Create Product
     ▼
┌─────────────────┐
│  Angular Admin  │
│  (Port 4200)    │
└────┬────────────┘
     │
     │ 2. POST /api/v1/private/login
     ▼
┌─────────────────┐
│  Backend API    │
└────┬────────────┘
     │
     │ 3. Validate & Return JWT
     ▼
┌─────────────────┐
│  Angular Admin  │
└────┬────────────┘
     │
     │ 4. POST /api/v1/private/product
     │    Authorization: Bearer <JWT>
     ▼
┌─────────────────┐
│  Backend API    │
└────┬────────────┘
     │
     │ 5. Verify JWT & Save Product
     ▼
┌─────────────────┐
│  H2 Database    │
└────┬────────────┘
     │
     │ 6. Return Product ID
     ▼
┌─────────────────┐
│  Backend API    │
└────┬────────────┘
     │
     │ 7. Success Response
     ▼
┌─────────────────┐
│  Angular Admin  │
└────┬────────────┘
     │
     │ 8. Show Success Message
     ▼
┌──────────┐
│  Admin   │
└──────────┘
```

---

## Module Dependencies

### Backend (shopizer)

```
┌─────────────────────────────────────────────────────────┐
│                    shopizer (parent)                    │
└─────────────────────────────────────────────────────────┘
                          │
        ┌─────────────────┼─────────────────┐
        │                 │                 │
        ▼                 ▼                 ▼
┌───────────────┐  ┌──────────────┐  ┌──────────────┐
│ sm-core-model │  │ sm-shop-model│  │ sm-core-     │
│               │  │              │  │ modules      │
│ • Entities    │  │ • DTOs       │  │              │
│ • Domain      │  │ • API Models │  │ • Payment    │
│   Models      │  │              │  │ • Shipping   │
└───────┬───────┘  └──────┬───────┘  │ • Email      │
        │                 │           └──────┬───────┘
        │                 │                  │
        └─────────────────┼──────────────────┘
                          │
                          ▼
                  ┌───────────────┐
                  │   sm-core     │
                  │               │
                  │ • Services    │
                  │ • Repositories│
                  │ • Business    │
                  │   Logic       │
                  └───────┬───────┘
                          │
                          ▼
                  ┌───────────────┐
                  │   sm-shop     │
                  │               │
                  │ • REST API    │
                  │ • Controllers │
                  │ • Security    │
                  │ • Main App    │
                  └───────────────┘
```

---

## Database Schema (Simplified)

```
┌─────────────────┐         ┌─────────────────┐
│ MERCHANT_STORE  │         │    CATEGORY     │
├─────────────────┤         ├─────────────────┤
│ id (PK)         │         │ id (PK)         │
│ code            │         │ code            │
│ name            │         │ merchant_id (FK)│
│ email           │         │ parent_id (FK)  │
│ phone           │         │ sort_order      │
└────────┬────────┘         │ visible         │
         │                  └────────┬────────┘
         │                           │
         │                           │
         │                  ┌────────▼────────┐
         │                  │CATEGORY_DESC    │
         │                  ├─────────────────┤
         │                  │ id (PK)         │
         │                  │ category_id (FK)│
         │                  │ language        │
         │                  │ name            │
         │                  │ description     │
         │                  └─────────────────┘
         │
         │
┌────────▼────────┐         ┌─────────────────┐
│    PRODUCT      │◄────────┤PRODUCT_CATEGORY │
├─────────────────┤         ├─────────────────┤
│ id (PK)         │         │ product_id (FK) │
│ sku             │         │ category_id (FK)│
│ merchant_id (FK)│         └─────────────────┘
│ available       │
│ quantity        │
└────────┬────────┘
         │
         │
         ├──────────────────┬──────────────────┐
         │                  │                  │
         ▼                  ▼                  ▼
┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐
│ PRODUCT_DESC    │ │PRODUCT_AVAIL    │ │ PRODUCT_PRICE   │
├─────────────────┤ ├─────────────────┤ ├─────────────────┤
│ id (PK)         │ │ id (PK)         │ │ id (PK)         │
│ product_id (FK) │ │ product_id (FK) │ │ availability_id │
│ language        │ │ region          │ │   (FK)          │
│ name            │ │ available       │ │ price           │
│ description     │ └─────────────────┘ │ discounted_price│
└─────────────────┘                     └─────────────────┘


┌─────────────────┐         ┌─────────────────┐
│    CUSTOMER     │         │  SALES_ORDER    │
├─────────────────┤         ├─────────────────┤
│ id (PK)         │◄────────┤ id (PK)         │
│ email           │         │ customer_id (FK)│
│ password        │         │ merchant_id (FK)│
│ first_name      │         │ order_date      │
│ last_name       │         │ total           │
└─────────────────┘         │ status          │
                            └────────┬────────┘
                                     │
                                     ▼
                            ┌─────────────────┐
                            │ ORDER_PRODUCT   │
                            ├─────────────────┤
                            │ id (PK)         │
                            │ order_id (FK)   │
                            │ product_id (FK) │
                            │ quantity        │
                            │ price           │
                            └─────────────────┘


┌─────────────────┐         ┌─────────────────┐
│ SHOPPING_CART   │         │SHOPPING_CART_   │
├─────────────────┤         │     ITEM        │
│ id (PK)         │◄────────┼─────────────────┤
│ customer_id (FK)│         │ id (PK)         │
│ merchant_id (FK)│         │ cart_id (FK)    │
│ cart_code       │         │ product_id (FK) │
└─────────────────┘         │ quantity        │
                            │ price           │
                            └─────────────────┘
```

---

## Security Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Security Flow                            │
└─────────────────────────────────────────────────────────────┘

┌──────────┐
│  Client  │
└────┬─────┘
     │
     │ 1. POST /api/v1/private/login
     │    {username, password}
     ▼
┌─────────────────────────────────────────────────────────────┐
│                    Spring Security                          │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌──────────────────────────────────────────────────────┐  │
│  │         Authentication Filter                        │  │
│  └────────────────────┬─────────────────────────────────┘  │
│                       │                                     │
│                       ▼                                     │
│  ┌──────────────────────────────────────────────────────┐  │
│  │    UserDetailsService                                │  │
│  │    • Load user from database                         │  │
│  │    • Verify password (BCrypt)                        │  │
│  └────────────────────┬─────────────────────────────────┘  │
│                       │                                     │
│                       ▼                                     │
│  ┌──────────────────────────────────────────────────────┐  │
│  │    JWT Token Generator                               │  │
│  │    • Create JWT with user info                       │  │
│  │    • Sign with secret key                            │  │
│  │    • Set expiration (7 days)                         │  │
│  └────────────────────┬─────────────────────────────────┘  │
│                       │                                     │
└───────────────────────┼─────────────────────────────────────┘
                        │
                        │ 2. Return JWT Token
                        ▼
                   ┌──────────┐
                   │  Client  │
                   └────┬─────┘
                        │
                        │ 3. Store token
                        │
                        │ 4. Subsequent requests
                        │    Authorization: Bearer <JWT>
                        ▼
┌─────────────────────────────────────────────────────────────┐
│                    JWT Validation                           │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌──────────────────────────────────────────────────────┐  │
│  │    JWT Authentication Filter                         │  │
│  │    • Extract token from header                       │  │
│  │    • Verify signature                                │  │
│  │    • Check expiration                                │  │
│  │    • Load user details                               │  │
│  └────────────────────┬─────────────────────────────────┘  │
│                       │                                     │
│                       ▼                                     │
│  ┌──────────────────────────────────────────────────────┐  │
│  │    Authorization                                     │  │
│  │    • Check user roles/permissions                    │  │
│  │    • Allow/Deny access                               │  │
│  └────────────────────┬─────────────────────────────────┘  │
│                       │                                     │
└───────────────────────┼─────────────────────────────────────┘
                        │
                        │ 5. Access granted
                        ▼
                   ┌──────────┐
                   │   API    │
                   │ Endpoint │
                   └──────────┘
```

---

## Deployment Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Production Deployment                    │
└─────────────────────────────────────────────────────────────┘

                        ┌──────────────┐
                        │   Internet   │
                        └──────┬───────┘
                               │
                               ▼
                    ┌──────────────────┐
                    │  Load Balancer   │
                    │   (HTTPS/SSL)    │
                    └────────┬─────────┘
                             │
                ┌────────────┼────────────┐
                │            │            │
                ▼            ▼            ▼
        ┌──────────┐  ┌──────────┐  ┌──────────┐
        │  CDN     │  │  CDN     │  │  API     │
        │  React   │  │ Angular  │  │ Gateway  │
        │  Shop    │  │  Admin   │  │          │
        └──────────┘  └──────────┘  └────┬─────┘
                                          │
                            ┌─────────────┼─────────────┐
                            │             │             │
                            ▼             ▼             ▼
                    ┌──────────┐  ┌──────────┐  ┌──────────┐
                    │ Backend  │  │ Backend  │  │ Backend  │
                    │Instance 1│  │Instance 2│  │Instance 3│
                    └────┬─────┘  └────┬─────┘  └────┬─────┘
                         │             │             │
                         └─────────────┼─────────────┘
                                       │
                                       ▼
                            ┌──────────────────┐
                            │  Database        │
                            │  (MySQL/         │
                            │   PostgreSQL)    │
                            │                  │
                            │  • Master        │
                            │  • Read Replicas │
                            └──────────────────┘
```

---

## Technology Stack Summary

```
┌─────────────────────────────────────────────────────────────┐
│                    Technology Stack                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Frontend (Customer Shop)                                   │
│  ├─ React.js                                                │
│  ├─ JavaScript/ES6                                          │
│  ├─ Axios (HTTP Client)                                     │
│  └─ Bootstrap/CSS                                           │
│                                                             │
│  Frontend (Admin Panel)                                     │
│  ├─ Angular 13                                              │
│  ├─ TypeScript                                              │
│  ├─ RxJS                                                    │
│  └─ Angular Material                                        │
│                                                             │
│  Backend                                                    │
│  ├─ Java 11/17/21                                           │
│  ├─ Spring Boot 2.5.12                                      │
│  ├─ Spring Security                                         │
│  ├─ Spring Data JPA                                         │
│  ├─ Hibernate                                               │
│  ├─ JWT (JSON Web Tokens)                                   │
│  └─ Swagger/OpenAPI                                         │
│                                                             │
│  Database                                                   │
│  ├─ H2 (Development)                                        │
│  ├─ MySQL (Production)                                      │
│  └─ PostgreSQL (Production)                                 │
│                                                             │
│  Build Tools                                                │
│  ├─ Maven (Backend)                                         │
│  ├─ npm (Frontend)                                          │
│  └─ Docker                                                  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

**Created**: March 11, 2026  
**Version**: 3.2.5
