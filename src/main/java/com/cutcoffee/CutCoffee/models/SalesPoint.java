package com.cutcoffee.CutCoffee.models;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "sales_points")
public class SalesPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_point")
    private Integer idPoint;

    @Column(name = "address")
    private String address;

    @Column(name = "open_points")
    private Integer openPoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_organization", referencedColumnName = "id_organization")
    private Integer idOrganization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator", referencedColumnName = "id_person")
    private Integer idAdministrator;
}
