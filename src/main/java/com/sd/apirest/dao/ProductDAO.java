package com.sd.apirest.dao;

import com.sd.apirest.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProductDAO extends MongoRepository<Product, String> {

    @Query("{_id:'?0'}")
    Product getProduct(String id);

}
