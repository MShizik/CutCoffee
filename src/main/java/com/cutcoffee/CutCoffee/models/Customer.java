package com.cutcoffee.CutCoffee.models;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Integer idCustomer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_person", referencedColumnName = "id_person")
    private Integer idPerson;

    @Column(name = "features")
    private String features;
}
