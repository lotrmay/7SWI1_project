package cz.osu.swi.car_service.services;

import cz.osu.swi.car_service.models.Order;
import cz.osu.swi.car_service.models.RegistrationTime;
import cz.osu.swi.car_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order checkIfRegistrationTimeIsReserved(LocalDate date, RegistrationTime time){
        return orderRepository.checkIfRegistrationTimeIsReserved(date, time);
    }

    public void saveOrder(Order order){
        this.orderRepository.save(order);
    }

    public List<RegistrationTime> getRegistrationTimesWithOrders(LocalDate date){
        return this.orderRepository.getRegistrationTimesWithOrders(date);
    }

}
