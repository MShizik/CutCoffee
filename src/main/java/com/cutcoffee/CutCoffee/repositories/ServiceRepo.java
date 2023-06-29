package com.cutcoffee.CutCoffee.repositories;

import com.cutcoffee.CutCoffee.models.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepo extends JpaRepository<Services, Integer> {
    public List<Services> findByIdBarber(Integer id_barber);
    List<Services> findByIdPoint(Integer id_point);
    public List<Services> findByIdBarberAndIdPoint(Integer idBarber, Integer idPoint);
    Services findByIdBarberAndIdPointAndServiceKey(Integer idBarber, Integer idPoint, String serviceKey);
    public Services findByIdService(Integer id_service);

}
