package com.cutcoffee.CutCoffee.repositories;

import com.cutcoffee.CutCoffee.models.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarberRepo extends JpaRepository<Barber, Integer> {

    Barber findByIdBarber(Integer idBarber);

}
