package com.cutcoffee.CutCoffee.controllers;

import com.cutcoffee.CutCoffee.services.AppointmentService;
import com.cutcoffee.CutCoffee.services.BarberService;
import com.cutcoffee.CutCoffee.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

}
