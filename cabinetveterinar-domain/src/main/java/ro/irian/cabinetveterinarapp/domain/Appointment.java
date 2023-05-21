package ro.irian.cabinetveterinarapp.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table()
public class Appointment {
    @Id
    @GeneratedValue
    private int id;
    private String animalName;
    private String dateAndTime;
    private String doctor;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Service>services;
    private String diagnostic;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Appointment(String animalName, String dateAndTime, String doctor, List<Service> services, String diagnostic, Status status) {
        this.animalName = animalName;
        this.dateAndTime = dateAndTime;
        this.doctor = doctor;
        this.services = services;
        this.diagnostic = diagnostic;
        this.status = status;
    }

    public Appointment() {
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTimeTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
