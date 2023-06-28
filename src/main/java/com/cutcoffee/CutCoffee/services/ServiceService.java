package com.cutcoffee.CutCoffee.services;

import com.cutcoffee.CutCoffee.repositories.BarberRepo;
import com.cutcoffee.CutCoffee.repositories.SalesPointRepo;
import com.cutcoffee.CutCoffee.repositories.ServiceRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ServiceService {
    private final ServiceRepo serviceRepo;

    private final SalesPointRepo salesPointRepo;

    private final BarberRepo barberRepo;

    public ServiceService(ServiceRepo serviceRepo, SalesPointRepo salesPointRepo, BarberRepo barberRepo) {
        this.serviceRepo = serviceRepo;
        this.salesPointRepo = salesPointRepo;
        this.barberRepo = barberRepo;
    }

    public List<com.cutcoffee.CutCoffee.models.Service> findAll(){
        return serviceRepo.findAll();
    }

    public List<com.cutcoffee.CutCoffee.models.Service> findByBarberId(Integer id_barber){
        return serviceRepo.finByIdBarber(id_barber);
    }

    public List<com.cutcoffee.CutCoffee.models.Service> findByPointId(Integer id_point){
        return serviceRepo.findByIdPoint(id_point);
    }

    public List<com.cutcoffee.CutCoffee.models.Service> findByPointId(Integer id_barber, Integer id_point){
        return serviceRepo.findByIdBarberAndIdPoint(id_barber, id_point);
    }

    public String addNewService(Integer duration, String serviceTitle, String serviceDescription, Integer id_barber, Integer id_point){

        if (duration == null || serviceTitle == null || serviceDescription == null || id_barber == null || id_point == null){
            return "Service data shouldn't contain null's";
        }

        if (serviceTitle.length() < 5){
            return "Service title should be longer than 5 symbols";
        }

        String serviceKey = serviceTitle.toLowerCase().replaceAll("[^a-z]", "");
        if (!serviceRepo.findByIdBarberAndIdPointAndIdServiceAndServiceKey(id_barber,id_point, serviceKey).isEmpty()){
            return "Such service already exist";
        }

        if (barberRepo.findByIdBarber(id_barber) == null){
            return "Such barber doesn't exist";
        }

        if (salesPointRepo.findByIdPoint(id_point) == null){
            return "Such sales point doesn't exist";
        }

        if (serviceDescription.length() < 10){
            return "Service description should be longer";
        }

        if (duration < 15){
            return "Duration should be longer than 15 minutes";
        }

        com.cutcoffee.CutCoffee.models.Service service = new com.cutcoffee.CutCoffee.models.Service();
        service.setServiceTitle(serviceTitle);
        service.setServiceKey(serviceKey);
        service.setServiceDescription(serviceDescription);
        service.setDuration(duration);
        service.setIdBarber(id_barber);
        service.setIdPoint(id_point);

        serviceRepo.save(service);
        return "OK";
    }

    public String deleteService(Integer idService){
        com.cutcoffee.CutCoffee.models.Service service = serviceRepo.findByIdService(idService);

        if (service == null){
            return "Such service doesn't exist";
        }

        serviceRepo.delete(service);
        return "OK";
    }
}
