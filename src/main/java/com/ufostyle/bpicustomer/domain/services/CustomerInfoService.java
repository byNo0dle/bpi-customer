package com.ufostyle.bpicustomer.domain.services;

import com.ufostyle.bpicustomer.domain.models.Customer;
import com.ufostyle.bpicustomer.domain.repositories.CustomerInfoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerInfoService {

    private final CustomerInfoRepo customerInfoRepo;

    public Flux<Customer> findAllCustomer() {
        return customerInfoRepo.findAll();
    }

    public Mono<Customer> findByIdCustomer(String idCustomer) {
        return customerInfoRepo.findById(idCustomer);
    }

    public Mono<Customer> createCustomer(Customer customer) {
        return customerInfoRepo.save(customer);
    }

    public Mono<Customer> updateCustomer(Customer customer) {
        return customerInfoRepo.findById(customer.getIdCustomer())
                .flatMap(updateCustomers -> {
                    updateCustomers.setIdCustomer(customer.getIdCustomer());
                    updateCustomers.setNombres(customer.getNombres());
                    updateCustomers.setApellidos(customer.getApellidos());
                    updateCustomers.setTipoDocumento(customer.getTipoDocumento());
                    updateCustomers.setNumeroDocumento(customer.getNumeroDocumento());

                    return customerInfoRepo.save(updateCustomers);
                });
    }

    public Mono<Void> deleteCustomer(String idCustomer) {
        return customerInfoRepo.deleteById(idCustomer);
    }
}
