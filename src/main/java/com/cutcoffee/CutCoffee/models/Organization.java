package com.cutcoffee.CutCoffee.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organization")
    private Integer idOrganization;

    @Column(name = "ur_address")
    private String urAddress;

    @Column(name = "fis_address")
    private String fisAddress;

    @Column(name = "tax_id")
    private String taxId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ceo", referencedColumnName = "id_person")
    private Integer ceo;

    @Column(name = "org_phone")
    private String orgPhone;

    @Column(name = "org_email")
    private String orgEmail;
}
