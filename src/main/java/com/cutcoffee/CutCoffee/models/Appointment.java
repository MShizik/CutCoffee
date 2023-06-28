package com.cutcoffee.CutCoffee.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_appointment")
    private Integer idAppointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer", referencedColumnName = "id_customer")
    private Integer idCustomer;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "service_list")
    private Integer[] serviceList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_barber", referencedColumnName = "id_barber")
    private Integer idBarber;
}
