package com.malingservice.api.controller;

import com.malingservice.api.entity.Address;
import com.malingservice.api.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@RequestMapping("/api")
@AllArgsConstructor
public class AddressController {
    private AddressService addressService;

    //    @PostMapping("/addresses") --> all params in POST request
//    @GetMapping("/addresses?line1=2100&state=MD") --> perfect scenario
//    @GetMapping("/addresses?queryString=12345") --> bad design
    @GetMapping("/addresses")
    public ResponseEntity<Set<Address>> getAddressByCriteria(@RequestParam("queryString") String queryString) {
        return new ResponseEntity<>(addressService.getByString(queryString), HttpStatus.OK);
    }

}
