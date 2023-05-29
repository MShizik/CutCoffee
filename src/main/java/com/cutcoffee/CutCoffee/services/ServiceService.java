package com.cutcoffee.CutCoffee.services;

import com.cutcoffee.CutCoffee.repositories.BarberRepo;
import com.cutcoffee.CutCoffee.repositories.ServiceRepo;
import com.cutcoffee.CutCoffee.models.Services;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ServiceService {

    private final BarberRepo barberRepo;

    private final ServiceRepo serviceRepo;

    @Autowired
    public ServiceService(ServiceRepo serviceRepo, BarberRepo barberRepo){
        this.barberRepo = barberRepo;
        this.serviceRepo = serviceRepo;
    }

    public List<Services> getAll(){
        return serviceRepo.findAll();
    }

    public List<Services> getAllByBarberID(Integer idBarber){
        return serviceRepo.findByIdBarber(idBarber);
    }

    public Services getServiceById(Integer idService){
        return serviceRepo.findByIdService(idService);
    }

    public String addService(Services service){
        if (!checkIfBarberExist(service.getIdBarber()))
            return "Such barber doesn't exist";
        if (service.getDuration() < 15)
            return "Duration is too small";
        serviceRepo.save(service);
        return "OK";
    }

    public Boolean checkIfBarberExist(Integer idBarber){
        return barberRepo.findByIdBarber(idBarber) != null;
    }

    public String changeService(Services updatedService){
        Services existService = getServiceById(updatedService.getIdService());
        if (existService == null)
            return "Service with such id doesn't exist";

        if (updatedService.getServiceTitle() != null){
            if (updatedService.getServiceTitle().length() > 5){
                existService.setServiceTitle(updatedService.getServiceTitle());
            }
            else{
                return "Service title is too short";
            }
        }

        if (updatedService.getServiceDescription() != null){
            if (updatedService.getServiceDescription().length() > 5){
                existService.setServiceDescription(updatedService.getServiceDescription());
            }
            else{
                return "Service description is too short";
            }
        }

        if (updatedService.getDuration() != null){
            if (updatedService.getDuration() > 15){
                existService.setDuration(updatedService.getDuration());
            }else{
                return "Service duration is too short";
            }
        }

        if (updatedService.getPrice() != null){
            if (updatedService.getPrice() > 0) {
                existService.setPrice(updatedService.getPrice());
            }
            else{
                return "Service price value is not valid";
            }
        }

        if (updatedService.getIdBarber() != null){
            if (checkIfBarberExist(updatedService.getIdBarber())){
                existService.setIdBarber(updatedService.getIdBarber());
            } else{
                return "Service id barber is not valid";
            }
        }

        serviceRepo.save(existService);
        return "OK";
    }

}
