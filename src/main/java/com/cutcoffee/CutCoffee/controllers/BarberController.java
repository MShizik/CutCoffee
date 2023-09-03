package com.cutcoffee.CutCoffee.controllers;

import com.cutcoffee.CutCoffee.models.Barber;
import com.cutcoffee.CutCoffee.services.AppointmentService;
import com.cutcoffee.CutCoffee.services.BarberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/barber")
public class BarberController {

    BarberService barberService;

    public BarberController(BarberService barberService){
        this.barberService = barberService;
    }

    @GetMapping("")
    public List<Barber> getAllBarbers(){
        return barberService.findAll();
    }

    @GetMapping("{id}")
    public Barber getBarberById(Integer id){
        return barberService.findById(id);
    }

    @PostMapping("")
    public ResponseEntity<String> addNewBarber(String tax_id, Integer person_id){
        return Responser.createResponse(barberService.addNewBarber(tax_id, person_id));
    }

    @PostMapping("{id}")
    public ResponseEntity<String> changeBarber(Integer id, String tax_id, Integer person_id){
        return Responser.createResponse(barberService.changeBarber(id, tax_id, person_id));
    }
}