package com.ufostyle.bpicustomer.domain.repositories;

import com.ufostyle.bpicustomer.domain.models.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInfoRepo extends ReactiveCrudRepository<Customer, String> {
}
