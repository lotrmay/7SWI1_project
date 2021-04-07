package cz.osu.swi.car_service.repository;

import cz.osu.swi.car_service.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface StateRepository
        extends JpaRepository<State, Long> {

    @Query("SELECT c FROM State c WHERE c.state_short = :state_shortcut")
    State getStateShortcut(@Param("state_shortcut") String countryShortcut);
}
