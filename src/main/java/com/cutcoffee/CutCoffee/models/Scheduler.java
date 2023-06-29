package com.cutcoffee.CutCoffee.models;

import lombok.Data;
import jakarta.persistence.*;

import java.util.Date;

@Data
@Entity
@Table(name = "scheduler")
public class Scheduler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_scheduler")
    private Integer idScheduler;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_point", referencedColumnName = "id_point")
    private Integer id_point;

    @Column(name = "day_scheduler")
    private String[][] dayScheduler;
}
