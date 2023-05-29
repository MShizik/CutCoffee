package com.cutcoffee.CutCoffee.repositories;

import com.cutcoffee.CutCoffee.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByIdBarber(Integer idBarber);
    List<Appointment> findByIdCustomer(Integer idCustomer);

    Appointment findByIdAppointment(Integer idAppointment);
}
