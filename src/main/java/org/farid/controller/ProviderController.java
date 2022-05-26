package org.farid.controller;


import io.swagger.annotations.ApiParam;
import org.farid.model.domain.ProviderNewRequest;
import org.farid.service.ProductService;
import org.farid.service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("v1/providers")
public class ProviderController {

    final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping(value = "")
    public ResponseEntity<?>  getAllProviders(){
        return new ResponseEntity<>(providerService.providerList(), HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<?>  newProvider(@Valid @RequestBody ProviderNewRequest providerNewRequest){
        return new ResponseEntity<>(providerService.newProvider(providerNewRequest), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?>  getProviderById(@ApiParam(value = "1", name = "id") @NotBlank @PathVariable Integer id){
        return new ResponseEntity<>(providerService.getProviderById(id), HttpStatus.OK);
    }

}
