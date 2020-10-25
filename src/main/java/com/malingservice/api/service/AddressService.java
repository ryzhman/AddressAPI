package com.malingservice.api.service;

import com.malingservice.api.entity.Address;

import java.util.List;

public interface AddressService {

    List<Address> getByString(String criteria);
}
