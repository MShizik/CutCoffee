package com.cutcoffee.CutCoffee.services;

import com.cutcoffee.CutCoffee.models.Services;
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

    public List<com.cutcoffee.CutCoffee.models.Services> findAll(){
        return serviceRepo.findAll();
    }

    public List<com.cutcoffee.CutCoffee.models.Services> findByBarberId(Integer id_barber){
        return serviceRepo.findByIdBarber(id_barber);
    }

    public List<com.cutcoffee.CutCoffee.models.Services> findByPointId(Integer id_point){
        return serviceRepo.findByIdPoint(id_point);
    }

    public List<com.cutcoffee.CutCoffee.models.Services> findByIdBarberAndIdPoint(Integer id_barber, Integer id_point){
        return serviceRepo.findByIdBarberAndIdPoint(id_barber, id_point);
    }

    public Services findByInfo(Integer id_barber, Integer id_point, String serviceKey){
        return serviceRepo.findByIdBarberAndIdPointAndServiceKey(id_barber, id_point, serviceKey);
    }

    public String addNewService(Integer duration, String serviceTitle, String serviceDescription, Integer id_barber, Integer id_point){

        if (duration == null || serviceTitle == null || serviceDescription == null || id_barber == null || id_point == null){
            return "Service data shouldn't contain null's";
        }

        if (serviceTitle.length() < 5){
            return "Service title should be longer than 5 symbols";
        }

        String serviceKey = getServiceKey(serviceTitle);
        if (serviceRepo.findByIdBarberAndIdPointAndServiceKey(id_barber,id_point, serviceKey) != null ){
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

        com.cutcoffee.CutCoffee.models.Services service = new com.cutcoffee.CutCoffee.models.Services();
        service.setServiceTitle(serviceTitle);
        service.setServiceKey(serviceKey);
        service.setServiceDescription(serviceDescription);
        service.setDuration(duration);
        service.setIdBarber(id_barber);
        service.setIdPoint(id_point);

        serviceRepo.save(service);
        return "OK";
    }

    public String deleteServiceByIdService(Integer idService){
        com.cutcoffee.CutCoffee.models.Services service = serviceRepo.findByIdService(idService);

        if (service == null){
            return "Such service doesn't exist";
        }

        serviceRepo.delete(service);
        return "OK";
    }

    public String deleteServiceByInfo(Integer id_barber, Integer id_point, String serviceKey){
        com.cutcoffee.CutCoffee.models.Services service = serviceRepo.findByIdBarberAndIdPointAndServiceKey(id_barber, id_point, serviceKey);

        if (service == null){
            return "Service doesn't exist";
        }

        serviceRepo.delete(service);
        return "OK";
    }

    public String deleteServiceByIdBarber(Integer id_barber){
        List<Services> services = serviceRepo.findByIdBarber(id_barber);

        if (services.isEmpty()){
            return "Such barber doesn't have any services";
        }

        services.forEach(serviceRepo::delete);

        return "OK";
    }

    public String deleteServiceByIdPoint(Integer id_point){
        List<Services> services = serviceRepo.findByIdPoint(id_point);

        if (services.isEmpty()){
            return "Such barber doesn't have any services";
        }

        services.forEach(serviceRepo::delete);

        return "OK";
    }

    public String deleteServiceByIdBarberAndIdPoint(Integer id_barber, Integer id_point){
        List<Services> services = serviceRepo.findByIdBarberAndIdPoint(id_barber, id_point);

        if (services.isEmpty())
            return "Such barber or point doesn't exist";

        services.forEach(serviceRepo::delete);
        return "OK";
    }

    public String changeService(Integer id_service, Integer duration, String serviceTitle, String serviceDescription, Integer price, Integer id_barber, Integer id_point){
        Services service = serviceRepo.findByIdService(id_service);

        if (service == null)
            return "Service with such id doesn't exist";

        if (duration != null && duration != -1){
            if (duration < 15){
                return "Duration shouldn't be less then 15 minutes";
            }
            else{
                service.setDuration(duration);
            }
        }

        if (id_barber != null && id_barber != -1){
            if (barberRepo.findByIdBarber(id_barber) == null){
                return "Barber with such id doesn't exist";
            }
            else{
                service.setIdBarber(id_barber);
            }
        }

        if (id_point != null && id_point != -1){
            if (salesPointRepo.findByIdPoint(id_point) == null){
                return "Sales point wuth such id doesn't exist";
            }
            else{
                service.setIdPoint(id_point);
            }
        }

        if (serviceTitle != null && !serviceTitle.isEmpty()){
            if (serviceTitle.length() < 5){
                return "Service title length shouldn't be less then 5 symbols";
            }
            else if(serviceRepo.findByIdBarberAndIdPointAndServiceKey(service.getIdBarber(), service.getIdPoint(), service.getServiceKey()) != null){
                return "Same service already exist";
            }
            else{
                service.setServiceTitle(serviceTitle);
                service.setServiceKey(getServiceKey(serviceTitle));
            }
        }

        if (serviceDescription != null && !serviceDescription.isEmpty()){
            if (serviceDescription.length() < 10){
                return "Service description shouldn't be less then 10 symbols";
            }
            else{
                service.setServiceDescription(serviceDescription);
            }
        }

        if (price != null && price != -1){
            if (price < 0){
                return "Price shouldn't be less then 0";
            }
            else{
                service.setPrice(price);
            }
        }

        serviceRepo.save(service);

        return "OK";
    }

    public String getServiceKey(String serviceTitle){
        return serviceTitle.toLowerCase().replaceAll("[^a-z]", "");
    }
}
