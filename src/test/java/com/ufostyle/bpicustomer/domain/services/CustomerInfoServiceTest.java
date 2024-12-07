package com.ufostyle.bpicustomer.domain.services;

import com.ufostyle.bpicustomer.domain.models.Customer;

import com.ufostyle.bpicustomer.domain.repositories.CustomerInfoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerInfoServiceTest {

    @InjectMocks
    private CustomerInfoService customerInfoService;

    @Mock
    private CustomerInfoRepo customerInfoRepo;

    private Customer customer;

    private String idCustomer;

    private List<Customer> customers;

    @BeforeEach
    void setUp() {
        // Inicializa el objeto Customer que se usara para la prueba
        idCustomer = "675328xyaebb7d677157278e";
        customer = new Customer(idCustomer, "Celia",
                "Villacorta Gonzales", "DNI", "70605040");

        // Inicializamos los clientes de prueba
        customers = List.of(
                new Customer("675328xyaebb7d677157278e", "Fabio", "Garcia Gomez",
                        "DNI", "72908070"),
                new Customer("675328mnaebb7d677157278e", "Edeiner", "Sabino Torres",
                        "DNI", "76213141")
        );
    }

    @Test
    void findAllCustomer() {
        // Mockeamos el comportamiento del repositorio para devolver una lista de clientes
        when(customerInfoRepo.findAll()).thenReturn(Flux.fromIterable(customers));

        //Llamando al metodo a probar
        Flux<Customer> result = customerInfoService.findAllCustomer();

        // Verificamos con StepVerifier (para trabajar con Mono/Flux)
        StepVerifier.create(result)
                .expectNextSequence(customers)
                .verifyComplete();

        // Verificamos que el repositorio haya sido llamado correctamente
        verify(customerInfoRepo, times(1)).findAll();
    }

    @Test
    void findByIdCustomer() {
        // Mockeamo el comportamiento del repositorio para devolver un customer
        when(customerInfoRepo.findById(idCustomer)).thenReturn(Mono.just(customer));

        // Llamamos al metodo a probar
        Mono<Customer> result = customerInfoService.findByIdCustomer(idCustomer);

        // Verificamos con StepVerifier (para trabajar con Mono/Flux)
        StepVerifier.create(result)
                .expectNext(customer)
                .verifyComplete();

        // Verificamos que el repositorio haya sido llamado correctamente
        verify(customerInfoRepo, times(1)).findById(idCustomer);

    }

    @Test
    void createCustomer() {
        // Mockeamos el comportamiento del repositorio
        when(customerInfoRepo.save(customer)).thenReturn(Mono.just(customer));

        // Llamando al metodo a probar
        Mono<Customer> result = customerInfoService.createCustomer(customer);

        // Verificamos con StepVerifier (para trabajar con Mono/Flux)
        StepVerifier.create(result)
                .expectNext(customer)
                .verifyComplete();

        // Verificamos que el repositorio haya sido llamado correctamente
        verify(customerInfoRepo, times(1)).save(customer);
    }

    @Test
    void updateCustomer() {
        // Mockeamos que el customer con el ID que tenemos exista
        Customer existingCustomer = new Customer(idCustomer, "Celia", "Villacorta Gonzales",
                "DNI", "70605040");
        when(customerInfoRepo.findById(idCustomer)).thenReturn(Mono.just(existingCustomer));

        // Mockeamos que el repositorio guarde correctamente el cliente actualizado
        when(customerInfoRepo.save(any(Customer.class))).thenReturn(Mono.just(customer));

        // Llamando al metodo a probar
        Mono<Customer> result = customerInfoService.updateCustomer(customer);

        // Verificamos con StepVerifier (para trabajar con Mono/Flux)
        StepVerifier.create(result)
                .expectNext(customer)
                .verifyComplete();

        // Verificamos que el repositorio haya sido llamado correctamente
        verify(customerInfoRepo, times(1)).findById(idCustomer);
        verify(customerInfoRepo, times(1)).save(customer);
    }

    @Test
    void deleteCustomer() {
        // Mockeamos que el repositorio elimina correctamente el cliente
        when(customerInfoRepo.deleteById(idCustomer)).thenReturn(Mono.empty());

        // Llamamos al metodo a probar
        Mono<Void> result = customerInfoService.deleteCustomer(idCustomer);

        // Verificamos con StepVerifier
        StepVerifier.create(result)
                .verifyComplete();

        // Verificamos que el repositorio haya sido llamado correctamente
        verify(customerInfoRepo, times(1)).deleteById(idCustomer);
    }
}