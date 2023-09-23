package com.springboot3.controllers;

import com.springboot3.dto.ProductRecordDto;
import jakarta.validation.Valid;
import com.springboot3.models.ProductModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.springboot3.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    //primeiro metodo para post

    @PostMapping("/products")
    public ResponseEntity<ProductModel>saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto,productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>>getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object>getOneProduct(@PathVariable(value= "id")UUID id){
        Optional<ProductModel> product0 = productRepository.findById(id);
        if (product0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found. ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }
}
