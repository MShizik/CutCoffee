package com.cutcoffee.CutCoffee.repositories;

import com.cutcoffee.CutCoffee.models.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepo extends JpaRepository<Services, Integer> {
    List<Services> findByIdBarber(Integer id_barber);

    Services findByIdService(Integer idService);
}
