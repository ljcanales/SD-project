package com.sd.apirest.controllers;

import com.sd.apirest.dao.ProductDAO;
import com.sd.apirest.model.Product;
import com.sd.apirest.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {

    @Autowired
    ProductDAO productDAO;

    @GetMapping("/products")
    public HttpEntity<?> getAllProducts() {
        List<Product> products = productDAO.findAll();
        List<EntityModel<Product>> productsModel = new ArrayList<>();
        for (Product product : products) {
            productsModel.add(getEntity(product));
        }
        return new ResponseEntity<>(productsModel, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public HttpEntity<?> getProduct(@PathVariable(name = "id") String productId) {
        Product product = productDAO.getProduct(productId);
        if (product == null) {
            return new ResponseEntity<>(new ResponseMessage("Product not found."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(getEntity(product), HttpStatus.OK);
    }

    @PostMapping("/product")
    public HttpEntity<?> saveProduct(@RequestBody Product product) {
        if (product == null || !product.validate()) {
            return new ResponseEntity<>(new ResponseMessage("No valid product was provided."), HttpStatus.BAD_REQUEST);
        }
        Product savedProduct = productDAO.save(product);
        return new ResponseEntity<>(getEntity(savedProduct), HttpStatus.OK);
    }

    @PutMapping("/product/{id}")
    public HttpEntity<?> replaceProduct(@PathVariable(name = "id") String productId, @RequestBody Product product) {
        if (product == null || !product.validate()) {
            return new ResponseEntity<>(new ResponseMessage("No valid product was provided."), HttpStatus.BAD_REQUEST);
        }
        if (productId.equals(product.getId())) {
            return new ResponseEntity<>(new ResponseMessage("IDs don't match."), HttpStatus.BAD_REQUEST);
        }
        Product productDB = productDAO.getProduct(productId);
        if (productDB == null) {
            return new ResponseEntity<>(new ResponseMessage("Product not found."), HttpStatus.NOT_FOUND);
        }
        Product modifiedProduct = productDAO.save(product);
        return new ResponseEntity<>(getEntity(modifiedProduct), HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable(name = "id") String productId) {
        Product productDB = productDAO.getProduct(productId);
        if (productDB == null) {
            return new ResponseEntity<>(new ResponseMessage("Product not found."), HttpStatus.NOT_FOUND);
        }
        productDAO.deleteById(productId);
        return new ResponseEntity<>(new ResponseMessage("Product has been deleted."), HttpStatus.OK);
    }

    private EntityModel<Product> getEntity(Product product) {
        EntityModel<Product> productResource = EntityModel.of(product);
        productResource.add(linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel());
        productResource.add(linkTo(methodOn(ProviderController.class).getProvider(product.getProvider())).withRel("provider"));
        return productResource;
    }
}
