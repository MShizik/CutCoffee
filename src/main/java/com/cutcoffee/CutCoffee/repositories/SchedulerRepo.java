package com.cutcoffee.CutCoffee.repositories;

import com.cutcoffee.CutCoffee.models.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SchedulerRepo extends JpaRepository<Scheduler, Integer> {

    List<Scheduler> findByDate(Date date);

    List<Scheduler> findByIdPoint(Integer id_point);

    Scheduler findByDateAndIdPoint(Date date, Integer id_point);

    Scheduler findByIdScheduler(Integer id_scheduler);


}
