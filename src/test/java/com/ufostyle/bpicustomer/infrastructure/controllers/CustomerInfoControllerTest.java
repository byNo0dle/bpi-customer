package com.ufostyle.bpicustomer.infrastructure.controllers;

import com.ufostyle.bpicustomer.domain.models.Customer;
import com.ufostyle.bpicustomer.domain.services.CustomerInfoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
public class CustomerInfoControllerTest {

    @Mock
    private CustomerInfoService customerInfoService;

    @InjectMocks
    private CustomerInfoController customerInfoController;

    @Test
    void findAllCustomer() {
        // Creamos datos de prueba
        Customer customer1 = new Customer("675328xyaebb7d677157278e", "Ivan", "Tomas Alvarado",
                "DNI", "79213141");
        Customer customer2 = new Customer("675328mnaebb7d677157278e", "Kevin", "Torres Camacho",
                "DNI", "77121314");
        Flux<Customer> customerFlux = Flux.fromIterable(List.of(customer1, customer2));

        // Simulamos la respuesta del servicio
        when(customerInfoService.findAllCustomer()).thenReturn(customerFlux);

        // Usamos WebTestClient para hacer la solicitud HTTP al controlador
        WebTestClient.bindToController(customerInfoController)
                .build()
                .get()
                .uri("/api/customers")
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectBodyList(Customer.class)
                .hasSize(2)
                .contains(customer1, customer2);

    }

    @Test
    void findByIdCustomer() {
        // Creamos datos de prueba
        String idCustomer = "675328xyaebb7d677157278e";
        Customer customer = new Customer(idCustomer, "Jose", "Rojas Guerra",
                "DNI", "01438945");
        Mono<Customer> customerMono = Mono.just(customer);

        // Simulamos la respuesta del servicio
        when(customerInfoService.findByIdCustomer(idCustomer)).thenReturn(customerMono);

        // Usamos WebTestClient para hacer la solicitud HTTP al controlador
        WebTestClient.bindToController(customerInfoController)
                .build()
                .get()
                .uri("/api/customers/{idCustomer}", idCustomer)
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectBody(Customer.class)
                .isEqualTo(customer);
    }

    @Test
    void createCustomer() {
        // Creamos datos de prueba
        Customer customer = new Customer("675328xyaebb7d677157278e", "Miguel", "Diaz Chavez",
                "DNI", "45239012");
        Mono<Customer> customerMono = Mono.just(customer);

        // Simulamos la respuesta del servicio
        when(customerInfoService.createCustomer(customer)).thenReturn(customerMono);

        // Usamos WebTestClient para hacer la solicitud HTTP al controlador
        WebTestClient.bindToController(customerInfoController)
                .build()
                .post()
                .uri("/api/customers/create")
                .bodyValue(customer)
                .exchange()
                .expectStatus().isEqualTo(CREATED)
                .expectBody(Customer.class)
                .isEqualTo(customer);
    }

    @Test
    void updateCustomer() {
        // Datos de prueba
        String idCustomer = "675328xyaebb7d677157278e";
        Customer existingCustomer = new Customer(idCustomer, "Leonardo", "Diaz Chavez",
                "DNI", "43871256");
        Customer updatedCustomer = new Customer(idCustomer, "Sharon", "Acho Hidalgo",
                "DNI", "74129043");
        Mono<Customer> existingCustomerMono = Mono.just(existingCustomer);
        Mono<Customer> updatedCustomerMono = Mono.just(updatedCustomer);

        // Simulamos que el cliente existe
        when(customerInfoService.findByIdCustomer(idCustomer)).thenReturn(existingCustomerMono);

        // Simulamos la actualizacion
        when(customerInfoService.updateCustomer(updatedCustomer)).thenReturn(updatedCustomerMono);

        // Usamos WebTestClient para hacer la solicitud HTTP al controlador
        WebTestClient.bindToController(customerInfoController)
                .build()
                .put()
                .uri("/api/customers/update/{idCustomer}", idCustomer)
                .bodyValue(updatedCustomer)
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectBody(Customer.class)
                .isEqualTo(updatedCustomer);
    }

    @Test
    void deleteCustomer() {
        // Datos de prueba
        String idCustomer = "675328xyaebb7d677157278e";

        // Simulamos que el servicio realiza la eliminacion correctamente
        Mono<Void> voidMono = Mono.empty();

        // Simulamos que el servicio deleteCustomer devuelve Mono.empty(), lo que indica que la eliminacion fue exitosa
        when(customerInfoService.deleteCustomer(idCustomer)).thenReturn(voidMono);

        // Usamos WebTestClient para hacer la solicitud HTTP al controlador
        WebTestClient.bindToController(customerInfoController)
                .build()
                .delete()
                .uri("/api/customers/delete/{idCustomer}", idCustomer)
                .exchange()
                .expectStatus().isEqualTo(OK)
                .expectBody().isEmpty();
    }
}