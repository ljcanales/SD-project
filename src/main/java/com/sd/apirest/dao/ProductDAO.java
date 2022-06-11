package com.sd.apirest.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.sd.apirest.db.DBService;
import com.sd.apirest.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDAO {

    private final MongoCollection<Product> productCollection;

    @Autowired
    public ProductDAO(DBService dbService) {
        this.productCollection = dbService.getDatabase().getCollection("Producto", Product.class);
    }

    public Product getProduct(String id) {
        return productCollection.find(Filters.eq("_id", id)).first();
    }

    public List<Product> getProducts() {
        List<Product> products = new LinkedList<>();
        FindIterable<Product> iterable = productCollection.find();
        for (Product producto : iterable) {
            products.add(producto);
        }
        return products;
    }

    public void saveProduct(Product product) {
        productCollection.insertOne(product);
    }

    public void replaceProduct(Product product) {
        productCollection.replaceOne(Filters.eq("_id", product.getId()), product);
    }

    public void deleteProduct(String id) {
        productCollection.deleteOne(Filters.eq("_id", id));
    }

    public void modifyProduct(HashMap fields, String id) {
        BasicDBObject updateBson = new BasicDBObject();
        updateBson.append("$set", fields);
        productCollection.updateOne(Filters.eq("_id", id), updateBson);
    }
}
