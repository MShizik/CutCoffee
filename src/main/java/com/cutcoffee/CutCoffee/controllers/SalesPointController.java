package com.cutcoffee.CutCoffee.controllers;

import com.cutcoffee.CutCoffee.services.AppointmentService;
import com.cutcoffee.CutCoffee.services.BarberService;
import com.cutcoffee.CutCoffee.services.SalesPointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sales_point")
public class SalesPointController {

    SalesPointService salesPointService;

    public SalesPointController(SalesPointService salesPointService) {
        this.salesPointService = salesPointService;
    }
}
