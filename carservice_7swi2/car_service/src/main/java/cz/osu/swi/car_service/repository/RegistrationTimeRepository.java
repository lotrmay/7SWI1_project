package cz.osu.swi.car_service.repository;

import cz.osu.swi.car_service.models.RegistrationTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationTimeRepository
        extends JpaRepository<RegistrationTime,Long> {
}
