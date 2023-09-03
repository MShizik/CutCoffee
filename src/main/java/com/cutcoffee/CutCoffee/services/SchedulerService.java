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
import java.util.Objects;

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

        int intervalMinutes = ((endHour * 60 + endMinutes) - (startHour * 60 + startMinutes));
        String[][] schedulerArray = new String[2][intervalMinutes];

        for (int i = 0 ; i < 2; i++){
            startMinutes += 15;
            if (startMinutes >= 60) {
                startMinutes -= 60;
                startHour++;
            }
            schedulerArray[0][i] = startHour + ":" + startMinutes;
            schedulerArray[1][i] = "Free";
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

    public String deleteAppointmentFromScheduler(Integer idScheduler,  String startTime ,String endTime){
        Scheduler scheduler = schedulerRepo.findByIdScheduler(idScheduler);
        if (scheduler == null){
            return "Scheduler wasn't found";
        }

        Boolean periodChecker = false;

        String[][] dayScheduler = scheduler.getDayScheduler();

        for (int i = 0 ; i < dayScheduler[0].length; i++){
            if (Objects.equals(dayScheduler[0][i], startTime)){
                dayScheduler[1][i] = "Free";
                periodChecker = true;
            } else if (periodChecker){
                dayScheduler[1][i] = "Free";
            } else if (Objects.equals(dayScheduler[0][i], endTime)){
                dayScheduler[1][i] = "Free";
                periodChecker = false;
            }
        }

        return "OK";
    }


    public String insertAppointmentIntoScheduler(Integer idScheduler, String startTime, String endTime, Integer idAppointment){
        Scheduler scheduler = schedulerRepo.findByIdScheduler(idScheduler);
        if (scheduler == null){
            return "Scheduler wasn't found";
        }

        if (!checkIfTimeClear(idScheduler, startTime, endTime)){
            return "Selected time isn't free";
        }

        Boolean periodChecker = false;

        String[][] dayScheduler = scheduler.getDayScheduler();

        for (int i = 0 ; i < dayScheduler[0].length; i++){
            if (Objects.equals(dayScheduler[0][i], startTime)){
                dayScheduler[1][i] = String.valueOf(idAppointment);
                periodChecker = true;
            } else if (periodChecker){
                dayScheduler[1][i] = String.valueOf(idAppointment);
            } else if (Objects.equals(dayScheduler[0][i], endTime)){
                dayScheduler[1][i] = String.valueOf(idAppointment);
                periodChecker = false;
            }
        }

        return "OK";
    }

    public Boolean checkIfTimeClear(Integer idScheduler, String startTime, String endTime){
        Scheduler scheduler = schedulerRepo.findByIdScheduler(idScheduler);
        if (scheduler == null){
            return false;
        }

        Boolean periodChecker = false;

        String[][] dayScheduler = scheduler.getDayScheduler();

        for (int i = 0 ; i < dayScheduler[0].length; i++){
            if (Objects.equals(dayScheduler[0][i], startTime)){
                if (!Objects.equals(dayScheduler[1][i], "Free")){
                    return false;
                }
                else{
                    periodChecker = true;
                }
            } else if (periodChecker){
                if (!Objects.equals(dayScheduler[1][i], "Free")){
                    return false;
                }
            } else if (Objects.equals(dayScheduler[0][i], endTime)){
                if (!Objects.equals(dayScheduler[1][i], "Free")){
                    return false;
                }
                else{
                    periodChecker = false;
                }
            }
        }

        return true;

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
