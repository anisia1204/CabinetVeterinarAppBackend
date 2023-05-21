package ro.irian.cabinetveterinarapp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.irian.cabinetveterinarapp.domain.ServiceRepository;

import java.util.List;

@Service
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }
    
    public List<ro.irian.cabinetveterinarapp.domain.Service> getAllServices() {
        return serviceRepository.findAll();
    }


    @Transactional
    public void save(){
        saveServices();
    }

    @Transactional
    public void createService(ro.irian.cabinetveterinarapp.domain.Service service){
        serviceRepository.save(service);
    }

    private void saveServices() {
        ro.irian.cabinetveterinarapp.domain.Service service1 = new ro.irian.cabinetveterinarapp.domain.Service("Deparazitare", 20);
        ro.irian.cabinetveterinarapp.domain.Service service2 = new ro.irian.cabinetveterinarapp.domain.Service("Efectuare vaccin", 30);
        ro.irian.cabinetveterinarapp.domain.Service service3 = new ro.irian.cabinetveterinarapp.domain.Service("Castrare", 40);

        serviceRepository.save(service1);
        serviceRepository.save(service2);
        serviceRepository.save(service3);

    }

}
