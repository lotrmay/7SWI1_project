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
    private final OrderService orderService;
    private final RegistrationTimeService registrationTimeService;

    @Autowired
    public OrderController(RegistrationTimeService registrationTimeService, OrderService orderService) {
        this.registrationTimeService = registrationTimeService;
        this.orderService = orderService;
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

        Optional<String> temp = ValidationUtils.checkOrderData(carPlate, carType, String.valueOf(payLoad.get("carYearTitle")),
                (String) payLoad.get("orderDateTitle"), String.valueOf(time),
                name, surname, phone, email, city, street, streetNumber, postCode, carServis, pneuServis, otherServices,registrationTimeService,orderService);

        if (temp.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, temp.get());


        int carYearOfProduction = Integer.parseInt(TextUtils.removeAllWhiteSpaces((String) payLoad.get("carYearTitle")));
        LocalDate dateOfFulfillment = ConversionUtils.convertStringToLocalDate((String) payLoad.get("orderDateTitle"));

        orderService.saveOrder(carPlate, carType, dateOfFulfillment, carYearOfProduction, carServis, pneuServis, otherServices, note, name, surname, phone, email, city, street, streetNumber, postCode, countryShortcut, time);

        throw new ResponseStatusException(HttpStatus.ACCEPTED, "Objednávka byla úspěšně zaregistrována!");
    }

    @GetMapping("/getAvailableTime")
    public LocalDateTime getBestTime(){
        LocalDate current = LocalDate.now();
        Time currentTime = ConversionUtils.getTimeFromString(LocalTime.now().toString());
        List<RegistrationTime> times = this.registrationTimeService.getRegistrationTime();
        RegistrationTime timeBest = null;

        do{
            List<RegistrationTime> temp = this.orderService.getRegistrationTimesWithOrders(current);
            if(temp==null || temp.size() == times.size()){
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
                    for (RegistrationTime time: times) {
                        if(Objects.requireNonNull(ConversionUtils.getTimeFromString(time.getTime())).after(currentTime)){
                            timeBest = time;
                            break;
                        }
                    }
                    break;
                }
            }

        }while (true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime temp =  LocalTime.parse(timeBest.getTime(),formatter);
        return LocalDateTime.of(current,temp);
    }
}
