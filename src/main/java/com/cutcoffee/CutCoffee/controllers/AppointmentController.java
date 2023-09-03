package com.cutcoffee.CutCoffee.controllers;

import com.cutcoffee.CutCoffee.models.Appointment;
import com.cutcoffee.CutCoffee.services.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @GetMapping("")
    public List<Appointment> getAllAppointment(){
        return appointmentService.getAll();
    }

    @GetMapping("?customer_id={idCustomer}")
    public List<Appointment> getAllAppointmentByCustomer(Integer idCustomer){
        return appointmentService.getByCustomer(idCustomer);
    }

    @GetMapping("?barber_id={idBarber}")
    public List<Appointment> getAllAppointmentByBarber(Integer idBarber){
        return appointmentService.getByIdBarber(idBarber);
    }

    @GetMapping("/{idAppointment}")
    public Appointment getAppointmentById(Integer idAppointment){
        return appointmentService.getById(idAppointment);
    }

    @PostMapping("")
    public ResponseEntity<String> createNewAppointment(Integer id_customer, LocalDateTime s_time, LocalDateTime e_time, Integer[] service_list, Integer id_barber, Integer id_point)
    {
        return  Responser.createResponse(appointmentService.createAppointment(id_customer, s_time, e_time, service_list, id_barber, id_point));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteAppointment(Integer id){
        return Responser.createResponse(appointmentService.deleteAppointment(id));
    }
}