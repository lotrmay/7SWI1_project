package cz.osu.swi.car_service.services;

import cz.osu.swi.car_service.models.*;
import cz.osu.swi.car_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final RegistrationTimeService registrationTimeService;
    private final StateService stateService;
    private final AddressService addressService;
    private final CustomerService customerService;

    @Autowired
    public OrderService(OrderRepository orderRepository,RegistrationTimeService registrationTimeService,StateService stateService, AddressService addressService,CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.registrationTimeService = registrationTimeService;
        this.stateService = stateService;
        this.addressService = addressService;
        this.customerService = customerService;
    }

    public Order checkIfRegistrationTimeIsReserved(LocalDate date, RegistrationTime time){
        return orderRepository.checkIfRegistrationTimeIsReserved(date, time);
    }

    public void saveOrder(String carPlate, String carType, LocalDate dateOfFulfillment,
                          int carYearOfProduction, int carServis, int pneuServis, int otherServices,
                          String note, String name, String surname, String phone, String email,
                          String city, String street, String streetNumber, String postCode, String countryShortcut,
                          Time time) {

        RegistrationTime timeForRegistration = registrationTimeService.getRegistrationTimeForOrder(time);
        State state = stateService.getStateByShortcut(countryShortcut);
        Address address = addressService.getAddressIfExists(city, street, streetNumber, postCode, state);
        if (address == null) {
            address = new Address(city, street, streetNumber, postCode, state);
            addressService.saveAddress(address);
        }

        Customer customer = customerService.getCustomerIfExists(name, surname, phone, email);
        if (customer == null) {
            customer = new Customer(name, surname, phone, email, address);
            customerService.saveCustomer(customer);
        } else if (customer.getAddress().getId() != address.getId()) {
            customer.setAddress(address);
            customerService.saveCustomer(customer);
        }

        this.orderRepository.save(new Order(carPlate, carType, dateOfFulfillment, carYearOfProduction, carServis, pneuServis, otherServices, note, customer, timeForRegistration));
    }

    public List<RegistrationTime> getRegistrationTimesWithOrders(LocalDate date){
        return this.orderRepository.getRegistrationTimesWithOrders(date);
    }

    public List<Order> getOrdersForDay(LocalDate date){
        return this.orderRepository.getOrdersForDay(date);
    }

    public Order getOrderForDateTime(RegistrationTime time, LocalDate date){
        return this.orderRepository.getOrderForDateTime(time, date);
    }
}
