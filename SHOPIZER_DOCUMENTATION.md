# Shopizer E-Commerce Platform - Complete Documentation

## Overview

Shopizer is a headless e-commerce platform built with Java Spring Boot backend and separate frontend applications for administration and customer shopping.

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                     SHOPIZER ECOSYSTEM                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────┐      ┌──────────────┐      ┌──────────────┐ │
│  │   Customer   │      │    Admin     │      │   Backend    │ │
│  │   Frontend   │      │   Frontend   │      │     API      │ │
│  │  (React.js)  │      │  (Angular)   │      │ (Spring Boot)│ │
│  │              │      │              │      │              │ │
│  │ Port: 3000   │      │ Port: 4200   │      │ Port: 8080   │ │
│  └──────┬───────┘      └──────┬───────┘      └──────┬───────┘ │
│         │                     │                     │         │
│         │    HTTP REST API    │    HTTP REST API    │         │
│         └─────────────────────┴─────────────────────┘         │
│                               │                               │
│                               ▼                               │
│                     ┌──────────────────┐                      │
│                     │   H2 Database    │                      │
│                     │   (In-Memory)    │                      │
│                     └──────────────────┘                      │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 1. Backend API (shopizer)

### Technology Stack
- **Language**: Java 11/17/21
- **Framework**: Spring Boot 2.5.12
- **Database**: H2 (in-memory) / MySQL / PostgreSQL
- **Build Tool**: Maven
- **API Documentation**: Swagger/OpenAPI

### Project Structure
```
shopizer/
├── sm-core-model/          # Domain models and entities
├── sm-core-modules/        # Business logic modules
├── sm-core/                # Core services
├── sm-shop-model/          # API models (DTOs)
└── sm-shop/                # REST API application
    ├── src/main/
    │   ├── java/           # Java source code
    │   └── resources/
    │       ├── application.properties
    │       └── database.properties
    └── pom.xml
```

### Key Features
- **Headless Commerce**: RESTful APIs for all operations
- **Multi-tenant**: Support for multiple stores
- **Product Management**: Categories, products, variants, options
- **Order Management**: Cart, checkout, order processing
- **Customer Management**: Registration, authentication, profiles
- **Payment Integration**: Stripe, PayPal support
- **Shipping**: Multiple shipping methods
- **Tax Management**: Configurable tax rates

### Configuration Files

#### `database.properties`
```properties
# H2 In-Memory Database
db.jdbcUrl=jdbc:h2:mem:SALESMANAGER;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;INIT=CREATE SCHEMA IF NOT EXISTS SALESMANAGER
db.user=sa
db.password=
db.driverClass=org.h2.Driver
hibernate.dialect=org.hibernate.dialect.H2Dialect
db.schema=SALESMANAGER
hibernate.hbm2ddl.auto=create
populate.testdata=true
```

### Running the Backend

**Prerequisites:**
- Java 11+ (tested with Java 21)
- Maven 3.6+

**Build & Run:**
```bash
cd shopizer
./mvnw clean install
cd sm-shop
./mvnw spring-boot:run
```

**Access Points:**
- API Base URL: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Health Check: `http://localhost:8080/actuator/health`

### API Endpoints

#### Public Endpoints
```
GET    /api/v1/store/{code}              # Get store info
GET    /api/v1/category                  # List categories
GET    /api/v1/category/{code}           # Get category
GET    /api/v1/products                  # List products
GET    /api/v1/products/{id}             # Get product
POST   /api/v1/cart                      # Create cart
GET    /api/v1/cart/{code}               # Get cart
POST   /api/v1/customer/register         # Register customer
POST   /api/v1/private/login             # Login
```

#### Private Endpoints (Require Authentication)
```
POST   /api/v1/private/product           # Create product
PUT    /api/v1/private/product/{id}      # Update product
DELETE /api/v1/private/product/{id}      # Delete product
POST   /api/v1/private/category          # Create category
PUT    /api/v1/private/category/{id}     # Update category
POST   /api/v1/private/user              # Create user
GET    /api/v1/private/users/{id}        # Get user
```

### Default Credentials
- **Username**: `admin@shopizer.com`
- **Password**: `password`

---

## 2. Admin Panel (shopizer-admin)

### Technology Stack
- **Framework**: Angular 13
- **Language**: TypeScript
- **Node Version**: 12.22.7
- **Package Manager**: npm

### Project Structure
```
shopizer-admin/
├── src/
│   ├── app/              # Angular components
│   ├── assets/           # Static assets
│   └── environments/     # Environment configs
├── angular.json
├── package.json
└── README.md
```

### Features
- **Dashboard**: Store overview and analytics
- **Catalogue Management**:
  - Products (CRUD operations)
  - Categories
  - Manufacturers
  - Product options and variants
- **Order Management**: View and process orders
- **Customer Management**: View customer data
- **Store Configuration**:
  - Payment methods
  - Shipping options
  - Tax rates
- **User Management**: Admin users and permissions

### Running the Admin Panel

**Option 1: Docker (Recommended)**
```bash
docker run -d \
  -e "APP_BASE_URL=http://localhost:8080/api" \
  -p 4200:80 \
  --name shopizer-admin \
  shopizerecomm/shopizer-admin
```

**Option 2: Local Development**
```bash
# Install nvm and Node 12.22.7
nvm install 12.22.7
nvm use 12.22.7

# Install Angular CLI
npm install -g @angular/cli@13.3

# Install dependencies
npm install --legacy-peer-deps

# Run development server
ng serve
```

**Access:**
- URL: `http://localhost:4200`
- Login: `admin@shopizer.com` / `password`

### Configuration
Backend API URL is configured via environment variable:
```bash
APP_BASE_URL=http://localhost:8080/api
```

---

## 3. Customer Shop (shopizer-shop-reactjs)

### Technology Stack
- **Framework**: React.js
- **Language**: JavaScript
- **Node Version**: 16.13.0
- **Package Manager**: npm

### Project Structure
```
shopizer-shop-reactjs/
├── public/
│   ├── env-config.js     # Runtime configuration
│   └── index.html
├── src/
│   ├── components/       # React components
│   ├── pages/           # Page components
│   ├── services/        # API services
│   └── App.js
├── package.json
└── README.md
```

### Features
- **Product Browsing**: Category and product listing
- **Product Search**: Search functionality
- **Product Details**: Detailed product information
- **Shopping Cart**: Add/remove items, update quantities
- **Checkout**: Complete purchase flow
- **Customer Account**:
  - Registration
  - Login
  - Order history
  - Profile management

### Running the Shop

**Option 1: Docker (Recommended)**
```bash
docker run -d \
  -e "APP_MERCHANT=DEFAULT" \
  -e "APP_BASE_URL=http://localhost:8080" \
  -p 3000:80 \
  --name shopizer-shop \
  shopizerecomm/shopizer-shop-reactjs
```

**Option 2: Local Development**
```bash
# Install nvm and Node 16.13.0
nvm install 16.13.0
nvm use 16.13.0

# Install dependencies
npm install --legacy-peer-deps

# Run development server
npm run dev
```

**Access:**
- URL: `http://localhost:3000`

### Configuration
Environment variables in `public/env-config.js`:
```javascript
window._env_ = {
  APP_BASE_URL: "http://localhost:8080",
  APP_API_VERSION: "/api/v1/",
  APP_MERCHANT: "DEFAULT",
  APP_PRODUCT_GRID_LIMIT: "15",
  APP_THEME_COLOR: "#D1D1D1"
}
```

---

## Complete Setup Guide

### Step 1: Start Backend
```bash
cd shopizer/sm-shop
./mvnw spring-boot:run
```
Wait for: `Started ShopApplication`

### Step 2: Start Admin Panel
```bash
docker run -d \
  -e "APP_BASE_URL=http://localhost:8080/api" \
  -p 4200:80 \
  shopizerecomm/shopizer-admin
```

### Step 3: Start Customer Shop
```bash
docker run -d \
  -e "APP_MERCHANT=DEFAULT" \
  -e "APP_BASE_URL=http://localhost:8080" \
  -p 3000:80 \
  shopizerecomm/shopizer-shop-reactjs
```

### Step 4: Verify All Services
```bash
# Backend health
curl http://localhost:8080/actuator/health

# Admin panel
open http://localhost:4200

# Customer shop
open http://localhost:3000
```

---

## Data Flow Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                        USER INTERACTIONS                        │
└─────────────────────────────────────────────────────────────────┘
                    │                           │
                    │                           │
        ┌───────────▼──────────┐    ┌──────────▼──────────┐
        │   Customer Shop      │    │   Admin Panel       │
        │   (Port 3000)        │    │   (Port 4200)       │
        │                      │    │                     │
        │  - Browse Products   │    │  - Manage Products  │
        │  - Add to Cart       │    │  - Process Orders   │
        │  - Checkout          │    │  - Configure Store  │
        └───────────┬──────────┘    └──────────┬──────────┘
                    │                           │
                    │    REST API Calls         │
                    │    (JSON over HTTP)       │
                    │                           │
                    └───────────┬───────────────┘
                                │
                    ┌───────────▼────────────┐
                    │   Backend API          │
                    │   (Port 8080)          │
                    │                        │
                    │  Controllers:          │
                    │  ├─ ProductAPI         │
                    │  ├─ CategoryAPI        │
                    │  ├─ CartAPI            │
                    │  ├─ OrderAPI           │
                    │  ├─ CustomerAPI        │
                    │  └─ UserAPI            │
                    │                        │
                    │  Services:             │
                    │  ├─ ProductService     │
                    │  ├─ CategoryService    │
                    │  ├─ OrderService       │
                    │  └─ CustomerService    │
                    └───────────┬────────────┘
                                │
                                │ JPA/Hibernate
                                │
                    ┌───────────▼────────────┐
                    │   H2 Database          │
                    │   (In-Memory)          │
                    │                        │
                    │  Tables:               │
                    │  ├─ PRODUCT            │
                    │  ├─ CATEGORY           │
                    │  ├─ CUSTOMER           │
                    │  ├─ SALES_ORDER        │
                    │  ├─ SHOPPING_CART      │
                    │  └─ MERCHANT_STORE     │
                    └────────────────────────┘
```

---

## API Authentication Flow

```
┌──────────┐                                    ┌──────────┐
│  Client  │                                    │  Backend │
└────┬─────┘                                    └────┬─────┘
     │                                               │
     │  POST /api/v1/private/login                  │
     │  {username, password}                        │
     ├──────────────────────────────────────────────>│
     │                                               │
     │                                               │ Validate
     │                                               │ Credentials
     │                                               │
     │  200 OK                                       │
     │  {id, token: "JWT_TOKEN"}                    │
     │<──────────────────────────────────────────────┤
     │                                               │
     │  Store token in memory/localStorage           │
     │                                               │
     │  POST /api/v1/private/product                │
     │  Authorization: Bearer JWT_TOKEN              │
     ├──────────────────────────────────────────────>│
     │                                               │
     │                                               │ Verify
     │                                               │ JWT Token
     │                                               │
     │  201 Created                                  │
     │  {id: 1}                                      │
     │<──────────────────────────────────────────────┤
     │                                               │
```

---

## Testing Guide

### 1. Test Backend API with Swagger
1. Open `http://localhost:8080/swagger-ui.html`
2. Click "Authorize" button
3. Login to get token:
   - Endpoint: `POST /api/v1/private/login`
   - Body: `{"username":"admin@shopizer.com","password":"password"}`
4. Copy the token from response
5. Click "Authorize" and enter: `Bearer <token>`
6. Test any endpoint

### 2. Test Admin Panel
1. Open `http://localhost:4200`
2. Login: `admin@shopizer.com` / `password`
3. Navigate to Catalogue → Products
4. Create a test product

### 3. Test Customer Shop
1. Open `http://localhost:3000`
2. Click "Shop Now"
3. Browse categories
4. View product details
5. Add to cart
6. Proceed to checkout

---

## Troubleshooting

### Backend Issues

**Problem**: Port 8080 already in use
```bash
# Find process using port 8080
lsof -i :8080
# Kill the process
kill -9 <PID>
```

**Problem**: Database connection errors
- Check `database.properties` configuration
- Ensure H2 driver is in classpath

### Admin Panel Issues

**Problem**: Cannot login
- Verify backend is running at `http://localhost:8080`
- Check browser console for CORS errors
- Verify credentials: `admin@shopizer.com` / `password`

**Problem**: 404 errors
- Check `APP_BASE_URL` environment variable
- Ensure it points to `http://localhost:8080/api`

### Customer Shop Issues

**Problem**: Products not showing
- Verify backend has products: `curl http://localhost:8080/api/v1/products?store=DEFAULT`
- Check browser console for API errors
- Verify `APP_BASE_URL` is set to `http://localhost:8080`

**Problem**: 404 on navigation
- Check `APP_MERCHANT` is set to `DEFAULT`
- Verify store exists in backend

---

## Production Deployment Considerations

### Backend
- Use MySQL/PostgreSQL instead of H2
- Configure proper database connection pooling
- Enable HTTPS
- Set up proper logging
- Configure CORS for production domains
- Use environment-specific profiles

### Frontend Applications
- Build production bundles
- Configure CDN for static assets
- Enable gzip compression
- Set up proper error tracking
- Configure analytics

### Security
- Change default admin password
- Use strong JWT secrets
- Enable rate limiting
- Implement proper CORS policies
- Use HTTPS everywhere
- Regular security updates

---

## Useful Commands

### Backend
```bash
# Build
./mvnw clean install

# Run
./mvnw spring-boot:run

# Run with profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=local

# Skip tests
./mvnw clean install -DskipTests
```

### Docker
```bash
# View logs
docker logs shopizer-shop
docker logs shopizer-admin

# Stop containers
docker stop shopizer-shop shopizer-admin

# Remove containers
docker rm shopizer-shop shopizer-admin

# View running containers
docker ps
```

### Git
```bash
# Check status
git status

# Commit changes
git add .
git commit -m "Your message"

# Push to GitHub
git push origin 3.2.7
```

---

## Resources

- **Backend Repository**: https://github.com/KirtiShetye/shopizer
- **Official Documentation**: https://shopizer-ecommerce.github.io/documentation/
- **Swagger API Docs**: http://localhost:8080/swagger-ui.html
- **Community**: https://shopizer.slack.com

---

## License

Apache License 2.0

---

**Last Updated**: March 11, 2026
**Version**: 3.2.5
