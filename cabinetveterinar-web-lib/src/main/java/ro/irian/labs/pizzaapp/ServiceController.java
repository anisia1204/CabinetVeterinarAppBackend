package ro.irian.labs.pizzaapp;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.irian.cabinetveterinarapp.domain.Service;
import ro.irian.cabinetveterinarapp.ServiceService;


import java.util.List;


@RestController
@RequestMapping("services")
@CrossOrigin(origins = "http://localhost:4200")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService){
        this.serviceService = serviceService;
    }
    
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<Service>> getAllServices(){
        List<Service> services = serviceService.getAllServices();
        if(services.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(services);
    }

    @PostMapping("/all")
    public void createService(@RequestBody Service service) {;
        serviceService.createService(service);
    }

}
