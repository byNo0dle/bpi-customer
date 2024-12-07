package com.ufostyle.bpicustomer.infrastructure.controllers;

import com.ufostyle.bpicustomer.domain.models.Customer;
import com.ufostyle.bpicustomer.domain.services.CustomerInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerInfoController {

    private final CustomerInfoService customerInfoService;

    @GetMapping
    public ResponseEntity<Flux<Customer>> findAllCustomer() {
        log.info("--- Se acaba de listar todos los clientes. ---");
        return new ResponseEntity<>(customerInfoService.findAllCustomer(), HttpStatus.OK);
    }

    @GetMapping("/{idCustomer}")
    public ResponseEntity<Mono<Customer>> findByIdCustomer(
            @PathVariable("idCustomer") String idCustomer) {
        Mono<Customer> entity = customerInfoService.findByIdCustomer(idCustomer);
        log.info("--- Se acaba de traer el cliente solicitado. ---");
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Mono<Customer>> createCustomer(
            @RequestBody Customer customer) {
        Mono<Customer> entity = customerInfoService.createCustomer(customer);
        log.info("--- Se acaba de crear un nuevo cliente exitosamente. ---");
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/update/{idCustomer}")
    public ResponseEntity<Mono<Customer>> updateCustomer(
            @PathVariable("idCustomer") String idCustomer, @RequestBody Customer customer) {
        // Get IdCustomer
        Mono<Customer> updateCustomers = customerInfoService.findByIdCustomer(idCustomer);
        // Update Customer
        updateCustomers = customerInfoService.updateCustomer(customer);
        log.info("--- Se actualizo correctamente los datos del cliente. ---");
        return new ResponseEntity<>(updateCustomers, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idCustomer}")
    public ResponseEntity<Mono<Void>> deleteCustomer (
            @PathVariable("idCustomer") String idCustomer) {
        Mono<Void> entity = customerInfoService.deleteCustomer(idCustomer);
        log.info("--- Se elimino los datos del cliente exitosamente. ---");
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}
