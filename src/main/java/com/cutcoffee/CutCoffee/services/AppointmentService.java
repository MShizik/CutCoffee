package com.cutcoffee.CutCoffee.services;

import com.cutcoffee.CutCoffee.models.Appointment;
import com.cutcoffee.CutCoffee.models.Scheduler;
import com.cutcoffee.CutCoffee.repositories.AppointmentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final CustomerService customerService;
    private final BarberService barberService;

    private final SalesPointService salesPointService;
    private final SchedulerService schedulerService;

    public AppointmentService(AppointmentRepo appointmentRepo, CustomerService customerService, BarberService barberService, SalesPointService salesPointService, SchedulerService schedulerService) {
        this.appointmentRepo = appointmentRepo;
        this.customerService = customerService;
        this.barberService = barberService;
        this.salesPointService = salesPointService;
        this.schedulerService = schedulerService;
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

    public String deleteAppointment(Integer idAppointment){
        Appointment appointment = this.getById(idAppointment);

        if (appointment == null){
            return "Appointment doesn't exist";
        }

        String startTime = appointment.getStartTime().getHour() + ":" + appointment.getStartTime().getMinute();
        String endTime = appointment.getEndTime().getHour() + ":" + appointment.getEndTime().getMinute();

        Scheduler scheduler = schedulerService.findBySalesPointAndDate(appointment.getIdPoint(), Date.from(appointment.getStartTime().toInstant(ZoneOffset.UTC)));

        return schedulerService.deleteAppointmentFromScheduler(scheduler.getIdScheduler(), startTime, endTime);
    }

    public String createAppointment(Integer idCustomer, LocalDateTime startTime, LocalDateTime endTime, Integer[] serviceList, Integer idBarber, Integer idPoint){
        if (customerService.findById(idCustomer) == null){
            return "Customer doesn't exist";
        }

        if (barberService.findById(idBarber) == null){
            return "Barber doesn't exist";
        }

        if (salesPointService.findById(idPoint) == null){
            return "Sales Point doesn't exist";
        }

        if (startTime == null){
            return "Start Time format is wrong";
        }

        if (endTime == null){
            return "End Time format is wrong";
        }

        if (startTime.isAfter(endTime)){
            return "Start time can be after end time";
        }

        if (serviceList.length == 0){
            return "Service List can't be less or equal zero";
        }

        Date startDate = Date.from(startTime.toInstant(ZoneOffset.UTC));
        Date endDate = Date.from(endTime.toInstant(ZoneOffset.UTC));

        if (startDate != endDate){
            return "Start date doesn't equal end date";
        }

        Scheduler foundedScheduler = schedulerService.findBySalesPointAndDate(idPoint, startDate);

        if (foundedScheduler == null){
            return "Sales point doesn't create schedudler for this date";
        }

        String sStartTime = startTime.getHour() + ":" + startTime.getMinute();
        String sEndTime = endTime.getHour() + ":" + endTime.getMinute();

        if (!schedulerService.checkIfTimeClear(foundedScheduler.getIdScheduler(), sStartTime, sEndTime)){
            return "Selected time isn't free";
        }

        Appointment createdAppointment = new Appointment();
        createdAppointment.setServiceList(serviceList);
        createdAppointment.setIdPoint(idPoint);
        createdAppointment.setIdBarber(idBarber);
        createdAppointment.setStartTime(startTime);
        createdAppointment.setEndTime(endTime);
        createdAppointment.setIdCustomer(idCustomer);
        appointmentRepo.save(createdAppointment);

        return schedulerService.insertAppointmentIntoScheduler(foundedScheduler.getIdScheduler(), sStartTime, sEndTime, createdAppointment.getIdAppointment());
    }
}
