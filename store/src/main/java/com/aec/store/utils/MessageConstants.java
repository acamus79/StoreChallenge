package com.aec.store.utils;
/**
 * This class contains constant messages used throughout the application.
 * It includes messages related to users, shopping carts, products, and other general messages.
 * These messages are used for various purposes, including response messages and error handling.
 * When applied in this way, they can be easily maintained, improved and translated.
 */
public class MessageConstants {
    // Messages for users
    public static final Object USER_FOUND = "Users found.";
    public static final Object USER_NOT_FOUND = "No users found.";
    public static final String USER_DELETED = "User deleted";
    public static final String NO_DELETE_USER = "The user has not been deleted.";
    public static final Object USER_UPDATED = "User updated successfully.";
    public static final Object USER_NOT_UPDATED = "User was not updated correctly.";
    public static final Object USER_CREATED = "User registered successfully.";

    // Messages for shopping carts
    public static final String CART_DELETED = "Shopping Cart has been removed.";
    public static final String CART_NOT_DELETED = "The Shopping Cart could not be removed.";
    public static final String CART_CREATED = "The shopping cart was created correctly.";
    public static final String CART_FOUND = "Shopping carts retrieved successfully.";
    public static final String CART_NOT_FOUND = "No shopping carts found.";
    public static final String USER_HAS_NO_ACTIVE_CART = "User does not have an active cart.";
    public static final String CONFIRMED_PURCHASES_FOUND = "Confirmed purchases found for the user.";
    public static final String USER_HAS_NO_CONFIRMED_PURCHASES = "The user has no confirmed purchases.";
    public static final String PRODUCT_QUANTITY_EMPTY = "Product-Quantity cannot be empty.";

    //Products
    public static final String PRODUCT_REGISTERED_SUCCESSFULLY = "Product registered successfully.";
    public static final String PRODUCT_REMOVED_SUCCESSFULLY = "The product was correctly removed.";
    public static final String PRODUCT_NOT_REMOVED = "Product could not be removed.";
    public static final String PRODUCT_NOT_FOUND = "No products found.";
    public static final String PRODUCT_FOUND = "Products retrieved successfully.";

    // Other messages
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error.";
    public static final String UPDATE_SUCCESSFUL = "Update successful.";
    public static final String UPDATE_FAILED = "Update failed";
    public static final String REGISTRATION_FAILED = "Registration failed.";
    public static final String AUTH_FAILED = "Authentication failed";
    public static final String AUTH_SUCCESS = "Authentication successful";
    public static final String IS_REQUIRED_OR_DOESNT_EXIST = "User id is required or does not exist";

    private MessageConstants() {
        // Private constructor to avoid instantiation of the class
    }
}
