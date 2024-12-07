package com.ufostyle.bpicustomer.domain.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "customers")
public class Customer {

    @Id
    private String idCustomer;
    private String nombres;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
}
