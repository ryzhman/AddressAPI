package com.malingservice.api.service;

import com.malingservice.api.entity.Address;
import com.malingservice.api.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;

    @Override
    public Set<Address> getByString(String criteria) {
        return addressRepository.getByString(criteria);
    }

}
