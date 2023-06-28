package com.cutcoffee.CutCoffee.models;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service")
    private Integer idService;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "service_title")
    private String serviceTitle;

    @Column(name = "service_key")
    private String serviceKey;

    @Column(name = "service_description")
    private String serviceDescription;

    @Column(name = "price")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_barber", referencedColumnName = "id_barber")
    private Integer idBarber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_point", referencedColumnName = "id_point")
    private Integer idPoint;
}
