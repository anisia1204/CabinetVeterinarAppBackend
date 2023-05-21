package ro.irian.cabinetveterinarapp;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.irian.cabinetveterinarapp.domain.Appointment;
import ro.irian.cabinetveterinarapp.domain.AppointmentRepository;
import ro.irian.cabinetveterinarapp.domain.ServiceRepository;
import ro.irian.cabinetveterinarapp.domain.Status;

import java.util.*;

import java.util.stream.Collectors;

import static ro.irian.cabinetveterinarapp.domain.Status.CREATED;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, ServiceRepository serviceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.serviceRepository = serviceRepository;
    }

    public Page<Appointment> getAllAppointments(PageRequest pageRequest) {
        return appointmentRepository.findAll(pageRequest);
    }

    @Transactional
    public void save(){
        saveAppointments();
    }

    @Transactional
    public void createAppointment(Appointment appointment) {
        if (checkIfDoctorHasAppointmentAtDateAndTime(appointment).isEmpty()) {
            appointment.setStatus(CREATED);
            appointment.setDiagnostic("?");
            List<ro.irian.cabinetveterinarapp.domain.Service> selectedServices = new ArrayList<>();
            appointment.getServices().forEach(service -> {
                ro.irian.cabinetveterinarapp.domain.Service existingService = serviceRepository.findByName(service.getServiceName());
                if(existingService != null)
                    selectedServices.add(existingService);
            });
            appointment.setServices(selectedServices);
            appointmentRepository.save(appointment);
        }
        else {
            throw new RuntimeException("The doctor already has an appointment at this date and time");
        }
    }

    private Set<Appointment> checkIfDoctorHasAppointmentAtDateAndTime(Appointment appointment){
        return appointmentRepository.findAll().stream()
                .filter(appointment1 -> appointment1.getDoctor().equals(appointment.getDoctor()))
                .filter(appointment1 -> appointment1.getDateAndTime().equals(appointment.getDateAndTime()))
                .collect(Collectors.toSet());
    }

    private void saveAppointments() {

        List<ro.irian.cabinetveterinarapp.domain.Service> services = serviceRepository.findAll();

        String dt = "24-05 10:30";
        String dt1 = "24-05 19:30";
        String dt2 = "25-05 11:00";
        String dt3 = "25-05 11:30";
        String dt4 = "26-05 15:30";
        String dt5 = "28-05 08:30";
        Appointment a1 = new Appointment("Max", dt, "Andrei", services, "?", CREATED);
        Appointment a2 = new Appointment("Tomi", dt1, "Anisia", services, "?", CREATED);
        Appointment a3 = new Appointment("Maya", dt2, "Alex", services, "?", CREATED);
        Appointment a4 = new Appointment("Ajax", dt3, "Andrei", services, "?", CREATED);
        Appointment a5 = new Appointment("Blacky", dt4, "Andrei", services, "?", CREATED);
        Appointment a6 = new Appointment("Tomi", dt5, "Raluca", services, "?", CREATED);
        Appointment a7 = new Appointment("Maya", dt1, "Andrei", services, "?", CREATED);
        Appointment a8 = new Appointment("Tomi", dt2, "Andrei", services, "?", CREATED);
        Appointment a9 = new Appointment("Max", dt4, "Anisia", services, "?", CREATED);
        Appointment a10 = new Appointment("Ajax", dt4, "Raluca", services, "?", CREATED);
        Appointment a11 = new Appointment("Blacky", dt, "Alex", services, "?", CREATED);

        appointmentRepository.save(a1);
        appointmentRepository.save(a2);
        appointmentRepository.save(a3);
        appointmentRepository.save(a4);
        appointmentRepository.save(a5);
        appointmentRepository.save(a6);
        appointmentRepository.save(a7);
        appointmentRepository.save(a8);
        appointmentRepository.save(a9);
        appointmentRepository.save(a10);
        appointmentRepository.save(a11);

    }

    public Appointment findById(int id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment doesn't exist with id " + id));

    }

    public Appointment updateAppointment(Appointment appointment, Appointment appointmentDetails) {
        appointment.setAnimalName(appointmentDetails.getAnimalName());
        appointment.setDoctor(appointmentDetails.getDoctor());
        appointment.setDateAndTimeTime(appointmentDetails.getDateAndTime());
        appointment.setServices(appointmentDetails.getServices());
        appointment.setDiagnostic(appointmentDetails.getDiagnostic());
        if(!appointmentDetails.getStatus().equals(Status.DONE))
            appointment.setStatus(appointmentDetails.getStatus());
        else
            if(appointmentDetails.getStatus().equals(Status.DONE) && !appointment.getDiagnostic().equals("?"))
                appointment.setStatus(appointmentDetails.getStatus());
            else
                throw new RuntimeException("You can't set the appointment to DONE if you don't provide a diagnostic");

        return appointmentRepository.save(appointment);
    }

    public List<Status> getStatusTypes() {

        return new ArrayList<>(Arrays.asList(Status.values()));
    }

    public List<Appointment> findAppointmentsByDoctor(String doctor) {
        return appointmentRepository.findAppointmentByDoctorOrderByDateAndTimeDesc(doctor);
    }
}
