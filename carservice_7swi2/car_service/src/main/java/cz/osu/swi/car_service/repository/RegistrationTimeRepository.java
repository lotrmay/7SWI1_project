package cz.osu.swi.car_service.repository;

import cz.osu.swi.car_service.models.RegistrationTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;


@Repository
public interface RegistrationTimeRepository
        extends JpaRepository<RegistrationTime, Long> {

    @Query("SELECT c FROM RegistrationTime c WHERE c.time = :time")
    RegistrationTime getRegistrationTimeForOrder(@Param("time") Time time);
}
