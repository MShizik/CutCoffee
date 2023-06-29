package com.cutcoffee.CutCoffee.services;

import com.cutcoffee.CutCoffee.models.Scheduler;
import com.cutcoffee.CutCoffee.repositories.AppointmentRepo;
import com.cutcoffee.CutCoffee.repositories.BarberRepo;
import com.cutcoffee.CutCoffee.repositories.SalesPointRepo;
import com.cutcoffee.CutCoffee.repositories.SchedulerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SchedulerService {

    private final AppointmentRepo appointmentRepo;
    private final SalesPointRepo salesPointRepo;

    private final BarberRepo barberRepo;
    private final SchedulerRepo schedulerRepo;

    public SchedulerService(AppointmentRepo appointmentRepo, SalesPointRepo salesPointRepo, BarberRepo barberRepo, SchedulerRepo schedulerRepo) {
        this.appointmentRepo = appointmentRepo;
        this.salesPointRepo = salesPointRepo;
        this.barberRepo = barberRepo;
        this.schedulerRepo = schedulerRepo;
    }

    public List<Scheduler> findByDate(Date date){
        return schedulerRepo.findByDate(date);
    }

    public List<Scheduler> findBySalesPoint(Integer id_point){
        return schedulerRepo.findByIdPoint(id_point);
    }

    public Scheduler findBySalesPointAndDate(Integer id_point, Date date){
        return schedulerRepo.findByDateAndIdPoint(date, id_point);
    }

    public String createScheduler(Date date, Integer id_sales_point, Integer startHour, Integer startMinutes, Integer endHour, Integer endMinutes){
        Scheduler scheduler = new Scheduler();

        if (date == null || id_sales_point == null || startHour == null || startMinutes == null || endHour == null || endMinutes == null){
            return "Some of params are null";
        }

        if (schedulerRepo.findByDateAndIdPoint(date, id_sales_point) != null){
            return "Scheduler for this date already exist";
        }

        if (!date.after(new Date())){
            return "Scheduler can't be created for the past days";
        }

        if (salesPointRepo.findByIdPoint(id_sales_point) == null){
            return "Sales point doesn't exist";
        }

        if (startHour < 0 || startHour > 24){
            return "Start hour is not valid";
        }

        if (endHour < 0 || endHour > 24){
            return "End hour is not valid";
        }

        if (startMinutes < 0 || startMinutes > 60 || startMinutes % 15 != 0){
            return "Start minutes are not valid";
        }

        if (endMinutes < 0 || endMinutes > 60 || endMinutes % 15 != 0){
            return "End minutes are not valid";
        }

        var openPoints = salesPointRepo.findByIdPoint(id_sales_point).getOpenPoints();

        int intervalMinutes = ((endHour * 60 + endMinutes) - (startHour * 60 + startMinutes));
        String[][] schedulerArray = new String[openPoints + 1][intervalMinutes];

        for (int i = 0 ; i < openPoints; i++){
            startMinutes += 15;
            if (startMinutes >= 60) {
                startMinutes -= 60;
                startHour++;
            }
            schedulerArray[0][i] = startHour + ":" + startMinutes;
            for(int j = 1; j < openPoints; j++){
                schedulerArray[j][i] = "Free";
            }
        }

        scheduler.setDayScheduler(schedulerArray);
        scheduler.setDate(date);
        scheduler.setId_point(id_sales_point);
        schedulerRepo.save(scheduler);
        return "OK";
    }

    public String deleteScheduler(Integer id_scheduler){
        Scheduler scheduler = schedulerRepo.findByIdScheduler(id_scheduler);
        if (scheduler == null){
            return "Scheduler with such id doesn't exist";
        }
        if (!checkClearness(scheduler.getDayScheduler())){
            return "Something was schedudle in it";
        }
        schedulerRepo.delete(scheduler);
        return "OK";
    }

    public String deleteSchedulerByInfo(Date date, Integer id_sales_point) {
        Scheduler scheduler = schedulerRepo.findByDateAndIdPoint(date, id_sales_point);
        if (scheduler == null) {
            return "Scheduler with such id doesn't exist";
        }
        if (!checkClearness(scheduler.getDayScheduler())) {
            return "Something was schedudle in it";
        }
        schedulerRepo.delete(scheduler);
        return "OK";
    }

    public Boolean checkClearness(String[][] schedulerArray){
        for (int i = 1; i < schedulerArray.length ;i++){
            for(int j = 0; j < schedulerArray[i].length; i++){
                if (!schedulerArray[i][j].equals("Free")){
                    return false;
                }
            }
        }
        return true;
    }
}
