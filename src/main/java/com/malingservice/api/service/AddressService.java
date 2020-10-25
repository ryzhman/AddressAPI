package com.malingservice.api.service;

import com.malingservice.api.entity.Address;

import java.util.Set;

public interface AddressService {

    Set<Address> getByString(String criteria);
}
