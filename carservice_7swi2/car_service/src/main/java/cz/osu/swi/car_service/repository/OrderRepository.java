package cz.osu.swi.car_service.repository;

import cz.osu.swi.car_service.models.Order;
import cz.osu.swi.car_service.models.RegistrationTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository
        extends JpaRepository<Order,Long> {

    @Query("SELECT c FROM Order c WHERE c.date_of_fulfillment = :dateOfFulfillment and c.time = :time")
    Order checkIfRegistrationTimeIsReserved(@Param("dateOfFulfillment") LocalDate date, @Param("time") RegistrationTime time);

    @Query("SELECT c \n" +
            "FROM RegistrationTime c \n" +
            "JOIN Order e ON e.time = c.id \n" +
            "WHERE e.date_of_fulfillment = :date")
    List<RegistrationTime> getRegistrationTimesWithOrders(@Param("date") LocalDate date);

}
