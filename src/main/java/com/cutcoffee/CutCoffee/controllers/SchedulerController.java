package com.cutcoffee.CutCoffee.controllers;

import com.cutcoffee.CutCoffee.services.AppointmentService;
import com.cutcoffee.CutCoffee.services.BarberService;
import com.cutcoffee.CutCoffee.services.SchedulerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {

    SchedulerService schedulerService;

    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }
}
