package com.electronics.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an electronic product returned/sent by the DummyJSON API.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private Integer id;
    private String  title;
    private String  description;
    private Double  price;
    private Double  discountPercentage;
    private Double  rating;
    private Integer stock;
    private String  brand;
    private String  category;
    private String  thumbnail;

    /**
     * Converts this model to a Map suitable for a POST/PUT request body.
     */
    public Map<String, Object> toRequestBody() {
        Map<String, Object> body = new HashMap<>();
        if (title              != null) body.put("title",              title);
        if (description        != null) body.put("description",        description);
        if (price              != null) body.put("price",              price);
        if (discountPercentage != null) body.put("discountPercentage", discountPercentage);
        if (rating             != null) body.put("rating",             rating);
        if (stock              != null) body.put("stock",              stock);
        if (brand              != null) body.put("brand",              brand);
        if (category           != null) body.put("category",           category);
        if (thumbnail          != null) body.put("thumbnail",          thumbnail);
        return body;
    }

    /**
     * Factory: creates a valid electronics product for testing.
     */
    public static Product validElectronics() {
        Product p = new Product();
        p.setTitle("Test Electronics Smartphone");
        p.setDescription("High-end test smartphone for automation");
        p.setPrice(999.99);
        p.setDiscountPercentage(10.5);
        p.setRating(4.5);
        p.setStock(50);
        p.setBrand("TechBrand");
        p.setCategory("smartphones");
        p.setThumbnail("https://i.dummyjson.com/data/products/1/thumbnail.jpg");
        return p;
    }
}
