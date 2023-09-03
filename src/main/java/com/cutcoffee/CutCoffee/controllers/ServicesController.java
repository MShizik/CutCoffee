package com.cutcoffee.CutCoffee.controllers;

import com.cutcoffee.CutCoffee.services.AppointmentService;
import com.cutcoffee.CutCoffee.services.BarberService;
import com.cutcoffee.CutCoffee.services.ServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServicesController {

    ServiceService serviceService;

    public ServicesController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }
}
