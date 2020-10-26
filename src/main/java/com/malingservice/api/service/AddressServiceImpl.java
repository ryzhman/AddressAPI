package com.malingservice.api.service;

import com.malingservice.api.entity.Address;
import com.malingservice.api.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;

    @Override
    public Set<Address> getByString(String criteria) {
        return addressRepository.getByStringWordsIndexes(criteria);
    }

    @Override
    public void add(Address address) {
        addressRepository.add(address);
    }

    @Override
    public boolean delete(Address address) {
        return addressRepository.delete(address);
    }

}
