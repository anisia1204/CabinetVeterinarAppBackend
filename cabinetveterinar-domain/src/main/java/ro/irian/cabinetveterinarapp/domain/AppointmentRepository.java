package ro.irian.cabinetveterinarapp.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findAppointmentByDoctorOrderByDateAndTimeDesc(String doctor);
}
