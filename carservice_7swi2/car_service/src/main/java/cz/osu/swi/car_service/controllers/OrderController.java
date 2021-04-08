package cz.osu.swi.car_service.controllers;

import cz.osu.swi.car_service.models.*;
import cz.osu.swi.car_service.services.*;
import cz.osu.swi.car_service.utils.ConversionUtils;
import cz.osu.swi.car_service.utils.TextUtils;
import cz.osu.swi.car_service.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(path = "/OrderCreation")
@CrossOrigin("*")
public class OrderController {

    private final RegistrationTimeService registrationTimeService;
    private final OrderService orderService;
    private final StateService stateService;
    private final AddressService addressService;
    private final CustomerService customerService;

    @Autowired
    public OrderController(RegistrationTimeService registrationTimeService, OrderService orderService, StateService stateService, AddressService addressService, CustomerService customerService) {
        this.registrationTimeService = registrationTimeService;
        this.orderService = orderService;
        this.stateService = stateService;
        this.addressService = addressService;
        this.customerService = customerService;
    }

    @PostMapping("/post")
    public void createOrder(@RequestBody Map<String, Object> payLoad) {
        String city = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderCityTitle")));
        String street = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderStreetTitle")));
        String streetNumber = TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderStreetCodeTitle"));
        String postCode = TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderPostCodeTitle"));

        String name = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderNameTitle")));
        String surname = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderSurnameTitle")));
        String phone = TextUtils.removeAllWhiteSpaces((String) payLoad.get("telephoneInputTitle"));
        String email = TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderEmailTitle"));

        String carType = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces((String) payLoad.get("carTypeTitle")));
        String carPlate = TextUtils.removeAllWhiteSpaces((String) payLoad.get("carPlateTitle"));
        String countryShortcut = TextUtils.removeAllWhiteSpaces((String) payLoad.get("countrySelectTitle"));

        int carServis = Integer.parseInt((String) payLoad.get("carServicesTitle"));
        int pneuServis = Integer.parseInt((String) payLoad.get("pneuServisTitle"));
        int otherServices = Integer.parseInt((String) payLoad.get("otherServicesTitle"));

        Time time = ConversionUtils.getTimeFromString((String) payLoad.get("carTimeTitle"));
        String note = (String) payLoad.get("note");

        LocalDate dateOfFulfillment = null;
        int carYearOfProduction = 0;
        try {
            carYearOfProduction = Integer.parseInt(TextUtils.removeAllWhiteSpaces((String) payLoad.get("carYearTitle")));
            dateOfFulfillment = ConversionUtils.convertStringToLocalDate((String) payLoad.get("orderDateTitle"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Data jsou ve špatném formátu!", e);
        }

        Optional<String> temp = ValidationUtils.checkGrammarRules(carPlate, carType, String.valueOf(carYearOfProduction),
                dateOfFulfillment, String.valueOf(time),
                name, surname, phone, email, city, street, streetNumber, postCode, carServis, pneuServis, otherServices);

        if (temp.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, temp.get());

        RegistrationTime timeForRegistration = registrationTimeService.getRegistrationTimeForOrder(time);
        Order order = orderService.checkIfRegistrationTimeIsReserved(dateOfFulfillment, timeForRegistration);

        if (order != null)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Termín je zabrán objednávkou č. " + order.getId());

        State state = stateService.getStateByShortcut(countryShortcut);
        Address address = addressService.getAddressIfExists(city, street, streetNumber, postCode, state);
        if (address == null) {
            address = new Address(city, street, streetNumber, postCode, state);
            addressService.saveAddress(address);
        }

        Customer customer = customerService.getIfCustomerExists(name, surname, phone, email);
        if (customer == null) {
            customer = new Customer(name, surname, phone, email, address);
            customerService.saveCustomer(customer);
        } else if (customer.getAddress().getId() != address.getId()) {
            customer.setAddress(address);
            customerService.saveCustomer(customer);
        }

        Order newOrder = new Order(carPlate, carType, dateOfFulfillment, carYearOfProduction, carServis, pneuServis, otherServices, note, customer, timeForRegistration);

        orderService.saveOrder(newOrder);

        throw new ResponseStatusException(HttpStatus.ACCEPTED, "Objednávka byla úspěšně zaregistrována !");
    }

    @GetMapping("/getAvailableTime")
    public LocalDateTime getBestTime(){
        LocalDate current = LocalDate.now();
        Time currentTime = ConversionUtils.getTimeFromString(LocalTime.now().toString());
        List<RegistrationTime> times = this.registrationTimeService.getRegistrationTime();
        RegistrationTime timeBest;

        do{
            List<RegistrationTime> temp = this.orderService.getRegistrationTimesWithOrders(current);
            if(temp==null||temp.size()==times.size()){
                current = current.plusDays(1);
            }
            else{
                times.removeAll(temp);
                Time lastAvailableTime=ConversionUtils.getTimeFromString(times.get(times.size()-1).getTime());
                if(Objects.requireNonNull(currentTime).after(lastAvailableTime)){
                    current = current.plusDays(1);
                    currentTime = Time.valueOf(LocalTime.of(6,0,0));
                    times=this.registrationTimeService.getRegistrationTime();
                }
                else{
                    timeBest = times.get(0);
                    break;
                }
            }

        }while (true);

        return LocalDateTime.of(current, LocalTime.parse(timeBest.getTime()));
    }
}
