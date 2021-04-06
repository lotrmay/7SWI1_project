package cz.osu.swi.car_service.controllers;

import cz.osu.swi.car_service.models.RegistrationTime;
import cz.osu.swi.car_service.models.State;
import cz.osu.swi.car_service.services.RegistrationTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/OrderCreation")
@CrossOrigin("*")
public class RegistrationTimeController {

    private final RegistrationTimeService registrationTimeService;

    @Autowired
    public RegistrationTimeController(RegistrationTimeService registrationTimeService) {
        this.registrationTimeService = registrationTimeService;
    }

    @GetMapping("/RegistrationTimes")
    public List<RegistrationTime> showStates(){
        return registrationTimeService.getRegistrationTime();
    }

}
