package ro.irian.labs.pizzaapp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.irian.cabinetveterinarapp.AppointmentService;
import ro.irian.cabinetveterinarapp.ServiceService;
import ro.irian.cabinetveterinarapp.domain.Appointment;
import ro.irian.cabinetveterinarapp.domain.Status;

import javax.annotation.PostConstruct;

import java.util.List;


@RestController
@RequestMapping("appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ServiceService serviceService;

    public AppointmentController(AppointmentService appointmentService, ServiceService serviceService){
        this.appointmentService = appointmentService;
        this.serviceService = serviceService;
    }

    @PostConstruct
    public void loadData(){
        serviceService.save();
        appointmentService.save();
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<Page<Appointment>> getAllAppointments(@RequestParam(defaultValue = "0") int page){
        Page<Appointment> appointments = appointmentService.getAllAppointments(PageRequest.of(page, 5, Sort.by("dateAndTime").descending()));
        if(appointments.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointments);
    }

    @PostMapping("/all")
    public void createAppointment(@RequestBody Appointment appointment) {
        appointmentService.createAppointment(appointment);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable int id){
        Appointment appointment = appointmentService.findById(id);
        return ResponseEntity.ok(appointment);
    }

    @PutMapping("/all/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable int id, @RequestBody Appointment appointmentDetails) {
        Appointment appointment = appointmentService.findById(id);

        Appointment updatedAppointment = appointmentService.updateAppointment(appointment, appointmentDetails);

        return ResponseEntity.ok(updatedAppointment);
    }

    @GetMapping("/statusTypes")
    public List<Status> getStatusTypes(){
        return appointmentService.getStatusTypes();
    }

    @GetMapping("/all/filteredByDoctor")
    public ResponseEntity<?> findAppointmentsByDoctor(@RequestParam String doctor){
        if (doctor == null || doctor.isEmpty()) {
            return ResponseEntity.badRequest().body("Doctor name is required");
        }

        List<Appointment> filteredAppointments = appointmentService.findAppointmentsByDoctor(doctor);

        if (filteredAppointments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(filteredAppointments);
    }
}
