# Inventory Management System

## Overview
The Inventory Management System is a comprehensive application designed to streamline the process of managing products, placing orders, making payments, and notifying administrators when a product reaches its stock limit. The system provides two main roles: **Admin** and **User**, each with distinct functionalities to ensure a smooth user experience.

## Key Features

### Admin Features:
- **Add Bulk Products**: Admin can add multiple products to the inventory in bulk. Products are added with essential details such as name, description, price, quantity, and other relevant attributes.
- **Update Product**: Admin can update product information, including stock, price, and description, for existing products in the inventory.
- **View Product Details**: Admin can retrieve the details of a specific product by querying the inventory system using the product ID.
- **Delete Product**: Admin has the ability to delete products from the inventory based on the product ID.
- **Stock Limit Notification**: If the quantity of a product reaches a predefined limit (low stock), the admin will receive an email notification to restock the product. This ensures that the inventory is adequately maintained and there is no risk of stockouts.

### User Features:
- **Place Order**: Users can select products from the inventory and place an order. The system generates an invoice based on the selected products and calculates the total amount.
- **Make Payment**: After placing an order, users can make a payment through the system. Payment is processed, and the user receives a confirmation message upon successful payment. Payment status is updated accordingly in the system.

### Email Notifications for Admin:
- When the quantity of a product reaches a specific threshold, an email is automatically sent to the admin to alert them about the low stock. This helps the admin in timely stock replenishment.

## Technologies Used
- **Backend**: Spring Boot (Java)
- **Database**: MySQL (or MongoDB, depending on requirements)
- **Security**: JWT Authentication, Spring Security
- **Email Service**: JavaMail (for sending emails)
- **Payment Processing**: Custom payment gateway integration (e.g., using tokens)

## Endpoints:

### Admin Controller (/admin)
- `POST /add-product`: Add multiple products in bulk.
- `POST /update-product`: Update product details.
- `DELETE /delete-product/{id}`: Delete a product by its ID.
- `GET /get-product/{id}`: Retrieve a product’s details by its ID.

### User Controller (/user)
- `POST /place-order`: Place an order by selecting products and providing user details.
- `GET /payment/make-payment`: Process the payment after placing the order.

### Payment Controller (/payment)
- `GET /make-payment`: Verify and process the payment after the user provides the payment token.

### Email Notification:
- Admin will receive an email when the stock of any product reaches its low stock threshold.

---

Feel free to add more details to your README as needed. This Markdown will properly structure your project description with clear sections, bullet points, and formatted code blocks.

### How to Use:
To use the system, follow the instructions below (if applicable):
- **Setup Instructions**: How to set up the project on your local machine.
- **Dependencies**: List of required dependencies and version numbers.

