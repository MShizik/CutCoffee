package com.cutcoffee.CutCoffee.services;

import com.cutcoffee.CutCoffee.models.Appointment;
import com.cutcoffee.CutCoffee.repositories.AppointmentRepo;
import com.cutcoffee.CutCoffee.repositories.BarberRepo;
import com.cutcoffee.CutCoffee.repositories.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final CustomerRepo customerRepo;
    private final BarberRepo barberRepo;

    @Autowired
    public AppointmentService(AppointmentRepo appointmentRepo, CustomerRepo customerRepo, BarberRepo barberRepo) {
        this.appointmentRepo = appointmentRepo;
        this.customerRepo = customerRepo;
        this.barberRepo = barberRepo;
    }

    public List<Appointment> getAll(){
        return appointmentRepo.findAll();
    }

    public List<Appointment> getByCustomer(Integer idCustomer){
        return appointmentRepo.findByIdCustomer(idCustomer);
    }

    public List<Appointment> getByIdBarber(Integer idBarber){
        return appointmentRepo.findByIdBarber(idBarber);
    }

    public Appointment getById(Integer idAppointment){
        return appointmentRepo.findByIdAppointment(idAppointment);
    }
}
