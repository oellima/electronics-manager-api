package com.electronics.utils;

public final class Constants {

    private Constants() {}

    // Base URLs
    public static final String BASE_URL = "https://dummyjson.com";

    // Endpoints
    public static final String ENDPOINT_TEST        = "/test";
    public static final String ENDPOINT_AUTH_LOGIN  = "/auth/login";
    public static final String ENDPOINT_AUTH_PRODUCTS = "/auth/products";
    public static final String ENDPOINT_PRODUCTS    = "/products";
    public static final String ENDPOINT_PRODUCTS_ADD = "/products/add";
    public static final String ENDPOINT_PRODUCTS_ID = "/products/{id}";

    // Auth credentials (from /users doc — username/password fields)
    public static final String VALID_USERNAME   = "emilys";
    public static final String VALID_PASSWORD   = "emilyspass";
    public static final String INVALID_USERNAME = "invalid_user";
    public static final String INVALID_PASSWORD = "wrong_password";

    // HTTP Status Codes
    public static final int STATUS_OK         = 200;
    public static final int STATUS_CREATED    = 201;
    public static final int STATUS_BAD_REQUEST= 400;
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_FORBIDDEN  = 403;
    public static final int STATUS_NOT_FOUND  = 404;

    // Content type
    public static final String CONTENT_TYPE_JSON = "application/json";

    // Token key in Context
    public static final String CONTEXT_TOKEN = "authToken";

    // Product fields
    public static final String PRODUCT_TITLE       = "Test Electronics Smartphone";
    public static final String PRODUCT_DESCRIPTION = "High-end test smartphone for automation";
    public static final double PRODUCT_PRICE       = 999.99;
    public static final double PRODUCT_DISCOUNT    = 10.5;
    public static final double PRODUCT_RATING      = 4.5;
    public static final int    PRODUCT_STOCK       = 50;
    public static final String PRODUCT_BRAND       = "TechBrand";
    public static final String PRODUCT_CATEGORY    = "smartphones";
    public static final String PRODUCT_THUMBNAIL   = "https://i.dummyjson.com/data/products/1/thumbnail.jpg";
}
