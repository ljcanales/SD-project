package com.sd.apirest.controllers;

import com.sd.apirest.dao.ProductDAO;
import com.sd.apirest.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductDAO productDAO;

    @GetMapping("/{id}")
    public HttpEntity<?> getProduct(@PathVariable String productId) {
        Product product = productDAO.getProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
