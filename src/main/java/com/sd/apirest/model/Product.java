package com.sd.apirest.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Producto")
public class Product {
    private String id;
    private String name;
    private String description;
    private float price;
    private int stock;
    private String provider;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public boolean validate() {
        if (id == null || name == null || description == null || price == 0) {
            return false;
        }
        return true;
    }

}
