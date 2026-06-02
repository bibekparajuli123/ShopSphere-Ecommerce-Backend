# Intern ID: CITS1741

# ShopSphere - E-Commerce Backend API

Backend-only e-commerce application built with Spring Boot, Spring Data JPA, Hibernate, MySQL, Maven, and Lombok.

## Features

### Category Management
- Add Category
- Update Category
- Delete Category
- View Categories

### Product Management
- Add Product
- Update Product
- Delete Product
- View Products
- Search Products

### Customer Management
- Add Customer
- Update Customer
- View Customers

### Shopping Cart
- Add Product To Cart
- Remove Product From Cart
- View Cart

### Order Management
- Place Order
- View Orders
- View Order Details

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Lombok

## Architecture

Controller
→ Service
→ Repository
→ MySQL

## Database Relationships

Category (1) → (*) Product

Customer (1) → (1) Cart

Cart (1) → (*) CartItem

Customer (1) → (*) Order

Order (1) → (*) OrderItem

## Running the Project

1. Create MySQL database:

```sql
CREATE DATABASE shopsphere_db;
