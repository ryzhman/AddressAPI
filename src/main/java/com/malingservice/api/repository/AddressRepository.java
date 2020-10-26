package com.malingservice.api.repository;

import com.malingservice.api.entity.Address;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class AddressRepository {
    //data from imaginary DB.
    //TODO replace with NoSQL DB like MongoDB or DynamoDB
    private List<Address> addresses;
    private Map<String, Set<Integer>> wordToAddressesIndexesDictionary = new HashMap<>();

    /**
     * see the details of implementation of data structure in method description
     */
    public AddressRepository() {
        addresses = Stream.of(
                new Address("Massachusetts Hall", "", "Cambridge", "MA", "02138"),
                new Address("1600 Holloway Ave", "Suite 10", "San Francisco", "CA", "94132"),
                new Address("1600 Holloway Ave", "Suite 20", "San Francisco", "CA", "94132"))
                .collect(Collectors.toList());

        //init Map
        for (int i = 0; i < addresses.size(); i++) {
            Address address = addresses.get(i);
            handleAdditionToDictionary(i, address);
        }
    }

    private void handleAdditionToDictionary(int i, Address address) {
        final String zipCode = address.getZipCode();
        addNewWordToDictionary(i, zipCode);

        final String state = address.getState();
        addNewWordToDictionary(i, state);

        String[] nameParts = null;

        final String city = address.getCity();
        nameParts = city.split(" ");
        for (String word : nameParts) {
            addNewWordToDictionary(i, word);
        }

        final String line1 = address.getLine1();
        nameParts = line1.split(" ");
        for (String word : nameParts) {
            addNewWordToDictionary(i, word);
        }

        final String line2 = address.getLine2();
        nameParts = line2.split(" ");
        for (String word : nameParts) {
            addNewWordToDictionary(i, word);
        }
    }

    private void addNewWordToDictionary(int indexOfAddressInArray, String word) {
        final Set<Integer> occurrances = wordToAddressesIndexesDictionary.get(word);
        if (occurrances != null) {
            occurrances.add(indexOfAddressInArray);
        } else {
            wordToAddressesIndexesDictionary.put(word, Stream.of(indexOfAddressInArray).collect(Collectors.toSet()));
        }
    }

    /**
     * This is brutforce algorithm to iterate over each and every string in JSON with address.
     * Fetch complexity is O(m) where m is the number of words in all JSON documents at all.
     *
     * @param criteria
     * @return
     */
    public List<Address> getByStringBrutForce(String criteria) {
        //scenarios to optimize brurforce solution
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

    /**
     * This is a more sophisticated approach that is based on reverse index.
     * The idea is to have the dictionary of all words in all JSON documents that are mapped to the addressed in array.
     * Memory complexity is O(m) (worst case when all the words are unique)
     * Fetch complexity from dictionary:
     * a. O(1) - search by full word or
     * b. O(m) - search by partial search. m is number of unique words if
     *
     * @param criteria
     * @return
     */
    public Set<Address> getByStringWordsIndexes(String criteria) {
        Set<Address> result = new HashSet<>();
        //O(1) - best case scenario when the full words is in criteria
        final Set<Integer> indexes = wordToAddressesIndexesDictionary.get(criteria);
        if (indexes != null) {
            return indexes.stream().map(index -> addresses.get(index)).collect(Collectors.toSet());
        }

        //O(m) when the criteria is part of the word
        wordToAddressesIndexesDictionary.entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains(criteria))
                .forEach(entry -> {
                    final Set<Integer> occuranceIndexes = entry.getValue();
                    for (Integer index : occuranceIndexes) {
                        result.add(addresses.get(index));
                    }
                });
        return result;
    }

    public void add(Address address) {
        addresses.add(address);
        //update dictionary
        handleAdditionToDictionary(addresses.size() - 1, address);
    }

    public boolean delete(Address address) {
        final int index = addresses.indexOf(address);
        if (index < 0) {
            return false;
        }

        final Address removedAddress = addresses.remove(index);
        if (removedAddress == null) {
            return false;
        }
        //remove all words from removed address from dictionary
        final String zipCode = address.getZipCode();
        removeWordOccurranceFromDict(index, zipCode);

        final String state = address.getState();
        removeWordOccurranceFromDict(index, state);

        String[] nameParts = null;
        final String city = address.getCity();
        nameParts = city.split(" ");
        for (String word : nameParts) {
            removeWordOccurranceFromDict(index, word);
        }

        final String line1 = address.getLine1();
        nameParts = line1.split(" ");
        for (String word : nameParts) {
            removeWordOccurranceFromDict(index, word);
        }

        final String line2 = address.getLine2();
        nameParts = line2.split(" ");
        for (String word : nameParts) {
            removeWordOccurranceFromDict(index, word);
        }

        return true;
    }

    private void removeWordOccurranceFromDict(int addressIndex, String wordToRemove) {
        Set<Integer> occurrancesInDict = wordToAddressesIndexesDictionary.get(wordToRemove);
        occurrancesInDict.remove(addressIndex);
    }
}
