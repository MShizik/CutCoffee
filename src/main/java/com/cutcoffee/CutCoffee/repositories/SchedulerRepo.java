package com.cutcoffee.CutCoffee.repositories;

import com.cutcoffee.CutCoffee.models.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerRepo extends JpaRepository<Scheduler, Integer> {
}
