# Web application with API for CRUD operation on addresses
##Model
Address is a JSON-originated object that for simplicity is stored in memory on repository layer.

Based on imaginary in-memory repo, reverse index-based data structure is created to perform faster string query operations. 
This data structure is hosted in-memory. Find out more implementation details in AddressRepository

##Sample requests
- **GET** localhost:8080/api/addresses?queryString=1600 - return all occurances of 1600 in all lines of address
- **POST** to create new Address: curl --location --request POST 'localhost:8080/api/addresses' \
                              --header 'Content-Type: application/json' \
                              --data-raw '{
                                  "line1": "1601 Holloway Ave",
                                  "line2": "Suite 30",
                                  "city": "San Francisco",
                                  "state": "CA",
                                  "zipCode": "94132"
                              }'

- **DELETE** to remove existing address - curl --location --request DELETE 'localhost:8080/api/addresses' \
                                     --header 'Content-Type: application/json' \
                                     --data-raw '{
                                         "line1": "1601 Holloway Ave",
                                         "line2": "Suite 30",
                                         "city": "San Francisco",
                                         "state": "CA",
                                         "zipCode": "94132"
                                     }'

-- **UPDATE** operation is omitted intentionally for the sake of time



