package org.farid.controller;


import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.farid.model.domain.ProductNewRequest;
import org.farid.service.ProductService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("v1/products")
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "")
    public ResponseEntity<?>  getAllProducts(){
        return new ResponseEntity<>(productService.productList(), HttpStatus.OK);
    }

    @GetMapping(value = "/review")
    public ResponseEntity<?>  productsReview(
            Integer pageNumber,
            Integer pageSize ,
            Integer categoryId){
        return new ResponseEntity<>(productService.review(pageNumber, pageSize , categoryId), HttpStatus.OK);
    }



    @PostMapping(value = "")
    public ResponseEntity<?>  newProduct(@Valid @RequestBody ProductNewRequest productNewRequest){
        return new ResponseEntity<>(productService.newProduct(productNewRequest), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?>  getProductById(@ApiParam(value = "1", name = "id") @NotBlank @PathVariable Long id){
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }
}
