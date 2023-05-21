package ro.irian.cabinetveterinarapp.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    @Query("SELECT s FROM Service s WHERE s.serviceName = :serviceName")
    Service findByName(@Param("serviceName") String serviceName);
}
