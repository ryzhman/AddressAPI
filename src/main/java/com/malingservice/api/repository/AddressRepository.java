package com.malingservice.api.repository;

import com.malingservice.api.entity.Address;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class AddressRepository {

    private List<Address> addresses = Stream.of(
            new Address("Massachusetts Hall", "", "Cambridge", "MA", "02138"),
            new Address("1600 Holloway Ave", "Suite 10", "San Francisco", "CA", "94132"),
            new Address("1600 Holloway Ave", "Suite 20", "San Francisco", "CA", "94132"))
            .collect(Collectors.toList());

    public List<Address> getByString(String criteria) {
        //scenarios
        //scenario 1: criteria.length == 1 and it is letter -> search everywhere except zipCode
        //scenario 2: criteria.length == 1 and it is digit -> search everywhere except state and city
        //scenario 3: criteria.length == 2 and it is letter -> search everywhere except zip code
        //scenario 4: criteria.length == 2 and it is digit -> search everywhere except state and city
        //scenario 5: criteria.length >= 2 and it is letter -> search everywhere except zip code and state
        //scenario 6: criteria.length >= 2 and it is digit -> search everywhere except zip code and state
        return addresses.stream()
                .filter(address ->
                        address.getCity().contains(criteria) ||
                                address.getLine1().contains(criteria) ||
                                address.getLine2().contains(criteria) ||
                                address.getState().contains(criteria) ||
                                address.getZipCode().contains(criteria))
                .collect(Collectors.toList());
    }
}
