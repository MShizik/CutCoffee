package com.cutcoffee.CutCoffee.models;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "barbers")
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_barber")
    private Integer idBarber;

    @Column(name = "tax_id")
    private String taxId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_person", referencedColumnName = "id_person")
    private Integer idPerson;
}
