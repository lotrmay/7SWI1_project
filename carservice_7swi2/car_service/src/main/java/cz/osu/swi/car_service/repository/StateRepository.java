package cz.osu.swi.car_service.repository;

import cz.osu.swi.car_service.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository
        extends JpaRepository<State, Long> {
}
