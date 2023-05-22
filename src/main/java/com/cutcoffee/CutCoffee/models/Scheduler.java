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

    @Column(name = "day_scheduler")
    private String[][] dayScheduler;
}
