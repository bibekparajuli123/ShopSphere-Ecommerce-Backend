# Intern ID: CITS1741

# ShopSphere - E-Commerce Backend API

A backend-only E-Commerce application built using Spring Boot, Spring Data JPA, Hibernate, MySQL, Maven, and Lombok.

## Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Lombok

## Features

### Category Management

* Create Category
* Update Category
* Delete Category
* View Categories

### Product Management

* Create Product
* Update Product
* Delete Product
* View Products
* Search Products

### Customer Management

* Create Customer
* Update Customer
* View Customers

### Shopping Cart

* Add Product to Cart
* Remove Product from Cart
* View Cart

### Order Management

* Place Order
* View Orders
* View Order Details

## Architecture

```text
Controller → Service → Repository → MySQL
```

## Entity Relationships

```text
Category (1) → (*) Product

Customer (1) → (1) Cart

Cart (1) → (*) CartItem

Customer (1) → (*) Order

Order (1) → (*) OrderItem
```

## Running the Project

```sql
CREATE DATABASE shopsphere_db;
```

Update database credentials in:

```text
src/main/resources/application.properties
```

Run:

```bash
mvn spring-boot:run
```

## Future Improvements

* Swagger/OpenAPI
* Spring Security + JWT
* Pagination & Sorting
* Docker
* Unit Testing

---

Built to practice Spring Boot backend development, JPA relationships, DTO mapping, validation, exception handling, and MySQL integration.
