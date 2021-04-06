package cz.osu.swi.car_service.repository;

import cz.osu.swi.car_service.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository
        extends JpaRepository<Order,Long> {
}
