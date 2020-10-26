package com.malingservice.api.controller;

import com.malingservice.api.entity.Address;
import com.malingservice.api.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/api/addresses")
@AllArgsConstructor
public class AddressController {
    private AddressService addressService;

    //    @PostMapping("/addresses") --> all params in POST request
//    @GetMapping("/addresses?line1=2100&state=MD") --> perfect scenario
//    @GetMapping("/addresses?queryString=12345") --> bad design
    @GetMapping()
    public ResponseEntity<Set<Address>> getAddressByCriteria(@RequestParam("queryString") String queryString) {
        return new ResponseEntity<>(addressService.getByString(queryString), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addAddress(@RequestBody Address address) {
        addressService.add(address);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    public ResponseEntity deleteAddress(@RequestBody Address address) {
        final boolean isDeleted = addressService.delete(address);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
