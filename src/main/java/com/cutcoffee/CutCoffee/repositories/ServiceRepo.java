package com.cutcoffee.CutCoffee.repositories;

import com.cutcoffee.CutCoffee.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepo extends JpaRepository<Service, Integer> {
    public List<Service> finByIdBarber(Integer id_barber);
    List<Service> findByIdPoint(Integer id_point);
    public List<Service> findByIdBarberAndIdPoint(Integer idBarber, Integer idPoint);
    List<Service> findByIdBarberAndIdPointAndIdServiceAndServiceKey(Integer idBarber, Integer idPoint, String serviceKey);
    public Service findByIdService(Integer id_service);

}
