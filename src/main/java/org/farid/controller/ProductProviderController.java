package org.farid.controller;


import io.swagger.annotations.ApiParam;
import org.farid.model.domain.ProductProviderNewRequest;
import org.farid.service.ProductProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("v1/product_providers")
public class ProductProviderController {

    final ProductProviderService productProviderService;

    public ProductProviderController(ProductProviderService productProviderService) {
        this.productProviderService = productProviderService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?>  newProvider(@Valid @RequestBody ProductProviderNewRequest productProviderNewRequest){
        return new ResponseEntity<>(productProviderService.newProductProvider(productProviderNewRequest), HttpStatus.CREATED);
    }

    @GetMapping(value = "/enquiry-system")
    public ResponseEntity<?>  enquirySystem(@ApiParam(value = "12,25,35", name = "productId") @NotBlank @RequestParam String productId){
        return new ResponseEntity<>(productProviderService.getProviders(productId), HttpStatus.OK);
    }
}
